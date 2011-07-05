package persistence.mybatis.mappers;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Adjustment;


public interface AdjustmentMapper {
	public Integer insertAdjustment(Adjustment adjustment);
	public Integer deleteAdjustmentsByIdBooking(Integer id_booking);
	public List<Adjustment> findAdjustmentsByIdBooking(Integer id_booking);	

}
