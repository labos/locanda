package persistence.mybatis.mappers;

import java.util.List;

import model.Image;

public interface ImageMapper {
	public Integer insertStructureImage(Image image);
	public List<Image> findImagesByIdStructure(Integer id_structure);
	public Integer deleteStructureImage(Integer id);

}
