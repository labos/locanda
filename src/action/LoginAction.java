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

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class LoginAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private String email = null;
	private String password;
	
	@Actions(value={
			@Action(value="/login", results={
					@Result(name="input",location="/WEB-INF/jsp/login.jsp"),
					@Result(name="loginSuccess", location="/homeLogged.jsp"),	
					@Result(name="loginError", location="/login.jsp")
			})
			
	})	
	
	public String execute(){
		String ret = null;	
		User user = null;
		Structure structure = null;
		Locale locale = null;
		SimpleDateFormat sdf = null;
		String datePattern = null;
		
		if(this.getEmail().trim().equals("locanda@locanda.it") &&
				this.getPassword().trim().equals("locanda")){
			user = new User();
			
			user.setEmail(this.getEmail());
			structure = this.buildStructure();
			user.setStructure(structure);
			this.getSession().put("user", user);
			locale = this.getLocale();
			sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT,locale);
			datePattern = sdf.toPattern();
			this.getSession().put("datePattern", datePattern);
			ret = "loginSuccess";
		}else{
			this.getSession().put("user", null);
			ret = "loginError";
		}		
		return ret;
	}
	

	
	private Structure buildStructure(){
		Structure ret = null;
			
		
		ret = new Structure();
		ret.setName("Polaris");
		ret.setEmail("polaris@locanda.it");
		ret.setPhone("+39 070123456");
		ret.setAddress("Località Piscinamanna");
		ret.setCountry("Italy");
		ret.setZipCode("09135");
		ret.setUrl("http://www.sardegnaricerche.it");
		ret.setNotes("struttura ricettiva alberghiera a 5 stelle");
		ret.setFax("+39 0705678383");
		ret.setCity("Pula");
		
		this.buildRoomFacilities(ret);
		this.buildRoomTypes(ret);
		this.buildRooms(ret);
		this.buildGuests(ret);
		this.buildSeasons(ret);
		this.buildConventions(ret);
		this.buildRoomPriceLists(ret);
		this.buildExtras(ret);
		this.buildExtraPriceLists(ret);
		this.buildBookings(ret);
		this.buildImages(ret);
		this.buildStructureFacilities(ret);
		return ret;		
	}
	
	private void buildRooms(Structure structure){
		Room aRoom = null;		
			
		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("101");
		aRoom.addRoomFacility(structure.getRoomFacilities().get(0));
		aRoom.addRoomFacility(structure.getRoomFacilities().get(2));
		aRoom.setRoomType(structure.getRoomTypes().get(0));
		structure.addRoom(aRoom);
		
		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("201");
		aRoom.addRoomFacility(structure.getRoomFacilities().get(1));
		aRoom.setRoomType(structure.getRoomTypes().get(1));
		structure.addRoom(aRoom);
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
		structure.addRoomTypeFacility(roomTypeFacility);
		
		roomTypeFacility = new RoomFacility();
		roomTypeFacility.setId(structure.nextKey());
		roomTypeFacility.setName("air conditioned");
		roomTypeFacility.setFileName("air_conditioned.png");
		aRoomType.addRoomTypeFacility(roomTypeFacility);
		structure.addRoomTypeFacility(roomTypeFacility);
		
		aRoomType.addRoomTypeImage(image);
		
		structure.addRoomType(aRoomType);
		
		image = new Image();
		image.setId(structure.nextKey());
		image.setName("doppia");
		image.setFileName("double.jpg");
		aRoomType = new RoomType();
		aRoomType.setId(structure.nextKey());
		aRoomType.setName("doppia");
		aRoomType.setMaxGuests(2);
		aRoomType.addRoomTypeImage(image);
		structure.addRoomType(aRoomType);
	}
	
	private void buildRoomFacilities(Structure structure){
		RoomFacility aRoomFacility = null;
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("AAD");
		aRoomFacility.setFileName("AAD.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("BAR");
		aRoomFacility.setFileName("BAR.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("PHO");
		aRoomFacility.setFileName("PHO.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("RAD");
		aRoomFacility.setFileName("RAD.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("TEL");
		aRoomFacility.setFileName("TEL.gif");
		structure.addRoomFacility(aRoomFacility);
		
	}
	
	private void buildGuests(Structure structure){
		Guest aGuest = null;
		
		aGuest = new Guest();
		aGuest.setId(structure.nextKey());
		aGuest.setFirstName("Paolo");
		aGuest.setLastName("Rossi");
		aGuest.setCountry("Italy");
		aGuest.setAddress("Roma, Via Rossini 82");
		aGuest.setEmail("paolo@rossi.it");
		aGuest.setPhone("06-6789458");
		aGuest.setZipCode("09123");
		aGuest.setNotes("Note su Paolo Rossi");
		structure.addGuest(aGuest);
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
		
		aBooking = new Booking();
		aRoom = structure.findRoomByName("101");
		aGuest = structure.getGuests().get(0);
		extras = new ArrayList<Extra>();
		extras.add(structure.getExtras().get(0));
		extras.add(structure.getExtras().get(1));
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
		aBooking.setConvention(structure.getConventions().get(0));
		roomSubtotal = structure.calculateRoomSubtotalForBooking(aBooking);
		aBooking.setRoomSubtotal(roomSubtotal);
		
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
		aBooking.setStatus("checkout");
		structure.addBooking(aBooking);		
	}
	
	private void buildExtras(Structure structure){
		Extra anExtra = null;
		
		anExtra = new Extra();
		anExtra.setId(structure.nextKey());
		anExtra.setName("Breakfast");
		anExtra.setResourcePriceType("per Room");
		anExtra.setTimePriceType("per Night");
		structure.addExtra(anExtra);
		
		anExtra = new Extra();
		anExtra.setId(structure.nextKey());
		anExtra.setName("Parking");
		anExtra.setResourcePriceType("per Room");
		anExtra.setTimePriceType("per Night");
		structure.addExtra(anExtra);
	}
	
	private void buildSeasons(Structure structure){
		Season aSeason = null;
		Period aPeriod = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		
		//Bassa Stagione
		aSeason = new Season();
		aSeason.setId(structure.nextKey());
		aSeason.setName("Bassa Stagione");
		aSeason.setYear(2011);
		
		aPeriod = new Period();
		aPeriod.setId(structure.nextKey());		
		try {
			aPeriod.setStartDate(sdf.parse("01/01/2011"));
			aPeriod.setEndDate(sdf.parse("30/04/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		aSeason.addPeriod(aPeriod);
		
		aPeriod = new Period();
		aPeriod.setId(structure.nextKey());		
		try {
			aPeriod.setStartDate(sdf.parse("01/09/2011"));
			aPeriod.setEndDate(sdf.parse("31/12/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		aSeason.addPeriod(aPeriod);		
		structure.addSeason(aSeason);
		
		//Alta Stagione
		aSeason = new Season();
		aSeason.setId(structure.nextKey());
		aSeason.setName("Alta Stagione");
		aSeason.setYear(2011);
		
		aPeriod = new Period();
		aPeriod.setId(structure.nextKey());		
		try {
			aPeriod.setStartDate(sdf.parse("01/05/2011"));
			aPeriod.setEndDate(sdf.parse("31/08/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		aSeason.addPeriod(aPeriod);		
		structure.addSeason(aSeason);
	}
	
	private void buildConventions(Structure structure){
		Convention convention = null;
		
		//convenzione di default
		convention = new Convention();
		convention.setId(structure.nextKey());
		convention.setName("agevolazione Default");
		convention.setDescription("Default convention");
		convention.setActivationCode("XXX");
		
		structure.addConvention(convention);
	}
	
	private void buildExtraPriceLists(Structure structure){
		ExtraPriceList extraPriceList = null;
		ExtraPriceListItem extraPriceListItem = null;
		Double prices[] = null;
		Double price = null;
		
		//Listino Extra per Camera Singola Bassa Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(0));
		extraPriceList.setSeason(structure.findSeasonByName("Bassa Stagione"));
		extraPriceList.setConvention(structure.getConventions().get(0));
		for (Extra eachExtra : structure.getExtras()) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			prices = new Double[7];
			prices[0] = 10.0;//lun
			prices[1] = 10.0;//mar
			prices[2] = 10.0;//mer
			prices[3] = 10.0;//gio
			prices[4] = 12.0;//ven
			prices[5] = 15.0;//sab
			prices[6] = 15.0;//dom
			extraPriceListItem.setPrices(prices);
			price = 10.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		structure.addExtraPriceList(extraPriceList);
		
		//Listino Extra per Camera Singola Alta Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(0));
		extraPriceList.setSeason(structure.findSeasonByName("Alta Stagione"));
		extraPriceList.setConvention(structure.getConventions().get(0));
		for (Extra eachExtra : structure.getExtras()) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			prices = new Double[7];
			prices[0] = 10.0;//lun
			prices[1] = 10.0;//mar
			prices[2] = 10.0;//mer
			prices[3] = 10.0;//gio
			prices[4] = 12.0;//ven
			prices[5] = 15.0;//sab
			prices[6] = 15.0;//dom
			extraPriceListItem.setPrices(prices);
			price = 15.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		structure.addExtraPriceList(extraPriceList);
		
		//Listino Extra per Camera Doppia Bassa Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(1));
		extraPriceList.setSeason(structure.findSeasonByName("Bassa Stagione"));
		extraPriceList.setConvention(structure.getConventions().get(0));
		for (Extra eachExtra : structure.getExtras()) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			prices = new Double[7];
			prices[0] = 10.0;//lun
			prices[1] = 10.0;//mar
			prices[2] = 10.0;//mer
			prices[3] = 10.0;//gio
			prices[4] = 12.0;//ven
			prices[5] = 15.0;//sab
			prices[6] = 15.0;//dom
			extraPriceListItem.setPrices(prices);
			price = 10.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		structure.addExtraPriceList(extraPriceList);
		
		//Listino Extra per Camera Doppia Alta Stagione
		extraPriceList = new ExtraPriceList();
		extraPriceList.setId(structure.nextKey());
		extraPriceList.setRoomType(structure.getRoomTypes().get(1));
		extraPriceList.setSeason(structure.findSeasonByName("Alta Stagione"));
		extraPriceList.setConvention(structure.getConventions().get(0));
		for (Extra eachExtra : structure.getExtras()) {
			extraPriceListItem = new ExtraPriceListItem();
			extraPriceListItem.setId(structure.nextKey());
			extraPriceListItem.setExtra(eachExtra);
			prices = new Double[7];
			prices[0] = 10.0;//lun
			prices[1] = 10.0;//mar
			prices[2] = 10.0;//mer
			prices[3] = 10.0;//gio
			prices[4] = 12.0;//ven
			prices[5] = 15.0;//sab
			prices[6] = 15.0;//dom
			extraPriceListItem.setPrices(prices);
			price = 15.0;
			extraPriceListItem.setPrice(price);
			extraPriceList.addItem(extraPriceListItem);
		}
		structure.addExtraPriceList(extraPriceList);			
	}
	
	private void buildRoomPriceLists(Structure structure){
		RoomPriceList roomPriceList = null;
		RoomPriceListItem roomPriceListItem = null;
		Double[] prices = null;
		
		//Listino Room per Camera Singola Bassa Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(structure.getRoomTypes().get(0));
		roomPriceList.setSeason(structure.findSeasonByName("Bassa Stagione"));
		roomPriceList.setConvention(structure.getConventions().get(0));
		roomPriceListItem = new RoomPriceListItem();
		roomPriceListItem.setId(structure.nextKey());
		roomPriceListItem.setNumGuests(1);
		roomPriceListItem.setPrices(prices);
		roomPriceList.addItem(roomPriceListItem);
		structure.addRoomPriceList(roomPriceList);
		
		//Listino Room per Camera Singola Alta Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(structure.getRoomTypes().get(0));
		roomPriceList.setSeason(structure.findSeasonByName("Alta Stagione"));
		roomPriceList.setConvention(structure.getConventions().get(0));
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
		structure.addRoomPriceList(roomPriceList);
		
		//Listino Room per Camera Doppia Bassa Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(structure.getRoomTypes().get(1));
		roomPriceList.setSeason(structure.findSeasonByName("Bassa Stagione"));
		roomPriceList.setConvention(structure.getConventions().get(0));
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
		
		structure.addRoomPriceList(roomPriceList);
		
		//Listino Room per Camera Doppia Alta Stagione
		roomPriceList =	new RoomPriceList();
		roomPriceList.setId(structure.nextKey());
		roomPriceList.setRoomType(structure.getRoomTypes().get(1));
		roomPriceList.setSeason(structure.findSeasonByName("Alta Stagione"));
		roomPriceList.setConvention(structure.getConventions().get(0));
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
		
		structure.addRoomPriceList(roomPriceList);				
	}
	
	private void buildImages(Structure structure){
		
		Image img = new Image();
		img.setId(structure.nextKey());
		img.setName("Facciata");
		img.setFileName("facciata.jpg");
		structure.addStructureImage(img);
	}
	
	private void buildStructureFacilities(Structure structure){
		
		StructureFacility structFacility = new StructureFacility();
		structFacility.setId(structure.nextKey());
		structFacility.setName("Restaurant");
		structFacility.setFileName("restaurant.png");
		structure.addStructureFacility(structFacility);
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
	

}
