package service;

import java.util.List;
import java.util.Map;

import model.Room;
import model.Structure;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomService {
	//public List<Room> findRoomsByIdStructure(Structure structure);
	//public Room findRoomByName(Structure structure, String name);
	//public List<Room> findRoomsByRoomTypeId(Structure structure, Integer idRoomType);
	//public Room findRoomById(Structure structure, Integer idRoom);
	//public Integer insertRoom(Structure structure,Room room);
	//public Integer updateRoom(Structure structure,Room room);
	//public Integer deleteRoom(Structure structure,Room room);
	
	
	public Integer insertRoom(Room room);
	
	public Integer updateRoom(Room room);
	
	public Integer deleteRoom(Integer id);
	
	
	public Room findRoomById(Integer id);
	public Room findRoomByIdStructureAndName(Integer id_structure, String name );
	public List<Room> findRoomsByIdStructure(Integer id_structure);
	public List<Room> findRoomsByIdRoomType(Integer id_roomType);
	

}
