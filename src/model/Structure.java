package model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Structure {
	private String name;
	private String email;
	private List<Room> rooms;
	private TreeSet<Integer> keys;
	private List<RoomFacility> roomFacilities;
	private List<Guest> guests;
	private List<Booking> bookings;
	
	
	public Structure(){
		this.rooms = new ArrayList<Room>();
		this.keys = new TreeSet<Integer>();
		this.roomFacilities = new ArrayList<RoomFacility>();
		this.guests = new ArrayList<Guest>();
		this.bookings = new ArrayList<Booking>();
		this.keys.add(1);
	}
	
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
	
	public Set<String> findAllRoomTypes(){
		Set<String> ret = new TreeSet<String>();
		
		for(Room each: this.getRooms()){
			ret.add(each.getRoomType());
		}
		return ret;
	}
	
	public void addRoom(Room aRoom){
		this.getRooms().add(aRoom);
	}	
	
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
	
	public Integer nextKey(){
		Integer ret = 0;
		
		ret = this.getKeys().last();
		ret = ret + 1;
		this.getKeys().add(ret);
		return ret;
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
	
	public Booking findBookingById(Integer id){
		Booking ret = null;
		
		for(Booking each: this.getBookings()){
			if(each.getId().equals(id)){
				return ret;
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
	
	public List<RoomFacility> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(List<RoomFacility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}

	public boolean deleteRoom(Room aRoom){
		return this.getRooms().remove(aRoom);
	}
	
	public boolean addGuest(Guest aGuest) {
		return this.guests.add(aGuest);
	}
	
	public boolean deleteGuest(Guest aGuest) {
		return this.guests.remove(aGuest);
	}
	
	public boolean addBooking(Booking aBooking) {
		return this.bookings.add(aBooking);
	}
	
	public boolean deleteBooking(Booking aBooking) {
		return this.bookings.remove(aBooking);
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
	
	

}
