package service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

@Transactional
public interface RoomPriceListService {
	
	public Integer insertRoomPriceList(RoomPriceList roomPriceList);
	public RoomPriceList findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(Integer id_structure, Integer id_season, Integer id_roomType, Integer id_convention);
	
	public RoomPriceList findRoomPriceListById(Integer id);
	public Integer deleteRoomPriceListById(Integer id);
	public Integer updateRoomPriceListItem(RoomPriceListItem roomPriceListItem);
	
	public Integer deleteRoomPriceListsByIdSeason(Integer id_season);
	public Integer deleteRoomPriceListsByIdRoomType(Integer id_roomType);
	public Integer deleteRoomPriceListsByIdConvention(Integer id_convention);
	

}
