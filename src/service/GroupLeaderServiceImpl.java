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

import model.GroupLeader;
import model.Housed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.GroupLeaderMapper;
import persistence.mybatis.mappers.HousedMapper;

@Service
public class GroupLeaderServiceImpl implements GroupLeaderService{
	@Autowired
	private GroupLeaderMapper groupLeaderMapper = null;
	@Autowired
	private HousedMapper housedMapper = null;
		
	@Override
	public Integer insert(Integer id_booking, Integer id_housed) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_booking",id_booking );
		map.put("id_housed",id_housed);
		return this.getGroupLeaderMapper().insert(map);
	}	
	
	@Override
	public Integer update(GroupLeader groupLeader) {
		Integer ret = null;
		
		ret = this.getGroupLeaderMapper().update(groupLeader);
		return ret;
	}
	
	@Override
	public GroupLeader findGroupLeaderByIdBooking(Integer id_booking) {
		GroupLeader ret = null;
		Housed housed = null;
		
		ret = this.getGroupLeaderMapper().findGroupLeaderByIdBooking(id_booking);
		housed = this.getHousedMapper().findHousedById(ret.getId_housed());
		ret.setHoused(housed);
		return ret;
	}
	
	@Override
	public Integer findIdByIdBookingAndIdHoused(Integer id_booking,	Integer id_housed) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_room", id_booking);
		map.put("id_facility", id_housed);
		
		return this.getGroupLeaderMapper().findIdByIdBookingAndIdHoused(map);
	}

	@Override
	public Integer delete(Integer id) {	
		return this.getGroupLeaderMapper().delete(id);
	}
	
	@Override
	public Integer deleteByIdBooking(Integer id_booking) {		
		return this.getGroupLeaderMapper().deleteByIdBooking(id_booking);
	}

	public GroupLeaderMapper getGroupLeaderMapper() {
		return groupLeaderMapper;
	}
	public void setGroupLeaderMapper(GroupLeaderMapper groupLeaderMapper) {
		this.groupLeaderMapper = groupLeaderMapper;
	}
	public HousedMapper getHousedMapper() {
		return housedMapper;
	}
	public void setHousedMapper(HousedMapper housedMapper) {
		this.housedMapper = housedMapper;
	}
	
}