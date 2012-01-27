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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ImageMapper;

import model.Image;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageMapper imageMapper = null;	

	@Override
	public Integer insertImage(Image image) {		
		return this.getImageMapper().insertImage(image);		
	}

	
	
	@Override
	public Integer insertStructureImage(Integer id_structure, Integer id_image) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_structure",id_structure );
		map.put("id_image",id_image);
		return this.getImageMapper().insertStructureImage(map);
	}



	@Override
	public Integer insertRoomTypeImage(Integer id_roomType, Integer id_image) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_roomType",id_roomType );
		map.put("id_image",id_image);
		return this.getImageMapper().insertRoomTypeImage(map);
	}




	@Override
	public Integer insertRoomImage(Integer id_room, Integer id_image) {
		Map map = null;		
		
		map = new HashMap();
		map.put("id_room",id_room );
		map.put("id_image",id_image);
		return this.getImageMapper().insertRoomImage(map);
	}
	

	@Override
	public Integer insertFacilityImage(Integer id_facility,Integer id_image) {
		Map map = null;			
		
		map = new HashMap();
		map.put("id_facility",id_facility );
		map.put("id_image",id_image);
		return this.getImageMapper().insertFacilityImage(map);
	}


	
	
	@Override
	public List<Image> findImagesByIdStructure(Integer id_structure) {
		List<Integer> ids = null;
		List<Image> ret = null;
		Image image = null;
		
		ids = this.getImageMapper().findImageIdsByIdStructure(id_structure);
		ret = new ArrayList<Image>();
		for(Integer each: ids){
			image = this.getImageMapper().findImageMetadataById(each);
			ret.add(image);
		}
		return ret;
	}

	
	@Override
	public List<Image> findImagesByIdRoomType(Integer id_roomType) {
		List<Integer> ids = null;
		List<Image> ret = null;
		Image image = null;
		
		ids = this.getImageMapper().findImageIdsByIdRoomType(id_roomType);
		ret = new ArrayList<Image>();
		for(Integer each: ids){
			image = this.getImageMapper().findImageMetadataById(each);
			ret.add(image);
		}
		return ret;
	}
	
	
	@Override
	public List<Image> findImagesByIdRoom(Integer id_room) {
		List<Integer> ids = null;
		List<Image> ret = null;
		Image image = null;
		
		ids = this.getImageMapper().findImageIdsByIdRoom(id_room);
		ret = new ArrayList<Image>();
		for(Integer each: ids){
			image = this.getImageMapper().findImageMetadataById(each);
			ret.add(image);
		}
		return ret;
	}
	
	@Override
	public Image findImageById(Integer id) {		
		return this.getImageMapper().findImageById(id);
	}	
	
	
	
	@Override
	public Integer deleteImage(Integer id) {
		
		return this.getImageMapper().deleteImage(id);
	}



	@Override
	public Integer deleteStructureImage(Integer id_structure, Integer id_image) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("id_image", id_image);
		return this.getImageMapper().deleteStructureImage(map);
	}



	@Override
	public Integer deleteStructureImages(Integer id_structure) {
		List<Integer> ids = null;
		Integer count = 0;		
		
		ids = this.getImageMapper().findImageIdsByIdStructure(id_structure);		
		for (Integer each: ids){			
			count = count + this.deleteStructureImage(id_structure,each);
		}
		return count;
	}



	@Override
	public Integer deleteRoomTypeImage(Integer id_roomType, Integer id_image) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_roomType", id_roomType);
		map.put("id_image", id_image);
		return this.getImageMapper().deleteRoomTypeImage(map);
	}



	@Override
	public Integer deleteRoomTypeImages(Integer id_roomType) {
		List<Integer> ids = null;
		Integer count = 0;		
		
		ids = this.getImageMapper().findImageIdsByIdRoomType(id_roomType);		
		for (Integer each: ids){			
			count = count + this.deleteRoomTypeImage(id_roomType,each);
		}
		return count;
	}



	@Override
	public Integer deleteRoomImage(Integer id_room, Integer id_image) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_room", id_room);
		map.put("id_image", id_image);
		return this.getImageMapper().deleteRoomImage(map);
	}



	@Override
	public Integer deleteRoomImages(Integer id_room) {
		List<Integer> ids = null;
		Integer count = 0;		
		
		ids = this.getImageMapper().findImageIdsByIdRoom(id_room);		
		for (Integer each: ids){			
			count = count + this.deleteRoomImage(id_room,each);
		}
		return count;
	}



	public ImageMapper getImageMapper() {
		return imageMapper;
	}
	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
	
}