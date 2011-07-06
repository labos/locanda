package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.RoomPriceListItemMapper;
import persistence.mybatis.mappers.RoomPriceListMapper;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

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

	/*
	@Override
	public List<RoomPriceList> findRoomPriceListsByIdSeason(Integer id_season) {
		List<RoomPriceList> roomPriceLists = null;
		List<RoomPriceListItem> roomPriceListItems = null;
		
		roomPriceLists = this.getRoomPriceListMapper().findRoomPriceListsByIdSeason(id_season);
		for (RoomPriceList each : roomPriceLists) {
			roomPriceListItems = this.getRoomPriceListItemMapper().findRoomPriceListItemsByIdRoomPriceList(each.getId());
			each.setItems(roomPriceListItems);
		}
		return roomPriceLists;
	}*/

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
