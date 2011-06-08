package service;

import model.RoomType;
import model.Structure;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomTypeService {
	public Integer insertRoomType(Structure structure, RoomType roomType);
	public Integer removeRoomType(Structure structure, RoomType roomType);
	public Integer updateRoomType(Structure structure, RoomType roomType);
	public RoomType findRoomTypeById(Structure structure,Integer id);
	public RoomType findRoomTypeByName(Structure structure,String name);

}
