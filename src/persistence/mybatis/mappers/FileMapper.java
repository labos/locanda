package persistence.mybatis.mappers;

import model.File;

public interface FileMapper {
	public Integer insert(File file);
	public Integer update(File file);
	public Integer delete(Integer id);
	public File find(Integer id);

}
