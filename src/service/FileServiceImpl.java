package service;

import model.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.FileMapper;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private FileMapper fileMapper = null;
	@Autowired
	private ImageFileService imageFileService = null;
	
	
	@Override
	public Integer insert(File file) {
		return this.getFileMapper().insert(file);
	}

	@Override
	public Integer update(File file) {
		return this.getFileMapper().update(file);
	}

	@Override
	public Integer delete(Integer id) {
		Integer count = 0;
		
		count = this.getFileMapper().delete(id);		
		return count;
	}
	
	@Override
	public Integer deleteByIdImage(Integer id) {
		Integer count;
		Integer id_file;
		
		id_file = this.getImageFileService().findIdFileByIdImage(id);
		this.getImageFileService().deleteByIdImage(id);
		count = this.getFileMapper().delete(id_file);	
		return count;
	}

	@Override
	public File findById(Integer id) {
		return this.getFileMapper().find(id);
	}

	public FileMapper getFileMapper() {
		return fileMapper;
	}
	public void setFileMapper(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}
	public ImageFileService getImageFileService() {
		return imageFileService;
	}
	public void setImageFileService(ImageFileService imageFileService) {
		this.imageFileService = imageFileService;
	}	
	
}