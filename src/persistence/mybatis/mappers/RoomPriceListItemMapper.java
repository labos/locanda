package persistence.mybatis.mappers;

import java.util.List;

import model.listini.RoomPriceListItem;

public interface RoomPriceListItemMapper {
	
	public Integer insertRoomPriceListItem(RoomPriceListItem roomPriceListItem);
	public Integer deleteRoomPriceListItemsByIdRoomPriceList(Integer id_roomPriceList);
	
	public List<RoomPriceListItem> findRoomPriceListItemsByIdRoomPriceList(Integer id_roomPriceList);
	
	
	
	public Integer updateRoomPriceListItem(RoomPriceListItem roomPriceListItem);
	
	

}
