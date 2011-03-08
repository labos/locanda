package model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Structure {
	private String name;
	private String email;
	private List<Room> rooms;
	private TreeSet<Integer> keys;
	
	public Structure(){
		this.rooms = new ArrayList<Room>();
		this.keys = new TreeSet<Integer>();
		this.keys.add(1);
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
		aRoom.setId(this.nextKey());
		this.getRooms().add(aRoom);
	}
	
	public void addAllRooms(List<Room> rooms){
		for(Room each: rooms){
			this.addRoom(each);
		}
	}
	
	public Integer nextKey(){
		Integer ret = 0;
		
		ret = this.getKeys().last();
		ret = ret + 1;
		return ret;
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

	public TreeSet<Integer> getKeys() {
		return keys;
	}

	public void setKeys(TreeSet<Integer> keys) {
		this.keys = keys;
	}
	
	

}
