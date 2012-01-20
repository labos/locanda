package resources;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Image;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.ImageService;
import service.RoomTypeService;

@Path("/images/")
@Component
@Scope("prototype")
public class ImageResource {
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	
	@Context
	private ServletContext servletContext = null;
	
	
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getStructureImages(@PathParam("idStructure") Integer idStructure){
		return this.getImageService().findImagesByIdStructure(idStructure);
	}

	@GET
	@Path("structure/{id}")
	@Produces("image/*")
	public Response getStructureImage(@PathParam("id") Integer id) {
		Image image = null;
		String filePath = null;
		File file = null;
		
		
		image = this.getImageService().findStructureImageById(id);
		if (image == null) {
			throw new WebApplicationException(404);
		}
		
		filePath = this.getServletContext().getRealPath("/") +  "resources/" + image.getId_structure() + "/images/structure/" + image.getFileName();
		file = new File(filePath);
		
		String mt = new MimetypesFileTypeMap().getContentType(file);
		return Response.ok(file, mt).build();
	}
	
	
	@GET
	@Path("roomType/{id}")
	@Produces("image/*")
	public Response getRoomTypeImage(@PathParam("id") Integer id) {
		Image image = null;
		String filePath = null;
		File file = null;
		Integer idStructure;
		
		image = this.getImageService().findRoomTypeImageById(id);
		if (image == null) {
			throw new WebApplicationException(404);
		}
		
		idStructure = this.getRoomTypeService().findIdStructureByIdRoomType(image.getId_roomType());
		filePath = this.getServletContext().getRealPath("/") +  "resources/" + idStructure + "/images/roomType/" + image.getFileName();
		file = new File(filePath);
		
		if (!file.exists()) {
			throw new WebApplicationException(404);
		}
		
		String mt = new MimetypesFileTypeMap().getContentType(file);
		return Response.ok(file, mt).build();
	}
	
	@DELETE
    @Path("roomType/{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteRoomTypeImage(@PathParam("id") Integer id){
    	Integer count = 0;			
		
		count = this.getImageService().deleteRoomTypeImage(id);		
		if(count == 0){
			throw new NotFoundException("Error: the image has NOT been deleted");
		}	
		
		return count;
    }   

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}

	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}	
	
	
	
}
