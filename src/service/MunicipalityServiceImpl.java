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

import model.questura.Municipality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.MunicipalityMapper;

@Service
public class MunicipalityServiceImpl implements MunicipalityService{
	@Autowired
	private MunicipalityMapper municipalityMapper;
	
	@Override
	public List<Municipality> findAll() {
		return this.getMunicipalityMapper().findAll();
	}
	
	
	
	

	@Override
	public List<String> findAllProvinces() {
		
		return this.getMunicipalityMapper().findAllProvinces();
	}





	@Override
	public List<Municipality> findMunicipalitiesByProvince(String province) {
		
		return this.getMunicipalityMapper().findMunicipalitiesByProvince(province);
	}

	

	@Override
	public Municipality findById(Integer id) {
		Municipality ret = null;
		
		ret = this.getMunicipalityMapper().findById(id);
		return ret;
	}



	public MunicipalityMapper getMunicipalityMapper() {
		return municipalityMapper;
	}
	public void setMunicipalityMapper(MunicipalityMapper municipalityMapper) {
		this.municipalityMapper = municipalityMapper;
	}

}