package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.RoomTypeMapper;

import model.Room;
import model.RoomType;
import model.Structure;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;

	@Override
	public Integer insertRoomType(RoomType roomType) {
		return this.getRoomTypeMapper().insertRoomType(roomType);
	}

	@Override
	public Integer updateRoomType(RoomType roomType) {
		return this.getRoomTypeMapper().updateRoomType(roomType);
	}

	@Override
	public Integer deleteRoomType(Integer id) {
		
		return this.getRoomTypeMapper().deleteRoomType(id);
	}

	@Override
	public List<RoomType> findRoomTypesByIdStructure(Integer id_structure) {
		
		return this.getRoomTypeMapper().findRoomTypesByIdStructure(id_structure);
	}

	@Override
	public RoomType findRoomTypeById(Integer id) {
		
		return this.getRoomTypeMapper().findRoomTypeById(id);
	}

	@Override
	public RoomType findRoomTypeByIdStructureAndName(Integer id_structure, String name){
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("name", name);
		return this.getRoomTypeMapper().findRoomTypeByIdStructureAndName(map);
	}

	/*
	@Override
	public List<RoomType> findRoomTypesByIdStructure(Structure structure) {
		
		return structure.getRoomTypes();
	}
*/
	/*
	@Override
	public Integer insertRoomType(Structure structure, RoomType roomType) {
		structure.getRoomTypes().add(roomType);
		return 1;
	}*/

	/*
	@Override
	public Integer removeRoomType(Structure structure, RoomType roomType) {
		structure.getRoomTypes().remove(roomType);
		return 1;
	}*/

	/*
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
	}*/

	/*
	@Override
	public RoomType findRoomTypeById(Structure structure,Integer id) {
		RoomType ret = null;
		
		for(RoomType each: structure.getRoomTypes()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}*/

	/*
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
*/
	public RoomTypeMapper getRoomTypeMapper() {
		return roomTypeMapper;
	}

	public void setRoomTypeMapper(RoomTypeMapper roomTypeMapper) {
		this.roomTypeMapper = roomTypeMapper;
	}
	

}
