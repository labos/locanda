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
