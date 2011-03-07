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
	
	public Boolean hasRoomNamed(String name){
		Boolean ret = false;
		
		for(Room each: this.getRooms()){
			if(each.getName().equals(name)){
				return true;
			}
		}
		return ret;
	}
	
	public void addRoom(Room aRoom){
		this.getRooms().add(aRoom);
	}
	
	public void addAllRooms(List<Room> rooms){
		this.getRooms().addAll(rooms);
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
