package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Room;
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
public class RoomAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Room room = null;
	private Message message = new Message();	
	
	
	@Actions({
		@Action(value="/addNewRoom",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String addNewRoom() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		String names = "";
		List<Room> rooms = this.splitRooms();
				
		for(Room each: rooms){
			if(structure.hasRoomNamed(each.getName())){
				names = names + "," + each.getName();
			}
		}
		
		if(names.length()>0){
			this.getMessage().setResult(Message.ERROR);
			String text = "Le stanze: " + names.substring(1) + " sono gia' presenti";
			this.getMessage().setDescription(text);
			return "error";			
		}	
		else{
			for(Room each: rooms){
				each.setId(structure.nextKey());
				structure.addRoom(each);
			}
			this.getMessage().setResult(Message.SUCCESS);
			String text = "Le stanze sono state inserite con successo";
			this.getMessage().setDescription(text);
			return "success";	
		}
	
	}
	
	private List<Room> splitRooms(){
		List<Room> rooms = new ArrayList<Room>();
		
		
		for(String each: this.getRoom().getName().split(",")){
			if(each.trim().length()>0){
				Room aRoom = new Room();
				aRoom.setName(each.trim());
				aRoom.setPrice(this.getRoom().getPrice());
				aRoom.setNotes(this.getRoom().getNotes());
				aRoom.setMaxGuests(this.getRoom().getMaxGuests());				
				aRoom.setRoomType(this.getRoom().getRoomType());
				rooms.add(aRoom);				
			}
		}		
		return rooms;
	}
	
	@Actions({
		@Action(value="/deleteRoom",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String deleteRoom() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		
		if(structure.deleteRoom(this.getRoom())){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("La stanza e' stata cancellata con successo");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non e' stato possiible cancellare la stanza");
			return "error";
		}
		
	}	
	
	@Actions({
		@Action(value="/updateRoom",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String updateRoom() {		
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();		
		String newName = this.getRoom().getName();
		
		if(newName.contains(",")){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Il nuovo nome non puo' contenere virgole");
			return "error";
		}
		
		Room originalRoom = structure.findRoomById(this.getRoom().getId());
		if(originalRoom == null){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Stai imbrogliando e lo stai facendo molto male");
			return "error";
		}
				
		if(!newName.equals(originalRoom.getName())){
			if(structure.findRoomByName(newName) != null){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("Stai usando un nome gia' esistente");
				return "error";
			}
		}
		originalRoom.setName(this.getRoom().getName());
		originalRoom.setMaxGuests(this.getRoom().getMaxGuests());
		originalRoom.setNotes(this.getRoom().getNotes());
		originalRoom.setPrice(this.getRoom().getPrice());
		originalRoom.setRoomType(this.getRoom().getRoomType());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("La stanza e' stata modificata con successo");
		return "success";		
	
	}	

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
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
