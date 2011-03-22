package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Guest;
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
public class GuestAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Guest> guests = null;
	private Guest guest = null;
	private Integer id;
	private Message message = new Message();
	
	@Actions({
		@Action(value="/findAllGuests",results = {
				@Result(name="success",location="/guests.jsp")
		}),
		@Action(value="/findAllGuestsJson",results = {
				@Result(type ="json",name="success", params={
						"root","guests"
				})}) 
		
	})
	public String findAllGuests(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		this.setGuests(structure.getGuests());
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/findGuestById",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,guests,id"
				}) ,
				@Result(type ="json",name="error", params={
						"root","message"
				})}) 
		
	})
	
	
	public String findGuestById(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		for(Guest each: structure.getGuests()){
			if(each.getId().equals(this.getId())){
				this.setGuest(each);
				this.getMessage().setResult(Message.SUCCESS);
				return SUCCESS;
			}
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Guest not found!");
		return ERROR;
	}
	
	
	@Actions({
		@Action(value="/goAddNewGuest",results = {
				@Result(name="success",location="/guest_new.jsp")
		})
		
	})
	public String goAddNewGuest() {
		
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/addNewGuest",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} )
		})
		
	})
	public String addNewGuest(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		structure.addGuest(this.getGuest());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Guest Added successfully");
		return "SUCCESS";
	}
	

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	
	

}
