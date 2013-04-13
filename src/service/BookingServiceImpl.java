/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Adjustment;
import model.Booker;
import model.Booking;
import model.ExtraItem;
import model.Guest;
import model.Housed;
import model.Payment;
import model.Room;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;
import model.questura.HousedExport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.BookingMapper;

/**
 * @author utente
 *
 */
/**
 * @author utente
 *
 */
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
	@Autowired
	private HousedService housedService = null;
	@Autowired
	private GroupLeaderService groupLeaderService = null;
	@Autowired
	private HousedExportService housedExportService = null;
	
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
		if(booking!= null){

			id_guest = this.getBookerService().findBookerByIdBooking(id).getId_guest();
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
		
			}
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
	public List<Booking> findBookingIdsByIdHousedGroupLeader(Integer id_housed) {
		List<Booking> bookings = null;
		Booking booking = null;
		
		bookings = new ArrayList<Booking>();
		for(Integer id: this.getBookingMapper().findBookingIdsByIdHousedGroupLeader(id_housed)){
			booking = this.findBookingById(id);
			if(booking != null){
				bookings.add(booking);		
			}
		}
		return bookings;
	}
	
	@Override
	public Integer countBookingsByIdConvention(Integer id_convention) {
		return this.getBookingMapper().countBookingsByIdConvention(id_convention);
	}

	@Override
	public Integer countBookingsByIdRoom(Integer id_room) {
		return this.getBookingMapper().countBookingsByIdRoom(id_room);
	}

	@Override
	public Integer countBookingsByIdExtra(Integer id_extra) {
		return this.getBookingMapper().countBookingsByIdExtra(id_extra);
	}

	@Override
	public Integer countBookingsByIdGuest(Integer id_guest) {
		return this.getBookingMapper().countBookingsByIdGuest(id_guest);
	}
	
	@Override
	public Integer countBookingsByIdSeason(Integer id_season) {
		List<Booking> allStructureBookings = null;
		List<Booking> bookings = new ArrayList<Booking>();
		List<Date> bookingDates = null;
		Season season = null;
		
		season = this.getSeasonService().findSeasonById(id_season);
		allStructureBookings = this.findBookingsByIdStructure(season.getId_structure());
		for (Booking aBooking : allStructureBookings) {	
			bookingDates = aBooking.calculateBookingDates();
			for(Date aBookingDate: bookingDates){
				if(this.seasonService.includesDate(season, aBookingDate)) {
					bookings.add(aBooking);
				}
			}
		}
		return bookings.size();
	}

	@Override
	public Integer saveUpdateBooking(Booking booking) {	
		Integer ret = 0;
		Guest oldBooker = null;
		//System.out.println("saveupdatebooking");
		
		ret = this.getBookingMapper().updateBooking(booking);
		if(ret.equals(0)){
			ret = this.getBookingMapper().insertBooking(booking);
		}
		System.out.println("saveupdatebooking");
		
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
		
		/*
		oldBooker = this.getGuestService().findGuestById(booking.getBooker().getId());
		if(oldBooker == null){
			//It's a new guest and must be added
			this.getBookerService().insertBooker(booking.getBooker(), booking.getId());
		}else{
			//It's an existing guest and must be updated
			this.getBookerService().updateBooker(booking.getBooker(),booking.getId());
		}*/
		
//		//Il booker ora è un Guest che già esiste. 
		Booker booker = null;
		
		booker = this.getBookerService().findBookerByIdBooking(booking.getId());
		if(booker == null) {
			//Si tratta del primo save e devo associare il booker al booking appena creato. 
			//L'update viene gestito in REST per cui non è necessario fare l'update in questo metodo della action
			Integer id_guest = booking.getBooker().getId();
			Integer id_booking = booking.getId();
			this.getBookerService().insert(id_guest, id_booking);
			
			
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
		//set exported as deleted
		this.updateHousedExport(id);
		this.getHousedService().deleteHousedByIdBooking(id);
		this.getGroupLeaderService().deleteByIdBooking(id);
		return this.getBookingMapper().deleteBooking(id);
	}
	
	private void updateHousedExport(Integer id_booking){
		List <Housed> housedListToDelete = null;
		HousedExport housedExport  = null;
		housedListToDelete = this.getHousedService().findHousedByIdBooking(id_booking);
		for(Housed each : housedListToDelete){
	 		housedExport = this.getHousedExportService().findByIdHoused(each.getId());
	 		if(!housedExport.getExported()){
	 			this.getHousedExportService().delete(housedExport.getId());	
	 		}else{
	 			housedExport.setMode(3);
	 	 		housedExport.setExported(false);
	 	 		housedExport.setExportedQuestura(false);
	 	 		this.getHousedExportService().update(housedExport);
	 		}	
		}

	}
	
	public Double calculateRoomSubtotalForBooking(Integer id_structure, Booking booking) {
		Double ret = 0.0;
		List<Date> bookingDates = null;
		RoomPriceList listinoCameraDelGiorno;
		Season season = null;
		Integer dayOfWeek = 0;
		Calendar calendar;
		
		bookingDates = booking.calculateBookingDates();
		for(Date aBookingDate: bookingDates){
			season = this.getSeasonService().findSeasonByDate(id_structure,aBookingDate );
			listinoCameraDelGiorno =	this.getRoomPriceListService().findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
						id_structure, 
						season.getId(),
						booking.getRoom().getRoomType().getId(), 
						booking.getConvention().getId());
			calendar = Calendar.getInstance();
			calendar.setTime(aBookingDate);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			ret = ret + listinoCameraDelGiorno.findRoomPrice(booking.getNrGuests(), dayOfWeek);
		}			
		return ret;
	}
	
	public Integer saveOnlineBooking(Booking booking) {
		Integer ret = 0;

		ret = this.getBookingMapper().insertBooking(booking);

		this.getExtraItemService().deleteExtraItemsByIdBooking(booking.getId());
		for (ExtraItem extraItem : booking.getExtraItems()) {
			extraItem.setId_booking(booking.getId());
			extraItem.setId_extra(extraItem.getExtra().getId());
			this.getExtraItemService().insertExtraItem(extraItem);
		}

		// The Online Booker online is always a new guest and I have to add it every time
		// TO BE FIXED
		//Rivedere il booking online
		//this.getBookerService().insertBooker(booking.getBooker(),booking.getId());
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

	public HousedService getHousedService() {
		return housedService;
	}

	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}

	public GroupLeaderService getGroupLeaderService() {
		return groupLeaderService;
	}

	public void setGroupLeaderService(GroupLeaderService groupLeaderService) {
		this.groupLeaderService = groupLeaderService;
	}

	public HousedExportService getHousedExportService() {
		return housedExportService;
	}

	public void setHousedExportService(HousedExportService housedExportService) {
		this.housedExportService = housedExportService;
	}
}