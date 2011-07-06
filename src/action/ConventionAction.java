package action;

import java.util.List;
import java.util.Map;

import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Convention;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.ConventionService;
import service.RoomPriceListService;
import service.StructureService;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ConventionAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private List<Convention> conventions = null;
	private Convention convention = null;
	@Autowired 
	private StructureService structureService = null;
	@Autowired
	private ConventionService conventionService = null;
	
	
	
	@Actions({
		@Action(value="/findAllConventions",results = {
				@Result(name="success",location="/conventions.jsp")
		})
	})
	public String findAllConventions(){
		User user = null;
		Structure structure = null;
	
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		this.setConventions(this.getConventionService().findConventionsByIdStructure(structure.getId()));
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/goUpdateConvention",results = {
				@Result(name="success",location="/convention_edit.jsp")
		})
	})
	public String goUpdateConvention() {
		User user = null;
		Structure structure = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		
		this.setConvention(this.getConventionService().findConventionById(this.getConvention().getId())); 
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/saveUpdateConvention",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				})
		})
	})
	public String saveUpdateConvention(){
		User user = null;
		Structure structure = null;
		Convention oldConvention = null;
				
		user = (User)session.get("user");
		structure = user.getStructure();
		
		oldConvention = this.getConventionService().findConventionById(this.getConvention().getId());
		if(oldConvention == null){
			//Si tratta di una aggiunta
			//this.getConvention().setId(structure.nextKey());
			this.getConvention().setId_structure(structure.getId());
			this.getConventionService().insertConvention(this.getConvention());
			this.getStructureService().refreshPriceLists(structure);
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("conventionAddSuccessAction"));
			
		}else{
			//Si tratta di un update
			this.getConvention().setId_structure(structure.getId());
			this.getConventionService().updateConvention(this.getConvention());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("conventionUpdateSuccessAction"));
		}
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/deleteConvention",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				})
		})
		
	})
	public String deleteConvention(){
		User user = null;
		Structure structure = null;
				
		user = (User)session.get("user");
		structure = user.getStructure();
		
		if(this.getConventionService().deleteConvention(this.getConvention().getId())>0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("conventionDeleteSuccessAction"));
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("conventionDeleteErrorAction"));
			return ERROR;
		}
	}

	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	public List<Convention> getConventions() {
		return conventions;
	}
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	public Convention getConvention() {
		return convention;
	}
	public void setConvention(Convention convention) {
		this.convention = convention;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	public ConventionService getConventionService() {
		return conventionService;
	}
	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}

	
	

}