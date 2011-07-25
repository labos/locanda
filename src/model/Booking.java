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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import service.StructureServiceImpl;

import model.internal.Message;
import model.listini.Convention;

import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.Season;

public class Booking implements Serializable{
	
	private Integer id;
	
	private Guest booker;
	private Integer nrGuests = 1;
	private Room room;
	private Date dateIn;
	private Date dateOut;
	private Double roomSubtotal = 0.0;
	private Double extraSubtotal = 0.0;
	private String notes;
	private List<Extra> extras;
	private List<Adjustment> adjustments;
	private List<Payment> payments = null;
	private String status = "confirmed";
	private List<Guest> guests = null;
	private Convention convention = null;
	private List<ExtraItem> extraItems;
	private Integer id_structure = null;
	private Integer id_convention = null;
	private Integer id_room = null;
	
	
	
	
	public Booking(){
		this.extras = new ArrayList<Extra>();
		this.adjustments = new ArrayList<Adjustment>();
		this.payments = new ArrayList<Payment>();
		this.guests = new ArrayList<Guest>();
		this.extraItems = new ArrayList<ExtraItem>();
	}
	
	public Integer calculateNumNights(){
		Long millis; 
		Integer days = 0;
		
		if((this.getDateOut()!=null) && (this.getDateIn()!=null)){
			millis = this.getDateOut().getTime() - this.getDateIn().getTime();
			days = (int) (millis/(1000*3600*24));
		}		
		return days;
	}
	
	public Boolean checkDates() {
		Boolean ret = true;

		if (DateUtils.truncatedCompareTo(this.getDateOut(), this.getDateIn(),
				Calendar.DAY_OF_MONTH) <= 0) {
			return false;
		}
		return ret;
	}
	
	public Double calculateAdjustmentsSubtotal(){
		Double ret = 0.0;
		
		for(Adjustment each: this.getAdjustments()){
			ret = ret + each.getAmount();
		}
		return ret;
	}
	
	public Double calculatePaymentsSubtotal(){
		Double ret = 0.0;
		
		for(Payment each: this.getPayments()){
			ret = ret + each.getAmount();
		}
		return ret;
	}
	
	
	public ExtraItem findExtraItem(Extra extra){
		ExtraItem ret = null;
		
		for(ExtraItem each: this.getExtraItems()){
			if(each.getExtra().equals(extra)){
				each.setExtra(extra);
				return each;
			}
		}
		return ret;
	}
	
	public Integer calculateExtraItemMaxQuantity(Extra extra) {
		Integer ret = 0;
		Integer numNights = 0;
		
		numNights = this.calculateNumNights();
		System.out.println(extra.getTimePriceType());	
		if (extra.getTimePriceType().equals("per Night")) {
			if (extra.getResourcePriceType().equals("per Room")) {
				ret = numNights;
			}
			else ret = numNights * this.getNrGuests(); 			//per Person - per Item non può esistere
		}
		else if (extra.getTimePriceType().equals("per Week")) {
			if (extra.getResourcePriceType().equals("per Room")) {
				ret = numNights/7 + 1;						//assumendo l'extra per week come indivisibile
			}
			else ret = (numNights/7 + 1) * this.getNrGuests();	//per Person - per Item non può esistere
		}else {												//per Booking
			if (extra.getResourcePriceType().equals("per Room")) {
				ret = 1;									//un Booking per ora è associato ad una sola Room!
			}
			else if (extra.getResourcePriceType().equals("per Person")) {
				ret = this.getNrGuests();
			}
			else ret = 10; //per Item
		}
		return ret;
	}	
	
	public List<Date> calculateBookingDates(){	//crea un array di date, che corrispondono alla permanenza
		List<Date> bookingDates = null; 
		Date current = null;
		Integer i = 0;
		
		bookingDates = new ArrayList<Date>();
		if(this.getDateIn()!=null && this.getDateOut()!=null){
			current  = DateUtils.addDays(this.getDateIn(), i );		
			while(DateUtils.truncatedCompareTo(current, this.getDateOut(),Calendar.DAY_OF_MONTH ) < 0){
				bookingDates.add(current);
				i = i + 1;
				current  = DateUtils.addDays(this.getDateIn(), i );
			}	
		}		
		return bookingDates;
	}
	
