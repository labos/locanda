package model;

import java.util.Date;

public class Esame {
	private Integer id;
	private String nome;
	private String docente;
	private Integer voto;
	private Date data;
	private Integer id_studente;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocente() {
		return docente;
	}
	public void setDocente(String docente) {
		this.docente = docente;
	}
	public Integer getVoto() {
		return voto;
	}
	public void setVoto(Integer voto) {
		this.voto = voto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getId_studente() {
		return id_studente;
	}
	public void setId_studente(Integer id_studente) {
		this.id_studente = id_studente;
	}
	
	

}
