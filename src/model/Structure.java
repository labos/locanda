package model;

import java.util.ArrayList;
import java.util.List;

public class Structure {
	private String name;
	private String email;
	private List<Room> rooms;
	
	public Structure(){
		this.rooms = new ArrayList<Room>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	

}
