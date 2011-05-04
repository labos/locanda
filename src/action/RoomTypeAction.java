package action;

import java.util.Map;
import java.util.Set;

import model.Structure;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
@ParentPackage(value="default")
public class RoomTypeAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Set<String> roomTypes = null;	
	
	@Actions({
		@Action(value="/findAllRoomTypes",results = {
				@Result(type ="json",name="success", params={
						"root","roomTypes"
				} )
		})
		
	})
	public String findAllRoomTypes() {
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRoomTypes(structure.findAllRoomTypeNames());
		return SUCCESS;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public Set<String> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(Set<String> roomTypes) {
		this.roomTypes = roomTypes;
	}
}
