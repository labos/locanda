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
import model.UserAware;
import model.internal.Message;
import model.internal.TreeNode;
import model.listini.Convention;
import model.listini.RoomPriceList;
import model.listini.RoomPriceListItem;
import model.listini.Season;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.ConventionService;
import service.RoomPriceListService;
import service.RoomTypeService;
import service.SeasonService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/homeNotLogged.jsp")
public class RoomPriceListAction extends ActionSupport implements SessionAware,UserAware{
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
	private Integer idStructure;
	
	@Autowired
	private SeasonService seasonService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private ConventionService conventionService = null;
	
		
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
		
		Set<Integer> years = null; 
		ServletContext context = null;
		String webappPath = null;
		
		
		years = new HashSet<Integer>();
		context = ServletActionContext.getServletContext();
		webappPath = context.getContextPath();
		
		for (Season eachSeason : this.getSeasonService().findSeasonsByIdStructure(this.getIdStructure())) {			//costruisco il set con tutti gli anni
			years.add(eachSeason.getYear());
		}
		for (Integer eachYear : years) {							//costruisco i nodi di primo livello - gli anni
			this.getTreeNodes().add(TreeNode.buildNode(eachYear.toString()));
		}
		for (TreeNode eachNode1 : this.getTreeNodes()) {						//per ogni anno costruisco i nodi di secondo livello - le stagioni
			List<Season> perYearSeasons = this.getSeasonService().findSeasonsByYear(this.getIdStructure(),Integer.parseInt(eachNode1.getData().getTitle()));	//tutte le stagioni di quell'anno
			for (Season eachYearSeason : perYearSeasons) {
				if (this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure()).size() == 0) {	//senza roomTypes devo assegnare una pagina vuota al nodo delle Seasons
					String href = "jsp/layout/blank.jsp";
					eachNode1.buildChild(eachYearSeason.getName(), href);
				} else {
					eachNode1.buildChild(eachYearSeason.getName());
				}	
			}
			for (TreeNode eachNode2 : eachNode1.getChildren()) {		//per ogni stagione costruisco i nodi di terzo livello - i roomTypes
				for (RoomType eachRoomType : this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure())) {
					if (this.getConventionService().findConventionsByIdStructure(this.getIdStructure()).size() == 0) {	//senza Conventions devo assegnare una pagina vuota al nodo dei RoomTypes
						String href = "jsp/layout/blank.jsp";
						eachNode2.buildChild(eachRoomType.getName(), href);
					}else {
						eachNode2.buildChild(eachRoomType.getName());
					}	
				}
					for (TreeNode eachNode3 : eachNode2.getChildren()) {//per ogni roomType costruisco i nodi di quarto livello - le convenzioni
						for (Convention aConvention : this.getConventionService().findConventionsByIdStructure(this.getIdStructure())) {
							String href = webappPath + "/findRoomPriceListItems" +
							"?seasonId=" + this.getSeasonService().findSeasonByName(this.getIdStructure(),eachNode2.getData().getTitle()).getId() + 
							"&roomTypeId=" + this.getRoomTypeService().findRoomTypeByIdStructureAndName(this.getIdStructure(),eachNode3.getData().getTitle()).getId() +
							"&conventionId=" + aConvention.getId();
							eachNode3.buildChild(aConvention.getName(), href);
						}
					}			
			}
		}
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("priceListTreeSuccessAction"));
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
		
		
		this.setPriceList(this.getRoomPriceListService().findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
				this.getIdStructure(), this.getSeasonId(), this.getRoomTypeId(), this.getConventionId()));
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
		
		
		for(RoomPriceListItem each: this.getPriceList().getItems()){
			each.setId_roomPriceList(this.getPriceList().getId());
			this.getRoomPriceListService().updateRoomPriceListItem(each);
		}
		
		
		this.getMessage().setResult(Message.SUCCESS);
		this.getMessage().setDescription(getText("priceListUpdateSuccessAction"));
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
	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}
	public void setRoomPriceListService(RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}
	public ConventionService getConventionService() {
		return conventionService;
	}
	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}

	public Integer getIdStructure() {
		return idStructure;
	}

	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	
}