	public Double calculateExtraSubtotalForBooking(){
		Double ret = 0.0;
		
		for (ExtraItem eachItem : this.getExtraItems()) {
				ret = ret + eachItem.getSubtotal();
			  }
		return ret;
	}
	
	public void updateExtraSubtotal(){
		Double extraSubtotal = 0.0;
		
		for (ExtraItem each : this.getExtraItems()) {
			extraSubtotal = extraSubtotal + each.getSubtotal();
		}
		this.setExtraSubtotal(extraSubtotal);
	}
	
	public List<Integer> calculateExtraIds(){
		List<Integer> ret = null;
		
		ret = new ArrayList<Integer>();
		for(ExtraItem each: this.getExtraItems()){
			ret.add(each.getId_extra());
		}
		return ret;
	}
	
	
	public Boolean addExtra(Extra anExtra){
		return this.getExtras().add(anExtra);
	}
	
	public Boolean addExtras(List<Extra> extras){
		return this.getExtras().addAll(extras);
	}
	
	public Boolean addAdjustment(Adjustment anAdjustment){
		return this.getAdjustments().add(anAdjustment);
	}
	
	public Boolean removeAdjustment(Adjustment anAdjustment){
		return this.getAdjustments().remove(anAdjustment);
	}
	
	public Boolean addPayment(Payment aPayment){
		return this.getPayments().add(aPayment);
	}
	
	public Boolean removePayment(Payment aPayment){
		return this.getPayments().remove(aPayment);
	}
	
	public Boolean addGuest(Guest aGuest){
		return this.getGuests().add(aGuest);
	}
	
	public Boolean removeGuest(Guest aGuest){
		return this.getGuests().remove(aGuest);
	}
	
	public Boolean addExtraItem(ExtraItem anExtraItem){
		return this.getExtraItems().add(anExtraItem);
	}
	
	public Boolean removeExtraItem(Guest anExtraItem){
		return this.getExtraItems().remove(anExtraItem);
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Guest getBooker() {
		return booker;
	}
	public void setBooker(Guest guest) {
		this.booker = guest;
	}
	public Integer getNrGuests() {
		return nrGuests;
	}
	public void setNrGuests(Integer nrGuests) {
		this.nrGuests = nrGuests;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<Extra> getExtras() {
		return extras;
	}
	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}
	public Double getExtraSubtotal() {
		return extraSubtotal;
	}
	public void setExtraSubtotal(Double extraSubtotal) {
		this.extraSubtotal = extraSubtotal;
	}
	public Double getRoomSubtotal() {
		return roomSubtotal;
	}
	public void setRoomSubtotal(Double roomSubtotal) {
		this.roomSubtotal = roomSubtotal;
	}
	public List<Adjustment> getAdjustments() {
		return adjustments;
	}
	public void setAdjustments(List<Adjustment> adjustments) {
		this.adjustments = adjustments;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Guest> getGuests() {
		return guests;
	}
	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}
	public Convention getConvention() {
		return convention;
	}
	public void setConvention(Convention convention) {
		this.convention = convention;
	}
	public List<ExtraItem> getExtraItems() {
		return extraItems;
	}
	public void setExtraItems(List<ExtraItem> extraItems) {
		this.extraItems = extraItems;
	}

	public Integer getId_structure() {
		return id_structure;
	}

	public void setId_structure(Integer id_structure) {
		this.id_structure = id_structure;
	}

	public Integer getId_convention() {
		return id_convention;
	}

	public void setId_convention(Integer id_convention) {
		this.id_convention = id_convention;
	}

	public Integer getId_room() {
		return id_room;
	}

	public void setId_room(Integer id_room) {
		this.id_room = id_room;
	}

	
}
