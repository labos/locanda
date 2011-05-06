package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Guest;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Convention;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ConventionAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Convention> conventions = null;
	private Convention convention = null;
	@Actions({
		@Action(value="/findAllConventions",results = {
				@Result(name="success",location="/conventions.jsp")
		}) 
		
	})
	public String findAllConventions(){
		User user = null;
		Structure structure = null;
		user = (User)session.get("user");
		structure = user.getStructure();
		

		
		
		
		return SUCCESS;		
	}
	
	
	
	@Actions({
		@Action(value="/saveUpdateConvention",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} )
		})
		
	})
	public String saveUpdateConvention(){
/*		User user = null;
		Structure structure = null;
		Guest oldGuest = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		
		oldGuest = structure.findGuestById(this.getGuest().getId());
		if(oldGuest == null){
			//Si tratta di una aggiunta
			this.getGuest().setId(structure.nextKey());
			structure.addGuest(this.getGuest());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Guest added successfully");
			
		}else{
			//Si tratta di un update
			structure.updateGuest(this.getGuest());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Guest updated successfully");
		}*/
		return SUCCESS;		
	}
	
	

	public Map<String, Object> getSession() {
		return session;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}



	public List<Convention> getConventions() {
		return conventions;
	}



	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}



	public Convention getConvention() {
		return convention;
	}



	public void setConvention(Convention convention) {
		this.convention = convention;
	}
	
	
	

}
