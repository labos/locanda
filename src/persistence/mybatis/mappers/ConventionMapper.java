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
package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.listini.Convention;

public interface ConventionMapper {
	public Integer insertConvention(Convention convention);
	public Integer updateConvention(Convention convention);
	public Integer deleteConvention(Integer id);
	public List<Convention> findAll();
	public List<Convention> findConventionsByIdStructure(Integer id_structure);
	public List<Convention> findConventionsByIdStructureWithoutDefault(Integer id_structure);
	public List<Convention> findConventionsByIdStructureAndOffsetAndRownum(Map map);
	public List<Convention> search(Map map);	
	
	public Convention findConventionById(Integer id);
	public Convention findConventionByIdWithoutDefault(Integer id);
}