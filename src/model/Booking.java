package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.Season;

public class Booking {
	
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
	private String status = "provisional";
	private List<Guest> guests = null;
	private Convention convention = null;
	private List<BookedExtraItem> extraItems;
	
	
	public Booking(){
		this.extras = new ArrayList<Extra>();
		this.adjustments = new ArrayList<Adjustment>();
		this.payments = new ArrayList<Payment>();
		this.guests = new ArrayList<Guest>();
		this.extraItems = new ArrayList<BookedExtraItem>();
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
	
	public void buildExtraItemsFromExtras(Structure structure, List<Extra> extras) {
		BookedExtraItem extraItem = null;
		List<BookedExtraItem> extraItemList = new ArrayList<BookedExtraItem>();
		
		for (Extra eachExtra : extras) {
			extraItem = new BookedExtraItem();
			extraItem.setId(structure.nextKey());
			extraItem.setExtra(eachExtra);
			extraItem.setQuantity(this.calculateExtraItemQuantity(extraItem));
			extraItem.setUnitaryPrice(this.calculateExtraItemUnitaryPrice(structure, extraItem));
			extraItemList.add(extraItem);
			this.setExtraItems(extraItemList);
		}
	}
	
	public Integer calculateExtraItemQuantity(BookedExtraItem extraItem) {
		Integer ret = 0;
		Integer numNights = this.calculateNumNights();
		Extra extra = extraItem.getExtra();
		
		if (extra.getTimePriceType().equals("per Night")) {
			if (extra.getResourcePriceType().equals("per Room")) {
				ret = numNights;
			}
			else ret = numNights * this.nrGuests; 			//per Person - per Item non può esistere
		}
		else if (extra.getTimePriceType().equals("per Week")) {
			if (extra.getResourcePriceType().equals("per Room")) {
				ret = numNights/7 + 1;						//assumendo l'extra per week come indivisibile
			}
			else ret = (numNights/7 + 1) * this.nrGuests;	//per Person - per Item non può esistere
		}else {												//per Booking
			if (extra.getResourcePriceType().equals("per Room")) {
				ret = 1;									//un Booking per ora è associato ad una sola Room!
			}
			else if (extra.getResourcePriceType().equals("per Person")) {
				ret = this.nrGuests;
			}
			else ret = 1; //per Item
		}
		return ret;
	}

	public Double calculateExtraItemUnitaryPrice(Structure structure, BookedExtraItem extraItem) {
		Double ret = 0.0;
		ExtraPriceList priceList = null;
		ExtraPriceList otherPriceList = null;
		Season season = null;
		Season otherSeason = null;
		Double price = 0.0;
		Double otherPrice = 0.0;
		
		season = structure.findSeasonByDate(this.getDateIn());
		priceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(season, this.getRoom().getRoomType(), this.getConvention());
		price = priceList.findExtraPrice(extraItem.getExtra());
		ret = price;
		
		if (!this.getDateIn().equals(this.getDateOut())) {	//se ho un booking a cavallo di due stagioni, prendo il prezzo più basso
			otherSeason = structure.findSeasonByDate(this.getDateOut());
			otherPriceList = structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(otherSeason, this.getRoom().getRoomType(), this.getConvention());				price = priceList.findExtraPrice(extraItem.getExtra());
			otherPrice = otherPriceList.findExtraPrice(extraItem.getExtra());
			if (price > otherPrice) {
				ret = otherPrice;
			}
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
	
	public Boolean addExtraItem(BookedExtraItem anExtraItem){
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
	public List<BookedExtraItem> getExtraItems() {
		return extraItems;
	}
	public void setExtraItems(List<BookedExtraItem> extraItems) {
		this.extraItems = extraItems;
	}

	
}
