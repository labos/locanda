package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
	private Integer id;
	private String name;
	private Double price;
	private String notes;
	private RoomType roomType;
	private List<RoomFacility> facilities;
	private List<Image> imageLists;
	
	
	public Room(){
		this.facilities = new ArrayList<RoomFacility>();
		this.imageLists = new ArrayList<Image>();
	}
	
	public Boolean addRoomFacility(RoomFacility roomFacility){
		return this.getFacilities().add(roomFacility);	
	}
	
	public Boolean updateRoomFacilities (List<RoomFacility> facilities){
		this.setFacilities(facilities);
		return true;
	}
	
	//Room Image Adding	
	public Boolean addRoomImage(Image roomImage){
		return this.getImageLists().add(roomImage);
	}
	
	public boolean deleteImage(Image aImage) {
		return this.getImageLists().remove(aImage);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<RoomFacility> getFacilities() {
		return facilities;
	}
	public void setFacilities(List<RoomFacility> facilities) {
		this.facilities = facilities;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	public List<Image> getImageLists() {
		return imageLists;
	}
	public void setImageLists(List<Image> imageLists) {
		this.imageLists = imageLists;
	}	

}
