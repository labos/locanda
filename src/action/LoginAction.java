package action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Adjustment;
import model.BookedExtraItem;
import model.Booking;
import model.Extra;
import model.Guest;
import model.Image;
import model.Payment;
import model.Room;
import model.RoomFacility;
import model.RoomType;
import model.Structure;
import model.StructureFacility;
import model.User;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.RoomPriceListItem;
import model.listini.RoomPriceList;
import model.listini.Period;
import model.listini.Season;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
import service.ConventionService;
import service.ExtraPriceListService;
import service.ExtraService;
import service.GuestService;
import service.RoomPriceListService;
import service.RoomService;
import service.RoomTypeService;
import service.SeasonService;
import service.StructureService;
import service.StructureServiceImpl;
import service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class LoginAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private String email = null;
	private String password;
	@Autowired
	private SeasonService seasonService = null;
	@Autowired 
	private ExtraService extraService = null;
	@Autowired 
	private GuestService guestService = null;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private BookingService bookingService  = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private ConventionService conventionService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
	private UserService userService = null;
	
	
	@Actions(value={
			@Action(value="/login", results={
					@Result(name="input",location="/login.jsp"),
					@Result(name="loginSuccess", location="/homeLogged.jsp"),	
					@Result(name="loginError", location="/login.jsp")
			})	
	})	
	
	public String execute() {
		String ret = null;
		User user = null;
		Structure structure = null;
		Locale locale = null;
		SimpleDateFormat sdf = null;
		String datePattern = null;

		user = this.getUserService().findUserByEmail(this.getEmail().trim());
		if(user.getPassword().equals(this.getPassword().trim())){
			
			structure = this.getStructureService().findStructureByIdUser(user.getId());
			this.buildStructure(structure);
			user.setStructure(structure);
			this.getSession().put("user", user);
			locale = this.getLocale();
			sdf = (SimpleDateFormat) DateFormat.getDateInstance(
					DateFormat.SHORT, locale);
			datePattern = sdf.toPattern();
			this.getSession().put("datePattern", datePattern);
			ret = "loginSuccess";			
		}else {
			this.getSession().put("user", null);
			ret = "loginError";
		}
		if (this.getEmail().trim().equals("locanda@locanda.it")
				&& this.getPassword().trim().equals("locanda")) {
			
		} 
		return ret;
	}
	

	
	private void buildStructure(Structure structure){
			
		this.buildRoomFacilities(structure);
		this.buildRoomTypes(structure);
		this.buildRooms(structure);
		this.buildGuests(structure);
		this.buildSeasons(structure);
		this.buildConventions(structure);
		this.buildRoomPriceLists(structure);
		this.buildExtras(structure);
		this.buildExtraPriceLists(structure);
		this.buildBookings(structure);
		this.buildImages(structure);
		this.buildStructureFacilities(structure);
			
	}
	
	private void buildRooms(Structure structure){
		Room aRoom = null;		
			
		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("101");
		aRoom.addRoomFacility(this.getStructureService().findRoomFacilitiesByIdStructure(structure).get(0));
		aRoom.addRoomFacility(this.getStructureService().findRoomFacilitiesByIdStructure(structure).get(2));
		aRoom.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(0));
		this.getRoomService().insertRoom(structure, aRoom);
		
		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("201");
		aRoom.addRoomFacility(this.getStructureService().findRoomFacilitiesByIdStructure(structure).get(1));
		aRoom.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(1));
		this.getRoomService().insertRoom(structure, aRoom);
	}
	
	private void buildRoomTypes(Structure structure){
		RoomType aRoomType = null;
		Image image = new Image();
		RoomFacility roomTypeFacility = new RoomFacility();
		image.setId(structure.nextKey());
		image.setName("singola");
		image.setFileName("single.jpg");
		roomTypeFacility.setId(structure.nextKey());
		roomTypeFacility.setName("wifi");
		roomTypeFacility.setFileName("wifi.png");
		
		aRoomType = new RoomType();
		aRoomType.setId(structure.nextKey());
		aRoomType.setName("singola");
		aRoomType.setMaxGuests(1);
		
		aRoomType.addRoomTypeFacility(roomTypeFacility);
		this.getStructureService().addRoomFacility(structure, roomTypeFacility);
		
		roomTypeFacility = new RoomFacility();
		roomTypeFacility.setId(structure.nextKey());
		roomTypeFacility.setName("air conditioned");
		roomTypeFacility.setFileName("air_conditioned.png");
		aRoomType.addRoomTypeFacility(roomTypeFacility);
		this.getStructureService().addRoomFacility(structure, roomTypeFacility);
		
		aRoomType.addRoomTypeImage(image);
		
		this.getRoomTypeService().insertRoomType(structure, aRoomType);
		
		image = new Image();
		image.setId(structure.nextKey());
		image.setName("doppia");
		image.setFileName("double.jpg");
		aRoomType = new RoomType();
		aRoomType.setId(structure.nextKey());
		aRoomType.setName("doppia");
		aRoomType.setMaxGuests(2);
		aRoomType.addRoomTypeImage(image);
		this.getRoomTypeService().insertRoomType(structure, aRoomType);
	}
	
	private void buildRoomFacilities(Structure structure){
		RoomFacility aRoomFacility = null;
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("AAD");
		aRoomFacility.setFileName("AAD.gif");
		this.getStructureService().addRoomFacility(structure, aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("BAR");
		aRoomFacility.setFileName("BAR.gif");
		this.getStructureService().addRoomFacility(structure, aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("PHO");
		aRoomFacility.setFileName("PHO.gif");
		this.getStructureService().addRoomFacility(structure, aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("RAD");
		aRoomFacility.setFileName("RAD.gif");
		this.getStructureService().addRoomFacility(structure, aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("TEL");
		aRoomFacility.setFileName("TEL.gif");
		this.getStructureService().addRoomFacility(structure, aRoomFacility);
		
	}
	
	private void buildGuests(Structure structure){
		structure.setGuests(
				this.getGuestService().findGuestsByIdStructure(
						structure.getId()));
	
	}
	
	private void buildBookings(Structure structure){
		Booking aBooking = null;
		Room aRoom = null;
		Guest aGuest = null;
		List<Extra> extras = null;
		Date dateIn = null;
		Date dateOut = null;
		Double roomSubtotal = 0.0;
		Adjustment anAdjustment = null;
		Payment aPayment = null;
		List<BookedExtraItem> bookedExtraItems = null;
		
		aBooking = new Booking();
		aRoom = this.getRoomService().findRoomByName(structure,"101");
		
		aGuest = this.getGuestService().findGuestsByIdStructure(structure.getId()).get(0);
		extras = new ArrayList<Extra>();
		extras.add(this.getExtraService().findExtrasByIdStructure(structure.getId()).get(0));
		extras.add(this.getExtraService().findExtrasByIdStructure(structure.getId()).get(1));
		aBooking.addExtras(extras);
		aBooking.setBooker(aGuest);
		aBooking.setRoom(aRoom);
		dateIn = new Date(System.currentTimeMillis());
		dateOut = new Date(System.currentTimeMillis() + 3*24*3600*1000);
		dateIn = DateUtils.truncate(dateIn, Calendar.DAY_OF_MONTH);
		dateOut = DateUtils.truncate(dateOut, Calendar.DAY_OF_MONTH);
		aBooking.setDateIn(dateIn);
		aBooking.setDateOut(dateOut);
		aBooking.setId(structure.nextKey());
		aBooking.setNrGuests(1);
		aBooking.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		roomSubtotal = this.getBookingService().calculateRoomSubtotalForBooking(structure,aBooking);
	
		aBooking.setRoomSubtotal(roomSubtotal);
		bookedExtraItems = this.calculateBookedExtraItems(structure, aBooking);
		aBooking.setExtraItems(bookedExtraItems);	
				
		anAdjustment = new Adjustment();
		anAdjustment.setId(structure.nextKey());
		anAdjustment.setDate(DateUtils.truncate(new Date(),Calendar.DAY_OF_MONTH));
		anAdjustment.setDescription("Sconto per doccia malfunzionante");
		anAdjustment.setAmount(new Double("-50.0"));
		aBooking.addAdjustment(anAdjustment);
		
		aPayment = new Payment();
		aPayment.setId(structure.nextKey());
		aPayment.setDate(DateUtils.truncate(new Date(),Calendar.DAY_OF_MONTH));
		aPayment.setDescription("Acconto");
		aPayment.setAmount(new Double("60.0"));
		aBooking.addPayment(aPayment);
		aBooking.setStatus("checkedout");
		this.getBookingService().insertBooking(structure, aBooking);
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
						this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each));
				
			}else{
				bookedExtraItem.setUnitaryPrice(
						this.getStructureService().calculateExtraItemUnitaryPrice(structure, booking.getDateIn(), booking.getDateOut(), booking.getRoom().getRoomType(), booking.getConvention(), each));	
			}
			bookedExtraItems.add(bookedExtraItem);
		}
		return bookedExtraItems;
	}
	
	private void buildExtras(Structure structure){
		
		structure.setExtras(
				this.getExtraService().findExtrasByIdStructure(
						structure.getId()));
		
	}
	
	
	private void buildSeasons(Structure structure){
		Season aSeason = null;
		Period aPeriod = null;
		SimpleDateFormat sdf = null;
		
		
		structure.setSeasons(
			this.getSeasonService().findSeasonsByStructureId(structure.getId()));
		
	}
	
	private void buildConventions(Structure structure){
		Convention convention = null;
		
		//convenzione di default
		convention = new Convention();
		convention.setId(structure.nextKey());
		convention.setName("agevolazione Default");
		convention.setDescription("Default convention");
		convention.setActivationCode("XXX");
		
		this.getConventionService().insertConvention(structure, convention);
		
	}
	
	private void buildExtraPriceLists(Structure structure){
		ExtraPriceList extraPriceList = null;
		ExtraPriceListItem extraPriceListItem = null;
		Double price = null;
		
		//Listino Extra per Camera Singola Bassa Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(0));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Bassa Stagione"));
		extraPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 10.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure, extraPriceList);
		
		//Listino Extra per Camera Singola Alta Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(0));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Alta Stagione"));
		extraPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 15.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure, extraPriceList);
		//Listino Extra per Camera Doppia Bassa Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(1));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Bassa Stagione"));
		extraPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 10.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure, extraPriceList);
		
		//Listino Extra per Camera Doppia Alta Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(1));
		extraPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Alta Stagione"));
		extraPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		for (Extra eachExtra : this.getExtraService().findExtrasByIdStructure(structure.getId())) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			price = 15.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		this.getExtraPriceListService().insertExtraPriceList(structure, extraPriceList);
	}
	
	private void buildRoomPriceLists(Structure structure){
		RoomPriceList roomPriceList = null;
		RoomPriceListItem roomPriceListItem = null;
		Double[] prices = null;
		
		//Listino Room per Camera Singola Bassa Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(0));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Bassa Stagione"));
		roomPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 50.0;//lun
		prices[1] = 50.0;//mar
		prices[2] = 50.0;//mer
		prices[3] = 50.0;//gio
		prices[4] = 50.0;//ven
		prices[5] = 50.0;//sab
		prices[6] = 50.0;//dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		this.getRoomPriceListService().insertRoomPriceList(structure, roomPriceList);
		
		
		//Listino Room per Camera Singola Alta Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(0));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Alta Stagione"));
		roomPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 80.0;//lun
		prices[1] = 80.0;//mar
		prices[2] = 80.0;//mer
		prices[3] = 80.0;//gio
		prices[4] = 80.0;//ven
		prices[5] = 80.0;//sab
		prices[6] = 80.0;//dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		this.getRoomPriceListService().insertRoomPriceList(structure, roomPriceList);
		
		//Listino Room per Camera Doppia Bassa Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(1));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Bassa Stagione"));
		roomPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 80.0;//lun
		prices[1] = 80.0;//mar
		prices[2] = 80.0;//mer
		prices[3] = 80.0;//gio
		prices[4] = 80.0;//ven
		prices[5] = 80.0;//sab
		prices[6] = 80.0;//dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(2);
		prices = new Double[7];
		prices[0] = 100.0;//lun
		prices[1] = 100.0;//mar
		prices[2] = 100.0;//mer
		prices[3] = 100.0;//gio
		prices[4] = 100.0;//ven
		prices[5] = 100.0;//sab
		prices[6] = 100.0;//dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		
		//structure.addRoomPriceList(roomPriceList);
		this.getRoomPriceListService().insertRoomPriceList(structure, roomPriceList);
		
		//Listino Room per Camera Doppia Alta Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(this.getRoomTypeService().findRoomTypesByIdStructure(structure).get(1));
		roomPriceList.setSeason(this.getSeasonService().findSeasonByName(structure.getId(),"Alta Stagione"));
		roomPriceList.setConvention(this.getConventionService().findConventionsByIdStructure(structure).get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 90.0;//lun
		prices[1] = 90.0;//mar
		prices[2] = 90.0;//mer
		prices[3] = 90.0;//gio
		prices[4] = 90.0;//ven
		prices[5] = 90.0;//sab
		prices[6] = 90.0;//dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(2);
		prices = new Double[7];
		prices[0] = 130.0;//lun
		prices[1] = 130.0;//mar
		prices[2] = 130.0;//mer
		prices[3] = 130.0;//gio
		prices[4] = 130.0;//ven
		prices[5] = 130.0;//sab
		prices[6] = 130.0;//dom
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);	
		
		this.getRoomPriceListService().insertRoomPriceList(structure, roomPriceList);
	}
	
	private void buildImages(Structure structure){
		
		Image img = null;
		
		img = new Image();
		img.setId(structure.nextKey());
		img.setName("Facciata");
		img.setFileName("facciata.jpg");
		this.getStructureService().insertImage(structure, img);
	}
	
	private void buildStructureFacilities(Structure structure){
		
		StructureFacility structFacility = null;
		
		structFacility = new StructureFacility();
		structFacility.setId(structure.nextKey());
		structFacility.setName("Restaurant");
		structFacility.setFileName("restaurant.png");
		this.getStructureService().insertStructureFacility(structure, structFacility);
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
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



	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}



	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
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



	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}



	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}



	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}



	public void setExtraPriceListService(ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
	}



	public UserService getUserService() {
		return userService;
	}



	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
