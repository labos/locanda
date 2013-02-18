package resources;

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

import model.Booking;
import model.GroupLeader;
import model.Housed;
import model.questura.HousedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import persistence.mybatis.mappers.HousedTypeMapper;

import service.GroupLeaderService;
import service.HousedService;

@Path("/groupLeader/")
@Component
@Scope("prototype")
public class GroupLeaderResource {
    @Autowired
    private HousedService housedService = null;
	@Autowired
    private GroupLeaderService groupLeaderService = null;
	@Autowired
    private HousedTypeMapper housedTypeMapper = null;

    @GET
    @Path("booking/{idBooking}")
    @Produces({MediaType.APPLICATION_JSON})
    public GroupLeader getGroupLeader(@PathParam("idBooking") Integer idBooking){
    	GroupLeader ret = null;
    	
    	ret = this.getGroupLeaderService().findGroupLeaderByIdBooking(idBooking);
        return ret;
    }

    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer setGroupLeader(Map map){
		Integer id_booking = null;
		Integer id_guest = null;
		String groupType = null;
		Integer id;
		Housed housed = null;
		HousedType housedType = null;
		
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		
		housed = this.getHousedService().findHousedByIdBookingAndIdGuest(id_booking, id_guest);
		
		housedType = this.getHousedTypeMapper().findHousedTypeByDescription(groupType);
 		id = this.getGroupLeaderService().insert(id_booking, housed.getId());
 		housed.setHousedType(housedType);
 		housed.setId_housedType(housedType.getId());
 		this.getHousedService().updateHoused(housed);
 		return id;
	}
    
    @PUT
    @Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer updateGroupLeader(Map map){
    	Integer ret;
    	Integer id_booking = null;
		Integer id_guest = null;
		String groupType = null;
		Housed housed = null;
		HousedType housedType = null;
		GroupLeader groupLeader = null;
		
    	housed = this.getHousedService().findHousedByIdBookingAndIdGuest(id_booking, id_guest);
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		housedType = this.getHousedTypeMapper().findHousedTypeByDescription(groupType);
    	housed.setHousedType(housedType);
    	housed.setId_housedType(housedType.getId());
    	this.getHousedService().updateHoused(housed);
    	groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(id_booking);
    	groupLeader.setId_housed(housed.getId());
		ret = this.groupLeaderService.update(groupLeader);
    	return ret;
    }
      
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer ret = 0;		
		
		ret = this.getGroupLeaderService().delete(id);
		return ret;
    }   
   
    public HousedService getHousedService() {
		return housedService;
	}
	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}
	public GroupLeaderService getGroupLeaderService() {
		return groupLeaderService;
	}
	public void setGroupLeaderService(GroupLeaderService groupLeaderService) {
		this.groupLeaderService = groupLeaderService;
	}
	public HousedTypeMapper getHousedTypeMapper() {
		return housedTypeMapper;
	}
	public void setHousedTypeMapper(HousedTypeMapper housedTypeMapper) {
		this.housedTypeMapper = housedTypeMapper;
	}	

}