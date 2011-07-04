package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.mybatis.mappers.FacilityMapper;
import persistence.mybatis.mappers.RoomMapper;
import persistence.mybatis.mappers.RoomTypeMapper;

import model.Facility;
import model.Room;
import model.RoomType;
import model.Structure;

@Service
public class RoomServiceImpl implements RoomService{
	@Autowired
	private RoomMapper roomMapper = null;
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;
	@Autowired
	private FacilityMapper facilityMapper = null;
	    
	@Override
	public Room findRoomById(Integer id) {	
		Room room = null;
		RoomType roomType = null;
		List<Facility> facilities = null;
		
		facilities = this.getFacilityMapper().findRoomFacilitiesByIdRoom(id);
		room = this.getRoomMapper().findRoomById(id);
		roomType = this.getRoomTypeMapper().findRoomTypeById(room.getId_roomType());
		room.setRoomType(roomType);
		room.setFacilities(facilities);
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

	@Override
	public Integer insertRoom(Room room) {		
		return this.getRoomMapper().insertRoom(room);
	}

	
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

	public FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}

	public void setFacilityMapper(FacilityMapper facilityMapper) {
		this.facilityMapper = facilityMapper;
	}
	
	

}
