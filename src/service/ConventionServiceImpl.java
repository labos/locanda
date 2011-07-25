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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ConventionMapper;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

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
		this.getRoomPriceListService().deleteRoomPriceListsByIdConvention(id);
		this.getExtraPriceListService().deleteExtraPriceListsByIdConvention(id);
		return this.getConventionMapper().deleteConvention(id);
	}


	@Override
	public List<Convention> findConventionsByIdStructure(Integer id_structure) {
		return this.getConventionMapper().findConventionsByIdStructure(id_structure);
	}


	@Override
	public Convention findConventionById(Integer id) {
		return this.getConventionMapper().findConventionById(id);
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
