package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.PaymentMapper;

import model.Payment;

@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	private PaymentMapper paymentMapper = null;

	@Override
	public Integer insertPayment(Payment payment) {
		
		return this.getPaymentMapper().insertPayment(payment);
	}

	@Override
	public Integer deletePaymentsByIdBooking(Integer id_booking) {
		
		return this.getPaymentMapper().deletePaymentsByIdBooking(id_booking);
	}

	@Override
	public List<Payment> findPaymentsByIdBooking(Integer id_booking) {
		
		return this.getPaymentMapper().findPaymentsByIdBooking(id_booking);
	}

	public PaymentMapper getPaymentMapper() {
		return paymentMapper;
	}

	public void setPaymentMapper(PaymentMapper paymentMapper) {
		this.paymentMapper = paymentMapper;
	}
	

}
