package resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Facility;
import model.Image;
import model.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.FacilityService;
import service.ImageService;
import service.RoomService;
import service.RoomTypeService;
import service.StructureService;

@Path("/roomTypes/")
@Component
@Scope("prototype")
public class RoomTypeResource {
	
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
    private StructureService structureService = null;
	@Autowired
    private RoomService roomService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private ImageService imageService = null;
	
	
	@GET
	@Path("structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<RoomType> getRoomTypes(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		List<RoomType> ret = null;
		List<Image> images = null;
		List<Facility> facilities = null;
		
		ret = this.getRoomTypeService().findRoomTypesByIdStructure(idStructure,offset,rownum);
		for(RoomType each: ret){
			images = this.getImageService().findImagesByIdRoomType(each.getId());
			each.setImages(images);
			facilities = this.getFacilityService().findRoomTypeFacilitiesByIdRoomType(each.getId());
			each.setFacilities(facilities);
		}
		return ret;
	}
	
	@GET
    @Path("structure/{idStructure}/search/{offset}/{rownum}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<RoomType> simpleSearch(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum, @QueryParam("term") String term){
        List<RoomType> filteredRoomTypes = null;
        List<Image> images = null;
		List<Facility> facilities = null;
       
        filteredRoomTypes = new ArrayList<RoomType>();
        
        for(RoomType each: this.getRoomTypeService().search(idStructure,offset,rownum, term)){           
        	images = this.getImageService().findImagesByIdRoomType(each.getId());
			each.setImages(images);
			facilities = this.getFacilityService().findRoomTypeFacilitiesByIdRoomType(each.getId());
            filteredRoomTypes.add(each);            		   
        }       
        return filteredRoomTypes;          
    }
	
	public boolean simpleSearchFilter(RoomType roomType, String term){
		boolean ret = false;
		
		ret = (roomType.getName() != null && roomType.getName().contains(term)) || 
			(roomType.getNotes() != null && roomType.getNotes().contains(term) )|| 
			(roomType.getMaxGuests() != null && roomType.getMaxGuests().toString().contains(term));
		return ret;
	} 
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public RoomType getRoomType(@PathParam("id") Integer id){
		RoomType ret = null;
		List<Image> images = null;
		List<Facility> facilities = null;
		
		ret = this.getRoomTypeService().findRoomTypeById(id);
		images = this.getImageService().findImagesByIdRoomType(id);
		ret.setImages(images);
		facilities = this.getFacilityService().findRoomTypeFacilitiesByIdRoomType(id);
		ret.setFacilities(facilities);
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RoomType save(RoomType roomType) {
       
        this.getRoomTypeService().insertRoomType(roomType);
        this.getStructureService().addPriceListsForRoomType(roomType.getId_structure(), roomType.getId());
        return roomType;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RoomType update(RoomType roomType) {        
        
    	this.getRoomTypeService().updateRoomType(roomType);
        return roomType;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public  Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		if(this.getRoomService().countRoomsByIdRoomType(id) > 0){
			throw new NotFoundException("The room type you are trying to delete has links to one or more room types." +
					" Please try to delete the associated rooms before.");
		}
		count = this.getRoomTypeService().deleteRoomType(id);
		if(count == 0){
			throw new NotFoundException("Error: the room type has NOT been deleted");
		}
		return count;
    }
	
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	
}