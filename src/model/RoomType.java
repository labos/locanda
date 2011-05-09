package model;

import java.util.ArrayList;
import java.util.List;

public class RoomType {
	
	private Integer id;
	private String name;
	private Integer maxGuests;
	private List<Image> imageLists;
	private List<RoomFacility> roomTypeFacilities;
	
	
	public RoomType(){
		
		this.imageLists = new ArrayList<Image>();
		this.roomTypeFacilities = new ArrayList<RoomFacility>();
	}
	
	//RoomType Photo Adding	
	public Boolean addRoomTypeImage(Image roomTypeImage){
		
		return this.getImageLists().add(roomTypeImage);
		
	}
	
	public boolean deleteImage(Image aImage) {
		return this.getImageLists().remove(aImage);
	}
	
	
	//RoomType facility Adding	
	public Boolean addRoomTypeFacility(RoomFacility roomTypeFacility){
		
		return this.getRoomTypeFacilities().add(roomTypeFacility);
		
	}
	
	public boolean deleteFacility(RoomFacility roomTypeFacility) {
		return this.getRoomTypeFacilities().remove(roomTypeFacility);
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
		RoomType other = (RoomType) obj;
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
	public Integer getMaxGuests() {
		return maxGuests;
	}
	public void setMaxGuests(Integer maxGuests) {
		this.maxGuests = maxGuests;
	}
	public List<Image> getImageLists() {
		return imageLists;
	}
	public void setImageLists(List<Image> imageLists) {
		this.imageLists = imageLists;
	}

	public List<RoomFacility> getRoomTypeFacilities() {
		return roomTypeFacilities;
	}

	public void setRoomTypeFacilities(List<RoomFacility> facilityList) {
		this.roomTypeFacilities = facilityList;
	}
	
	

}
