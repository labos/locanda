package resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.listini.Convention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.ConventionService;

@Path("/conventions/")
@Component
@Scope("prototype")
public class ConventionResource {
	
	@Autowired
	private ConventionService conventionService;
	
	@GET
	@Path("structure/{idStructure}")
	@Produces({MediaType.APPLICATION_JSON})
	
	public List<Convention> getConventions(@PathParam("idStructure") Integer idStructure){
		List<Convention> filteredConventions = null;
		
		filteredConventions = new ArrayList<Convention>();
		
		for(Convention each: this.getConventionService().findConventionsByIdStructure(idStructure)){			
			if(!each.getActivationCode().equals("thisconventionshouldntneverberemoved")){
				filteredConventions.add(each);
			}			
		}		
		return filteredConventions;	
		
	}
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	
	public Convention getConvention(@PathParam("id") Integer id){
		Convention ret = null;
		
		ret = this.getConventionService().findConventionById(id);
		return ret;
		
	}
	
	
	public ConventionService getConventionService() {
		return conventionService;
	}

	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}
	
	

}
