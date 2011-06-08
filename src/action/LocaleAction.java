package action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.ServletUrlRenderer;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
//@Result(name="success", type="redirect", location="accomodation")
public class LocaleAction extends ActionSupport{
	private String redirect;
	private String sect;

	public String execute() {
	
		return "SUCCESS";

	}
	public String getRedirect() {
			
		return this.redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public String getSect() {
		return sect;
	}
	public void setSect(String sect) {
		this.sect = sect;
	}
	
	

}