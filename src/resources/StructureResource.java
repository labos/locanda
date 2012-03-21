package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
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
import model.Structure;
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

import service.FacilityService;
import service.ImageService;
import service.StructureService;

@Path("/structures/")
@Component
@Scope("prototype")
public class StructureResource {
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private FacilityService facilityService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
    private SolrServer solrServerStructure = null;
   
    
    @PostConstruct
    public void init(){
    	List<Structure> structures = null;
    	
    	structures = this.getStructureService().findAll();
    	try {
			this.getSolrServerStructure().addBeans(structures);
			this.getSolrServerStructure().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @GET
    @Path("search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Structure> search(@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
        List<Structure> structures = null;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Structure aStructure = null;
        Integer id;             
       
        if(term.trim().equals("")){
        	term = "*:*";
        }
        //term = term + " AND id_structure:" + idStructure.toString();
        query = new SolrQuery();   		
        query.setQuery( term);
        query.setStart(start);
        query.setRows(rows);
              
        try {
			rsp = this.getSolrServerStructure().query( query );
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

       structures = new ArrayList<Structure>();
       if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
        	   aStructure = this.getStructureService().findStructureById(id);
        	   structures.add(aStructure);
           }  
       }       
       return structures;          
    }
    
    @GET
    @Path("suggest")
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
			rsp = this.getSolrServerStructure().query( query );
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
	public Structure getStructure(@PathParam("id") Integer idStructure){
		Structure ret = null;
		List<Image> images = null;
		List<Facility> facilities = null;
		
		ret = this.getStructureService().findStructureById(idStructure);
		images = this.getImageService().findCheckedByIdRoomType(idStructure);
		ret.setImages(images);
		facilities = this.getFacilityService().findCheckedByIdRoomType(idStructure);
		ret.setFacilities(facilities);
		return ret;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Structure save(Structure structure) {
	       
		this.getStructureService().insertStructure(structure);
		try {
			this.getSolrServerStructure().addBean(structure);			
			this.getSolrServerStructure().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	    return structure;
	}
	   
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Structure update(Structure structure) {
	    	
	    try{
	    	this.getStructureService().updateStructure(structure);
	    }catch(Exception ex){}
	    try {
			this.getSolrServerStructure().addBean(structure);			
			this.getSolrServerStructure().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	    return structure;
	}

	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
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
	public SolrServer getSolrServerStructure() {
		return solrServerStructure;
	}
	public void setServerStructure(SolrServer solrServerStructure) {
		this.solrServerStructure = solrServerStructure;
	}
	
}