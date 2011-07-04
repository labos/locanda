package persistence.mybatis.mappers;

import java.util.List;

import model.listini.Convention;

public interface ConventionMapper {
	public Integer insertConvention(Convention convention);
	public Integer updateConvention(Convention convention);
	public Integer deleteConvention(Integer id);
	public List<Convention> findConventionsByIdStructure(Integer id_structure);
	public Convention findConventionById(Integer id);
}
