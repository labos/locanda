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

import model.Image;

public interface ImageMapper {
	public Integer insertImage(Image image);
	public Integer insertStructureImage(Map map);
	public Integer insertRoomTypeImage(Map map);
	public Integer insertRoomImage(Map map);
	public Integer insertFacilityImage(Map map);
	
	public List<Integer> findImageIdsByIdStructure(Integer id_structure);
	public List<Integer> findImageIdsByIdRoomType(Integer id_roomType);
	public List<Integer> findImageIdsByIdRoom(Integer id_room);
	public Image findImageById(Integer id);	
	public Image findImageMetadataById(Integer id);		
	
	public Integer deleteImage(Integer id);
	public Integer deleteStructureImage(Map map);		
	public Integer deleteRoomTypeImage(Map map);	
	public Integer deleteRoomImage(Map map);	
	
	
}