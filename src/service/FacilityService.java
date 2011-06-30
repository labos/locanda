package service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import model.Facility;

@Transactional
public interface FacilityService {
	public Integer insertUploadedFacility(Facility facility);
	public Integer insertRoomFacility(Integer id_uploadedFacility, Integer id_room);
	public Integer insertRoomFacilities(List<Integer> uploadedFacilitiesIds, Integer id_room);
	public Integer insertRoomTypeFacility(Integer id_uploadedFacility, Integer id_roomType);
	public Integer insertRoomTypeFacilities(List<Integer> uploadedFacilitiesIds, Integer id_roomType);
	public Integer insertStructureFacility(Facility facility);
	
	public List<Facility> findUploadedFacilitiesByIdStructure(Integer id_structure);
	public Facility findUploadedFacilityById(Integer id);
	public List<Facility> findUploadedFacilitiesByIds(List<Integer> ids);
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room);
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType);
	
	public Integer deleteUploadedFacility(Integer id);
	public Integer deleteAllFacilitiesFromRoom(Integer id_room);
	public Integer deleteAllFacilitiesFromRoomType(Integer id_roomType);
	
	public Integer deleteStructureFacility(Integer id);	
	
	public Integer updateUploadedFacility(Facility facility);

}
