package service;

import java.util.List;
import java.util.Map;

import model.Room;
import model.Structure;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomService {
	
	public Integer insertRoom(Room room);
	
	public Integer updateRoom(Room room);
	
	public Integer deleteRoom(Integer id);
	
	
	public Room findRoomById(Integer id);
	public Room findRoomByIdStructureAndName(Integer id_structure, String name );
	public List<Room> findRoomsByIdStructure(Integer id_structure);
	public List<Room> findRoomsByIdRoomType(Integer id_roomType);
	
	public Integer countRoomsByIdRoomType(Integer id_roomType);
	

}
