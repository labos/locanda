package action;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;

import model.Image;
import model.Room;
import model.RoomFacility;
import model.RoomType;
import model.Structure;
import model.StructureFacility;
import model.User;
import model.internal.Message;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
@ParentPackage(value="default")
public class UploadAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private String name;
	private Message message = new Message();
	private RoomFacility roomFacility = null;
	private Room room = null;
	private RoomType roomType = null;
	private StructureFacility structureFacility = null;
	private Image image = null;
	
	
	@Actions({
		@Action(value="/uploadFacilityIF",results = {
				@Result(name="success",location="/message_upload.jsp")
		}),
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
		User user = null; 
		ServletContext context = null; 
		String imgPath = null; 
		//Controllare che sia diverso da null in un interceptor
		Structure structure = null; 
		
		user = (User)this.getSession().get("user");
		context =  ServletActionContext.getServletContext();
		imgPath = context.getRealPath("/")+ "images/room_facilities/";
		structure = user.getStructure();
		
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
		//this.roomFacility.setId(structure.nextKey());
		structure.addRoomFacility(roomFacility);
		
		message.setResult(Message.SUCCESS);
		message.setDescription("Facility inserita correttamente!");
		return SUCCESS;
		
	
	}
	
	
	@Actions({
		@Action(value="/uploadStructureImageIF",results = {
				@Result(name="success",location="/message_upload.jsp")
		}),
		@Action(value="/uploadStructureImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "session,upload,uploadFileName,uploadContentType,name"
						} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})

	public String uploadStructureImage() throws IOException {
		User user = (User)this.getSession().get("user");
		ServletContext context = ServletActionContext.getServletContext();
		String imgPath = context.getRealPath("/")+ "images/structure/";
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		if (structure.hasRoomFacilityNamed(this.getName())) {
			message.setResult(Message.ERROR);
			message.setDescription("Esiste già una foto con lo stesso nome");
			return ERROR;
		};
		
		File target = new File(imgPath + this.getUploadFileName());
		FileUtils.copyFile(this.upload, target);
		
		
		this.image = new Image();
		this.image.setName(this.name);
		this.image.setFileName(this.uploadFileName);
		structure.addStructureImage(this.image);
		
		message.setResult(Message.SUCCESS);
		message.setDescription("Foto inserita correttamente!");
		return SUCCESS;
		
	
	}
	
	@Actions({
		@Action(value="/uploadStructureFacilityIF",results = {
				@Result(name="success",location="/message_upload.jsp")
		}),
		@Action(value="/uploadStructureFacility",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "session,upload,uploadFileName,uploadContentType,name,roomFacility,image"
						} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})

	public String uploadStructureFacility() throws IOException {
		User user = (User)this.getSession().get("user");
		ServletContext context = ServletActionContext.getServletContext();
		String imgPath = context.getRealPath("/")+ "images/struct_facilities/";
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		if (structure.hasRoomFacilityNamed(this.getName())) {
			message.setResult(Message.ERROR);
			message.setDescription("Esiste già una facility con lo stesso nome");
			return ERROR;
		};
		
		File target = new File(imgPath + this.getUploadFileName());
		FileUtils.copyFile(this.upload, target);
		
		
		this.structureFacility = new StructureFacility();
		this.structureFacility.setName(this.name);
		this.structureFacility.setFileName(this.uploadFileName);
		structure.addStructureFacility(this.structureFacility);
		message.setResult(Message.SUCCESS);
		message.setDescription("Facility inserita correttamente!");
		return SUCCESS;
		
	
	}
	
	
	
	@Actions({
		@Action(value="/uploadRoomImageIF",results = {
				@Result(name="success",location="/message_upload.jsp")
		}),
		@Action(value="/uploadRoomImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "session,upload,uploadFileName,uploadContentType,name"
						} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})

	public String uploadRoomImage() throws IOException {
		Room aRoom = null;
		User user = (User)this.getSession().get("user");
		ServletContext context = ServletActionContext.getServletContext();
		String imgPath = context.getRealPath("/")+ "images/room_images/";
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		if (structure.hasRoomPhotoNamed(this.getName())) {
			message.setResult(Message.ERROR);
			message.setDescription("Esiste già una foto con lo stesso nome");
			return ERROR;
		};
		
		File target = new File(imgPath + this.getUploadFileName());
		FileUtils.copyFile(this.upload, target);
		
		
		this.image = new Image();
		this.image.setId(structure.nextKey());
		this.image.setName(this.name);
		this.image.setFileName(this.uploadFileName);
		aRoom = structure.findRoomById(this.getRoom().getId());
		if (aRoom == null){
			
			message.setResult(Message.ERROR);
			message.setDescription("Non esiste la Room per l'aggiunta della foto");
			return ERROR;
		}
		
		try {
			
			aRoom.addImage(this.getImage());
			structure.updateRoom(aRoom);
			
		}
		catch(Exception e){
			
			message.setResult(Message.ERROR);
			message.setDescription("Problema nell'aggiunta della foto");
			return ERROR;
			
		}

		
		
		message.setResult(Message.SUCCESS);
		message.setDescription("Foto inserita correttamente!");
		return SUCCESS;
		
	
	}
	
	
	
	@Actions({
		@Action(value="/uploadRoomTypeImageIF",results = {
				@Result(name="success",location="/message_upload.jsp")
		}),
		@Action(value="/uploadRoomTypeImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "session,upload,uploadFileName,uploadContentType,name"
						} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})

	public String uploadRoomTypeImage() throws IOException {
		RoomType aRoomType = null;
		User user = (User)this.getSession().get("user");
		ServletContext context = ServletActionContext.getServletContext();
		String imgPath = context.getRealPath("/")+ "images/roomtype/";
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		if (structure.hasRoomPhotoNamed(this.getName())) {
			message.setResult(Message.ERROR);
			message.setDescription("Esiste già una foto con lo stesso nome");
			return ERROR;
		};
		
		File target = new File(imgPath + this.getUploadFileName());
		FileUtils.copyFile(this.upload, target);
		
		
		this.image = new Image();
		this.image.setId(structure.nextKey());
		this.image.setName(this.name);
		this.image.setFileName(this.uploadFileName);
		aRoomType = structure.findRoomTypeById(this.getRoomType().getId());
		if (aRoomType == null){			
			message.setResult(Message.ERROR);
			message.setDescription("Non esiste la RoomType per l'aggiunta della foto");
			return ERROR;
		}
		
		try {
			
			aRoomType.addRoomTypeImage(this.getImage());
			structure.updateRoomType(aRoomType);
			
			
		}
		catch(Exception e){
			
			message.setResult(Message.ERROR);
			message.setDescription("Problema nell'aggiunta della foto");
			return ERROR;
			
		}

		
		
		message.setResult(Message.SUCCESS);
		message.setDescription("Foto inserita correttamente!");
		return SUCCESS;
		
	
	}
	
	
	
	
	@Actions({
		@Action(value="/uploadRoomTypeFacilityIF",results = {
				@Result(name="success",location="/message_upload.jsp")
		}),
		@Action(value="/uploadRoomTypeFacility",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "session,upload,uploadFileName,uploadContentType,name"
						} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})

	public String uploadRoomTypeFacility() throws IOException {
		RoomType aRoomType = null;
		User user = (User)this.getSession().get("user");
		ServletContext context = ServletActionContext.getServletContext();
		String imgPath = context.getRealPath("/")+ "images/roomtype_facilities/";
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		if (structure.hasRoomTypeFacilityNamed(this.getName())) {
			message.setResult(Message.ERROR);
			message.setDescription("Esiste già una facility con lo stesso nome");
			return ERROR;
		}
		
		File target = new File(imgPath + this.getUploadFileName());
		FileUtils.copyFile(this.upload, target);		
		
		this.roomFacility = new RoomFacility();
		this.roomFacility.setName(this.name);
		this.roomFacility.setFileName(this.uploadFileName);
		//this.roomFacility.setId(structure.nextKey());
		
		structure.addRoomTypeFacility(this.roomFacility);
		message.setResult(Message.SUCCESS);
		message.setDescription("Logo inserito correttamente!");
		return SUCCESS;
		
	
	}
	
	public Map<String, Object> getSession() {
		return session;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
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



	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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



	public Message getMessage() {
		return message;
	}



	public void setMessage(Message message) {
		this.message = message;
	}



	public RoomFacility getRoomFacility() {
		return roomFacility;
	}



	public void setRoomFacility(RoomFacility roomFacility) {
		this.roomFacility = roomFacility;
	}


	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}


	public StructureFacility getStructureFacility() {
		return structureFacility;
	}


	public void setStructureFacility(StructureFacility structureFacility) {
		this.structureFacility = structureFacility;
	}


	public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}


	public RoomType getRoomType() {
		return roomType;
	}


	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	
	
	

}
