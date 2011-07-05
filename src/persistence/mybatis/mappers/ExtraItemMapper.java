package persistence.mybatis.mappers;

import java.util.List;

import model.ExtraItem;




public interface ExtraItemMapper {
	public List<ExtraItem> findExtraItemsByIdBooking(Integer id_booking);
	public Integer insertExtraItem(ExtraItem extraItem);
	public Integer deleteExtraItemsByIdBooking(Integer id_booking);
	

}
