package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Facility;
import model.Image;
import model.Room;
import model.RoomType;
import model.Structure;
import model.User;
import model.internal.Message;
import model.internal.TreeNode;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
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

@ParentPackage(value="default")
public class RoomAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Room room = null;
	private Message message = new Message();
	private List<Facility> roomFacilities = null;
	private List<Integer> roomFacilitiesIds = new ArrayList<Integer>();
	private List<Room> rooms = null;
	private Integer roomId;
	private Image image = null;
	private Integer roomTypeId = null;
	private List<RoomType> roomTypes = null;
	private List<Facility> roomTypeFacilities = null;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	
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
		@Action(value="/findAllRooms",results = {
				@Result(name="success",location="/accomodation.jsp")
		}),
		@Action(value="/findAllRoomsJson",results = {
				@Result(type ="json",name="success", params={
						"root","rooms"
				})}) 
	})
	public String findAllRooms() {
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(structure));
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(structure));
		this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
		if (this.getRoomTypeId() != null){			
			this.setRooms(this.getRoomService().findRoomsByRoomTypeId(structure,this.getRoomTypeId()));
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findAllTreeRoomsJson",results = {
				@Result(type ="json",name="success", params={
						"root","treeNodes"
				})}) 
	})
	public String findAllTreeRooms() {
		User user = null;
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		this.setRooms(this.getRoomService().findRoomsByIdStructure(structure));
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(structure));
		this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
			
		//Setting tree node for rooms folding
		for (RoomType eachRoomType : this.getRoomTypes()) {							
			//build first level nodes - room types
			this.treeNodes.add(TreeNode.buildNode(eachRoomType.getName().toString(), "?roomTypeId=" + eachRoomType.getId()));
		}
		return SUCCESS;
	}

	@Actions({
		@Action(value="/findRoomTypesForRoom",results = {
				@Result(name="success",location="/jsp/contents/roomTypeFacility_table.jsp")
		}),
		@Action(value="/findRoomTypesForRoomJson",results = {
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String findAllRoomTypesForRoom() {
		User user = null;
		Structure structure = null;
		List <Facility> selectedFacilities = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
		selectedFacilities = this.getRoomTypeService().findRoomTypeById(structure,this.getRoom().getRoomType().getId()).getFacilities();
		for(Facility each: selectedFacilities){			
			this.getRoomFacilitiesIds().add(each.getId());		//popolo l'array roomFacilitiesIds con gli id delle Facilities già presenti nella Room da editare
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goUpdateRoom",results = {
				@Result(name="success",location="/room_edit.jsp")
		})
	})
	public String goUpdateRoom() {
		User user = null;
		Structure structure = null;
		Room oldRoom = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		oldRoom = this.getRoomService().findRoomById(structure,this.getRoom().getId());
		oldRoom.setImages(this.getImageService().findImagesByIdRoom(this.getRoom().getId()));
		this.setRoom(oldRoom);
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(structure));
		//this.setRoomFacilities(this.getStructureService().findRoomFacilitiesByIdStructure(structure));
		this.setRoomFacilities(this.getFacilityService().findUploadedFacilitiesByIdStructure(structure.getId()));
		for(Facility each: this.getRoom().getFacilities()){			
			this.getRoomFacilitiesIds().add(each.getId());		//popolo l'array roomFacilitiesIds con gli id delle Facilities già presenti nella Room da editare
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/saveUpdateRoom",results = {
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String saveUpdateRoom() {
		User user = null; 
		Structure structure = null;
		Room oldRoom = null;
				
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		oldRoom = this.getRoomService().findRoomById(structure,this.getRoom().getId());			
		if(oldRoom == null){
			//Si tratta di un add			
			return this.saveRoom(structure);			
		}else{
			//Si tratta di un update			
			return this.updateRoom(structure, oldRoom);			
		}	
	}
	
	private String saveRoom(Structure structure){
		List<Room> rooms = null;
		String names = "";
		List<Facility> checkedFacilities = null;
		RoomType theRoomType = null;
		
		rooms = this.splitRooms();					
		for(Room each: rooms){
			if(this.getRoomService().findRoomByName(structure,each.getName()) != null){
				names = names + "," + each.getName();
			}
		}
		
		if(names.length()>0){
			this.getMessage().setResult(Message.ERROR);
			String text = getText("rooms") + names.substring(1) + getText("alreadyPresent");
			this.getMessage().setDescription(text);
			return "error";			
		}	
		else{	
			checkedFacilities = this.getFacilityService().findUploadedFacilitiesByIds(this.getRoomFacilitiesIds());
			for(Room each: rooms){
				each.setId(structure.nextKey());				
				each.setFacilities(checkedFacilities);
				this.getFacilityService().insertRoomFacilities(this.getRoomFacilitiesIds(), each.getId());

				if(this.getRoom().getRoomType().getId() < 0){
					this.getMessage().setResult(Message.ERROR);
					
					String text = (this.getRoomTypeService().findRoomTypesByIdStructure(structure).size() > 0)? getText("roomTypeNotSelectedAction") : getText("roomTypeAbsentAction");
					this.getMessage().setDescription(text);
					return "error";	
				}
				theRoomType = this.getRoomTypeService().findRoomTypeById(structure,this.getRoom().getRoomType().getId());
				each.setRoomType(theRoomType);
				this.getRoomService().insertRoom(structure, each);
			}
			this.getMessage().setResult(Message.SUCCESS);
			String text = getText("roomsAddSuccessAction");
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
	
	private String updateRoom(Structure structure, Room oldRoom){
		//Si tratta di un update
		String newName = null;
		List<Facility> checkedFacilities = null;
		RoomType theRoomType = null;
		
		newName = this.getRoom().getName();
		if(newName.contains(",")){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("roomNameCommaError"));
			return "error";
		}				
		if(!newName.equals(oldRoom.getName())){
			if(this.getRoomService().findRoomByName(structure,newName) != null){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription(getText("roomNameAlreadyPresentError"));
				return "error";
			}
		}
		//checkedFacilities = this.getStructureService().findRoomFacilitiesByIds(structure,this.getRoomFacilitiesIds());
		checkedFacilities = this.getFacilityService().findUploadedFacilitiesByIds(this.getRoomFacilitiesIds());
		this.getRoom().setFacilities(checkedFacilities);
		
		this.getFacilityService().deleteAllFacilitiesFromRoom(this.getRoom().getId());
		this.getFacilityService().insertRoomFacilities(this.getRoomFacilitiesIds(), this.getRoom().getId());
		
		theRoomType = this.getRoomTypeService().findRoomTypeById(structure,this.getRoom().getRoomType().getId());
		this.getRoom().setRoomType(theRoomType);
		
		this.getRoomService().updateRoom(structure, this.getRoom());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("roomUpdateSuccessAction"));
		return "success";
	}

	@Actions({
		@Action(value="/deleteRoom",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String deleteRoom() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		if(this.getRoomService().deleteRoom(structure,this.getRoom())>0){
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
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	public String deleteRoomImage() {
		User user = null; 
				
		user = (User)this.getSession().get("user");
				
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
	public List<Integer> getRoomFacilitiesIds() {
		return roomFacilitiesIds;
	}
	public void setRoomFacilitiesIds(List<Integer> roomFacilitiesIds) {
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
	
	
}