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
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import model.Facility;

@Transactional
public interface FacilityService {
	public Integer insertUploadedFacility(Facility facility);
	public Integer insertRoomFacility(Integer id_uploadedFacility, Integer id_room);
	public Integer insertRoomFacilities(List<Integer> uploadedFacilitiesIds, Integer id_room);
	public Integer insertRoomTypeFacility(Integer id_uploadedFacility, Integer id_roomType);
	public Integer insertRoomTypeFacilities(List<Integer> uploadedFacilitiesIds, Integer id_roomType);
	public Integer insertStructureFacility(Facility facility);
	
	public List<Facility> findUploadedFacilitiesByIdStructure(Integer id_structure);
	public Facility findUploadedFacilityById(Integer id);
	
	public List<Facility> findUploadedFacilitiesByIds(List<Integer> ids);
	
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);
	
	public Facility findUploadedFacilityByName(Integer id_structure, String name);	
	public Facility findStructureFacilityByName(Integer id_structure, String name);
	
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room);
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType);
	
	public Integer deleteUploadedFacility(Integer id);
	public Integer deleteAllFacilitiesFromRoom(Integer id_room);
	public Integer deleteAllFacilitiesFromRoomType(Integer id_roomType);
	
	public Integer deleteStructureFacility(Integer id);	
	
	public Integer updateUploadedFacility(Facility facility);

}
