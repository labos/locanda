package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.AdjustmentMapper;

import model.Adjustment;

@Service
public class AdjustmentServiceImpl implements AdjustmentService{
	@Autowired
	private AdjustmentMapper adjustmentMapper = null;

	@Override
	public Integer insertAdjustment(Adjustment adjustment) {
		
		return this.getAdjustmentMapper().insertAdjustment(adjustment);
	}

	@Override
	public Integer deleteAdjustmentsByIdBooking(Integer id_booking) {
		
		return this.getAdjustmentMapper().deleteAdjustmentsByIdBooking(id_booking);
	}

	@Override
	public List<Adjustment> findAdjustmentsByIdBooking(Integer id_booking) {
		List<Adjustment> adjustments = null;
		
		adjustments =  this.getAdjustmentMapper().findAdjustmentsByIdBooking(id_booking);
		return adjustments; 
	}

	public AdjustmentMapper getAdjustmentMapper() {
		return adjustmentMapper;
	}

	public void setAdjustmentMapper(AdjustmentMapper adjustmentMapper) {
		this.adjustmentMapper = adjustmentMapper;
	}
	
	

}
