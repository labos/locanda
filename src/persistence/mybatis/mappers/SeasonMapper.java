package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.listini.Period;
import model.listini.Season;

public interface SeasonMapper {
	public List<Season> findSeasonsByStructureId(Integer structureId);	
	public Season findSeasonById(Integer seasonId);
	public List<Season> findSeasonsByYear(Map params);
	public Season findSeasonByName(Map params);
	public Integer insertSeason(Season season);
	public Integer insertPeriod(Period period);
	public Integer updateSeason(Season season);
	public Integer updatePeriod(Period period);
	public Integer deleteSeason(Integer seasonId);
	public Integer deletePeriodsFromSeason(Integer seasonId);
	public Integer deletePeriod(Integer periodId);

}
