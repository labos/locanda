package action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.RoomType;
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
	private List<RoomType> roomTypes;	
	
	@Actions({
		@Action(value="/findAllRoomTypes",results = {
				@Result(name="success",location="/roomTypes.jsp")
		})
	})
	public String findAllRoomTypes() {
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRoomTypes(structure.getRoomTypes());
		return SUCCESS;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}
	
}
