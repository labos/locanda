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
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StructureFacilityService {	
	
	public Integer insert(Integer id_structure,Integer id_facility);	
	public List<Integer> findIdFacilityByIdStructure(Integer id_structure,Integer offset, Integer rownum);	
	public Integer findIdByIdStructureAndIdFacility(Integer id_structure,Integer id_facility);
	public Integer delete(Integer id);
	public Integer deleteByIdStructure(Integer id_structure);
	public Integer deleteByIdFacility(Integer id_facility);	
}