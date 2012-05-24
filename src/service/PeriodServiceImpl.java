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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.PeriodMapper;

import model.listini.Period;
import model.listini.Season;

@Service
public class PeriodServiceImpl implements PeriodService{
	@Autowired
	private PeriodMapper periodMapper = null;
	@Autowired
	private SeasonService seasonService = null;

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
	public List<Period> findPeriodsByIdStructure(Integer id_structure) {
		List<Season> seasons = new ArrayList<Season>();
		List<Period> periods = new ArrayList<Period>();
		
		seasons.addAll(this.getSeasonService().findSeasonsByIdStructure(id_structure));
		for (Season season : seasons) {
			periods.addAll(this.findPeriodsByIdSeason(season.getId()));
		}
		return periods;
	}
	
	@Override
	public List<Period> findPeriodsByIdSeason(Integer id_season) {
		List<Period> periods = null;
		
		periods =  this.getPeriodMapper().findPeriodsByIdSeason(id_season);
		return periods; 
	}
	
	@Override
	public Boolean includesDate(Period period, Date date){
		if((DateUtils.truncatedCompareTo(date,period.getStartDate(), Calendar.DAY_OF_MONTH) >= 0) &&
				(DateUtils.truncatedCompareTo(date, period.getEndDate(), Calendar.DAY_OF_MONTH) <= 0)	){
			return true;
		}		
		return false;
	}	
	
	
	@Override
	public Boolean checkYears(Period period) {
		Season season = this.getSeasonService().findSeasonById(period.getId_season());
		Boolean ret = false;
		
		if (period.getStartYear().equals(season.getYear())) {
			ret = true;
		}
		return ret;
	}
	
	@Override
	public Boolean checkOverlappingPeriods(Period period) {
		Integer currentSeasonId = period.getId_season();
		Season currentSeason = this.getSeasonService().findSeasonById(currentSeasonId);
		Integer structureId = currentSeason.getId_structure();
		List<Period> currentPeriods = new ArrayList<Period>();
		List<Period> periods = new ArrayList<Period>();
		Boolean ret = false;
		
		currentPeriods.addAll(this.findPeriodsByIdStructure(structureId));
		
		for (Period aPeriod : currentPeriods) {
			if (aPeriod.getId() != period.getId()) {
				periods.add(aPeriod);
			}
		}
			
		for(Period anyOtherPeriod : periods){
			if(anyOtherPeriod.getEndDate().compareTo(period.getStartDate()) >=0 && anyOtherPeriod.getStartDate().compareTo(period.getEndDate())<=0){
				ret = true;
			}
			if(anyOtherPeriod.getStartDate().compareTo(period.getEndDate()) <=0 && anyOtherPeriod.getEndDate().compareTo(period.getStartDate()) >=0){
				ret = true;
			}
			if (anyOtherPeriod.getEndDate().compareTo(period.getEndDate()) >=0 && anyOtherPeriod.getStartDate().compareTo(period.getStartDate()) <=0) {
				ret = true;
			}
		}	
		//								period												period
		//              startDate |---------------------------| endDate    		startDate |--------| endDate
		//       |------------------|    |---------|     |--------------------------------------|    periods
		//          anyOtherPeriod       anyOtherPeriod         		anyOtherPeriod
		return ret;
	}

	public PeriodMapper getPeriodMapper() {
		return periodMapper;
	}
	public void setPeriodMapper(PeriodMapper periodMapper) {
		this.periodMapper = periodMapper;
	}
	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

}