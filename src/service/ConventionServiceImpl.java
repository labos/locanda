package service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

@Service
public class ConventionServiceImpl implements ConventionService{

	
	public Integer insertConvention(Structure structure, Convention convention) {
		structure.getConventions().add(convention);
		return 1;
	}

	
	public Integer updateConvention(Structure structure, Convention convention) {
		Convention oldConvention = null;
		
		oldConvention = this.findConventionById(structure,convention.getId());		
		if(oldConvention == null){
			return 0;
		}
		oldConvention.setName(convention.getName());
		oldConvention.setActivationCode(convention.getActivationCode());
		oldConvention.setDescription(convention.getDescription());
		return 1;
	}

	
	public Integer deleteConvention(Structure structure, Convention convention) {
		structure.getConventions().remove(convention);
		return 1;
	}

	
	public Convention findConventionById(Structure structure, Integer id) {
		Convention ret = null;
		
		for(Convention each: structure.getConventions()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}

	
	public Set<Convention> findConventionsByStructureAndSeasonAndRoomType(Structure structure, Season season, RoomType roomType) {
		Set<Convention> ret = new HashSet<Convention>();
		
		for (RoomPriceList roomPriceListBySeason : structure.findRoomPriceListsBySeason(season)) {
			if (roomPriceListBySeason.getRoomType().equals(roomType)) {
				ret.add(roomPriceListBySeason.getConvention());
			}
		}
		return ret;
	}

}
