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
package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.Housed;

public interface HousedMapper {
	public List<Housed> findAll();
	public List<Housed> findHousedByIdBooking(Integer id_booking);
	public List<Housed> findHousedByIdGuest(Integer id_guest);
	public Housed findHousedById(Integer id);
	public Housed findHousedByIdIncludingDeleted(Integer id);
	public Housed findHousedByIdBookingAndIdGuest(Map map);
	public Integer insert(Housed housed);
	public Integer update(Housed housed);
	public Integer delete(Integer id);
	public Integer deleteHousedByIdBooking(Integer id_booking);
}