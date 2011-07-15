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
