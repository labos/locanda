package model;

public class Room {
	private String name;
	private Double price;
	private String notes;
	private Integer maxGuests;
	private String roomType;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
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

	public Integer getMaxGuests() {
		return maxGuests;
	}

	public void setMaxGuests(Integer maxGuests) {
		this.maxGuests = maxGuests;
	}
	
	
	
	

}
