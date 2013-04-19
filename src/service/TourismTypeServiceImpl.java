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

import model.questura.TourismType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.TourismTypeMapper;

@Service
public class TourismTypeServiceImpl implements TourismTypeService{
	@Autowired
	private TourismTypeMapper tourismTypeMapper;
	
	
	@Override
	public TourismType findById(Integer id) {
		return this.getTourismTypeMapper().findById(id);
	}

	@Override
	public List<TourismType> findAll() {
		return this.getTourismTypeMapper().findAll();
	}

	public TourismTypeMapper getTourismTypeMapper() {
		return tourismTypeMapper;
	}
	public void setTourismTypeMapper(TourismTypeMapper transportMapper) {
		this.tourismTypeMapper = transportMapper;
	}

}