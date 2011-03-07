package action;

import java.util.Map;

import model.Room;
import model.Structure;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class LoginAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private String email = null;
	private String password;
	
	@Actions(value={
			@Action(value="/login", results={
					@Result(name="input",location="/WEB-INF/jsp/login.jsp"),
					@Result(name="loginSuccess", location="/homeLogged.jsp"),	
					@Result(name="loginError", location="/login.jsp")
			})
			
	})	
	
	public String execute(){
		String ret = null;	
		User user = null;
		Structure structure = null;
		
		if(this.getEmail().trim().equals("locanda@locanda.it") &&
				this.getPassword().trim().equals("locanda")){
			user = new User();
			
			user.setEmail(this.getEmail());
			structure = this.buildStructure();
			user.setStructure(structure);
			this.getSession().put("user", user);	
			ret = "loginSuccess";
		}else{
			this.getSession().put("user", null);
			ret = "loginError";
		}		
		return ret;
	}
	
	private Structure buildStructure(){
		Structure ret = null;
		Room aRoom = null;		
		
		ret = new Structure();
		ret.setName("polaris");
		ret.setEmail("polaris@locanda.it");
		
		aRoom = new Room();
		aRoom.setName("101");
		aRoom.setRoomType("singola");
		aRoom.setPrice(80.0);
		aRoom.setMaxGuests(1);
		ret.getRooms().add(aRoom);
		
		aRoom = new Room();
		aRoom.setName("201");
		aRoom.setRoomType("doppia");
		aRoom.setPrice(120.0);
		aRoom.setMaxGuests(2);
		ret.getRooms().add(aRoom);
		
		
		return ret;		
	}
	
		
	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
