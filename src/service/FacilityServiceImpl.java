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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Facility;
import model.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.mybatis.mappers.FacilityMapper;

@Service
public class FacilityServiceImpl implements FacilityService{
	@Autowired
	private FacilityMapper facilityMapper = null;	
	@Autowired
	private StructureFacilityService structureFacilityService = null;
	@Autowired
	private RoomFacilityService roomFacilityService = null;
	@Autowired
	private RoomTypeFacilityService roomTypeFacilityService = null;	
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private FacilityImageService facilityImageService = null;
	
	
	@Override
	public Integer insert(Facility facility) {	
		Integer count;

		count = this.getFacilityMapper().insert(facility);	
		this.getFacilityImageService().associateDefaultImage(facility);		
		return count;		
	}	
		
	@Override
	public Integer update(Facility facility) {
		Integer ret;
		
		ret = this.getFacilityMapper().update(facility);
		this.getFacilityImageService().updateAssociatedImage(facility);
		return ret;
	}
	
	@Override
	public List<Facility> findAll() {
		List<Facility> facilities = null;
		
		facilities = this.getFacilityMapper().findAll();
		return facilities;
	}

	@Override
	public Facility find(Integer id) {	
		Facility ret = null;
		Image image = null;
		
		ret = this.getFacilityMapper().find(id);
		image = this.getImageService().findByIdFacility(id);
		ret.setImage(image);
		return ret;
	}
	
	@Override
	public List<Facility> findByIds(List<Integer> ids) {
		List<Facility> ret = null;
		
		ret = new ArrayList<Facility>();
		for(Integer each: ids){
			ret.add(this.find(each));
		}
		return ret;
	}
	
	@Override
	public List<Facility> findByIdStructure(Integer id_structure,Integer offset, Integer rownum) {
		List<Facility> ret = null;
		Image image = null;
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("offset", offset);
		map.put("rownum", rownum);
		
		ret = this.getFacilityMapper().findByIdStructure(map);
		for(Facility each: ret){
			image = this.getImageService().findByIdFacility(each.getId());
			each.setImage(image);
		}
		return ret;
	}

	@Override
	public List<Facility> findCheckedByIdStructure(Integer id_structure,Integer offset, Integer rownum) {		
		List<Integer> ids = null;
		
		ids = this.getStructureFacilityService().findIdFacilityByIdStructure(id_structure,offset,rownum);
		return this.findByIds(ids);		
	}
	
	@Override
	public List<Facility> findCheckedByIdRoomType(Integer id_roomType,Integer offset, Integer rownum) {
		List<Integer> ids = null;
		
		ids = this.getRoomTypeFacilityService().findIdFacilityByIdRoomType(id_roomType,offset,rownum);
		return this.findByIds(ids);
	}
	
	@Override
	public List<Facility> findCheckedByIdRoom(Integer id_room,Integer offset, Integer rownum) {	
		List<Integer> ids = null;
		
		ids = this.getRoomFacilityService().findIdFacilityByIdRoom(id_room,offset,rownum);
		return this.findByIds(ids);
	}	

	@Override
	public Integer delete(Integer id) {
		Integer count = 0;
		Integer id_image;
		
		this.getStructureFacilityService().deleteByIdFacility(id);
		this.getRoomTypeFacilityService().deleteByIdFacility(id);
		this.getRoomFacilityService().deleteByIdFacility(id);
		
		id_image = this.getImageService().findByIdFacility(id).getId();
		this.getFacilityImageService().deleteByIdFacility(id);
		this.getImageService().delete(id_image);
		count = this.getFacilityMapper().delete(id);
		return count;
	}
	
	public FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}
	public void setFacilityMapper(FacilityMapper structureFacilityMapper) {
		this.facilityMapper = structureFacilityMapper;
	}	
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public StructureFacilityService getStructureFacilityService() {
		return structureFacilityService;
	}
	public void setStructureFacilityService(StructureFacilityService structureFacilityService) {
		this.structureFacilityService = structureFacilityService;
	}
	public RoomTypeFacilityService getRoomTypeFacilityService() {
		return roomTypeFacilityService;
	}
	public void setRoomTypeFacilityService(RoomTypeFacilityService roomTypeFacilityService) {
		this.roomTypeFacilityService = roomTypeFacilityService;
	}
	public RoomFacilityService getRoomFacilityService() {
		return roomFacilityService;
	}
	public void setRoomFacilityService(RoomFacilityService roomFacilityService) {
		this.roomFacilityService = roomFacilityService;
	}
	public FacilityImageService getFacilityImageService() {
		return facilityImageService;
	}
	public void setFacilityImageService(FacilityImageService facilityImageService) {
		this.facilityImageService = facilityImageService;
	}
	
}