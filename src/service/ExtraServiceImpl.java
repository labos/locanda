package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ExtraMapper;

import model.Extra;
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



	public ExtraMapper getExtraMapper() {
		return extraMapper;
	}

	public void setExtraMapper(ExtraMapper extraMapper) {
		this.extraMapper = extraMapper;
	}

	
	

}
