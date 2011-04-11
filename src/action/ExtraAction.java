package action;

import java.util.List;
import java.util.Map;

import model.Extra;
import model.Structure;
import model.User;
import model.internal.Message;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ExtraAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private List<Extra> extras = null;
	private Extra extra = null;
	
	@Actions({
		@Action(value="/findAllExtras",results = {
				@Result(name="success",location="/extras.jsp")
		}) 
		
	})
	public String findAllExtras() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		this.setExtras(structure.getExtras());
		return SUCCESS;
	}
	
		
	@Actions({
		@Action(value="/saveUpdateExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String saveUpdateExtra() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Extra oldExtra = structure.findExtraById(this.getExtra().getId());
		if(oldExtra == null){
			//Si tratta di un add
			this.getExtra().setId(structure.nextKey());
/*			this.getExtra().setResourcePriceType("per Room");
			this.getExtra().setTimePriceType("per Night");*/
			structure.addExtra(this.getExtra());
			
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Extra Added successfully");
			return SUCCESS;
		}else{
			//Si tratta di un update
			structure.updateExtra(this.getExtra());
			//Aggiungere update error
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Extra updated successfully");
			return SUCCESS;
			
		}
		
		
	}
	
	
	@Actions({
		@Action(value="/goUpdateExtra",results = {
				@Result(name="success",location="/extra_edit.jsp")
		})
		
	})
	public String goUpdateExtra() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Extra extra = structure.findExtraById(this.getExtra().getId());
		this.setExtra(extra);
		return SUCCESS;
	}
	
		
	@Actions({
		@Action(value="/deleteExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				} ),
				@Result(type ="json",name="error", params={
						"root","message"
				} )
		})
		
	})
	
	public String deleteExtra() {
		User user = (User)this.getSession().get("user");
		//Controllare che sia diverso da null in un interceptor
		Structure structure = user.getStructure();
		Extra currentExtra = structure.findExtraById(this.getExtra().getId());
		if(structure.deleteExtra(currentExtra)){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("L'extra e' stato cancellato con successo");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non e' stato possibile cancellare l'extra");
			return "error";
		}		
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

	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

}
