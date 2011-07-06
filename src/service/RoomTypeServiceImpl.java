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
	@Autowired
	private RoomPriceListService roomPriceListService = null;

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
		
		this.getRoomPriceListService().deleteRoomPriceListsByIdRoomType(id);
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

	
	public RoomTypeMapper getRoomTypeMapper() {
		return roomTypeMapper;
	}

	public void setRoomTypeMapper(RoomTypeMapper roomTypeMapper) {
		this.roomTypeMapper = roomTypeMapper;
	}

	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}

	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}
	

}
