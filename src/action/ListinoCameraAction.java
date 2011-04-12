package action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Extra;
import model.Room;
import model.Structure;
import model.User;
import model.internal.Message;

import org.apache.commons.lang.time.DateUtils;
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
				} ),
				@Result(name="input", location = "/validationError.jsp" )
		})
		
	})
	
	
	
	public String calculatePrices() {
		User user = null; 
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		Structure structure = null; 
						
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		if ( (this.getBooking().getDateOut() != null) && (this.getBooking().getDateIn() != null) ) {
			if(DateUtils.truncatedCompareTo(this.getBooking().getDateOut(), this.getBooking().getDateIn(), Calendar.DAY_OF_MONTH)<=0){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("DateOut deve essere maggiore di DateIn!");
				return "error";
			}				
		}
		this.saveUpdateBookingRoom(structure);		
		this.saveUpdateBookingExtras(structure);		
		this.calculateNumNights();
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());		
		this.getBooking().setRoomSubtotal(roomSubtotal);		
		extraSubtotal = structure.calculateExtraSubtotalForBooking(this.getBooking());
		this.getBooking().setExtraSubtotal(extraSubtotal);		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Prezzo Calcolato con Successo");
		return "success";				
	}	
	
	private void calculateNumNights(){
		Long millis; 
		Integer days = 0;
		
		if((this.getBooking().getDateOut()!=null) && (this.getBooking().getDateIn()!=null)){
			millis = this.getBooking().getDateOut().getTime() - this.getBooking().getDateIn().getTime();
			days = (int) (millis/(1000*3600*24));
		}		
		this.setNumNights(days);
	}
	
	private Boolean saveUpdateBookingRoom(Structure structure){
		Room theBookedRoom = null;
		
		theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		return true;
	}
	
	
	private Boolean saveUpdateBookingExtras(Structure structure){ 
		List<Extra>  checkedExtras = null;
		
		checkedExtras = structure.findExtrasByIds(this.getBookingExtraIds());
		this.getBooking().setExtras(checkedExtras);	
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
