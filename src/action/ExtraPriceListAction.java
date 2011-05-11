package action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import model.Booking;
import model.RoomType;
import model.Structure;
import model.User;
import model.internal.Message;
import model.internal.TreeNode;
import model.listini.Convention;
import model.listini.ExtraPriceList;
import model.listini.Season;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class ExtraPriceListAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private Booking booking = null;
	private List<Integer> bookingExtraIds = new ArrayList<Integer>();
	private Integer numNights;
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	private ExtraPriceList priceList = null;
	
	private Integer seasonId = null;
	private Integer roomTypeId = null;
	private Integer conventionId = null;
	
	
	@Actions({
		@Action(value="/goFindAllExtraPriceLists",results = {
				@Result(name="success",location="/extraPriceLists.jsp")
		}) 
		
	})
	public String goFindAllExtraPriceLists() {
		
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="/findAllExtraPriceLists",results = {
				@Result(type ="json",name="success", params={
						"root","treeNodes"
				} ),
				@Result(type ="json",name="error", params={
						"excludeProperties","session"
				} ),
				@Result(name="input", location = "/validationError.jsp" )
		})
		
	})
	public String findAllExtraPriceLists() {
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
			List<Season> perYearSeasons = structure.findSeasonsByYear(Integer.parseInt(eachNode1.getData().getTitle()));	//tutte le stagioni di quell'anno
			for (Season eachYearSeason : perYearSeasons) {
				eachNode1.buildChild(eachYearSeason.getName());
			}
			for (TreeNode eachNode2 : eachNode1.getChildren()) {		//per ogni stagione costruisco i nodi di terzo livello - i roomTypes
				for (RoomType eachRoomType : structure.getRoomTypes()) {
					eachNode2.buildChild(eachRoomType.getName());
				}
					for (TreeNode eachNode3 : eachNode2.getChildren()) {//per ogni roomType costruisco i nodi di quarto livello - le convenzioni
						Set<Convention> perRoomTypeConventions = new HashSet<Convention>();	//tutte le convenzioni associate a quel roomType in un certo listino
						perRoomTypeConventions = structure.findConventionsBySeasonAndRoomType(structure.findSeasonByName(eachNode2.getData().getTitle()), structure.findRoomTypeByName(eachNode3.getData().getTitle()));
						for (Convention eachRoomTypeConvention : perRoomTypeConventions) {
							String href = webappPath + "/findExtraPriceListItems" +
							"?seasonId=" + structure.findSeasonByName(eachNode2.getData().getTitle()).getId() + 
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
		@Action(value="/findExtraPriceListItems",results = {
				@Result(name="success",location="/jsp/contents/extraPriceList_table.jsp")
		}),
		@Action(value="/findExtraPriceListItemsJson",results = {
				@Result(type ="json",name="success", params={
						"root","priceList"
				})})
	})
	public String findExtraPriceListItems() {
		User user = null;
		Structure structure = null;
		Season season = null;
		RoomType roomType = null;
		Convention convention = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		
		season = structure.findSeasonById(this.getSeasonId());
		roomType = structure.findRoomTypeById(this.getRoomTypeId());
		convention = structure.findConventionById(this.getConventionId());
		
		this.setPriceList(structure.findExtraPriceListBySeasonAndRoomTypeAndConvention(season, roomType, convention));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/updateExtraPriceListItems",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				})
		})
	})
	public String updateExtraPriceListItems(){
		User user = null;
		Structure structure = null;
		ExtraPriceList oldExtraPriceList = null;
		
		user = (User)this.getSession().get("user");
		structure = user.getStructure();
		oldExtraPriceList = structure.findExtraPriceListById(this.getPriceList().getId());
		
		for (int i = 0; i < oldExtraPriceList.getItems().size(); i++) {
			oldExtraPriceList.updateItem(this.getPriceList().getItems().get(i));
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

	public ExtraPriceList getPriceList() {
		return priceList;
	}

	public void setPriceList(ExtraPriceList priceList) {
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

	
}