package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.listini.ExtraPriceList;


public interface ExtraPriceListMapper {

	public Integer insertExtraPriceList(ExtraPriceList extraPriceList);
	public ExtraPriceList findExtraPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(Map map);
	public List<ExtraPriceList> findExtraPriceListsByIdStructure(Integer id_structure);
	public List<ExtraPriceList> findExtraPriceListsByIdSeason(Integer id_season);
	public List<ExtraPriceList> findExtraPriceListsByIdRoomType(Integer id_roomType);
	public List<ExtraPriceList> findExtraPriceListsByIdConvention(Integer id_convention);
	
	public ExtraPriceList findExtraPriceListById(Integer id);
	public Integer deleteExtraPriceListById(Integer id);
}
