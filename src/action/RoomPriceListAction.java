package action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Booking;
import model.Extra;
import model.Room;
import model.Structure;
import model.User;
import model.internal.Message;
import model.internal.TreeData;
import model.internal.TreeNode;
import model.listini.Season;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class RoomPriceListAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private Booking booking = null;
	private List<Integer> bookingExtraIds = new ArrayList<Integer>();
	private Integer numNights;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	
	@Actions({
		@Action(value="/calculatePrices",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session"
				} ),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				} ),
				@Result(name="input", location = "/validationError.jsp" )
		})
		
	})	
	
	public String calculatePrices() {
		User user = null; 
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		Structure structure = null; 
		Room theBookedRoom = null;
		List<Extra>  checkedExtras = null;
		Integer numNights;
						
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		if ( (this.getBooking().getDateOut() != null) && (this.getBooking().getDateIn() != null) ) {
			if(DateUtils.truncatedCompareTo(this.getBooking().getDateOut(), this.getBooking().getDateIn(), Calendar.DAY_OF_MONTH)<=0){
				this.getMessage().setResult(Message.ERROR);
				this.getMessage().setDescription("DateOut deve essere maggiore di DateIn!");
				return "error";
			}				
		}
		
		theBookedRoom = structure.findRoomById(this.getBooking().getRoom().getId());
		this.getBooking().setRoom(theBookedRoom);
		
		checkedExtras = structure.findExtrasByIds(this.getBookingExtraIds());
		this.getBooking().setExtras(checkedExtras);	
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());		
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		extraSubtotal = structure.calculateExtraSubtotalForBooking(this.getBooking());
		this.getBooking().setExtraSubtotal(extraSubtotal);	
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Prezzo Calcolato con Successo");
		return "success";				
	}
	
	@Actions({
		@Action(value="/goFindAllRoomPriceLists",results = {
				@Result(name="success",location="/priceLists.jsp")
		}) 
		
	})
	public String goFindAllRoomPriceLists() {
		
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/findAllRoomPriceLists",results = {
				@Result(type ="json",name="success", params={
						"root","treeNodes"
				} ),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				} ),
				@Result(name="input", location = "/validationError.jsp" )
		})
		
	})
	
	public String findAllRoomPriceLists() {
		User user = null;
		Structure structure = null;
		Set<Integer> years = new HashSet<Integer>();			
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		for (Season eachSeason : structure.getSeasons()) {			//costruisco il set con tutti gli anni
			years.add(eachSeason.getYear());
		}
		for (Integer eachYear : years) {							//costruisco i nodi di primo livello - gli anni
			TreeData data = new TreeData();
			data.setTitle(eachYear.toString());
			TreeNode node1 = new TreeNode();
			node1.setData(data);
			this.treeNodes.add(node1);
		}
		
		for (TreeNode eachNode1 : this.treeNodes) {					//costruisco i nodi di secondo livello - le stagioni
			List<Season> perYearSeasons = structure.findSeasonsByYear(Integer.parseInt(eachNode1.getData().getTitle()));	//tutte le stagioni di quell'anno
			for (Season eachYearSeason : perYearSeasons) {
				eachNode1.buildChild(eachYearSeason.getName());
			}
			for (TreeNode eachNode2 : eachNode1.getChildren()) {	//costruisco i nodi di terzo livello - i roomTypes
				for (String eachRoomType : structure.findAllRoomTypes()) {
					eachNode2.buildChild(eachRoomType);
				}			
			}
		}
		
		return "success";
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
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}



	public List<Integer> getBookingExtraIds() {
		return bookingExtraIds;
	}

	public void setBookingExtraIds(List<Integer> bookingExtraIds) {
		this.bookingExtraIds = bookingExtraIds;
	}

	public Integer getNumNights() {
		return numNights;
	}

	public void setNumNights(Integer numNights) {
		this.numNights = numNights;
	}

	public List<TreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}
	
	
}
