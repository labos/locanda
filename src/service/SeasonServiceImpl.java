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

import persistence.mybatis.mappers.SeasonMapper;

import model.listini.Period;
import model.listini.Season;

@Service
public class SeasonServiceImpl implements SeasonService{
	@Autowired
	private SeasonMapper seasonMapper = null;
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
		if(ret > 0){
			for(Period each: season.getPeriods()){
				each.setId_season(season.getId());
				this.getSeasonMapper().insertPeriod(each);
			}
		}		
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
		if(ret>0){
			for(Period each: season.getPeriods()){
				if(each.getId()==null){
					//Si tratta di un nuovo period quindi devo fare un insert
					each.setId_season(season.getId());
					this.getSeasonMapper().insertPeriod(each);
				}else{
					//Si tratta di un period esistente quindi devo fare un update
					
					oldPeriodIds.remove(each.getId());
					each.setId_season(season.getId());
					this.getSeasonMapper().updatePeriod(each);
				}				
			}
			//La collezione oldPeriodIds in questo punto contiene gli Id dei periodi che
			//devono essere rimossi
			for(Integer oldPeriodId: oldPeriodIds){
				this.getSeasonMapper().deletePeriod(oldPeriodId);
			}
		}
		return ret;
	}

	public Integer deleteSeason(Integer seasonId) {
		Integer ret = 0;
		
		this.getSeasonMapper().deletePeriodsFromSeason(seasonId);
		this.getRoomPriceListService().deleteRoomPriceListsByIdSeason(seasonId);
		this.getExtraPriceListService().deleteExtraPriceListsByIdSeason(seasonId);
		ret = this.getSeasonMapper().deleteSeason(seasonId);
		return ret;
	}

	public Integer deletePeriod(Integer periodId) {
		Integer ret = 0;
		
		ret = this.getSeasonMapper().deletePeriod(periodId);
		return ret;
	}

	public SeasonMapper getSeasonMapper() {
		return seasonMapper;
	}

	public void setSeasonMapper(SeasonMapper seasonMapper) {
		this.seasonMapper = seasonMapper;
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