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
import model.listini.Convention;
import model.User;
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
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class OnlineBookingAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	
	private List<Room> rooms = null;
	private Booking booking = null;
	private Integer id;
	private Integer numNights = 1;
	private List<Extra> extras;
	private List<Integer> bookingExtrasId = null;
	private Integer idStructure;
	private Structure structure;
	
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
		Booking booking = null;
			
		this.getSession().put("idStructure", this.getIdStructure());
		
		booking = new Booking();
		this.getSession().put("onlineBooking", booking);
		this.setBooking(booking);
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
		List <Room> rooms = null;
		Booking booking = null;
		Date dateOut = null;
		Convention defaultConvention = null;
		
		this.setIdStructure((Integer) this.getSession().get("idStructure"));
	
		structure = ( (User) this.getSession().get("user")).getStructure();
		booking = (Booking) this.getSession().get("onlineBooking");
		
		booking.setNrGuests(this.getBooking().getNrGuests());
		booking.setDateIn(this.getBooking().getDateIn());
		
		dateOut  = DateUtils.addDays(booking.getDateIn(), this.getNumNights());	
		booking.setDateOut(dateOut);
		
		defaultConvention = this.getConventionService().findConventionsByIdStructure(structure).get(0);
		booking.setConvention(defaultConvention);
		
		this.setRooms(new ArrayList<Room>());
		
		rooms = new ArrayList<Room>();
		for(Room each : this.getRoomService().findRoomsByIdStructure(structure)){			
			if ( (each.getRoomType().getMaxGuests() >= booking.getNrGuests() ) && 
					this.getStructureService().hasRoomFreeInPeriod(structure,each.getId(), booking.getDateIn(), booking.getDateOut()) ) {
				booking.setRoom(each);
				each.setPrice(this.calculateTotalForBooking(structure, booking));
				rooms.add(each);
			}
		}	
		booking.setRoom(null);
		this.setRooms(rooms);
		this.setBooking(booking);
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
		Booking booking = null;
		
		this.setIdStructure((Integer) this.getSession().get("idStructure"));
		
		structure = ( (User) this.getSession().get("user")).getStructure();
		booking = (Booking) this.getSession().get("onlineBooking");
		
		theBookedRoom = this.getRoomService().findRoomById(structure,this.getBooking().getRoom().getId());
		booking.setRoom(theBookedRoom);		
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
		booking.setRoomSubtotal(roomSubtotal);		
		
		this.setExtras(this.getExtraService().findExtrasByIdStructure(structure.getId()));		
		
		this.setBooking(booking);
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
		Booking booking = null;
		
		this.setIdStructure((Integer) this.getSession().get("idStructure"));
		structure = ( (User) this.getSession().get("user")).getStructure();
		booking = (Booking) this.getSession().get("onlineBooking");
		
		checkedExtras = new ArrayList<Extra>();
		if (this.getBookingExtrasId() != null) {
			checkedExtras = this.getExtraService().findExtrasByIds(this.getBookingExtrasId());
		}
		booking.setExtras(checkedExtras);

		bookedExtraItems = this.calculateBookedExtraItems(structure,booking);
		booking.setExtraItems(bookedExtraItems);

		extraSubtotal = booking.calculateExtraSubtotalForBooking();
		booking.setExtraSubtotal(extraSubtotal);
		
		this.setBooking(booking);
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingFinal",results = {
				@Result(name="success",location="/jsp/online/widget5.jsp"),
				@Result(name="error",location="/jsp/online/validationError.jsp"),
				@Result(name="input", location="/jsp/online/validationError.jsp")
		})
	})
	public String goOnlineBookingFinal() {
		Structure structure = null;
		Booking booking = null;

		this.setIdStructure((Integer) this.getSession().get("idStructure"));
		structure = ((User) this.getSession().get("user")).getStructure();

		this.setStructure(structure);
		booking = (Booking) this.getSession().get("onlineBooking");
		
		this.getBooking().getBooker().setId_structure(this.getIdStructure());
		booking.setBooker(this.getBooking().getBooker());
		this.getGuestService().insertGuest(booking.getBooker());
		
		booking.setStatus("online");
		
		if (this.getBookingService().saveOnlineBooking(structure,booking) < 1) {
			this.getSession().put("onlineBooking", null);
			addActionError("This booking cannot be saved");
			return ERROR;
		}
		
		this.setBooking(booking);
		return SUCCESS;
	}

	
	private Double calculateTotalForBooking(Structure structure,Booking booking){
		Double subTotal = null;
		
		subTotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,booking);
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
		
		if(booking.getRoom() != null && booking.getId() != null && booking.getBooker() != null 
									 && booking.getDateIn() != null && booking.getDateOut() != null){
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
	
	public Integer getNumNights() {
		return numNights;
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
	
	public void setNumNights(Integer numNight) {
		this.numNights = numNight;
	}
	public List<Integer> getBookingExtrasId() {
		return bookingExtrasId;
	}
	public void setBookingExtrasId(List<Integer> bookingExtrasId) {
		this.bookingExtrasId = bookingExtrasId;
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
	public Structure getStructure() {
		return structure;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	
}