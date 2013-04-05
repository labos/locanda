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

import model.questura.HousedExport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.HousedExportMapper;

@Service
public class HousedExportServiceImpl implements HousedExportService{
	@Autowired
	private HousedExportMapper housedExportMapper;
	
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
	
}