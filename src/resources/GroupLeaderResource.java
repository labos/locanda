package resources;

import java.util.List;
import java.util.Locale;
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
import model.Guest;
import model.Housed;
import model.questura.HousedType;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
//import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import persistence.mybatis.mappers.HousedTypeMapper;

import service.GroupLeaderService;
import service.GuestService;
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
	@Autowired
    private GuestService guestService = null; 
	//@Autowired
	// private MessageSource messageSource;

    @GET
    @Path("booking/{id_booking}")
    @Produces({MediaType.APPLICATION_JSON})
    public GroupLeader getGroupLeader(@PathParam("id_booking") Integer id_booking){
    	GroupLeader ret = null;
    	
    	ret = this.getGroupLeaderService().findGroupLeaderByIdBooking(id_booking);
        return ret;
    }

    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer setGroupLeader(Map map){
		Integer id_booking = null;
		Integer id_guest = null;
		String groupType = null;
		Integer ret;
		Housed housed = null;
		HousedType housedType = null;
		List<Housed> housedList = null;
		Guest guest = null;
		
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		//Locale locale = LocaleContextHolder.getLocale();
		guest = this.getGuestService().findGuestById(id_guest);
		if (!guest.canBeSingleOrLeader()) {
			throw new NotFoundException("The guest you are trying to house does not have all the requested fields." +
					"Please fill all these fields before adding this guest as housed"
					
					);
			
			//messageSource.getMessage("selectHousedDates", null, "Default",null)
		}
		
		housed = this.getHousedService().findMostRecentHousedByIdGuest(id_guest);			
		
 		ret = this.getGroupLeaderService().insert(id_booking, housed.getId());
 		housedType = this.getHousedTypeMapper().findHousedTypeByDescription(groupType);
 		housed.setHousedType(housedType);
 		housed.setId_housedType(housedType.getId());
 		this.getHousedService().update(housed); 		
 		
 		//We set the new housed as a group leader in his/her own booking
		GroupLeader newGroupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(housed.getId_booking());
		if(newGroupLeader == null){
			//The new chosen housed is not a group leader in his/her booking. Let's add the new group leader
			this.getGroupLeaderService().insert(housed.getId_booking(), housed.getId());
		}
 		
 		return ret;
	}
    
    /*
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
		
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		
    	housed = this.getHousedService().findMostRecentHousedByIdGuest(id_guest);
    	housedType = this.getHousedTypeMapper().findHousedTypeByDescription(groupType);
    	housed.setHousedType(housedType);
    	housed.setId_housedType(housedType.getId());
    	this.getHousedService().update(housed);
    	
    	groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(id_booking);
    	groupLeader.setId_housed(housed.getId());
    	
		ret = this.getGroupLeaderService().update(groupLeader);
    	return ret;
    }*/
    
    @PUT
    @Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer updateGroupLeader(Map map){
    	Integer ret;
    	Integer id_booking = null;
		Integer id_guest = null;
		String groupType = null;
		Housed currentHoused = null;
		Housed housed = null;
		HousedType housedType = null;
		GroupLeader groupLeader = null;
		Boolean housedChanged = false;
		Guest guest = null;
		
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		
		groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(id_booking);
		//NUOVA PARTE
		if(groupLeader==null){
			throw new NotFoundException("Si  verificato un problema nell'aggiornamento del group leader");
		}
		
		currentHoused = groupLeader.getHoused();
		
		//checking first if the guest associated with the new housed can be a Leader
		guest = this.getGuestService().findGuestById(id_guest);
		if (!guest.canBeSingleOrLeader()) {
			throw new NotFoundException("The guest you are trying to house does not have all the requested fields." +
											"Please fill all these fields before adding this guest as housed");
		}
		
		housedChanged = (id_guest != currentHoused.getId_guest());
		if(housedChanged){
			//We are choosing a new housed for this group leader
			housed = this.getHousedService().findMostRecentHousedByIdGuest(id_guest);	
			
		}else{
			//We are not changing the housed for this group leader
			housed = currentHoused;			
		}
		//We Update the housed type (i.e. from family to group). We don't check if the housed type has changed, we update it anyway
		housedType = this.getHousedTypeMapper().findHousedTypeByDescription(groupType);
    	housed.setHousedType(housedType);
    	housed.setId_housedType(housedType.getId());
    	ret = this.getHousedService().update(housed);    	
		
    	if(housedChanged){
    		//We Update the housed in all the group leaders of the same group of persons
    		List<GroupLeader> groupLeadersToUpdate = this.getGroupLeaderService().findByIdHoused(currentHoused.getId());
    		for(GroupLeader each: groupLeadersToUpdate){
    			each.setId_housed(housed.getId());
    			ret = ret + this.getGroupLeaderService().update(each);
    		} 
    		//We set the new housed as a group leader in his/her own booking
    		GroupLeader newGroupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(housed.getId_booking());
    		if(newGroupLeader == null){
    			//The new chosen housed is not a group leader in his/her booking. Let's add the new group leader
    			this.getGroupLeaderService().insert(housed.getId_booking(), housed.getId());
    		}
    	}		
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
	public GuestService getGuestService() {
		return guestService;
	}
	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}

}