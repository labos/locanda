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

import persistence.mybatis.mappers.StructureImageMapper;

@Service
public class StructureImageServiceImpl implements StructureImageService{	
	@Autowired
	private StructureImageMapper structureImageMapper = null;
	
	@Override
	public Integer insert(Integer id_structure, Integer id_image) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_structure",id_structure );
		map.put("id_image",id_image);
		return this.getStructureImageMapper().insert(map);
	}	
		
	@Override
	public Integer findIdByIdStructureAndIdImage(Integer id_structure,Integer id_image) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("id_image", id_image);		
		return this.getStructureImageMapper().findIdByIdStructureAndIdImage(map);
	}

	@Override
	public List<Integer> findIdImageByIdStructure(Integer id_structure, Integer offset, Integer rownum) {
		List<Integer> ret = null;
		Map map = null;
		
		ret = new ArrayList<Integer>();
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("offset", offset);
		map.put("rownum", rownum);
		for(Map each: this.getStructureImageMapper().findByIdStructure(map)){
			ret.add((Integer)each.get("id_image"));
		}
		return ret;
	}

	@Override
	public Integer delete(Integer id) {
		
		return this.getStructureImageMapper().delete(id);
	}

	@Override
	public Integer deleteByIdImage(Integer id_image) {
		
		return this.getStructureImageMapper().deleteByIdImage(id_image);
	}

	@Override
	public Integer deleteByIdStructure(Integer id_structure) {
		
		return this.getStructureImageMapper().deleteByIdStructure(id_structure);
	}

	public StructureImageMapper getStructureImageMapper() {
		return structureImageMapper;
	}

	public void setStructureImageMapper(StructureImageMapper structureImageMapper) {
		this.structureImageMapper = structureImageMapper;
	}

	

		
}
