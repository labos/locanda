package action;

import java.util.List;
import java.util.Map;

import model.Extra;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;

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
		User user = null;
		Structure structure = null;
		Extra anEmptyExtra = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		this.setExtras(structure.getExtras());
		
		anEmptyExtra = new Extra();
		anEmptyExtra.setTimePriceType("per Night");
		anEmptyExtra.setResourcePriceType("per Room");
		this.setExtra(anEmptyExtra);		
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goUpdateExtra",results = {
				@Result(name="success",location="/extra_edit.jsp")
		})		
	})
	public String goUpdateExtra() {
		User user = null;
		Structure structure = null;
		Extra extra = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		extra = structure.findExtraById(this.getExtra().getId());
		this.setExtra(extra);
		return SUCCESS;
	}
		
	@Actions({
		@Action(value="/saveUpdateExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String saveUpdateExtra() {
		User user = null;
		Structure structure = null;
		Extra oldExtra = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		oldExtra = structure.findExtraById(this.getExtra().getId());
		if(oldExtra == null){
			//Si tratta di un add
			this.getExtra().setId(structure.nextKey());
			structure.addExtra(this.getExtra());
			this.buildExtraPriceListFromExtra();
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Extra Added successfully");
			return SUCCESS;
		}else{
			//Si tratta di un update
			structure.updateExtra(this.getExtra());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Extra updated successfully");
			return SUCCESS;			
		}		
	}
	
	@Actions({
		@Action(value="/deleteExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String deleteExtra() {
		User user = null;
		Structure structure = null;
		Extra currentExtra = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		currentExtra = structure.findExtraById(this.getExtra().getId());
		if(structure.deleteExtra(currentExtra)){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Extra deleted successfully");
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error deleting extra");
			return "error";
		}		
	}
	
	public void buildExtraPriceListFromExtra() {
		User user = null;
		Structure structure = null;
		ExtraPriceListItem newExtraPriceListItem = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		for (ExtraPriceList eachPriceList : structure.getExtraPriceLists()) {
			newExtraPriceListItem = new ExtraPriceListItem();
			newExtraPriceListItem.setId(structure.nextKey());
			newExtraPriceListItem.setExtra(this.getExtra());
			newExtraPriceListItem.setPrice(0.0);
			eachPriceList.addItem(newExtraPriceListItem);
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