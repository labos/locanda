package action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
//Da usare come template per tutte le action
public class TemplateAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	
	

	public Map<String, Object> getSession() {
		return session;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	
	
	

}
