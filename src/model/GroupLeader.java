package model;

import java.io.Serializable;

public class GroupLeader implements Serializable {
	private Integer id;
	private Integer id_booking;
	private Housed housed;
	private Integer id_housed;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_housed == null) ? 0 : id_housed.hashCode());
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
		GroupLeader other = (GroupLeader) obj;
		if (id_housed == null) {
			if (other.id_housed != null)
				return false;
		} else if (!id_housed.equals(other.id_housed))
			return false;
		return true;
	}
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