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

import model.Image;
import model.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.RoomTypeMapper;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private RoomTypeFacilityService roomTypeFacilityService = null;
	@Autowired
	private RoomTypeImageService roomTypeImageService = null;
	
	
	@Override
	public List<RoomType> findAll() {
		return this.getRoomTypeMapper().findAll();
	}
	
	@Override
	public List<RoomType> findRoomTypesByIdStructure(Integer id_structure) {
		List<RoomType> ret = null;
		
		ret =  this.getRoomTypeMapper().findRoomTypesByIdStructure(id_structure);
		return ret;
	}
	
	@Override
	public List<Integer> findRoomTypeIdsByIdStructure(Integer id_structure) {
		return this.getRoomTypeMapper().findRoomTypeIdsByIdStructure(id_structure);
	}

	@Override
	public List<RoomType> findRoomTypesByIdStructure(Integer id_structure, Integer offset, Integer rownum) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure",id_structure );
		map.put("offset", offset);
		map.put("rownum",rownum );
		return this.getRoomTypeMapper().findRoomTypesByIdStructureAndOffsetAndRownum(map);
	}

	@Override
	public RoomType findRoomTypeById(Integer id) {
		RoomType roomType = null;
		//List<Image> images = null;
		
		roomType = this.getRoomTypeMapper().findRoomTypeById(id);
		//images = this.getImageService().findCheckedByIdRoomType(id);
		//roomType.setImages(images);
		return roomType;
		
	}

	@Override
	public RoomType findRoomTypeByIdStructureAndName(Integer id_structure, String name){
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("name", name);
		return this.getRoomTypeMapper().findRoomTypeByIdStructureAndName(map);
	}
	
	@Override
	public Integer findIdStructureByIdRoomType(Integer idRoomType) {
		return this.getRoomTypeMapper().findIdStructureByIdRoomType(idRoomType);
	}

	@Override
	public Integer insertRoomType(RoomType roomType) {
		Integer ret = 0;
		
		ret = this.getRoomTypeMapper().insertRoomType(roomType);		
		return ret ;
	}

	@Override
	public Integer updateRoomType(RoomType roomType) {
		Integer ret = 0;
		
		ret = this.getRoomTypeMapper().updateRoomType(roomType);
		return ret;
	}

	@Override
	public Integer deleteRoomType(Integer id) {
		
		this.getRoomTypeFacilityService().deleteByIdRoomType(id);
		this.getRoomTypeImageService().deleteByIdRoomType(id);
		return this.getRoomTypeMapper().deleteRoomType(id);
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
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public RoomTypeImageService getRoomTypeImageService() {
		return roomTypeImageService;
	}
	public void setRoomTypeImageService(RoomTypeImageService roomTypeImageService) {
		this.roomTypeImageService = roomTypeImageService;
	}
	public RoomTypeFacilityService getRoomTypeFacilityService() {
		return roomTypeFacilityService;
	}
	public void setRoomTypeFacilityService(RoomTypeFacilityService roomTypeFacilityService) {
		this.roomTypeFacilityService = roomTypeFacilityService;
	}

}