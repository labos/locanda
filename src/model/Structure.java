package model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;

import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

public class Structure {
	private String name;
	private String email;
	private String url;
	private String phone;
	private String fax;
	private String address;
	private String city;
	private String country;
	private String zipCode;
	private String notes;
	
	private List<Room> rooms;
	private TreeSet<Integer> keys;
	private List<RoomFacility> roomFacilities;
	private List<Guest> guests;
	private List<Booking> bookings;
	private List<Extra> extras;
	private List<Season> seasons;
	private List<RoomPriceList> roomPriceLists;
	
	
	public Structure(){
		this.setRooms(new ArrayList<Room>());
		this.setKeys(new TreeSet<Integer>());
		this.getKeys().add(1);
		this.setRoomFacilities(new ArrayList<RoomFacility>());
		this.setGuests(new ArrayList<Guest>());
		this.setBookings(new ArrayList<Booking>());
		this.setExtras(new ArrayList<Extra>());
		this.setSeasons(new ArrayList<Season>());
		this.setRoomPriceLists(new ArrayList<RoomPriceList>());
	}
	
	public Integer nextKey(){
		Integer ret = 0;
		
		ret = this.getKeys().last();
		ret = ret + 1;
		this.getKeys().add(ret);
		return ret;
	}
	
	//Room
	/*
	public Boolean hasRoomNamed(String name){
		Boolean ret = false;
		
		for(Room each: this.getRooms()){
			if(each.getName().equals(name)){
				return true;
			}
		}
		return ret;
	}*/
	
	public Room findRoomByName(String name){
		Room ret = null;
		
		for(Room each: this.getRooms()){
			if(each.getName().equals(name)){
				return each;
			}
		}
		return ret;
	}
	
	public Room findRoomById(Integer id){
		Room ret = null;
		
		for(Room each: this.getRooms()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}
	
	public void addRoom(Room aRoom){
		this.getRooms().add(aRoom);
	}	
	
	public Boolean updateRoom(Room room){
		
		Room originalRoom = this.findRoomById(room.getId());
		if(originalRoom==null){
			return false;
		}
		originalRoom.setName(room.getName());
		originalRoom.setMaxGuests(room.getMaxGuests());
		originalRoom.setNotes(room.getNotes());
		originalRoom.setPrice(room.getPrice());
		originalRoom.setRoomType(room.getRoomType());
		originalRoom.setFacilities(room.getFacilities());
		return true;
	}
	
	public boolean deleteRoom(Room aRoom){
		return this.getRooms().remove(aRoom);
	}
	
	
	public Boolean hasRoomFreeInPeriod(Integer roomId, Date dateIn, Date dateOut){
		//Estraggo i Booking della camera con roomId dato
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: this.getBookings()){
			if(each.getRoom().getId().equals(roomId)){
				roomBookings.add(each);
			}
		}
		//               dateIn |-------------------------| dateOut              dateIn |-----| dateOut 
		//       |------------------|    |---|     |---------------------------|    |------------------|  roomBookings
		//             aBooking         aBooking         aBooking							aBooking 
		
		for(Booking aBooking: roomBookings){
			if(aBooking.getDateOut().after(dateIn) && (aBooking.getDateOut().compareTo(dateOut)<= 0 ) ){
				return false;
				
			}
			if(aBooking.getDateIn().after(dateIn) && aBooking.getDateIn().before(dateOut)){
				return false;
			}
			
			if(aBooking.getDateIn().after(dateIn) && aBooking.getDateOut().before(dateOut)){
				return false;
			}
			if(aBooking.getDateOut().after(dateOut) && aBooking.getDateIn().compareTo(dateIn)<=0){
				return false;
			}
		}
		
		return true;
	}
	
