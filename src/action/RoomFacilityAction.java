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
	private Facility facility;
	private Integer idStructure;
	@Autowired
	private FacilityService facilityService = null;
	
	@Actions({
		@Action(value="/goUpdateRoomFacilities",results = {
				@Result(name="success",location="/WEB-INF/jsp/roomFacilities_edit.jsp")
		})
	})
	public String goUpdateRoomFacilities() {
		
		this.setRoomFacilities(this.getFacilityService().findCheckedByIdStructure(this.getIdStructure(),0,100));
		for(Facility each: this.getFacilityService().findCheckedByIdRoom(this.getIdRoom(),0,100)){	
			this.roomFacilitiesIds.add(each.getId());			
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
	public Integer getIdRoom() {
		return idRoom;
	}
	public void setIdRoom(Integer idRoom) {
		this.idRoom = idRoom;
	}
	public List<Integer> getRoomFacilitiesIds() {
		return roomFacilitiesIds;
	}
	public void setRoomFacilitiesIds(List<Integer> roomFacilitiesIds) {
		this.roomFacilitiesIds = roomFacilitiesIds;
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