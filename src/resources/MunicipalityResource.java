package resources;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.questura.Municipality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.MunicipalityService;

@Path("/municipalities/")
@Component
@Scope("prototype")
public class MunicipalityResource {
	@Autowired
    private MunicipalityService municipalityService = null;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Municipality> findAll(){
    	List<Municipality> ret = null;
    	
    	ret = this.getMunicipalityService().findAll();
        return ret;
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Municipality findById(@PathParam("id") Integer id){
    	Municipality ret = null;
    	
    	ret = this.getMunicipalityService().findById(id);
        return ret;
    }
    
    @GET
    @Path("province/{province}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Municipality> findMunicipalitiesByProvince(@PathParam("province") String province){
    	List<Municipality> ret = null;
    	
    	ret = this.getMunicipalityService().findMunicipalitiesByProvince(province);
        return ret;
    }
    
    @GET
    @Path("provinces")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> findAllProvinces(){
    	List<String> ret = null;
    	
    	ret = this.getMunicipalityService().findAllProvinces();
    	
    	return ret;
    }

	public MunicipalityService getMunicipalityService() {
		return municipalityService;
	}
	public void setMunicipalityService(MunicipalityService municipalityService) {
		this.municipalityService = municipalityService;
	}

}