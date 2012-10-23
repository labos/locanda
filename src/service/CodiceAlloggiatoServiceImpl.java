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

import model.questura.CodiceAlloggiato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.CodiceAlloggiatoMapper;

@Service
public class CodiceAlloggiatoServiceImpl implements CodiceAlloggiatoService{
	@Autowired
	private CodiceAlloggiatoMapper codiceAlloggiatoMapper;
	
		
	@Override
	public Integer insertGuest(CodiceAlloggiato codiceAlloggiato) {
		return this.getCodiceAlloggiatoMapper().insertCodiceAlloggiato(codiceAlloggiato);
	}
	
	@Override
	public List<CodiceAlloggiato> findAll() {
		return this.getCodiceAlloggiatoMapper().findAll();
	}
	
	
	public CodiceAlloggiatoMapper getCodiceAlloggiatoMapper() {
		return codiceAlloggiatoMapper;
	}
	public void setCodiceAlloggiatoMapper(CodiceAlloggiatoMapper codiceAlloggiatoMapper) {
		this.codiceAlloggiatoMapper = codiceAlloggiatoMapper;
	}

}