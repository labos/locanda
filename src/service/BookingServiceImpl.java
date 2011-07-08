package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Adjustment;
import model.Booking;
import model.ExtraItem;
import model.Guest;
import model.Payment;
import model.Room;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private SeasonService seasonService = null;
	@Autowired
	private BookingMapper bookingMapper = null;
	@Autowired
	private BookerService bookerService = null;
	@Autowired
	private GuestService guestService = null;
	@Autowired 
	private ExtraItemService extraItemService = null;
	@Autowired
	private AdjustmentService adjustmentService = null;
	@Autowired
	private PaymentService paymentService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private ConventionService conventionService = null;

	public Booking findBookingById(Integer id){
		Booking booking = null;
		Integer id_guest;
		Guest booker = null;
		List<ExtraItem> extraItems = null;
		List<Adjustment> adjustments = null;
		List<Payment> payments = null;
		Convention convention = null;
		Room room = null;
		
		
		booking = this.getBookingMapper().findBookingById(id);
		
		id_guest = this.getBookerService().findIdBookerByIdBooking(id);
		booker = this.getGuestService().findGuestById(id_guest);
		booking.setBooker(booker);
		
		extraItems = this.getExtraItemService().findExtraItemsByIdBooking(id);
		booking.setExtraItems(extraItems);
		
		adjustments = this.getAdjustmentService().findAdjustmentsByIdBooking(id);
		booking.setAdjustments(adjustments);
		
		payments = this.getPaymentService().findPaymentsByIdBooking(id);
		booking.setPayments(payments);
		
		room = this.getRoomService().findRoomById(booking.getId_room());
		booking.setRoom(room);
		
		convention = this.getConventionService().findConventionById(booking.getId_convention());
		booking.setConvention(convention);
		
		
		return booking;
	}
	
	
	
	@Override
	public List<Integer> findBookingIdsByIdStructure(Integer id_structure) {
		
		return this.getBookingMapper().findBookingIdsByIdStructure(id_structure);
	}



	@Override
	public List<Booking> findBookingsByIdStructure(Integer id_structure) {
		List<Booking> bookings = null;
		Booking booking = null;
		
		
		bookings = new ArrayList<Booking>();
		for(Integer id: this.getBookingMapper().findBookingIdsByIdStructure(id_structure)){
			booking = this.findBookingById(id);
			bookings.add(booking);
		}
			
		return bookings;
	}

	

	@Override
	public List<Integer> findBookingIdsByIdBooker(Integer id_booker) {
		
		return this.getBookingMapper().findBookingIdsByIdBooker(id_booker);
	}



	@Override
	public List<Booking> findBookingsByIdBooker(Integer id_booker) {
		List<Booking> bookings = null;
		Booking booking = null;
		
		
		bookings = new ArrayList<Booking>();
		for(Integer id: this.getBookingMapper().findBookingIdsByIdBooker(id_booker)){
			booking = this.findBookingById(id);
			bookings.add(booking);
		}
			
		return bookings;
	}	

	@Override
	public Integer saveUpdateBooking(Booking booking) {	
		Integer ret = 0;
		Guest oldBooker = null;
		
		ret = this.getBookingMapper().updateBooking(booking);
		if(ret.equals(0)){
			ret = this.getBookingMapper().insertBooking(booking);
		}
		
		
		this.getExtraItemService().deleteExtraItemsByIdBooking(booking.getId());
		for(ExtraItem extraItem: booking.getExtraItems()){
			extraItem.setId_booking(booking.getId());
			extraItem.setId_extra(extraItem.getExtra().getId());
			this.getExtraItemService().insertExtraItem(extraItem);
		}
		
		this.getAdjustmentService().deleteAdjustmentsByIdBooking(booking.getId());
		for(Adjustment adjustment: booking.getAdjustments()){
			adjustment.setId_booking(booking.getId());
			this.getAdjustmentService().insertAdjustment(adjustment);
		}
		
		this.getPaymentService().deletePaymentsByIdBooking(booking.getId());
		for(Payment payment: booking.getPayments()){
			payment.setId_booking(booking.getId());
			this.getPaymentService().insertPayment(payment);
		}		
		
		oldBooker = this.getGuestService().findGuestById(booking.getBooker().getId());
		if(oldBooker == null){
			//Si tratta di un nuovo guest e devo aggiungerlo
			this.getBookerService().insertBooker(booking.getBooker(), booking.getId());
		}else{
			//Si tratta di un guest esistente e devo fare l'update
			this.getBookerService().updateBooker(booking.getBooker(),booking.getId());
		}
				
		return ret;
	}

	@Override
	public Integer updateBooking(Booking booking) {		
		
		return this.getBookingMapper().updateBooking(booking);
	}
	
	@Override
	public Integer deleteBooking(Integer id) {
		this.getExtraItemService().deleteExtraItemsByIdBooking(id);
		this.getAdjustmentService().deleteAdjustmentsByIdBooking(id);
		this.getPaymentService().deletePaymentsByIdBooking(id);
		this.getBookerService().deleteBookerByIdBooking(id);
		return this.getBookingMapper().deleteBooking(id);
	}
	
	
	public Double calculateRoomSubtotalForBooking(Structure structure, Booking booking) {
		Double ret = 0.0;
		List<Date> bookingDates = null;
		RoomPriceList listinoCameraDelGiorno;
		Season season = null;
		Integer dayOfWeek = 0;
		Calendar calendar;
		
		bookingDates = booking.calculateBookingDates();
		for(Date aBookingDate: bookingDates){
			season = this.getSeasonService().findSeasonByDate(structure.getId(),aBookingDate );
			listinoCameraDelGiorno =
//				this.getRoomPriceListService().findRoomPriceListByStructureAndSeasonAndRoomTypeAndConvention(
//						structure, season, booking.getRoom().getRoomType(), booking.getConvention());
				this.getRoomPriceListService().findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
						structure.getId(), season.getId(), booking.getRoom().getRoomType().getId(), booking.getConvention().getId());
			calendar = Calendar.getInstance();
			calendar.setTime(aBookingDate);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			ret = ret + listinoCameraDelGiorno.findRoomPrice(booking.getNrGuests(), dayOfWeek);
		}			
		return ret;
	}
	
	public Integer saveOnlineBooking(Structure structure, Booking booking) {
		Integer ret = 0;
		Guest oldBooker = null;

		ret = this.getBookingMapper().insertBooking(booking);

		this.getExtraItemService().deleteExtraItemsByIdBooking(booking.getId());
		for (ExtraItem extraItem : booking.getExtraItems()) {
			extraItem.setId_booking(booking.getId());
			extraItem.setId_extra(extraItem.getExtra().getId());
			this.getExtraItemService().insertExtraItem(extraItem);
		}

		// Il Booker online viene sempre considerato come un nuovo guest e devo
		// sempre aggiungerlo
		// DA MODIFICARE
		this.getBookerService().insertBooker(booking.getBooker(),booking.getId());
		return ret;

	}

	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}

	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}

	public SeasonService getSeasonService() {
		return seasonService;
	}

	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	public BookingMapper getBookingMapper() {
		return bookingMapper;
	}

	public void setBookingMapper(BookingMapper bookingMapper) {
		this.bookingMapper = bookingMapper;
	}



	public BookerService getBookerService() {
		return bookerService;
	}



	public void setBookerService(BookerService bookerService) {
		this.bookerService = bookerService;
	}



	public GuestService getGuestService() {
		return guestService;
	}



	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}



	public ExtraItemService getExtraItemService() {
		return extraItemService;
	}



	public void setExtraItemService(ExtraItemService extraItemService) {
		this.extraItemService = extraItemService;
	}



	public AdjustmentService getAdjustmentService() {
		return adjustmentService;
	}



	public void setAdjustmentService(AdjustmentService adjustmentService) {
		this.adjustmentService = adjustmentService;
	}



	public PaymentService getPaymentService() {
		return paymentService;
	}



	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}



	public RoomService getRoomService() {
		return roomService;
	}



	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}



	public ConventionService getConventionService() {
		return conventionService;
	}



	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}


	

}
