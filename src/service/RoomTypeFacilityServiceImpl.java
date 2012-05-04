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
import persistence.mybatis.mappers.RoomTypeFacilityMapper;
import persistence.mybatis.mappers.StructureFacilityMapper;

import model.Facility;
import model.Image;

@Service
public class RoomTypeFacilityServiceImpl implements RoomTypeFacilityService{
	
	@Autowired
	private RoomTypeFacilityMapper roomTypeFacilityMapper = null;
			
		
	@Override
	public Integer insert(Integer id_roomType,Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_roomType", id_roomType);
		map.put("id_facility", id_facility);
		
		return this.getRoomTypeFacilityMapper().insert(map);
		
	}
	
	
	
		
	@Override
	public List<Integer> findIdFacilityByIdRoomType(Integer id_roomType,Integer offset, Integer rownum) {
		List<Integer> ret = null;
		Map map = null;
		
		ret = new ArrayList<Integer>();
		map = new HashMap();
		map.put("id_roomType",id_roomType );
		map.put("offset",offset );
		map.put("rownum",rownum );
		
		for(Map each: this.getRoomTypeFacilityMapper().findByIdRoomType(map)){
			ret.add((Integer)each.get("id_facility"));
		}
		return ret;
	}
	

	@Override
	public Integer findIdByIdRoomTypeAndIdFacility(Integer id_roomType,	Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_roomType", id_roomType);
		map.put("id_facility", id_facility);
		return this.getRoomTypeFacilityMapper().findIdByIdRoomTypeAndIdFacility(map);
	}




	@Override
	public Integer delete(Integer id) {		
		return this.getRoomTypeFacilityMapper().delete(id);
	}

	@Override
	public Integer deleteByIdRoomType(Integer id_roomType) {
		
		return this.getRoomTypeFacilityMapper().deleteByIdRoomType(id_roomType);
	}




	@Override
	public Integer deleteByIdFacility(Integer id_facility) {
		
		return this.getRoomTypeFacilityMapper().deleteByIdFacility(id_facility);
	}

	public RoomTypeFacilityMapper getRoomTypeFacilityMapper() {
		return roomTypeFacilityMapper;
	}


	public void setRoomTypeFacilityMapper(
			RoomTypeFacilityMapper roomTypeFacilityMapper) {
		this.roomTypeFacilityMapper = roomTypeFacilityMapper;
	}
	
		
}