	public Boolean hasRoomFreeForBooking(Booking booking){
		//Estraggo i Booking della camera con roomId dato
		List<Booking> roomBookings = new ArrayList<Booking>();
		
		for(Booking each: this.getBookings()){
			if( each.getRoom().getId().equals(booking.getRoom().getId()) && !each.equals(booking)    ){
				roomBookings.add(each);
			}
		}
		//               dateIn |-------------------------| dateOut    
		//       |------------------|    |---|     |---------------------------|    roomBookings
		//             aBooking         aBooking         aBooking
		
		for(Booking aBooking: roomBookings){
			if(aBooking.getDateOut().after(booking.getDateIn()) && aBooking.getDateOut().before(booking.getDateOut())){
				return false;
			}
			if(aBooking.getDateIn().after(booking.getDateIn()) && aBooking.getDateIn().before(booking.getDateOut())){
				return false;
			}
		}
		
		return true;
	}
	
	
	//RoomFacility	
	public Boolean addRoomFacility(RoomFacility roomFacility){
		roomFacility.setId(this.nextKey());
		return this.getRoomFacilities().add(roomFacility);
	}	
	
	public boolean hasRoomFacilityNamed(String roomFacilityName){
		for(RoomFacility each: this.getRoomFacilities()){
			if(each.getName().equals(roomFacilityName)){
				return true;
			}
		}
		return false;
	}
	
	public List<RoomFacility> findFacilitiesByIds(List<Integer> ids){
		List<RoomFacility> ret = new ArrayList<RoomFacility>();
		for(Integer each:ids){
			RoomFacility aRoomFacility = this.findFacilityById(each);
			ret.add(aRoomFacility);
		}
		return ret;
	}
	
	public RoomFacility findFacilityById(Integer id){
		RoomFacility ret = null;
		for (RoomFacility each:this.getRoomFacilities()){
			if (each.getId().equals(id)) {
				return each;
			}
		}
		return ret;
	}
	
	
	//Booking	
	public boolean addBooking(Booking aBooking) {
		return this.bookings.add(aBooking);
	}
	
	public Boolean updateBooking(Booking booking){
		
		Booking oldBooking = this.findBookingById(booking.getId());
		if(oldBooking==null){
			return false;
		}
		oldBooking.setDateIn(booking.getDateIn());
		oldBooking.setDateOut(booking.getDateOut());
		oldBooking.setNrGuests(booking.getNrGuests());
		oldBooking.setExtraSubtotal(booking.getExtraSubtotal());
		oldBooking.setRoomSubtotal(booking.getRoomSubtotal());
		oldBooking.setNotes(booking.getNotes());
		oldBooking.setRoom(booking.getRoom());
		oldBooking.setExtras(booking.getExtras());
		oldBooking.setAdjustments(booking.getAdjustments());
		oldBooking.setPayments(booking.getPayments());
		
		return true;
	}
	
	public boolean deleteBooking(Booking aBooking) {
		return this.bookings.remove(aBooking);
	}
	
