package service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ConventionMapper;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

@Service
public class ConventionServiceImpl implements ConventionService{
	@Autowired
	private ConventionMapper conventionMapper = null;

	
	
	@Override
	public Integer insertConvention(Convention convention) {		
		return this.getConventionMapper().insertConvention(convention);
	}


	@Override
	public Integer updateConvention(Convention convention) {
		return this.getConventionMapper().updateConvention(convention);
	}


	@Override
	public Integer deleteConvention(Integer id) {
		return this.getConventionMapper().deleteConvention(id);
	}


	@Override
	public List<Convention> findConventionsByIdStructure(Integer id_structure) {
		return this.getConventionMapper().findConventionsByIdStructure(id_structure);
	}


	@Override
	public Convention findConventionById(Integer id) {
		return this.getConventionMapper().findConventionById(id);
	}



	public ConventionMapper getConventionMapper() {
		return conventionMapper;
	}


	public void setConventionMapper(ConventionMapper conventionMapper) {
		this.conventionMapper = conventionMapper;
	}
	
	
}
