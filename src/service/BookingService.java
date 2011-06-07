package service;

import model.Booking;
import model.Structure;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookingService {
	public Double calculateRoomSubtotalForBooking(Structure structure, Booking booking);

}
