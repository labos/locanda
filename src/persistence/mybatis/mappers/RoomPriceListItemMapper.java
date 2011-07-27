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

import model.listini.RoomPriceListItem;

public interface RoomPriceListItemMapper {
	public Integer insertRoomPriceListItem(RoomPriceListItem roomPriceListItem);
	public Integer deleteRoomPriceListItemsByIdRoomPriceList(Integer id_roomPriceList);
	public List<RoomPriceListItem> findRoomPriceListItemsByIdRoomPriceList(Integer id_roomPriceList);
	public Integer updateRoomPriceListItem(RoomPriceListItem roomPriceListItem);
}