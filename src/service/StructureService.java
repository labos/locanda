package service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Booking;
import model.Extra;
import model.Facility;
import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.Period;
import model.listini.Season;

@Transactional
public interface StructureService {
	public Double calculateExtraItemUnitaryPrice(Structure structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra);
	public void refreshPriceLists(Structure structure);
	public void addPriceListsForSeason(Structure structure, Integer id_season);
	public void addPriceListsForRoomType(Structure structure, Integer id_roomType);
	public void addPriceListsForConvention(Structure structure, Integer id_convention);
	public Integer addRoomFacility(Structure structure, Facility roomFacility);
	public Facility findRoomFacilityByName(Structure structure, String roomFacilityName);
	public Facility findRoomFacilityById(Structure structure,Integer id);
	public List<Facility> findRoomFacilitiesByIds(Structure structure, List<Integer> ids);
	public List<Facility> findRoomFacilitiesByIdStructure(Structure structure);
	public Boolean hasRoomFreeInPeriod(Structure structure,Integer roomId, Date dateIn, Date dateOut);
	public Boolean hasRoomFreeForBooking(Structure structure,Booking booking);
	public Boolean hasPeriodFreeForSeason(Structure structure, List<Period> periods);
	public Boolean hasPeriodFreeForSeason(Structure structure, Season aSeason);
	
	
		
	public Structure findStructureByIdUser(Integer id_user);
	public Structure findStructureById(Integer id);
	public Integer updateStructure(Structure structure);
	
	//Da rimuovere
	public void buildStructure(Structure structure);
	

}
