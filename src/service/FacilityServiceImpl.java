package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityMapper;

import model.Facility;

@Service
public class FacilityServiceImpl implements FacilityService{
	@Autowired
	private FacilityMapper facilityMapper = null;
	

	@Override
	public Integer insertUploadedFacility(Facility facility) {
		return this.getFacilityMapper().insertUploadedFacility(facility);
	}

	@Override
	public Integer insertRoomFacility(Integer id_uploadedFacility,Integer id_room) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_uploadedFacility", id_uploadedFacility);
		map.put("id_room", id_room);
		return this.getFacilityMapper().insertRoomFacility(map);
	}
	
	@Override
	public Integer insertRoomTypeFacility(Integer id_uploadedFacility,Integer id_roomType) {
		Map map = null;
		
		map = new HashMap();
		map.put("id_uploadedFacility", id_uploadedFacility);
		map.put("id_roomType", id_roomType);
		return this.getFacilityMapper().insertRoomTypeFacility(map);
	}

	@Override
	public Integer insertRoomFacilities(List<Integer> uploadedFacilitiesIds,Integer id_room) {
		Integer ret = 0;
		for(Integer id_uploadedFacility: uploadedFacilitiesIds){
			ret = ret + this.insertRoomFacility(id_uploadedFacility, id_room);
		}
		return ret;
	}


	
	
	
	
	@Override
	public Integer insertRoomTypeFacilities(List<Integer> uploadedFacilitiesIds, Integer id_roomType) {
		Integer ret = 0;
		for(Integer id_uploadedFacility: uploadedFacilitiesIds){
			ret = ret + this.insertRoomTypeFacility(id_uploadedFacility, id_roomType);
		}
		return ret;
	}


	public Integer insertStructureFacility(Facility facility) {
		return this.getFacilityMapper().insertStructureFacility(facility);
	}
	

	
	@Override
	public List<Facility> findUploadedFacilitiesByIdStructure(Integer id_structure) {
		return this.getFacilityMapper().findUploadedFacilitiesByIdStructure(id_structure);
	}

	
	@Override
	public Facility findUploadedFacilityById(Integer id) {
		
		return this.getFacilityMapper().findUploadedFacilityById(id);
	}

	
	@Override
	public Facility findUploadedFacilityByName(String name) {		
		return this.getFacilityMapper().findUploadedFacilityByName(name);
	}

	@Override
	public List<Facility> findUploadedFacilitiesByIds(List<Integer> ids) {
		List<Facility> ret = null;
		
		ret = new ArrayList<Facility>();
		for(Integer each: ids){
			ret.add(this.findUploadedFacilityById(each));
		}
		return ret;
	}


	@Override
	public List<Facility> findStructureFacilitiesByIdStructure(	Integer id_structure) {
		
		return this.getFacilityMapper().findStructureFacilitiesByIdStructure(id_structure);
	}
	
	
	
	@Override
	public Facility findStructureFacilityByName(String name) {
		
		return this.getFacilityMapper().findStructureFacilityByName(name);
	}

	@Override
	public List<Facility> findRoomFacilitiesByIdRoom(Integer id_room) {		
		return this.getFacilityMapper().findRoomFacilitiesByIdRoom(id_room);
	}

	
	
	@Override
	public List<Facility> findRoomTypeFacilitiesByIdRoomType(Integer id_roomType) {
		return this.getFacilityMapper().findRoomTypeFacilitiesByIdRoomType(id_roomType);
	}






	@Override
	public Integer deleteStructureFacility(Integer id) {
		return this.getFacilityMapper().deleteStructureFacility(id);
	}

	

	





	@Override
	public Integer deleteUploadedFacility(Integer id) {
		Integer ret = 0;
		
		ret = this.getFacilityMapper().deleteFacilityFromAllRooms(id); 
		ret = ret + this.getFacilityMapper().deleteFacilityFromAllRoomTypes(id);
		ret = ret + this.getFacilityMapper().deleteUploadedFacility(id);
		return ret;
	}

	

	@Override
	public Integer deleteAllFacilitiesFromRoom(Integer id_room) {		
		return this.getFacilityMapper().deleteAllFacilitiesFromRoom(id_room);
	}


	@Override
	public Integer deleteAllFacilitiesFromRoomType(Integer id_roomType) {		
		return this.getFacilityMapper().deleteAllFacilitiesFromRoomType(id_roomType);
	}

	
	
	@Override
	public Integer updateUploadedFacility(Facility facility) {		
		return this.getFacilityMapper().updateUploadedFacility(facility);
	}

	public FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}


	public void setFacilityMapper(
			FacilityMapper structureFacilityMapper) {
		this.facilityMapper = structureFacilityMapper;
	}
	

}
