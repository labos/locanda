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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.PeriodMapper;
import persistence.mybatis.mappers.SeasonMapper;

import model.listini.Period;
import model.listini.Season;

@Service
public class SeasonServiceImpl implements SeasonService{
	
	@Autowired
	private SeasonMapper seasonMapper = null;
	@Autowired
	private PeriodMapper periodMapper = null;
	@Autowired
	private PeriodService periodService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	
	
	@Override
	public List<Season> findAll(){
		return this.seasonMapper.findAll();
	}
	
	@Override
	public List<Season> findSeasonsByIdStructure(Integer id_structure) {
		return this.getSeasonMapper().findSeasonsByStructureId(id_structure);
	}
	
	@Override
	public List<Season> findSeasonsByYear(Integer id_structure,Integer year) {
		Map params = new HashMap();
		params.put("id_structure", id_structure);
		params.put("year", year);
		
		return this.getSeasonMapper().findSeasonsByYear(params);
	}
	
	@Override
	public Season findSeasonById(Integer seasonId) {
		return this.getSeasonMapper().findSeasonById(seasonId);
	}
	
	@Override
	public Season findSeasonByName(Integer id_structure,String name) {
		Map params = new HashMap();
		params.put("id_structure", id_structure);
		params.put("name", name);
		
		return this.getSeasonMapper().findSeasonByName(params);
	}
	
	@Override
	public Season findSeasonByDate(Integer id_structure, Date date){
		Season ret = null;
		
		for(Season each: this.getSeasonMapper().findSeasonsByStructureId(id_structure)){
			if(this.includesDate(each, date)){
				return each;
			}
		}
		return ret;
	}
	
	@Override
	public Boolean checkYears(Season season) {
		List<Period> periods = new ArrayList<Period>();
		Boolean ret = true;
		
		periods.addAll(this.getPeriodService().findPeriodsByIdSeason(season.getId()));
		
		for (Period eachPeriod : periods) {
			if (!(eachPeriod.getStartYear().equals(season.getYear()))) {
				ret = false;
			}
		}
		return ret;
	}
	
	@Override
	public Boolean includesDate(Season season, Date date){
		for(Period each: this.getPeriodMapper().findPeriodsByIdSeason(season.getId())){
			if(this.periodService.includesDate(each, date)){
				return true;
			}
		}		
		return false;
	}
	
	@Override
	public Integer insertSeason(Season season) {		
		return this.getSeasonMapper().insertSeason(season);		
	}
	
	@Override
	public Integer updateSeason(Season season) {
		return this.getSeasonMapper().updateSeason(season);
	}
	
	@Override
	public Integer deleteSeason(Integer seasonId) {
		Integer ret = 0;
		
		this.getPeriodMapper().deletePeriodsFromSeason(seasonId);
		ret = this.getSeasonMapper().deleteSeason(seasonId);
		return ret;
	}

	public SeasonMapper getSeasonMapper() {
		return seasonMapper;
	}
	public void setSeasonMapper(SeasonMapper seasonMapper) {
		this.seasonMapper = seasonMapper;
	}
	public PeriodMapper getPeriodMapper() {
		return periodMapper;
	}
	public void setPeriodMapper(PeriodMapper periodMapper) {
		this.periodMapper = periodMapper;
	}
	public PeriodService getPeriodService() {
		return periodService;
	}
	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
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