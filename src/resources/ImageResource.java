package resources;

import java.io.IOException;
import java.io.InputStream;
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

import model.File;
import model.Image;

import org.apache.commons.io.IOUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.NotFoundException;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import service.ImageService;
import utils.I18nUtils;

@Path("/images/")
@Component
@Scope("prototype")
public class ImageResource {
	@Autowired
	private ImageService imageService = null;	
	@Autowired
	private SolrServer solrServerImage = null;
	
	
	@PostConstruct
    public void init(){
    	List<Image> images;
    	
    	images = this.getImageService().findAll();
    	try {
			this.getSolrServerImage().addBeans(images);
			this.getSolrServerImage().commit();
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @GET
    @Path("structure/{idStructure}/search/{start}/{rows}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Image> search(@PathParam("idStructure") Integer idStructure,@PathParam("start") Integer start,@PathParam("rows") Integer rows, @QueryParam("term") String term){
       
    	List<Image> images;
        SolrQuery query = null;
        QueryResponse rsp = null;
        SolrDocumentList solrDocumentList = null;
        SolrDocument solrDocument = null;
        Image anImage = null;
        Integer id;             
       
        if(term.trim().equals("")){
        	term = "*:*";
        }
        term = term + " AND id_structure:" + idStructure.toString();
        query = new SolrQuery();   		
        query.setQuery( term);
        query.setStart(start);
        query.setRows(rows);
              
        try {
			rsp = this.getSolrServerImage().query( query );
			
		} catch (SolrServerException e) {
			e.printStackTrace();			
		}

       images = new ArrayList<Image>();
       if(rsp!=null){
    	   solrDocumentList = rsp.getResults();
           for(int i = 0; i <solrDocumentList.size(); i++){
        	   solrDocument = solrDocumentList.get(i);
        	   id = (Integer)solrDocument.getFieldValue("id");
        	// System.out.println("----> "+solrDocument.getFieldValues("text")+" <-----");
        	   anImage = this.getImageService().find(id);
        	   if(anImage!=null){
            	   images.add(anImage);       		   
        	   }

           }  
       }       
       return images;          
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
			rsp = this.getSolrServerImage().query(query);
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
		@FormDataParam("idStructure") Integer idStructure){															
 
		Image image = null;
		File file = null;
		
		image = new Image();
		image.setCaption(caption);
		image.setId_structure(idStructure);
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
		try {
			this.getSolrServerImage().addBean(image);			
			this.getSolrServerImage().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		image.getFile().setData(new byte[0]);
		return image;
	}
	
	@POST
	@Path("/ie")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.TEXT_HTML}) 
	public String uploadImageIe(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("caption") String caption,
		@FormDataParam("idStructure") Integer idStructure){															
 
		Image image = null;
		File file = null;
		String str = "";
		
		image = new Image();
		image.setCaption(caption);
		image.setId_structure(idStructure);
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
		image.getFile().setData(new byte[0]);

		ObjectMapper mapper = new ObjectMapper();
		try {
			str = mapper.writeValueAsString(file);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.getSolrServerImage().addBean(image);			
			this.getSolrServerImage().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	@PUT
	@Path("{id}")
	@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON}) 
	public Image updateImage(Image image) {
		
		this.getImageService().update(image);	
		try {
			this.getSolrServerImage().addBean(image);			
			this.getSolrServerImage().commit();			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return image;
	}
		
	@DELETE
    @Path("{id}")
	@Produces({MediaType.APPLICATION_JSON})   
    public Integer deleteImage(@PathParam("id") Integer id){
    	Integer count = 0;				
		
    	count = this.getImageService().delete(id);
    	if(count == 0){
			throw new NotFoundException(I18nUtils.getProperty("imageDeleteError"));
		}
    	
    	try {
			this.getSolrServerImage().deleteById(id.toString());
			this.getSolrServerImage().commit();
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
	public List<Image> getAllImages(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		return this.getImageService().findByIdStructure(idStructure,offset,rownum);	
	}	
	
	@GET
	@Path("checked/structure/{idStructure}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getStructureImages(@PathParam("idStructure") Integer idStructure,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		return this.getImageService().findCheckedByIdStructure(idStructure,offset,rownum);
	}	
	
	@GET
	@Path("checked/roomType/{idRoomType}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getRoomTypeImages(@PathParam("idRoomType") Integer idRoomType,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		return this.getImageService().findCheckedByIdRoomType(idRoomType,offset,rownum);
	}
	
	@GET
	@Path("checked/room/{idRoom}/{offset}/{rownum}")
	@Produces({MediaType.APPLICATION_JSON})	
	public List<Image> getRoomImages(@PathParam("idRoom") Integer idRoom,@PathParam("offset") Integer offset,@PathParam("rownum") Integer rownum){
		return this.getImageService().findCheckedByIdRoom(idRoom,offset,rownum);
	}

	@GET
	@Path("checked/facility/{idFacility}")
	@Produces({MediaType.APPLICATION_JSON})	
	public Image getFacilityImage(@PathParam("idFacility") Integer idFacility){
		return this.getImageService().findByIdFacility(idFacility);
	}

	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public SolrServer getSolrServerImage() {
		return solrServerImage;
	}
	public void setSolrServerImage(SolrServer solrServerImage) {
		this.solrServerImage = solrServerImage;
	}
		
}