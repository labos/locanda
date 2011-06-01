package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Extra;
import model.Guest;
import model.Room;
import model.RoomFacility;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Convention;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.ExtraService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class OnlineBookingAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<RoomFacility> roomFacilities = null;
	private List<Room> rooms = null;
	private Booking booking = null;
	private Integer id;
	private Date dateArrival;
	private Integer numGuests = 1;
	private Integer numNight = 1;
	private List<Extra> extras;
	private List<Integer> bookingExtrasId = null;
	private Guest guest;
	private Integer roomId;
	@Autowired
	private ExtraService extraService = null;
	
	
	@Actions({
		@Action(value="/goOnlineBookingCalendar",results = {
				@Result(name="success",location="/jsp/online/widget1.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
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
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingRooms(){
		User user = null;
		Structure structure = null;
		List <Booking> toRemoveBookings = new ArrayList<Booking>();
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		List <Room> rooms = new ArrayList<Room>();
		
		rooms = structure.getRooms();
		this.setRooms(new ArrayList<Room>());
		//remove not completed booking from the memory
		
		for (Booking abooking : structure.getBookings()){
			
			if (! checkBookingIsValid(abooking) ){
			
				
				toRemoveBookings.add(abooking);
			
		}
			}
		
		structure.getBookings().removeAll(toRemoveBookings);
		
		for(Room each : rooms){
			
			if ( (each.getRoomType().getMaxGuests() >= this.getNumGuests() ) && structure.hasRoomFreeInPeriod(each.getId(), this.getDateArrival(), this.calculateDateOut()) ) 
			{
				each.setPrice( calculateTotalForBooking(each));
				this.getRooms().add(each);
			}
			
		}
		
/*		if(!structure.hasRoomFreeForBooking(this.getBooking())){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Booking sovrapposti!");
			return ERROR;
	}*/
		//-- this.setRooms(structure.getRooms());
		this.setRoomFacilities(structure.getRoomFacilities());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingExtras",results = {
				@Result(name="success",location="/jsp/online/widget3.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingExtras(){
		User user = null;
		Structure structure = null;
		Room theBookedRoom = null;
		Double roomSubtotal = 0.0;
		Convention defaultConvention = null;
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		theBookedRoom = structure.findRoomById(this.getRoomId());
		this.setBooking(new Booking());
		this.getBooking().setRoom(theBookedRoom);	
		this.setBooking(new Booking());
		this.getBooking().setId(structure.nextKey());
		this.getBooking().setRoom(theBookedRoom);
		this.getBooking().setDateIn(this.getDateArrival());
		this.getBooking().setDateOut(this.calculateDateOut());
		this.getBooking().setNrGuests(this.getNumGuests());
		defaultConvention = structure.getConventions().get(0);
		this.getBooking().setConvention(defaultConvention);
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		
		structure.addBooking(this.getBooking());
		
		//this.setRooms(structure.getRooms());
		this.setRoomFacilities(structure.getRoomFacilities());
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingGuest",results = {
				@Result(name="success",location="/jsp/online/widget4.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingGuest(){
		User user = null;
		Structure structure = null;
		List<Extra>  checkedExtras = null;
		Double extraSubtotal = 0.0;
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		//this.setRooms(structure.getRooms());
		this.setBooking(structure.findBookingById(this.getBooking().getId()));
		if (this.getBookingExtrasId() != null){
			
		//checkedExtras = structure.findExtrasByIds(this.getBookingExtrasId());	
		checkedExtras = this.getExtraService().findExtrasByIds(this.getBookingExtrasId());
		this.getBooking().setExtras(checkedExtras);	
		
		extraSubtotal = this.getBooking().calculateExtraSubtotalForBooking();
		this.getBooking().setExtraSubtotal(extraSubtotal);
		
		}

		structure.updateBooking(this.getBooking());
		this.setRoomFacilities(structure.getRoomFacilities());
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/goOnlineBookingFinal",results = {
				@Result(name="success",location="/jsp/online/widget5.jsp"),
				@Result(name="error",location="/jsp/online/validationError.jsp"),
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingFinal(){
		User user = null;
		Structure structure = null;
		Guest oldGuest = null;
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setBooking(structure.findBookingById(this.getBooking().getId()));
		try {
			
			
			oldGuest = structure.findGuestById(this.getGuest().getId());		
			if(oldGuest == null){
				//Si tratta di un nuovo guest e devo aggiungerlo
				this.getGuest().setId(structure.nextKey());
				structure.addGuest(guest);			
			}	
			
			
			this.getBooking().setBooker(this.getGuest());
			this.getBooking().setStatus("provisional");
			//check if current booking is valid to save
			if (checkBookingIsValid (this.getBooking())){
				
				structure.updateBooking(this.getBooking());
			}
			else {
				
				structure.deleteBooking(this.getBooking());
				addActionError("This booking cannot be saved");
				return ERROR;
				
				
			}
			
		}
		 catch(NullPointerException e) {
			 
				structure.deleteBooking(this.getBooking());
				addActionError("This booking cannot be saved");
				return ERROR;

		 }
		
		
		this.setRoomFacilities(structure.getRoomFacilities());
		return SUCCESS;
	}

	private Date calculateDateOut(){

		Date dateOut = null;
		
		if(( this.getNumNight()!=null ) && ( this.getDateArrival()!=null )){

			dateOut  = DateUtils.addDays(this.getDateArrival(), this.getNumNight() );	
			
		}		
		return dateOut;
	}
	
	
	private Double calculateTotalForBooking(Room aRoom){
		User user = null;
		Structure structure = null;
		Double subTotal = null;
		Booking aBooking = new Booking();
		Convention defaultConvention = null;
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		

		
		aBooking.setId(structure.nextKey());
		aBooking.setRoom(aRoom);
		aBooking.setDateIn(this.getDateArrival());
		aBooking.setDateOut(this.calculateDateOut());
		aBooking.setNrGuests(this.getNumGuests());
		defaultConvention = structure.getConventions().get(0);
		aBooking.setConvention(defaultConvention);
		subTotal = structure.calculateRoomSubtotalForBooking(aBooking);
		return subTotal;
		
	}
	
	private Boolean checkBookingIsValid (Booking booking){
		
	if(booking.getRoom() != null && booking.getId() != null && booking.getBooker() != null && booking.getDateIn() != null && booking.getDateOut() != null){
		return true;
	}
		
		return false;
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





	public Integer getNumNight() {
		return numNight;
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


	public Booking getBooking() {
		return booking;
	}


	public void setBooking(Booking booking) {
		this.booking = booking;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getNumGuests() {
		return numGuests;
	}


	public void setNumGuests(Integer numGuests) {
		this.numGuests = numGuests;
	}


	public void setNumNight(Integer numNight) {
		this.numNight = numNight;
	}


	public List<Integer> getBookingExtrasId() {
		return bookingExtrasId;
	}


	public void setBookingExtrasId(List<Integer> bookingExtrasId) {
		this.bookingExtrasId = bookingExtrasId;
	}


	public Integer getRoomId() {
		return roomId;
	}


	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}


	public ExtraService getExtraService() {
		return extraService;
	}


	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}
	
	
	

}
