package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.Room;

public interface RoomMapper {
	public Integer insertRoom(Room room);
	
	public Integer updateRoom(Room room);
	
	public Integer deleteRoom(Integer id);
	
	public Room findRoomById(Integer id);
	public Room findRoomByIdStructureAndName(Map map);
	public List<Room> findRoomsByIdStructure(Integer id_structure);
	public List<Room> findRoomsByIdRoomType(Integer id_roomType);
	

}
