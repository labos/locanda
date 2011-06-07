package service;

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
