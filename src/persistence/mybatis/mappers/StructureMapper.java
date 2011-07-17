package persistence.mybatis.mappers;

import model.Structure;

public interface StructureMapper {
	public Structure findStructureByIdUser(Integer id_user);
	public Structure findStructureById(Integer id);
	public Integer updateStructure(Structure structure);
	public Integer insertStructure(Structure structure);

}
