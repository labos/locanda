package action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Booking;
import model.Extra;
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
	private String dateOut = null;
	private List<Extra> extras = null;
	private List<Integer> bookingExtraIds = new ArrayList<Integer>();
	
	
	@Actions({
		@Action(value="/goAddNewBooking",results = {
				@Result(name="success",location="/book.jsp")
		}),
		@Action(value="/goAddBookingFromPlanner",results = {
				@Result(name="success",location="/jsp/contents/booking_form.jsp"),
				
				@Result(name="input", location="/validationError.jsp")
		})
	})
	
	public String goAddNewBooking() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Room theBookedRoom = structure.findRoomById(this.getBooking().getId());
		this.getBooking().setRoom(theBookedRoom);
		this.setRooms(structure.getRooms());
		this.setExtras(structure.getExtras());
		Long millis = this.getBooking().getDateOut().getTime() - this.getBooking().getDateIn().getTime();
		Integer days = (int) (millis/(1000*3600*24));
		this.setNumNights(days);
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
		this.setExtras(structure.getExtras());
				
		// popolo bookingExtras con gli id degli extra già presenti nel booking
		for(Extra each: aBooking.getExtras()){
			bookingExtraIds.add(each.getId());
		}
		
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//this.setDateIn(sdf.format(aBooking.getDateIn()));
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
		@Action(value="/saveUpdateBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	public String saveUpdateBooking(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		
		if(!structure.hasRoomFreeInPeriod(this.getBooking().getRoom().getId(),this.getBooking().getDateIn(), this.getBooking().getDateOut())){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Booking sovrapposti!");
			return ERROR;
		}
		this.saveUpdateBookingRoom(structure);		
		this.saveUpdateBookingGuest(this.getBooking().getGuest(), structure);
		
		Booking oldBooking = 
			structure.findBookingById(this.getBooking().getId());
		if(oldBooking==null){
			//Si tratta di un nuovo booking
			this.getBooking().setId(structure.nextKey());
			this.saveUpdateBookingExtras(bookingExtraIds, structure);
			structure.addBooking(this.getBooking());
		}else{
			//Si tratta di un update di un booking esistente
			this.saveUpdateBookingExtras(bookingExtraIds, structure);
			structure.updateBooking(this.getBooking());			
		}		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Booking added/modified successfully");
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/checkBookingDates",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	public String checkBookingDates(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		
		if(!structure.hasRoomFreeInPeriod(this.getBooking().getRoom().getId(),this.getBooking().getDateIn(), this.getBooking().getDateOut())){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("Booking sovrapposti!");
				return ERROR;
		}
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Booking Dates OK!");
		return SUCCESS;
	}
	
	
	private Boolean saveUpdateBookingRoom(Structure structure){
		Room theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		return true;
	}
	
	
	private Boolean saveUpdateBookingGuest(Guest guest, Structure structure){ 
		Guest oldGuest = structure.findGuestById(guest.getId());
		
		if(oldGuest == null){
			//Si tratta di un nuovo guest e devo aggiungerlo
			guest.setId(structure.nextKey());
			structure.addGuest(guest);
			
		}else{
			//Si tratta di un guest esistente e devo fare l'update
			structure.updateGuest(guest);
			
		}		
		
		return true;
	}
	
	private Boolean saveUpdateBookingExtras(List<Integer> extras, Structure structure){ 
		List<Extra>  checkedExtras = new ArrayList<Extra>();
		checkedExtras = structure.findExtrasByIds(extras);		// popolo checkedExtras con gli extra checkati
		
		this.getBooking().setExtras(new ArrayList<Extra>());	// azzero l'array di extra del booking
		this.getBooking().addExtras(checkedExtras);				// popolo l'array di extra del booking con gli extra checkati
		return true;
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

	public String getDateOut() {
		return dateOut;
	}

	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}

	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}


	public List<Integer> getBookingExtraIds() {
		return bookingExtraIds;
	}


	public void setBookingExtraIds(List<Integer> bookingExtraIds) {
		this.bookingExtraIds = bookingExtraIds;
	}


	

	

}
