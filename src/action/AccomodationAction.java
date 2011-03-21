package action;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class AccomodationAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Room> rooms = null;
	private Set<String> roomTypes = null;
	private List<RoomFacility> roomFacilities = null;
	
	
	@Actions({
		@Action(value="/findAllRoomTypes",results = {
				@Result(type ="json",name="success", params={
						"root","roomTypes"
				} )
		})
		
	})
	public String findAllRoomTypes() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRoomTypes(structure.findAllRoomTypes());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findAllRooms",results = {
				@Result(name="success",location="/accomodation.jsp")
		})
		
	})
	public String findAllRooms() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRooms(structure.getRooms());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goAddNewRoom",results = {
				@Result(name="success",location="/add_new.jsp")
		})
		
	})
	public String goAddNewRoom() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRoomFacilities(structure.getRoomFacilities());
		return SUCCESS;
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

	public Set<String> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(Set<String> roomTypes) {
		this.roomTypes = roomTypes;
	}

		

}
