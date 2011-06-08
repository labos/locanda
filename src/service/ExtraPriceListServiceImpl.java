package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.Season;

@Service
public class ExtraPriceListServiceImpl implements ExtraPriceListService{

	
	@Override
	public List<ExtraPriceList> findExtraPriceListsByIdStructure(Structure structure) {
		
		return structure.getExtraPriceLists();
	}

	@Override
	public Integer insertExtraPriceList(Structure structure,ExtraPriceList aPriceList) {
		structure.getExtraPriceLists().add(aPriceList);
		return 1;
	}

	@Override
	public Integer deleteExtraPriceList(Structure structure,ExtraPriceList aPriceList) {
		structure.getExtraPriceLists().remove(aPriceList);
		return 1;
	}

	@Override
	public ExtraPriceList findExtraPriceListById(Structure structure, Integer id) {
		ExtraPriceList ret = null;
		
		for(ExtraPriceList each: structure.getExtraPriceLists()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}

	/*
	public List<ExtraPriceList> findExtraPriceListsBySeason(Structure structure, Season season) {
		List<ExtraPriceList> ret = null;
		
		ret = new ArrayList<ExtraPriceList>();
		
		for(ExtraPriceList each: structure.getExtraPriceLists()){
			if (each.getSeason().equals(season)) {
				ret.add(each);
			}
		}
		return ret;
	}
	*/
	
	@Override
	public ExtraPriceList findExtraPriceListByStructureAndSeasonAndRoomTypeAndConvention(Structure structure, Season season, RoomType roomType,Convention convention) {
		ExtraPriceList ret = null;
		
		for(ExtraPriceList each: structure.getExtraPriceLists()) {
			if (each.getSeason().equals(season) && each.getRoomType().equals(roomType) && each.getConvention().equals(convention)) {
				return each;
			}
		}
		return ret;
	}
	

}
