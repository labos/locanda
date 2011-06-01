package persistence.mybatis.mappers;

import java.util.List;

import model.Guest;

public interface GuestMapper {
	public List<Guest> findGuestsByIdStructure(Integer id_structure);
	public Integer insertGuest(Guest guest);
	public Guest findGuestById(Integer id);
	public Integer updateGuest(Guest guest);
	public Integer deleteGuest(Integer id);

}
