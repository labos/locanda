package model;

import java.util.ArrayList;
import java.util.List;

public class RoomType {
	
	private Integer id;
	private String name;
	private Integer maxGuests;
	private List<Image> images;
	private List<RoomFacility> roomTypeFacilities;
	
	
	public RoomType(){
		
		this.images = new ArrayList<Image>();
		this.roomTypeFacilities = new ArrayList<RoomFacility>();
	}
	
	
	public Boolean addRoomTypeImage(Image roomTypeImage){
		
		return this.getImages().add(roomTypeImage);
		
	}
	
	public boolean deleteImage(Image aImage) {
		return this.getImages().remove(aImage);
	}
	
	
	
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
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<RoomFacility> getRoomTypeFacilities() {
		return roomTypeFacilities;
	}

	public void setRoomTypeFacilities(List<RoomFacility> facilityList) {
		this.roomTypeFacilities = facilityList;
	}
	
	

}
