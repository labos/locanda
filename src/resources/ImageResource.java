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
import service.StructureImageOwnershipService;

@Path("/image/")
@Component
@Scope("prototype")

public class ImageResource {
	@Autowired
	private ImageService imageService = null;
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})  
	public Image getImage(@PathParam("id") Integer id) {
		Image image = null;
		
		image = this.getImageService().find(id);
		if (image == null) {
			throw new WebApplicationException(404);
		}			
		return image;
	}	
	
		
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON}) 
	public Image uploadImage(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("caption") String caption,
		@PathParam("idStructure") Integer idStructure){															
 
		Image image = null;
		File file = null;
		
		image = new Image();
		image.setCaption(caption);
		file = new File();
		file.setName(fileDetail.getFileName());
		try {
			file.setData(
					IOUtils.toByteArray(uploadedInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image.setFile(file);
		this.getImageService().insert(image,idStructure);
		return image;
	}
	
	
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON}) 
	public Image updateImage(Image image) {
		
		image = this.getImageService().update(image);		
		
		return image;
	}
	
		
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteImage(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getImageService().delete(id);
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

	

	
}
