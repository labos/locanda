package service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import model.Guest;

@Transactional
public interface GuestService {
	public List<Guest> findGuestsByIdStructure(Integer id_structure);
	public Integer insertGuest(Guest guest);
	public Guest findGuestById(Integer id);
	public Integer updateGuest(Guest guest);
	public Integer deleteGuest(Integer id);
}
