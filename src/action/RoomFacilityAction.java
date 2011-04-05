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
	private List<Integer> facilities = new ArrayList<Integer>();
	private Integer idRoom;
	private Room room = null;
	private Message message = new Message();
	
	@Actions({
		@Action(value="/goRoomFacilities_edit",results = {
				@Result(name="success",location="/roomFacilities_edit.jsp")
		})
	})
	
	public String goRoomFacilities_edit() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		//setto la room corrente con l'idForm settato 
		room = structure.findRoomById(idRoom);
		//creo un oggetto vuoto di id di facilities
		this.facilities = new ArrayList<Integer>();
		this.setRoomFacilities(structure.getRoomFacilities());
		
		List <RoomFacility> currentRoom = structure.findRoomById(idRoom).getFacilities();
		
		for(RoomFacility each: currentRoom){
			
			facilities.add(each.getId());
			
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/roomFacilities_edit", results={
				@Result(type ="json",name="success", params={
						"root","message"
				})					
			})
	})
	
	
	public String roomFacilities_edit() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		room = structure.findRoomById(idRoom);
		List<RoomFacility>  checkedFacilities = new ArrayList<RoomFacility>();
		if(facilities != null)
		{
			checkedFacilities = structure.findFacilitiesByIds(facilities);	
		}
		
		room.setFacilities(new ArrayList<RoomFacility>());
		try{
			room.addRoomFacilities(checkedFacilities);
		}
		catch(Exception e)
			{
			return ERROR;
			}
		
		this.getMessage().setResult(Message.SUCCESS);
		String text = "Facilities added/modified successfully";
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

	public List<Integer> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<Integer> facilities) {
		this.facilities = facilities;
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
	
	
	
	

}
