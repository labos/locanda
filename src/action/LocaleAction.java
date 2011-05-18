package action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class LocaleAction extends ActionSupport{

	@Actions(value={
			@Action(value="/locale", results={
					@Result(name="SUCCESS",location="/login.jsp"),
			})	
	})	
	public String execute() {
	
		return "SUCCESS";

	}

}