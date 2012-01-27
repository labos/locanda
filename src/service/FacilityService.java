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
import java.util.Set;
import model.Facility;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FacilityService {	
	
	public Integer insertFacility(Facility facility);		
	public Integer insertStructureFacility(Integer id_structure,Integer id_facility);		
	public Integer insertRoomTypeFacility(Integer id_roomType,Integer id_facility);		
	public Integer insertRoomFacility(Integer id_room,Integer id_facility);	
		
	public Facility findFacilityById(Integer id);
	public List<Facility> findFacilitiesByIds(List<Integer> ids);
	
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);
	
	public List<Facility> findRoomAndRoomTypeFacilitiesByIdStructure(Integer id_structure);	
	
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType);
	
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room);		
	
	public Integer deleteStructureFacility(Integer id_structure,Integer id_facility);
	public Integer deleteStructureFacilities(Integer id_structure);
	
	public Integer deleteRoomTypeFacility(Integer id_roomType,Integer id_facility);
	public Integer deleteRoomTypeFacilities(Integer id_roomType);
	
	public Integer deleteRoomFacility(Integer id_room,Integer id_facility);	
	public Integer deleteRoomFacilities(Integer id_room);
	
	
	
	
}