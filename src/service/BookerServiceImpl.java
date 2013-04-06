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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.BookerMapper;
import persistence.mybatis.mappers.GuestMapper;

import model.Booker;
import model.Guest;

@Service
public class BookerServiceImpl implements BookerService{
	@Autowired
	private GuestService guestService = null;
	@Autowired 
	private BookerMapper bookerMapper = null;

	
	
	@Override
	public Integer insert(Integer id_guest, Integer id_booking) {
		Integer ret = 0;
		Map map = null;
		
		map = new HashMap();
		map.put("id_guest",id_guest);
		map.put("id_booking", id_booking);		
		ret = this.getBookerMapper().insert(map);
		
		return ret;
	}


	@Override
	public Integer update(Booker booker) {
		
		return this.getBookerMapper().update(booker);
	}
	

	@Override
	public Integer deleteBookerByIdBooking(Integer id_booking) {
		
		return this.getBookerMapper().deleteBookerByIdBooking(id_booking);
	}

	
	@Override
	public Booker findBookerByIdBooking(Integer id_booking) {
		Booker ret = null;
		Guest guest = null;
		
		ret = this.getBookerMapper().findBookerByIdBooking(id_booking);
		if (ret != null) {
			guest = this.getGuestService().findGuestById(ret.getId_guest());
			ret.setGuest(guest);
		}
		return ret;
	}

	
	public BookerMapper getBookerMapper() {
		return bookerMapper;
	}
	public void setBookerMapper(BookerMapper bookerMapper) {
		this.bookerMapper = bookerMapper;
	}


	public GuestService getGuestService() {
		return guestService;
	}


	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}
	
}