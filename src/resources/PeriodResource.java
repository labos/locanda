package resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.listini.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.PeriodService;
import service.SeasonService;
import service.StructureService;
import utils.I18nUtils;

@Path("/periods/")
@Component
@Scope("prototype")
public class PeriodResource {
	@Autowired
	private PeriodService periodService = null;
	@Autowired
    private StructureService structureService = null;
	@Autowired
    private SeasonService seasonService = null;
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Period getPeriod(@PathParam("id") Integer id){
		Period ret = null;
		
		ret = this.getPeriodService().findPeriodById(id);
		return ret;
	}
	
	@GET
	@Path("season/{idSeason}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Period> getPeriodBySeason(@PathParam("idSeason") Integer idSeason){
		List<Period> ret = null;
		
		ret = this.getPeriodService().findPeriodsByIdSeason(idSeason);
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Period save(Period period) {	
		Boolean validDates = period.checkDates();
		Boolean validYear = this.getPeriodService().checkYears(period);
		Boolean overlap = this.getPeriodService().checkOverlappingPeriods(period);
		
		if (!validDates) {
			throw new NotFoundException(I18nUtils.getProperty("periodDatesError"));
		}
		if (!validYear) {
			throw new NotFoundException(I18nUtils.getProperty("periodYearError"));
		}
		if (overlap) {
			throw new NotFoundException(I18nUtils.getProperty("periodOverlappedAction"));
		}
		this.getPeriodService().insertPeriod(period);
        return period;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Period update(Period period) {
    	Boolean validDates = period.checkDates();
		Boolean validYear = this.getPeriodService().checkYears(period);
		Boolean overlap = this.getPeriodService().checkOverlappingPeriods(period);
		
		if (!validDates) {
			throw new NotFoundException(I18nUtils.getProperty("periodDatesError"));
		}
		if (!validYear) {
			throw new NotFoundException(I18nUtils.getProperty("periodYearError"));
		}
		if (overlap) {
			throw new NotFoundException(I18nUtils.getProperty("periodOverlappedAction"));
		}
		this.getPeriodService().updatePeriod(period);
        return period;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		count = this.getPeriodService().deletePeriod(id);
		if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("periodDeleteError"));
		}
		return count;
    }
	
	public PeriodService getPeriodService() {
		return periodService;
	}
	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}
	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	
}