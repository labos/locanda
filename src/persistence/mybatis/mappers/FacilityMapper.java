package persistence.mybatis.mappers;

import java.util.List;
import java.util.Map;

import model.Facility;

public interface FacilityMapper {
	public Integer insertUploadedFacility(Facility facility);
	public Integer insertRoomFacility(Map map);
	public Integer insertRoomTypeFacility(Map map);
	public Integer insertStructureFacility(Facility facility);
	
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);
	public List<Facility> findUploadedFacilitiesByIdStructure(Integer id_structure);
	public Facility findUploadedFacilityById(Integer id);
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room);
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType);
	
	
	public Integer deleteUploadedFacility(Integer id);
	public Integer deleteFacilityFromAllRooms(Integer id_uploadedFacility);
	public Integer deleteFacilityFromAllRoomTypes(Integer id_uploadedFacility);
	
	public Integer deleteAllFacilitiesFromRoom(Integer id_room);
	public Integer deleteAllFacilitiesFromRoomType(Integer id_roomType);
	
	public Integer deleteStructureFacility(Integer id);
	
	public Integer updateUploadedFacility(Facility facility);
	
	

}
