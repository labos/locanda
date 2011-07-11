package persistence.mybatis.mappers;

import java.util.List;

import model.Booking;
import model.Structure;

public interface BookingMapper {
	public Booking findBookingById(Integer id);
	public List<Integer> findBookingIdsByIdStructure(Integer id_structure);
	public List<Integer> findBookingIdsByIdBooker(Integer id_booker);	
	
	public Integer countBookingsByIdConvention(Integer id_convention);
	public Integer countBookingsByIdRoom(Integer id_room);
	public Integer countBookingsByIdExtra(Integer id_extra);
	public Integer countBookingsByIdGuest(Integer id_guest);
	
	public Integer insertBooking(Booking booking);
	public Integer updateBooking(Booking booking);
	public Integer deleteBooking(Integer id);

}
