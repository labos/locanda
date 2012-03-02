/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Facility;
import model.Image;
import model.Room;
import model.RoomType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityMapper;
import persistence.mybatis.mappers.ImageMapper;
import persistence.mybatis.mappers.RoomImageMapper;
import persistence.mybatis.mappers.RoomMapper;
import persistence.mybatis.mappers.RoomTypeMapper;

@Service
public class RoomServiceImpl implements RoomService{
	@Autowired
	private RoomMapper roomMapper = null;
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private RoomFacilityService roomFacilityService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private RoomImageService roomImageService = null;
	
	
	
	@Override
	public Integer findIdStructureByIdRoom(Integer idRoom) {
		
		return this.getRoomMapper().findIdStructureByIdRoom(idRoom);
	}

	@Override
	public Room findRoomById(Integer id) {	
		Room room = null;
		RoomType roomType = null;
		List<Facility> facilities = null;
		List<Image> images = null;
		
		room = this.getRoomMapper().findRoomById(id);
		if (room!=null){
			roomType = this.getRoomTypeMapper().findRoomTypeById(room.getId_roomType());
			room.setRoomType(roomType);	
			facilities = this.getFacilityService().findCheckedByIdRoom(id);
			room.setFacilities(facilities);
			images = this.getImageService().findCheckedByIdRoom(id);
			room.setImages(images);
		}
		return room;
	}

	@Override
	public Integer updateRoom(Room room) {
		Integer ret = 0;
		
		ret = this.getRoomMapper().updateRoom(room);
		
		/*this.getFacilityService().deleteAllFacilitiesFromRoom(room.getId());
		
		for(Facility each: room.getFacilities()){
			this.getFacilityService().insertRoomFacility(each.getId(), room.getId());
		}*/
		return ret;
	}	

	@Override
	public Integer deleteRoom(Integer id) {		
		Integer ret = 0;		
		
		this.getRoomFacilityService().deleteByIdRoom(id);
		this.getRoomImageService().deleteByIdRoom(id);
		ret = this.getRoomMapper().deleteRoom(id);
		return ret;
	}

	public List<Room> findAll() {
		return this.getRoomMapper().findAll();
	}
	
	@Override
	public List<Room> findRoomsByIdStructure(Integer id_structure) {	
		List<Room> rooms = null;
		RoomType roomType = null;
		
		rooms = this.getRoomMapper().findRoomsByIdStructure(id_structure);
		for(Room each: rooms){
			roomType = this.getRoomTypeMapper().findRoomTypeById(each.getId_roomType());
			roomType.setImages(this.getImageService().findCheckedByIdRoomType(roomType.getId()));
			each.setRoomType(roomType);
			each.setImages(this.getImageService().findCheckedByIdRoom(each.getId()));
		}
		return rooms;
	}
	
	
	
	@Override
	public List<Integer> findRoomIdsByIdStructure(Integer id_structure) {
		
		return this.getRoomMapper().findRoomIdsByIdStructure(id_structure);
	}

	@Override
	public List<Room> findRoomsByIdStructure(Integer id_structure, Integer offset, Integer rownum) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure",id_structure );
		map.put("offset", offset);
		map.put("rownum",rownum );
		return this.getRoomMapper().findRoomsByIdStructureAndOffsetAndRownum(map);
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
		/*
		for(Facility each: room.getFacilities()){
			this.getFacilityService().insertRoomFacility(each.getId(),room.getId());
		}
		*/
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

	

	public RoomImageService getRoomImageService() {
		return roomImageService;
	}

	public void setRoomImageService(RoomImageService roomImageService) {
		this.roomImageService = roomImageService;
	}

	public RoomFacilityService getRoomFacilityService() {
		return roomFacilityService;
	}

	public void setRoomFacilityService(RoomFacilityService roomFacilityService) {
		this.roomFacilityService = roomFacilityService;
	}
	
	

}
