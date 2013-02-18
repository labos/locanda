package model;

import java.io.Serializable;

public class Booker implements Serializable {
	private Integer id;
	private Integer id_booking;
	private Guest guest;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_booking() {
		return id_booking;
	}
	public void setId_booking(Integer id_booking) {
		this.id_booking = id_booking;
	}
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	
}
