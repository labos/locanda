package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.questura.IdentificationType;
import model.questura.HousedType;
import model.questura.Municipality;
import model.questura.Country;



import service.TipoDocumentoService;
import service.NazioneService;
import service.CodiceAlloggiatoService;
import service.ComuneService;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;




@Path("/questura/")
@Component
@Scope("prototype")
public class QuesturaResource {
   
    @Autowired
    private TipoDocumentoService documentoService = null;

    @Autowired
    private NazioneService nazioneService = null;
    
    @Autowired
    private CodiceAlloggiatoService codiceAlloggiatoService = null;
    
    @Autowired
    private ComuneService comuneService = null;
    
    
    

    
    @PostConstruct
    public void init(){
    	
    }
        
    @GET
    @Path("documento/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<IdentificationType> getDocumenti(){
        return this.getDocumentoService().findAll();
        
    }

    @GET
    @Path("codicealloggiato/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<HousedType> getCodiceAlloggiati(){
        return this.getCodiceAlloggiatoService().findAll();
    }
    
    @GET
    @Path("nazione/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Country> getNazioni(){
        return this.getNazioneService().findAll();
    }
    
    @GET
    @Path("comune/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Municipality> getComuni(){
        return this.getComuneService().findAll();
    }
    
    @GET
    @Path("comune/allprovincia")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> getComuniProv(){
        return this.getComuneService().getAllProvincia();
    }
    
    
	public NazioneService getNazioneService() {
		return nazioneService;
	}

	public void setNazioneService(NazioneService nazioneService) {
		this.nazioneService = nazioneService;
	}

	public CodiceAlloggiatoService getCodiceAlloggiatoService() {
		return codiceAlloggiatoService;
	}

	public void setCodiceAlloggiatoService(
			CodiceAlloggiatoService codiceAlloggiatoService) {
		this.codiceAlloggiatoService = codiceAlloggiatoService;
	}

	public TipoDocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(TipoDocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public ComuneService getComuneService() {
		return comuneService;
	}

	public void setComuneService(ComuneService comuneService) {
		this.comuneService = comuneService;
	}
  
  
  
	
}