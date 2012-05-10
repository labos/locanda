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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import model.Facility;
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


import service.FacilityService;

@Path("/facilities/")
@Component
@Scope("prototype")
public class FacilityResource {
	@Autowired
	private FacilityService facilityService = null;	
	@Autowired
	private SolrServer solrServerFacility = null;
		
	
	@PostConstruct
    public void init(){
    	List<Facility> facilities = null;
    	
    	facilities = this.getFacilityService().findAll();
    	try {
			this.getSolrServerFacility().addBeans(facilities);
			this.getSolrServerFacility().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@GET
	@Path("structure/{idStructure}/search/{start}/{rows}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Facility> search(
			@PathParam("idStructure") Integer idStructure,
			@PathParam("start") Integer start, @PathParam("rows") Integer rows,
			@QueryParam("term") String term) {
		List<Facility> facilities = null;
		
		SolrQuery query = null;
		QueryResponse rsp = null;
		SolrDocumentList solrDocumentList = null;
		SolrDocument solrDocument = null;
		Facility aFacility = null;
		Integer id;

		if (term.trim().equals("")) {
			term = "*:*";
		}
		term = term + " AND id_structure:" + idStructure.toString();
		query = new SolrQuery();
		query.setQuery(term);
		query.setStart(start);
		query.setRows(rows);

		try {
			rsp = this.getSolrServerFacility().query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		facilities = new ArrayList<Facility>();
		if (rsp != null) {
			solrDocumentList = rsp.getResults();
			for (int i = 0; i < solrDocumentList.size(); i++) {
				solrDocument = solrDocumentList.get(i);
				id = (Integer) solrDocument.getFieldValue("id");
				aFacility = this.getFacilityService().find(id);
				if (aFacility != null) {
					facilities.add(aFacility);
				}
			}
		}
		return facilities;
	}
	
	@GET
	@Path("structure/{idStructure}/suggest")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> suggest(@PathParam("idStructure") Integer idStructure,@QueryParam("term") String term) {
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
			rsp = this.getSolrServerFacility().query(query);
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
	public Facility findFacilityById(@PathParam("id") Integer id){
		Facility facility = null;
		
		facility = this.getFacilityService().find(id);
		if (facility == null) {
			throw new WebApplicationException(404);
		}
		return 	facility;	
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
	public Facility insert(Facility facility){
		this.getFacilityService().insert(facility);
		try {
			this.getSolrServerFacility().addBean(facility);			
			this.getSolrServerFacility().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return facility;		
	}
	
	@PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
	public Facility update(Facility facility){
		this.getFacilityService().update(facility);	
		try {
			this.getSolrServerFacility().addBean(facility);			
			this.getSolrServerFacility().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return facility;		
	}
	
	 @DELETE
	 @Path("{id}")
	 @Produces({MediaType.APPLICATION_JSON})   
	 public Integer delete(@PathParam("id") Integer id){
		 Integer count = 0;		
		
		count =  this.getFacilityService().delete(id);
		try {
			this.getSolrServerFacility().deleteById(id.toString());
			this.getSolrServerFacility().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	 } 
	
	 @GET
	 @Path("all/structure/{idStructure}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getAllFacilities(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findByIdStructure(idStructure,offset,rownum);
	 }
	 
	 @GET
	 @Path("checked/structure/{idStructure}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getStructureFacilities(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findCheckedByIdStructure(idStructure,offset, rownum);
	 }
	 
	 @GET
	 @Path("checked/roomType/{idRoomType}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getRoomTypeFacilities(@PathParam("idRoomType") Integer idRoomType,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findCheckedByIdRoomType(idRoomType,offset, rownum);
	 }
	 
	 @GET
	 @Path("checked/room/{idRoom}/{offset}/{rownum}")
	 @Produces({MediaType.APPLICATION_JSON})	
	 public List<Facility> getRoomFacilities(@PathParam("idRoom") Integer idRoom,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		 return this.getFacilityService().findCheckedByIdRoom(idRoom,offset, rownum);
	 }
	
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public SolrServer getSolrServerFacility() {
		return solrServerFacility;
	}
	public void setSolrServerFacility(SolrServer solrServerFacility) {
		this.solrServerFacility = solrServerFacility;
	}
	
}