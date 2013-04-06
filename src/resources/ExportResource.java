package resources;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import model.Booking;
import model.Group;
import model.GroupLeader;
import model.GuestQuesturaFormatter;
import model.Housed;
import model.questura.HousedExport;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.BookingService;
import service.GroupLeaderService;
import service.HousedExportService;
import service.HousedService;

@Path("/export/")
@Component
@Scope("prototype")
public class ExportResource {
	@Autowired
    private BookingService bookingService = null;
	@Autowired
	private GroupLeaderService groupLeaderService = null;
	@Autowired
	private HousedService housedService = null;
	@Autowired
	private HousedExportService housedExportService = null;	
	private static Logger logger = Logger.getLogger(Logger.class);

	@GET
    @Path("structure/{idStructure}/dates/available")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Date> availableDatesForExport(@PathParam("idStructure") Integer idStructure){
		Set<Date> ret = null;
		List <HousedExport> housedExportList = null;
		Date checkinDate = null;
		
		ret = new HashSet<Date>();		
		housedExportList = this.getHousedExportService().findByIdStructureAndExported(idStructure, false);
		for(HousedExport each : housedExportList){
			checkinDate = each.getHoused().getCheckInDate();
			ret.add(DateUtils.truncate(checkinDate, Calendar.DAY_OF_MONTH));
		}
		
		return new ArrayList<Date>(ret);	
	
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
	public Response exportFileQuestura(@PathParam("idStructure") Integer idStructure,@QueryParam("date") String date) {
		List<Booking> allBookings = null;
		List<Booking> activeBookings = null;
		Date exportDate = null;
		List<GroupLeader> groupLeaders = null;
		List<GroupLeader> mainGroupLeaders = null;
		List<Booking> simpleBookings = null;
		StringBuilder sb = null;
		
		
		allBookings = this.getBookingService().findBookingsByIdStructure(idStructure);
		activeBookings = new ArrayList<Booking>();
		
		exportDate = new Date(Long.parseLong(date));
		//ACTIVE BOOKINGS 
		for(Booking each: allBookings){
			if( (DateUtils.truncatedCompareTo(each.getDateIn(), exportDate, Calendar.DAY_OF_MONTH) <= 0) &&
					(DateUtils.truncatedCompareTo(exportDate,each.getDateOut(), Calendar.DAY_OF_MONTH) < 0) ){
				activeBookings.add(each);				
			}
		}
		
		//GROUP LEADERS
		groupLeaders = new ArrayList<GroupLeader>();
		simpleBookings = new ArrayList<Booking>();
		mainGroupLeaders = new ArrayList<GroupLeader>();
		
		
		for(Booking each: activeBookings){	
			GroupLeader groupLeader = null;
			groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getId());
			if(groupLeader!=null){
				groupLeaders.add(groupLeader);
				if(groupLeader.getId_booking().equals(groupLeader.getHoused().getId_booking())){
					mainGroupLeaders.add(groupLeader);
				}
			}else{
				simpleBookings.add(each);
			}
		}
		
		//CREO I GRUPPI
		List<Group> groups = new ArrayList<Group>();
		
		for(GroupLeader mainGroupLeader: mainGroupLeaders){
			Group group = new Group();
			group.setLeader(mainGroupLeader.getHoused());
			
			//Membri dello stesso booking dove si trova il leader
			for(Housed housed:  this.getHousedService().findHousedByIdBooking(mainGroupLeader.getId_booking())){
				if(!housed.equals(mainGroupLeader)){
					group.getMembers().add(housed);
				}
			}
			
			//Membri dei booking collegati
			List<Integer> linkedBookingIds = new ArrayList<Integer>();
			
			for(GroupLeader groupLeader: groupLeaders){
				if( (!groupLeader.getId().equals(mainGroupLeader.getId())) && (groupLeader.getId_housed().equals(mainGroupLeader.getId_housed()))  ){
					linkedBookingIds.add(groupLeader.getId_booking());
				}
			}
			
			for(Integer idBooking: linkedBookingIds){
				group.getMembers().addAll(
				    this.getHousedService().findHousedByIdBooking(idBooking));
			}
			
			groups.add(group);			
		}
		
		sb = new StringBuilder();
		//STAMPO I GRUPPI
		for(Group each: groups){
			sb.append(each.printGroup() + "\n");
		}
		
		//STAMPO I BOOKING SEMPLICI
		
		for(Booking each: simpleBookings){
			for(Housed housed: this.getHousedService().findHousedByIdBooking(each.getId())){
				sb.append(housed.getGuest().getFirstName() + "  Ospite Singolo" + "\n");
			}
			
		}
		
		String str = sb.toString();
				
		ResponseBuilder response = Response.ok((Object) str);
		response.header("Content-Disposition",
			"attachment; filename=\"file_from_server_questura.txt\"");
		return response.build();
 
	}
	
