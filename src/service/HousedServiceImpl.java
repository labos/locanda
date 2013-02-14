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

import model.Housed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.HousedMapper;

@Service
public class HousedServiceImpl implements HousedService{
	@Autowired
	private HousedMapper housedMapper;
	
	public List<Housed> findHousedByIdBooking(Integer id_booking) {
		return this.getHousedMapper().findHousedByIdBooking(id_booking);
	}
	
	@Override
	public List<Housed> findAll() {
		return this.getHousedMapper().findAll();
	}
	
	@Override
	public Housed findHousedById(Integer id) {		
		return this.getHousedMapper().findHousedById(id);
	}
	
	@Override
	public Integer insertHoused(Housed housed) {
		return this.getHousedMapper().insertHoused(housed);
	}
	
	@Override
	public Integer updateHoused(Housed housed) {
		return this.getHousedMapper().updateHoused(housed);
	}

	@Override
	public Integer deleteHoused(Integer id) {
		return this.getHousedMapper().deleteHoused(id);
	}

	public HousedMapper getHousedMapper() {
		return housedMapper;
	}
	public void setHousedMapper(HousedMapper housedMapper) {
		this.housedMapper = housedMapper;
	}

}