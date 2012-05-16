/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package action;

import java.io.ByteArrayInputStream;
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
		if( this.getRp().equals("bookinginvoice") ) {
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
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public Map<String, Object> getSession() {
		return session;
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