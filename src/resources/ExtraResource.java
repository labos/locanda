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

import model.Extra;
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

import service.BookingService;
import service.ExtraPriceListService;
import service.ExtraService;
import service.StructureService;
import utils.I18nUtils;

@Path("/extras/")
@Component
@Scope("prototype")
public class ExtraResource {
    @Autowired
    private ExtraService extraService = null;
    @Autowired
    private ExtraPriceListService extraPriceListService = null;
    @Autowired
    private StructureService structureService = null;
    @Autowired
    private BookingService bookingService = null;
    @Autowired
    private SolrServer solrServerExtra = null;
   
    
    @PostConstruct
    public void init(){
    	List<Extra> extras = null;
    	
    	extras = this.getExtraService().findAll();
    	try {
			this.getSolrServerExtra().addBeans(extras);
			this.getSolrServerExtra().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @GET
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Extra> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<Extra> extras = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Extra anExtra = null;
        Integer id;             
       
        if(term.trim().equals("")){
        	term = "*:*";
        }
        term = term + " AND id_structure:" + idStructure.toString();
        query = new SolrQuery();   		
        query.setQuery(term);
        query.setStart(start);
        query.setRows(rows);
              
        try {
			rsp = this.getSolrServerExtra().query(query);
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

        extras = new ArrayList<Extra>();
        if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
        	// System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   anExtra = this.getExtraService().findExtraById(id);
        	   extras.add(anExtra);
           }  
       }       
       return extras;          
    }
    
    @GET
   	@Path("structure/{idStructure}/suggest")
   	@Produces({ MediaType.APPLICATION_JSON })
   	public List<String> suggest(@PathParam("idStructure") Integer idStructure,
   			@QueryParam("term") String term) {
   		SolrQuery query = null;
   		QueryResponse rsp = null;
   		List<String> ret = null;
   		List<Count> values = null;
   		
   		query = new SolrQuery();
   		query.setFacet(true);
   		query.setQuery("*:* AND id_structure:" + idStructure.toString());

   		query.addFacetField("text");
   		term = term.toLowerCase();
   		query.setFacetPrefix(term);

   		try {
   			rsp = this.getSolrServerExtra().query(query);
   		} catch (SolrServerException e) {
   			e.printStackTrace();
   		}
   		ret = new ArrayList<String>();

   		if (rsp != null) {
   			values = rsp.getFacetField("text").getValues();
   			if(values!=null){
   				for(Count each: values){
   					if(each.getCount()>0){
   						ret.add(each.getName());
   					}
   				}	
   			}					
   		}
   		return ret;
   	}
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Extra getExtra(@PathParam("id") Integer id){
    	Extra ret = null;
       
        ret = this.getExtraService().findExtraById(id);
        return ret;   
    }
  
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Extra save(Extra extra) {
       
        this.getExtraService().insertExtra(extra);
        this.getStructureService().modifyPriceListsForExtra(extra.getId_structure(),extra.getId());
        try {
			this.getSolrServerExtra().addBean(extra);			
			this.getSolrServerExtra().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return extra;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Extra update(Extra extra) {  
    	try{
    		this.getExtraService().updateExtra(extra);
    		}catch(Exception ex){
    	}
    	try {
			this.getSolrServerExtra().addBean(extra);			
			this.getSolrServerExtra().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return extra;
    }
   
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		if(this.getBookingService().countBookingsByIdExtra(id) > 0){
			throw new NotFoundException(I18nUtils.getProperty("extraDeleteBookingError"));
		}
		count = this.getExtraService().deleteExtra(id);
		this.getExtraPriceListService().deleteExtraPriceListItemsByIdExtra(id);
		if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("extraDeleteErrorAction"));
		}
		
		try {
			this.getSolrServerExtra().deleteById(id.toString());
			this.getSolrServerExtra().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
    }

	public ExtraService getExtraService() {
		return extraService;
	}
	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}
	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}
	public void setExtraPriceListService(ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
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
	public SolrServer getSolrServerExtra() {
		return solrServerExtra;
	}
	public void setSolrServerExtra(SolrServer solrServerExtra) {
		this.solrServerExtra = solrServerExtra;
	}   
   
}