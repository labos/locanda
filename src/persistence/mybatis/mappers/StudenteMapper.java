package persistence.mybatis.mappers;

import java.util.List;
import model.Studente;

public interface StudenteMapper {
	public List<Studente> findAll();
	
	//public Studente findByMatricola(Integer matricola);
	
	//public Integer insert(Studente studente);
	
	//public Integer update(Studente studente);	
}
