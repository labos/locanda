package persistence.mybatis.mappers;

import java.util.List;

import model.Facility;

public interface FacilityMapper {
	public Integer insertStructureFacility(Facility facility);
	public Integer deleteStructureFacility(Integer id);
	public List<Facility> findStructureFacilitiesByIdStructure(Integer id_structure);

}
