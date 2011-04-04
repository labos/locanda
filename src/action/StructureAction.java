package action;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.*;

import model.Extra;
import model.Room;
import model.RoomFacility;
import model.Structure;
import model.User;
import model.internal.Message;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;


import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class StructureAction extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session = null;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private String name;
	private Message message = new Message();
	private RoomFacility roomFacility = null;
	private List<Extra> extras = null;
	private Extra extra = null;
	private Set<String> roomTypes = null;
	private List<Room> rooms = null;
	

	@Actions({
		@Action(value="/findAllRooms",results = {
				@Result(name="success",location="/accomodation.jsp")
		}),
		@Action(value="/findAllRoomsJson",results = {
				@Result(type ="json",name="success", params={
						"root","rooms"
				})}) 
		
	})
	public String findAllRooms() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRooms(structure.getRooms());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findAllRoomTypes",results = {
				@Result(type ="json",name="success", params={
						"root","roomTypes"
				} )
		})
		
	})
	public String findAllRoomTypes() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setRoomTypes(structure.findAllRoomTypes());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/uploadFacility",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "session,upload,uploadFileName,uploadContentType,name"
						} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})

	public String uploadFacility() throws IOException {
		User user = (User)this.getSession().get("user");
		ServletContext context = ServletActionContext.getServletContext();
		String imgPath = context.getRealPath("/")+ "images/room_facilities/";
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		if (structure.hasRoomFacilityNamed(this.getName())) {
			message.setResult(Message.ERROR);
			message.setDescription("Esiste già una facility con lo stesso nome");
			return ERROR;
		};
		
		File target = new File(imgPath + this.getUploadFileName());
		FileUtils.copyFile(this.upload, target);
		
		
		
		this.roomFacility = new RoomFacility();
		this.roomFacility.setName(this.name);
		this.roomFacility.setFileName(this.uploadFileName);
		this.roomFacility.setId(structure.nextKey());
		structure.addRoomFacility(roomFacility);
		message.setResult(Message.SUCCESS);
		message.setDescription("Facility inserita correttamente!");
		return SUCCESS;
		
	
	}

	@Actions({
		@Action(value="/findAllExtras",results = {
				@Result(name="success",location="/extras.jsp")
		}) 
		
	})
	public String findAllExtras() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setExtras(structure.getExtras());
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/addNewExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String addNewExtra() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.getExtra().setId(structure.nextKey());
		this.getExtra().setName(this.getExtra().getName());
		this.getExtra().setPrice(this.getExtra().getPrice());
		structure.addExtra(this.getExtra());
		this.getExtra().setResourcePriceType("per Room");
		this.getExtra().setTimePriceType("per Night");
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Extra Added successfully");
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/updateExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} )
		})
		
	})
	public String updateExtra(){
		User user = (User)session.get("user");
		Structure structure = user.getStructure();
		structure.updateExtra(this.getExtra());
		//Aggiungere update error
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Extra modified successfully");
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/deleteExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String deleteExtra() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Extra currentExtra = structure.findExtraById(this.getExtra().getId());
		if(structure.deleteExtra(currentExtra)){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("L'extra e' stato cancellato con successo");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non e' stato possibile cancellare l'extra");
			return "error";
		}		
	}	
	
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	


	public String getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String fileName) {
		this.uploadFileName = fileName;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomFacility getRoomFacility() {
		return roomFacility;
	}

	public void setRoomFacility(RoomFacility roomFacility) {
		this.roomFacility = roomFacility;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	public Set<String> getRoomTypes() {
		return roomTypes;
	}

	public void setRoomTypes(Set<String> roomTypes) {
		this.roomTypes = roomTypes;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	
	
	
	
	

}
