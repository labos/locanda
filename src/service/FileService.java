package service;

import model.File;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FileService {
	
	public Integer insert(File file);
	public Integer update(File file);
	public Integer delete(Integer id);
	public Integer deleteByIdImage(Integer id);
	public File findById(Integer id);

}
