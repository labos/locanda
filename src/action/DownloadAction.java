package action;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class DownloadAction extends ActionSupport{

	private InputStream fileInputStream;
	private String fileName = null;
	
	@Actions(
			@Action(value = "/goDownload", results = {
					@Result(name = "success", location = "/download.jsp") 
					}) 
				)
		public String goDownload() {

			return SUCCESS;
		}


	
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

	
}