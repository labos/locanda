package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

import model.Room;
import model.RoomType;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.TermsResponse;
import org.apache.solr.client.solrj.response.TermsResponse.Term;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
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
	@Autowired
    private SolrServer solrServerRoom = null;
	
	
	@PostConstruct
    public void init(){
    	List<Room> rooms = null;
    	
    	rooms = this.getRoomService().findAll();
    	try {
			this.getSolrServerRoom().addBeans(rooms);
			this.getSolrServerRoom().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@GET
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Room> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<Room> rooms = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Room aRoom = null;
        Integer id;             
       
        if(term.trim().equals("")){
        	term = "*:*";
        }
        term = term + " AND id_structure:" + idStructure.toString();
        query = new SolrQuery();   		
        query.setQuery(term);
        query.setStart(start);
        query.setRows(rows);
              
        try {
			rsp = this.getSolrServerRoom().query(query);
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

        rooms = new ArrayList<Room>();
        if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
            // System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   aRoom = this.getRoomService().findRoomById(id);
        	   aRoom.setRoomType(this.getRoomTypeService().findRoomTypeById(aRoom.getId_roomType()));
        	   rooms.add(aRoom);
           }  
       }       
       return rooms;          
    }
    
    @GET
    @Path("structure/{idStructure}/suggest")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> suggest(@PathParam("idStructure") Integer idStructure,@QueryParam("term") String term){        
        SolrQuery query = null;
        QueryResponse rsp = null;
        List<String> ret = null;
        TermsResponse termsResponse = null;
        List<Term> terms;
        
        query = new SolrQuery();         
        query.setQueryType("/terms");
        query.addTermsField("text");
        query.setParam("terms.prefix", term); 
     // query.setParam("id_structure", idStructure.toString());
        
        try {
			rsp = this.getSolrServerRoom().query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();			
		} 
        ret = new ArrayList<String>();
        
        if(rsp!=null){
        	termsResponse = rsp.getTermsResponse();
            terms = termsResponse.getTerms("text");
            for(int i = 0; i <terms.size(); i++){
            	ret.add(terms.get(i).getTerm());
            } 
        }         
        return ret; 
     } 
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Room getRoom(@PathParam("id") Integer id){
		Room ret = null;
		
		ret = this.getRoomService().findRoomById(id);
		ret.setRoomType(this.getRoomTypeService().findRoomTypeById(ret.getId_roomType()));
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Room save(Room room) {
       
        this.getRoomService().insertRoom(room);
        try {
			this.getSolrServerRoom().addBean(room);			
			this.getSolrServerRoom().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        room.setRoomType(this.getRoomTypeService().findRoomTypeById(room.getId_roomType()));
        return room;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Room update(Room room) {        
        
    	try {
    	this.getRoomService().updateRoom(room);
    	}catch(Exception ex){}
    	try {
    		this.getSolrServerRoom().addBean(room);			
    		this.getSolrServerRoom().commit();			
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (SolrServerException e) {
    		e.printStackTrace();
    	}
    	room.setRoomType(this.getRoomTypeService().findRoomTypeById(room.getId_roomType()));
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
		try {
			this.getSolrServerRoom().deleteById(id.toString());
			this.getSolrServerRoom().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
	public SolrServer getSolrServerRoom() {
		return solrServerRoom;
	}
	public void setSolrServerRoom(SolrServer solrServerRoom) {
		this.solrServerRoom = solrServerRoom;
	}
	
}