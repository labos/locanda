package service;

import java.util.Map;

import model.Guest;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookerService {
	public Integer insertBooker(Guest booker, Integer id_booking);
	public Integer updateBooker(Guest booker, Integer id_booking);
	
	public Integer deleteBookerByIdBooking(Integer id_booking);
	
	public Integer findIdBookerByIdBooking(Integer id_booking);
	
}
