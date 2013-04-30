package resources;


import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.BookingService;
import service.StructureService;
import utils.I18nUtils;

@Path("/bookings/")
@Component
@Scope("prototype")
public class BookingResource {
	@Autowired
    private StructureService structureService = null;
	@Autowired
    private BookingService bookingService = null;
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Booking getPeriod(@PathParam("id") Integer id){
		Booking ret = null;
		
		ret = this.getBookingService().findBookingById(id);
		return ret;
	}
	
	@GET
	@Path("structure/{idStructure}/search/{start}/{rows}/guest/{idGuest}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Booking> getPeriodBySeason(@PathParam("idGuest") Integer idGuest){
		List<Booking> ret = null;
		
		ret = this.getBookingService().findBookingsByIdGuest(idGuest);
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Booking save(Booking booking) {	
		this.getBookingService().saveUpdateBooking(booking);
        return booking;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Booking update(Booking booking) {
    		this.getBookingService().updateBooking(booking);
        return booking;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		count = this.getBookingService().deleteBooking(id);
		if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("periodDeleteError"));
		}
		return count;
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
