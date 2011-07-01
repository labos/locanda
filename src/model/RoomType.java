package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomType implements Serializable{
	
	private Integer id;
	private String name;
	private Integer maxGuests;
	private String notes;
	private List<Image> images;
	private List<Facility> facilities;
	private Integer id_structure;
	
	
	public RoomType(){
		
		this.images = new ArrayList<Image>();
		this.facilities = new ArrayList<Facility>();
	}
	
	
	public Boolean addRoomTypeImage(Image roomTypeImage){
		
		return this.getImages().add(roomTypeImage);
		
	}
	
	public boolean deleteImage(Image aImage) {
		return this.getImages().remove(aImage);
	}
	
	
	
	public Boolean addRoomTypeFacility(Facility roomTypeFacility){
		
		return this.getFacilities().add(roomTypeFacility);
		
	}
	
	public boolean deleteFacility(Facility roomTypeFacility) {
		return this.getFacilities().remove(roomTypeFacility);
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

	public List<Facility> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<Facility> facilityList) {
		this.facilities = facilityList;
	}


	public Integer getId_structure() {
		return id_structure;
	}


	public void setId_structure(Integer id_structure) {
		this.id_structure = id_structure;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	

}
