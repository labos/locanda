package resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.questura.Country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.CountryService;
import utils.I18nUtils;

@Path("/countries/")
@Component
@Scope("prototype")
public class CountryResource {
	@Autowired
    private CountryService countryService = null;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Country> findAll(){
    	List<Country> ret = null;
    	
    	ret = this.getCountryService().findAll();
    	
    	for(Country aCountry : ret){
    		aCountry.setDescription(I18nUtils.getProperty(aCountry.getDescription()));
    	}
        return ret;
    }

	public CountryService getCountryService() {
		return countryService;
	}
	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

}