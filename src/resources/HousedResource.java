package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import service.GuestService;
import service.HousedService;
import service.StructureService;

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
    private SolrServer solrServerGuest = null;
   
    
//    @PostConstruct
//    public void init(){
//    	List<Guest> guests = null;
//    	
//    	guests = this.getGuestService().findAll();
//    	try {
//			this.getSolrServerGuest().addBeans(guests);
//			this.getSolrServerGuest().commit();
//		} catch (SolrServerException e) {			
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
    @GET
    @Path("booking/{idBooking}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Housed> search(@PathParam("idBooking") Integer idBooking, @PathParam("start") Integer start, @PathParam("rows") Integer rows) {
    	List<Housed> ret = null;
    	
    	ret = this.getHousedService().findHousedByIdBooking(idBooking);
    	return ret;
    }
    
    
//    @GET
//    @Path("structure/{idStructure}/search/{start}/{rows}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<Guest> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
//        List<Guest> guests = null;
//        SolrQuery query = null;
//        QueryResponse rsp = null;
//        SolrDocumentList solrDocumentList = null;
//        SolrDocument solrDocument = null;
//        Guest aGuest = null;
//        Integer id;             
//       
//        if(term.trim().equals("")){
//        	term = "*:*";
//        }
//        term = term + " AND id_structure:" + idStructure.toString();
//        query = new SolrQuery();   		
//        query.setQuery(term);
//        query.setStart(start);
//        query.setRows(rows);
//              
//        try {
//			rsp = this.getSolrServerGuest().query(query);
//			
//		} catch (SolrServerException e) {
//			e.printStackTrace();			
//		}
//
//        guests = new ArrayList<Guest>();
//        if(rsp!=null){
//    	   solrDocumentList = rsp.getResults();
//           for(int i = 0; i <solrDocumentList.size(); i++){
//        	   solrDocument = solrDocumentList.get(i);
//        	   id = (Integer)solrDocument.getFieldValue("id");
//        	  // System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
//        	   aGuest = this.getGuestService().findGuestById(id);
//        	   guests.add(aGuest);
//           }  
//       }       
//       return guests;          
//    }
    
//    @GET
//   	@Path("structure/{idStructure}/suggest")
//   	@Produces({ MediaType.APPLICATION_JSON })
//   	public List<String> suggest(@PathParam("idStructure") Integer idStructure,
//   			@QueryParam("term") String term) {
//   		SolrQuery query = null;
//   		QueryResponse rsp = null;
//   		List<String> ret = null;
//   		List<Count> values = null;
//   		
//   		query = new SolrQuery();
//   		query.setFacet(true);
//   		query.setQuery("*:* AND id_structure:" + idStructure.toString());
//
//   		query.addFacetField("text");
//   		term = term.toLowerCase();
//   		query.setFacetPrefix(term);
//
//   		try {
//   			rsp = this.getSolrServerGuest().query(query);
//   		} catch (SolrServerException e) {
//   			e.printStackTrace();
//   		}
//   		ret = new ArrayList<String>();
//
//   		if (rsp != null) {
//   			values = rsp.getFacetField("text").getValues();
//   			if(values!=null){
//   				for(Count each: values){
//   					if(each.getCount()>0){
//   						ret.add(each.getName());
//   					}
//   				}	
//   			}					
//   		}
//   		return ret;
//   	}
//    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Housed getHoused(@PathParam("id") Integer id){
    	Housed aHoused = null;
       
    	aHoused = this.getHousedService().findHousedById(id);
        return aHoused;   
    }
 
    @POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Housed insertHoused(Housed housed){
 		
 		this.getHousedService().insertHoused(housed);
 		return housed;
	}
    
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Housed update(Housed housed) {  
    	
    	this.getHousedService().updateHoused(housed);
        return housed;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
//		if(this.getBookingService().countGroupLeaderByIdHoused(id) > 0){
//			throw new NotFoundException("The housed you are trying to delete is the leader of the group associated with this booking" +
//					" Please change the group/family leader first.");
//		}
		count = this.getHousedService().deleteHoused(id);
		if(count == 0){
			throw new NotFoundException("Error: the housed has NOT been deleted");
		}
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
	public SolrServer getSolrServerGuest() {
		return solrServerGuest;
	}
	public void setSolrServerGuest(SolrServer solrServerGuest) {
		this.solrServerGuest = solrServerGuest;
	}  	

}