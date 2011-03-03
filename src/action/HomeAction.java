package action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class HomeAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	
	@Actions(value={
			@Action(value="/home", results={
					@Result(name="logged",location="/homeLogged.jsp"),
					@Result(name="notLogged", location="/homeNotLogged.jsp")				
			})
			
	})
	public String execute(){
		String ret = "notLogged";
		
		if(this.getSession().get("user")!=null){
			ret = "logged";
		}
		return ret;

	}
	
		
	public Map<String, Object> getSession() {
		return session;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

}
