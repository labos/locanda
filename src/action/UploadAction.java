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

import service.FacilityService;
import service.ImageService;
import service.RoomService;
import service.RoomTypeService;
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
	private RoomTypeService roomTypeService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private ImageService imageService = null;
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
		
		structure = this.getStructureService().findStructureById(this.getId());
		if (structure == null){			
			message.setResult(Message.ERROR);
			message.setDescription(getText("structureImageNoStructureError"));
			return ERROR;
		}
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());		
		image.setFileName(this.getUploadFileName());
		image.setData(data);	
		
		this.getImageService().insertImage(image);
		this.getImageService().insertStructureImage(this.getId(), image.getId());
			
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
		
		aRoomType = this.getRoomTypeService().findRoomTypeById(this.getId());
		if (aRoomType == null){			
			message.setResult(Message.ERROR);
			message.setDescription(getText("roomTypeImageNoRoomTypeError"));
			return ERROR;
		}
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());		
		image.setFileName(this.getUploadFileName());
		image.setData(data);	
		
		this.getImageService().insertImage(image);
		this.getImageService().insertRoomTypeImage(this.getId(), image.getId());
			
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
		
		room = this.getRoomService().findRoomById(this.getId());
		if (room == null){			
			message.setResult(Message.ERROR);
			message.setDescription(getText("roomImageNoRoomError"));
			return ERROR;
		}
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());		
		image.setFileName(this.getUploadFileName());
		image.setData(data);	
		
		this.getImageService().insertImage(image);
		this.getImageService().insertRoomImage(this.getId(),image.getId());
			
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
		
		
		if (this.getFacilityService().findFacilityById(this.getId())== null){
			message.setResult(Message.ERROR);
			message.setDescription("facility not found");//TO DO: I18N 
			return ERROR;
		}		
		
		byte[] data = FileUtils.readFileToByteArray(this.getUpload());
		
		image = new Image();
		image.setCaption(this.getCaption());		
		image.setFileName(this.getUploadFileName());
		image.setData(data);			
		
		this.getImageService().insertImage(image);
		this.getImageService().insertFacilityImage(this.getId(),image.getId());
				
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
	
	
	
	
	
}