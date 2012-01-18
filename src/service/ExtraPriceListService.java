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

import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExtraPriceListService {
	public Integer insertExtraPriceList(ExtraPriceList extraPriceList);
	public Integer insertExtraPriceListItem(ExtraPriceListItem extraPriceListItem);
	
	public ExtraPriceList findExtraPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(Integer id_structure, Integer id_season, Integer id_roomType, Integer id_convention);
	public ExtraPriceList findExtraPriceListById(Integer id);
	public List<ExtraPriceList> findExtraPriceListsByIdStructure(Integer id_structure);
	
	public Integer updateExtraPriceListItem(ExtraPriceListItem extraPriceListItem);
	
	public Integer deleteExtraPriceListById(Integer id);
	public Integer deleteExtraPriceListsByIdSeason(Integer id_season);
	public Integer deleteExtraPriceListsByIdRoomType(Integer id_roomType); 
	public Integer deleteExtraPriceListsByIdConvention(Integer id_convention);
	public Integer deleteExtraPriceListItemsByIdExtra(Integer id_extra);
}