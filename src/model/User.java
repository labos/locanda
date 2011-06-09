package model;

import java.io.Serializable;

public class User implements Serializable{
	private Integer id;
	private String email;
	private Structure structure = null;
	private String password;
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
