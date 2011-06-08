package service;

import java.util.List;

import model.Room;
import model.RoomType;
import model.Structure;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomTypeService {
	public List<RoomType> findRoomTypesByIdStructure(Structure structure);
	public Integer insertRoomType(Structure structure, RoomType roomType);
	public Integer removeRoomType(Structure structure, RoomType roomType);
	public Integer updateRoomType(Structure structure, RoomType roomType);
	public RoomType findRoomTypeById(Structure structure,Integer id);
	public RoomType findRoomTypeByName(Structure structure,String name);

}
