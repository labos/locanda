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

import model.listini.Period;
import model.listini.Season;

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
import service.PeriodService;
import service.RoomPriceListService;
import service.SeasonService;
import service.StructureService;
import utils.I18nUtils;

@Path("/seasons/")
@Component
@Scope("prototype")
public class SeasonResource {
	@Autowired
	private SeasonService seasonService = null;
	@Autowired
	private PeriodService periodService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
    private StructureService structureService = null;
	@Autowired
    private BookingService bookingService = null;
	@Autowired
    private SolrServer solrServerSeason = null;
	
	
	@PostConstruct
    public void init(){
    	List<Season> seasons = null;
    	
    	seasons = this.getSeasonService().findAll();
    	try {
			this.getSolrServerSeason().addBeans(seasons);
			this.getSolrServerSeason().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @GET
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Season> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<Season> seasons = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Season aSeason = null;
        List<Period> periods = null;
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
			rsp = this.getSolrServerSeason().query(query);	
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

        seasons = new ArrayList<Season>();
        if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
        	// System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   aSeason = this.getSeasonService().findSeasonById(id);
        	   periods = this.getPeriodService().findPeriodsByIdSeason(id);
        	   aSeason.setPeriods(periods);
        	   seasons.add(aSeason);
           }  
       }       
       return seasons;          
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
   			rsp = this.getSolrServerSeason().query(query);
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
	public Season getSeason(@PathParam("id") Integer id){
		Season ret = null;
		List<Period> periods = null;
		
		ret = this.getSeasonService().findSeasonById(id);
		periods = this.getPeriodService().findPeriodsByIdSeason(id);
		ret.setPeriods(periods);
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Season save(Season season) {
		
        this.getSeasonService().insertSeason(season);
        this.getStructureService().addPriceListsForSeason(season.getId_structure(), season.getId());
        try {
			this.getSolrServerSeason().addBean(season);			
			this.getSolrServerSeason().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return season;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Season update(Season season) {
    	Boolean validYear = this.getSeasonService().checkYears(season);
        
    	if (!validYear) {
			throw new NotFoundException("Season's year does not match with periods' year");
		}
    	try{
    	this.getSeasonService().updateSeason(season);
    	}catch(Exception ex){}	
    	try {
			this.getSolrServerSeason().addBean(season);			
			this.getSolrServerSeason().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return season;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
    	if(this.getBookingService().countBookingsByIdSeason(id) > 0){
			throw new NotFoundException(I18nUtils.getProperty("seasonDeleteWithBookingError"));
		}
		count = this.getSeasonService().deleteSeason(id);
		this.getRoomPriceListService().deleteRoomPriceListsByIdSeason(id);
		this.getExtraPriceListService().deleteExtraPriceListsByIdSeason(id);
		if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("seasonDeleteErrorAction"));
		}
		try {
			this.getSolrServerSeason().deleteById(id.toString());
			this.getSolrServerSeason().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
    }
	
	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}
	public PeriodService getPeriodService() {
		return periodService;
	}
	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
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
	public SolrServer getSolrServerSeason() {
		return solrServerSeason;
	}
	public void setSolrServerSeason(SolrServer solrServerSeason) {
		this.solrServerSeason = solrServerSeason;
	}
	
}