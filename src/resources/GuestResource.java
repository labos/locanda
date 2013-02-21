package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import model.Guest;
import model.Housed;

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
import service.GuestService;
import service.HousedService;
import service.StructureService;

@Path("/guests/")
@Component
@Scope("prototype")
public class GuestResource {
    @Autowired
    private GuestService guestService = null;
    @Autowired
    private StructureService structureService = null;
    @Autowired
    private BookingService bookingService = null;
    @Autowired
    private SolrServer solrServerGuest = null;
    @Autowired 
    private HousedService housedService = null;
   
    
    @PostConstruct
    public void init(){
    	List<Guest> guests = null;
    	
    	guests = this.getGuestService().findAll();
    	try {
			this.getSolrServerGuest().addBeans(guests);
			this.getSolrServerGuest().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @GET
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Guest> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<Guest> guests = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Guest aGuest = null;
        Integer id;             
       
        if(term.trim().equals("")){
        	term = "*:*";
        }
        term = term + " AND id_structure:" + idStructure.toString();
        query = new SolrQuery();   		
        query.setQuery(term);
        query.setStart(start);
        query.setRows(rows);
              
        try {
			rsp = this.getSolrServerGuest().query(query);
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

        guests = new ArrayList<Guest>();
        if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
        	  // System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   aGuest = this.getGuestService().findGuestById(id);
        	   guests.add(aGuest);
           }  
       }       
       return guests;          
    }
    
    @GET
   	@Path("structure/{idStructure}/suggest")
   	@Produces({ MediaType.APPLICATION_JSON })
   	public List<String> suggest(@PathParam("idStructure") Integer idStructure,
   			@QueryParam("term") String term) {
   		SolrQuery query = null;
   		QueryResponse rsp = null;
   		List<String> ret = null;
   		List<Count> values = null;
   		
   		query = new SolrQuery();
   		query.setFacet(true);
   		query.setQuery("*:* AND id_structure:" + idStructure.toString());

   		query.addFacetField("text");
   		term = term.toLowerCase();
   		query.setFacetPrefix(term);

   		try {
   			rsp = this.getSolrServerGuest().query(query);
   		} catch (SolrServerException e) {
   			e.printStackTrace();
   		}
   		ret = new ArrayList<String>();

   		if (rsp != null) {
   			values = rsp.getFacetField("text").getValues();
   			if(values!=null){
   				for(Count each: values){
   					if(each.getCount()>0){
   						ret.add(each.getName());
   					}
   				}	
   			}					
   		}
   		return ret;
   	}
        
    
    @GET
    @Path("structure/{idStructure}/housed")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Guest> findHousedGuests(@PathParam("idStructure") Integer idStructure){
        List<Guest> guests = null;
        Guest aGuest = null;
        List<Housed> housedList = null;       
        
        housedList = this.getHousedService().findAll();
        guests = new ArrayList<Guest>();
        
        for(Housed each: housedList){
        	aGuest = this.getGuestService().findGuestById(each.getId_guest());
        	if(aGuest.getId_structure().equals(idStructure)){
        		guests.add(aGuest);
        	}        	
        }           
       return guests;          
    }   
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Guest getGuest(@PathParam("id") Integer id){
    	Guest ret = null;
       
        ret = this.getGuestService().findGuestById(id);
        return ret;   
    }
  
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Guest save(Guest guest) {
       
        this.getGuestService().insertGuest(guest);
        try {
			this.getSolrServerGuest().addBean(guest);			
			this.getSolrServerGuest().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return guest;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Guest update(Guest guest) {  
    	try{
    		this.getGuestService().updateGuest(guest);
    	}catch(Exception ex){}	
    	try {
			this.getSolrServerGuest().addBean(guest);			
			this.getSolrServerGuest().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return guest;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		if(this.getBookingService().countBookingsByIdGuest(id) > 0){
			throw new NotFoundException("The guest you are trying to delete has links to one or more bookings." +
					" Please try to delete the associated bookings before.");
		}
		count = this.getGuestService().deleteGuest(id);
		if(count == 0){
			throw new NotFoundException("Error: the guest has NOT been deleted");
		}
		try {
			this.getSolrServerGuest().deleteById(id.toString());
			this.getSolrServerGuest().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
    }   
   
    public GuestService getGuestService() {
		return guestService;
	}
	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
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
	public SolrServer getSolrServerGuest() {
		return solrServerGuest;
	}
	public void setSolrServerGuest(SolrServer solrServerGuest) {
		this.solrServerGuest = solrServerGuest;
	}

	public HousedService getHousedService() {
		return housedService;
	}

	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}  	
	
	

}