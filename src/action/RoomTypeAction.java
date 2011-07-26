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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Facility;
import model.Image;
import model.RoomType;
import model.UserAware;
import model.internal.Message;

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
public class RoomTypeAction extends ActionSupport implements SessionAware,UserAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private List<RoomType> roomTypes;
	private RoomType roomType = null;
	private Image image = null;
	private List<Facility> facilities = null;
	private List roomTypeFacilitiesIds = new ArrayList();
	private Integer idStructure;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private RoomService roomService = null;
	
	@Actions({
		@Action(value="/findAllRoomTypes",results = {
				@Result(name="success",location="/roomTypes.jsp")
		})
	})
	public String findAllRoomTypes() {
		List<RoomType> roomTypes = null;
		
		roomTypes = this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure());
		for(RoomType each: roomTypes){
			each.setImages(this.getImageService().findImagesByIdRoomType(each.getId()));
		}
		this.setRoomTypes(roomTypes);
		this.setFacilities(this.getFacilityService().findUploadedFacilitiesByIdStructure(this.getIdStructure()));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goUpdateRoomType",results = {
				@Result(name="success",location="/roomType_edit.jsp")
		})		
	})
	public String goUpdateRoomType() {
		RoomType roomType = null;
				
		roomType = this.getRoomTypeService().findRoomTypeById(this.getRoomType().getId());
		roomType.setFacilities(
				this.getFacilityService().findRoomTypeFacilitiesByIdRoomType(this.getRoomType().getId()));
		roomType.setImages(this.getImageService().findImagesByIdRoomType(this.getRoomType().getId()));
		
		this.setRoomType(roomType);
		this.setFacilities(this.getFacilityService().findUploadedFacilitiesByIdStructure(this.getIdStructure()));
		for(Facility each: this.getRoomType().getFacilities()){			
			this.getRoomTypeFacilitiesIds().add(each.getId());		//populating roomFacilitiesIds array with the ids of facilities that are already in rooms to be edited
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/saveUpdateRoomType",results = {
				@Result(type ="json",name="success", params={"root","message"})
		})
	})
	public String saveUpdateRoomType(){
		RoomType oldRoomtype = null;
		List<Facility> checkedFacilities = null;
		List<Integer> filteredRoomTypeFacilitesIds = null;
		Integer anInt;
		
		this.getRoomType().setId_structure(this.getIdStructure());
		
		filteredRoomTypeFacilitesIds = new ArrayList<Integer>();
		for(Object each: this.getRoomTypeFacilitiesIds()){
			try{
				anInt = Integer.parseInt((String)each);
				filteredRoomTypeFacilitesIds.add(anInt);
			}catch (Exception e) {
			}			
		}		
		checkedFacilities = this.getFacilityService().findUploadedFacilitiesByIds(filteredRoomTypeFacilitesIds);
		this.getRoomType().setFacilities(checkedFacilities);
		
		oldRoomtype = this.getRoomTypeService().findRoomTypeById(this.getRoomType().getId());
		if(oldRoomtype == null){
			//It's a new room type		
			this.getRoomTypeService().insertRoomType(this.getRoomType());			
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("roomTypeAddSuccessAction"));			
		}else{
			//It's an existing room type
			this.getRoomTypeService().updateRoomType(this.getRoomType());			
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("roomTypeUpdateSuccessAction"));
		}
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/deleteRoomType",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteRoomType(){
		Integer id_roomType = 0;		
		Integer count = 0;

		id_roomType = this.getRoomType().getId();
		if(this.getRoomService().countRoomsByIdRoomType(id_roomType) > 0){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomTypeDeleteRoomError"));
			return ERROR;
		}

		count = this.getRoomTypeService().deleteRoomType(id_roomType);		
		if(count >0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("roomTypeDeleteSuccessAction"));
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomTypeDeleteErrorAction"));
			return ERROR;
		}
	}
		
	@Actions({
		@Action(value="/deleteRoomTypeImage",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteRoomTypeImage() {
		
		if(this.getImageService().deleteRoomTypeImage(this.getImage().getId())>0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("roomTypeImageDeleteSuccessAction"));
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomTypeImageDeleteErrorAction"));
			return "error";
		}		
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public List getRoomTypeFacilitiesIds() {
		return roomTypeFacilitiesIds;
	}
	public void setRoomTypeFacilitiesIds(List roomFacilitiesIds) {
		this.roomTypeFacilitiesIds = roomFacilitiesIds;
	}	
	public List<Facility> getFacilities() {
		return facilities;
	}
	public void setFacilities(List<Facility> roomTypeFacilities) {
		this.facilities = roomTypeFacilities;
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
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	
}