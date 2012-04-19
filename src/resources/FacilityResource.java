package resources;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import model.Facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import service.FacilityService;

@Path("/facilities/")
@Component
@Scope("prototype")
public class FacilityResource {
	@Autowired
	private FacilityService facilityService = null;	
		
		
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})	
	public Facility findFacilityById(@PathParam("id") Integer id){
		Facility facility = null;
		
		facility = this.getFacilityService().find(id);
		if (facility == null) {
			throw new WebApplicationException(404);
		}
		
		return 	facility;	
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
	public Facility insert(Facility facility){
		this.getFacilityService().insert(facility);
		return facility;		
	}
	
	@PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
	public Facility update(Facility facility){
		this.getFacilityService().update(facility);		
		return facility;		
	}
	
	 @DELETE
	 @Path("{id}")
	 @Produces({MediaType.APPLICATION_JSON})   
	 public Integer delete(@PathParam("id") Integer id){
		 Integer count = 0;		
		
		count =  this.getFacilityService().delete(id);
		
		return count;
	 } 
	
	 @GET
	 @Path("all/structure/{idStructure}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getAllFacilities(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findByIdStructure(idStructure,offset,rownum);
	 }
	 
	 @GET
	 @Path("checked/structure/{idStructure}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getStructureFacilities(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findCheckedByIdStructure(idStructure);
	 }
	 
	 @GET
	 @Path("checked/roomType/{idRoomType}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getRoomTypeFacilities(@PathParam("idRoomType") Integer idRoomType,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findCheckedByIdRoomType(idRoomType);
	 }
	 
	 @GET
	 @Path("checked/room/{idRoom}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getRoomFacilities(@PathParam("idRoom") Integer idRoom,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findCheckedByIdRoom(idRoom);
	 }
	
	public FacilityService getFacilityService() {
		return facilityService;
	}

	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

		
	
}
