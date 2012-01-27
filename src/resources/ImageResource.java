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

import org.apache.commons.io.FileUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.multipart.file.DefaultMediaTypePredictor;

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
	@Path("{id}")
	@Produces("image/*")
	public Response getImage(@PathParam("id") Integer id) {
		Image image = null;
		
		image = this.getImageService().findImageById(id);
		if (image == null) {
			throw new WebApplicationException(404);
		}		
		
		String mt = DefaultMediaTypePredictor.CommonMediaTypes.getMediaTypeFromFileName(image.getFileName()).toString();
		return Response.ok(image.getData(), mt).build();
	}
	
	
	@DELETE
    @Path("roomType/{id_roomType}/{id_image}")
    @Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteRoomTypeImage(@PathParam("id_roomType") Integer id_roomType, @PathParam("id_image") Integer id_image){
    	Integer count = 0;			
		
		
    	count = this.getImageService().deleteRoomTypeImage(id_roomType, id_image);
    	count = this.getImageService().deleteImage(id_image);
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
