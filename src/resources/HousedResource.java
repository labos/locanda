package resources;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Booking;
import model.GroupLeader;
import model.Guest;
import model.Housed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import org.apache.log4j.Logger;
import service.BookingService;
import service.GroupLeaderService;
import service.GuestService;
import service.HousedService;
import service.StructureService;
import utils.I18nUtils;

import com.opensymphony.xwork2.ActionContext;
import com.sun.jersey.api.NotFoundException;

@Path("/housed/")
@Component
@Scope("prototype")
public class HousedResource {
	@Autowired
    private HousedService housedService = null;
    @Autowired
    private StructureService structureService = null;
    @Autowired
    private BookingService bookingService = null;
    @Autowired
    private GroupLeaderService groupLeaderService = null;
    @Autowired
    private GuestService guestService = null; 
    
	private static Logger logger = Logger.getLogger(Logger.class);
	
    
    @GET
    @Path("booking/{idBooking}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Housed> findHousedByIdBooking(@PathParam("idBooking") Integer idBooking) {
    	List<Housed> ret = null;
    	
    	ret = this.getHousedService().findHousedByIdBooking(idBooking);
    	return ret;
    }
    
    @GET
    @Path("booking/{idBooking}/maxGuests")
    @Produces({MediaType.APPLICATION_JSON})
    public Integer findMaxNumberOfGuestsByIdBooking(@PathParam("idBooking") Integer idBooking) {
    	Integer ret = null;
    	Booking booking = null;
    	
    	booking = this.getBookingService().findBookingById(idBooking);
    	if(booking!=null){
    		ret = booking.getRoom().getRoomType().getMaxGuests();
    	}
    	
    	return ret;
    }
 
    /*
    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer insertHoused(Map map){
    	Housed housed = null;
    	Booking booking = null;
    	
    	Integer id_booking = null;
		Integer id_guest = null;
 		Integer id = null;
 		
 		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");

		
		housed = new Housed();
		housed.setId_booking(id_booking);
		housed.setId_guest(id_guest);
		booking = this.getBookingService().findBookingById(id_booking);
 		housed.setCheckInDate(booking.getDateIn());
 		housed.setCheckOutDate(booking.getDateOut());
		
 		this.getHousedService().insert(housed);
 		id = housed.getId();
 		return id;
	}*/
    
    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Housed insertHoused(Map map){
    	Housed housed = null;
    	Booking booking = null;
    	Guest guest = null;
    	GroupLeader groupLeader = null;
    	
    	Integer id_booking = null;
		Integer id_guest = null;
 		
 		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		
		guest = this.getGuestService().findGuestById(id_guest);
		groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(id_booking);
		if (groupLeader == null) {
			if (!guest.canBeSingleOrLeader()) {
				throw new NotFoundException(I18nUtils.getProperty("canBeSingleOrLeader"));
			}
		}else {
			if (!guest.canBeMember()) {
			throw new NotFoundException(I18nUtils.getProperty("canBeMember"));
			}
		}
		
		housed = new Housed();
		housed.setId_booking(id_booking);
		housed.setId_guest(id_guest);
		booking = this.getBookingService().findBookingById(id_booking);
 		housed.setCheckInDate(booking.getDateIn());
 		housed.setCheckOutDate(booking.getDateOut());
		
 		this.getHousedService().insert(housed);
 		
 		return housed;
	}
    
    /*
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Integer update(Map map) {
    	Integer id;
    	Integer id_booking = null;
		Integer id_guest = null;
		Long checkInDateMillis = null;
		Long checkOutDateMillis = null;
		
		Housed housed = null;
		
		id = (Integer)map.get("id");
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		checkInDateMillis = (Long)map.get("checkInDate");
		checkOutDateMillis = (Long)map.get("checkOutDate");
		
		housed = this.getHousedService().findHousedById(id);
		
		housed.setId_guest(id_guest);
		if(checkInDateMillis!=null){
			housed.setCheckInDate(new Date(checkInDateMillis));
		}
		if(checkOutDateMillis!=null){
			housed.setCheckOutDate(new Date(checkOutDateMillis));
		}    	
    	this.getHousedService().update(housed);
        return id;
    }*/
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Housed update(Map map) {
    	Integer id;
    	Integer id_booking = null;
		Integer id_guest = null;
		Long checkInDateMillis = null;
		Long checkOutDateMillis = null;
		
		Housed housed = null;
		
		id = (Integer)map.get("id");
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		checkInDateMillis = (Long)map.get("checkInDate");
		checkOutDateMillis = (Long)map.get("checkOutDate");
		
		housed = this.getHousedService().findHousedById(id);
		
		housed.setId_guest(id_guest);
		if(checkInDateMillis!=null){
			housed.setCheckInDate(new Date(checkInDateMillis));
		}
		if(checkOutDateMillis!=null){
			housed.setCheckOutDate(new Date(checkOutDateMillis));
		}    	
    	this.getHousedService().update(housed);
        return housed;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;
    	Housed housed = null;
    	Integer id_booking;
    	GroupLeader groupLeader = null;
		List <GroupLeader> groupLeaders = null;
		String message = "";
//		if(this.getBookingService().countGroupLeaderByIdHoused(id) > 0){
//			throw new NotFoundException("The housed you are trying to delete is the leader of the group associated with this booking" +
//					" Please change the group/family leader first.");
//		}
    	
    	housed = this.getHousedService().findHousedById(id);
    	id_booking = housed.getId_booking();
    	groupLeaders = this.getGroupLeaderService().findByIdHoused(id);
    	/*
    	groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBookingAndIdHoused(id_booking, id);
    	if (groupLeader != null) {
    		message = "The housed you are trying to delete is the leader of the group associated with this booking" +
    										"Please change the group/family leader first.";
    	}
    	*/
    	
    	if (groupLeaders.size() > 0) {
    		Booking booking = null;
    		message =I18nUtils.getProperty("housedDeleteIsGroupLeaderError");
    		//get all bookings
    		for(GroupLeader each: groupLeaders){
    			booking = this.getBookingService().findBookingById(each.getId_booking());
    			message+=  "\n***(" + I18nUtils.getProperty("room") + ": " + booking.getRoom().getName() + " " + booking.getDateIn() + " - " + booking.getDateOut() +")" ;
    			
    		}
    		throw new NotFoundException(message);
    	}
		count = this.getHousedService().delete(id);
		return count;
    }  
    
    public HousedService getHousedService() {
		return housedService;
	}
	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}
	public StructureService getStructureService() {
        return structureService;
    }
    public void setStructureService(StructureService structureService) {
        this.structureService = structureService;
    }
	public BookingService getBookingService() {
		return bookingService;
	}
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	public GroupLeaderService getGroupLeaderService() {
		return groupLeaderService;
	}
	public void setGroupLeaderService(GroupLeaderService groupLeaderService) {
		this.groupLeaderService = groupLeaderService;
	}
	public GuestService getGuestService() {
		return guestService;
	}
	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}
	/*
	public static String getPropertyValue(String key)  
    {  
		String lang = (String) (ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE") != null ? "en" : ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE"));
		
		//String localizedKeyValue = LocalizedTextUtil.findDefaultText(key, ActionContext.getContext().getLocale());
        ResourceBundle bundle = ResourceBundle.getBundle("global_" + lang);  
        String strVal = ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE") + bundle.getString(key);  
        if (strVal == null)  
        {  
            strVal = "";  
        }  
        bundle = null;  
        return strVal;  
    } 
	*/
	
}