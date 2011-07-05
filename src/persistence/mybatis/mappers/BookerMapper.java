package persistence.mybatis.mappers;

import java.util.Map;

public interface BookerMapper {
	public Integer insertBooker(Map map);
	public Integer deleteBookerByIdBooking(Integer id_booking);
	public Integer findIdBookerByIdBooking(Integer id_booking);
	

}
