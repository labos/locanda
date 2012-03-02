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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityMapper;
import persistence.mybatis.mappers.ImageMapper;
import persistence.mybatis.mappers.RoomFacilityMapper;
import persistence.mybatis.mappers.RoomTypeFacilityMapper;
import persistence.mybatis.mappers.StructureFacilityMapper;

import model.Facility;
import model.Image;

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
	
	@Override
	public Integer insert(Facility facility) {		
		return this.getFacilityMapper().insert(facility);		
	}	
		
	@Override
	public Integer update(Facility facility) {		
		return this.getFacilityMapper().update(facility);
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
	public List<Facility> findByIdStructure(Integer id_structure) {
		List<Facility> ret = null;
		Image image = null;
		
		ret = this.getFacilityMapper().findByIdStructure(id_structure);
		for(Facility each: ret){
			image = this.getImageService().findByIdFacility(each.getId());
			each.setImage(image);
		}
		return ret;
	}

	@Override
	public List<Facility> findCheckedByIdStructure(Integer id_structure) {		
		List<Integer> ids = null;
		
		ids = this.getStructureFacilityService().findIdFacilityByIdStructure(id_structure);
		return this.findByIds(ids);		
	}
	
	@Override
	public List<Facility> findCheckedByIdRoomType(Integer id_roomType) {
		List<Integer> ids = null;
		
		ids = this.getRoomTypeFacilityService().findIdFacilityByIdRoomType(id_roomType);
		return this.findByIds(ids);
	}
	
	@Override
	public List<Facility> findCheckedByIdRoom(Integer id_room) {	
		List<Integer> ids = null;
		
		ids = this.getRoomFacilityService().findIdFacilityByIdRoom(id_room);
		return this.findByIds(ids);
	}	

	@Override
	public Integer delete(Integer id) {
		Integer count = 0;
		Integer id_image;
		
		
		id_image = this.getImageService().findByIdFacility(id).getId();
		this.getImageService().delete(id_image);
		count = this.getFacilityMapper().delete(id);
		
		return count;
		
	}
	
	public FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}
	
	public void setFacilityMapper(
			FacilityMapper structureFacilityMapper) {
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


	public void setRoomTypeFacilityService(
			RoomTypeFacilityService roomTypeFacilityService) {
		this.roomTypeFacilityService = roomTypeFacilityService;
	}


	public RoomFacilityService getRoomFacilityService() {
		return roomFacilityService;
	}


	public void setRoomFacilityService(RoomFacilityService roomFacilityService) {
		this.roomFacilityService = roomFacilityService;
	}
	
	
		
}