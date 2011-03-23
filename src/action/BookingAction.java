package action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Guest;
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
public class BookingAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Booking> bookings = null;
	private Booking booking = null;
	private String dateIn = null;
	private Integer id;
	private Integer numNights;
	private List<Room> rooms = null;
	private Message message = new Message();
	
	
	
	
	@Actions({
		@Action(value="/goAddNewBooking",results = {
				@Result(name="success",location="/book.jsp")
		})
	})
	
	public String goAddNewBooking() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRooms(structure.getRooms());
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/goUpdateBooking",results = {
				@Result(name="success",location="/book.jsp")
		}),
		@Action(value="/goUpdateBookingFromPlanner",results = {
				@Result(name="success",location="/jsp/contents/booking_form.jsp")
		})
	})
	
	public String goUpdateBooking() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRooms(structure.getRooms());
		Booking aBooking = structure.findBookingById(this.getId());
		this.setBooking(aBooking);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		this.setDateIn(sdf.format(aBooking.getDateIn()));
		Long millis = aBooking.getDateOut().getTime() - aBooking.getDateIn().getTime();
		Integer days = (int) (millis/(1000*3600*24));
		this.setNumNights(days);
		return SUCCESS;
	}
	
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
		Booking aBooking = structure.findBookingById(this.getId());
		if(aBooking!=null){
			this.setBooking(aBooking);
			this.getMessage().setResult(Message.SUCCESS);
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Booking not found!");
		return ERROR;
	}
	
	
	@Actions({
		@Action(value="/addNewBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(name="input", location="/validationError.jsp")
		})
		
	})
	public String addNewBooking(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date dateOut = null;
		Long millis = null;
		
		try {
			
			this.getBooking().setDateIn(sdf.parse(this.getDateIn()));
			millis = this.getBooking().getDateIn().getTime() + 
					this.getNumNights() * 24 * 3600 * 1000;
			dateOut = new Date(millis);
			this.getBooking().setDateOut(dateOut);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getBooking().setId(structure.nextKey());
				
		Room aRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(aRoom);
		
		Integer idGuest = this.getBooking().getGuest().getId();
		Guest guest = structure.findGuestById(idGuest);
		
		if(guest == null){
			//si tratta di un nuovo guest e devo aggiungerlo
			this.getBooking().getGuest().setId(structure.nextKey());
			structure.addGuest(this.getBooking().getGuest());
			
		}else{
			//si tratta di un guest esistente e devo fare l'update
			structure.updateGuest(this.getBooking().getGuest());
			
		}
		structure.addBooking(this.getBooking());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Booking Added successfully");
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/addOrUpdateBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(name="input", location="/validationError.jsp")
		})
		
	})
	public String addOrUpdateBooking(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date dateOut = null;
		Long millis = null;
		
		try {
			
			this.getBooking().setDateIn(sdf.parse(this.getDateIn()));
			millis = this.getBooking().getDateIn().getTime() + 
					this.getNumNights() * 24 * 3600 * 1000;
			dateOut = new Date(millis);
			this.getBooking().setDateOut(dateOut);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getBooking().setId(structure.nextKey());
				
		Room aRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(aRoom);
		
		Integer idGuest = this.getBooking().getGuest().getId();
		Guest guest = structure.findGuestById(idGuest);
		
		if(guest == null){
			//si tratta di un nuovo guest e devo aggiungerlo
			this.getBooking().getGuest().setId(structure.nextKey());
			structure.addGuest(this.getBooking().getGuest());
			
		}else{
			//si tratta di un guest esistente e devo fare l'update
			structure.updateGuest(this.getBooking().getGuest());
			
		}
		structure.addBooking(this.getBooking());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Booking Added successfully");
		return SUCCESS;
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

	public String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}


	public Integer getNumNights() {
		return numNights;
	}

	public void setNumNights(Integer numNights) {
		this.numNights = numNights;
	}




	public List<Room> getRooms() {
		return rooms;
	}




	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	
	

}
