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
import service.RoomTypeFacilityService;
import service.RoomTypeService;
import utils.I18nUtils;

import com.sun.jersey.api.NotFoundException;

@Path("/roomTypeFacilities/")
@Component
@Scope("prototype")
public class RoomTypeFacilityResource {	
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private RoomTypeFacilityService roomTypeFacilityService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	
		
	@GET
	@Path("roomType/{idRoomType}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Map> getRoomTypeFacilities(@PathParam("idRoomType") Integer idRoomType,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		List<Map> ret = null;
		List<Facility> facilities = null;
		Integer id = null;
		Map map = null;
		Integer idStructure;
		
		ret = new ArrayList<Map>();
		
		idStructure = this.getRoomTypeService().findIdStructureByIdRoomType(idRoomType);
		facilities = this.getFacilityService().findByIdStructure(idStructure,offset,rownum);
	
		for(Facility each: facilities){
			id = this.getRoomTypeFacilityService().findIdByIdRoomTypeAndIdFacility(idRoomType, each.getId()); 
			map = new HashMap();
			map.put("id", id);
			map.put("idRoomType", idRoomType);
			map.put("facility", each);
			ret.add(map);
		}		
		return ret;
	}	
	
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Map insertRoomTypeFacility(Map map){
		Integer id_roomType = null;
		Integer id_facility;
		Integer id;
		
		id_roomType = (Integer)map.get("idRoomType");
		id_facility = (Integer)((Map)map.get("facility")).get("id");
 		
 		this.getRoomTypeFacilityService().insert(id_roomType, id_facility);
		id = this.getRoomTypeFacilityService().findIdByIdRoomTypeAndIdFacility(id_roomType, id_facility);
		map.put("id", id);
 		return map;
	}
	
	
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteRoomTypeFacility(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getRoomTypeFacilityService().delete(id);
    	if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("roomTypeFacilityDelete"));
		}			
		return count;
    }   

	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public RoomTypeFacilityService getRoomTypeFacilityService() {
		return roomTypeFacilityService;
	}
	public void setRoomTypeFacilityService(
			RoomTypeFacilityService roomTypeFacilityService) {
		this.roomTypeFacilityService = roomTypeFacilityService;
	}
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}	
	
}