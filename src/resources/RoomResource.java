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
import model.Room;
import model.RoomType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.BookingService;
import service.FacilityService;
import service.ImageService;
import service.RoomService;
import service.RoomTypeService;

@Path("/rooms/")
@Component
@Scope("prototype")
public class RoomResource {
	
	@Autowired
    private RoomService roomService = null;
	@Autowired
    private RoomTypeService roomTypeService = null;
	@Autowired
    private BookingService bookingService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private ImageService imageService = null;
	
	
	@GET
    @Path("structure/{idStructure}/search/{offset}/{rownum}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Room> simpleSearch(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum, @QueryParam("term") String term){
        List<Room> filteredRooms = null;
        List<Image> images = null;
		List<Facility> facilities = null;
		RoomType roomType = null;
       
        filteredRooms = new ArrayList<Room>();
        
        for(Room each: this.getRoomService().search(idStructure,offset,rownum, term)){
        	roomType = this.getRoomTypeService().findRoomTypeById(each.getId_roomType());
        	each.setRoomType(roomType);
        	images = this.getImageService().findImagesByIdRoom(each.getId());
			each.setImages(images);
			facilities = this.getFacilityService().findRoomFacilitiesByIdRoom(each.getId());
			filteredRooms.add(each);            		   
        }       
        return filteredRooms;          
    } 
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Room getRoom(@PathParam("id") Integer id){
		Room ret = null;
		List<Image> images = null;
		List<Facility> facilities = null;
		
		ret = this.getRoomService().findRoomById(id);
		images = this.getImageService().findImagesByIdRoom(id);
		ret.setImages(images);
		facilities = this.getFacilityService().findRoomFacilitiesByIdRoom(id);
		ret.setFacilities(facilities);
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Room save(Room room) {
       
        this.getRoomService().insertRoom(room);
        return room;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Room update(Room room) {        
        
    	this.getRoomService().updateRoom(room);
        return room;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public  Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;
		
    	if(this.getBookingService().countBookingsByIdRoom(id)>0){
    		throw new NotFoundException("The room you are trying to delete has links to one or more bookings." +
					" Please try to delete the associated bookings before.");
		}
		count = this.getRoomService().deleteRoom(id);
		if(count == 0){
			throw new NotFoundException("Error: the room has NOT been deleted");
		}
		return count;
    }
	
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}
	public BookingService getBookingService() {
		return bookingService;
	}
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
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