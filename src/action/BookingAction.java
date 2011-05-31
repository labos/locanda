package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Adjustment;
import model.BookedExtraItem;
import model.Booking;
import model.Extra;
import model.Guest;
import model.Payment;
import model.Room;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Convention;

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
	private List<Convention> conventions = null;
	private List<Integer> bookingExtraIds = new ArrayList<Integer>();
	private Double adjustmentsSubtotal = 0.0;
	private Double paymentsSubtotal = 0.0;
	
	
	@Actions({
		@Action(value="/updateBookingInMemory",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String updateBookingInMemory() {
		User user = null; 
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		Structure structure = null; 
		Room theBookedRoom = null;
		List<Extra> checkedExtras = null;
		Integer numNights;
		List<BookedExtraItem> bookedExtraItems = null;
						
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		//Update Booking in memory
		if(!this.getBooking().checkDates()){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("DateOut deve essere maggiore di DateIn!");
			return "error";
		}
		
		theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		
		if(!structure.hasRoomFreeForBooking(this.getBooking())){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Booking sovrapposti!");
			return ERROR;
		}	
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		if (this.getBooking().getNrGuests() > theBookedRoom.getRoomType().getMaxGuests()) {	//nel caso cambiassi la room con preselezionato un nrGuests superiore al maxGuests della room stessa
			this.getBooking().setNrGuests(theBookedRoom.getRoomType().getMaxGuests());
		}
		
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());		
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		checkedExtras = structure.findExtrasByIds(this.getBookingExtraIds());
		this.getBooking().setExtras(checkedExtras);		
		
		bookedExtraItems = this.calculateBookedExtraItems(structure, this.getBooking());
		this.getBooking().setExtraItems(bookedExtraItems);
		
			
		extraSubtotal = this.getBooking().calculateExtraSubtotalForBooking();
		this.getBooking().setExtraSubtotal(extraSubtotal);			

		this.filterAdjustments();
		this.filterPayments();
		this.filterGuests();
			
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Prezzo Calcolato con Successo");
		return "success";	
					
	}
	
	
	@Actions({
		@Action(value="/saveUpdateBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String saveUpdateBooking(){
		User user = null;
		Structure structure = null;
		Booking oldBooking = null;
		Room theBookedRoom = null;
		Guest guest = null;
		Guest oldGuest = null;
		List<Extra>  checkedExtras = null;
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		List<BookedExtraItem> bookedExtraItems = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		
		//Update Booking in memory
		if(!this.getBooking().checkDates()){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("DateOut deve essere maggiore di DateIn!");
			return "error";
		}
		
		theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		
		if(!structure.hasRoomFreeForBooking(this.getBooking())){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Booking sovrapposti!");
			return ERROR;
		}	
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		if (this.getBooking().getNrGuests() > theBookedRoom.getRoomType().getMaxGuests()) {	//nel caso cambiassi la room con preselezionato un nrGuests superiore al maxGuests della room stessa
			this.getBooking().setNrGuests(theBookedRoom.getRoomType().getMaxGuests());
		}
		
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());		
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		checkedExtras = structure.findExtrasByIds(this.getBookingExtraIds());
		this.getBooking().setExtras(checkedExtras);		
		
		bookedExtraItems = this.calculateBookedExtraItems(structure, this.getBooking());
		this.getBooking().setExtraItems(bookedExtraItems);
		
			
		extraSubtotal = this.getBooking().calculateExtraSubtotalForBooking();
		this.getBooking().setExtraSubtotal(extraSubtotal);			

		this.filterAdjustments();
		this.filterPayments();
		this.filterGuests();
		
		//Persist the Booking
		oldBooking = structure.findBookingById(this.getBooking().getId());
		if(oldBooking==null){
			//Si tratta di un nuovo booking
			this.getBooking().setId(structure.nextKey());
			structure.addBooking(this.getBooking());
		}else{
			//Si tratta di un update di un booking esistente			
			structure.updateBooking(this.getBooking());		
		}	
		
		guest = this.getBooking().getBooker();
		oldGuest = structure.findGuestById(guest.getId());		
		if(oldGuest == null){
			//Si tratta di un nuovo guest e devo aggiungerlo
			guest.setId(structure.nextKey());
			structure.addGuest(guest);			
		}else{
			//Si tratta di un guest esistente e devo fare l'update
			structure.updateGuest(guest);			
		}
		
		for(Guest each: this.getBooking().getGuests()){
			if(each.getId()== null){
				each.setId(structure.nextKey());
			}
		}	
		
		for(Payment each: this.getBooking().getPayments()){
			if(each.getId()== null){
				each.setId(structure.nextKey());
			}
		}	
		
		for(Adjustment each: this.getBooking().getAdjustments()){
			if(each.getId()==null){
				each.setId(structure.nextKey());
			}
		}
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Booking added/modified successfully");
		return SUCCESS;
	}
	
	
	private List<BookedExtraItem> calculateBookedExtraItems(Structure structure, Booking booking){
		BookedExtraItem bookedExtraItem = null;
		List<BookedExtraItem> bookedExtraItems = null;
		
		bookedExtraItems = new ArrayList<BookedExtraItem>();
		for(Extra each: booking.getExtras()){
			bookedExtraItem = booking.findExtraItem(each);
			if(bookedExtraItem==null){
				bookedExtraItem = new BookedExtraItem();
				bookedExtraItem.setExtra(each);
				bookedExtraItem.setQuantity(booking.calculateExtraItemMaxQuantity(each));
				bookedExtraItem.setUnitaryPrice(booking.calculateExtraItemUnitaryPrice(structure, each));				
			}else{
				bookedExtraItem.setUnitaryPrice(booking.calculateExtraItemUnitaryPrice(structure, each));	
			}
			bookedExtraItems.add(bookedExtraItem);
		}
		return bookedExtraItems;
	}
	
	@Actions({
		@Action(value="/goAddBookingFromPlanner",results = {
				@Result(name="success",location="/jsp/contents/booking_form.jsp"),
				
				@Result(name="input", location="/validationError.jsp")
		})
	})
	public String goAddNewBookingFromPlanner() {
		User user = null;
		Structure structure = null;
		Room theBookedRoom = null;
		Convention defaultConvention = null;
		Integer numNights = 0;
		Double roomSubtotal = 0.0;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		
		defaultConvention = structure.getConventions().get(0);
		this.getBooking().setConvention(defaultConvention);
		this.setRooms(structure.getRooms());
		this.setExtras(structure.getExtras());
		this.setConventions(structure.getConventions());
		
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goAddNewBooking",results = {
				@Result(name="success",location="/book.jsp")
		})
	})
	public String goAddNewBooking() {
		User user = null;
		Structure structure = null;
		Convention defaultConvention = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		
		this.setRooms(structure.getRooms());
		this.setExtras(structure.getExtras());
		this.setConventions(structure.getConventions());
		this.setBooking(new Booking());
		defaultConvention = structure.getConventions().get(0);
		this.getBooking().setConvention(defaultConvention);
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
		User user = null;
		Structure structure = null;
		Booking oldBooking = null;
		Integer numNights = 0;
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		Double adjustmentsSubtotal = 0.0;
		Double paymentsSubtotal = 0.0;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		oldBooking = structure.findBookingById(this.getId());
		this.setBooking(oldBooking);
		
		roomSubtotal = structure.calculateRoomSubtotalForBooking(oldBooking);
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		extraSubtotal = this.getBooking().calculateExtraSubtotalForBooking();
		this.getBooking().setExtraSubtotal(extraSubtotal);
		
				
		this.setRooms(structure.getRooms());
		this.setExtras(structure.getExtras());		
		this.setBookingExtraIds(this.calculateBookingExtraIds());
		this.setConventions(structure.getConventions());		
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		adjustmentsSubtotal = this.getBooking().calculateAdjustmentsSubtotal();
		this.setAdjustmentsSubtotal(adjustmentsSubtotal);
		
		paymentsSubtotal = this.getBooking().calculatePaymentsSubtotal();
		this.setPaymentsSubtotal(paymentsSubtotal);
		
		return SUCCESS;
	}
	
	private List<Integer> calculateBookingExtraIds(){
		List<Integer> ret = null;
		
		ret = new ArrayList<Integer>();
		for(Extra each: this.getBooking().getExtras()){
			ret.add(each.getId());
		}
		return ret;
	}
	
	@Actions({
		@Action(value="/findAllBookings",results = {
				@Result(name="success",location="/bookings.jsp")
		}),
		@Action(value="/findAllBookingsJson",results = {
				@Result(type ="json",name="success", params={
						"root","bookings"
				})
		}) 
	})
	public String findAllBookings(){
		User user = null;
		Structure structure = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		this.setBookings(structure.getBookings());
		return SUCCESS;		
	}	
	
	/*
	@Actions({
		@Action(value="/findBookingById",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,bookings,id"
				}) ,
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String findBookingById(){
		User user = null;
		Structure structure = null;
		Booking aBooking = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		aBooking = structure.findBookingById(this.getId());
		if(aBooking!=null){
			this.setBooking(aBooking);
			this.getMessage().setResult(Message.SUCCESS);
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Booking not found!");
		return ERROR;
	}*/
	
	
	
	@Actions({
		@Action(value="/checkBookingDates",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String checkBookingDates(){
		User user = null;
		Structure structure = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();		
		if(!structure.hasRoomFreeForBooking(this.getBooking())){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("Booking sovrapposti!");
				return ERROR;
		}
		this.getMessage().setResult(Message.SUCCESS);
		
		this.getMessage().setDescription("Booking Dates OK!");
		return SUCCESS;
	}	
	
	private void filterAdjustments(){
		List<Adjustment> adjustmentsWithoutNulls = null;
		
		adjustmentsWithoutNulls = new ArrayList<Adjustment>();
		
		for(Adjustment each: this.getBooking().getAdjustments()){
			if(each!=null){
				adjustmentsWithoutNulls.add(each);
			}			
		}
		this.getBooking().setAdjustments(adjustmentsWithoutNulls);
		
	}
	
	private void filterPayments(){
		List<Payment> paymentsWithoutNulls = null;
		
		paymentsWithoutNulls = new ArrayList<Payment>();
		
		for(Payment each: this.getBooking().getPayments()){
			if(each!=null){
				paymentsWithoutNulls.add(each);
			}			
		}
		this.getBooking().setPayments(paymentsWithoutNulls);		
	}
	
	private void filterGuests(){
		List<Guest> guestWithoutNulls = null;
		
		guestWithoutNulls = new ArrayList<Guest>();
		
		for(Guest each: this.getBooking().getGuests()){
			if(each!=null){
				guestWithoutNulls.add(each);
			}			
		}
		this.getBooking().setGuests(guestWithoutNulls);
		
	}
	
	@Actions({
		@Action(value="/deleteBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String deleteBooking() {
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		structure = user.getStructure();
		
		if(structure.deleteBooking(this.getBooking())){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Booking deleted successfully");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error: booking not deleted");
			return "error";
		}		
	}	
	
	@Actions({
		@Action(value="/checkInBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String checkInBooking() {
		User user = null;
		Structure structure = null;
		Booking aBooking = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		aBooking = structure.findBookingById(this.getId());
		if(aBooking!=null){
			aBooking.setStatus("checkin");
			structure.updateBooking(aBooking);		
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Booking checked In successfully");
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Booking not found!");
		return ERROR;	
	}
	
	@Actions({
		@Action(value="/checkOutBooking",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String checkOutBooking() {
		User user = null;
		Structure structure = null;
		Booking aBooking = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		aBooking = structure.findBookingById(this.getId());
		if(aBooking!=null){
			aBooking.setStatus("checkout");
			structure.updateBooking(aBooking);		
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Booking checked Out successfully");
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription("Booking not found!");
		return ERROR;	
	}
	
	@Actions({
		@Action(value="/goOnlineBookings",results = {
				@Result(name="success",location="/onlineBookings.jsp")
		}) 	
	})
	public String goOnlineBookings(){
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
	public Double getAdjustmentsSubtotal() {
		return adjustmentsSubtotal;
	}
	public void setAdjustmentsSubtotal(Double adjustmentsSubtotal) {
		this.adjustmentsSubtotal = adjustmentsSubtotal;
	}
	public Double getPaymentsSubtotal() {
		return paymentsSubtotal;
	}
	public void setPaymentsSubtotal(Double paymentsSubtotal) {
		this.paymentsSubtotal = paymentsSubtotal;
	}
	public List<Convention> getConventions() {
		return conventions;
	}
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	
}
