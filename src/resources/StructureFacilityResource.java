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
import service.StructureFacilityService;
import utils.I18nUtils;

import com.sun.jersey.api.NotFoundException;

@Path("/structureFacilities/")
@Component
@Scope("prototype")
public class StructureFacilityResource {	
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private StructureFacilityService structureFacilityService = null;
	
		
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Map> getStructureFacilities(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		List<Map> ret = null;
		List<Facility> facilities = null;
		Integer id = null;
		Map map = null;
							
		ret = new ArrayList<Map>();
		facilities = this.getFacilityService().findByIdStructure(idStructure,offset,rownum);
	
		for(Facility each: facilities){
			id = this.getStructureFacilityService().findIdByIdStructureAndIdFacility(idStructure, each.getId()); 
			map = new HashMap();
			map.put("id", id);
			map.put("idStructure", idStructure);
			map.put("facility", each);
			ret.add(map);
		}		
		return ret;
	}	
	
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Map insertStructureFacility(Map map){
		Integer id_structure = null;
		Integer id_facility;
		Integer id;
		
		id_structure = (Integer)map.get("idStructure");
		id_facility = (Integer)((Map)map.get("facility")).get("id");
 		
 		this.getStructureFacilityService().insert(id_structure, id_facility);
		id = this.getStructureFacilityService().findIdByIdStructureAndIdFacility(id_structure, id_facility);
		map.put("id", id);
 		return map;
	}
	
	
	@DELETE
    @Path("{idStructure}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteStructureFacility(@PathParam("idStructure") Integer id){
    	Integer count = 0;				
		
    	count = this.getStructureFacilityService().delete(id);
    	if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("structureFacilityDeleteErrorAction"));
		}			
		return count;
    }   

	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public StructureFacilityService getStructureFacilityService() {
		return structureFacilityService;
	}
	public void setStructureFacilityService(StructureFacilityService structureFacilityService) {
		this.structureFacilityService = structureFacilityService;
	}	
		
}