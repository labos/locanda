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

import model.Housed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FamilyLeaderMapper;

@Service
public class FamilyLeaderServiceImpl implements FamilyLeaderService{
	@Autowired
	private FamilyLeaderMapper familyLeaderMapper = null;
		
	@Override
	public Integer insert(Integer id_booking, Integer id_housed) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_booking",id_booking );
		map.put("id_housed",id_housed);
		return this.getFamilyLeaderMapper().insert(map);
	}	
	
	@Override
	public Housed findFamilyLeaderByIdBooking(Integer id_booking) {
		return this.getFamilyLeaderMapper().findFamilyLeaderByIdBooking(id_booking);
	}
	
	@Override
	public Integer findIdByIdBookingAndIdHoused(Integer id_booking,	Integer id_housed) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_room", id_booking);
		map.put("id_facility", id_housed);
		
		return this.getFamilyLeaderMapper().findIdByIdBookingAndIdHoused(map);
	}

	@Override
	public Integer delete(Integer id) {	
		return this.getFamilyLeaderMapper().delete(id);
	}
	
	@Override
	public Integer deleteByIdBooking(Integer id_booking) {		
		return this.getFamilyLeaderMapper().deleteByIdBooking(id_booking);
	}

	public FamilyLeaderMapper getFamilyLeaderMapper() {
		return familyLeaderMapper;
	}
	public void setFamilyLeaderMapper(FamilyLeaderMapper familyLeaderMapper) {
		this.familyLeaderMapper = familyLeaderMapper;
	}
	
}