	public Booking findBookingById(Integer id){
		Booking ret = null;
		
		for(Booking each: this.getBookings()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}
	
	public List<Booking> findBookingsByGuestId(Integer guestId){
		List<Booking> ret = new ArrayList<Booking>();
		
		for(Booking each: this.getBookings()){
			if(each.getGuest()!=null && each.getGuest().getId().equals(guestId)){
				ret.add(each);
			}
		}
		return ret;
	}
	
	

	//Guest	
	public boolean addGuest(Guest aGuest) {
		return this.guests.add(aGuest);
	}
	
	public Boolean updateGuest(Guest guest){
		
		Guest oldGuest = this.findGuestById(guest.getId());
		if(oldGuest==null){
			return false;
		}
		oldGuest.setFirstName(guest.getFirstName());
		oldGuest.setLastName(guest.getLastName());
		oldGuest.setAddress(guest.getAddress());
		oldGuest.setCountry(guest.getCountry());
		oldGuest.setEmail(guest.getEmail());
		oldGuest.setNotes(guest.getNotes());
		oldGuest.setPhone(guest.getPhone());
		oldGuest.setZipCode(guest.getZipCode());
		return true;
	}	
	
	public boolean deleteGuest(Guest aGuest) {
		return this.guests.remove(aGuest);
	}
	
	public Guest findGuestById(Integer id){
		Guest ret = null;
		for (Guest each : this.getGuests()) {
			if (each.getId().equals(id)) {
				return each;
			}
		}
		
		return ret;
	}
	
	
	//Extra
	public boolean addExtra(Extra anExtra) {
		return this.extras.add(anExtra);
	}
	
	public Boolean updateExtra(Extra extra){
		
		Extra oldExtra = this.findExtraById(extra.getId());
		if(oldExtra==null){
			return false;
		}
		oldExtra.setName(extra.getName());
		oldExtra.setPrice(extra.getPrice());
		oldExtra.setTimePriceType(extra.getTimePriceType());
		oldExtra.setResourcePriceType(extra.getResourcePriceType());
		
		return true;
	}
	
	
	public boolean deleteExtra(Extra anExtra) {
		return this.extras.remove(anExtra);
	}
	
	public Extra findExtraById(Integer id){
		Extra ret = null;
		
		for (Extra each : this.getExtras()) {
			if (each.getId().equals(id)) {
				return each;
			}
		}
		
		return ret;
	}
	
	public List<Extra> findExtrasByIds(List<Integer> ids){
		List<Extra> ret = new ArrayList<Extra>();
		for(Integer each:ids){
			Extra anExtra = this.findExtraById(each);
			ret.add(anExtra);
		}
		return ret;
	}	
	
	
	
	//RoomTypes
	public Set<String> findAllRoomTypes(){
		Set<String> ret = new TreeSet<String>();
		
		for(Room each: this.getRooms()){
			ret.add(each.getRoomType());
		}
		return ret;
	}
	
	
	//Season
	public Boolean addSeason(Season aSeason){
		return this.getSeasons().add(aSeason);
		
	}
	
	public Boolean removeSeason(Season aSeason){
		
		return this.getSeasons().remove(aSeason);		
		
	}
	
	
	public Season findSeasonById(Integer id){
		for(Season each: this.getSeasons()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}
	
	public List<Season> findSeasonsByYear(Integer year){
		List<Season> ret = new ArrayList<Season>();
		
		for(Season each: this.getSeasons()){
			if(each.getYear().equals(year)){
				ret.add(each);
			}
		}
		return ret;
	}
	
	
	public Boolean updateSeason(Season aSeason){
		Season oldSeason = this.findSeasonById(aSeason.getId());
		
		if(oldSeason == null){
			return false;
		}
		oldSeason.setName(aSeason.getName());
		oldSeason.setYear(aSeason.getYear());
		oldSeason.setPeriods(aSeason.getPeriods());
		return true;
	}
	
	public Season findSeasonByName(String name){
		Season ret = null;
		
		for(Season each: this.getSeasons()){
			if(each.getName().equalsIgnoreCase(name)){
				return each;
			}
		}
		return ret;
	}
	
	
	//Listino Camera
	public Boolean addRoomPriceList(RoomPriceList listino){
		return this.getRoomPriceLists().add(listino);
		
	}
	
	public Boolean removeRoomPriceList(RoomPriceList listino){
		return this.getRoomPriceLists().remove(listino);
	}
	
	public RoomPriceList findRoomPriceListById(Integer id){
		RoomPriceList ret = null;
		
		for(RoomPriceList each: this.getRoomPriceLists()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}
	
	/*
	public Double calculateRoomSubtotal(Room room, Date dateIn, Date dateOut,  Agevolazione agevolazione, Integer numGuests){
		Double ret = 0.0;
		//Prendere i giorni dell'intervallo dateIn dateOut
		//Per ogni giorno dell'intervallo ricavare il listino
		//Chiedere il prezzo del giorno al listino
		List<Date> bookingDates = null;
		ListinoCamera listinoCameraDelGiorno;
		Integer dayOfWeek = 0;
		Calendar calendar;
		
		bookingDates = this.calculateBookingDates(dateIn, dateOut);
		for(Date aBookingDate: bookingDates){
			listinoCameraDelGiorno = this.findListinoCamera(room, aBookingDate);
			calendar = Calendar.getInstance();
			calendar.setTime(aBookingDate);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			ret = ret + listinoCameraDelGiorno.findRoomPrice(numGuests, dayOfWeek);
		}			
		return ret;
	}
	*/
	
	public Double calculateRoomSubtotalForBooking(Booking booking){
		Double ret = 0.0;
		List<Date> bookingDates = null;
		RoomPriceList listinoCameraDelGiorno;
		Integer dayOfWeek = 0;
		Calendar calendar;
		
		bookingDates = this.calculateBookingDates(booking.getDateIn(), booking.getDateOut());
		for(Date aBookingDate: bookingDates){
			listinoCameraDelGiorno = this.findRoomPriceListByRoomAndDate(booking.getRoom(), aBookingDate);
			calendar = Calendar.getInstance();
			calendar.setTime(aBookingDate);
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			ret = ret + listinoCameraDelGiorno.findRoomPrice(booking.getNrGuests(), dayOfWeek);
		}			
		return ret;
	}
	
	public Double calculateExtraSubtotalForBooking(Booking booking){
		Double ret = 0.0;
		
		for(Extra each: booking.getExtras()){
			ret = ret + each.getPrice();
		}		
		return ret;
	}
	
	private List<Date> calculateBookingDates(Date dateIn, Date dateOut){
		List<Date> bookingDates = null; 
		Date current = null;
		Integer i = 0;
		
		bookingDates = new ArrayList<Date>();
		if(dateIn!=null && dateOut!=null){
			current  = DateUtils.addDays(dateIn, i );		
			while(DateUtils.truncatedCompareTo(current, dateOut,Calendar.DAY_OF_MONTH ) < 0){
				bookingDates.add(current);
				i = i + 1;
				current  = DateUtils.addDays(dateIn, i );
			}	
		}
		
		return bookingDates;
	}
	
	public RoomPriceList findRoomPriceListByRoomAndDate(Room room, Date date){
		RoomPriceList ret = null;
		Season season = null;
		
		season = this.findSeasonByDate(date);
		for(RoomPriceList each: this.getRoomPriceLists()){
			if(each.getSeason().getName().equalsIgnoreCase(season.getName()) &&
					each.getRoomType().equalsIgnoreCase(room.getRoomType()) ){
				return each;
			}
		}		
		return ret;
	}
	
	public RoomPriceList findRoomPriceListBySeasonAndRoomType(Season season, String roomType) {
		RoomPriceList ret = null;
		
		for(RoomPriceList each: this.getRoomPriceLists()) {
			if (each.getSeason().equals(season) && each.getRoomType().equalsIgnoreCase(roomType)) {
				return each;
			}
		}
		return ret;
	}
	
	public Season findSeasonByDate(Date date){
		Season ret = null;
		
		for(Season each: this.getSeasons()){
			if(each.includesDate(date)){
				return each;
			}
		}
		return ret;
	}
	
	public Boolean updateListinoCamera(RoomPriceList listino){		
		return true;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public TreeSet<Integer> getKeys() {
		return keys;
	}

	public void setKeys(TreeSet<Integer> keys) {
		this.keys = keys;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

	public List<RoomPriceList> getRoomPriceLists() {
		return roomPriceLists;
	}

	public void setRoomPriceLists(List<RoomPriceList> listiniCamere) {
		this.roomPriceLists = listiniCamere;
	}
	public List<RoomFacility> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(List<RoomFacility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}
	

}
