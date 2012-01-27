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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityMapper;

import model.Facility;

@Service
public class FacilityServiceImpl implements FacilityService{
	@Autowired
	private FacilityMapper facilityMapper = null;				
	
	@Override
	public Integer insertFacility(Facility facility) {		
		return this.getFacilityMapper().insertFacility(facility);		
	}	

	@Override
	public Integer insertStructureFacility(Integer id_structure,Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("id_facility", id_facility);
		return this.getFacilityMapper().insertStructureFacility(map);
	}
	
	@Override
	public Integer insertRoomTypeFacility(Integer id_roomType,Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_roomType", id_roomType);
		map.put("id_facility", id_facility);
		
		return this.getFacilityMapper().insertRoomTypeFacility(map);
	}
	
		
	@Override
	public Integer insertRoomFacility(Integer id_room,Integer id_facility ) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_room", id_room);
		map.put("id_facility", id_facility);
		return this.getFacilityMapper().insertRoomFacility(map);
	}
	
	@Override
	public Facility findFacilityById(Integer id) {		
		return this.getFacilityMapper().findFacilityById(id);
	}
	
	
	@Override
	public List<Facility> findFacilitiesByIds(List<Integer> ids) {
		List<Facility> ret = null;
		
		ret = new ArrayList<Facility>();
		for(Integer each: ids){
			ret.add(this.findFacilityById(each));
		}
		return ret;
	}

	@Override
	public List<Facility> findStructureFacilitiesByIdStructure(	Integer id_structure) {		
		List<Integer> ids = null;
		
		ids = this.getFacilityMapper().findRoomFacilityIdsByIdStructure(id_structure);
		return this.findFacilitiesByIds(ids);
		
	}
	
	@Override
	public List<Facility> findRoomAndRoomTypeFacilitiesByIdStructure(Integer id_structure) {
		Set<Integer> ids = null;
		
		ids = new HashSet<Integer>();
		ids.addAll(this.getFacilityMapper().findRoomFacilityIdsByIdStructure(id_structure));
		ids.addAll(this.getFacilityMapper().findRoomTypeFacilityIdsByIdStructure(id_structure));
		return this.findFacilitiesByIds(new ArrayList<Integer>(ids));
	}

	@Override
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room) {	
		List<Integer> ids = null;
		
		ids = this.getFacilityMapper().findRoomFacilityIdsByIdRoom(id_room);
		return this.findFacilitiesByIds(ids);
	}

	@Override
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType) {
		List<Integer> ids = null;
		
		ids = this.getFacilityMapper().findRoomTypeFacilityIdsByIdRoomType(id_roomType);
		return this.findFacilitiesByIds(ids);
	}

	

	@Override
	public Integer deleteStructureFacility(Integer id_structure,Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("id_facility", id_facility);
		return this.getFacilityMapper().deleteStructureFacility(map);
	}
	
	

	@Override
	public Integer deleteStructureFacilities(Integer id_structure) {
		List<Integer> ids = null;
		Integer count = 0;		
		
		ids = this.getFacilityMapper().findStructureFacilityIdsByIdStructure(id_structure);		
		for (Integer each: ids){			
			count = count + this.deleteStructureFacility(id_structure,each);
		}
		return count;
	}

	@Override
	public Integer deleteRoomTypeFacility(Integer id_roomType,	Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_roomType", id_roomType);
		map.put("id_facility", id_facility);
		return this.getFacilityMapper().deleteRoomTypeFacility(map);
	}
	
	@Override
	public Integer deleteRoomTypeFacilities(Integer id_roomType) {		
		List<Integer> ids = null;
		Integer count = 0;		
		
		ids = this.getFacilityMapper().findRoomTypeFacilityIdsByIdRoomType(id_roomType);		
		for (Integer each: ids){			
			count = count + this.deleteRoomTypeFacility(id_roomType,each);
		}
		return count;
	}

	@Override
	public Integer deleteRoomFacility(Integer id_room, Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_room", id_room);
		map.put("id_facility", id_facility);
		return this.getFacilityMapper().deleteRoomFacility(map);
	}

	@Override
	public Integer deleteRoomFacilities(Integer id_room) {
		List<Integer> ids = null;
		Integer count = 0;		
		
		ids = this.getFacilityMapper().findRoomFacilityIdsByIdRoom(id_room);
		for (Integer each: ids){
			count = count + this.deleteRoomFacility(id_room,each);
		}
		return count;
	}
	
	

	
	public FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}
	public void setFacilityMapper(
			FacilityMapper structureFacilityMapper) {
		this.facilityMapper = structureFacilityMapper;
	}
	
}