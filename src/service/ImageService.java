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

import model.Image;

@Transactional
public interface ImageService {
	public Integer insertImage(Image image);
	public Integer insertStructureImage(Integer id_structure,Integer id_image);
	public Integer insertRoomTypeImage(Integer id_roomType,Integer id_image);
	public Integer insertRoomImage(Integer id_room,Integer id_image);
	public Integer insertFacilityImage(Integer id_facility,Integer id_image);
	
	public List<Image> findImagesByIdStructure(Integer id_structure);
	public List<Image> findImagesByIdRoomType(Integer id_roomType);
	public List<Image> findImagesByIdRoom(Integer id_room);
	public Image findImageById(Integer id);	
	
	public Integer deleteImage(Integer id);
	public Integer deleteStructureImage(Integer id_structure,Integer id_image);
	public Integer deleteStructureImages(Integer id_structure);
	
	public Integer deleteRoomTypeImage(Integer id_roomType,Integer id_image);
	public Integer deleteRoomTypeImages(Integer id_roomType);
	
	public Integer deleteRoomImage(Integer id_room,Integer id_image);	
	public Integer deleteRoomImages(Integer id_room);
	
	
	
	
	
}