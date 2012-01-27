package resources;

import java.io.File;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.FacilityService;

@Path("/facilities/")
@Component
@Scope("prototype")
public class FacilityResource {
	@Autowired
	private FacilityService facilityService = null;
	
	@Context
	private ServletContext servletContext = null;
	
	
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Facility> getStructureFacilities(@PathParam("idStructure") Integer idStructure){
		return this.getFacilityService().findStructureFacilitiesByIdStructure(idStructure);
	}	
	
	@GET
	@Path("roomAndRoomTypeFacilities/{idStructure}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Facility> getRoomAndRoomTypeFacilities(@PathParam("idStructure") Integer idStructure){
		return this.getFacilityService().findRoomAndRoomTypeFacilitiesByIdStructure(idStructure);
	}	
	
	@POST
    @Path("checkRoomTypeFacility/{id_roomType}/{id_facility}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer checkRoomTypeFacility(@PathParam("id_roomType") Integer id_roomType, @PathParam("id_facility") Integer id_facility){
    	Integer count = 0;			
		
		
    	count = this.getFacilityService().insertRoomTypeFacility(id_roomType, id_facility);
    	if(count == 0){
			throw new NotFoundException("Error: the facility has NOT been checked");
		}			
		return count;
    }  
	
	@DELETE
    @Path("uncheckRoomTypeFacility/{id_roomType}/{id_facility}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer uncheckRoomTypeFacility(@PathParam("id_roomType") Integer id_roomType, @PathParam("id_facility") Integer id_facility){
    	Integer count = 0;				
		
    	count = this.getFacilityService().deleteRoomTypeFacility(id_roomType, id_facility);
    	if(count == 0){
			throw new NotFoundException("Error: the facility has NOT been deleted");
		}	
		
		return count;
    }   
	
	 

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public FacilityService getFacilityService() {
		return facilityService;
	}

	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}	
	
	
}
