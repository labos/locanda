package service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ExtraMapper;

import model.Extra;
import model.Structure;
@Service
public class ExtraServiceImpl implements ExtraService {
	@Autowired
	private ExtraMapper extraMapper = null;
	


	public List<Extra> findExtrasByIdStructure(Integer idStructure) {
		List<Extra> ret = null;
		
		ret = this.getExtraMapper().findExtrasByIdStructure(idStructure);
		return ret;
	}	
	
	

	@Override
	public Integer insertExtra(Extra extra) {
		
		return this.getExtraMapper().insertExtra(extra);
	}
	
	@Override
	public Extra findExtraById(Integer id) {
		
		return this.getExtraMapper().findExtraById(id);
	}
	


	@Override
	public Integer updateExtra(Extra extra) {
		return this.getExtraMapper().updateExtra(extra);
	}
	
	

	@Override
	public Integer deleteExtra(Integer id) {
		
		return this.getExtraMapper().deleteExtra(id);
	}

	public List<Extra> findExtrasByIds(List<Integer> ids){
		List<Extra> ret = new ArrayList<Extra>();
		
		for(Integer each:ids){
			Extra anExtra = this.getExtraMapper().findExtraById(each);
			ret.add(anExtra);					
		}
		return ret;
	}
	
	public ExtraMapper getExtraMapper() {
		return extraMapper;
	}

	public void setExtraMapper(ExtraMapper extraMapper) {
		this.extraMapper = extraMapper;
	}

	
	

}
