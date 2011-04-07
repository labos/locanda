package action;

import java.util.Map;

import model.Booking;
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
	private Message message = null;
	private Booking booking = null;
	
	
	@Actions({
		@Action(value="/calculateRoomSubtotal",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session"
				} ),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				} )
		})
		
	})
	
	
	
	public String calculateRoomSubtotal() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Room theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		Double roomSubtotal = 0.0;
		
		roomSubtotal = structure.calculateTotalRoomPrice(theBookedRoom,this.getBooking().getDateIn(), this.getBooking().getDateOut(), null, this.getBooking().getNrGuests());
		this.getBooking().setRoomSubtotal(roomSubtotal);
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Prezzo Calcolato con Successo");
		return "success";				
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
	
	
	
	
	
	
	

}
