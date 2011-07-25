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
	public Integer insertRoomImage(Image image);
	public Integer insertRoomTypeImage(Image image);
	public Integer insertStructureImage(Image image);
	
	public List<Image> findImagesByIdRoom(Integer id_room);
	public List<Image> findImagesByIdRoomType(Integer id_roomType);
	public List<Image> findImagesByIdStructure(Integer id_structure);
	
	public Image findStructureImageByName(Map map);
	public Image findRoomImageByName(Map map);
	public Image findRoomTypeImageByName(Map map);
	
	public Integer deleteRoomImage(Integer id);
	public Integer deleteAllImagesFromRoom(Integer id);
	
	public Integer deleteRoomTypeImage(Integer id);
	public Integer deleteAllImagesFromRoomType(Integer id);
	
	public Integer deleteStructureImage(Integer id);
	
	
}
