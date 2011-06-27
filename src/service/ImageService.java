package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Image;

@Transactional
public interface ImageService {
	public Integer insertStructureImage(Image image);	
	public List<Image> findImagesByIdStructure(Integer id_structure);
	public Integer deleteStructureImage(Integer id);

}
