package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Extra;
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
	private List<Booking> bookings = null;
	private String term;
	
	@Actions({
		@Action(value="/findAllGuests",results = {
				@Result(name="success",location="/guests.jsp")
		}) 
		
	})
	public String findAllGuests(){
		User user = null;
		Structure structure = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		this.setGuests(structure.getGuests());
		return SUCCESS;		
	}
	
	
	@Actions({

		@Action(value="/findAllGuestsJson",results = {
				@Result(type ="json",name="success", params={
						"root","guests"
				})}) 
		
	})
	public String findAllGuestsFiltered(){
		User user = null;
		Structure structure = null;
		List <Guest> allGuests = null;
		List <Guest> returnedGuests = new ArrayList<Guest>();
		user = (User)session.get("user");
		structure = user.getStructure();
		
		
		   if (this.getTerm()!= null && this.getTerm().length() > 1)
		    {
			   allGuests = structure.getGuests();
		      
		      for (Guest guest: allGuests)
		      {
		        if ( guest.getLastName().toLowerCase().contains(this.getTerm().toLowerCase()))
		        {
		        	returnedGuests.add(guest);
		        }
		      }
		      
		    }
		   this.setGuests(returnedGuests);
		
		
		
		return SUCCESS;		
	}
	
	
	@Actions({
		@Action(value="/findAllGuestsByName",results = {
				@Result(name="success",location="/guests.jsp")
		}) 
		
	})
	public String findAllGuestsByName(){
		User user = null;
		Structure structure = null;
		List <Guest> allGuests = null;
		List <Guest> returnedGuests = new ArrayList<Guest>();
		user = (User)session.get("user");
		structure = user.getStructure();
		
		
		   if (this.getTerm()!= null && this.getTerm().length() > 1)
		    {
			   allGuests = structure.getGuests();
		      
		      for (Guest guest: allGuests)
		      {
		    	 String allName =  guest.getFirstName().toLowerCase() + guest.getLastName().toLowerCase();
		        if ( allName.equals(this.getTerm().toLowerCase() ))
		        {
		        	returnedGuests.add(guest);
		        }
		      }
		      
		    }
		   this.setGuests(returnedGuests);
		
		
		
		return SUCCESS;		
	}
	
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
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
		User user = null;
		Structure structure = null;
		Guest aGuest = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		aGuest = structure.findGuestById(this.getId());
		if(aGuest != null){
			this.setGuest(aGuest);
			this.getMessage().setResult(Message.SUCCESS);
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Guest not found!");
		return ERROR;
	}
	
	@Actions({
		@Action(value="/goUpdateGuest",results = {
				@Result(name="success",location="/guest_edit.jsp")
		})
		
	})
	public String goUpdateGuest() {
		User user = null;
		Structure structure = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		this.setGuest(structure.findGuestById(this.getId())); 
		this.setBookings(structure.findBookingsByGuestId(this.getId()));
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/saveUpdateGuest",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} )
		})
		
	})
	public String saveUpdateGuest(){
		User user = null;
		Structure structure = null;
		Guest oldGuest = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		
		oldGuest = structure.findGuestById(this.getGuest().getId());
		if(oldGuest == null){
			//Si tratta di una aggiunta
			this.getGuest().setId(structure.nextKey());
			structure.addGuest(this.getGuest());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Guest added successfully");
			
		}else{
			//Si tratta di un update
			structure.updateGuest(this.getGuest());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Guest updated successfully");
		}
		return SUCCESS;		
	}
	
	
	
	
	@Actions({
		@Action(value="/deleteGuest",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} )
		})
		
	})
	public String deleteGuest(){
		User user = null;
		Structure structure = null;
		Guest currentGuest = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		currentGuest = structure.findGuestById(this.getId());
		if(structure.deleteGuest(currentGuest)){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Guest removed successfully");
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error deleting guest");
			return ERROR;
		}
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

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	

}
