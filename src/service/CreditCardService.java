package service;

import model.CreditCard;
import model.listini.Convention;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CreditCardService {
	public Integer insertCreditCard(CreditCard creditCard);
	public Integer updateCreditCard(CreditCard creditCard);
	public Integer deleteCreditCard(Integer id);
	public Integer deleteCreditCardByIdBooking(Integer id);
	public CreditCard findCreditCardById(Integer id);
	public CreditCard findCreditCardByIdBooking(Integer id_booking);
}

