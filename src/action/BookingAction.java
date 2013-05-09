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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.time.DateUtils;

import model.Adjustment;
import model.CreditCard;
import model.ExtraItem;
import model.Booking;
import model.Extra;
import model.Guest;
import model.Housed;
import model.Payment;
import model.Room;
import model.RoomType;
import model.GroupLeader;
import model.UserAware;
import model.internal.Message;
import model.listini.Convention;
import model.listini.Season;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
import service.ConventionService;
import service.CreditCardService;
import service.ExtraService;
import service.GroupLeaderService;
import service.GuestService;
import service.HousedService;
import service.RoomService;
import service.SeasonService;
import service.StructureService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
public class BookingAction extends ActionSupport implements SessionAware,UserAware{
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
	private List bookingExtraIds = new ArrayList();
	private Double adjustmentsSubtotal = 0.0;
	private Double paymentsSubtotal = 0.0;
	private Integer idStructure;
	private String start = null;
	private List<Integer> listNumGuests = null;
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
	private SeasonService seasonService = null;
	@Autowired
	private GroupLeaderService groupLeaderService = null;
	@Autowired
	private HousedService housedService = null;
	@Autowired
	private CreditCardService creditCardService = null;
	
	private static Logger logger = Logger.getLogger(Logger.class);
	
