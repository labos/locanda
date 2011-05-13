package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Extra;
import model.RoomType;
import model.Structure;
import model.User;
import model.internal.Message;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ConventionAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private List<Convention> conventions = null;
	private Convention convention = null;
	
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
		this.setConventions(structure.getConventions());
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
		this.setConvention(structure.findConventionById(this.getConvention().getId())); 
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
		
		oldConvention = structure.findConventionById(this.getConvention().getId());
		if(oldConvention == null){
			//Si tratta di una aggiunta
			this.getConvention().setId(structure.nextKey());
			structure.addConvention(this.getConvention());
			this.buildRoomPriceListFromConvention();
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Convention added successfully");
			
		}else{
			//Si tratta di un update
			structure.updateConvention(this.getConvention());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Convention updated successfully");
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
		Convention currentConvention = null;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		currentConvention = structure.findConventionById(this.getConvention().getId());
		if(structure.removeConvention(currentConvention)){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription("Convention removed successfully");
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Error deleting convention");
			return ERROR;
		}
	}
	
	public void buildRoomPriceListFromConvention(){
		User user = null;
		Structure structure = null;
		RoomPriceList newRoomPriceList = null;
		ExtraPriceList newExtraPriceList = null;
		RoomPriceListItem newRoomPriceListItem = null;
		ExtraPriceListItem newExtraPriceListItem = null;
		Double[] prices = null;
		Double price = 0.0;
		
		user = (User)session.get("user");
		structure = user.getStructure();
		for (Season eachSeason : structure.getSeasons()) {
			for (RoomType eachRoomType : structure.getRoomTypes()) {
				newRoomPriceList = new RoomPriceList();
				newRoomPriceList.setId(structure.nextKey());
				newRoomPriceList.setSeason(eachSeason);
				newRoomPriceList.setRoomType(eachRoomType);
				newRoomPriceList.setConvention(this.getConvention());
				List<RoomPriceListItem> roomItems = new ArrayList<RoomPriceListItem>();
				for (int i=1; i<=eachRoomType.getMaxGuests(); i++) {
					newRoomPriceListItem = new RoomPriceListItem();
					newRoomPriceListItem.setId(structure.nextKey());
					newRoomPriceListItem.setNumGuests(i);
					prices = new Double[7];
					for (int y=0; y<7; y++) {
						prices[y] = 0.0;
					}
					newRoomPriceListItem.setPrices(prices);
					roomItems.add(newRoomPriceListItem);
				}
				newRoomPriceList.setItems(roomItems);
				structure.addRoomPriceList(newRoomPriceList);
					
				newExtraPriceList = new ExtraPriceList();
				newExtraPriceList.setId(structure.nextKey());
				newExtraPriceList.setSeason(eachSeason);
				newExtraPriceList.setRoomType(eachRoomType);
				newExtraPriceList.setConvention(this.getConvention());
				List<ExtraPriceListItem> extraItems = new ArrayList<ExtraPriceListItem>();
				for (Extra eachExtra : structure.getExtras()) {
					newExtraPriceListItem = new ExtraPriceListItem();
					newExtraPriceListItem.setId(structure.nextKey());
					newExtraPriceListItem.setExtra(eachExtra);
					prices = new Double[7];
					for (int y=0; y<7; y++) {
						prices[y] = 0.0;
					}
					newExtraPriceListItem.setPrices(prices);
					newExtraPriceListItem.setPrice(price);
					extraItems.add(newExtraPriceListItem);
				}
				newExtraPriceList.setItems(extraItems);
				structure.addExtraPriceList(newExtraPriceList);
			}
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
	

}
