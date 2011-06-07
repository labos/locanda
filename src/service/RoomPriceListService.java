package service;

import org.springframework.transaction.annotation.Transactional;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

@Transactional
public interface RoomPriceListService {
	public RoomPriceList findRoomPriceListByStructureAndSeasonAndRoomTypeAndConvention(Structure structure, Season season, RoomType roomType, Convention convention);

}
