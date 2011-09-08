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
import model.Room;
import model.RoomType;
import model.UserAware;
import model.internal.Message;
import model.internal.TreeNode;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
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
public class RoomAction extends ActionSupport implements SessionAware,UserAware{
	private Map<String, Object> session = null;
	private Room room = null;
	private Message message = new Message();
	private List<Facility> roomFacilities = null;
	private List roomFacilitiesIds = new ArrayList();
	private List<Room> rooms = null;
	private Integer roomId;
	private Image image = null;
	private Integer roomTypeId = null;
	private List<RoomType> roomTypes = null;
	private List<Facility> roomTypeFacilities = null;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	private Integer idStructure;
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
	@Autowired
	private BookingService bookingService = null;
	
	@Actions({
		@Action(value="/findAllRooms",results = {
				@Result(name="success",location="/rooms.jsp")
		}),
		@Action(value="/findAllRoomsJson",results = {
				@Result(type ="json",name="success", params={"root","rooms"})
				}) 
	})
	public String findAllRooms() {
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(this.getIdStructure()));
		for(Room aRoom: this.getRooms()){
			aRoom.setFacilities(this.getFacilityService().findRoomFacilitiesByIdRoom(aRoom.getId()));
		}
		
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure()));
		this.setRoomFacilities(this.getFacilityService().findUploadedFacilitiesByIdStructure(this.getIdStructure()));
		if (this.getRoomTypeId() != null){			
			this.setRooms(this.getRoomService().findRoomsByIdRoomType(this.getRoomTypeId()));
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findAllTreeRoomsJson",results = {
				@Result(type ="json",name="success", params={"root","treeNodes"})
				}) 
	})
	public String findAllTreeRooms() {
				
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure()));
		//Setting tree node for rooms folding
		for (RoomType eachRoomType : this.getRoomTypes()) {							
			//build first level nodes - room types
			this.treeNodes.add(TreeNode.buildNode(eachRoomType.getName().toString(), eachRoomType.getId(), "?roomTypeId=" + eachRoomType.getId() + "&sect=accomodation"));
		}
		return SUCCESS;
	}

	@Actions({
		@Action(value="/findRoomTypesForRoom",results = {
				@Result(name="success",location="/jsp/contents/roomTypeFacility_table.jsp")
		}),
		@Action(value="/findRoomTypesForRoomJson",results = {
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String findAllRoomTypesForRoom() {
		List <Facility> selectedFacilities = null;
				
		this.setRoomFacilities(this.getFacilityService().findUploadedFacilitiesByIdStructure(this.getIdStructure()));
		selectedFacilities = this.getFacilityService().findRoomTypeFacilitiesByIdRoomType(this.getRoom().getRoomType().getId());
		for(Facility each: selectedFacilities){			
			this.getRoomFacilitiesIds().add(each.getId());		//populating roomFacilitiesIds array with the ids of facilities that are already in rooms to be edited
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goUpdateRoom",results = {
				@Result(name="success",location="/room_edit.jsp"),
				@Result(name = "nullpointer", type="redirect", location = "findAllRooms.action")
		})
	})
	public String goUpdateRoom() {
		Room oldRoom = null;
		
		oldRoom = this.getRoomService().findRoomById(this.getRoom().getId());
		oldRoom.setImages(this.getImageService().findImagesByIdRoom(this.getRoom().getId()));
		this.setRoom(oldRoom);
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure()));
		this.setRoomFacilities(this.getFacilityService().findUploadedFacilitiesByIdStructure(this.getIdStructure()));
		for(Facility each: this.getRoom().getFacilities()){			
			this.getRoomFacilitiesIds().add(each.getId());		//populating roomFacilitiesIds array with the ids of facilities that are already in rooms to be edited
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/saveUpdateRoom",results = {
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String saveUpdateRoom() {
		Room oldRoom = null;
		
		oldRoom = (this.getRoom().getId() == null )? null :  this.getRoomService().findRoomById(this.getRoom().getId());
		if(oldRoom == null){
			//It's a new room			
			return this.saveRoom(this.getIdStructure());			
		}else{
			//It's an existing room	
			return this.updateRoom(this.getIdStructure(), oldRoom);			
		}	
	}
	
	private String saveRoom(Integer id_structure){
		List<Room> rooms = null;
		String names = "";
		List<Facility> checkedFacilities = null;
		RoomType theRoomType = null;
		List<Integer> filteredRoomFacilitiesIds = null;
		Integer anInt;
		String text = null;
		
		filteredRoomFacilitiesIds = new ArrayList<Integer>();
		for(Object each: this.getRoomFacilitiesIds()){
			try{
				anInt = Integer.parseInt((String)each);
				filteredRoomFacilitiesIds.add(anInt);
			}catch (Exception e) {
				
			}			
		}
		rooms = this.splitRooms();					
		for(Room each: rooms){
			
			if(this.getRoomService().findRoomByIdStructureAndName(id_structure,each.getName()) != null){
				names = names + "," + each.getName();
			}
		}
		
		if(names.length()>0){
			this.getMessage().setResult(Message.ERROR);
			text = getText("rooms") + names.substring(1) + getText("alreadyPresent");
			this.getMessage().setDescription(text);
			return "error";			
		}	
		else{	
			checkedFacilities = this.getFacilityService().findUploadedFacilitiesByIds(filteredRoomFacilitiesIds);
			for(Room each: rooms){				

				if(this.getRoom().getRoomType().getId() < 0){
					this.getMessage().setResult(Message.ERROR);
					
					text = (this.getRoomTypeService().findRoomTypesByIdStructure(id_structure).size() > 0)? getText("roomTypeNotSelectedAction") : getText("roomTypeAbsentAction");
					this.getMessage().setDescription(text);
					return "error";	
				}
				
				each.setFacilities(checkedFacilities);
								
				theRoomType = this.getRoomTypeService().findRoomTypeById(this.getRoom().getRoomType().getId());
				each.setRoomType(theRoomType);				
				each.setId_roomType(theRoomType.getId());
				each.setId_structure(id_structure);
				
				this.getRoomService().insertRoom(each);
				
			}
			this.getMessage().setResult(Message.SUCCESS);
			text = getText("roomsAddSuccessAction");
			this.getMessage().setDescription(text);
			return "success";	
		}
	}
	
	private List<Room> splitRooms(){
		List<Room> rooms = null;
		
		rooms = new ArrayList<Room>();		
		for(String each: this.getRoom().getName().split(",")){
			if(each.trim().length()>0){
				Room aRoom = new Room();
				aRoom.setName(each.trim());
				aRoom.setNotes(this.getRoom().getNotes());		
				aRoom.setRoomType(this.getRoom().getRoomType());
				rooms.add(aRoom);				
			}
		}		
		return rooms;
	}
	
	private String updateRoom(Integer id_structure, Room oldRoom){
		String newName = null;
		List<Facility> checkedFacilities = null;
		RoomType theRoomType = null;
		List<Integer> filteredRoomFacilitiesIds = null;
		Integer anInt;
		
		filteredRoomFacilitiesIds = new ArrayList<Integer>();
		for(Object each: this.getRoomFacilitiesIds()){
			try{
				anInt = Integer.parseInt((String)each);
				filteredRoomFacilitiesIds.add(anInt);
			}catch (Exception e) {	
			}			
		}
		newName = this.getRoom().getName();
		if(newName.contains(",")){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomNameCommaError"));
			return "error";
		}				
		if(!newName.equals(oldRoom.getName())){
			if(this.getRoomService().findRoomByIdStructureAndName(id_structure, newName) != null){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("roomNameAlreadyPresentError"));
				return "error";
			}
		}
		
		checkedFacilities = this.getFacilityService().findUploadedFacilitiesByIds(filteredRoomFacilitiesIds);
		this.getRoom().setFacilities(checkedFacilities);
		
		theRoomType = this.getRoomTypeService().findRoomTypeById(this.getRoom().getRoomType().getId());
		this.getRoom().setRoomType(theRoomType);		
		this.getRoom().setId_roomType(this.getRoom().getRoomType().getId());
		
		this.getRoom().setId_structure(id_structure);
				
		this.getRoomService().updateRoom(this.getRoom());
			
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("roomUpdateSuccessAction"));
		return "success";
	}

	@Actions({
		@Action(value="/deleteRoom",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteRoom() {
		
		Integer count = 0;
		Integer id_room;
	
		
		id_room = this.getRoom().getId();
		
		if(this.getBookingService().countBookingsByIdRoom(id_room)>0){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non è possibile cancellare la stanza perchè esistono Booking associati a quella stanza");
			return "error";
		}
		count = this.getRoomService().deleteRoom(id_room);
		if(count>0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("roomDeleteSuccessAction"));
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomDeleteErrorAction"));
			return "error";
		}		
	}	
	
	@Actions({
		@Action(value="/deleteRoomImage",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
		
	})
	public String deleteRoomImage() {
					
		if(this.getImageService().deleteRoomImage(this.getImage().getId()) >0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("roomImageDeleteSuccessAction"));
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomImageDeleteErrorAction"));
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
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public List<Integer> getFacilities() {
		return roomFacilitiesIds;
	}
	public void setFacilities(List<Integer> roomFacilitiesIds) {
		this.roomFacilitiesIds = roomFacilitiesIds;
	}
	public List<Facility> getRoomFacilities() {
		return roomFacilities;
	}
	public void setRoomFacilities(List<Facility> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer id) {
		this.roomTypeId = id;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public List getRoomFacilitiesIds() {
		return roomFacilitiesIds;
	}
	public void setRoomFacilitiesIds(List roomFacilitiesIds) {
		this.roomFacilitiesIds = roomFacilitiesIds;
	}
	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public List<Facility> getRoomTypeFacilities() {
		return roomTypeFacilities;
	}
	public void setRoomTypeFacilities(List<Facility> roomTypeFacility) {
		this.roomTypeFacilities = roomTypeFacility;
	}
	public List<TreeNode> getTreeNodes() {
		return treeNodes;
	}
	public void setTreeNodes(List<TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
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
	public BookingService getBookingService() {
		return bookingService;
	}
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	
}