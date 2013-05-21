package resources;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.questura.IdentificationType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.IdentificationTypeService;
import utils.I18nUtils;

@Path("/identificationTypes/")
@Component
@Scope("prototype")
public class IdentificationTypeResource {
	@Autowired
    private IdentificationTypeService identificationTypeService = null;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<IdentificationType> findAll(){
    	List<IdentificationType> ret = null;
    	
    	ret = this.getIdentificationTypeService().findAll();
    	
    	for(IdentificationType anIdentificationType : ret){
    		if (I18nUtils.getProperty(anIdentificationType.getDescription()) != null) {
    			anIdentificationType.setDescription(I18nUtils.getProperty(anIdentificationType.getDescription()));
    		}
    	}
        return ret;
    }

	public IdentificationTypeService getIdentificationTypeService() {
		return identificationTypeService;
	}
	public void setIdentificationTypeService(
			IdentificationTypeService identificationTypeService) {
		this.identificationTypeService = identificationTypeService;
	}

}