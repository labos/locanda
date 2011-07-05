package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Payment;

@Transactional
public interface PaymentService {
	public Integer insertPayment(Payment payment);
	public Integer deletePaymentsByIdBooking(Integer id_booking);
	public List<Payment> findPaymentsByIdBooking(Integer id_booking);

}