	@Actions({
		@Action(
				value="/updateBookingDates",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(name="input", location = "/WEB-INF/jsp/validationError.jsp")
		})
	})	
	public String updateBookingDates() {
		Integer numNights;
		Booking booking = null;
		
		if(!this.checkBookingDates(this.getIdStructure())){
			return ERROR;
		}		
		booking  = (Booking) this.getSession().get("booking");		
		booking.setDateIn(this.getBooking().getDateIn());
		booking.setDateOut(this.getBooking().getDateOut());
		
		this.updateRoomSubtotal(booking);		
		this.updateUnitaryPriceInBookedExtraItems(booking);
		this.updateMaxQuantityInBookedExtraItems(booking);
		this.updateQuantityInBookedExtraItems(booking);
		booking.updateExtraSubtotal();
		
		this.setBooking(booking);	
		
		numNights = booking.calculateNumNights();
		this.setNumNights(numNights);
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	@Actions({
		@Action(value="/updateRoom",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(name="input", location = "/WEB-INF/jsp/validationError.jsp")
		})
	})	
	public String updateRoom() {
		Integer numGuests;
		Booking booking = null;			
		RoomType newRoomType = null;
		RoomType oldRoomType = null;
		
		if(!this.checkBookingDates(this.getIdStructure())){
			return ERROR;
		}		
				
		booking  = (Booking) this.getSession().get("booking");		
		if(booking.getRoom()!=null){
			oldRoomType = booking.getRoom().getRoomType();
		}		
		this.updateRoom(booking);	
		
		if(booking.getRoom()!=null){
			newRoomType = booking.getRoom().getRoomType();
		}		
		//If the Room Type changes, prices must be updated
		if((oldRoomType!= null) && (newRoomType!= null) && !(oldRoomType.equals(newRoomType) )){
			numGuests = booking.getNrGuests();
			if (numGuests > newRoomType.getMaxGuests()) {	//when changing a room which has pre-selected the nrGuests attribute greater than the maxGuests attribute
				numGuests = newRoomType.getMaxGuests();
				booking.setNrGuests(numGuests);
				//If nrGuests changes, extra items' max quantity must be updated
				this.updateMaxQuantityInBookedExtraItems(booking);
			}			
			this.updateRoomSubtotal(booking);			
			this.updateUnitaryPriceInBookedExtraItems(booking);
			booking.updateExtraSubtotal();
		}
		this.setBooking(booking);
		
		this.updateListNumGuests(booking);
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	private void updateRoom(Booking booking){
		Room newRoom = null; 
		
		newRoom = this.getRoomService().findRoomById(this.getBooking().getRoom().getId());
		booking.setRoom(newRoom);	
	}
	
	@Actions({
		@Action(value="/updateNrGuests",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(name="input", location = "/WEB-INF/jsp/validationError.jsp")
		})
	})	
	public String updateNrGuests() {
		Booking booking = null;	
		
		if(!this.checkBookingDates(this.getIdStructure())){
			return ERROR;
		}				
		booking  = (Booking) this.getSession().get("booking");			
		
		booking.setNrGuests(this.getBooking().getNrGuests());
		this.updateRoomSubtotal(booking);
		this.updateMaxQuantityInBookedExtraItems(booking);	
		this.updateQuantityInBookedExtraItems(booking);
		booking.updateExtraSubtotal();
		this.setBooking(booking);		
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	@Actions({
		@Action(value="/updateExtras",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(name="input", location = "/WEB-INF/jsp/validationError.jsp")
		})
	})	
	public String updateExtras() {
		Booking booking = null;	
		List<Extra> checkedExtras = null;
		List<Integer> filteredBookingExtraIds = null;
		Integer anInt;
		
		if(!this.checkBookingDatesNotNull(this.getIdStructure())){
			return ERROR;
		}					
		booking  = (Booking) this.getSession().get("booking");			
		
		filteredBookingExtraIds = new ArrayList<Integer>();
		for(Object each: this.getBookingExtraIds()){
			try{
				anInt = Integer.parseInt((String)each);
				filteredBookingExtraIds.add(anInt);
			}catch (Exception e) {
				
			}			
		}		
		checkedExtras = this.getExtraService().findExtrasByIds(filteredBookingExtraIds);
		this.updateExtraItems(booking,checkedExtras);					
		booking.updateExtraSubtotal();	
		
		this.setBooking(booking);	
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	@Actions({
		@Action(value="/updateConvention",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,extraService,guestService,structureService,bookingService,roomService,conventionService,seasonService"
				}),
				@Result(name="input", location = "/WEB-INF/jsp/validationError.jsp")
		})
	})	
	public String updateConvention() {
		Booking booking = null;			
		
		if(!this.checkBookingDates(this.getIdStructure())){
			return ERROR;
		}			
		booking  = (Booking) this.getSession().get("booking");
		booking.setConvention(this.getBooking().getConvention());
				
		//If the convention changes, then room and extra prices must be updated
		this.updateRoomSubtotal(booking);			
		this.updateUnitaryPriceInBookedExtraItems(booking);
		booking.updateExtraSubtotal();
		
		this.setBooking(booking);	
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("calculatedPriceAction"));
		return "success";						
	}
	
	private void updateExtraItems(Booking booking,List<Extra> checkedExtras){
		ExtraItem extraItem = null;		
		List<ExtraItem> extraItems = null;
		
		extraItems = new ArrayList<ExtraItem>();	
		for(Extra each: checkedExtras){
			extraItem = null;
			for(ExtraItem bookedExtraItem: this.getBooking().getExtraItems()){
				//each already exists and I have to update just the quantity reading it from the request
				if(bookedExtraItem.getExtra().equals(each)){
					extraItem = bookedExtraItem;	
					extraItem.setExtra(each);//in some cases extraItem.extra had only the id and not all the other attributes
					extraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));					
				}				
			}
			if(extraItem == null){
				//each is a new extra, so a new extra item must be created
				extraItem = new ExtraItem();
				extraItem.setExtra(each);
				extraItem.setQuantity(booking.calculateExtraItemMaxQuantity(each));
				extraItem.setMaxQuantity(booking.calculateExtraItemMaxQuantity(each));
				extraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(this.getIdStructure(), booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each));
			}
			extraItems.add(extraItem);	
		}	
		booking.setExtraItems(extraItems);		
	}
	
	private void updateRoomSubtotal(Booking booking){
		Double roomSubtotal = 0.0;
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(this.getIdStructure(),booking);
		booking.setRoomSubtotal(roomSubtotal);		
	}
	
	private void updateMaxQuantityInBookedExtraItems(Booking booking){
		Integer maxQuantity;
		
		for(ExtraItem each: booking.getExtraItems()){
			maxQuantity = booking.calculateExtraItemMaxQuantity(each.getExtra());
			each.setMaxQuantity(maxQuantity);
		}		
	}	
	
	private void updateQuantityInBookedExtraItems(Booking booking){
		Integer maxQuantity;
		
		for(ExtraItem each: booking.getExtraItems()){
			maxQuantity = booking.calculateExtraItemMaxQuantity(each.getExtra());
			each.setQuantity(maxQuantity);
		}		
	}
	
	private void updateUnitaryPriceInBookedExtraItems(Booking booking){
		Double unitaryPrice;
		if((booking.getDateIn()!=null) && (booking.getDateOut()!=null)){
			for(ExtraItem each: booking.getExtraItems()){
				unitaryPrice = this.getStructureService().calculateExtraItemUnitaryPrice(this.getIdStructure(), booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each.getExtra());
				each.setUnitaryPrice(unitaryPrice);
			}	
		}	
	}
	
	@Actions({
		@Action(value="/displayQuantitySelect",results = {
				@Result(name="success",location="/WEB-INF/jsp/contents/extraQuantity_select.jsp")
				})
	})
	public String displayQuantitySelect() {
		
		this.setExtras(this.getExtraService().findExtrasByIdStructure(this.getIdStructure()));
		this.setBooking((Booking) this.getSession().get("booking"));
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/saveUpdateBooking",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(name="input", location="/WEB-INF/jsp/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String saveUpdateBooking(){
		Guest booker = null;
		Booking booking = null;
		Convention convention = null;
		CreditCard creditCard = null;
		
		if(!this.checkBookingDates(this.getIdStructure())){
			return ERROR;
		}		
		
		if(!this.checkBookingChecking()){
			return ERROR;
		}		
		
		booking = (Booking) this.getSession().get("booking");
		
		//Adjustments
		this.filterAdjustments();
		booking.setAdjustments(this.getBooking().getAdjustments());
		//Payments
		this.filterPayments();
		booking.setPayments(this.getBooking().getPayments());
		//ExtraItems: no need to filter them, because they're not updated at every updateExtras() call		
		
		//Guests: da inserire quando ci saranno i guests
		/*
		this.filterGuests();	
		booking.setGuests(this.getBooking().getGuests());
		*/
		booker = this.getBooking().getBooker();	
		if(booker.getId() == null){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("bookingAddErrorNoBookerAction"));
			return ERROR;
		}
		
		booker.setId_structure(this.getIdStructure());
		booking.setBooker(booker);
		
		booking.setId_room(this.getBooking().getRoom().getId());
		
		convention = this.getBooking().getConvention();
		booking.setId_convention(convention.getId());
		booking.setNotes(this.getBooking().getNotes());
		creditCard = this.getBooking().getCreditCard();
		booking.setCreditCard(creditCard);
		logger.info("****** carta di credito " + creditCard.getCardType());
		booking.setStatus(this.getBooking().getStatus());		
		booking.setId_structure(this.getIdStructure());
		
		this.getBookingService().saveUpdateBooking(booking);
		
		this.getSession().put("booking", booking);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("bookingAddUpdateSuccessAction"));
		return SUCCESS;
	}
	
		
	@Actions({
		@Action(value="/goAddBookingFromPlanner",results = {
				@Result(name="success",location="/WEB-INF/jsp/contents/booking_form.jsp"),
				@Result(name="input", location="/WEB-INF/jsp/validationError.jsp")
		})
	})
	public String goAddNewBookingFromPlanner() {
		Room theBookedRoom = null;
		Convention defaultConvention = null;
		Integer numNights = 0;
		Double roomSubtotal = 0.0;
		Booking booking = null;
		CreditCard creditCard = new CreditCard();
		
		booking = new Booking();
		this.getSession().put("booking", booking);
		
		booking.setDateIn(this.getBooking().getDateIn());
		booking.setDateOut(this.getBooking().getDateOut());		
		
		theBookedRoom = this.getRoomService().findRoomById(this.getBooking().getRoom().getId());
		booking.setRoom(theBookedRoom);
		this.updateListNumGuests(booking);
		
		defaultConvention = this.getConventionService().findConventionsByIdStructure(this.getIdStructure()).get(0);
		booking.setConvention(defaultConvention);
		
		booking.setCreditCard(creditCard);
		
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(this.getIdStructure(),booking);
		booking.setRoomSubtotal(roomSubtotal);
		
		this.setBooking(booking);
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(this.getIdStructure()));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(this.getIdStructure()));
		this.setConventions(this.getConventionService().findConventionsByIdStructure(this.getIdStructure()));
				
		numNights = booking.calculateNumNights();
		this.setNumNights(numNights);
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goAddNewBooking",results = {
				@Result(name="success",location="/WEB-INF/jsp/booking.jsp")
		})
	})
	public String goAddNewBooking() {
		Convention defaultConvention = null;
		Booking booking = null;
		CreditCard creditCard = new CreditCard();
				
		booking = new Booking();
		this.getSession().put("booking", booking);
						
		defaultConvention = this.getConventionService().findConventionsByIdStructure(this.getIdStructure()).get(0);
		booking.setConvention(defaultConvention);	
		booking.setCreditCard(creditCard);
		
		this.setBooking(booking);
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(this.getIdStructure()));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(this.getIdStructure()));
		this.setConventions(this.getConventionService().findConventionsByIdStructure(this.getIdStructure()));
		this.setListNumGuests(new ArrayList<Integer>());
			
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goAddNewBookingFromGuest",results = {
				@Result(name="success",location="/WEB-INF/jsp/booking.jsp")
		})
	})
	public String goAddNewBookingFromGuest() {
		Convention defaultConvention = null;
		Booking booking = null;
		Guest booker = null;		
		booking = new Booking();
		this.getSession().put("booking", booking);
						
		defaultConvention = this.getConventionService().findConventionsByIdStructure(this.getIdStructure()).get(0);
		booking.setConvention(defaultConvention);
		
		//booker settings..
		booker = this.getGuestService().findGuestById(this.getId());
		booking.setBooker(booker);
		
		this.setBooking(booking);
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(this.getIdStructure()));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(this.getIdStructure()));
		this.setConventions(this.getConventionService().findConventionsByIdStructure(this.getIdStructure()));
		this.setListNumGuests(new ArrayList<Integer>());
			
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goUpdateBooking",results = {
				@Result(name="success",location="/WEB-INF/jsp/booking.jsp")
		}),
		@Action(value="/goUpdateBookingFromPlanner",results = {
				@Result(name="success",location="/WEB-INF/jsp/contents/booking_form.jsp")
		})
	})
	public String goUpdateBooking() {
		Booking booking = null;
		Integer numNights = 0;
		Double adjustmentsSubtotal = 0.0;
		Double paymentsSubtotal = 0.0;
		
		booking = this.getBookingService().findBookingById(this.getId());
		this.getSession().put("booking", booking);
		this.setBooking(booking);
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(this.getIdStructure()));
		this.setExtras(this.getExtraService().findExtrasByIdStructure(this.getIdStructure()));
		this.setBookingExtraIds(booking.calculateExtraIds());
		this.setConventions(this.getConventionService().findConventionsByIdStructure(this.getIdStructure()));		
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		adjustmentsSubtotal = this.getBooking().calculateAdjustmentsSubtotal();
		this.setAdjustmentsSubtotal(adjustmentsSubtotal);
		
		paymentsSubtotal = this.getBooking().calculatePaymentsSubtotal();
		this.setPaymentsSubtotal(paymentsSubtotal);		
			
		this.updateListNumGuests(booking);
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findAllBookingsJson",results = {
				@Result(type ="json",name="success", params={"root","bookings"})
		}) 
	})
	public String findAllBookings(){
		
		this.setBookings(this.getBookingService().findBookingsByIdStructure(this.getIdStructure()));
		return SUCCESS;		
	}	
	
	@Actions({
		@Action(value="/findAllBookingsByStartDateAndLengthOfStay",results = {
				@Result(type ="json",name="success", params={"root","bookings"})
		}) 
	})
	public String findAllBookingsByStartDateAndLengthOfStay(){
		
		if (this.getStart() != null && this.getStart().length() > 1) {
			Date startDateBookings = new Date(Long.parseLong(this.getStart()) * 1000);
			logger.info("**** data inizio2 ***" + startDateBookings);
			this.setBookings(this.getBookingService().findAllBookingsByStartDateAndLengthOfStay(this.getIdStructure(), startDateBookings, 10));
			return SUCCESS;
		}
		return ERROR;
	
	}	

	@Actions({
		@Action(value="/checkBookingDates",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(name="input", location="/WEB-INF/jsp/validationError.jsp"),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String checkBookingDates(){
		
		if(!this.checkBookingDates(this.getIdStructure())){
			return ERROR;
		}
		return SUCCESS;
				
	}
	
	@Actions({
		@Action(value="/checkBookingDatesNotNull",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(name="input", location="/WEB-INF/jsp/validationError.jsp"),				
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String checkBookingDatesNotNull(){
		
		if(!this.checkBookingDatesNotNull(this.getIdStructure())){
			return ERROR;
		}
		return SUCCESS;
	}
	
	private Boolean checkBookingDates(Integer id_structure) {
		List<Date> bookingDates = null;
		Season season = null;
		
		if(this.getBooking().getDateIn()!=null && this.getBooking().getDateOut()!=null){
			if(!this.getBooking().checkDates()){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("dateOutMoreDateInAction"));
				return false;
			}
			if ((this.getBooking().getRoom().getId()!=null) && (!this.getStructureService().hasRoomFreeForBooking(id_structure,this.getBooking()))) {
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("bookingOverlappedAction"));
				return false;
			}	
			bookingDates = this.getBooking().calculateBookingDates();
			for(Date aBookingDate: bookingDates){
				season = this.getSeasonService().findSeasonByDate(id_structure,aBookingDate );
				if(season == null){
					this.getMessage().setResult(Message.ERROR);
					this.getMessage().setDescription(getText("periodSeasonError"));
					return false;
				}
			}
		}
	
		this.getMessage().setDescription(getText("bookingDatesOK"));
		this.getMessage().setResult(Message.SUCCESS);
		return true;
	}
	
	private Boolean checkBookingDatesNotNull(Integer id_structure) {
		List<Date> bookingDates = null;
		Season season = null;
		
		if(this.getBooking().getDateIn()!=null && this.getBooking().getDateOut()!=null){
			if(!this.getBooking().checkDates()){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("dateOutMoreDateInAction"));
				return false;
			}
			if ((this.getBooking().getRoom().getId()!=null) && (!this.getStructureService().hasRoomFreeForBooking(id_structure,this.getBooking()))) {
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("bookingOverlappedAction"));
				return false;
			}
			bookingDates = this.getBooking().calculateBookingDates();
			for(Date aBookingDate: bookingDates){
				season = this.getSeasonService().findSeasonByDate(id_structure,aBookingDate );
				if(season == null){
					this.getMessage().setResult(Message.ERROR);
					this.getMessage().setDescription(getText("periodSeasonError"));
					return false;
				}
			}
		}
		else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("bookingNotChosenDates"));
			return false;
		}
		this.getMessage().setDescription(getText("bookingDatesOK"));
		this.getMessage().setResult(Message.SUCCESS);
		return true;
	}
	
	private Boolean checkBookingChecking(){
		
		Date dateOut = this.getBooking().getDateOut();
		String status = this.getBooking().getStatus();
		if(status!=null && status.equals("checkedout") && dateOut!=null){
			
			if (DateUtils.truncatedCompareTo(Calendar.getInstance().getTime(), dateOut,Calendar.DAY_OF_MONTH) < 0)
			{
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("bookingCheckOutDateError"));
				return false;
			}
		}
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
	
	@Actions({
		@Action(value="/deleteBooking",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteBooking() {
		Integer count = 0;
		
		// check groupleader
		GroupLeader groupLeader = null;
		Housed housed = null;
		List<Booking> bookings= null;
		List<Housed> housedInBooking = null;
		String groupLeaderinBookingPresenceMessage = "";
		Boolean groupLeaderinBookingPresence = false;
		Boolean groupLeadersPresence = false;
		String  groupLeadersPresenceMessage = "";
		List<GroupLeader> groupLeaders = null;

		
		groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(this.getBooking().getId());
		logger.info("**** bookingaction, found a groupleader " + groupLeader);
		if(groupLeader != null){
			//if groupleader has not an housed following fail
			housed = this.getHousedService().findHousedByIdBookingAndIdGuest(this.getBooking().getId(), groupLeader.getHoused().getId_guest());

			//check if groupleader is housed
			if(housed != null){
				logger.info("**** housed  found " + housed + "with id:" + housed.getId());

			//search for groupleader presence in bookings
				bookings = this.getBookingService().findBookingIdsByIdHousedGroupLeader(housed.getId());
				logger.info("**** bookingaction, booking length " + bookings.size());
			// check if groupleader is housed in existing booking
			if(bookings.size() == 0){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("bookingDeleteWithGroupLeaderNotHousedErrorAction"));
				return "error";
			}
			//scan all bookings found	
				for(Booking each: bookings){

					if(!each.getId().equals(this.getBooking().getId())){
						groupLeaderinBookingPresenceMessage += "\n***(" + getText("room") + ": " + each.getRoom().getName() + " " + each.getDateIn() + " - " + each.getDateOut() +")";
						groupLeaderinBookingPresence = true;
					}
				}
				logger.info("**** bookingaction, bookpresence = " +groupLeaderinBookingPresence);	
			if(groupLeaderinBookingPresence == true){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("bookingDeleteWithGroupLeaderErrorAction") + groupLeaderinBookingPresenceMessage);
				return "error";
			}

			}

			
		}
		
		// check for groupLeader in each housed
		housedInBooking = this.getHousedService().findHousedByIdBooking(this.getBooking().getId());
		for(Housed each: housedInBooking){

			groupLeaders = this.getGroupLeaderService().findByIdHoused(each.getId());
			
	    	if (groupLeaders.size() > 0) {
				logger.info("**** bookingaction, found groupleaders size = " + groupLeaders.size());	

	    		Booking bookingGroupLeader = null;
	    		groupLeadersPresence = true;
	    		//get all bookings info
	    		for(GroupLeader aGroupLeader: groupLeaders){
	    			bookingGroupLeader = this.getBookingService().findBookingById(aGroupLeader.getId_booking());
	    			groupLeadersPresenceMessage+=  "\n***(room: " + bookingGroupLeader.getRoom().getName() + " " + bookingGroupLeader.getDateIn() + " - " + bookingGroupLeader.getDateOut() +")" ;
	    			
	    		}
	    	}
			
	
		}
		
		if(groupLeadersPresence == true){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("bookingDeleteWithGroupLeaderErrorAction") + groupLeadersPresenceMessage);
			return "error";
		}
		
		count = this.getBookingService().deleteBooking(this.getBooking().getId());
		if(count > 0 ){
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
		@Action(value="/goOnlineBookings",results = {
				@Result(name="success",location="/WEB-INF/jsp/onlineBookings.jsp")
		}) 	
	})
	public String goOnlineBookings(){
		return SUCCESS;		
	}
	
	private void updateListNumGuests(Booking booking){
		List<Integer> listNumGuests = null;
		Integer maxGuests = 0;
		
		listNumGuests = new ArrayList<Integer>();
		maxGuests = booking.getRoom().getRoomType().getMaxGuests();
		for (Integer i= 1; i<= maxGuests; i++) {
			listNumGuests.add(i);
		}
		this.setListNumGuests(listNumGuests);
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
	public List getBookingExtraIds() {
		return bookingExtraIds;
	}
	public void setBookingExtraIds(List bookingExtraIds) {
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
	public List<Integer> getListNumGuests() {
		return listNumGuests;
	}
	public void setListNumGuests(List<Integer> listNumGuests) {
		this.listNumGuests = listNumGuests;
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
	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}

	public GroupLeaderService getGroupLeaderService() {
		return groupLeaderService;
	}

	public void setGroupLeaderService(GroupLeaderService groupLeaderService) {
		this.groupLeaderService = groupLeaderService;
	}

	public HousedService getHousedService() {
		return housedService;
	}

	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}

	public CreditCardService getCreditCardService() {
		return creditCardService;
	}

	public void setCreditCardService(CreditCardService creditCardService) {
		this.creditCardService = creditCardService;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
		
}