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
public class RoomFacilityServiceImpl implements RoomFacilityService{
	@Autowired
	private StructureFacilityService structureFacilityService = null;
	@Autowired
	private RoomFacilityMapper roomFacilityMapper = null;
	@Autowired
	private RoomTypeFacilityService roomTypeFacilityService = null;
	
	@Autowired
	private ImageService imageService = null;
	
			
	@Override
	public Integer insert(Integer id_room,Integer id_facility ) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_room", id_room);
		map.put("id_facility", id_facility);
		return this.getRoomFacilityMapper().insert(map);
		
	}
		
	@Override
	public List<Integer> findIdFacilityByIdRoom(Integer id_room) {
		List<Integer> ret = null;
		
		ret = new ArrayList<Integer>();
		for(Map map: this.getRoomFacilityMapper().findByIdRoom(id_room)){
			ret.add((Integer)map.get("id_facility"));
		}
		return ret;
		
	}



	@Override
	public Integer delete(Integer id) {
		
		return this.getRoomFacilityMapper().delete(id);
	}



	@Override
	public Integer deleteByIdRoom(Integer id_room) {
		
		return this.getRoomFacilityMapper().deleteByIdRoom(id_room);
	}



	@Override
	public Integer deleteByIdFacility(Integer id_facility) {		
		return this.getRoomFacilityMapper().deleteByIdFacility(id_facility);
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


	public void setStructureFacilityService(
			StructureFacilityService structureFacilityService) {
		this.structureFacilityService = structureFacilityService;
	}

	public RoomTypeFacilityService getRoomTypeFacilityService() {
		return roomTypeFacilityService;
	}


	public void setRoomTypeFacilityService(
			RoomTypeFacilityService roomTypeFacilityService) {
		this.roomTypeFacilityService = roomTypeFacilityService;
	}


	public RoomFacilityMapper getRoomFacilityMapper() {
		return roomFacilityMapper;
	}


	public void setRoomFacilityMapper(RoomFacilityMapper roomFacilityMapper) {
		this.roomFacilityMapper = roomFacilityMapper;
	}

		
}