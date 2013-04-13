package resources;

import java.util.ArrayList;
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
import model.questura.HousedExport;
import model.questura.HousedType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
//import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import persistence.mybatis.mappers.HousedTypeMapper;

import service.GroupLeaderService;
import service.GuestService;
import service.HousedExportService;
import service.HousedService;
import utils.I18nUtils;

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
	@Autowired
	private HousedExportService housedExportService = null;
	private static Logger logger = Logger.getLogger(Logger.class);

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
		List<GroupLeader> groupLeadersInvolved = null;
		Guest guest = null;
		
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		//Locale locale = LocaleContextHolder.getLocale();
		guest = this.getGuestService().findGuestById(id_guest);
		if (!guest.canBeSingleOrLeader()) {
			throw new NotFoundException(I18nUtils.getProperty("canBeSingleOrLeader"));
			
			//messageSource.getMessage("selectHousedDates", null, "Default",null)
		}
		
		housed = this.getHousedService().findMostRecentHousedByIdGuest(id_guest);			
		
 		ret = this.getGroupLeaderService().insert(id_booking, housed.getId());
 		housedType = this.getHousedTypeMapper().findHousedTypeByDescription(groupType);
 		housed.setHousedType(housedType);
 		housed.setId_housedType(housedType.getId());
 		this.getHousedService().update(housed); 		
 		
 		groupLeadersInvolved = new ArrayList<GroupLeader>();
 		//We set the new housed as a group leader in his/her own booking
		GroupLeader newGroupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(housed.getId_booking());
		if(newGroupLeader == null){
			//The new chosen housed is not a group leader in his/her booking. Let's add the new group leader
			this.getGroupLeaderService().insert(housed.getId_booking(), housed.getId());
		}
 		groupLeadersInvolved.addAll(this.groupLeaderService.findByIdHoused(housed.getId()));
 		this.setExportLinkedHoused(groupLeadersInvolved);
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
		List<GroupLeader> groupLeadersInvolved = null;
				
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		groupType = (String)map.get("groupType");
		
		groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(id_booking);
		//NUOVA PARTE
		if(groupLeader==null){
			throw new NotFoundException(I18nUtils.getProperty("groupleaderUpdateError"));
		}
		
		currentHoused = groupLeader.getHoused();
		
		//checking first if the guest associated with the new housed can be a Leader
		guest = this.getGuestService().findGuestById(id_guest);
		if (!guest.canBeSingleOrLeader()) {
			throw new NotFoundException(I18nUtils.getProperty("canBeSingleOrLeader"));
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
	groupLeadersInvolved = new ArrayList<GroupLeader>();
	
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
    			//groupLeadersInvolved.add(newGroupLeader);
    		}
    		
    		
    	}
    	groupLeadersInvolved.addAll(this.getGroupLeaderService().findByIdHoused(housed.getId()));
    	this.setExportLinkedHoused(groupLeadersInvolved);
    	return ret;
    }
      
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer ret = 0;		
		this.setExportLinkedHoused(this.groupLeaderService.findGroupLeaderById(id));
		ret = this.getGroupLeaderService().delete(id);
		return ret;
    }
    
    private void setExportLinkedHoused(List<GroupLeader> groupLeaders){
    logger.info("##### - founds " + groupLeaders.size() + " to update");
    	for(GroupLeader aGroupLeader : groupLeaders){
        	List<Housed> housedToUpdate = this.housedService.findHousedByIdBooking(aGroupLeader.getId_booking());
            for(Housed each: housedToUpdate){
            	HousedExport housedExport  = null;
         		housedExport = this.getHousedExportService().findByIdHoused(each.getId());
         		if(!housedExport.getExported()){
         			housedExport.setMode(1);	
         		}else{
         			housedExport.setMode(2);
         		}
         		housedExport.setExported(false);
         		housedExport.setExportedQuestura(false);

         		this.getHousedExportService().update(housedExport);
            }
    		
    	}
    	
    }
    private void setExportLinkedHoused(GroupLeader groupLeader){
    	if(groupLeader != null){
    	List<Housed> housedToUpdate = this.housedService.findHousedByIdBooking(groupLeader.getId_booking());
    for(Housed each: housedToUpdate){
    	HousedExport housedExport  = null;
 		housedExport = this.getHousedExportService().findByIdHoused(each.getId());
 		if(!housedExport.getExported()){
 			housedExport.setMode(1);	
 		}else{
 			housedExport.setMode(2);
 		}
 		housedExport.setExported(false);
 		housedExport.setExportedQuestura(false);

		this.getHousedExportService().update(housedExport);
    }
    	}
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

	public HousedExportService getHousedExportService() {
		return housedExportService;
	}

	public void setHousedExportService(HousedExportService housedExportService) {
		this.housedExportService = housedExportService;
	}

}