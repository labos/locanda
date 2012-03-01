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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityImageMapper;
import persistence.mybatis.mappers.FileMapper;
import persistence.mybatis.mappers.ImageFileMapper;
import persistence.mybatis.mappers.ImageMapper;
import persistence.mybatis.mappers.RoomImageMapper;
import persistence.mybatis.mappers.RoomTypeImageMapper;


import model.File;
import model.Image;

@Service
public class FacilityImageServiceImpl implements FacilityImageService{
	@Autowired
	private FacilityImageMapper facilityImageMapper = null;	
	
	@Override
	public Integer insert(Integer id_facility,Integer id_image) {
		Map map = null;			
		
		map = new HashMap();
		map.put("id_facility",id_facility );
		map.put("id_image",id_image);
		return this.getFacilityImageMapper().insert(map);
	}


	@Override
	public Integer findIdImageByIdFacility(Integer id_facility) {
		return (Integer)this.getFacilityImageMapper().findByIdFacility(id_facility).get("id_image");
	}


	@Override
	public Integer delete(Integer id) {
		
		return this.getFacilityImageMapper().delete(id);
	}
	

	@Override
	public Integer deleteByIdImage(Integer id_image) {
		
		return this.getFacilityImageMapper().deleteByIdImage(id_image);
	}

	@Override
	public Integer deleteByIdFacility(Integer id_facility) {
		
		return this.getFacilityImageMapper().deleteByIdFacility(id_facility);
	}



	public FacilityImageMapper getFacilityImageMapper() {
		return facilityImageMapper;
	}

	public void setFacilityImageMapper(FacilityImageMapper facilityImageMapper) {
		this.facilityImageMapper = facilityImageMapper;
	}

		
}