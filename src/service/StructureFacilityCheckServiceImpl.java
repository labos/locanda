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
import persistence.mybatis.mappers.StructureFacilityCheckMapper;

import model.Facility;
import model.Image;

@Service
public class StructureFacilityCheckServiceImpl implements StructureFacilityCheckService{
	@Autowired
	private StructureFacilityCheckMapper structureFacilityCheckMapper = null;
	@Autowired
	private ImageService imageService = null;
	
		
	@Override
	public Integer insert(Integer id_structure,Integer id_facility) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("id_facility", id_facility);
		return this.getStructureFacilityCheckMapper().insert(map);
	}
	
	
	
		
	@Override
	public List<Integer> findIdFacilityByIdStructure(Integer id_structure) {
		List<Integer> ret = null;
		
		ret = new ArrayList<Integer>();
		for(Map map: this.getStructureFacilityCheckMapper().findByIdStructure(id_structure)){
			ret.add((Integer)map.get("id_facility"));
		}
		
		return ret;
	}


	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	public StructureFacilityCheckMapper getStructureFacilityCheckMapper() {
		return structureFacilityCheckMapper;
	}

	public void setStructureFacilityCheckMapper(StructureFacilityCheckMapper structureFacilityCheckMapper) {
		this.structureFacilityCheckMapper = structureFacilityCheckMapper;
	}
		
}