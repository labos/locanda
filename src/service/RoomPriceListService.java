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

import org.springframework.transaction.annotation.Transactional;

import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;

@Transactional
public interface RoomPriceListService {
	
	public Integer insertRoomPriceList(RoomPriceList roomPriceList);
	public RoomPriceList findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(Integer id_structure, Integer id_season, Integer id_roomType, Integer id_convention);
	public RoomPriceList findRoomPriceListById(Integer id);
	public Integer updateRoomPriceListItem(RoomPriceListItem roomPriceListItem);
	
	public Integer deleteRoomPriceListById(Integer id);
	public Integer deleteRoomPriceListsByIdSeason(Integer id_season);
	public Integer deleteRoomPriceListsByIdRoomType(Integer id_roomType);
	public Integer deleteRoomPriceListsByIdConvention(Integer id_convention);
}