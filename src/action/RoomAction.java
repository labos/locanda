package action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.Extra;
import model.Image;
import model.Room;
import model.RoomFacility;
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

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class RoomAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Room room = null;
	private Message message = new Message();
	private List<RoomFacility> roomFacilities = null;
	private List<Integer> roomFacilitiesIds = new ArrayList<Integer>();
	private List<Room> rooms = null;
	private Integer roomId;
	private Image image = null;
	private Integer roomTypeId = null;
	private List<RoomType> roomTypes = null;
	private List<RoomFacility> roomTypeFacility = null;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	
	
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
		//Controllare che sia diverso da null in un interceptor
		structure = user.getStructure();
		this.setRooms(structure.getRooms());
		this.setRoomTypes(structure.getRoomTypes());
		this.setRoomFacilities(structure.getRoomFacilities());
		this.setRoomTypeFacility(structure.getRoomTypeFacilities());
		
		if (this.getRoomTypeId() != null){
			
			this.setRooms(structure.findRoomsByRoomTypeId(this.getRoomTypeId()));
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
		//Controllare che sia diverso da null in un interceptor
		structure = user.getStructure();
		this.setRooms(structure.getRooms());
		this.setRoomTypes(structure.getRoomTypes());
		this.setRoomFacilities(structure.getRoomFacilities());
		this.setRoomTypeFacility(structure.getRoomTypeFacilities());
		//Setting tree node for rooms folding
		for (RoomType eachRoomType : this.getRoomTypes()) {							//costruisco i nodi di primo livello - gli anni
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
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
	})
	public String findAllRoomTypesForRoom() {
		User user = null;
		Structure structure = null;
		List <RoomFacility> selectedFacility = null;
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setRoomFacilities(structure.getRoomFacilities());
		selectedFacility = structure.findRoomTypeById(this.getRoom().getRoomType().getId() ).getRoomTypeFacilities();
		this.setRoomTypeFacility(selectedFacility);
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
		oldRoom = structure.findRoomById(this.getRoom().getId());
		this.setRoom(oldRoom);
		this.setRoomTypes(structure.getRoomTypes());
		this.setRoomFacilities(structure.getRoomFacilities());
		for(RoomFacility each: this.getRoom().getFacilities()){			
			this.getRoomFacilitiesIds().add(each.getId());		//popolo l'array roomFacilitiesIds con gli id delle Facilities già presenti nella Room da editare
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/saveUpdateRoom",results = {
				@Result(name="input", location="/validationError.jsp"),
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String saveUpdateRoom() {
		User user = null; 
		Structure structure = null;
		Room oldRoom = null;
				
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		oldRoom = structure.findRoomById(this.getRoom().getId());	
		
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
		List<RoomFacility> checkedFacilities = null;
		RoomType theRoomType = null;
		
		rooms = this.splitRooms();					
		for(Room each: rooms){
			if(structure.findRoomByName(each.getName()) != null){
				names = names + "," + each.getName();
			}
		}
		
		if(names.length()>0){
			this.getMessage().setResult(Message.ERROR);
			String text = "Le stanze: " + names.substring(1) + " sono gia' presenti";
			this.getMessage().setDescription(text);
			return "error";			
		}	
		else{	
			checkedFacilities = structure.findAllRoomFacilitiesByIds(this.getRoomFacilitiesIds());
			for(Room each: rooms){
				each.setId(structure.nextKey());				
				each.setFacilities(checkedFacilities);
				theRoomType = structure.findRoomTypeById(this.getRoom().getRoomType().getId());
				each.setRoomType(theRoomType);
				structure.addRoom(each);
			}
			this.getMessage().setResult(Message.SUCCESS);
			String text = "Le stanze sono state inserite con successo";
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
		List<RoomFacility> checkedFacilities = null;
		RoomType theRoomType = null;
		
		newName = this.getRoom().getName();
		if(newName.contains(",")){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Il nuovo nome non puo' contenere virgole");
			return "error";
		}				
		if(!newName.equals(oldRoom.getName())){
			if(structure.findRoomByName(newName) != null){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("Stai usando un nome gia' esistente");
				return "error";
			}
		}
		checkedFacilities = structure.findAllRoomFacilitiesByIds(this.getRoomFacilitiesIds());
		this.getRoom().setFacilities(checkedFacilities);
		
		theRoomType = structure.findRoomTypeById(this.getRoom().getRoomType().getId());
		this.getRoom().setRoomType(theRoomType);
		
		structure.updateRoom(this.getRoom());
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("La stanza e' stata modificata con successo");
		return "success";
		
	}

	
	@Actions({
		@Action(value="/deleteRoom",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String deleteRoom() {
		User user = null; 
		Structure structure = null;
		
		user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		structure = user.getStructure();
		
		if(structure.deleteRoom(this.getRoom())){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("La stanza e' stata cancellata con successo");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non e' stato possibile cancellare la stanza");
			return "error";
		}		
	}	
	
	
	@Actions({
		@Action(value="/deletePhotoRoom",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	public String deletePhotoRoom() {
		User user = null; 
		Structure structure = null;
		Room aRoom = null;
		user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		structure = user.getStructure();
		
		aRoom = structure.findRoomById(this.getRoom().getId());
		
		if(aRoom.deleteImage(this.getImage())){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("La foto e' stata cancellata con successo");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non e' stato possibile cancellare la foto");
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


	public List<RoomFacility> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(List<RoomFacility> roomFacilities) {
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


	public List<RoomFacility> getRoomTypeFacility() {
		return roomTypeFacility;
	}


	public void setRoomTypeFacility(List<RoomFacility> roomTypeFacility) {
		this.roomTypeFacility = roomTypeFacility;
	}

	public List<TreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}
	
}
