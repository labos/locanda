package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.ExtraMapper;
import persistence.mybatis.mappers.ExtraPriceListItemMapper;

import model.Extra;
@Service
public class ExtraServiceImpl implements ExtraService {
	@Autowired
	private ExtraMapper extraMapper = null;
	@Autowired
	private ExtraPriceListItemMapper extraPriceListItemMapper = null;
	


	public List<Extra> findExtrasByIdStructure(Integer id_structure) {
		List<Extra> ret = null;
		
		ret = this.getExtraMapper().findExtrasByIdStructure(id_structure);
		return ret;
	}	
	
	public List<Extra> findExtrasByIdStructureAndAvailableOnline(Integer id_structure, Boolean availableOnline) {
		List<Extra> ret = null;
		Map params = null;
		
		params = new HashMap();
		params.put("id_structure", id_structure);
		params.put("availableOnline", availableOnline);
		
		ret = this.getExtraMapper().findExtrasByIdStructureAndAvailableOnline(params);
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
		this.getExtraPriceListItemMapper().deleteExtraPriceListItemsByIdExtra(id);
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

	public ExtraPriceListItemMapper getExtraPriceListItemMapper() {
		return extraPriceListItemMapper;
	}

	public void setExtraPriceListItemMapper(
			ExtraPriceListItemMapper extraPriceListItemMapper) {
		this.extraPriceListItemMapper = extraPriceListItemMapper;
	}

	
	

}
