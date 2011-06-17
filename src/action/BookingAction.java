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
import model.RoomType;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Convention;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
import service.ConventionService;
import service.ExtraService;
import service.GuestService;
import service.RoomService;
import service.StructureService;
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
	private Integer idStructure;
	@Autowired
	private ExtraService extraService = null;
	@Autowired
	private GuestService guestService = null;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private BookingService bookingService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private ConventionService conventionService = null;
	
	
	@Actions({
		@Action(value="/updateBookingInMemory",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String updateBookingInMemory() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();		
		//Update Booking in memory			
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}		
		this.updateBookingInMemory(structure);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	public void updateBookingInMemory(Structure structure) {
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		List<Extra> checkedExtras = null;
		Integer numNights;
		Integer numGuests;
		List<BookedExtraItem> bookedExtraItems = null;
		Room theBookedRoom = null;
						
		//update room	
		theBookedRoom = this.getRoomService().findRoomById(structure,this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		
		//update numNights
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		//num guests
		numGuests = this.getBooking().getNrGuests();
		if (numGuests > this.getBooking().getRoom().getRoomType().getMaxGuests()) {	//nel caso cambiassi la room con preselezionato un nrGuests superiore al maxGuests della room stessa
			numGuests = this.getBooking().getRoom().getRoomType().getMaxGuests();
			this.getBooking().setNrGuests(numGuests);
		}
		
		//room subtotal
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,this.getBooking());
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		//checked extras 
		checkedExtras = this.getExtraService().findExtrasByIds(this.getBookingExtraIds());
		this.getBooking().setExtras(checkedExtras);		
		
		//booked extra items
		
		bookedExtraItems = this.calculateBookedExtraItems(structure, this.getBooking());
		this.getBooking().setExtraItems(bookedExtraItems);		
		
		//extra subtotal
		extraSubtotal = this.getBooking().calculateExtraSubtotalForBooking();
		this.getBooking().setExtraSubtotal(extraSubtotal);			

		//Adjustments
		this.filterAdjustments();
		//Payments
		this.filterPayments();
		//Guests
		this.filterGuests();	
		this.getSession().put("booking", this.getBooking());
	}
	
	
	@Actions({
		@Action(value="/displayQuantitySelect",results = {
				@Result(name="success",location="/jsp/contents/extraQuantity_select.jsp")
				})
	})
	public String displayQuantitySelect() {
		User user = null;
		Structure structure = null;
			
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
			
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));
		this.setBooking((Booking) this.getSession().get("booking"));
		return SUCCESS;		
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
		Guest guest = null;
		Guest oldGuest = null;
				
		user = (User)session.get("user");
		structure = user.getStructure();		
		
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}		
		
		this.updateBookingInMemory(structure);
		
		//Persist the Booking
		oldBooking = this.getBookingService().findBookingById(structure, this.getBooking().getId());
	
		if(oldBooking==null){
			//Si tratta di un nuovo booking
			this.getBooking().setId(structure.nextKey());
			this.getBookingService().insertBooking(structure, this.getBooking());
		}else{
			//Si tratta di un update di un booking esistente			
			this.getBookingService().updateBooking(structure, this.getBooking());
		}	
		
		guest = this.getBooking().getBooker();
		guest.setId_structure(structure.getId());
		oldGuest = this.getGuestService().findGuestById(guest.getId());
		if(oldGuest == null){
			//Si tratta di un nuovo guest e devo aggiungerlo
			this.getGuestService().insertGuest(guest);
		}else{
			//Si tratta di un guest esistente e devo fare l'update
			this.getGuestService().updateGuest(guest);
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
		
		for (BookedExtraItem each : this.getBooking().getExtraItems()) {
			if (each.getId() == null) {
				each.setId(structure.nextKey());
			}
		}
		
		//Salvare anche la convenzione
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("bookingAddUpdateSuccessAction"));
		return SUCCESS;
	}
	
	
	private List<BookedExtraItem> calculateBookedExtraItems(Structure structure, Booking booking){
		BookedExtraItem bookedExtraItem = null;
		List<BookedExtraItem> bookedExtraItems = null;
				
		bookedExtraItems = new ArrayList<BookedExtraItem>();
		if(booking.getDateIn()!=null && booking.getDateOut()!=null){
			for(Extra each: booking.getExtras()){
				bookedExtraItem = booking.findExtraItem(each);
				if(bookedExtraItem==null){
					bookedExtraItem = new BookedExtraItem();
					bookedExtraItem.setExtra(each);
					bookedExtraItem.setQuantity(booking.calculateExtraItemMaxQuantity(each));
					bookedExtraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));
					bookedExtraItem.setUnitaryPrice(
							this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each));
					
				}else{
					bookedExtraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));
					bookedExtraItem.setUnitaryPrice(
							this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each));	
				}
				bookedExtraItems.add(bookedExtraItem);
			}
			
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
		Booking booking = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		booking = new Booking();
		this.getSession().put("booking", booking);
		
		booking.setDateIn(this.getBooking().getDateIn());
		booking.setDateOut(this.getBooking().getDateOut());		
		
		theBookedRoom = this.getRoomService().findRoomById(structure,this.getBooking().getRoom().getId());
		booking.setRoom(theBookedRoom);
		
		defaultConvention = this.getConventionService().findConventionsByIdStructure(structure).get(0);
		booking.setConvention(defaultConvention);
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
		booking.setRoomSubtotal(roomSubtotal);
		
		this.setBooking(booking);
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(structure));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));
		this.setConventions(this.getConventionService().findConventionsByIdStructure(structure));
				
		numNights = booking.calculateNumNights();
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
		Booking booking = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		booking = new Booking();
		this.getSession().put("booking", booking);
						
		defaultConvention = this.getConventionService().findConventionsByIdStructure(structure).get(0);
		booking.setConvention(defaultConvention);	
		this.setBooking(booking);
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(structure));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));
		this.setConventions(this.getConventionService().findConventionsByIdStructure(structure));		
		
			
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
		Booking booking = null;
		Integer numNights = 0;
		Double adjustmentsSubtotal = 0.0;
		Double paymentsSubtotal = 0.0;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		booking = this.getBookingService().findBookingById(structure, this.getId());
		this.getSession().put("booking", booking);
		
		this.setBooking(booking);
						
		this.setRooms(this.getRoomService().findRoomsByIdStructure(structure));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));		
		this.setBookingExtraIds(this.calculateBookingExtraIds());
		this.setConventions(this.getConventionService().findConventionsByIdStructure(structure));		
		
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
		this.setBookings(this.getBookingService().findBookingsByIdStructure(structure));
		return SUCCESS;		
	}	

	
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
		
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}
		return SUCCESS;
				
	}
	
	private Boolean checkBookingDates(Structure structure) {
		
		if(this.getBooking().getDateIn()!=null && this.getBooking().getDateOut()!=null){
			if(!this.getBooking().checkDates()){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("dateOutMoreDateInAction"));
				return false;
			}
			if ((this.getBooking().getRoom().getId()!=null) && (!this.getStructureService().hasRoomFreeForBooking(structure,this.getBooking()))) {
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("bookingOverlappedAction"));
				return false;
			}			
		}		
		this.getMessage().setDescription(getText("bookingDatesOK"));
		this.getMessage().setResult(Message.SUCCESS);
		return true;
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
		structure = user.getStructure();
		
		if(this.getBookingService().deleteBooking(structure,this.getBooking())>0 ){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("bookingDeleteSuccessAction"));
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("bookingDeleteErrorAction"));
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
		
		aBooking = this.getBookingService().findBookingById(structure, this.getId());
		if(aBooking!=null){
			aBooking.setStatus("checkedin");
			this.getBookingService().updateBooking(structure, aBooking);
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("bookingCheckInSuccessAction"));
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription(getText("bookingNotFoundAction"));
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
		
		aBooking = this.getBookingService().findBookingById(structure, this.getId());
		if(aBooking!=null){
			aBooking.setStatus("checkedout");
			this.getBookingService().updateBooking(structure, aBooking);
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("bookingCheckOutSuccessAction"));
			return SUCCESS;
		}
		this.getMessage().setResult(Message.ERROR);
		this.getMessage().setDescription(getText("bookingNotFoundAction"));
		return ERROR;	
	}
	
	@Actions({
		@Action(value="/goOnlineBookings",results = {
				@Result(name="success",location="/onlineBookings.jsp")
		}) 	
	})
	public String goOnlineBookings(){
		User user = null;
		Structure structure = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		this.setIdStructure(structure.getId());
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


	public Integer getIdStructure() {
		return idStructure;
	}

	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}

	public ExtraService getExtraService() {
		return extraService;
	}


	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}


	public GuestService getGuestService() {
		return guestService;
	}


	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}

	public StructureService getStructureService() {
		return structureService;
	}

	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public ConventionService getConventionService() {
		return conventionService;
	}

	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}
	
	
	//Evento updateBookingDates (cambia dateIn || cambia dateOut || cambia numNights)
		//-ricalcola prezzo room
		//-ricalcola prezzo unitario extra 
		//-ricalcola quantità massima extra items
	
	
	@Actions({
		@Action(value="/updateBookingDates",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String updateBookingDates() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();		
				
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}		
		this.updateBookingDates(structure);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	public void updateBookingDates(Structure structure) {
		Integer numNights;
		Booking booking = null;
		
		booking  = (Booking) this.getSession().get("booking");
		
		booking.setDateIn(this.getBooking().getDateIn());
		booking.setDateOut(this.getBooking().getDateOut());
		
		numNights = booking.calculateNumNights();
		this.setNumNights(numNights);
		
		//roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
		//booking.setRoomSubtotal(roomSubtotal);
		this.updateRoomSubtotal(structure, booking);
		
		this.updateUnitaryPriceInBookedExtraItems(structure, booking);
		this.updateMaxQuantityInBookedExtraItems(structure, booking);
		
		//extra subtotal
		//extraSubtotal = booking.calculateExtraSubtotalForBooking();
		//booking.setExtraSubtotal(extraSubtotal);	
		booking.updateExtraSubtotal();

		this.setBooking(booking);
	}
	
	
	//Evento updateRoom (cambia room)
		//Se la categoria della camera cambia allora:
			//-ricalcola prezzo room
			//-ricalcola prezzo unitario extra 	items
	
	@Actions({
		@Action(value="/updateRoom",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String updateRoom() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();		
				
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}		
		this.updateRoom(structure);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	public void updateRoom(Structure structure) {
		Double roomSubtotal = 0.0;
		Integer numGuests;
		Room theBookedRoom = null;
		Booking booking = null;	
		RoomType oldRoomType = null;
		RoomType newRoomType = null;
		
		booking  = (Booking) this.getSession().get("booking");
		
		if(booking.getRoom()!=null){
			oldRoomType = booking.getRoom().getRoomType();
		}
		
		theBookedRoom = this.getRoomService().findRoomById(structure,this.getBooking().getRoom().getId());
		booking.setRoom(theBookedRoom);		
		
		if(theBookedRoom!=null){
			newRoomType = theBookedRoom.getRoomType();
		}
		
		//Se cambia la categoria allora devo aggiornare i prezzi
		if((oldRoomType!= null) && (newRoomType!= null) && (!oldRoomType.equals(newRoomType) )){
			numGuests = booking.getNrGuests();
			if (numGuests > newRoomType.getMaxGuests()) {	//nel caso cambiassi la room con preselezionato un nrGuests superiore al maxGuests della room stessa
				numGuests = newRoomType.getMaxGuests();
				booking.setNrGuests(numGuests);
				//Se cambia il numero di guest devo aggiornare anche la quantità massima degli extra item
				this.updateMaxQuantityInBookedExtraItems(structure, booking);
			}			
			roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
			booking.setRoomSubtotal(roomSubtotal);
			
			this.updateUnitaryPriceInBookedExtraItems(structure, booking);
			
			booking.updateExtraSubtotal();
			
		}
		
		this.setBooking(booking);
	}
	
	
	//Evento updateNrGuests (cambia il numero di Guests)
		//-ricalcola prezzo room
		//-ricalcola quantità massima degli extra items
	
	@Actions({
		@Action(value="/updateNrGuests",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String updateNrGuests() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();		
				
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}		
		this.updateNrGuests(structure);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	public void updateNrGuests(Structure structure) {
		Double roomSubtotal = 0.0;
		Booking booking = null;		
		
		booking  = (Booking) this.getSession().get("booking");			
		
		booking.setNrGuests(this.getBooking().getNrGuests());
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
		booking.setRoomSubtotal(roomSubtotal);	
		
		this.updateMaxQuantityInBookedExtraItems(structure, booking);
		
		this.setBooking(booking);
	}
	
	
	
	//Evento addExtra (viene aggiunto un nuovo extra)
		//aggiunge un nuovo extra item ma non tocca quelli esistenti 		
	
	
	//Evento deleteExtra (viene rimosso un extra)
		//rimuove l'extra ma non tocca gli altri
	
	//Evento changeExtraItemQuantity (viene cambiata la quantità di un extra già selezionato)
		//cambia solo la quantità di un extra item e non cambia nient'altro
		
	@Actions({
		@Action(value="/updateExtras",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String updateExtras() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();		
				
		if(!this.checkBookingDates(structure)){
			return ERROR;
		}		
		this.updateExtras(structure);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	public void updateExtras(Structure structure) {
		
		Booking booking = null;			
		BookedExtraItem bookedExtraItem = null;		
		List<BookedExtraItem> bookedExtraItems = null;
		List<Extra> extras = null;
		
		booking  = (Booking) this.getSession().get("booking");				
		
		extras = this.getExtraService().findExtrasByIds(this.getBookingExtraIds());
		booking.setExtras(extras);
		
		bookedExtraItems = new ArrayList<BookedExtraItem>();		
		
		for(Extra each: extras){
			bookedExtraItem = null;
			for(BookedExtraItem extraItem: this.getBooking().getExtraItems()){
				//each esiste già e devo aggiornare solo la quantità leggendola dalla request
				if(extraItem.getExtra().equals(each)){
					bookedExtraItem = extraItem;	
					bookedExtraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));
					
				}				
			}
			if(bookedExtraItem == null){
				//each è un nuovo extra quindi devo creare un nuovo extra item associato
				bookedExtraItem = new BookedExtraItem();
				bookedExtraItem.setExtra(each);
				bookedExtraItem.setQuantity(booking.calculateExtraItemMaxQuantity(each));
				bookedExtraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));
				bookedExtraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each));
			}
			bookedExtraItems.add(bookedExtraItem);	
		}	
		
		booking.setExtraItems(bookedExtraItems);		
					
		booking.updateExtraSubtotal();		
			
		this.setBooking(booking);
	}	
	
	
	private void updateRoomSubtotal(Structure structure, Booking booking){
		Double roomSubtotal = 0.0;
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
		booking.setRoomSubtotal(roomSubtotal);
	}
	
	
	
	private void updateMaxQuantityInBookedExtraItems(Structure structure, Booking booking){
		Integer maxQuantity;
		
		for(BookedExtraItem each: booking.getExtraItems()){
			maxQuantity = booking.calculateExtraItemMaxQuantity(each.getExtra());
			each.setMaxQuantity(maxQuantity);
		}		
	}	
	
	private void updateUnitaryPriceInBookedExtraItems(Structure structure, Booking booking){
		Double unitaryPrice;
		if((booking.getDateIn()!=null) && (booking.getDateOut()!=null)){
			for(BookedExtraItem each: booking.getExtraItems()){
				unitaryPrice = this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each.getExtra());
				each.setUnitaryPrice(unitaryPrice);
			}	
			
		}
			
	}
	
}
