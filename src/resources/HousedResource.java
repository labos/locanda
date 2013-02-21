package resources;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.GroupLeader;
import model.Housed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.BookingService;
import service.GroupLeaderService;
import service.HousedService;
import service.StructureService;

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
    
    @GET
    @Path("booking/{idBooking}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Housed> findHousedByIdBooking(@PathParam("idBooking") Integer idBooking) {
    	List<Housed> ret = null;
    	
    	ret = this.getHousedService().findHousedByIdBooking(idBooking);
    	return ret;
    }
 
    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer insertHoused(Map map){
    	Housed housed = null;
    	
    	Integer id_booking = null;
		Integer id_guest = null;
 		Integer id = null;
 		
 		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");

		housed = new Housed();
		housed.setId_booking(id_booking);
		housed.setId_guest(id_guest);
 		
 		this.getHousedService().insertHoused(housed);
 		id = housed.getId();
 		return id;
	}
    
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Integer update(Map map) {
    	Integer id;
    	Integer id_booking = null;
		Integer id_guest = null;
		Long checkInDate = null;
		Long checkOutDate = null;
		
		Housed housed = null;
		
		id = (Integer)map.get("id");
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		checkInDate = (Long)map.get("checkInDate");
		checkOutDate = (Long)map.get("checkOutDate");
		
		housed = this.getHousedService().findHousedById(id);
		
		housed.setId_guest(id_guest);
		housed.setCheckInDate(new Date(checkInDate));
		housed.setCheckOutDate(new Date(checkOutDate));
    	
    	this.getHousedService().updateHoused(housed);
        return id;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;
    	Housed housed = null;
    	Integer id_booking;
    	GroupLeader groupLeader = null;
		
//		if(this.getBookingService().countGroupLeaderByIdHoused(id) > 0){
//			throw new NotFoundException("The housed you are trying to delete is the leader of the group associated with this booking" +
//					" Please change the group/family leader first.");
//		}
    	
    	housed = this.getHousedService().findHousedById(id);
    	id_booking = housed.getId_booking();
    	groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBookingAndIdHoused(id_booking, id);
    	if (groupLeader != null) {
    		throw new NotFoundException("The housed you are trying to delete is the leader of the group associated with this booking" +
    										"Please change the group/family leader first.");
    	}
		count = this.getHousedService().deleteHoused(id);
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

}