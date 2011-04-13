package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Room;
import model.RoomFacility;
import model.Structure;
import model.User;
import model.internal.Message;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class RoomFacilityAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<RoomFacility> roomFacilities = null;
	private List<Integer> roomFacilitiesIds = new ArrayList<Integer>();
	private Integer idRoom;
	private Room room = null;
	private Message message = new Message();
	
	
	
	@Actions({
		@Action(value="/goUpdateRoomFacilities",results = {
				@Result(name="success",location="/roomFacilities_edit.jsp")
		})
	})
	
	public String goUpdateRoomFacilities() {
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRoomFacilities(structure.getRoomFacilities());		
		for(RoomFacility each: structure.findRoomById(this.idRoom).getFacilities()){			
			this.roomFacilitiesIds.add(each.getId());			
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/updateRoomFacilities", results={
				@Result(type ="json",name="success", params={
						"root","message"
				})					
			})
	})
	
	
	public String updateRoomFacilities() {
		User user = null;
		Structure structure = null;
		List<RoomFacility>  checkedFacilities = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRoom(structure.findRoomById(this.getIdRoom()));		
		checkedFacilities = structure.findFacilitiesByIds(this.getRoomFacilitiesIds());			
		this.room.updateRoomFacilities(checkedFacilities);
		this.getMessage().setResult(Message.SUCCESS);
		String text = "Facilities updated successfully";
		this.getMessage().setDescription(text);
		return SUCCESS;
	}
	
		
	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public List<RoomFacility> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(List<RoomFacility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}

	public Integer getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Integer> getRoomFacilitiesIds() {
		return roomFacilitiesIds;
	}

	public void setRoomFacilitiesIds(List<Integer> roomFacilitiesIds) {
		this.roomFacilitiesIds = roomFacilitiesIds;
	}	
	
	

}
