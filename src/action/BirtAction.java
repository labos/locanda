package action;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import model.UserAware;

import org.apache.struts2.util.ServletContextAware;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware; 
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;



import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionSupport;

import birt.RunBirt;

@ParentPackage(value = "default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
public class BirtAction extends ActionSupport implements ServletContextAware, ServletRequestAware, SessionAware, UserAware {
	
	private Map<String, Object> session = null;
	private Integer idStructure;
	private ByteArrayInputStream inputStream;
	private ServletContext context;
	private HttpServletRequest request;
	private String fileName = null;
	private String rp = null;
	
	


	public String execute() throws Exception {
		String prefixNameFile = "";
				if( this.getRp().equals("bookinginvoice") ){
			prefixNameFile = getText("invoice") + "_";
		}
		

		RunBirt rb = new RunBirt();
		rb.setLocale(this.getLocale());
		rb.setIdStructure(this.getIdStructure());
		this.setInputStream(new ByteArrayInputStream(rb.runReport(this.getContext(), this.getRequest())));
		this.setFileName( prefixNameFile + UUID.randomUUID().toString().substring(0, 7) + ".pdf");
		return SUCCESS;
	}
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
	
	
	public ServletContext getContext() {
		return context;
	}


	public void setContext(ServletContext context) {
		this.context = context;
	}


	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}


	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}   
  
	public void setServletContext(ServletContext context) {  
		this.context = context;  
	}  
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	
	public Integer getIdStructure() {
		return idStructure;
	}
	
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRp() {
		return rp;
	}

	public void setRp(String rp) {
		this.rp = rp;
	}

	
}