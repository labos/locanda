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

import model.RoomType;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;

import service.ExtraPriceListService;
import service.FacilityService;
import service.ImageService;
import service.RoomPriceListService;
import service.RoomService;
import service.RoomTypeService;
import service.StructureService;
import utils.I18nUtils;

@Path("/roomTypes/")
@Component
@Scope("prototype")
public class RoomTypeResource {
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
    private StructureService structureService = null;
	@Autowired
    private RoomService roomService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
    private SolrServer solrServerRoomType = null;
	
	@PostConstruct
    public void init(){
    	List<RoomType> roomTypes = null;
    	
    	roomTypes = this.getRoomTypeService().findAll();
    	try {
			this.getSolrServerRoomType().addBeans(roomTypes);
			this.getSolrServerRoomType().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@GET
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<RoomType> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<RoomType> roomTypes = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        RoomType aRoomType = null;
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
			rsp = this.getSolrServerRoomType().query(query);
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

        roomTypes = new ArrayList<RoomType>();
        if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
            // System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   aRoomType = this.getRoomTypeService().findRoomTypeById(id);
        	   
        	   roomTypes.add(aRoomType);
           }  
       }       
       return roomTypes;          
    }
    
	@GET
	@Path("structure/{idStructure}/suggest")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> suggest(@PathParam("idStructure") Integer idStructure, @QueryParam("term") String term) {
		SolrQuery query = null;
		QueryResponse rsp = null;
		List<String> ret = null;
		List<Count> values = null;
			
		query = new SolrQuery();
		query.setFacet(true);
		query.setQuery("*:* AND id_structure:" + idStructure.toString());
		query.addFacetField("text");
		term = term.toLowerCase();
		query.setFacetPrefix(term);

		try {
			rsp = this.getSolrServerRoomType().query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		ret = new ArrayList<String>();

		if (rsp != null) {
			values = rsp.getFacetField("text").getValues();
			if(values!=null){
				for(Count each: values){
					if(each.getCount()>0){
						ret.add(each.getName());
					}
				}	
			}					
		}
		return ret;
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public RoomType getRoomType(@PathParam("id") Integer id){
		RoomType ret = null;
		//List<Image> images = null;
		//List<Facility> facilities = null;
		
		ret = this.getRoomTypeService().findRoomTypeById(id);
		//images = this.getImageService().findCheckedByIdRoomType(id);
		//ret.setImages(images);
		//facilities = this.getFacilityService().findCheckedByIdRoomType(id);
		//ret.setFacilities(facilities);
		return ret;
	}
	
	@POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RoomType save(RoomType roomType) {
		
        this.getRoomTypeService().insertRoomType(roomType);
        this.getStructureService().addPriceListsForRoomType(roomType.getId_structure(), roomType.getId());
        try {
			this.getSolrServerRoomType().addBean(roomType);			
			this.getSolrServerRoomType().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return roomType;
    }
   
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public RoomType update(RoomType roomType) {        
        
    	try {
    	this.getRoomTypeService().updateRoomType(roomType);
    	}catch(Exception ex){}
    	try {
			this.getSolrServerRoomType().addBean(roomType);			
			this.getSolrServerRoomType().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
        return roomType;
    }
    
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})   
    public  Integer delete(@PathParam("id") Integer id){
    	Integer count = 0;		
		
		if(this.getRoomService().countRoomsByIdRoomType(id) > 0){
			throw new NotFoundException(I18nUtils.getProperty("roomTypeDeleteRoomError"));
		}
		count = this.getRoomTypeService().deleteRoomType(id);
		this.getRoomPriceListService().deleteRoomPriceListsByIdRoomType(id);
		this.getExtraPriceListService().deleteExtraPriceListsByIdRoomType(id);
		if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("roomTypeDeleteErrorAction"));
		}
		try {
			this.getSolrServerRoomType().deleteById(id.toString());
			this.getSolrServerRoomType().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
    }
	
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}	
	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}
	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}
	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}
	public void setExtraPriceListService(ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
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
	public SolrServer getSolrServerRoomType() {
		return solrServerRoomType;
	}
	public void setSolrServerRoomType(SolrServer solrServerRoomType) {
		this.solrServerRoomType = solrServerRoomType;
	}
	
}