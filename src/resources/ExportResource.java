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
import model.questura.HousedExport;
import model.questura.HousedExportGroup;
import model.questura.HousedType;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.BookingService;
import service.ExportService;
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
	@Autowired
	private ExportService exportService = null;	
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
		List<HousedExport> housedExportList = null;
		Date exportDate  = null;
		StringBuilder sb = null;
		List<HousedExportGroup> housedExportGroupList = null;		
		List<HousedExport> housedExportSingleList = null;
		Integer availableRooms;
		Integer availableBeds;
		
	
		exportDate = new Date(Long.parseLong(date));
		
		housedExportList = this.findHousedExportList(idStructure, exportDate);
				
		//CREO I GRUPPI HousedExportGroup			
		housedExportGroupList = this.findHousedExportGroups(housedExportList);
		
		//CREO LA LISTA DEGLI HOUSED EXPORT Singoli
		housedExportSingleList = this.findHousedExportSingleList(housedExportList);
		
		availableRooms =this.getExportService().calculateAvailableNumberOfRoomsForStructureInDate(idStructure, exportDate);
		availableBeds = this.getExportService().calculateAvailableNumberOfBedsForStructureInDate(idStructure, exportDate);
		
		sb = new StringBuilder();
		
		for(HousedExportGroup each : housedExportGroupList){			
			GuestQuesturaFormatter guestQuesturaFormatterLeader = new GuestQuesturaFormatter();
			guestQuesturaFormatterLeader.setModalita(each.getHousedExportLeader().getMode());
			guestQuesturaFormatterLeader.setCamereOccupate(this.calculateNumberOfOccupiedRoomsForGroup(each));
			guestQuesturaFormatterLeader.setCamereDisponibili(availableRooms);
			guestQuesturaFormatterLeader.setLettiDisponibili(availableBeds);
			guestQuesturaFormatterLeader.setNumGiorniPermanenza(this.calculateLengthOfStay(each.getHousedExportLeader()));
			guestQuesturaFormatterLeader.setDataFromHousedForQuestura(each.getHousedExportLeader().getHoused());
			sb.append(guestQuesturaFormatterLeader.getRowQuestura(false));
			for(HousedExport aMember: each.getHousedExportMembers()){

				GuestQuesturaFormatter guestQuesturaFormatter = new GuestQuesturaFormatter();
				HousedType anHousedType = new HousedType();
				if(each.getHousedExportLeader().getHoused().getHousedType().getCode().equals(HousedType.CAPOFAMIGLIA)){
					anHousedType.setCode(HousedType.FAMILIARE);
				}else{
					anHousedType.setCode(HousedType.MEMBRO_GRUPPO);
				}
				aMember.getHoused().setHousedType(anHousedType);	
				guestQuesturaFormatter.setNumGiorniPermanenza(this.calculateLengthOfStay(aMember));
				guestQuesturaFormatter.setDataFromHousedForQuestura(aMember.getHoused());
				sb.append(guestQuesturaFormatter.getRowQuestura(false));

			}
			
		}
		
		for(HousedExport each : housedExportSingleList){
			GuestQuesturaFormatter guestQuesturaFormatter = new GuestQuesturaFormatter();
			HousedType anHousedType = new HousedType();
			anHousedType.setCode(HousedType.OSPITE_SINGOLO);
			each.getHoused().setHousedType(anHousedType);	
			guestQuesturaFormatter.setModalita(each.getMode());
			guestQuesturaFormatter.setCamereOccupate(1);
			guestQuesturaFormatter.setCamereDisponibili(availableRooms);
			guestQuesturaFormatter.setLettiDisponibili(availableBeds);
			guestQuesturaFormatter.setNumGiorniPermanenza(this.calculateLengthOfStay(each));
			guestQuesturaFormatter.setDataFromHousedForQuestura(each.getHoused());
			sb.append(guestQuesturaFormatter.getRowQuestura(false));
			
		}
		
		String str = sb.toString();
	    
		for(HousedExport each : housedExportList){
			each.setExported(true);
			this.getHousedExportService().update(each);
		}
		
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
		Date exportDate  = null;
		StringBuilder sb = null;
		List<HousedExportGroup> housedExportGroupList = null;		
		List<HousedExport> housedExportSingleList = null;
		Integer availableRooms;
		Integer availableBeds;
		
	
		exportDate = new Date(Long.parseLong(date));
		
		housedExportList = this.findHousedExportList(idStructure, exportDate);
				
		//CREO I GRUPPI HousedExportGroup			
		housedExportGroupList = this.findHousedExportGroups(housedExportList);
		
		//CREO LA LISTA DEGLI HOUSED EXPORT Singoli
		housedExportSingleList = this.findHousedExportSingleList(housedExportList);
		
		availableRooms =this.getExportService().calculateAvailableNumberOfRoomsForStructureInDate(idStructure, exportDate);
		availableBeds = this.getExportService().calculateAvailableNumberOfBedsForStructureInDate(idStructure, exportDate);
		
		sb = new StringBuilder();
		
		for(HousedExportGroup each : housedExportGroupList){			
			GuestQuesturaFormatter guestQuesturaFormatterLeader = new GuestQuesturaFormatter();
			guestQuesturaFormatterLeader.setModalita(each.getHousedExportLeader().getMode());
			guestQuesturaFormatterLeader.setCamereOccupate(this.calculateNumberOfOccupiedRoomsForGroup(each));
			guestQuesturaFormatterLeader.setCamereDisponibili(availableRooms);
			guestQuesturaFormatterLeader.setLettiDisponibili(availableBeds);
			guestQuesturaFormatterLeader.setDataFromHousedForRegione(each.getHousedExportLeader().getHoused());
			sb.append(guestQuesturaFormatterLeader.getRowRegione());
			for(HousedExport aMember: each.getHousedExportMembers()){

				GuestQuesturaFormatter guestQuesturaFormatter = new GuestQuesturaFormatter();
				HousedType anHousedType = new HousedType();
				if(each.getHousedExportLeader().getHoused().getHousedType().getCode().equals(HousedType.CAPOFAMIGLIA)){
					anHousedType.setCode(HousedType.FAMILIARE);
				}else{
					anHousedType.setCode(HousedType.MEMBRO_GRUPPO);
				}
				aMember.getHoused().setHousedType(anHousedType);
				guestQuesturaFormatter.setModalita(aMember.getMode());
				guestQuesturaFormatter.setDataFromHousedForRegione(aMember.getHoused());
				sb.append(guestQuesturaFormatter.getRowRegione());

			}
			
		}
		
		for(HousedExport each : housedExportSingleList){
			GuestQuesturaFormatter guestQuesturaFormatter = new GuestQuesturaFormatter();
			HousedType anHousedType = new HousedType();
			anHousedType.setCode(HousedType.OSPITE_SINGOLO);
			each.getHoused().setHousedType(anHousedType);	
			guestQuesturaFormatter.setModalita(each.getMode());
			guestQuesturaFormatter.setCamereOccupate(1);
			guestQuesturaFormatter.setCamereDisponibili(availableRooms);
			guestQuesturaFormatter.setLettiDisponibili(availableBeds);
			guestQuesturaFormatter.setDataFromHousedForRegione(each.getHoused());
			sb.append(guestQuesturaFormatter.getRowRegione());
			
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
	
	private List<HousedExport> findHousedExportList(Integer idStructure, Date exportDate){
		List<HousedExport> ret = null;
		Date checkinDate = null;
				
		ret = new ArrayList<HousedExport>();
		for(HousedExport each : this.getHousedExportService().findByIdStructureAndExported(idStructure, false) ){
			checkinDate = each.getHoused().getCheckInDate();
			if(checkinDate != null && DateUtils.truncatedCompareTo(checkinDate, exportDate, Calendar.DAY_OF_MONTH) == 0){
				ret.add(each);
			}
		}	
		
		return ret;
		
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
	
	private Set<HousedExport> findHousedExportLeaders(List<HousedExport> housedExportList ){
		Set<HousedExport> ret = null;
		
		ret = new HashSet<HousedExport>();
		for(HousedExport each: housedExportList){
			GroupLeader eachGroupLeader = null;			
			eachGroupLeader = this.getGroupLeaderService().findGroupLeaderByIdBooking(each.getHoused().getId_booking());
			if(eachGroupLeader!=null){
				HousedExport housedExportLeader = null;
				housedExportLeader = this.findHousedExportByHousedInHousedExportList(eachGroupLeader.getHoused(), housedExportList);
				if(housedExportLeader!=null){
					ret.add(housedExportLeader);
				}
			}			
		}
		return ret;
	}
	
	
	private List<HousedExportGroup> findHousedExportGroups(List<HousedExport> housedExportList ){
		List<HousedExportGroup> ret = null;
		Set<HousedExport> housedExportLeaders  = null;
		
		//HOUSED EXPORT LEADERS
		housedExportLeaders = this.findHousedExportLeaders(housedExportList);	
		
		//CREO I GRUPPI a partire da ogni HOUSED EXPORT LEADER
		ret = new ArrayList<HousedExportGroup>();	
		
		for(HousedExport each: housedExportLeaders){			
			List<HousedExport> housedExportMembers = null;			
			HousedExportGroup housedExportGroup = null;
			
			housedExportGroup = new HousedExportGroup();
			
			housedExportGroup.setHousedExportLeader(each);
			housedExportMembers = this.findHousedExportMembersOfHousedExportLeader(each, housedExportList);
			housedExportGroup.getHousedExportMembers().addAll(housedExportMembers);		
			
			ret.add(housedExportGroup);		
		}	
		
		return ret;
	}
	
	private Integer calculateNumberOfOccupiedRoomsForGroup(HousedExportGroup housedExportGroup){
		Integer ret = 0;
		Set<Integer> bookingIds = null;
		
		bookingIds = new HashSet<Integer>();
		
		bookingIds.add(housedExportGroup.getHousedExportLeader().getHoused().getId_booking());
		for(HousedExport each: housedExportGroup.getHousedExportMembers()){
			bookingIds.add(each.getHoused().getId_booking());
		}
		
		ret = bookingIds.size();
		return ret;
	}
	
	private Integer calculateLengthOfStay(HousedExport housedExport){
		//30 is the max length of stay 
		Integer ret = 30;
		
		Date checkinDate = null;
		Date checkOutDate = null;
		
		checkinDate = housedExport.getHoused().getCheckInDate();
		checkOutDate = housedExport.getHoused().getCheckOutDate();
		
		if(checkinDate!=null && checkOutDate!=null){
			List<Date> bookingDates = null; 
			Date current = null;
			Integer i = 0;
			
			bookingDates = new ArrayList<Date>();
			
			current  = DateUtils.addDays(checkinDate, i );		
			while(DateUtils.truncatedCompareTo(current, checkOutDate,Calendar.DAY_OF_MONTH ) < 0){
				bookingDates.add(current);
				i = i + 1;
				current  = DateUtils.addDays(checkinDate, i );
			}	
			ret = bookingDates.size();
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


	public ExportService getExportService() {
		return exportService;
	}


	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
	
	
    	
}