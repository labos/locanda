package service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.StudenteMapper;

import model.Studente;

@Service
public class StudenteServiceImpl implements StudenteService{
	@Autowired
	StudenteMapper studenteMapper = null;
	
	public List<Studente> findAll() {
		List<Studente> ret = null;
			
		ret = this.getStudenteMapper().findAll();
		return ret;
	}

	
	public StudenteMapper getStudenteMapper() {
		return studenteMapper;
	}



	public void setStudenteMapper(StudenteMapper studenteMapper) {
		this.studenteMapper = studenteMapper;
	}
	
	
}
