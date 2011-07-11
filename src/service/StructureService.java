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
	public Double calculateExtraItemUnitaryPrice(Integer id_structure, Date dateIn, Date dateOut, RoomType roomType, Convention convention, Extra extra);
	
	public void addPriceListsForSeason(Integer id_structure, Integer id_season);
	public void addPriceListsForRoomType(Integer id_structure, Integer id_roomType);
	public void addPriceListsForConvention(Integer id_structure, Integer id_convention);
	
	public Boolean hasRoomFreeInPeriod(Integer id_structure,Integer roomId, Date dateIn, Date dateOut);
	public Boolean hasRoomFreeForBooking(Integer id_structure,Booking booking);
	public Boolean hasPeriodFreeForSeason(Integer id_structure, Season aSeason);
	
	
		
	public Structure findStructureByIdUser(Integer id_user);
	public Structure findStructureById(Integer id);
	public Integer updateStructure(Structure structure);
	
		

}
