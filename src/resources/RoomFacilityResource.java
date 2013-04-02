package resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Facility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.FacilityService;
import service.RoomFacilityService;
import service.RoomService;
import utils.I18nUtils;

import com.sun.jersey.api.NotFoundException;

@Path("/roomFacilities/")
@Component
@Scope("prototype")
public class RoomFacilityResource {	
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private RoomFacilityService roomFacilityService = null;
	@Autowired
	private RoomService roomService = null;
	
		
	@GET
	@Path("room/{idRoom}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Map> getRoomFacilities(@PathParam("idRoom") Integer idRoom,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		List<Map> ret = null;
		List<Facility> facilities = null;
		Integer id = null;
		Map map = null;
		Integer idStructure;
		
		ret = new ArrayList<Map>();
		
		idStructure = this.getRoomService().findIdStructureByIdRoom(idRoom);
		facilities = this.getFacilityService().findByIdStructure(idStructure,offset,rownum);
	
		for(Facility each: facilities){
			id = this.getRoomFacilityService().findIdByIdRoomAndIdFacility(idRoom, each.getId()); 
			map = new HashMap();
			map.put("id", id);
			map.put("idRoom", idRoom);
			map.put("facility", each);
			ret.add(map);
		}		
		return ret;
	}	
	
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Map insertRoomFacility(Map map){
		Integer id_room = null;
		Integer id_facility;
		Integer id;
		
		id_room = (Integer)map.get("idRoom");
		id_facility = (Integer)((Map)map.get("facility")).get("id");
 		
 		this.getRoomFacilityService().insert(id_room, id_facility);
		id = this.getRoomFacilityService().findIdByIdRoomAndIdFacility(id_room, id_facility);
		map.put("id", id);
 		return map;
	}
	
	
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteRoomFacility(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getRoomFacilityService().delete(id);
    	if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("roomFacilityDeleteError"));
		}			
		return count;
    }   

	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public RoomFacilityService getRoomFacilityService() {
		return roomFacilityService;
	}
	public void setRoomFacilityService(RoomFacilityService roomFacilityService) {
		this.roomFacilityService = roomFacilityService;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	
}