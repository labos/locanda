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

import model.questura.HousedExport;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface HousedExportService {
	public Integer insert(HousedExport housedExport);
	public Integer update(HousedExport housedExport);
	public Integer delete(Integer id);
	public HousedExport findById(Integer id);
	public HousedExport findByIdHoused(Integer id_housed);
	public List<HousedExport> findByExported(Boolean exported);
	public List<HousedExport> findByExportedQuestura(Boolean exported);
	public List<HousedExport> findByIdStructureAndExported(Integer id_structure, Boolean exported);
	public List<HousedExport> findByIdStructureAndExportedQuestura(Integer id_structure, Boolean exported);

}