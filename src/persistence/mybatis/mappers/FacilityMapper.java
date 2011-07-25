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

import model.Facility;

public interface FacilityMapper {
	public Integer insertUploadedFacility(Facility facility);
	public Integer insertRoomFacility(Map map);
	public Integer insertRoomTypeFacility(Map map);
	public Integer insertStructureFacility(Facility facility);
	
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);
	
	public List<Facility> findUploadedFacilitiesByIdStructure(Integer id_structure);
	public Facility findUploadedFacilityById(Integer id);
	
	public Facility findStructureFacilityByName(Map map);
	public Facility findUploadedFacilityByName(Map map);
	
	
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room);
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType);
	
	
	public Integer deleteUploadedFacility(Integer id);
	public Integer deleteFacilityFromAllRooms(Integer id_uploadedFacility);
	public Integer deleteFacilityFromAllRoomTypes(Integer id_uploadedFacility);
	
	public Integer deleteAllFacilitiesFromRoom(Integer id_room);
	public Integer deleteAllFacilitiesFromRoomType(Integer id_roomType);
	
	public Integer deleteStructureFacility(Integer id);
	
	public Integer updateUploadedFacility(Facility facility);
	
	

}
