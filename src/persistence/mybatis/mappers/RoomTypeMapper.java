package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.Room;
import model.RoomType;

public interface RoomTypeMapper {
	public Integer insertRoomType(RoomType roomType);
	
	public Integer updateRoomType(RoomType roomType);
	
	public Integer deleteRoomType(Integer id);
	
	public List<RoomType> findRoomTypesByIdStructure(Integer id_structure);
	public RoomType findRoomTypeById(Integer id);
	public RoomType findRoomTypeByIdStructureAndName(Map map);
	
	
	

}
