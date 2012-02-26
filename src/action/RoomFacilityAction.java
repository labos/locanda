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
import service.RoomService;
import service.StructureService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
public class RoomFacilityAction extends ActionSupport implements SessionAware,UserAware{
	private Map<String, Object> session = null;
	private List<Facility> roomFacilities = null;
	private List<Integer> roomFacilitiesIds = new ArrayList<Integer>();
	private Integer idRoom;
	private Room room = null;
	private Message message = new Message();
	private Facility facility;
	private Integer idStructure;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private FacilityService facilityService = null;
	
	@Actions({
		@Action(value="/goUpdateRoomFacilities",results = {
				@Result(name="success",location="/WEB-INF/jsp/roomFacilities_edit.jsp")
		})
	})
	public String goUpdateRoomFacilities() {
		
		this.setRoomFacilities(this.getFacilityService().findByIdStructure(this.getIdStructure()));
		for(Facility each: this.getFacilityService().findByIdRoom(this.getIdRoom())){	
			this.roomFacilitiesIds.add(each.getId());			
		}
		return SUCCESS;
	}
	
	/*
	@Actions({
		@Action(value="/updateRoomFacilities", results={
				@Result(type ="json",name="success", params={"root","message"})					
			})
	})
	public String updateRoomFacilities() {
		List<Facility>  checkedFacilities = null;
		
		this.setRoom(this.getRoomService().findRoomById(this.getIdRoom()));
		checkedFacilities = this.getFacilityService().findFacilitiesByIds(this.getRoomFacilitiesIds());
		this.getRoom().setFacilities(checkedFacilities);
		this.getFacilityService().deleteAllFacilitiesFromRoom(this.getIdRoom());
		this.getFacilityService().insertRoomFacilities(this.getRoomFacilitiesIds(), this.getIdRoom());
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("facilitiesUpdatedSuccessAction"));
		return SUCCESS;
	}
	*/
	/*
	@Actions({
		@Action(value="/deleteFacility", results={
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})	
			})
	})
	public String deleteUploadedFacility() {
				
		if(this.getFacilityService().deleteUploadedFacility(this.getFacility().getId()) > 0){
			this.getMessage().setResult(Message.SUCCESS);
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			return ERROR;
		}
	}
*/
	/*
	@Actions({
		@Action(value="/updateFacility", results={
				@Result(type ="json",name="success", params={
						"excludeProperties","session,roomFacilities,roomFacilitiesIds,idRoom,room,structureService,roomService,facilityService"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session,roomFacilities,roomFacilitiesIds,idRoom,room,structureService,roomService,facilityService"
				})	
			})
	})
	public String updateUploadedFacility() {
		
		if(this.getFacilityService().updateUploadedFacility(this.getFacility()) > 0){
			this.setFacility(this.getFacilityService().findFacilityById(this.getFacility().getId()));
			this.getMessage().setResult(Message.SUCCESS);
			return SUCCESS;
		}else{
			this.setFacility(this.getFacilityService().findFacilityById(this.getFacility().getId()));
			this.getMessage().setResult(Message.ERROR);
			return ERROR;
		}
	}*/
		
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
	public Integer getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
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
	public List<Integer> getRoomFacilitiesIds() {
		return roomFacilitiesIds;
	}
	public void setRoomFacilitiesIds(List<Integer> roomFacilitiesIds) {
		this.roomFacilitiesIds = roomFacilitiesIds;
	}
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
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
	public Facility getFacility() {
		return facility;
	}
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
		
}