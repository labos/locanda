package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.mybatis.mappers.FacilityMapper;
import persistence.mybatis.mappers.RoomMapper;
import persistence.mybatis.mappers.RoomTypeMapper;
import persistence.mybatis.mappers.ImageMapper;
import model.Facility;
import model.Room;
import model.RoomType;

@Service
public class RoomServiceImpl implements RoomService{
	@Autowired
	private RoomMapper roomMapper = null;
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;
	@Autowired
	private ImageMapper imageMapper = null;   
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private ImageService imageService = null;
	
	
	@Override
	public Room findRoomById(Integer id) {	
		Room room = null;
		RoomType roomType = null;
		List<Facility> facilities = null;
		
		
		room = this.getRoomMapper().findRoomById(id);
		if (room!=null){
			roomType = this.getRoomTypeMapper().findRoomTypeById(room.getId_roomType());
			room.setRoomType(roomType);
			
			facilities = this.getFacilityService().findRoomFacilitiesByIdRoom(id);
			room.setFacilities(facilities);
		}

		return room;
	}

	@Override
	public Integer updateRoom(Room room) {
		Integer ret = 0;
		
		ret = this.getRoomMapper().updateRoom(room);
		
		this.getFacilityService().deleteAllFacilitiesFromRoom(room.getId());
		for(Facility each: room.getFacilities()){
			this.getFacilityService().insertRoomFacility(each.getId(), room.getId());
		}
		return ret;
	}	

	@Override
	public Integer deleteRoom(Integer id) {		
		Integer ret = 0;
		
		//Verificare che la room si possa cancellare
		this.getFacilityService().deleteAllFacilitiesFromRoom(id);
		this.getImageService().deleteAllImagesFromRoom(id);
		ret = this.getRoomMapper().deleteRoom(id);
		return ret;
	}

	@Override
	public List<Room> findRoomsByIdStructure(Integer id_structure) {	
		List<Room> rooms = null;
		RoomType roomType = null;
		
		rooms = this.getRoomMapper().findRoomsByIdStructure(id_structure);
		for(Room each: rooms){
			roomType = this.getRoomTypeMapper().findRoomTypeById(each.getId_roomType());
			roomType.setImages(this.getImageMapper().findImagesByIdRoomType(roomType.getId()));
			each.setRoomType(roomType);
			each.setImages(this.getImageMapper().findImagesByIdRoom(each.getId()));
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
	public Integer countRoomsByIdRoomType(Integer id_roomType) {
		
		return this.getRoomMapper().countRoomsByIdRoomType(id_roomType);
	}

	@Override
	public Integer insertRoom(Room room) {	
		Integer ret = 0;
		
		ret = this.getRoomMapper().insertRoom(room);
		for(Facility each: room.getFacilities()){
			this.getFacilityService().insertRoomFacility(each.getId(),room.getId());
		}
		return ret;
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

	
	public ImageMapper getImageMapper() {
		return imageMapper;
	}

	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}

	public FacilityService getFacilityService() {
		return facilityService;
	}

	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
	

}
