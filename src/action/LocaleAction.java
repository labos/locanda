package action;

import com.opensymphony.xwork2.ActionSupport;


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