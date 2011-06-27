package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FacilityMapper;

import model.Facility;

@Service
public class FacilityServiceImpl implements FacilityService{
	@Autowired
	private FacilityMapper facilityMapper = null;
	
	
	public Integer insertStructureFacility(Facility facility) {
		return this.getFacilityMapper().insertStructureFacility(facility);
	}
	

	@Override
	public Integer deleteStructureFacility(Integer id) {
		return this.getFacilityMapper().deleteStructureFacility(id);
	}

	

	@Override
	public List<Facility> findStructureFacilitiesByIdStructure(	Integer id_structure) {
		
		return this.getFacilityMapper().findStructureFacilitiesByIdStructure(id_structure);
	}


	public FacilityMapper getFacilityMapper() {
		return facilityMapper;
	}


	public void setFacilityMapper(
			FacilityMapper structureFacilityMapper) {
		this.facilityMapper = structureFacilityMapper;
	}
	
	

}
