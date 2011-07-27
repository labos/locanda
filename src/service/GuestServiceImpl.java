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

import model.Guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.GuestMapper;

@Service
public class GuestServiceImpl implements GuestService{
	@Autowired
	private GuestMapper guestMapper;
	
	public List<Guest> findGuestsByIdStructure(Integer id_structure) {
		return this.getGuestMapper().findGuestsByIdStructure(id_structure);
	}
	
	@Override
	public Integer insertGuest(Guest guest) {
		return this.getGuestMapper().insertGuest(guest);
	}
	
	@Override
	public Guest findGuestById(Integer id) {		
		return this.getGuestMapper().findGuestById(id);
	}
	
	@Override
	public Integer updateGuest(Guest guest) {
		return this.getGuestMapper().updateGuest(guest);
	}

	@Override
	public Integer deleteGuest(Integer id) {
		return this.getGuestMapper().deleteGuest(id);
	}

	public GuestMapper getGuestMapper() {
		return guestMapper;
	}
	public void setGuestMapper(GuestMapper guestMapper) {
		this.guestMapper = guestMapper;
	}

}