package service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.listini.Season;

@Transactional
public interface SeasonService {
	public List<Season> findSeasonsByStructureId(Integer structureId);
	public List<Season> findSeasonsByYear(Integer structureId,Integer year);
	public Season findSeasonById(Integer seasonId);
	public Season findSeasonByName(Integer structureId,String name);
	public Season findSeasonByDate(Integer structureId, Date date);
	public Integer insertSeason(Season season);
	public Integer updateSeason(Season season);
	public Integer deleteSeason(Integer seasonId);
	public Integer deletePeriod(Integer periodId);
	
}
