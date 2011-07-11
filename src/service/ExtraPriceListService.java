package service;

import java.util.List;


import org.springframework.transaction.annotation.Transactional;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.Season;

@Transactional
public interface ExtraPriceListService {
	public Integer insertExtraPriceList(ExtraPriceList extraPriceList);
	public Integer insertExtraPriceListItem(ExtraPriceListItem extraPriceListItem);
	public ExtraPriceList findExtraPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(Integer id_structure, Integer id_season, Integer id_roomType, Integer id_convention);
	
	public ExtraPriceList findExtraPriceListById(Integer id);
	public List<ExtraPriceList> findExtraPriceListsByIdStructure(Integer id_structure);
	public Integer deleteExtraPriceListById(Integer id);
	public Integer updateExtraPriceListItem(ExtraPriceListItem extraPriceListItem);
	
	public Integer deleteExtraPriceListsByIdSeason(Integer id_season);
	public Integer deleteExtraPriceListsByIdRoomType(Integer id_roomType); 
	public Integer deleteExtraPriceListsByIdConvention(Integer id_convention); 
	
}
