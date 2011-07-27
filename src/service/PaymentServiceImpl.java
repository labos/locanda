/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
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