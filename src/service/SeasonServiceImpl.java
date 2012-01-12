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
	
	public List<Season> findSeasonsByIdStructure(Integer structureId) {
		return this.getSeasonMapper().findSeasonsByStructureId(structureId);
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
	
	public List<Season> findSeasonsByYear(Integer structureId,Integer year) {
		Map params = new HashMap();
		params.put("structureId", structureId);
		params.put("year", year);
		
		return this.getSeasonMapper().findSeasonsByYear(params);
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
	
	@Override
	public List<Season> search(Integer id_structure, Integer offset, Integer rownum, String term) {
		Map map = null;
		SearchParser< Season> parser;
		
		parser = new SearchParser<Season>(Season.class);
		map = new HashMap();
		map.put("id_structure", id_structure );
		map.put("offset", offset );
		map.put("rownum", rownum );			
		map.putAll(parser.parse(term));
		return this.getSeasonMapper().search(map);
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
		Integer ret = 0;
		
		ret = this.getSeasonMapper().insertSeason(season);
//		if(ret > 0){
//			for(Period each: season.getPeriods()){
//				each.setId_season(season.getId());
//				this.getPeriodMapper().insertPeriod(each);
//			}
//		}		
		return ret;
	}
	
	public Integer updateSeason(Season season) {
		Integer ret = null;
		List<Integer> oldPeriodIds = null;
		Season oldSeason = null;
		
		oldPeriodIds = new ArrayList<Integer>();
		oldSeason = this.getSeasonMapper().findSeasonById(season.getId());
		for(Period each: oldSeason.getPeriods()){
			oldPeriodIds.add(each.getId());
		}
		
		ret = this.getSeasonMapper().updateSeason(season);
//		if(ret>0){
//			for(Period each: season.getPeriods()){
//				if(each.getId()==null){
//					//It's a new period, so an insert is needed
//					each.setId_season(season.getId());
//					this.getPeriodMapper().insertPeriod(each);
//				}else{
//					//It's an existing period, so an update is needed
//					
//					oldPeriodIds.remove(each.getId());
//					each.setId_season(season.getId());
//					this.getPeriodMapper().updatePeriod(each);
//				}				
//			}
//			//The oldPeriodIds collection now contains the ids of all periods that must be removed
//			for(Integer oldPeriodId: oldPeriodIds){
//				this.getPeriodMapper().deletePeriod(oldPeriodId);
//			}
//		}
		return ret;
	}

	public Integer deleteSeason(Integer seasonId) {
		Integer ret = 0;
		
		this.getPeriodMapper().deletePeriodsFromSeason(seasonId);
		this.getRoomPriceListService().deleteRoomPriceListsByIdSeason(seasonId);
		this.getExtraPriceListService().deleteExtraPriceListsByIdSeason(seasonId);
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