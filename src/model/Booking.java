package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class Booking {
	
	private Integer id;
	
	private Guest guest;
	private Integer nrGuests = 1;
	private Room room;
	private Date dateIn;
	private Date dateOut;
	private Double roomSubtotal = 0.0;
	private Double extraSubtotal = 0.0;
	private String notes;
	private List<Extra> extras;
	private List<Adjustment> adjustments;
	private List<Payment> payments = null;
	
	public Booking(){
		this.extras = new ArrayList<Extra>();
		this.adjustments = new ArrayList<Adjustment>();
		this.payments = new ArrayList<Payment>();
	}
	
	
	public Boolean addExtra(Extra anExtra){
		return this.getExtras().add(anExtra);
	}
	
	public Boolean addExtras(List<Extra> extras){
		return this.getExtras().addAll(extras);
	}
	
	public Boolean addAdjustment(Adjustment anAdjustment){
		return this.getAdjustments().add(anAdjustment);
	}
	
	public Boolean removeAdjustment(Adjustment anAdjustment){
		return this.getAdjustments().remove(anAdjustment);
	}
	
	public Boolean addPayment(Payment aPayment){
		return this.getPayments().add(aPayment);
	}
	
	public Boolean removePayment(Payment aPayment){
		return this.getPayments().remove(aPayment);
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
		Booking other = (Booking) obj;
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

	public Double getRoomSubtotal() {
		return roomSubtotal;
	}

	public void setRoomSubtotal(Double roomSubtotal) {
		this.roomSubtotal = roomSubtotal;
	}


	public List<Adjustment> getAdjustments() {
		return adjustments;
	}


	public void setAdjustments(List<Adjustment> adjustments) {
		this.adjustments = adjustments;
	}


	public List<Payment> getPayments() {
		return payments;
	}


	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
	
	
	
}
