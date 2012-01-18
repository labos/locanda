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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import parser.SearchParser;
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
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	
	
	public List<Season> findAll(){
		return this.seasonMapper.findAll();
	}
	
	public List<Season> findSeasonsByIdStructure(Integer structureId) {
		return this.getSeasonMapper().findSeasonsByStructureId(structureId);
	}
	
	public List<Season> findSeasonsByYear(Integer structureId,Integer year) {
		Map params = new HashMap();
		params.put("structureId", structureId);
		params.put("year", year);
		
		return this.getSeasonMapper().findSeasonsByYear(params);
	}
	
	public Season findSeasonById(Integer seasonId) {
		return this.getSeasonMapper().findSeasonById(seasonId);
	}
	
	public Season findSeasonByName(Integer structureId,String name) {
		Map params = new HashMap();
		params.put("structureId", structureId);
		params.put("name", name);
		
		return this.getSeasonMapper().findSeasonByName(params);
	}
	
	public Season findSeasonByDate(Integer structureId, Date date){
		Season ret = null;
		
		for(Season each: this.getSeasonMapper().findSeasonsByStructureId(structureId)){
			if(each.includesDate(date)){
				return each;
			}
		}
		return ret;
	}
	
	public Boolean checkYears(Season season) {
		Integer year = null;
		List<Period> periods = new ArrayList<Period>();
		Boolean ret = false;
		Calendar startCalendar = new GregorianCalendar();
		Calendar endCalendar = new GregorianCalendar();
		
		year = season.getYear();
		periods = season.getPeriods();
		
		for (Period eachPeriod : periods) {
			startCalendar.setTime(eachPeriod.getStartDate());
			endCalendar.setTime(eachPeriod.getEndDate());
			if (startCalendar.get(Calendar.YEAR) == year && endCalendar.get(Calendar.YEAR) == year) {
				ret = true;
			}
		}
		return ret;
	}
	
	public Integer insertSeason(Season season) {		
		return this.getSeasonMapper().insertSeason(season);		
	}
	
	public Integer updateSeason(Season season) {
		return this.getSeasonMapper().updateSeason(season);
	}

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