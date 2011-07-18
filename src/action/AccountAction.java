package action;

import java.util.Date;

import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public class AccountAction extends ActionSupport {
	private User user = null;
	
	@Autowired
	private UserService userService = null;
	
	@Actions(
			@Action(value = "/goCreateAccount", 
					results = { @Result(name = "success", location = "/createAccount-input.jsp") }))
	public String goCreateAccount() {

		return SUCCESS;
	}
	
	@Actions(
			@Action(value = "/createAccount", 
					results = { 
					@Result(name = "success", location = "/createAccount-output.jsp"),
					@Result(name = "input", location = "/createAccount-input.jsp")}))
	public String createAccount() {
		if(this.getUserService().findUserByEmail(this.getUser().getEmail()) != null){
			//Scrivere un messaggio di user duplicato!!!!!
			addActionError(getText("warningDuplicateUser"));
			return INPUT;
		}
		this.getUser().setPassword("locanda");
		this.getUser().setCreationDate(new Date());
		this.getUserService().insertUser(this.getUser());
		return SUCCESS;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	

}
