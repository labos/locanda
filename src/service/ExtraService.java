package service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import model.Extra;


@Transactional
public interface ExtraService {
	public List<Extra> findExtrasByIdStructure(Integer structureId);
	public Integer insertExtra(Extra extra);
	
}
