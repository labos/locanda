package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Studente;

@Transactional
public interface StudenteService {
	public List<Studente> findAll();
}
