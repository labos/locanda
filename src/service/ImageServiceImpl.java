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

import persistence.mybatis.mappers.FacilityImageMapper;
import persistence.mybatis.mappers.FileMapper;
import persistence.mybatis.mappers.ImageFileMapper;
import persistence.mybatis.mappers.ImageMapper;
import persistence.mybatis.mappers.RoomImageMapper;
import persistence.mybatis.mappers.RoomTypeImageMapper;
import persistence.mybatis.mappers.StructureImageOwnershipMapper;

import model.File;
import model.Image;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageMapper imageMapper = null;		
	@Autowired
	private ImageFileService imageFileService = null;
	@Autowired
	private StructureImageOwnershipService structureImageOwnershipService = null;
	@Autowired
	private StructureImageCheckService structureImageCheckService = null;
	@Autowired
	private RoomTypeImageService roomTypeImageService = null;
	@Autowired
	private RoomImageService roomImageService = null;
	@Autowired
	private FacilityImageService facilityImageService = null;
	@Autowired
	private FileService fileService = null;

	@Override
	public Integer insert(Image image,Integer id_structure) {	
		Integer count;
		File file = null;
		
		count = this.getImageMapper().insert(image);
		this.getStructureImageOwnershipService().insert(id_structure, image.getId());
		file = image.getFile();
		this.getFileService().insert(file);		
		this.getImageFileService().insert(image.getId(), file.getId());		
		return count;				
	}	
	
	@Override
	public Image update(Image image) {		
		return this.getImageMapper().update(image);
	}
	
	@Override
	public Integer delete(Integer id) {
		Integer count = 0;
				
		this.getStructureImageOwnershipService().deleteByIdImage(id);
		this.getStructureImageCheckService().deleteByIdImage(id);
		this.getRoomTypeImageService().deleteByIdImage(id);
		this.getRoomImageService().deleteByIdImage(id);
		this.getFacilityImageService().deleteByIdImage(id);		
		
		this.getFileService().deleteByIdImage(id);
			
		count = this.getImageMapper().delete(id);
		return count; 
		
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
	public List<Image> findByIdStructure_ownership(Integer id_structure) {
		List<Image> ret = null;
		Image image = null;
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getStructureImageOwnershipService().findIdImageByIdStructure(id_structure)){
			image = this.find(id);
			ret.add(image);
		}
		return ret;
	}

	@Override
	public List<Image> findByIdStructure_check(Integer id_structure) {
		List<Image> ret = null;
		Image image = null;
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getStructureImageCheckService().findIdImageByIdStructure(id_structure)){
			image = this.find(id);
			ret.add(image);
		}
		return ret;
	}

	
	@Override
	public List<Image> findByIdRoomType(Integer id_roomType) {
		List<Image> ret = null;
		Image image = null;
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getRoomTypeImageService().findIdImageByIdRoomType(id_roomType)){
			image = this.find(id);
			ret.add(image);
		}
		return ret;
	}
	
	
	@Override
	public List<Image> findByIdRoom(Integer id_room) {
		List<Image> ret = null;
		Image image = null;		
		
		ret = new ArrayList<Image>();
		for(Integer id: this.getRoomImageService().findIdImageByIdRoom(id_room)){
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

	public StructureImageOwnershipService getStructureImageOwnershipService() {
		return structureImageOwnershipService;
	}

	public void setStructureImageOwnershipService(StructureImageOwnershipService structureImageOwnershipService) {
		this.structureImageOwnershipService = structureImageOwnershipService;
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

	public FacilityImageService getFacilityImageService() {
		return facilityImageService;
	}

	public void setFacilityImageService(FacilityImageService facilityImageService) {
		this.facilityImageService = facilityImageService;
	}

	public StructureImageCheckService getStructureImageCheckService() {
		return structureImageCheckService;
	}

	public void setStructureImageCheckService(StructureImageCheckService structureImageCheckService) {
		this.structureImageCheckService = structureImageCheckService;
	}
		
}