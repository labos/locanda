package action;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.io.*;

import model.Extra;
import model.Image;
import model.Room;
import model.RoomFacility;
import model.Structure;
import model.StructureFacility;
import model.User;
import model.internal.Message;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.StructureService;


import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class StructureAction extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session = null;
	private Message message = new Message();
	
	private Structure structure = null;
	private Image image = null;
	private User user = new User();
	private String reTyped;
	@Autowired
	private StructureService structureService = null;
	

	@Actions({
		@Action(value="/goUpdateDetails",results = {
				@Result(name="success",location="/details_edit.jsp")
		})
		
	})
	public String goUpdateDetails() {
		User user = null; 
				
		user = (User)this.getSession().get("user");
		this.setStructure(user.getStructure());
		this.setUser(user);
		return SUCCESS;
	}

	@Actions({
		@Action(value="/updateDetails",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String updateDetails() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		this.updateStructure(user.getStructure());
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Structure details modified succesfully");
		return SUCCESS;
		
	}
	
	@Actions({
		@Action(value="/updateAccount",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String updateAccount() {
		User user = (User)this.getSession().get("user");
		//check password requirements
		String newPassword = this.getUser().getPassword();
		if (newPassword .length() >5  &&  (  newPassword.equals(this.getReTyped()) )){
			user.setPassword(this.getUser().getPassword());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Password modified succesfully");
			return SUCCESS;	
		}
		else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Your password must be at least 5 characters and/or the two passwords are different");
			return SUCCESS;	
		}
	
		
	}
	
	@Actions({
		@Action(value="/deleteImageStructure",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	public String deleteImageStructure() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		this.setStructure(user.getStructure());
		Image anImage = null;
		//anImage = structure.findImageById(this.image.getId());
		anImage = this.getStructureService().findImageById(structure,this.getImage().getId());
		
		//if (structure.deleteImage(aImage)) {
		if (this.getStructureService().deleteImage(structure,anImage) > 0) {
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Structure image deleted modified succesfully");
			
			return SUCCESS;
		}
		else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error removing Image");
			return ERROR;
		}
		
	}
	
	
	@Actions({
		@Action(value="/deleteStructureFacility",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	public String deleteStructureFacility() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		this.setStructure(user.getStructure());
		StructureFacility aStructureFacility = null;

		//aStructureFacility = structure.findStructureFacilityById(this.image.getId());
		aStructureFacility = this.getStructureService().findStructureFacilityById(structure,this.getImage().getId());
		
		//if ( structure.deleteStructureFacility( aStructureFacility )) {
		if ( this.getStructureService().deleteStructureFacility(structure, aStructureFacility )>0) {
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Structure Facility deleted succesfully");
			
			return SUCCESS;
		}
		else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error removing Structure Facility");
			return ERROR;
		}
		
	}
	
	
	
	
	private void updateStructure(Structure structure) {
		structure.setName(this.structure.getName());
		structure.setEmail(this.structure.getEmail());
		structure.setPhone(this.structure.getPhone());
		structure.setAddress(this.structure.getAddress());
		structure.setCity(this.structure.getCity());
		structure.setCountry(this.structure.getCountry());
		structure.setZipCode(this.structure.getZipCode());
		structure.setUrl(this.structure.getUrl());
		structure.setFax(this.structure.getFax());
		structure.setNotes(this.structure.getNotes());
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReTyped() {
		return reTyped;
	}

	public void setReTyped(String reTyped) {
		this.reTyped = reTyped;
	}

	public StructureService getStructureService() {
		return structureService;
	}

	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	
	

}
