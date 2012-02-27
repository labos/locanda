package resources;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.ImageService;

@Path("/images/")
@Component
@Scope("prototype")

public class ImagesResource {
	@Autowired
	private ImageService imageService = null;	
		
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getStructureImages(@PathParam("idStructure") Integer idStructure){
		return this.getImageService().findByIdStructure_check(idStructure);
		
	}	
	
	@GET
	@Path("roomType/{idRoomType}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getRoomTypeImages(@PathParam("idRoomType") Integer idRoomType){
		return this.getImageService().findByIdRoomType(idRoomType);
	}
	
	@GET
	@Path("room/{idRoom}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getRoomImages(@PathParam("idRoom") Integer idRoom){
		return this.getImageService().findByIdRoom(idRoom);
	}

	@GET
	@Path("facility/{idFacility}")
	@Produces({MediaType.APPLICATION_JSON})	
	public Image getFacilityImage(@PathParam("idFacility") Integer idFacility){
		return this.getImageService().findByIdFacility(idFacility);
	}
	
	
	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
}
