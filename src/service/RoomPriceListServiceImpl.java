package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

@Service
public class RoomPriceListServiceImpl implements RoomPriceListService{

	
	public RoomPriceList findRoomPriceListByStructureAndSeasonAndRoomTypeAndConvention(
			Structure structure, Season season, RoomType roomType, Convention convention) {
		RoomPriceList ret = null;
		
		for(RoomPriceList each: structure.getRoomPriceLists()) {
			if (each.getSeason().equals(season) && each.getRoomType().equals(roomType) && each.getConvention().equals(convention)) {
				return each;
			}
		}
		return ret;
	}

	@Override
	public List<RoomPriceList> findRoomPriceListsBySeason(Structure structure,Season season) {
		List<RoomPriceList> ret = null;
		
		ret = new ArrayList<RoomPriceList>();
		
		for(RoomPriceList each: structure.getRoomPriceLists()){
			if (each.getSeason().equals(season)) {
				ret.add(each);
			}
		}
		return ret;
	}
	
	

}
