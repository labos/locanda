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


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;

import model.UserAware;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/homeNotLogged.jsp")
public class DownloadAction extends ActionSupport implements SessionAware,UserAware{
	private Integer idStructure;
	private Map<String, Object> session;
	private InputStream fileInputStream;
	private String fileName = null;
	
		
	public String execute() throws Exception {
		ServletContext context = null; 
		String filePath = null;
		
		context =  ServletActionContext.getServletContext();
		this.setFileName("locanda.zip");
		filePath = context.getRealPath("/") + "WEB-INF/download/" + this.getFileName();			
		fileInputStream = new FileInputStream(new File(filePath ));

	    return SUCCESS;
	  
	}

	public InputStream getFileInputStream() {
		 
		return fileInputStream;
	   
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getIdStructure() {
		return idStructure;
	}

	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	
	
	
	

	
}