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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import model.Facility;
import model.File;
import model.Image;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityImageMapper;

@Service
public class FacilityImageServiceImpl implements FacilityImageService{
	@Autowired
	private FacilityImageMapper facilityImageMapper = null;	
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private ApplicationContext applicationContext = null;
	
	@Override
	public Facility associateDefaultImage(Facility facility) {
		Image image = null;
		File file = null;
		byte[] data = null;
		
		image = new Image();
		image.setCaption(facility.getName());
		image.setId_structure(facility.getId_structure());
		file = new File();
		file.setName("image-default.png");
		
		try {
			data = IOUtils.toByteArray(this.getApplicationContext().getResource("/images/image-default.png").getInputStream());
			file.setData(data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image.setFile(file);
		this.getImageService().insert(image);
		facility.setImage(image);
		this.insert(facility.getId(), image.getId());
		return facility;
	}
	
	@Override
	public void updateAssociatedImage(Facility facility) {	//updates the caption of the associated image. called by the facility update method
		Image image = null;
		
		image = this.imageService.findByIdFacility(facility.getId());
		image.setCaption(facility.getName());
		this.getImageService().update(image);
	}
	
	@Override
	public Integer insert(Integer id_facility, Integer id_image) {
		Map map = null;			
		
		map = new HashMap();
		map.put("id_facility",id_facility );
		map.put("id_image",id_image);
		return this.getFacilityImageMapper().insert(map);
	}

	@Override
	public Integer findIdImageByIdFacility(Integer id_facility) {
		Map map = null;
		Integer ret = 0;
		
		map = this.getFacilityImageMapper().findByIdFacility(id_facility);
		if (map != null) {
			ret = (Integer)map.get("id_image");
		}
		return ret;
	}

	@Override
	public Integer findIdFacilityByIdImage(Integer id_image) {
		Map map = null;
		Integer ret = 0;
		
		map = this.getFacilityImageMapper().findByIdImage(id_image);
		if (map != null) {
			ret = (Integer)map.get("id_facility");
		}
		return ret;
	}

	@Override
	public Integer delete(Integer id) {
		return this.getFacilityImageMapper().delete(id);
	}

	@Override
	public Integer deleteByIdImage(Integer id_image) {
		return this.getFacilityImageMapper().deleteByIdImage(id_image);
	}

	@Override
	public Integer deleteByIdFacility(Integer id_facility) {
		return this.getFacilityImageMapper().deleteByIdFacility(id_facility);
	}
	
	public FacilityImageMapper getFacilityImageMapper() {
		return facilityImageMapper;
	}
	public void setFacilityImageMapper(FacilityImageMapper facilityImageMapper) {
		this.facilityImageMapper = facilityImageMapper;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
		
}