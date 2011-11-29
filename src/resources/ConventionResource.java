package resources;

import java.util.ArrayList;
import java.util.List;

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
import model.listini.Convention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.BookingService;
import service.ConventionService;
import service.StructureService;

@Path("/conventions/")
@Component
@Scope("prototype")
public class ConventionResource {
   
    @Autowired
    private ConventionService conventionService = null;
    @Autowired
    private StructureService structureService = null;
    @Autowired
    private BookingService bookingService = null;
   
       
    @GET
    @Path("structure/{idStructure}/{offset}/{rownum}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Convention> getConventions(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
        List<Convention> filteredConventions = null;
       
        filteredConventions = new ArrayList<Convention>();
        for(Convention each: this.getConventionService().findConventionsByIdStructure(idStructure,offset,rownum)){           
            if(!each.getActivationCode().equals("thisconventionshouldntneverberemoved")){
                filteredConventions.add(each);
            }           
        }       
        return filteredConventions;   
    }
    
    @GET
    @Path("structure/{idStructure}/search/{offset}/{rownum}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Convention> simpleSearch(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum, @QueryParam("term") String term){
        List<Convention> filteredConventions = null;
       
        filteredConventions = new ArrayList<Convention>();
        for(Convention each: this.getConventionService().search(idStructure,offset,rownum, term)){           
            if(!each.getActivationCode().equals("thisconventionshouldntneverberemoved")){  
            	filteredConventions.add(each);            		
            }           
        }       
        return filteredConventions;          
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Convention getConvention(@PathParam("id") Integer id){
        Convention ret = null;
       
        ret = this.getConventionService().findConventionById(id);
        return ret;   
    }
  
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Convention save(Convention convention) {
       
        this.getConventionService().insertConvention(convention);
        this.getStructureService().addPriceListsForConvention(convention.getId_structure(),convention.getId() );
        return convention;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Convention update(Convention convention) {        
        
    	this.getConventionService().updateConvention(convention);
        return convention;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		if(this.getBookingService().countBookingsByIdConvention(id) > 0){
			throw new NotFoundException("The convention you are trying to delete has links to one or more bookings." +
					" Please try to delete the associated bookings before.");
		}
		count = this.getConventionService().deleteConvention(id);
		if(count == 0){
			throw new NotFoundException("Error: the convention has NOT been deleted");
		}
		return count;
    }   
   
    public ConventionService getConventionService() {
        return conventionService;
    }
    public void setConventionService(ConventionService conventionService) {
        this.conventionService = conventionService;
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

}