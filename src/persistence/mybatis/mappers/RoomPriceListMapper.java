package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;

public interface RoomPriceListMapper {

	public Integer insertRoomPriceList(RoomPriceList roomPriceList);
	public RoomPriceList findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(Map map);
	public List<RoomPriceList> findRoomPriceListsByIdSeason(Integer id_season);
	public List<RoomPriceList> findRoomPriceListsByIdRoomType(Integer id_roomType);
	public List<RoomPriceList> findRoomPriceListsByIdConvention(Integer id_convention);
	
	public RoomPriceList findRoomPriceListById(Integer id);
	public Integer deleteRoomPriceListById(Integer id);
}
