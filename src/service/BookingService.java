package service;

import java.util.List;

import model.Booking;
import model.Structure;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookingService {
	public Double calculateRoomSubtotalForBooking(Integer id_structure, Booking booking);
	
	public Integer saveOnlineBooking(Booking booking);
	
	public Booking findBookingById(Integer id);
	public List<Integer> findBookingIdsByIdStructure(Integer id_structure);
	public List<Booking> findBookingsByIdStructure(Integer id_structure);
	public List<Integer> findBookingIdsByIdBooker(Integer id_booker);
	public List<Booking> findBookingsByIdBooker(Integer id_booker);
	
	public Integer countBookingsByIdConvention(Integer id_convention);
	public Integer countBookingsByIdRoom(Integer id_room);
	public Integer countBookingsByIdExtra(Integer id_extra);
	public Integer countBookingsByIdGuest(Integer id_guest);
	
	
	public Integer saveUpdateBooking(Booking booking); 
	public Integer updateBooking(Booking booking);
	public Integer deleteBooking(Integer id);
	
}