	@GET
	@Path("structure/{idStructure}/do/sired")
	@Produces("text/plain")
	public Response exportFileSired(@PathParam("idStructure") Integer idStructure,@QueryParam("date") String date) {
		List<HousedExport> housedExportList = null;
		Date checkinDate = null;
		Date exportDate  = null;
		Set<Booking> toExportBookings = null;
		StringBuilder sb = null;
		List<GroupLeader> groupLeaders = null;
		List<GroupLeader> mainGroupLeaders = null;
		List<Booking> simpleBookings = null;
		List<Booking> activeBookings = null;
		GuestQuesturaFormatter guestQuesturaFormatter = null;
		
		exportDate = new Date(Long.parseLong(date));
		
		housedExportList = new ArrayList<HousedExport>();
		for(HousedExport each : this.getHousedExportService().findByIdStructureAndExported(idStructure, false) ){
			if(each.getHoused() != null){
				checkinDate = each.getHoused().getCheckInDate();
			}
			if(checkinDate != null && DateUtils.truncatedCompareTo(checkinDate, exportDate, Calendar.DAY_OF_MONTH) == 0){
				housedExportList.add(each);
			}
		}
		
		
		/*for(HousedExport each : housedExportList){
			sb.append(each.getHoused().getId() + " " + each.getHoused().getCheckInDate() + "\n");
		}
		*/
		
		toExportBookings = new HashSet<Booking>();	
		for(HousedExport each : housedExportList){
			toExportBookings.add(this.getBookingService().findBookingById(each.getHoused().getId_booking()));
		}
		activeBookings = new ArrayList<Booking>(toExportBookings);	
		
		//GROUP LEADERS
		groupLeaders = new ArrayList<GroupLeader>();
		simpleBookings = new ArrayList<Booking>();
		mainGroupLeaders = new ArrayList<GroupLeader>();
		
		
		for(Booking each: activeBookings){	
			GroupLeader groupLeader = null;
			groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getId());
			if(groupLeader!=null){
				groupLeaders.add(groupLeader);
				if(groupLeader.getId_booking().equals(groupLeader.getHoused().getId_booking())){
					mainGroupLeaders.add(groupLeader);
				}
			}else{
				simpleBookings.add(each);
			}
		}
		logger.info("#####Grouplears size: " + groupLeaders.size());
		logger.info("#####MainGrouplears size: " + mainGroupLeaders.size());
		//CREO I GRUPPI
		List<Group> groups = new ArrayList<Group>();
		
		for(GroupLeader aGroupLeader: groupLeaders){
			Group group = new Group();
			group.setLeader(aGroupLeader.getHoused());
			
			//Membri dello stesso booking dove si trova il leader
			for(Housed housed:  this.getHousedService().findHousedByIdBooking(aGroupLeader.getId_booking())){
				if(this.housedIsIncludedInHousedExportList(housed, housedExportList) && !housed.equals(aGroupLeader)){
					group.getMembers().add(housed);
				}
			}
			
			//Membri dei booking collegati
			List<Integer> linkedBookingIds = new ArrayList<Integer>();
			
			for(GroupLeader groupLeader: groupLeaders){
				if( (!groupLeader.getId().equals(aGroupLeader.getId())) && (groupLeader.getId_housed().equals(aGroupLeader.getId_housed()))  ){
					linkedBookingIds.add(groupLeader.getId_booking());
				}
			}
			
			for(Integer idBooking: linkedBookingIds){
				/*group.getMembers().addAll(
				    this.getHousedService().findHousedByIdBooking(idBooking));*/
				for(Housed each: this.getHousedService().findHousedByIdBooking(idBooking)){
					if(this.housedIsIncludedInHousedExportList(each, housedExportList)){
						group.getMembers().add(each);
					}
				}
			}
			
			groups.add(group);			
		}
		logger.info("#####groups size: " + groups.size());

		
		sb = new StringBuilder();
		guestQuesturaFormatter = new GuestQuesturaFormatter();
		//STAMPO I GRUPPI
		for(Group each: groups){
			sb.append(each.printGroup() + "\n");
		}
		
		//STAMPO I BOOKING SEMPLICI
		
		for(Booking each: simpleBookings){
			for(Housed housed: this.getHousedService().findHousedByIdBooking(each.getId())){
				if(this.housedIsIncludedInHousedExportList(housed, housedExportList)){
					guestQuesturaFormatter.setDataFromHoused(housed);
					sb.append(guestQuesturaFormatter.getRowRegione());
				}
			}
			
		}
		
		
		String str = sb.toString();
	    
		for(HousedExport each : housedExportList){
			each.setExported(true);
			this.getHousedExportService().update(each);
		}
		
		ResponseBuilder response = Response.ok((Object) str);
		response.header("Content-Disposition",
			"attachment; filename=\"file_from_server_sired.txt\"");
		return response.build();
 
	}
	
	public Boolean housedIsIncludedInHousedExportList(Housed housed, List<HousedExport> housedExportList ){
		Boolean ret = false;
		
		for(HousedExport each : housedExportList){
			if(each.getId_housed().equals(housed.getId()) ){
				return true;
			}
		}
		return ret;
		
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}


	public GroupLeaderService getGroupLeaderService() {
		return groupLeaderService;
	}


	public void setGroupLeaderService(GroupLeaderService groupLeaderService) {
		this.groupLeaderService = groupLeaderService;
	}


	public HousedService getHousedService() {
		return housedService;
	}


	public void setHousedService(HousedService housedService) {
		this.housedService = housedService;
	}


	public HousedExportService getHousedExportService() {
		return housedExportService;
	}


	public void setHousedExportService(HousedExportService housedExportService) {
		this.housedExportService = housedExportService;
	}
	
	
	
    	
}