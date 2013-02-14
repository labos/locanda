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

import persistence.mybatis.mappers.GroupLeaderMapper;

@Service
public class GroupLeaderServiceImpl implements GroupLeaderService{
	@Autowired
	private GroupLeaderMapper groupLeaderMapper = null;
		
	@Override
	public Integer insert(Integer id_booking, Integer id_housed) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_booking",id_booking );
		map.put("id_housed",id_housed);
		return this.getGroupLeaderMapper().insert(map);
	}	
	
	@Override
	public Housed findGroupLeaderByIdBooking(Integer id_booking) {
		return this.getGroupLeaderMapper().findGroupLeaderByIdBooking(id_booking);
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
	
}