package service;

import java.util.List;

import model.Room;
import model.Structure;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomService {
	public List<Room> findRoomsByIdStructure(Structure structure);
	public Room findRoomByName(Structure structure, String name);
	public List<Room> findRoomsByRoomTypeId(Structure structure, Integer idRoomType);
	public Room findRoomById(Structure structure, Integer idRoom);
	public Integer insertRoom(Structure structure,Room room);
	public Integer updateRoom(Structure structure,Room room);
	public Integer deleteRoom(Structure structure,Room room);

}
