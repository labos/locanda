package resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.questura.Transport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.TransportService;
import utils.I18nUtils;

@Path("/transports/")
@Component
@Scope("prototype")
public class TransportResource {
	@Autowired
    private TransportService transportService = null;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Transport> findAll(){
    	List<Transport> ret = null;
    	
    	ret = this.getTransportService().findAll();
    	for(Transport aTransport : ret){
    		aTransport.setName(I18nUtils.getProperty(aTransport.getName()));
    	}
        return ret;
    }

	public TransportService getTransportService() {
		return transportService;
	}
	public void setTransportService(TransportService transportService) {
		this.transportService = transportService;
	}

}