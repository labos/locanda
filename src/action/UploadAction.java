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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import model.Facility;
import model.Image;
import model.Room;
import model.RoomType;
import model.Structure;
import model.UserAware;
import model.internal.Message;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.FacilityImageService;
import service.FacilityService;
import service.ImageService;
import service.RoomImageService;
import service.RoomService;
import service.RoomTypeImageService;
import service.RoomTypeService;
import service.StructureImageService;
import service.StructureService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/homeNotLogged.jsp")
public class UploadAction extends ActionSupport {	
	private File upload;
	private String uploadFileName;
	private Message message = new Message();	
	private String caption;
	private Integer id;
	
	
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private RoomTypeImageService roomTypeImageService = null;
	@Autowired
	private StructureImageService structureImageService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private RoomImageService roomImageService = null;
	@Autowired
	private FacilityImageService facilityImageService = null;
	@Autowired
	private FacilityService facilityService = null;
	
	
	@Actions({
		@Action(value="/uploadStructureImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
						}),
				@Result(type ="json",name="error", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
				})
		})
	})
	
	public String uploadStructureImage() throws IOException {
		Image image = null;
		Structure structure = null;
		model.File file = null;
		
		structure = this.getStructureService().findStructureById(this.getId());
		if (structure == null){			
			message.setResult(Message.ERROR);
			message.setDescription(getText("structureImageNoStructureError"));
			return ERROR;
		}
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());	
		file = new model.File();
		file.setName(this.getUploadFileName());
		file.setData(data);	
		image.setFile(file);
		this.getImageService().insert(image);
		this.getStructureImageService().insert(this.getId(), image.getId());
			
		message.setResult(Message.SUCCESS);
		message.setDescription(getText("structureImageAddSuccessAction"));
		return SUCCESS;
	}
	
	
	
	@Actions({
		@Action(value="/uploadRoomTypeImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
						}),
				@Result(type ="json",name="error", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
				})
		})
	})
	
	public String uploadRoomTypeImage() throws IOException {
		RoomType aRoomType = null;
		Image image = null;
		model.File file = null;
		
		aRoomType = this.getRoomTypeService().findRoomTypeById(this.getId());
		if (aRoomType == null){			
			message.setResult(Message.ERROR);
			message.setDescription(getText("roomTypeImageNoRoomTypeError"));
			return ERROR;
		}
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());	
		file = new model.File();
		file.setName(this.getUploadFileName());
		file.setData(data);	
		image.setFile(file);
		
		this.getImageService().insert(image);
		this.getRoomTypeImageService().insert(this.getId(), image.getId());
			
		message.setResult(Message.SUCCESS);
		message.setDescription(getText("roomTypeImageAddSuccessAction"));
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/uploadRoomImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
						}),
				@Result(type ="json",name="error", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
				})
		})
	})
	
	public String uploadRoomImage() throws IOException {
		Room room = null;
		Image image = null;
		model.File file = null;
		
		room = this.getRoomService().findRoomById(this.getId());
		if (room == null){			
			message.setResult(Message.ERROR);
			message.setDescription(getText("roomImageNoRoomError"));
			return ERROR;
		}
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());	
		file = new model.File();
		file.setName(this.getUploadFileName());
		file.setData(data);	
		image.setFile(file);
		
		this.getImageService().insert(image);
		this.getRoomImageService().insert(this.getId(),image.getId());
			
		message.setResult(Message.SUCCESS);
		message.setDescription(getText("roomImageAddSuccessAction"));
		return SUCCESS;
	}
	
	
	@Actions({		
		@Action(value="/uploadFacilityImage",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
						}),
				@Result(type ="json",name="error", params={
						"excludeProperties", "upload,uploadFileName,caption,id,structureService,roomTypeService,roomService,imageService,facilityService"
				})
		})
	})
	public String uploadFacilityImage() throws IOException {
		Image image = null;
		model.File file = null;
		
		
		if (this.getFacilityService().find(this.getId())== null){
			message.setResult(Message.ERROR);
			message.setDescription("facility not found");//TO DO: I18N 
			return ERROR;
		}		
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());	
		file = new model.File();
		file.setName(this.getUploadFileName());
		file.setData(data);	
		image.setFile(file);			
		
		this.getImageService().insert(image);
		this.getFacilityImageService().insert(this.getId(),image.getId());
				
		message.setResult(Message.SUCCESS);
		message.setDescription(getText("facilityImageAddSuccessAction"));
		return SUCCESS;
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
	
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public StructureImageService getStructureImageService() {
		return structureImageService;
	}



	public void setStructureImageService(StructureImageService structureImageService) {
		this.structureImageService = structureImageService;
	}



	public RoomTypeImageService getRoomTypeImageService() {
		return roomTypeImageService;
	}



	public void setRoomTypeImageService(RoomTypeImageService roomTypeImageService) {
		this.roomTypeImageService = roomTypeImageService;
	}



	public RoomImageService getRoomImageService() {
		return roomImageService;
	}



	public void setRoomImageService(RoomImageService roomImageService) {
		this.roomImageService = roomImageService;
	}



	public FacilityImageService getFacilityImageService() {
		return facilityImageService;
	}



	public void setFacilityImageService(FacilityImageService facilityImageService) {
		this.facilityImageService = facilityImageService;
	}
	
	
	
	
	
}