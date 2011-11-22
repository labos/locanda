package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.StructureService;

@Path("/structure/")
@Component
@Scope("prototype")
public class StructureResource {
	@Autowired
	private StructureService structureService = null;
	
	@GET
	@Path("{idStructure}")
	@Produces({MediaType.APPLICATION_JSON})
	public Structure getStructure(@PathParam("idStructure") Integer idStructure){
		return this.getStructureService().findStructureById(idStructure);				
	}
	
	public StructureService getStructureService() {
		return structureService;
	}

	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	
}
