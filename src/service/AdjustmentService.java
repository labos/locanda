package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Adjustment;
@Transactional
public interface AdjustmentService {
	public Integer insertAdjustment(Adjustment adjustment);
	public Integer deleteAdjustmentsByIdBooking(Integer id_booking);
	public List<Adjustment> findAdjustmentsByIdBooking(Integer id_booking);	
}
