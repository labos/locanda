package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.CreditCardMapper;
import model.CreditCard;

@Service
public class CreditCardServiceImpl implements CreditCardService {
	@Autowired
	private CreditCardMapper creditCardMapper = null;
	
	@Override
	public Integer insertCreditCard(CreditCard creditCard) {
		return this.getCreditCardMapper().insertCreditCard(creditCard);
	}

	@Override
	public Integer updateCreditCard(CreditCard creditCard) {
		return this.getCreditCardMapper().updateCreditCard(creditCard);
	}

	@Override
	public Integer deleteCreditCard(Integer id) {
		return this.getCreditCardMapper().deleteCreditCard(id);
	}

	@Override
	public Integer deleteCreditCardByIdBooking(Integer id_booking) {
		return this.getCreditCardMapper().deleteCreditCardByIdBooking(id_booking);
	}

	public CreditCardMapper getCreditCardMapper() {
		return creditCardMapper;
	}

	public void setCreditCardMapper(CreditCardMapper creditCardMapper) {
		this.creditCardMapper = creditCardMapper;
	}

	@Override
	public CreditCard findCreditCardById(Integer id) {
		return this.getCreditCardMapper().findCreditCardById(id);
	}

	@Override
	public CreditCard findCreditCardByIdBooking(Integer id_booking) {
		return this.getCreditCardMapper().findCreditCardByIdBooking(id_booking);
	}

}
