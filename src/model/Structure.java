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
package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.RoomPriceList;
import model.listini.Season;

@XmlRootElement
@JsonIgnoreProperties({"rooms","roomTypes","roomFacilities","facilities","guests","bookings","extras","seasons","roomPriceLists","extraPriceLists","images","conventions","keys"})
public class Structure implements Serializable {
	
	private Integer id;
	
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
	private Integer id_user;
	
	
	private List<Room> rooms;	
	private List<RoomType> roomTypes;	
	private List<Facility> roomFacilities;	
	private List<Facility> facilities;		
	private List<Guest> guests;	
	private List<Booking> bookings;	
	private List<Extra> extras;	
	private List<Season> seasons;	
	private List<RoomPriceList> roomPriceLists;	
	private List<ExtraPriceList> extraPriceLists;	
	private List<Image> images;	
	private List<Convention> conventions;	
	private TreeSet<Integer> keys;
	
	public Structure(){
		this.setRooms(new ArrayList<Room>());
		this.setKeys(new TreeSet<Integer>());
		this.getKeys().add(1);
		this.setRoomFacilities(new ArrayList<Facility>());
		this.setGuests(new ArrayList<Guest>());
		this.setBookings(new ArrayList<Booking>());
		this.setExtras(new ArrayList<Extra>());
		this.setSeasons(new ArrayList<Season>());
		this.setRoomPriceLists(new ArrayList<RoomPriceList>());
		this.setExtraPriceLists(new ArrayList<ExtraPriceList>());
		this.setImages(new ArrayList<Image>());
		this.setRoomTypes(new ArrayList<RoomType>());
		this.setFacilities(new ArrayList<Facility>());
		this.setConventions(new ArrayList<Convention>());
	}
	
	public Integer nextKey(){
		Integer ret = 0;
		ret = this.getKeys().last();
		ret = ret + 1;
		this.getKeys().add(ret);
		return ret;
	}	
	
	public boolean hasRoomPhotoNamed(String roomPhotoName){
		/*	IN PROGRESS...	*/
		return false;
	}
	//Room		
	//RoomFacility	//RoomTypeFacility		
	//Booking			
	//Guest		
	//Extra	
	//RoomType		
	//Season	
	//Room Price List
	//Extra Price List	
	//Convention	
	// Prezzi	
	//Structure Images
	
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
	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
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
	public List<ExtraPriceList> getExtraPriceLists() {
		return extraPriceLists;
	}
	public void setExtraPriceLists(List<ExtraPriceList> extraPriceLists) {
		this.extraPriceLists = extraPriceLists;
	}
	public List<Facility> getRoomFacilities() {
		return roomFacilities;
	}
	public void setRoomFacilities(List<Facility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public List<Facility> getFacilities() {
		return facilities;
	}
	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}
	public List<Convention> getConventions() {
		return conventions;
	}
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_user() {
		return id_user;
	}
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}
	
}