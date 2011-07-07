package persistence.mybatis.mappers;

import java.util.List;

import model.listini.ExtraPriceListItem;


public interface ExtraPriceListItemMapper {
	
	public Integer insertExtraPriceListItem(ExtraPriceListItem extraPriceListItem);
	public Integer deleteExtraPriceListItemsByIdExtraPriceList(Integer id_extraPriceList);
	public Integer deleteExtraPriceListItemsByIdExtra(Integer id_extra);
	public List<ExtraPriceListItem> findExtraPriceListItemsByIdExtraPriceList(Integer id_extraPriceList);
	
	
	
	public Integer updateExtraPriceListItem(ExtraPriceListItem extraPriceListItem);
	
	

}
