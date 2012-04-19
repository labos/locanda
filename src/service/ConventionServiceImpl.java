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

import model.listini.Convention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ConventionMapper;

@Service
public class ConventionServiceImpl implements ConventionService{
	@Autowired
	private ConventionMapper conventionMapper = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;

	
	@Override
	public Integer insertConvention(Convention convention) {		
		return this.getConventionMapper().insertConvention(convention);
	}

	@Override
	public Integer updateConvention(Convention convention) {
		return this.getConventionMapper().updateConvention(convention);
	}

	@Override
	public Integer deleteConvention(Integer id) {
		return this.getConventionMapper().deleteConvention(id);
	}

	@Override
	public List<Convention> findAll() {
		return this.getConventionMapper().findAll();
	}

	@Override
	public List<Convention> findConventionsByIdStructure(Integer id_structure) {
		return this.getConventionMapper().findConventionsByIdStructure(id_structure);
	}
	
	@Override
	public List<Convention> findConventionsByIdStructureWithoutDefault(Integer id_structure) {
		return this.getConventionMapper().findConventionsByIdStructureWithoutDefault(id_structure);
	}
	
	@Override
	public List<Convention> findConventionsByIdStructure(Integer id_structure, Integer offset, Integer rownum) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure );
		map.put("offset", offset );
		map.put("rownum", rownum );
		
		return this.getConventionMapper().findConventionsByIdStructureAndOffsetAndRownum(map);
	}
	
	@Override
	public Convention findConventionById(Integer id) {
		return this.getConventionMapper().findConventionById(id);
	}
	
	@Override
	public Convention findConventionByIdWithoutDefault(Integer id) {
		return this.getConventionMapper().findConventionByIdWithoutDefault(id);
	}
	

	public ConventionMapper getConventionMapper() {
		return conventionMapper;
	}
	public void setConventionMapper(ConventionMapper conventionMapper) {
		this.conventionMapper = conventionMapper;
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
	
}