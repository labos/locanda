/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
package action;

import java.util.Map;

import model.Image;
import model.Structure;
import model.User;
import model.UserAware;
import model.internal.Message;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.FacilityService;
import service.ImageService;
import service.StructureService;
import service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
public class StructureAction extends ActionSupport implements SessionAware,UserAware {
	
	private Map<String, Object> session = null;
	private Message message = new Message();	
	private Structure structure = null;
	private Image image = null;
	private String password;
	private String reTyped;
	private Integer idStructure;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private UserService userService = null;
	@Autowired
	private ImageService imageService = null;
	@Autowired
	private FacilityService facilityService = null;
	
	@Actions({
		@Action(value="/goUpdateDetails",results = {
				@Result(name="success",location="/WEB-INF/jsp/details_edit.jsp")
		})
	})
	public String goUpdateDetails() {
		Structure structure = null;
		
		structure = this.getStructureService().findStructureById(this.getIdStructure());		
		structure.setImages(this.getImageService().findByIdStructure(structure.getId()));
		structure.setFacilities(this.getFacilityService().findByIdStructure(structure.getId()));
		
		this.setStructure(structure);
		return SUCCESS;
	}

	@Actions({
		@Action(value="/updateDetails",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String updateDetails() {
		User user = null;
		
		user = (User)this.getSession().get("user");
		
		this.getStructure().setId_user(user.getId());
		this.getStructureService().updateStructure(this.getStructure());
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("structureDetailsUpdatedSuccessAction"));
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/updateAccount",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String updateAccount() {
		User user = null;
		
		user = (User)this.getSession().get("user");		
		if ((this.getPassword().length() <= 5) && (this.getReTyped().length() <= 5) ){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("passwordLengthError"));
			return ERROR;	
		}
		if (!this.getPassword().equals(this.getReTyped()) ){			
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("passwordConfirmError"));
			return ERROR;				
		}
		user.setPassword(this.getPassword());
		this.getUserService().updateUser(user);
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("passwordUpdateSuccessAction"));
		return SUCCESS;	
	}
	
	/*
	@Actions({
		@Action(value="/deleteImageStructure",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteImageStructure() {
					
		if (this.getImageService().deleteStructureImage(this.getImage().getId()) > 0) {
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("structureImageDeleteSuccessAction"));	
			return SUCCESS;
		}
		else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("structureImageDeleteErrorAction"));
			return ERROR;
		}
	}
	*/
	/*
	@Actions({
		@Action(value="/deleteStructureFacility",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteStructureFacility() {
				
		if ( this.getFacilityService().deleteStructureFacility(this.getImage().getId())>0) {
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("structureFacilityDeleteSuccessAction"));			
			return SUCCESS;
		}
		else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("structureFacilityDeleteErrorAction"));
			return ERROR;
		}
	}	
*/
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
	public String getReTyped() {
		return reTyped;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public ImageService getImageService() {
		return imageService;
	}
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
	public FacilityService getFacilityService() {
		return facilityService;
	}
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}
	public Integer getIdStructure() {
		return idStructure;
	}
	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	
}