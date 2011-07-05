package persistence.mybatis.mappers;

import java.util.List;


import model.Payment;

public interface PaymentMapper {
	public Integer insertPayment(Payment payment);
	public Integer deletePaymentsByIdBooking(Integer id_booking);
	public List<Payment> findPaymentsByIdBooking(Integer id_booking);	
}
