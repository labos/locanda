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
import service.RoomImageService;
import service.RoomService;
import utils.I18nUtils;

import com.sun.jersey.api.NotFoundException;

@Path("/roomImages/")
@Component
@Scope("prototype")
public class RoomImageResource {
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private RoomImageService roomImageService = null;
	@Autowired
	private RoomService roomService = null;
		
	
	@GET
	@Path("room/{idRoom}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Map> getRoomImages(@PathParam("idRoom") Integer idRoom,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		List<Map> ret = null;
		List<Image> images = null;
		Integer id = null;
		Integer idStructure = null;
		Map map = null; 
					
		ret = new ArrayList<Map>();
		
		idStructure = this.getRoomService().findIdStructureByIdRoom(idRoom);
		images = this.getImageService().findByIdStructure(idStructure,offset,rownum);
		for(Image each: images){
			id = this.getRoomImageService().findIdByIdRoomAndIdImage(idRoom, each.getId());  
			map = new HashMap();
			map.put("id", id);
			map.put("idRoom", idRoom);
			map.put("image", each);
			ret.add(map);
		}		
		return ret;
	}
	
	@POST	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON}) 
	public Map insertRoomImage(Map map){
		Integer id_room = null;
		Integer id_image;
		Integer id;
		
		id_room = (Integer)map.get("idRoom");
		id_image = (Integer)((Map)map.get("image")).get("id");
		
 		this.getRoomImageService().insert(id_room, id_image);
		id = this.getRoomImageService().findIdByIdRoomAndIdImage(id_room, id_image);
		map.put("id", id);
 		return map;
	}
	
	
		
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteRoomImage(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getRoomImageService().delete(id);
    	if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("roomImageDeleteErrorAction"));
		}			
		return count;
    }   

	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public RoomImageService getRoomImageService() {
		return roomImageService;
	}
	public void setRoomImageService(RoomImageService roomImageService) {
		this.roomImageService = roomImageService;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}	
		
}