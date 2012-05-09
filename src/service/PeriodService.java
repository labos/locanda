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

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.listini.Period;

@Transactional
public interface PeriodService {
	public Integer insertPeriod(Period period);
	public Integer updatePeriod(Period period);
	public Integer deletePeriod(Integer id_period);
	public Integer deletePeriodsByIdSeason(Integer id_season);
	public Period findPeriodById(Integer id_period);
	public List<Period> findPeriodsByIdStructure(Integer id_structure);
	public List<Period> findPeriodsByIdSeason(Integer id_season);
	public Boolean includesDate(Period period, Date date);
	public Boolean checkYears(Period period);
	public Boolean checkOverlappingPeriods(Period period);
}