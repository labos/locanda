package resources;

import java.util.ArrayList;
import java.util.List;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.PeriodService;
import service.SeasonService;
import service.StructureService;

@Path("/seasons/")
@Component
@Scope("prototype")
public class SeasonResource {
	
	@Autowired
	private SeasonService seasonService = null;
	@Autowired
	private PeriodService periodService = null;
	@Autowired
    private StructureService structureService = null;
	
	
	@GET
    @Path("structure/{idStructure}/search/{offset}/{rownum}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Season> simpleSearch(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum, @QueryParam("term") String term){
        List<Season> filteredSeasons = null;
        List<Period> periods = null;
       
		filteredSeasons = new ArrayList<Season>();
        for(Season each: this.getSeasonService().search(idStructure, offset, rownum, term)){           
        	periods = this.getPeriodService().findPeriodsByIdSeason(each.getId());
			each.setPeriods(periods);
			filteredSeasons.add(each);            		   
        }       
        return filteredSeasons;          
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
        return season;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Season update(Season season) {        
        
    	this.getSeasonService().updateSeason(season);
        return season;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		count = this.getSeasonService().deleteSeason(id);
		if(count == 0){
			throw new NotFoundException("Error: the season has NOT been deleted");
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
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	
}