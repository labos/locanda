package service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import model.Extra;


@Transactional
public interface ExtraService {
	public List<Extra> findExtrasByIdStructure(Integer structureId);
	public List<Extra> findExtrasByIdStructureAndAvailableOnline(Integer idStructure, Boolean availableOnline);
	public Integer insertExtra(Extra extra);
	public Extra findExtraById(Integer id);
	public Integer updateExtra(Extra extra);
	public Integer deleteExtra(Integer id);
	public List<Extra> findExtrasByIds(List<Integer> ids);
	
}
