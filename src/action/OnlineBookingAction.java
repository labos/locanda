package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.BookedExtraItem;
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

import service.BookingService;
import service.ConventionService;
import service.ExtraService;
import service.GuestService;
import service.RoomService;
import service.StructureService;
import service.StructureServiceImpl;

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
		@Action(value="/goOnlineBookingCalendar",results = {
				@Result(name="success",location="/jsp/online/widget1.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingCalendar(){
		
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/goOnlineBookingRooms",results = {
				@Result(name="success",location="/jsp/online/widget2.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingRooms(){
		Structure structure = null;
		List <Booking> toRemoveBookings =  null; 
		List <Room> rooms = null;
		toRemoveBookings = new ArrayList<Booking>();
		
		structure = this.getStructureService().findStructureById(this.getIdStructure());
		
		rooms = this.getRoomService().findRoomsByIdStructure(structure);
		this.setRooms(new ArrayList<Room>());
		//remove not completed booking from the memory
		
		for (Booking abooking : this.getBookingService().findBookingsByIdStructure(structure)){			
			if (! checkBookingIsValid(abooking) ){				
				toRemoveBookings.add(abooking);
			}
		}
		
		this.getBookingService().findBookingsByIdStructure(structure);
		for(Room each : rooms){			
			if ( (each.getRoomType().getMaxGuests() >= this.getNumGuests() ) && 
					this.getStructureService().hasRoomFreeInPeriod(structure,each.getId(), this.getDateArrival(), this.calculateDateOut()) ) {
				each.setPrice( calculateTotalForBooking(each));
				this.getRooms().add(each);
			}
			
		}
		

		this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingExtras",results = {
				@Result(name="success",location="/jsp/online/widget3.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingExtras(){
		Structure structure = null;
		Room theBookedRoom = null;
		Double roomSubtotal = 0.0;
		Convention defaultConvention = null;
		
		structure = this.getStructureService().findStructureById(this.getIdStructure());
		theBookedRoom = this.getRoomService().findRoomById(structure,this.getRoomId());
		this.setBooking(new Booking());
		this.getBooking().setRoom(theBookedRoom);	
		this.setBooking(new Booking());
		this.getBooking().setId(structure.nextKey());
		this.getBooking().setRoom(theBookedRoom);
		this.getBooking().setDateIn(this.getDateArrival());
		this.getBooking().setDateOut(this.calculateDateOut());
		this.getBooking().setNrGuests(this.getNumGuests());
		defaultConvention = this.getConventionService().findConventionsByIdStructure(structure).get(0);
		this.getBooking().setConvention(defaultConvention);
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,this.getBooking());
		this.getBooking().setRoomSubtotal(roomSubtotal);		
		this.getSession().put("workingBooking", this.getBooking());
		this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingGuest",results = {
				@Result(name="success",location="/jsp/online/widget4.jsp"),
				
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	
	public String goOnlineBookingGuest() {
		Structure structure = null;
		List<Extra> checkedExtras = null;
		Double extraSubtotal = 0.0;
		List<BookedExtraItem> bookedExtraItems = null;
		structure = this.getStructureService().findStructureById(this.getIdStructure());
		this.setBooking((Booking) this.getSession().get("workingBooking"));
		if (this.getBookingExtrasId() != null) {
			checkedExtras = this.getExtraService().findExtrasByIds(
					this.getBookingExtrasId());
			this.getBooking().setExtras(checkedExtras);

			bookedExtraItems = this.calculateBookedExtraItems(structure,
					this.getBooking());
			this.getBooking().setExtraItems(bookedExtraItems);

			extraSubtotal = this.getBooking()
					.calculateExtraSubtotalForBooking();
			this.getBooking().setExtraSubtotal(extraSubtotal);

		}
		//this.getBookingService().updateBooking(structure, this.getBooking());
		this.setRoomFacilities(this.getStructureService()
				.findRoomFacilitiesByIdStructure(structure));
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
		Structure structure = null;
		Guest oldGuest = null;
		
		structure = this.getStructureService().findStructureById(this.getIdStructure());
		this.setBooking((Booking) this.getSession().get("workingBooking"));
		
		try {			
			oldGuest = this.getGuestService().findGuestById(this.getGuest().getId());
			this.getGuest().setId_structure(structure.getId());
			if(oldGuest == null){
				//Si tratta di un nuovo guest e devo aggiungerlo
				this.getGuestService().insertGuest(this.getGuest());
			}				
			this.getBooking().setBooker(this.getGuest());
			this.getBooking().setStatus("online");
			//check if current booking is valid to save
			if (checkBookingIsValid (this.getBooking())){				
				this.getBookingService().updateBooking(structure, this.getBooking());			
			}
			else {				
				//this.getBookingService().deleteBooking(structure, this.getBooking());
				this.getSession().put("workingBooking", null);
				addActionError("This booking cannot be saved");
				return ERROR;				
			}
			
		}catch(NullPointerException e) {			 
				//this.getBookingService().deleteBooking(structure, this.getBooking());
				this.getSession().put("workingBooking", null);
			 	addActionError("This booking cannot be saved");
				return ERROR;

		 }finally{
			 this.getSession().put("workingBooking", null);
		 }
		 
		this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
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
		Structure structure = null;
		Double subTotal = null;
		Booking aBooking = new Booking();
		Convention defaultConvention = null;
		
		structure = this.getStructureService().findStructureById(this.getIdStructure());
		aBooking.setId(structure.nextKey());
		aBooking.setRoom(aRoom);
		aBooking.setDateIn(this.getDateArrival());
		aBooking.setDateOut(this.calculateDateOut());
		aBooking.setNrGuests(this.getNumGuests());
		defaultConvention = this.getConventionService().findConventionsByIdStructure(structure).get(0);
		aBooking.setConvention(defaultConvention);
		subTotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,aBooking);
		return subTotal;
		
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
				bookedExtraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(),booking.getRoom().getRoomType(), booking.getConvention(), each));				
			}else{
				bookedExtraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(),booking.getRoom().getRoomType(), booking.getConvention(), each));	
			}
			bookedExtraItems.add(bookedExtraItem);
		}
		return bookedExtraItems;
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
	
	
	

}
