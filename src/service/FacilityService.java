package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Facility;

@Transactional
public interface FacilityService {
	public Integer insertStructureFacility(Facility facility);
	public Integer deleteStructureFacility(Integer id);
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);

}
