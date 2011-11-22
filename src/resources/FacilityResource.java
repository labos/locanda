package resources;

import java.io.File;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
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
	@Path("structure/{id}")
	@Produces("image/*")
	public Response getFacility(@PathParam("id") Integer id) {
		Facility facility = null;
		String filePath = null;
		File file = null;
		
		facility = this.getFacilityService().findStructureFacilityById(id);
		if (facility == null) {
			throw new WebApplicationException(404);
		}
		
		filePath = this.getServletContext().getRealPath("/") +  "resources/" + facility.getId_structure() + "/facilities/structure/" + facility.getFileName();
		file = new File(filePath);
		
		String mt = new MimetypesFileTypeMap().getContentType(file);
		return Response.ok(file, mt).build();
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
