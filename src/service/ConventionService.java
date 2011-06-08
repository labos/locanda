package service;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import model.RoomType;
import model.Structure;
import model.listini.Convention;
import model.listini.Season;

@Transactional
public interface ConventionService {
	public Integer insertConvention(Structure structure, Convention convention);
	public Integer updateConvention(Structure structure, Convention convention);
	public Integer deleteConvention(Structure structure, Convention convention);
	public List<Convention> findConventionsByIdStructure(Structure structure);
	public Convention findConventionById(Structure structure, Integer id);
	
	

}
