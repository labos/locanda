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
import java.util.Collections;

import model.questura.Comune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ComuneMapper;

@Service
public class ComuneServiceImpl implements ComuneService{
	@Autowired
	private ComuneMapper comuneMapper;
	
		
		
	@Override
	public List<Comune> findAll() {
		return this.getComuneMapper().findAll();
	}

	@Override
	public List<String> getAllProvincia() {
		List<String> prov = this.getComuneMapper().getAllProvincia();
		Collections.sort(prov);
		return prov;
	}


	public ComuneMapper getComuneMapper() {
		return comuneMapper;
	}



	public void setComuneMapper(ComuneMapper comuneMapper) {
		this.comuneMapper = comuneMapper;
	}
	
	
	


}