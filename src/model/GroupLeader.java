package model;

import java.io.Serializable;

public class GroupLeader implements Serializable {
	private Integer id;
	private Integer id_booking;
	private Housed housed;
	private Integer id_housed;
	
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
	public Housed getHoused() {
		return housed;
	}
	public void setHoused(Housed housed) {
		this.housed = housed;
	}
	public Integer getId_housed() {
		return id_housed;
	}
	public void setId_housed(Integer id_housed) {
		this.id_housed = id_housed;
	}
	
}
