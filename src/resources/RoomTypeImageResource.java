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

@Path("/roomTypeImages/")
@Component
@Scope("prototype")

public class RoomTypeImageResource {
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private RoomTypeImageService roomTypeImageService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
		
	@GET
	@Path("roomType/{idRoomType}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public JSONArray getStructureImages(@PathParam("idRoomType") Integer idRoomType){
		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		List<Image> images = null;
		ObjectMapper objectMapper = null; 
		String imageAsJsonString = null;
		Integer id = null;
		String idAsJsonString = null;
		Integer idStructure = null;
				
		jsonArray = new JSONArray();		
		
		objectMapper = new ObjectMapper();
		idStructure = this.getRoomTypeService().findIdStructureByIdRoomType(idRoomType);
		images = this.getImageService().findByIdStructure(idStructure);
		for(Image each: images){
			id = this.getRoomTypeImageService().findIdByIdRoomTypeAndIdImage(idRoomType, each.getId());  
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
				jsonObject.put("idRoomType", idRoomType);
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
	public JSONObject insertRoomTypeImage(JSONObject jsonObject){	
		Integer id_roomType = null;
		Integer id_image = null;
		Integer id;
		
 		try {
 			id_roomType = jsonObject.getInt("idRoomType");
			id_image = jsonObject.getJSONObject("image").getInt("id");			
			
		} catch (JSONException e) {
			e.printStackTrace();
		} 
 		this.getRoomTypeImageService().insert(id_roomType, id_image);
 		id = this.getRoomTypeImageService().findIdByIdRoomTypeAndIdImage(id_roomType, id_image);
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
    public Integer deleteRoomTypeImage(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getRoomTypeImageService().delete(id);
    	if(count == 0){
			throw new NotFoundException("Error: the roomType image has NOT been deleted");
		}			
		return count;
    }   

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	
	public RoomTypeImageService getRoomTypeImageService() {
		return roomTypeImageService;
	}

	public void setRoomTypeImageService(RoomTypeImageService roomTypeImageService) {
		this.roomTypeImageService = roomTypeImageService;
	}

	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}

	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}	
	
	
	
}
