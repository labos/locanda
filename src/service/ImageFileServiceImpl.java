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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ImageFileMapper;

@Service
public class ImageFileServiceImpl implements ImageFileService{	
	@Autowired
	private ImageFileMapper imageFileMapper = null;
	
	@Override
	public Integer insert(Integer id_image,Integer id_file) {
		Map map = null;		
		
		map = new HashMap();		
		map.put("id_image",id_image);
		map.put("id_file",id_file );
		return this.getImageFileMapper().insert(map);
	}

	@Override
	public Integer delete(Integer id) {
		return this.getImageFileMapper().delete(id);
	}

	@Override
	public Integer deleteByIdImage(Integer id) {
		return this.getImageFileMapper().deleteByIdImage(id);
	}

	@Override
	public Integer findIdFileByIdImage(Integer id_image) {
		Integer ret;
		
		ret = (Integer)this.getImageFileMapper().findByIdImage(id_image).get("id_file");	
		return ret;
	}

	public ImageFileMapper getImageFileMapper() {
		return imageFileMapper;
	}
	public void setImageFileMapper(ImageFileMapper imageFileMapper) {
		this.imageFileMapper = imageFileMapper;
	}
		
}