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

import model.Booking;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookingService {
	public Double calculateRoomSubtotalForBooking(Integer id_structure, Booking booking);
	
	public Booking findBookingById(Integer id);
	public List<Integer> findBookingIdsByIdStructure(Integer id_structure);
	public List<Booking> findBookingsByIdStructure(Integer id_structure);
	public List<Integer> findBookingIdsByIdBooker(Integer id_booker);
	public List<Booking> findBookingsByIdBooker(Integer id_booker);
	public List<Booking> findBookingIdsByIdHousedGroupLeader(Integer id_housed);
	
	public Integer countBookingsByIdConvention(Integer id_convention);
	public Integer countBookingsByIdRoom(Integer id_room);
	public Integer countBookingsByIdExtra(Integer id_extra);
	public Integer countBookingsByIdGuest(Integer id_guest);
	public Integer countBookingsByIdSeason(Integer id_season);
	
	public Integer saveOnlineBooking(Booking booking);
	public Integer saveUpdateBooking(Booking booking); 
	public Integer updateBooking(Booking booking);
	public Integer deleteBooking(Integer id);

}