package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Booking;
import model.Room;
import model.RoomType;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportServiceImpl implements ExportService{
	@Autowired
	private BookingService bookingService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private HousedService housedService = null;

	@Override
	public Integer calculateAvailableNumberOfRoomsForStructureInDate(Integer id_structure, Date date) {
		Integer ret = 0;
		Integer totalNumberOfRooms = 0;
		Integer numberOfOccupiedRooms = 0;		
		
		totalNumberOfRooms = this.getRoomService().findRoomsByIdStructure(id_structure).size();				
		numberOfOccupiedRooms = this.activeBookingsForStructureInDate(id_structure, date).size();			
		ret = totalNumberOfRooms - numberOfOccupiedRooms;
		
		return ret;
	}

	@Override
	public Integer calculateAvailableNumberOfBedsForStructureInDate(Integer id_structure, Date date) {
		Integer ret = 0;
		Integer totalNumberOfBeds = 0;
		Integer numberOfOccupiedBeds = 0;
		
		for(Room each: this.getRoomService().findRoomsByIdStructure(id_structure)){
			totalNumberOfBeds = totalNumberOfBeds + each.getRoomType().getMaxGuests();
			
		}
		
		for(Booking each: this.activeBookingsForStructureInDate(id_structure, date)){
			numberOfOccupiedBeds = numberOfOccupiedBeds + this.getHousedService().findHousedByIdBooking(each.getId()).size();
		}
		
		ret = totalNumberOfBeds - numberOfOccupiedBeds;
		return ret;
	}
	
	//Active bookings have
	// dateIn <= date < dateOut
	private List<Booking> activeBookingsForStructureInDate(Integer id_structure, Date date){
		List<Booking> activeBookings = null;
		
		activeBookings = new ArrayList<Booking>();
		for(Booking each: this.getBookingService().findBookingsByIdStructure(id_structure)){
			if( (DateUtils.truncatedCompareTo(each.getDateIn(), date, Calendar.DAY_OF_MONTH) <= 0) &&
					(DateUtils.truncatedCompareTo(date,each.getDateOut(), Calendar.DAY_OF_MONTH) < 0) ){
				activeBookings.add(each);	
			}
		}
		
		return activeBookings;
		
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}

	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}

	public HousedService getHousedService() {
		return housedService;
	}

	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}
	
	

}
