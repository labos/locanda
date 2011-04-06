package model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.listini.ListinoCamera;
import model.listini.Season;

public class Structure {
	private String name;
	private String email;
	private List<Room> rooms;
	private TreeSet<Integer> keys;
	private List<RoomFacility> roomFacilities;
	private List<Guest> guests;
	private List<Booking> bookings;
	private List<Extra> extras;
	private List<Season> seasons;
	private List<ListinoCamera> listiniCamere;
	
	
	public Structure(){
		this.setRooms(new ArrayList<Room>());
		this.setKeys(new TreeSet<Integer>());
		this.getKeys().add(1);
		this.setRoomFacilities(new ArrayList<RoomFacility>());
		this.setGuests(new ArrayList<Guest>());
		this.setBookings(new ArrayList<Booking>());
		this.setExtras(new ArrayList<Extra>());
		this.setSeasons(new ArrayList<Season>());
		this.setListiniCamere(new ArrayList<ListinoCamera>());
	}
	
	public Integer nextKey(){
		Integer ret = 0;
		
		ret = this.getKeys().last();
		ret = ret + 1;
		this.getKeys().add(ret);
		return ret;
	}
	
	//Room
	public Boolean hasRoomNamed(String name){
		Boolean ret = false;
		
		for(Room each: this.getRooms()){
			if(each.getName().equals(name)){
				return true;
			}
		}
		return ret;
	}
	
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
		//               dateIn |-------------------------| dateOut    
		//       |------------------|    |---|     |---------------------------|    roomBookings
		//             aBooking         aBooking         aBooking
		
		for(Booking aBooking: roomBookings){
			if(aBooking.getDateOut().after(dateIn) && aBooking.getDateOut().before(dateOut)){
				return false;
			}
			if(aBooking.getDateIn().after(dateIn) && aBooking.getDateIn().before(dateOut)){
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
		oldBooking.setSubtotal(booking.getSubtotal());
		oldBooking.setNotes(booking.getNotes());
		oldBooking.setRoom(booking.getRoom());
		oldBooking.setExtras(booking.getExtras());
		
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
	public Boolean addListinoCamera(ListinoCamera listino){
		return this.getListiniCamere().add(listino);
		
	}
	
	public Boolean removeListinoCamera(ListinoCamera listino){
		return this.getListiniCamere().remove(listino);
	}
	
	public ListinoCamera findListinoCameraById(Integer id){
		ListinoCamera ret = null;
		
		for(ListinoCamera each: this.getListiniCamere()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return ret;
	}
	
	public List<ListinoCamera> findListiniCamereByYear(Integer year){
		List<ListinoCamera> ret = new ArrayList<ListinoCamera>();		
		return ret;
	}
	
	public Boolean updateListinoCamera(ListinoCamera listino){		
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

	public List<ListinoCamera> getListiniCamere() {
		return listiniCamere;
	}

	public void setListiniCamere(List<ListinoCamera> listiniCamere) {
		this.listiniCamere = listiniCamere;
	}
	public List<RoomFacility> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(List<RoomFacility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}
	

}
