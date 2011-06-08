package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

@Transactional
public interface RoomPriceListService {
	public RoomPriceList findRoomPriceListByStructureAndSeasonAndRoomTypeAndConvention(Structure structure, Season season, RoomType roomType, Convention convention);
	public List<RoomPriceList> findRoomPriceListsBySeason(Structure structure,Season season);
	public RoomPriceList findRoomPriceListById(Structure structure,Integer id);
	public Integer insertRoomPriceList(Structure structure,RoomPriceList aPriceList);
	public Integer deleteRoomPriceList(Structure structure,RoomPriceList aPriceList);

}
