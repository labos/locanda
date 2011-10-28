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
	private Integer disclaimer = null;
	
	@Autowired
	private UserService userService = null;
	
	@Actions(
		@Action(value = "/goCreateAccount", results = { 
				@Result(name = "success", location = "/WEB-INF/jsp/createAccount-input.jsp")}
		)
	)
	public String goCreateAccount() {

		return SUCCESS;
	}
	
	@Actions(
			@Action(value = "/createAccount", results = { 
					@Result(name = "success", location = "/WEB-INF/jsp/createAccount-output.jsp"),
					@Result(name = "input", location = "/WEB-INF/jsp/createAccount-input.jsp")
			})
	)
	public String createAccount() {
		if(this.getUserService().findUserByEmail(this.getUser().getEmail()) != null){
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
	public Integer getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(Integer disclaimer) {
		this.disclaimer = disclaimer;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}