package action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import model.Booking;
import model.Guest;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Period;
import model.listini.Season;

@ParentPackage(value = "default")
public class SeasonAction extends ActionSupport implements SessionAware {
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
		User user = (User) session.get("user");
		Structure structure = user.getStructure();
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
			@Result(type = "json", name = "success"),
			@Result(type = "json", name = "error", params = { "root", "message" }) })

	})
	public String findSeasonByPeriod() {
		User user = (User) session.get("user");
		Structure structure = user.getStructure();
		//in working....
		return SUCCESS;
	}

	@Actions({ @Action(value = "/saveUpdateSeason", results = {
			@Result(type = "json", name = "success", params = { "root",
					"message" }),
			@Result(name = "input", location = "/validationError.jsp"),
			@Result(type = "json", name = "error", params = { "root", "message" }) }) })
	public String saveUpdateSeason() {
		User user = (User) session.get("user");
		Structure structure = user.getStructure();
		this.setSeasons(structure.getSeasons());
		saveUpdatePeriods(structure);
		if (!this.saveUpdatePeriodDates()) {
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Problema ai dati della season!");
			return ERROR;

		}

		Season oldSeason = structure.findSeasonById(this.getSeason().getId());
		if (oldSeason == null) {
			// Si tratta di una nuova season
			this.getSeason().setId(structure.nextKey());
			structure.addSeason(this.getSeason());
		} else {
			// Si tratta di un update di un booking esistente
			structure.updateSeason(this.getSeason());

		}

		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Season Added successfully");
		return SUCCESS;

	}

	private Boolean saveUpdatePeriods(Structure structure) {
		int index = 0;
		for (Period currPeriod : this.periods) {
			if (currPeriod.getId() == null) {

				this.periods.get(index).setId(structure.nextKey());
			}

			index++;
		}
		return true;

	}

	@Actions({ @Action(value = "/deletePeriodFromSeason", results = {
			@Result(type = "json", name = "success"),
			@Result(type = "json", name = "error", params = { "root", "message" }) })

	})
	public String deletePeriodFromSeason() {

		User user = (User) session.get("user");
		Structure structure = user.getStructure();
		Season currentSeason = structure.findSeasonById(this.season.getId());
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

	private Boolean saveUpdatePeriodDates() {

		this.getSeason().setPeriods(this.periods);
		return true;

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

	
	public Integer getIdPeriod() {
		return idPeriod;
	}

	public void setIdPeriod(Integer idPeriod) {
		this.idPeriod = idPeriod;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;

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

}