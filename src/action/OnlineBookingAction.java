/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Booking;
import model.Extra;
import model.ExtraItem;
import model.Guest;
import model.Image;
import model.Room;
import model.Structure;
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
import service.ImageService;
import service.RoomService;
import service.StructureService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class OnlineBookingAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	
	private List<Room> rooms = null;
	private Booking booking = null;
	private Guest onlineGuest = null;
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
	@Autowired
	private ImageService imageService = null;
	@Actions({
		@Action(value="/mobile",results = {
				@Result(name="success",location="/WEB-INF/jsp/online/widget1.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/online/validationError.jsp")
		}),
		@Action(value="/goOnlineBookingCalendar",results = {
				@Result(name="success",location="/WEB-INF/jsp/online/widget1.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/online/validationError.jsp")
		})
	})
	public String goOnlineBookingCalendar(){
		Booking booking = null;
		Structure structure = null;
		Locale locale = null;
		SimpleDateFormat sdf = null;
		String datePattern = null;
		locale = this.getLocale();
		sdf = (SimpleDateFormat) DateFormat.getDateInstance(
				DateFormat.SHORT, locale);
		datePattern = sdf.toPattern();
		this.getSession().put("datePattern", datePattern);	
		structure = this.getStructureService().findStructureById(this.getIdStructure());
		this.setStructure(structure);
		
		booking = new Booking();
		booking.setStatus("online");
		booking.setId_structure(structure.getId());		
		this.getSession().put("onlineBooking", booking);
		this.getSession().put("structure", structure);
		this.setBooking(booking);
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingRooms",results = {
				@Result(name="success",location="/WEB-INF/jsp/online/widget2.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/online/validationError.jsp")
		})
	})
	public String goOnlineBookingRooms(){
		Structure structure = null;
		List <Room> rooms = null;
		List <Image> images = null;
		Booking booking = null;
		Date dateOut = null;
		Convention defaultConvention = null;

		structure =  (Structure) this.getSession().get("structure");
		this.setStructure(structure);
		this.setIdStructure(structure.getId());
		
		booking = (Booking) this.getSession().get("onlineBooking");		
		
		booking.setNrGuests(this.getBooking().getNrGuests());
		booking.setDateIn(this.getBooking().getDateIn());
		
		dateOut  = DateUtils.addDays(booking.getDateIn(), this.getNumNights());	
		booking.setDateOut(dateOut);
		
		for(Convention each: this.getConventionService().findConventionsByIdStructure(structure.getId())){
			if(each.getActivationCode().equals("thisconventionshouldntneverberemoved")){
				defaultConvention = each;
			}			
		}
		booking.setConvention(defaultConvention);
		booking.setId_convention(defaultConvention.getId());
		
		this.setRooms(new ArrayList<Room>());
		
		rooms = new ArrayList<Room>();
		for(Room each : this.getRoomService().findRoomsByIdStructure(structure.getId())){			
			if ( (each.getRoomType().getMaxGuests() >= booking.getNrGuests() ) && 
					this.getStructureService().hasRoomFreeInPeriod(structure.getId(),each.getId(), booking.getDateIn(), booking.getDateOut()) ) {
				booking.setRoom(each);
				each.setPrice(this.calculateTotalForBooking(structure.getId(), booking));
				images = new ArrayList<Image>();
				images = this.getImageService().findCheckedByIdRoom(each.getId(),0,10);
				each.setImages(images);
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
				@Result(name="success",location="/WEB-INF/jsp/online/widget3.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/online/validationError.jsp")
		})
	})
	public String goOnlineBookingExtras(){
		Structure structure = null;
		Room theBookedRoom = null;
		Double roomSubtotal = 0.0;
		Booking booking = null;		
		
		structure =  (Structure) this.getSession().get("structure");
		this.setStructure(structure);
		
		booking = (Booking) this.getSession().get("onlineBooking");
		
		theBookedRoom = this.getRoomService().findRoomById(this.getBooking().getRoom().getId());
		booking.setRoom(theBookedRoom);		
		booking.setId_room(theBookedRoom.getId());
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure.getId(),booking);
		booking.setRoomSubtotal(roomSubtotal);		
		
		this.setExtras(this.getExtraService().findExtrasByIdStructureAndAvailableOnline(structure.getId(), true));		
		
		this.setBooking(booking);
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingGuest",results = {
				@Result(name="success",location="/WEB-INF/jsp/online/widget4.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/online/validationError.jsp")
		})
	})
	public String goOnlineBookingGuest() {
		Structure structure = null;
		List<Extra> checkedExtras = null;
		Double extraSubtotal = 0.0;
		List<ExtraItem> extraItems = null;
		Booking booking = null;
		
		structure =  (Structure) this.getSession().get("structure");
		this.setStructure(structure);
		
		booking = (Booking) this.getSession().get("onlineBooking");
		
		checkedExtras = new ArrayList<Extra>();
		if (this.getBookingExtrasId() != null) {
			checkedExtras = this.getExtraService().findExtrasByIds(this.getBookingExtrasId());
		}
		
		extraItems = this.calculateExtraItems(structure.getId(),booking,checkedExtras);
		booking.setExtraItems(extraItems);

		extraSubtotal = booking.calculateExtraSubtotalForBooking();
		booking.setExtraSubtotal(extraSubtotal);
		
		this.setBooking(booking);
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goOnlineBookingFinal",results = {
				@Result(name="success",location="/WEB-INF/jsp/online/widget5.jsp"),
				@Result(name="error",location="/WEB-INF/jsp/online/validationError.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/online/validationError.jsp")
		})
	})
	public String goOnlineBookingFinal() {
		Structure structure = null;
		Booking booking = null;

		structure =  (Structure) this.getSession().get("structure");		
		this.setStructure(structure);
		
		booking = (Booking) this.getSession().get("onlineBooking");
		//create an online guest
		this.getOnlineGuest().setId_structure(structure.getId());
		this.getGuestService().insertGuest(this.getOnlineGuest());
		booking.setBooker(this.getOnlineGuest());
	
		if (this.getBookingService().saveOnlineBooking(booking,this.getOnlineGuest()) == 0 ) {
			
			this.getSession().put("onlineBooking", null);
			addActionError("This booking cannot be saved");
			return ERROR;
		}
		
		this.setBooking(booking);
		return SUCCESS;
	}

	private Double calculateTotalForBooking(Integer id_structure,Booking booking){
		Double subTotal = null;
		
		subTotal = this.getBookingService().calculateRoomSubtotalForBooking(id_structure,booking);
		return subTotal;
	}
	
	private List<ExtraItem> calculateExtraItems(Integer id_structure, Booking booking,List<Extra> checkedExtras){
		ExtraItem extraItem = null;
		List<ExtraItem> extraItems = null;
		
		extraItems = new ArrayList<ExtraItem>();
		for(Extra each: checkedExtras){
			extraItem = booking.findExtraItem(each);
			if(extraItem==null){
				extraItem = new ExtraItem();
				extraItem.setExtra(each);
				extraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));
				extraItem.setQuantity(booking.calculateExtraItemMaxQuantity(each));
				extraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(id_structure, booking.getDateIn(), booking.getDateOut(),booking.getRoom().getRoomType(), booking.getConvention(), each));				
			}else{
				extraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(id_structure, booking.getDateIn(), booking.getDateOut(),booking.getRoom().getRoomType(), booking.getConvention(), each));	
			}
			extraItems.add(extraItem);
		}
		return extraItems;
	}
	
	@Actions({
		@Action(value="/online",results = {
				@Result(name="success",location="/WEB-INF/jsp/onlineBookingsPreview.jsp")
		}) 	
	})
	public String goOnlinePreviewBookings(){
		return SUCCESS;		
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
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public Structure getStructure() {
		return structure;
	}
	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	public Guest getOnlineGuest() {
		return onlineGuest;
	}
	public void setOnlineGuest(Guest onlineGuest) {
		this.onlineGuest = onlineGuest;
	}
}