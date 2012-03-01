package resources;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.MediaType;


import model.File;
import model.Image;


import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


import service.FacilityImageService;
import service.ImageService;
import service.RoomImageService;
import service.RoomTypeImageService;
import service.RoomTypeService;
import service.StructureImageService;

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
	public JSONArray getStructureImages(@PathParam("idStructure") Integer idStructure){
		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		List<Image> images = null;
		ObjectMapper objectMapper = null; 
		String imageAsJsonString = null;
		Integer id = null;
		String idAsJsonString = null;;
				
		jsonArray = new JSONArray();		
		
		objectMapper = new ObjectMapper();
		images = this.getImageService().findByIdStructure(idStructure);
		for(Image each: images){
			id = this.getStructureImageService().findIdByIdStructureAndIdImage(idStructure, each.getId());  
			try {
				imageAsJsonString = objectMapper.writeValueAsString(each);
				idAsJsonString = objectMapper.writeValueAsString(id);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			jsonObject = new JSONObject();			
			
			try {
				jsonObject.put("id",new JSONObject(idAsJsonString));
				jsonObject.put("idStructure", idStructure);
				jsonObject.put("image", new JSONObject(imageAsJsonString));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}	
		
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public JSONObject insertStructureImage(JSONObject jsonObject){	
		Integer id_structure = null;
		Integer id_image = null;
		Integer id;
						
 		try {
			id_structure = jsonObject.getInt("idStructure");
			id_image = jsonObject.getJSONObject("image").getInt("id");			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 
 		this.getStructureImageService().insert(id_structure, id_image);
 		id = this.getStructureImageService().findIdByIdStructureAndIdImage(id_structure, id_image);
 		try {
			jsonObject.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
		
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteStructureImage(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getStructureImageService().delete(id);
    	if(count == 0){
			throw new NotFoundException("Error: the structure image has NOT been deleted");
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
