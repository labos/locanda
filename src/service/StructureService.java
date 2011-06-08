package service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Booking;
import model.Extra;
import model.RoomFacility;
import model.RoomType;
import model.Structure;
import model.listini.Convention;

@Transactional
public interface StructureService {
	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra);
	public void refreshPriceLists(Structure structure);
	public Integer addRoomFacility(Structure structure, RoomFacility roomFacility);
	public RoomFacility findRoomFacilityByName(Structure structure, String roomFacilityName);
	public RoomFacility findRoomFacilityById(Structure structure,Integer id);
	public List<RoomFacility> findRoomFacilitiesByIds(Structure structure, List<Integer> ids);
	public Boolean hasRoomFreeInPeriod(Structure structure,Integer roomId, Date dateIn, Date dateOut);
	public Boolean hasRoomFreeForBooking(Structure structure,Booking booking);

}
