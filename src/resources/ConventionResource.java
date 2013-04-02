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
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.BookingService;
import service.ConventionService;
import service.ExtraPriceListService;
import service.RoomPriceListService;
import service.StructureService;
import utils.I18nUtils;

@Path("/conventions/")
@Component
@Scope("prototype")
public class ConventionResource {
    @Autowired
    private ConventionService conventionService = null;
    @Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
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
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Convention> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<Convention> conventions = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Convention aConvention = null;
        Integer id;             
       
        if(term.trim().equals("")){
        	term = "*:*";
        }
        term = term + " AND id_structure:" + idStructure.toString();
        query = new SolrQuery();   		
        query.setQuery( term);
        query.setStart(start);
        query.setRows(rows);
              
        try {
			rsp = this.getSolrServerConvention().query( query );
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

       conventions = new ArrayList<Convention>();
       if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
        	// System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   aConvention = this.getConventionService().findConventionByIdWithoutDefault(id);
        	   if(aConvention!=null){
            	   conventions.add(aConvention);       		   
        	   }

           }  
       }       
       return conventions;          
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
			rsp = this.getSolrServerConvention().query(query);
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
    	}catch(Exception ex){}	
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
			throw new NotFoundException(I18nUtils.getProperty("conventionDeleteBookingError"));
		}
		count = this.getConventionService().deleteConvention(id);
		this.getRoomPriceListService().deleteRoomPriceListsByIdSeason(id);
		this.getExtraPriceListService().deleteExtraPriceListsByIdSeason(id);
		if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("conventionDeleteErrorAction"));
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
    public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}
	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
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
	public SolrServer getSolrServerConvention() {
		return solrServerConvention;
	}
	public void setSolrServerConvention(SolrServer solrServerConvention) {
		this.solrServerConvention = solrServerConvention;
	}  	
	
}