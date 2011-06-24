package service;

import java.util.List;

import org.springframework.stereotype.Service;

import model.RoomType;
import model.Structure;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{

	
	@Override
	public List<RoomType> findRoomTypesByIdStructure(Structure structure) {
		
		return structure.getRoomTypes();
	}

	@Override
	public Integer insertRoomType(Structure structure, RoomType roomType) {
		structure.getRoomTypes().add(roomType);
		return 1;
	}

	@Override
	public Integer removeRoomType(Structure structure, RoomType roomType) {
		structure.getRoomTypes().remove(roomType);
		return 1;
	}

	@Override
	public Integer updateRoomType(Structure structure, RoomType roomType) {
		RoomType oldRoomType = null;
		
		oldRoomType = this.findRoomTypeById(structure,roomType.getId());		
		if(oldRoomType==null){
			return 0;
		}
		oldRoomType.setName(roomType.getName());
		oldRoomType.setMaxGuests(roomType.getMaxGuests());
		oldRoomType.setFacilities(roomType.getFacilities());
		return 1;
	}

	@Override
	public RoomType findRoomTypeById(Structure structure,Integer id) {
		RoomType ret = null;
		
		for(RoomType each: structure.getRoomTypes()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}

	@Override
	public RoomType findRoomTypeByName(Structure structure, String name) {
		RoomType ret = null;
		
		for(RoomType each: structure.getRoomTypes()){
			if(each.getName().equalsIgnoreCase(name)){
				return each;
			}
		}
		return ret;
	}
	

}
