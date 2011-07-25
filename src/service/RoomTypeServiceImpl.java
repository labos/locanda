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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.RoomTypeMapper;

import model.Facility;
import model.Room;
import model.RoomType;
import model.Structure;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{
	@Autowired
	private RoomTypeMapper roomTypeMapper = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private ImageService imageService = null;

	@Override
	public Integer insertRoomType(RoomType roomType) {
		Integer ret = 0;
		
		ret = this.getRoomTypeMapper().insertRoomType(roomType);
		for(Facility each: roomType.getFacilities()){
			this.getFacilityService().insertRoomTypeFacility(each.getId(), roomType.getId());
		}
		this.getStructureService().addPriceListsForRoomType(roomType.getId_structure(), roomType.getId());
		return ret ;
	}

	@Override
	public Integer updateRoomType(RoomType roomType) {
		Integer ret = 0;
		
		this.getFacilityService().deleteAllFacilitiesFromRoomType(roomType.getId());
		for(Facility each: roomType.getFacilities()){
			this.getFacilityService().insertRoomTypeFacility(each.getId(), roomType.getId());
		}
		ret = this.getRoomTypeMapper().updateRoomType(roomType);
		return ret;
	}

	@Override
	public Integer deleteRoomType(Integer id) {
		//Controllare se ci sono Room con id_roomType == id
		this.getRoomPriceListService().deleteRoomPriceListsByIdRoomType(id);
		this.getExtraPriceListService().deleteExtraPriceListsByIdRoomType(id);
		this.getFacilityService().deleteAllFacilitiesFromRoomType(id);
		this.getImageService().deleteAllImagesFromRoomType(id);
		
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

	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}

	public void setExtraPriceListService(ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
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
	
	
	

}
