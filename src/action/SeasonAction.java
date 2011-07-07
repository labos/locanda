package action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.SeasonService;
import service.StructureService;
import com.opensymphony.xwork2.ActionSupport;

import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Period;
import model.listini.Season;

@ParentPackage(value = "default")
public class SeasonAction extends ActionSupport implements SessionAware {
	private Integer id;
	private Map<String, Object> session = null;
	private List<Season> seasons = null;
	private Season season = null;
	private Integer idPeriod = null;
	private List<Period> periods = new ArrayList<Period>();
	private Message message = new Message();
	@Autowired
	private StructureService structureService = null;	
	@Autowired
	private SeasonService seasonService = null;
	
	@Actions({ 
		@Action(value = "/findAllSeasons", 
				results = { @Result(name = "success", location = "/seasons.jsp") })
	})
	public String findAllSeasons() {
		User user = null;
		Structure structure = null;
		
		user = (User) session.get("user");
		structure = user.getStructure();		
		
		this.setSeasons(this.getSeasonService().findSeasonsByIdStructure(structure.getId()));
		//Rimuovere questa istruzione quando tutto sarà sul DB
		structure.setSeasons(this.getSeasons());
		
		return SUCCESS;
	}

	@Actions({ 
		@Action(value = "/goUpdateSeason", 
				results = { @Result(name = "success", location = "/season_edit.jsp") })
	})
	public String goUpdateSeason() {
		Season theSeason = null;
		//User user = null;
		//user = (User) session.get("user");
		
		theSeason = this.getSeasonService().findSeasonById(this.getId());
		this.setSeason(theSeason);
		return SUCCESS;
	}
	
	@Actions({ @Action(value = "/saveUpdateSeason", results = {
			@Result(type = "json", name = "success", params = { "root",
					"message" 
					}),
			@Result(name = "input", location = "/validationError.jsp"),
			@Result(type = "json", name = "error", params = { "root", "message" }) 
			}) 
	})
	public String saveUpdateSeason() {
		User user = null;
		Structure structure = null;
		Season oldSeason = null;
		List <Period> periodsWithoutNulls = null; 
		
		user = (User) session.get("user");
		structure = user.getStructure();
		periodsWithoutNulls = new ArrayList<Period>();

		for (Period currPeriod : this.getPeriods()) {
			if ((currPeriod != null )){
				if (! currPeriod.checkDates()){
					this.getMessage().setResult(Message.ERROR);
					this.getMessage().setDescription(getText("dateOutMoreDateInAction"));
					return ERROR;
				}
				periodsWithoutNulls.add(currPeriod);				
			}
		}
		this.getSeason().setPeriods(periodsWithoutNulls);
		
		if (!this.getStructureService().hasPeriodFreeForSeason(structure, this.getSeason())) {
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("periodOverlappedAction"));
			return ERROR;
		}	
		this.getSeason().setId_structure(structure.getId());
		
		int currentYear = (this.getSeason().getYear() == null )?Calendar.getInstance().get(Calendar.YEAR): this.getSeason().getYear();		
		this.getSeason().setYear(currentYear);
		
		if (!this.getSeasonService().checkYears(this.getSeason())) {
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("periodYearError"));
			return ERROR;
		}
		oldSeason = this.getSeasonService().findSeasonById(this.getSeason().getId());
		if (oldSeason == null) {
			// Si tratta di una nuova season
			//Voglio settare l'anno della stagione con l'anno corrente	
			this.getSeasonService().insertSeason(this.getSeason());		
			//this.getStructureService().refreshPriceLists(structure);	
			this.getStructureService().addPriceListsForSeason(structure, this.getSeason().getId());
			this.getMessage().setDescription(getText("seasonAddSuccessAction"));		
		} else {
			// Si tratta di un update di una season esistente
			this.getSeasonService().updateSeason(this.getSeason());			
			this.getMessage().setDescription(getText("seasonUpdateSuccessAction"));			
		}
		this.getMessage().setResult(Message.SUCCESS);
		return SUCCESS;
	}
	
	@Actions({ @Action(value = "/deleteSeason", results = {
			@Result(type = "json", name = "success", params = { "root", "message" }),
			@Result(type = "json", name = "error", params = { "root", "message" }) 
			})
	})
	public String deleteSeason() {
		try{
			this.getSeasonService().deleteSeason(this.getSeason().getId());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("seasonDeleteSuccessAction"));
			return SUCCESS;
		}catch (Exception e) {
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("seasonDeleteErrorAction"));
			return ERROR;
		}
	}
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public Integer getIdPeriod() {
		return idPeriod;
	}
	public void setIdPeriod(Integer idPeriod) {
		this.idPeriod = idPeriod;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public List<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}
	public List<Period> getPeriods() {
		return periods;
	}
	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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