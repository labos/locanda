package action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import model.Booking;
import model.Extra;
import model.Room;
import model.RoomType;
import model.Structure;
import model.User;
import model.internal.Message;
import model.internal.TreeNode;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.Season;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.SeasonService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class RoomPriceListAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private Booking booking = null;
	private List<Integer> bookingExtraIds = new ArrayList<Integer>();
	private Integer numNights;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	private RoomPriceList priceList = null;
	
	private Integer seasonId = null;
	private Integer roomTypeId = null;
	private Integer conventionId = null;
	@Autowired
	private SeasonService seasonService = null;
	
	@Actions({
		@Action(value="/calculatePrices",results = {
				@Result(type ="json",name="success", params={
						"excludeProperties","session"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})	
	public String calculatePrices() {
		User user = null; 
		Double roomSubtotal = 0.0;
		Double extraSubtotal = 0.0;
		Structure structure = null; 
		Room theBookedRoom = null;
		List<Extra> checkedExtras = null;
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
		
		if (this.getBooking().getNrGuests() > theBookedRoom.getRoomType().getMaxGuests()) {	//nel caso cambiassi la room con preselezionato un nrGuests superiore al maxGuests della room stessa
			this.getBooking().setNrGuests(theBookedRoom.getRoomType().getMaxGuests());
		}
		
		numNights = this.getBooking().calculateNumNights();
		this.setNumNights(numNights);
		roomSubtotal = structure.calculateRoomSubtotalForBooking(this.getBooking());		
		this.getBooking().setRoomSubtotal(roomSubtotal);
		
		checkedExtras = structure.findExtrasByIds(this.getBookingExtraIds());
		this.getBooking().setExtras(checkedExtras);
		this.getBooking().buildExtraItemsFromExtras(structure);
		extraSubtotal = structure.calculateExtraSubtotalForBooking(this.getBooking());
		this.getBooking().setExtraSubtotal(extraSubtotal);	
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Prezzo Calcolato con Successo");
		return "success";				
	}
	
	@Actions({
		@Action(value="/goFindAllRoomPriceLists",results = {
				@Result(name="success",location="/roomPriceLists.jsp")
		}) 
	})
	public String goFindAllRoomPriceLists() {
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findAllRoomPriceLists",results = {
				@Result(type ="json",name="success", params={
						"root","treeNodes"
				}),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				}),
				@Result(name="input", location = "/validationError.jsp")
		})
	})
	public String findAllRoomPriceLists() {
		User user = null;
		Structure structure = null;
		Set<Integer> years = new HashSet<Integer>();
		ServletContext context = ServletActionContext.getServletContext();
		String webappPath = context.getContextPath();
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		for (Season eachSeason : structure.getSeasons()) {			//costruisco il set con tutti gli anni
			years.add(eachSeason.getYear());
		}
		for (Integer eachYear : years) {							//costruisco i nodi di primo livello - gli anni
			this.treeNodes.add(TreeNode.buildNode(eachYear.toString()));
		}
		
		for (TreeNode eachNode1 : this.treeNodes) {						//per ogni anno costruisco i nodi di secondo livello - le stagioni
			//List<Season> perYearSeasons = structure.findSeasonsByYear(Integer.parseInt(eachNode1.getData().getTitle()));	//tutte le stagioni di quell'anno
			List<Season> perYearSeasons = this.getSeasonService().findSeasonsByYear(structure.getId(),Integer.parseInt(eachNode1.getData().getTitle()));	//tutte le stagioni di quell'anno
			for (Season eachYearSeason : perYearSeasons) {
				eachNode1.buildChild(eachYearSeason.getName());
			}
			for (TreeNode eachNode2 : eachNode1.getChildren()) {		//per ogni stagione costruisco i nodi di terzo livello - i roomTypes
				for (RoomType eachRoomType : structure.getRoomTypes()) {
					eachNode2.buildChild(eachRoomType.getName());
				}
					for (TreeNode eachNode3 : eachNode2.getChildren()) {//per ogni roomType costruisco i nodi di quarto livello - le convenzioni
						Set<Convention> perRoomTypeConventions = new HashSet<Convention>();	//tutte le convenzioni associate a quel roomType in un certo listino
						//perRoomTypeConventions = structure.findConventionsBySeasonAndRoomType(structure.findSeasonByName(eachNode2.getData().getTitle()), structure.findRoomTypeByName(eachNode3.getData().getTitle()));
						perRoomTypeConventions = structure.findConventionsBySeasonAndRoomType(
								this.getSeasonService().findSeasonByName(structure.getId(),eachNode2.getData().getTitle()),
								structure.findRoomTypeByName(eachNode3.getData().getTitle()));
						for (Convention eachRoomTypeConvention : perRoomTypeConventions) {
							/*String href = webappPath + "/findRoomPriceListItems" +
							"?seasonId=" + structure.findSeasonByName(eachNode2.getData().getTitle()).getId() + 
							"&roomTypeId=" + structure.findRoomTypeByName(eachNode3.getData().getTitle()).getId() +
							"&conventionId=" + eachRoomTypeConvention.getId();*/
							String href = webappPath + "/findRoomPriceListItems" +
							"?seasonId=" + this.getSeasonService().findSeasonByName(structure.getId(),eachNode2.getData().getTitle()).getId() + 
							"&roomTypeId=" + structure.findRoomTypeByName(eachNode3.getData().getTitle()).getId() +
							"&conventionId=" + eachRoomTypeConvention.getId();
							eachNode3.buildChild(eachRoomTypeConvention.getName(), href);
						}
					}			
			}
		}
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/findRoomPriceListItems",results = {
				@Result(name="success",location="/jsp/contents/roomPriceList_table.jsp")
				}),
		@Action(value="/findRoomPriceListItemsJson",results = {
				@Result(type ="json",name="success", params={
						"root","priceList"
				})
		})
	})
	public String findRoomPriceListItems() {
		User user = null;
		Structure structure = null;
		Season season = null;
		RoomType roomType = null;
		Convention convention = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		//season = structure.findSeasonById(this.getSeasonId());
		season = this.getSeasonService().findSeasonById(this.getSeasonId());
		roomType = structure.findRoomTypeById(this.getRoomTypeId());
		convention = structure.findConventionById(this.getConventionId());
		
		this.setPriceList(structure.findRoomPriceListBySeasonAndRoomTypeAndConvention(season, roomType, convention));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/updateRoomPriceListItems",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				})
		})
	})
	public String updateRoomPriceListItems(){
		User user = null;
		Structure structure = null;
		RoomPriceList oldRoomPriceList = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		oldRoomPriceList = structure.findRoomPriceListById(this.getPriceList().getId());
		
		for (int i = 0; i < oldRoomPriceList.getItems().size(); i++) {
			oldRoomPriceList.updateItem(this.getPriceList().getItems().get(i));
		}
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription("Price List Items updated successfully");
		return SUCCESS;		
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
	public RoomPriceList getPriceList() {
		return priceList;
	}
	public void setPriceList(RoomPriceList priceList) {
		this.priceList = priceList;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public Integer getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Integer roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public Integer getConventionId() {
		return conventionId;
	}
	public void setConventionId(Integer conventionId) {
		this.conventionId = conventionId;
	}

	public SeasonService getSeasonService() {
		return seasonService;
	}

	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}
	
	
}