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

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import model.Guest;
import model.Housed;

@Transactional
public interface HousedService {
	public List<Housed> findAll();
	public Housed findHousedById(Integer id);
	public Housed findHousedByIdIncludingDeleted(Integer id);
	public Housed findHousedByIdBookingAndIdGuest(Integer id_booking, Integer id_guest);
	public List<Housed> findHousedByIdBooking(Integer id_booking);
	public List<Housed> findHousedByIdGuest(Integer id_guest);
	public Housed findMostRecentHousedByIdGuest(Integer id_guest);
	public Boolean checkOverlappingHoused(Date checkInDate, Date checkOutDate, Guest guest);
	public Integer insert(Housed housed);
	public Integer update(Housed housed);
	public Integer delete(Integer id);
	public Integer deleteHousedByIdBooking(Integer id_booking);
}