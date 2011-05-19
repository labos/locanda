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
	private String startDate = null;
	private String endDate = null;
	private Integer idPeriod = null;
	private List<Period> periods = new ArrayList<Period>();
	private Message message = new Message();

	@Actions({ 
		@Action(value = "/findAllSeasons", 
				results = { @Result(name = "success", location = "/seasons.jsp") })
	})
	public String findAllSeasons() {
		User user = null;
		Structure structure = null;
		
		user = (User) session.get("user");
		structure = user.getStructure();
		this.setSeasons(structure.getSeasons());
		return SUCCESS;
	}

	@Actions({ 
		@Action(value = "/findSeasonById", 
				results = { @Result(name = "success", location = "/seasons.jsp") })
	})
	public String findSeasonById() {
		//in working....
		return SUCCESS;
	}

	@Actions({ @Action(value = "/findSeasonByPeriod", results = {
			@Result(type = "json", name = "success", params = { "root", "message" }),
			@Result(type = "json", name = "error", params = { "root", "message" }) })
	})
	public String findSeasonByPeriod() {
		User user = null;
		Structure structure = null;
		
		user = (User) session.get("user");
		structure = user.getStructure();
		//in progress....
		return SUCCESS;
	}
	
	@Actions({ 
		@Action(value = "/goUpdateSeason", 
				results = { @Result(name = "success", location = "/season_edit.jsp") })
	})
	public String goUpdateSeason() {
		User user = null;
		Structure structure = null;
		Season theSeason = null;
		
		user = (User) session.get("user");
		structure = user.getStructure();
		theSeason = structure.findSeasonById(this.getId());
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
		
		user = (User) session.get("user");
		structure = user.getStructure();
		this.setSeasons(structure.getSeasons());
		saveUpdatePeriods(structure);
		oldSeason = structure.findSeasonById(this.getSeason().getId());
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);		//voglio settare l'anno della stagione con l'anno corrente
		if (oldSeason == null) {
			// Si tratta di una nuova season
			this.getSeason().setId(structure.nextKey());
			this.getSeason().setYear(currentYear);
			structure.addSeason(this.getSeason());
			structure.refreshPriceLists();
		} else {
			// Si tratta di un update di un booking esistente
			structure.updateSeason(this.getSeason());
		}
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Season Added successfully");
		return SUCCESS;
	}

	private Boolean saveUpdatePeriods(Structure structure) {
		List <Period> periodsWithoutNulls = new ArrayList<Period>();
		
		for (Period currPeriod : this.periods) {
			if ((currPeriod != null )){
				periodsWithoutNulls.add(currPeriod);
				if  (currPeriod.getId() == null) {
					currPeriod.setId(structure.nextKey());
				}
			}
		}
		this.getSeason().setPeriods(periodsWithoutNulls);
		return true;
	}

	@Actions({ @Action(value = "/deleteSeason", results = {
			@Result(type = "json", name = "success", params = { "root", "message" }),
			@Result(type = "json", name = "error", params = { "root", "message" }) 
			})

	})
	public String deleteSeason() {
		User user = null;
		Structure structure = null;
		Season currentSeason = null; 
		
		user = (User) session.get("user");
		structure = user.getStructure();
		currentSeason = structure.findSeasonById(this.season.getId());
		if(structure.removeSeason(currentSeason)){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Season removed successfully");
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error removing Season");
			return ERROR;
		}
	}
	
	@Actions({ @Action(value = "/deletePeriodFromSeason", results = {
			@Result(type = "json", name = "success", params = { "root", "message" }),
			@Result(type = "json", name = "error", params = { "root", "message" }) 
			})
	})
	public String deletePeriodFromSeason() {
		User user = null;
		Structure structure = null;
		Season currentSeason = null;
		
		user = (User) session.get("user");
		structure = user.getStructure();
		currentSeason = structure.findSeasonById(this.season.getId());
		try {
			if (currentSeason.getPeriods().remove(
					this.getPeriodById(this.idPeriod, currentSeason))) {
				this.getMessage().setResult(Message.SUCCESS);
				this.getMessage().setDescription("Period removed successfully");
				return SUCCESS;
			} else {
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("Error in removing the selected period");
				return ERROR;
			}
		} catch (Exception e) {
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Exception removing period");
			return ERROR;
		}
	}

	private Period getPeriodById(Integer id, Season season) {
		Period aPeriod = null;

		for (Period currPeriod : season.getPeriods()) {
			if (currPeriod.getId() == id) {
				aPeriod = currPeriod;
			}
		}
		return aPeriod;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	
}