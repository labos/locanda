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

import model.Booker;
import model.Booking;
import model.GroupLeader;
import model.Housed;
import model.questura.HousedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import persistence.mybatis.mappers.HousedTypeMapper;

import service.BookerService;
import service.GroupLeaderService;
import service.HousedService;

@Path("/booker/")
@Component
@Scope("prototype")
public class BookerResource {
    @Autowired
	private BookerService bookerService = null;

    @GET
    @Path("booking/{id_booking}")
    @Produces({MediaType.APPLICATION_JSON})
    public Booker getBooker(@PathParam("id_booking") Integer id_booking){
    	Booker  ret = null;
    	    	
        ret = this.getBookerService().findBookerByIdBooking(id_booking);
    	return ret;
    }

       
    @PUT
    @Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Integer update(Map map){
    	Integer ret;
    	Integer id;
    	Integer id_booking = null;
		Integer id_guest = null;
		Booker booker = null;
		
		//id = (Integer)map.get("id");
		id_booking = (Integer)map.get("id_booking");
		id_guest = (Integer)map.get("id_guest");
		
		booker = this.getBookerService().findBookerByIdBooking(id_booking);
		
		booker.setId_guest(id_guest);
		
    	ret = this.getBookerService().update(booker);
    	return ret;
    }
      
   
   
   

	public BookerService getBookerService() {
		return bookerService;
	}


	public void setBookerService(BookerService bookerService) {
		this.bookerService = bookerService;
	}
	

}