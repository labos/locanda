package service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.BookerMapper;
import persistence.mybatis.mappers.GuestMapper;

import model.Guest;

@Service
public class BookerServiceImpl implements BookerService{
	@Autowired
	private GuestMapper guestMapper = null;
	@Autowired 
	private BookerMapper bookerMapper = null;

	

	@Override
	public Integer insertBooker(Guest booker, Integer id_booking) {
		Integer ret = 0;
		Map map = null;
		
		this.getGuestMapper().insertGuest(booker);
		map = new HashMap();
		map.put("id_booking", id_booking);
		map.put("id_guest",booker.getId());
		ret = this.getBookerMapper().insertBooker(map);
		
		return ret;
	}
	
	

	@Override
	public Integer updateBooker(Guest booker, Integer id_booking) {
		Integer ret = 0;
		Map map = null;
		
		this.getGuestMapper().updateGuest(booker);
		map = new HashMap();
		map.put("id_booking", id_booking);
		map.put("id_guest",booker.getId());
		this.getBookerMapper().deleteBookerByIdBooking(id_booking);
		ret = this.getBookerMapper().insertBooker(map);
		
		return ret;
	}

	

	@Override
	public Integer deleteBookerByIdBooking(Integer id_booking) {
		
		return this.getBookerMapper().deleteBookerByIdBooking(id_booking);
	}



	@Override
	public Integer findIdBookerByIdBooking(Integer id_booking) {
		
		return this.getBookerMapper().findIdBookerByIdBooking(id_booking);
	}



	public GuestMapper getGuestMapper() {
		return guestMapper;
	}

	public void setGuestMapper(GuestMapper guestMapper) {
		this.guestMapper = guestMapper;
	}

	public BookerMapper getBookerMapper() {
		return bookerMapper;
	}

	public void setBookerMapper(BookerMapper bookerMapper) {
		this.bookerMapper = bookerMapper;
	}
	
	

}
