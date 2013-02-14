package resources;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Booking;
import model.Guest;
import model.Housed;
import model.questura.Country;
import model.questura.HousedType;
import model.questura.Municipality;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.BookingService;
import service.GroupLeaderService;
import service.GuestService;
import service.HousedService;
import service.StructureService;

@Path("/groupLeader/")
@Component
@Scope("prototype")
public class GroupLeaderResource {
    @Autowired
    private HousedService housedService = null;
	@Autowired
    private GroupLeaderService groupLeaderService = null;

    @GET
    @Path("booking/{idBooking}")
    @Produces({MediaType.APPLICATION_JSON})
    public Housed getGroupLeader(@PathParam("idBooking") Integer idBooking){
    	Housed ret = null;
    	
    	ret = this.getGroupLeaderService().findGroupLeaderByIdBooking(idBooking);
        return ret;
    }

    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Housed setGroupLeader(Housed housed, Booking booking, HousedType type){
		Integer id_booking = null;
		Integer id_housed = null;
		
		id_booking = booking.getId();
		id_housed = housed.getId();
 		this.getGroupLeaderService().insert(id_booking, id_housed);
 		housed.setHousedType(type);
 		this.getHousedService().updateHoused(housed);
 		return housed;
	}
      
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
//		if(this.getBookingService().countBookingsByIdGuest(id) > 0){
//			throw new NotFoundException("The guest you are trying to delete has links to one or more bookings." +
//					" Please try to delete the associated bookings before.");
//		}
		count = this.getGroupLeaderService().delete(id);
//		if(count == 0){
//			throw new NotFoundException("Error: the guest has NOT been deleted");
//		}
		return count;
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

}