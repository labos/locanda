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
import model.listini.Convention;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.update.SolrIndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.BookingService;
import service.ConventionService;
import service.StructureService;

@Path("/conventions/")
@Component
@Scope("prototype")
public class ConventionResource {
   
    @Autowired
    private ConventionService conventionService = null;
    @Autowired
    private StructureService structureService = null;
    @Autowired
    private BookingService bookingService = null;
    @Autowired
    private SolrServer solrServerConvention = null;
   
    @PostConstruct
    public void init(){
    	List<Convention> conventions = null;
    	
    	conventions = this.getConventionService().findAll();
    	try {
			this.getSolrServerConvention().addBeans(conventions);
			this.getSolrServerConvention().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    @GET
    @Path("structure/{idStructure}/search/{offset}/{rownum}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Convention> simpleSearch(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum, @QueryParam("term") String term){
        List<Convention> conventions = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Convention aConvention = null;
        Integer id;                
        
        query = new SolrQuery();
        if(term.trim().equals("")){
        	term = "*:*";
        }
        query.setQuery( term );
        try {
			rsp = this.getSolrServerConvention().query( query );
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

       conventions = new ArrayList<Convention>();
        
       solrDocumentList = rsp.getResults();
       for(int i = 0; i <solrDocumentList.size(); i++){
    	   solrDocument = solrDocumentList.get(i);
    	   id = (Integer)solrDocument.getFieldValue("id");
    	   aConvention = this.getConventionService().findConventionById(id);
    	   conventions.add(aConvention);
       }
       return conventions;          
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Convention getConvention(@PathParam("id") Integer id){
        Convention ret = null;
       
        ret = this.getConventionService().findConventionById(id);
        return ret;   
    }
  
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Convention save(Convention convention) {
       
        this.getConventionService().insertConvention(convention);
        this.getStructureService().addPriceListsForConvention(convention.getId_structure(),convention.getId() );
        try {
			this.getSolrServerConvention().addBean(convention);			
			this.getSolrServerConvention().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return convention;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Convention update(Convention convention) {  
    	try{
    		this.getConventionService().updateConvention(convention);
    	}catch(Exception ex){
    		
    	}
    	
    	try {
			this.getSolrServerConvention().addBean(convention);			
			this.getSolrServerConvention().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return convention;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		if(this.getBookingService().countBookingsByIdConvention(id) > 0){
			throw new NotFoundException("The convention you are trying to delete has links to one or more bookings." +
					" Please try to delete the associated bookings before.");
		}
		count = this.getConventionService().deleteConvention(id);
		if(count == 0){
			throw new NotFoundException("Error: the convention has NOT been deleted");
		}
		
		try {
			this.getSolrServerConvention().deleteById(id.toString());
			this.getSolrServerConvention().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
    }   
   
    public ConventionService getConventionService() {
        return conventionService;
    }
    public void setConventionService(ConventionService conventionService) {
        this.conventionService = conventionService;
    }
    public StructureService getStructureService() {
        return structureService;
    }
    public void setStructureService(StructureService structureService) {
        this.structureService = structureService;
    }
	public BookingService getBookingService() {
		return bookingService;
	}
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public SolrServer getSolrServerConvention() {
		return solrServerConvention;
	}

	public void setSolrServerConvention(SolrServer solrServerConvention) {
		this.solrServerConvention = solrServerConvention;
	}  	

}