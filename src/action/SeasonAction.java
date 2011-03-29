package action;

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
import model.listini.Season;
@ParentPackage(value="default")
public class SeasonAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Season> seasons = null;
	private String startDate = null;
	private String endDate = null;
	


	@Actions({
		@Action(value="/goAddNewSeason",results = {
				@Result(name="success",location="/seasons.jsp")
				})			
				})
				
	public String goAddNewSeason(){
		
		return SUCCESS;
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