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
import java.util.List;

import model.questura.HousedExport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.HousedExportMapper;

@Service
public class HousedExportServiceImpl implements HousedExportService{
	@Autowired
	private HousedExportMapper housedExportMapper;
	@Autowired
	private HousedService housedService = null;
	
	@Override
	public Integer insert(HousedExport housedExport) {
		return this.getHousedExportMapper().insert(housedExport);
	}
	@Override
	public Integer update(HousedExport housedExport) {
		return this.getHousedExportMapper().update(housedExport);
	}
	@Override
	public Integer delete(Integer id) {
		return this.getHousedExportMapper().delete(id);
	}
	
	public HousedExportMapper getHousedExportMapper() {
		return housedExportMapper;
	}
	public void setHousedExportMapper(HousedExportMapper housedExportMapper) {
		this.housedExportMapper = housedExportMapper;
	}
	public HousedService getHousedService() {
		return housedService;
	}
	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}
	@Override
	public HousedExport findById(Integer id) {
		return this.getHousedExportMapper().findById(id);
	}
	@Override
	public HousedExport findByIdHoused(Integer id_housed) {
		return this.getHousedExportMapper().findByIdHoused(id_housed);
	}
	
	@Override
	public List<HousedExport> findByExported(Boolean exported) {
		List <HousedExport> ret = null;
		
		ret = this.getHousedExportMapper().findByExported(exported);
		for(HousedExport each: ret){
			each.setHoused(this.getHousedService().findHousedByIdIncludingDeleted(each.getId_housed()));
		}
		return ret;
	}
	@Override
	public List<HousedExport> findByIdStructureAndExported(Integer id_structure, Boolean exported) {
		List<HousedExport> ret = null;
		
		ret = new ArrayList<HousedExport>();
		for(HousedExport each: this.findByExported(exported)){
			if(each.getHoused() != null && each.getHoused().getGuest().getId_structure().equals(id_structure)){
				ret.add(each);
			}
		}		
		return ret;
	}
	@Override
	public List<HousedExport> findByExportedQuestura(Boolean exported) {
		List <HousedExport> ret = null;
		
		ret = this.getHousedExportMapper().findByExportedQuestura(exported);
		for(HousedExport each: ret){
			each.setHoused(this.getHousedService().findHousedByIdIncludingDeleted(each.getId_housed()));
		}
		return ret;
	}
	@Override
	public List<HousedExport> findByIdStructureAndExportedQuestura(Integer id_structure, Boolean exported) {
		List<HousedExport> ret = null;
		
		ret = new ArrayList<HousedExport>();
		for(HousedExport each: this.findByExportedQuestura(exported)){
			if(each.getHoused() != null && each.getHoused().getGuest().getId_structure().equals(id_structure)){
				ret.add(each);
			}
		}		
		return ret;
	}
	
}