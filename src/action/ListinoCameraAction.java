package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Extra;
import model.Room;
import model.Structure;
import model.User;
import model.internal.Message;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ListinoCameraAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private Booking booking = null;
	private List<Integer> bookingExtraIds = new ArrayList<Integer>();
	private Integer numNights;
	
	@Actions({
		@Action(value="/calculatePrices",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session"
				} ),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				} )
		})
		
	})
	
	
	
	public String calculatePrices() {
		User user = (User)this.getSession().get("user");
		Double roomSubtotal = 0.0;
		Long millis;
		Integer days;
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Room theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.saveUpdateBookingExtras(this.getBookingExtraIds(), structure);
		if (this.getBooking().getDateOut() != null && this.getBooking().getDateIn() != null ) {
			
			millis = this.getBooking().getDateOut().getTime() - this.getBooking().getDateIn().getTime();
			days = (int) (millis/(1000*3600*24));
			this.setNumNights(days);

			roomSubtotal = structure.calculateRoomSubtotal(theBookedRoom,this.getBooking().getDateIn(), this.getBooking().getDateOut(), null, this.getBooking().getNrGuests());
			
		}
		else {
			
			this.setNumNights(0);
		}

		
		Double extraSubtotal = 0.0;
		// popolo extrasIds con gli id degli extra già presenti nel booking
		for(Extra each: this.getBooking().getExtras()){
			extraSubtotal = extraSubtotal + each.getPrice();
			// extrasIds.add(each.getId());
		}		
		this.getBooking().setExtraSubtotal(extraSubtotal);
		this.getBooking().setRoomSubtotal(roomSubtotal);
	
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Prezzo Calcolato con Successo");
		return "success";				
	}	
	
	private Boolean saveUpdateBookingExtras(List<Integer> extras, Structure structure){ 
		List<Extra>  checkedExtras = new ArrayList<Extra>();
		checkedExtras = structure.findExtrasByIds(extras);		// popolo checkedExtras con gli extra checkati
		
		this.getBooking().setExtras(checkedExtras);	// popolo l'array di extra del booking con gli extra checkati
		return true;
	}
	
	
	public Map<String, Object> getSession() {
		return session;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}



	public List<Integer> getBookingExtraIds() {
		return bookingExtraIds;
	}

	public void setBookingExtraIds(List<Integer> bookingExtraIds) {
		this.bookingExtraIds = bookingExtraIds;
	}

	public Integer getNumNights() {
		return numNights;
	}

	public void setNumNights(Integer numNights) {
		this.numNights = numNights;
	}
	
	
	
	
	
	
	

}
