package resources;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import model.Booking;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.BookingService;

@Path("/export/")
@Component
@Scope("prototype")
public class ExportResource {
	@Autowired
    private BookingService bookingService = null;
	
	@GET
    @Path("structure/{idStructure}/dates/available")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Date> availableDatesForExport(@PathParam("idStructure") Integer idStructure){
		List<Date> dates = null;
		Date date = null;
		
		date = new Date();
		dates = new ArrayList<Date>();
		dates.add(date);		
		return dates;	
	
	}
	
		
	@GET
    @Path("structure/{idStructure}/check/questura")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Booking> checkExportQuesturaForDate(@PathParam("idStructure") Integer idStructure,@QueryParam("date") String date){
		List<Booking> bookingsWithProblems = null;
		
		bookingsWithProblems = this.getBookingService().findBookingsByIdStructure(idStructure);
				
		return bookingsWithProblems;	
	
	}
	
	@GET
    @Path("structure/{idStructure}/check/sired")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Booking> checkExportSiredForDate(@PathParam("idStructure") Integer idStructure,@QueryParam("date") String date){
		List<Booking> bookingsWithProblems = null;
		
		bookingsWithProblems = this.getBookingService().findBookingsByIdStructure(idStructure);
				
		return bookingsWithProblems;	
	
	}
	
	@GET
	@Path("structure/{idStructure}/do/questura")
	@Produces("text/plain")
	public Response exportFileQuestura(@QueryParam("date") String date) {
 
				
		String str = "questo è il file di export per la questura";
				
		ResponseBuilder response = Response.ok((Object) str);
		response.header("Content-Disposition",
			"attachment; filename=\"file_from_server_questura.txt\"");
		return response.build();
 
	}
	
	@GET
	@Path("structure/{idStructure}/do/sired")
	@Produces("text/plain")
	public Response exportFileSired(@QueryParam("date") String date) {
 
				
		String str = "questo è il file di export per il sired";
				
		ResponseBuilder response = Response.ok((Object) str);
		response.header("Content-Disposition",
			"attachment; filename=\"file_from_server_sired.txt\"");
		return response.build();
 
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
    	
}