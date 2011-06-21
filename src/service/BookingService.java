package service;

import java.util.List;

import model.Booking;
import model.Structure;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookingService {
	public Double calculateRoomSubtotalForBooking(Structure structure, Booking booking);
	public Integer insertBooking(Structure structure,Booking aBooking); 
	public Integer updateBooking(Structure structure,Booking booking);
	public List<Booking> findBookingsByIdStructure(Structure structure);
	public Booking findBookingById(Structure structure,Integer id);
	public List<Booking> findBookingsByGuestId(Structure structure,Integer guestId);
	public Integer deleteBooking(Structure structure, Booking aBooking);
	public Integer saveOnlineBooking(Structure structure, Booking booking);
}
