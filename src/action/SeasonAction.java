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
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Period;
import model.listini.Season;
@ParentPackage(value="default")
public class SeasonAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Season> seasons = null;
	private Season season = null;
	private String startDate = null;
	private String endDate = null;
	private Message message = new Message();


	@Actions({
		@Action(value="/goAddNewSeason",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		}	)		
				})
				
	public String goAddNewSeason(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		this.setSeasons(structure.getSeasons());
		
		if (!this.saveUpdatePeriodDates())
		{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Problema ai dati della season!");
			return ERROR;
			
		}
		
		Season oldSeason = 
			structure.findSeasonById(this.getSeason().getId());
		if(oldSeason==null){
			//Si tratta di una nuova season
			this.getSeason().setId(structure.nextKey());
			structure.addSeason(this.getSeason());
		}else{
			//Si tratta di un update di un booking esistente
			structure.updateSeason(this.getSeason());
			
		}

		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Season Added successfully");
		return SUCCESS;

	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Actions({
		@Action(value="/findAllSeasons",results = {
				@Result(name="success",location="/seasons.jsp")
		})
		
	})
	public String findAllSeasons(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		this.setSeasons(structure.getSeasons());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findSeasonById",results = {
				@Result(name="success",location="/seasons.jsp")
		})
		
	})
	public String findSeasonById(){
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findSeasonByPeriod",results = {
				@Result(type="json", name="success"),
				@Result(type ="json",name="error", params={
						"root","message"
				})	
		})
		
	})
	public String findSeasonByPeriod(){
		
		return SUCCESS;
	}
	
	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	
	private Boolean saveUpdatePeriodDates(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date dateOut = null;
		Long millis = null;
		Period aPeriod = new Period();
		List <Period> aPeriods = new ArrayList<Period>();
		
		try {
			aPeriod.setStartDate(sdf.parse(this.getStartDate()));
			aPeriod.setEndDate(sdf.parse(this.getEndDate()));
			aPeriods.add(aPeriod);
			this.getSeason().setPeriods(aPeriods);			
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
			
		
		return true;
		
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
	
	
	

}