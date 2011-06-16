package service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Booking;
import model.Extra;
import model.Image;
import model.RoomFacility;
import model.RoomType;
import model.Structure;
import model.StructureFacility;
import model.listini.Convention;
import model.listini.Period;

@Transactional
public interface StructureService {
	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra);
	public void refreshPriceLists(Structure structure);
	public Integer addRoomFacility(Structure structure, RoomFacility roomFacility);
	public RoomFacility findRoomFacilityByName(Structure structure, String roomFacilityName);
	public RoomFacility findRoomFacilityById(Structure structure,Integer id);
	public List<RoomFacility> findRoomFacilitiesByIds(Structure structure, List<Integer> ids);
	public List<RoomFacility> findRoomFacilitiesByIdStructure(Structure structure);
	public Boolean hasRoomFreeInPeriod(Structure structure,Integer roomId, Date dateIn, Date dateOut);
	public Boolean hasRoomFreeForBooking(Structure structure,Booking booking);
	public Boolean hasPeriodFreeForSeason(Structure structure, List<Period> periods);
	public Image findImageById(Structure structure,Integer id);
	public Integer insertImage(Structure structure,Image structureImage);
	public Integer deleteImage(Structure structure,Image structureImage);
	public StructureFacility findStructureFacilityById(Structure structure,Integer id);
	public Integer insertStructureFacility(Structure structure,StructureFacility structureFacility);
	public Integer deleteStructureFacility(Structure structure,StructureFacility structureFacility);
	
	public Structure findStructureByIdUser(Integer id_user);
	public Structure findStructureById(Integer id);
	public Integer updateStructure(Structure structure);
	
	//Da rimuovere
	public void buildStructure(Structure structure);

}
