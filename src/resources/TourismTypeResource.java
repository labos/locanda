package resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.questura.TourismType;
import model.questura.Transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.TourismTypeService;
import utils.I18nUtils;

@Path("/tourismTypes/")
@Component
@Scope("prototype")
public class TourismTypeResource {
	@Autowired
    private TourismTypeService tourismTypeService = null;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TourismType> findAll(){
    	List<TourismType> ret = null;
    	
    	ret = this.getTourismTypeService().findAll();
      	for(TourismType aTourismType : ret){
      		aTourismType.setName(I18nUtils.getProperty(aTourismType.getName()));
    	}
        return ret;
    }

	public TourismTypeService getTourismTypeService() {
		return tourismTypeService;
	}
	public void setTourismTypeService(TourismTypeService tourismTypeService) {
		this.tourismTypeService = tourismTypeService;
	}

}