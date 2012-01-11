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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.PeriodMapper;

import model.listini.Period;

@Service
public class PeriodServiceImpl implements PeriodService{
	@Autowired
	private PeriodMapper periodMapper = null;

	@Override
	public Integer insertPeriod(Period period) {
		return this.getPeriodMapper().insertPeriod(period);
	}
	
	@Override
	public Integer updatePeriod(Period period) {
		return this.getPeriodMapper().updatePeriod(period);
	}

	@Override
	public Integer deletePeriod(Integer id_period) {
		return this.getPeriodMapper().deletePeriod(id_period);
	}
	
	@Override
	public Integer deletePeriodsByIdSeason(Integer id_season) {
		return this.getPeriodMapper().deletePeriodsByIdSeason(id_season);
	}

	public Period findPeriodById(Integer periodId) {
		return this.getPeriodMapper().findPeriodById(periodId);
	}
	
	@Override
	public List<Period> findPeriodsByIdSeason(Integer id_season) {
		List<Period> periods = null;
		
		periods =  this.getPeriodMapper().findPeriodsByIdSeason(id_season);
		return periods; 
	}

	public PeriodMapper getPeriodMapper() {
		return periodMapper;
	}
	public void setPeriodMapper(PeriodMapper periodMapper) {
		this.periodMapper = periodMapper;
	}

}