/*******************************************************************************
 *
 *  Copyright 2013 - Sardegna Ricerche, Distretto ICT, Pula, Italy
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
package persistence.mybatis.mappers;

import model.CreditCard;

public interface CreditCardMapper {
	public CreditCard findCreditCardById(Integer id);
	public CreditCard findCreditCardByIdBooking(Integer id_booking);
	public Integer updateCreditCard(CreditCard creditCard);
	public Integer insertCreditCard(CreditCard creditCard);
	public Integer deleteCreditCard(Integer id);
	public Integer deleteCreditCardByIdBooking(Integer id_booking);
}