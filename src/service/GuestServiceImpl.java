package service;

import java.util.List;

import model.Guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.GuestMapper;

@Service
public class GuestServiceImpl implements GuestService{
	@Autowired
	private GuestMapper guestMapper;
	
	public List<Guest> findGuestsByIdStructure(Integer id_structure) {
		
		return this.getGuestMapper().findGuestsByIdStructure(id_structure);
	}
	
	@Override
	public Integer insertGuest(Guest guest) {
		
		return this.getGuestMapper().insertGuest(guest);
	}
	
	@Override
	public Guest findGuestById(Integer id) {		
		return this.getGuestMapper().findGuestById(id);
	}
	
	@Override
	public Integer updateGuest(Guest guest) {
		
		return this.getGuestMapper().updateGuest(guest);
	}

	@Override
	public Integer deleteGuest(Integer id) {
		
		return this.getGuestMapper().deleteGuest(id);
	}

	public GuestMapper getGuestMapper() {
		return guestMapper;
	}

	public void setGuestMapper(GuestMapper guestMapper) {
		this.guestMapper = guestMapper;
	}

}