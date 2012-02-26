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
import service.StructureImageService;

@Path("/images/")
@Component
@Scope("prototype")

public class ImageResource {
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private StructureImageService structureImageService = null;
	@Autowired
	private RoomTypeImageService roomTypeImageService = null;
	@Autowired
	private RoomImageService roomImageService = null;
	@Autowired
	private FacilityImageService facilityImageService = null;
	
	@Autowired
	private RoomTypeService roomTypeService = null;	
	
	
	
	@GET
	@Path("{id}")
	//@Path("structure/{idImage}")
	//@Path("roomType/{idImage}")
	//@Path("room/{idImage}")
	//@Path("facility/{idImage}")
	@Produces({MediaType.APPLICATION_JSON})  
	public Image getImage(@PathParam("id") Integer id) {
		Image image = null;
		
		image = this.getImageService().find(id);
		if (image == null) {
			throw new WebApplicationException(404);
		}			
		return image;
	}	
	
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getStructureImages(@PathParam("idStructure") Integer idStructure){
		return this.getImageService().findByIdStructure(idStructure);
		
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
	
	
	
	@POST
	@Path("structure/{idStructure}")
	
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON}) 
	public Image uploadStructureImage(
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
		this.getImageService().insert(image);
		this.getStructureImageService().insert(idStructure, image.getId());
		
		return image;
	}
	
	
	@POST
	@Path("roomType/{idRoomType}")
	//@Path("roomType")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON}) 
	public Image uploadRoomTypeImage(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("caption") String caption,
		@PathParam("idRoomType") Integer idRoomType){															
 
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
		this.getImageService().insert(image);
		this.getRoomTypeImageService().insert(idRoomType, image.getId());
		return image;
	}
	
	@POST
	@Path("room/{idRoom}")
	//@Path("room")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON}) 
	public Image uploadRoomImage(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("caption") String caption,
		@PathParam("idRoom") Integer idRoom){															
 
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
		this.getImageService().insert(image);
		this.getRoomImageService().insert(idRoom, image.getId());
		return image;
	}
	
	@POST
	@Path("facility/{idFacility}")
	//@Path("facility")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON}) 
	public Image uploadFacilityImage(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("caption") String caption,
		@PathParam("idFacility") Integer idFacility){															
 
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
		this.getImageService().insert(image);
		this.getFacilityImageService().insert(idFacility, image.getId());
		return image;
	}
	
	
	
	@PUT
	@Path("{id}")
	//@Path("structure/{idImage}")
	//@Path("roomType/{idImage}")
	//@Path("room/{idImage}")
	//@Path("facility/{idImage}")
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON}) 
	public Image updateImage(Image image) {
		
		image = this.getImageService().update(image);		
		
		return image;
	}
	
		
	@DELETE
    @Path("{id}")
	//@Path("structure/{idImage}")
	//@Path("roomType/{idImage}")
	//@Path("room/{idImage}")
	//@Path("facility/{idImage}")
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

	

	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}

	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}

	public StructureImageService getStructureImageService() {
		return structureImageService;
	}

	public void setStructureImageService(StructureImageService structureImageService) {
		this.structureImageService = structureImageService;
	}

	public RoomTypeImageService getRoomTypeImageService() {
		return roomTypeImageService;
	}

	public void setRoomTypeImageService(RoomTypeImageService roomTypeImageService) {
		this.roomTypeImageService = roomTypeImageService;
	}

	public RoomImageService getRoomImageService() {
		return roomImageService;
	}

	public void setRoomImageService(RoomImageService roomImageService) {
		this.roomImageService = roomImageService;
	}

	public FacilityImageService getFacilityImageService() {
		return facilityImageService;
	}

	public void setFacilityImageService(FacilityImageService facilityImageService) {
		this.facilityImageService = facilityImageService;
	}	
	
	
	
}
