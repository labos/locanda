package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Extra;
import model.Guest;
import model.Room;
import model.RoomFacility;
import model.Structure;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class OnlineBookingAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<RoomFacility> roomFacilities = null;
	private List<Room> rooms = null;
	private  Date dateArrival;
	private Integer nrGuests = 1;
	private Integer numNights = 1;
	private List<Extra> extras;
	private Guest guest;
	
	
	@Actions({
		@Action(value="/goOnlineBookingCalendar",results = {
				@Result(name="success",location="/jsp/online/widget1.jsp"),
				
				@Result(name="input", location="/validationError.jsp")
		})
	})
	
	public String goOnlineBookingCalendar(){
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/goOnlineBookingRooms",results = {
				@Result(name="success",location="/jsp/online/widget2.jsp"),
				
				@Result(name="input", location="/validationError.jsp")
		})
	})
	
	public String goOnlineBookingRooms(){
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRooms(structure.getRooms());
		this.setRoomFacilities(structure.getRoomFacilities());
		return SUCCESS;
	}
	
	
	public List<Extra> getExtras() {
		return extras;
	}


	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}


	public Guest getGuest() {
		return guest;
	}


	public void setGuest(Guest guest) {
		this.guest = guest;
	}


	public Date getDateArrival() {
		return dateArrival;
	}


	public void setDateArrival(Date dateArrival) {
		this.dateArrival = dateArrival;
	}


	public Integer getNrGuests() {
		return nrGuests;
	}


	public void setNrGuests(Integer nrGuests) {
		this.nrGuests = nrGuests;
	}


	public Integer getNumNights() {
		return numNights;
	}


	public void setNumNights(Integer numNights) {
		this.numNights = numNights;
	}


	public List<RoomFacility> getRoomFacilities() {
		return roomFacilities;
	}


	public void setRoomFacilities(List<RoomFacility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}


	public List<Room> getRooms() {
		return rooms;
	}


	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}



	public Map<String, Object> getSession() {
		return session;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	
	

}
