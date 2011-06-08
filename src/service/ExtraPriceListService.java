package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.Season;

@Transactional
public interface ExtraPriceListService {
	public Integer insertExtraPriceList(Structure structure,ExtraPriceList aPriceList);
	public Integer deleteExtraPriceList(Structure structure,ExtraPriceList aPriceList);
	public List<ExtraPriceList> findExtraPriceListsByIdStructure(Structure structure);
	public ExtraPriceList findExtraPriceListById(Structure structure,Integer id);
	public ExtraPriceList findExtraPriceListByStructureAndSeasonAndRoomTypeAndConvention(Structure structure,Season season, RoomType roomType, Convention convention);

}
