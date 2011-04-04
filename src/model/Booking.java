package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class Booking {
	
	private Integer id;
	
	private Guest guest;
	private Integer nrGuests;
	private Room room;
	private Date dateIn;
	private Date dateOut;
	private Double subtotal;
	private Double extraSubtotal;
	private String notes;
	private List<Extra> extras;
	
	public Booking(){
		this.extras = new ArrayList<Extra>();
	}
	
	public Boolean addExtra(Extra anExtra){
		return this.getExtras().add(anExtra);
	}
	
	public Boolean addExtras(List<Extra> extras){
		return this.getExtras().addAll(extras);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public Integer getNrGuests() {
		return nrGuests;
	}
	public void setNrGuests(Integer nrGuests) {
		this.nrGuests = nrGuests;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<Extra> getExtras() {
		return extras;
	}
	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public Double getExtraSubtotal() {
		return extraSubtotal;
	}

	public void setExtraSubtotal(Double extraSubtotal) {
		this.extraSubtotal = extraSubtotal;
	}
	
	
}
