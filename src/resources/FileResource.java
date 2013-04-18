package resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.File;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import service.FileService;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.file.DefaultMediaTypePredictor;

@Path("/file/")
@Component
@Scope("prototype")
public class FileResource {
	@Autowired
	private FileService fileService;
	
	
	@GET
	@Path("{id}")
	public Response getFile(@PathParam("id") Integer id) {
		model.File file = null;
		
		file = this.getFileService().findById(id);
		if (file == null) {
			throw new WebApplicationException(404);
		}	
		
		String mt = DefaultMediaTypePredictor.CommonMediaTypes.getMediaTypeFromFileName(file.getName()).toString();
		return Response.ok(file.getData(), mt).build();
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON}) 
	public File update(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("id") Integer id){
		
		File file = null;
		
		file = new File();
		file.setId(id);
		file.setName(fileDetail.getFileName());
		try {
			file.setData(IOUtils.toByteArray(uploadedInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getFileService().update(file);
		file.setData(new byte[0]);
		return file;
	}
	
	@POST
	@Path("/ie")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.TEXT_HTML}) 
	public String updateIe(
		@FormDataParam("upload") InputStream uploadedInputStream,
		@FormDataParam("upload") FormDataContentDisposition fileDetail,
		@FormDataParam("id") Integer id){
		
		File file = null;
		String str = "";
		file = new File();
		file.setId(id);
		file.setName(fileDetail.getFileName());
		try {
			file.setData(IOUtils.toByteArray(uploadedInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getFileService().update(file);
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
		return str;
	}

	public FileService getFileService() {
		return fileService;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
}