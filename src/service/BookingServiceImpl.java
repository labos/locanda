package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Booking;
import model.Structure;
import model.listini.RoomPriceList;
import model.listini.Season;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private SeasonService seasonService = null;

	
	@Override
	public List<Booking> findBookingsByIdStructure(Structure structure) {
		
		return structure.getBookings();
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
				this.getRoomPriceListService().findRoomPriceListByStructureAndSeasonAndRoomTypeAndConvention(
						structure, season, booking.getRoom().getRoomType(), booking.getConvention());
			calendar = Calendar.getInstance();
			calendar.setTime(aBookingDate);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			ret = ret + listinoCameraDelGiorno.findRoomPrice(booking.getNrGuests(), dayOfWeek);
		}			
		return ret;
	}
	
	
	
	public Booking findBookingById(Structure structure,Integer id) {
		Booking ret = null;
		
		for(Booking each: structure.getBookings()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}

	public Integer insertBooking(Structure structure, Booking aBooking) {
		structure.getBookings().add(aBooking);
		return 1;
	}	
	
	public Integer updateBooking(Structure structure, Booking booking) {
		Booking oldBooking = this.findBookingById(structure,booking.getId());
		if(oldBooking==null){
			return 0;
		}
		oldBooking.setDateIn(booking.getDateIn());
		oldBooking.setDateOut(booking.getDateOut());
		oldBooking.setNrGuests(booking.getNrGuests());
		oldBooking.setExtraSubtotal(booking.getExtraSubtotal());
		oldBooking.setRoomSubtotal(booking.getRoomSubtotal());
		oldBooking.setNotes(booking.getNotes());
		oldBooking.setRoom(booking.getRoom());
		oldBooking.setExtras(booking.getExtras());
		oldBooking.setExtraItems(booking.getExtraItems());
		oldBooking.setAdjustments(booking.getAdjustments());
		oldBooking.setPayments(booking.getPayments());
		oldBooking.setGuests(booking.getGuests());
		oldBooking.setStatus(booking.getStatus());
		oldBooking.setConvention(booking.getConvention());
		//System.out.println(booking.getConvention().getId());
		//System.out.println(booking.getConvention().getName());Scrive null
		
		return 1;
	}
	
	public Integer saveOnlineBooking(Structure structure, Booking booking) {
		booking.setId(structure.nextKey());
		structure.getBookings().add(booking);
		
		
		return 1;
		
	}


	@Override
	public List<Booking> findBookingsByGuestId(Structure structure,Integer guestId) {
		List<Booking> ret = null;
		
		ret =new ArrayList<Booking>();
		for(Booking each: structure.getBookings()){
			if(each.getBooker()!=null && each.getBooker().getId().equals(guestId)){
				ret.add(each);
			}
		}
		return ret;		
	}

	


	@Override
	public Integer deleteBooking(Structure structure, Booking aBooking) {
		structure.getBookings().remove(aBooking);
		return 1;
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


	

}
