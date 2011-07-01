package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.mybatis.mappers.RoomMapper;
import persistence.mybatis.mappers.RoomTypeMapper;

import model.Room;
import model.RoomType;
import model.Structure;

@Service
public class RoomServiceImpl implements RoomService{
	@Autowired
	private RoomMapper roomMapper = null;
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;

	
	/*
	@Override
	public List<Room> findRoomsByIdStructure(Structure structure) {		
		return structure.getRooms();
	}	
	*/
    
	@Override
	public Room findRoomById(Integer id) {	
		Room room = null;
		RoomType roomType = null;
		
		room = this.getRoomMapper().findRoomById(id);
		roomType = this.getRoomTypeMapper().findRoomTypeById(room.getId_roomType());
		room.setRoomType(roomType);
		return room;
	}

	@Override
	public Integer updateRoom(Room room) {		
		return this.getRoomMapper().updateRoom(room);
	}	

	@Override
	public Integer deleteRoom(Integer id) {		
		return this.getRoomMapper().deleteRoom(id);
	}

	@Override
	public List<Room> findRoomsByIdStructure(Integer id_structure) {	
		List<Room> rooms = null;
		RoomType roomType = null;
		
		rooms = this.getRoomMapper().findRoomsByIdStructure(id_structure);
		for(Room each: rooms){
			roomType = this.getRoomTypeMapper().findRoomTypeById(each.getId_roomType());
			each.setRoomType(roomType);
		}
		return rooms;
	}
	
	
	public List<Room> findRoomsByIdRoomType(Integer id_roomType){
		List<Room> rooms = null;
		RoomType roomType = null;
		
		rooms = this.getRoomMapper().findRoomsByIdRoomType(id_roomType);
		for(Room each: rooms){
			roomType = this.getRoomTypeMapper().findRoomTypeById(each.getId_roomType());
			each.setRoomType(roomType);
		}
		return rooms;
		
		
		
		
		
		
	}

	@Override
	public Room findRoomByIdStructureAndName(Integer id_structure, String name ){	
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("name", name);
		return this.getRoomMapper().findRoomByIdStructureAndName(map);
	}

	/*
	@Override
	public Room findRoomByName(Structure structure, String name) {
		Room ret = null;
		
		for(Room each: structure.getRooms()){
			if(each.getName().equals(name)){
				return each;
			}
		}
		return ret;
	}*/

	/*
	@Override
	public List<Room> findRoomsByRoomTypeId(Structure structure, Integer idRoomType) {
		List <Room> ret = null;
		
		ret = new ArrayList<Room>();
		
		for(Room each: structure.getRooms()){
			if(each.getRoomType().getId().equals(idRoomType)){
				ret.add(each);
			}
		}
		ret.removeAll(Collections.singletonList(null));
		return ret;
	}*/

	/*
	@Override
	public Room findRoomById(Structure structure, Integer idRoom) {
		Room ret = null;
		
		for(Room each: structure.getRooms()){
			if(each.getId().equals(idRoom)){
				return each;
			}
		}
		return ret;
	}*/

	/*
	@Override
	public Integer insertRoom(Structure structure, Room room) {
		structure.getRooms().add(room);		
		return 1;
	}*/
	
	

	@Override
	public Integer insertRoom(Room room) {		
		return this.getRoomMapper().insertRoom(room);
	}

	/*
	@Override
	public Integer updateRoom(Structure structure, Room room) {
		Room originalRoom = null;
		
		originalRoom = this.findRoomById(structure,room.getId());
		if(originalRoom==null){
			return 0;
		}
		originalRoom.setName(room.getName());
		originalRoom.setNotes(room.getNotes());
		originalRoom.setRoomType(room.getRoomType());
		originalRoom.setFacilities(room.getFacilities());
		//la lista delle immagini gli viene aggiornata solo in occasione di ogni upload
		//originalRoom.setImages(room.getImages());
		return 1;
	}*/

	/*
	@Override
	public Integer deleteRoom(Structure structure, Room room) {
		structure.getRooms().remove(room);
		return 1;
	}*/

	public RoomMapper getRoomMapper() {
		return roomMapper;
	}

	public void setRoomMapper(RoomMapper roomMapper) {
		this.roomMapper = roomMapper;
	}

	public RoomTypeMapper getRoomTypeMapper() {
		return roomTypeMapper;
	}

	public void setRoomTypeMapper(RoomTypeMapper roomTypeMapper) {
		this.roomTypeMapper = roomTypeMapper;
	}
	
	

}
