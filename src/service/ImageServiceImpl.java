package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ImageMapper;

import model.Image;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageMapper imageMapper = null;

	@Override
	public Integer insertStructureImage(Image image) {
		
		return this.getImageMapper().insertStructureImage(image);
	}
	
	

	@Override
	public List<Image> findImagesByIdStructure(Integer id_structure) {
		
		return this.getImageMapper().findImagesByIdStructure(id_structure);
	}

	


	@Override
	public Integer deleteStructureImage(Integer id) {
		
		return this.getImageMapper().deleteStructureImage(id);
	}



	public ImageMapper getImageMapper() {
		return imageMapper;
	}

	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}
	
	

}
