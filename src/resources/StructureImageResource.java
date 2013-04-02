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

import model.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.ImageService;
import service.StructureImageService;
import utils.I18nUtils;

import com.sun.jersey.api.NotFoundException;

@Path("/structureImages/")
@Component
@Scope("prototype")
public class StructureImageResource {
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private StructureImageService structureImageService = null;	
	
		
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Map> getStructureImages(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		List<Map> ret = null;
		List<Image> images = null;
		Integer id = null;
		Map map = null;
							
		ret = new ArrayList<Map>();
		images = this.getImageService().findByIdStructure(idStructure,offset,rownum);
		for(Image each: images){
			id = this.getStructureImageService().findIdByIdStructureAndIdImage(idStructure, each.getId()); 
			map = new HashMap();
			map.put("id", id);
			map.put("idStructure", idStructure);
			map.put("image", each);
			ret.add(map);
		}		
		return ret;
	}	
	
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Map insertStructureImage(Map map){
		Integer id_structure = null;
		Integer id_image;
		Integer id;
		
		id_structure = (Integer)map.get("idStructure");
		id_image = (Integer)((Map)map.get("image")).get("id");
 		
 		this.getStructureImageService().insert(id_structure, id_image);
		id = this.getStructureImageService().findIdByIdStructureAndIdImage(id_structure, id_image);
		map.put("id", id);
 		return map;
	}
	
	
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteStructureImage(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getStructureImageService().delete(id);
    	if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("structureImageDeleteErrorAction"));
		}			
		return count;
    }   

	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public StructureImageService getStructureImageService() {
		return structureImageService;
	}
	public void setStructureImageService(StructureImageService structureImageService) {
		this.structureImageService = structureImageService;
	}	
	
}