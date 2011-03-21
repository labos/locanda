package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Booking;
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
public class BookingAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Booking> bookings = null;
	private Booking booking = null;
	private Integer id;
	private Message message = new Message();
	
	@Actions({
		@Action(value="/findAllBookings",results = {
				@Result(name="success",location="/bookings.jsp")
		}),
		@Action(value="/findAllBookingsJson",results = {
				@Result(type ="json",name="success", params={
						"root","bookings"
				})}) 
		
	})
	public String findAllBookings(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		this.setBookings(structure.getBookings());
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/findBookingById",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,bookings,id"
				}) ,
				@Result(type ="json",name="error", params={
						"root","message"
				})}) 
		
	})
	
	
	public String findBookingById(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		for(Booking each: structure.getBookings()){
			if(each.getId().equals(this.getId())){
				this.setBooking(each);
				this.getMessage().setResult(Message.SUCCESS);
				return SUCCESS;
			}
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Booking not found!");
		return ERROR;
	}
	
	
	@Actions({
		@Action(value="/addNewBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} )
		})
		
	})
	public String addNewBooking(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		structure.addBooking(this.getBooking());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Booking Added successfully");
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

	


	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	

}
