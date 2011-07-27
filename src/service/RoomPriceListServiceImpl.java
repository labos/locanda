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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.RoomPriceListItemMapper;
import persistence.mybatis.mappers.RoomPriceListMapper;

@Service
public class RoomPriceListServiceImpl implements RoomPriceListService{
	@Autowired
	private RoomPriceListMapper roomPriceListMapper;
	@Autowired
	private RoomPriceListItemMapper roomPriceListItemMapper;
	
	@Override
	public Integer insertRoomPriceList(RoomPriceList roomPriceList) {
		Integer ret = 0;
		
		this.getRoomPriceListMapper().insertRoomPriceList(roomPriceList);
		for(RoomPriceListItem each:roomPriceList.getItems()) {
			each.setId_roomPriceList(roomPriceList.getId());
			this.getRoomPriceListItemMapper().insertRoomPriceListItem(each);
		}
		return ret;
	}

	@Override
	public RoomPriceList findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
			Integer id_structure, Integer id_season, Integer id_roomType, Integer id_convention) {
		
		RoomPriceList ret = null;
		Map map = null;
		List<RoomPriceListItem> roomPriceListItems;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("id_season", id_season);
		map.put("id_roomType", id_roomType);
		map.put("id_convention", id_convention);
		ret = this.getRoomPriceListMapper().findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(map);
		if (ret != null) {
			roomPriceListItems = this.getRoomPriceListItemMapper().findRoomPriceListItemsByIdRoomPriceList(ret.getId());
			ret.setItems(roomPriceListItems);
		}
		return ret;
	}

	@Override
	public RoomPriceList findRoomPriceListById(Integer id) {
		RoomPriceList roomPriceList = null;
		List<RoomPriceListItem> roomPriceListItems = null;
		
		roomPriceList = this.getRoomPriceListMapper().findRoomPriceListById(id);
		roomPriceListItems = this.getRoomPriceListItemMapper().findRoomPriceListItemsByIdRoomPriceList(roomPriceList.getId());
		roomPriceList.setItems(roomPriceListItems);
		return roomPriceList;
	}	

	@Override
	public Integer deleteRoomPriceListById(Integer id) {
		Integer ret = 0;
		
		this.getRoomPriceListItemMapper().deleteRoomPriceListItemsByIdRoomPriceList(id);
		ret = this.getRoomPriceListMapper().deleteRoomPriceListById(id);
		return ret;
	}	

	@Override
	public Integer deleteRoomPriceListsByIdSeason(Integer id_season) {
		Integer ret = 0;
		List<RoomPriceList> roomPriceLists = null;
		
		roomPriceLists = this.getRoomPriceListMapper().findRoomPriceListsByIdSeason(id_season);
		for(RoomPriceList each: roomPriceLists){
			this.deleteRoomPriceListById(each.getId());
		}
		return ret;
	}

	@Override
	public Integer deleteRoomPriceListsByIdRoomType(Integer id_roomType) {
		Integer ret = 0;
		List<RoomPriceList> roomPriceLists = null;
		
		roomPriceLists = this.getRoomPriceListMapper().findRoomPriceListsByIdRoomType(id_roomType);
		for(RoomPriceList each: roomPriceLists){
			this.deleteRoomPriceListById(each.getId());
		}
		return ret;
	}

	@Override
	public Integer deleteRoomPriceListsByIdConvention(Integer id_convention) {
		Integer ret = 0;
		List<RoomPriceList> roomPriceLists = null;
		
		roomPriceLists = this.getRoomPriceListMapper().findRoomPriceListsByIdConvention(id_convention);
		for(RoomPriceList each: roomPriceLists){
			this.deleteRoomPriceListById(each.getId());
		}
		return ret;
	}

	@Override
	public Integer updateRoomPriceListItem(RoomPriceListItem roomPriceListItem) {
		return this.getRoomPriceListItemMapper().updateRoomPriceListItem(roomPriceListItem);
	}

	public RoomPriceListMapper getRoomPriceListMapper() {
		return roomPriceListMapper;
	}
	public void setRoomPriceListMapper(RoomPriceListMapper roomPriceListMapper) {
		this.roomPriceListMapper = roomPriceListMapper;
	}

	public RoomPriceListItemMapper getRoomPriceListItemMapper() {
		return roomPriceListItemMapper;
	}
	public void setRoomPriceListItemMapper(
			RoomPriceListItemMapper roomPriceListItemMapper) {
		this.roomPriceListItemMapper = roomPriceListItemMapper;
	}

}