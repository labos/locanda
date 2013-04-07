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
import model.GroupLeader;
import model.GuestQuesturaFormatter;
import model.Housed;
import model.questura.Group;
import model.questura.HousedExport;
import model.questura.HousedExportGroup;
import model.questura.HousedType;

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
		Set<Booking> bookingExportSet = null;
		StringBuilder sb = null;
		List<GroupLeader> groupLeaders = null;
		List<Booking> simpleBookings = null;
		GuestQuesturaFormatter guestQuesturaFormatter = null;
		List<Group> groups = null;
		Set<Housed> housedLeaderSet = null;
		List<HousedExportGroup> housedExportGroupList = null;
		List<HousedExport> housedExportSingleList = null;
		
		
		exportDate = new Date(Long.parseLong(date));
		
		housedExportList = new ArrayList<HousedExport>();
		for(HousedExport each : this.getHousedExportService().findByIdStructureAndExported(idStructure, false) ){
			checkinDate = each.getHoused().getCheckInDate();
			if(checkinDate != null && DateUtils.truncatedCompareTo(checkinDate, exportDate, Calendar.DAY_OF_MONTH) == 0){
				housedExportList.add(each);
			}
		}		
		
		bookingExportSet = new HashSet<Booking>();	
		for(HousedExport each : housedExportList){
			bookingExportSet.add(this.getBookingService().findBookingById(each.getHoused().getId_booking()));
		}
				
		//GROUP LEADERS
		groupLeaders = new ArrayList<GroupLeader>();
		for(Booking each: bookingExportSet){	
			GroupLeader groupLeader = null;
			groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getId());
			if(groupLeader!=null && this.housedIsIncludedInHousedExportList(groupLeader.getHoused(), housedExportList)){
				groupLeaders.add(groupLeader);
			}
		}
		logger.info("#####Group leaders size: " + groupLeaders.size());
		
		// HOUSED LEADER SET		
		housedLeaderSet = new HashSet<Housed>();
		for(GroupLeader each: groupLeaders){
			housedLeaderSet.add(each.getHoused());
		}
		
		//CREO I GRUPPI HousedExportGroup
		housedExportGroupList = new ArrayList<HousedExportGroup>();
		for(Housed each: housedLeaderSet){
			HousedExport housedExportLeader = null;
			List<HousedExport> housedExportMembers = null;			
			HousedExportGroup housedExportGroup = null;
			
			housedExportGroup = new HousedExportGroup();
			
			housedExportLeader = this.findHousedExportByHousedInHousedExportList(each, housedExportList);
			housedExportGroup.setHousedExportLeader(housedExportLeader);
			housedExportMembers = this.findHousedExportMembersOfHousedExportLeader(housedExportLeader, housedExportList);
			housedExportGroup.getHousedExportMembers().addAll(housedExportMembers);		
			
			housedExportGroupList.add(housedExportGroup);			
		}
		
		
		//CREO LA LISTA DEGLI HOUSED EXPORT Singoli
		housedExportSingleList = new ArrayList<HousedExport>();		
		housedExportSingleList = this.findHousedExportSingleList(housedExportList);
		
		
		//STAMPO I GRUPPI
		
		//STAMPO GLI HOUSED EXPORT SINGOLI
		
		/*
		
		//SIMPLE BOOKINGS Da RIVEdere
		simpleBookings = new ArrayList<Booking>();
		for(Booking each: bookingExportSet){	
			GroupLeader groupLeader = null;
			groupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getId());
			if(groupLeader ==null){
				simpleBookings.add(each);
			}
		}
		logger.info("#####Simple Booking size: " + simpleBookings.size());	
		
		
		
		
		//CREO I GRUPPI
		groups = new ArrayList<Group>();
		
		for(GroupLeader aGroupLeader: groupLeaders){
			//check if current groupLeader is already present
			Group group = null;
			Boolean alreadyInGroups = false; //required to choose if add a new group
			for(Group each : groups){
				if(each.getLeader().equals(aGroupLeader.getHoused())){
					 group = each;
					 alreadyInGroups = true;
				}
			}
			if(!alreadyInGroups){
				group = new Group();
				group.setLeader(aGroupLeader.getHoused());
			}

		 

			for(Housed housed:  this.getHousedService().findHousedByIdBooking(aGroupLeader.getId_booking())){
				if(this.housedIsIncludedInHousedExportList(housed, housedExportList) && !housed.equals(aGroupLeader.getHoused())){
					HousedType anHousedType = new HousedType();
					//a group member
					if(aGroupLeader.getHoused().getHousedType().getCode() == 17){
						anHousedType.setCode(19);
					}else{
						anHousedType.setCode(20);
					}
					
					housed.setHousedType(anHousedType);
					group.getMembers().add(housed);
				}
			}
			
			
			if(!alreadyInGroups){
				groups.add(group);	
			}
		
		}
		logger.info("#####groups size: " + groups.size());

		
		sb = new StringBuilder();
		
		//STAMPO I GRUPPI
		for(Group each: groups){
			sb.append(each.printGroup());
		}
		
		//STAMPO I BOOKING SEMPLICI
		// ATTENZIONE: ci vuole un findHousedByIdBookingIncludingDeleted
		for(Booking each: simpleBookings){
			for(Housed housed: this.getHousedService().findHousedByIdBooking(each.getId())){
				if(this.housedIsIncludedInHousedExportList(housed, housedExportList)){
					guestQuesturaFormatter = new GuestQuesturaFormatter();
					HousedType anHousedType = new HousedType();
					//a group member
					anHousedType.setCode(16);
					housed.setHousedType(anHousedType);
					guestQuesturaFormatter.setModalita(this.getHousedExportService().findByIdHoused(housed.getId()).getMode());
					guestQuesturaFormatter.setDataFromHousedForRegione(housed);
					sb.append(guestQuesturaFormatter.getRowRegione());
				}
			}
			
		}
		
		*/
		
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
	
	private Boolean housedIsIncludedInHousedExportList(Housed housed, List<HousedExport> housedExportList ){
		Boolean ret = false;
		
		for(HousedExport each : housedExportList){
			if(each.getId_housed().equals(housed.getId()) ){
				return true;
			}
		}
		return ret;
		
	}
	
	private HousedExport findHousedExportByHousedInHousedExportList(Housed housed, List<HousedExport> housedExportList ){
		HousedExport ret = null;
		
		for(HousedExport each: housedExportList){
			if(each.getHoused().equals(housed)){
				return each;
			}
		}
		
		return ret;
	}
	
	private List<HousedExport> findHousedExportMembersOfHousedExportLeader(HousedExport housedExportLeader, List<HousedExport> housedExportList ){
		List<HousedExport> ret = null;
		
		
		ret = new ArrayList<HousedExport>();
		for(HousedExport each: housedExportList){
			GroupLeader eachGroupLeader = null;
			if(!each.equals(housedExportLeader)){
				eachGroupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getHoused().getId_booking());
				if(eachGroupLeader!=null && eachGroupLeader.getHoused().equals(housedExportLeader.getHoused())){
					ret.add(each);
				}
			}
			
		}
		return ret;
	}
	
	private List<HousedExport> findHousedExportSingleList(List<HousedExport> housedExportList ){
		List<HousedExport> ret = null;
		
		
		ret = new ArrayList<HousedExport>();
		for(HousedExport each: housedExportList){
			GroupLeader eachGroupLeader = null;
			eachGroupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getHoused().getId_booking());
			
			if(eachGroupLeader == null){
				ret.add(each);
			}else{
				if(!this.housedIsIncludedInHousedExportList(eachGroupLeader.getHoused(), housedExportList)){
					ret.add(each);
				}
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