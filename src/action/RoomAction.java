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
import model.Room;
import model.RoomType;
import model.UserAware;
import model.internal.TreeNode;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.FacilityService;
import service.RoomService;
import service.RoomTypeService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
public class RoomAction extends ActionSupport implements SessionAware,UserAware{
	private Map<String, Object> session = null;
	private List<Facility> roomFacilities = null;
	private List<Room> rooms = null;
	private Integer roomTypeId = null;
	private List<RoomType> roomTypes = null;
	private List<Facility> roomTypeFacilities = null;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	private Integer idStructure;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private FacilityService facilityService = null;
	
	@Actions({
		@Action(value="/findAllRooms",results = {
				@Result(name="success",location="/WEB-INF/jsp/rooms.jsp")
		}),
		@Action(value="/findAllRoomsJson",results = {
				@Result(type ="json",name="success", params={"root","rooms"})
				}) 
	})
	public String findAllRooms() {
		
		this.setRooms(this.getRoomService().findRoomsByIdStructureOrdered(this.getIdStructure()));
		for(Room aRoom: this.getRooms()){
			aRoom.setFacilities(this.getFacilityService().findCheckedByIdRoom(aRoom.getId(),0,100));
		}
		
		this.setRoomTypes(this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure()));
		this.setRoomFacilities(this.getFacilityService().findCheckedByIdStructure(this.getIdStructure(),0,100));
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
	

	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
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
	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
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
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
}