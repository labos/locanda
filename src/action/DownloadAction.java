package action;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class DownloadAction extends ActionSupport {
	private InputStream inputStream;
	private String name;
	private String surname;
	private String email;
	private String phone;

	@Actions(
		@Action(value = "/goDownload", results = {
				@Result(name = "success", location = "/download.jsp") 
				}) 
			)
	public String goDownload() {

		return SUCCESS;
	}

	@Actions(
	@Action(value = "/download", results = {
				@Result(name = "success", type = "stream", params = {
					"inputName","inputStream", "contentType", "application/octet-stream"}) 
					})
		)
	public String download() {
		String fileName = "/locanda.zip";
		
		 try {
			this.setInputStream(new FileInputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	
}
