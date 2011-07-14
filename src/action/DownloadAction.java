package action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class DownloadAction extends ActionSupport {
	private String name;
	private String surname;
	private String email;
	private String phone;

	@Actions(value = { @Action(value = "/goDownload", results = { @Result(name = "success", location = "/download.jsp") }) })
	public String goDownload() {
				

		return SUCCESS;
	}

	@Actions(value = { @Action(value = "/download", results = { @Result(name = "success", location = "/download.jsp") }) })
	public String download() {

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

}
