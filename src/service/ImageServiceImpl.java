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
import model.File;
import model.Image;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageMapper imageMapper = null;		
	@Autowired
	private ImageFileService imageFileService = null;
	@Autowired
	private StructureImageService structureImageService = null;
	@Autowired
	private RoomTypeImageService roomTypeImageService = null;
	@Autowired
	private RoomImageService roomImageService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private FacilityImageService facilityImageService = null;
	@Autowired
	private FileService fileService = null;

	@Override
	public Integer insert(Image image) {	
		Integer count;
		File file = null;
		
		count = this.getImageMapper().insert(image);
		file = image.getFile();
		this.getFileService().insert(file);		
		this.getImageFileService().insert(image.getId(), file.getId());		
		return count;				
	}	
	
	@Override
	public Integer update(Image image) {		
		return this.getImageMapper().update(image);
	}
	
	@Override
	public Integer delete(Integer id) {
		Integer count = 0;
		Integer id_facility = 0;
				
		this.getStructureImageService().deleteByIdImage(id);
		this.getRoomTypeImageService().deleteByIdImage(id);
		this.getRoomImageService().deleteByIdImage(id);
		
		//if the image is associated with a facility, creates a new association with the default image
		id_facility = this.getFacilityImageService().findIdFacilityByIdImage(id);
		if (id_facility != 0) {
			this.getFacilityImageService().associateDefaultImage(this.getFacilityService().find(id_facility));
			this.getFacilityImageService().deleteByIdImage(id);
			}
		
		//now all the data related to the old image are erased
		this.getFileService().deleteByIdImage(id);
		count = this.getImageMapper().delete(id);
		return count; 
	}
	
	
	
	@Override
	public List<Image> findAll() {
		
		return this.getImageMapper().findAll();
	}

	@Override
	public Image find(Integer id) {	
		Image ret = null;
		File file = null;
		Integer id_file;
		
		ret = this.getImageMapper().find(id);
		file = new File();
		id_file = this.getImageFileService().findIdFileByIdImage(id);		
		file.setId(id_file);
		ret.setFile(file);
		return ret;		
	}	

	@Override
	public List<Image> findByIdStructure(Integer id_structure,Integer offset, Integer rownum) {
		List<Image> ret = null;
		Integer id_file;
		File file;
		Map map = null;
		
		map = new HashMap();
		map.put("id_structure", id_structure);
		map.put("offset", offset);
		map.put("rownum", rownum);
		
		ret = this.getImageMapper().findByIdStructure(map);
		for(Image each: ret){
			file = new File();
			id_file = this.getImageFileService().findIdFileByIdImage(each.getId());		
			file.setId(id_file);
			each.setFile(file);
		}		
		return ret;
	}

	@Override
	public List<Image> findCheckedByIdStructure(Integer id_structure,Integer offset, Integer rownum) {
		List<Image> ret = null;
		Image image = null;
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getStructureImageService().findIdImageByIdStructure(id_structure,offset, rownum)){
			image = this.find(id);
			ret.add(image);
		}
		return ret;
	}
	
	@Override
	public List<Image> findCheckedByIdRoomType(Integer id_roomType, Integer offset, Integer rownum ) {
		List<Image> ret = null;
		Image image = null;
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getRoomTypeImageService().findIdImageByIdRoomType(id_roomType,offset, rownum)){
			image = this.find(id);
			ret.add(image);
		}
		return ret;
	}
	
	@Override
	public List<Image> findCheckedByIdRoom(Integer id_room,Integer offset, Integer rownum) {
		List<Image> ret = null;
		Image image = null;		
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getRoomImageService().findIdImageByIdRoom(id_room,offset,rownum)){
			image = this.find(id);
			ret.add(image);
		}
		return ret;
	}
	
	@Override
	public Image findByIdFacility(Integer id_facility) {
		Integer id_image = 0;
		Image ret = null;
		
		id_image = this.getFacilityImageService().findIdImageByIdFacility(id_facility);		
		ret = this.find(id_image);
		return ret;
	}
	
	public ImageMapper getImageMapper() {
		return imageMapper;
	}
	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
	public ImageFileService getImageFileService() {
		return imageFileService;
	}
	public void setImageFileService(ImageFileService imageFileService) {
		this.imageFileService = imageFileService;
	}
	public FileService getFileService() {
		return fileService;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	public RoomTypeImageService getRoomTypeImageService() {
		return roomTypeImageService;
	}
	public void setRoomTypeImageService(RoomTypeImageService roomTypeImageService) {
		this.roomTypeImageService = roomTypeImageService;
	}
	public RoomImageService getRoomImageService() {
		return roomImageService;
	}
	public void setRoomImageService(RoomImageService roomImageService) {
		this.roomImageService = roomImageService;
	}
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public FacilityImageService getFacilityImageService() {
		return facilityImageService;
	}
	public void setFacilityImageService(FacilityImageService facilityImageService) {
		this.facilityImageService = facilityImageService;
	}
	public StructureImageService getStructureImageService() {
		return structureImageService;
	}
	public void setStructureImageService(StructureImageService structureImageService) {
		this.structureImageService = structureImageService;
	}
		
}