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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import model.Booking;
import model.RoomType;
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
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
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
				@Result(name="success",location="/WEB-INF/jsp/roomPriceLists.jsp")
		}) 
	})
	public String goFindAllRoomPriceLists() {
		return SUCCESS;
	}
	
	@Actions(
			@Action(value = "/toBlankPage", results = { 
					@Result(name = "success", location = "/WEB-INF/jsp/layout/blank.jsp")}
			)
		)
		public String toBlankPage() {
			return SUCCESS;
		}
	
	@Actions({
		@Action(value="/findAllRoomPriceLists",results = {
				@Result(type ="json",name="success", params={"root","treeNodes"}),
				@Result(type ="json",name="error", params={"excludeProperties","session"
				}),
				@Result(name="input", location = "/WEB-INF/jsp/validationError.jsp")
		})
	})
	public String findAllRoomPriceLists() {
		Set<Integer> years = null; 
		ServletContext context = null;
		String webappPath = null;
		
		years = new HashSet<Integer>();
		context = ServletActionContext.getServletContext();
		webappPath = context.getContextPath();
		
		for (Season eachSeason : this.getSeasonService().findSeasonsByIdStructure(this.getIdStructure())) {				//Building the Set with all years
			years.add(eachSeason.getYear());
		}
		for (Integer eachYear : years) {								//Building level-1 nodes - years
			this.getTreeNodes().add(TreeNode.buildNode(eachYear.toString()));
		}
		for (TreeNode eachNode1 : this.getTreeNodes()) {				//For each year, building level-2 nodes - seasons
			List<Season> perYearSeasons = this.getSeasonService().findSeasonsByYear(this.getIdStructure(),Integer.parseInt(eachNode1.getData().getTitle()));	//All seasons for that particular year
			for (Season eachYearSeason : perYearSeasons) {
				if (this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure()).size() == 0) {			//Without room types, click on a season node must link to a blank page
					String href = webappPath + "/toBlankPage";
					eachNode1.buildChild(eachYearSeason.getName(), eachYearSeason.getId(), href);
				} else {
					eachNode1.buildChild(eachYearSeason.getName(), eachYearSeason.getId());
				}	
			}
			for (TreeNode eachNode2 : eachNode1.getChildren()) {		//For each season, building level-3 nodes - room types
				for (RoomType eachRoomType : this.getRoomTypeService().findRoomTypesByIdStructure(this.getIdStructure())) {
					if (this.getConventionService().findConventionsByIdStructure(this.getIdStructure()).size() == 0) {	//Without conventions, click on a room type node must link to a blank page
						String href = webappPath + "/toBlankPage";
						eachNode2.buildChild(eachRoomType.getName(), eachRoomType.getId(), href);
					}else {
						eachNode2.buildChild(eachRoomType.getName(), eachRoomType.getId());
					}	
				}
					for (TreeNode eachNode3 : eachNode2.getChildren()) {//For each room type, building level-4 nodes - conventions
						for (Convention aConvention : this.getConventionService().findConventionsByIdStructure(this.getIdStructure())) {
							String href = webappPath + "/findRoomPriceListItems" +
							"?seasonId=" + eachNode2.getAttr().get("id") + 
							"&roomTypeId=" + this.getRoomTypeService().findRoomTypeByIdStructureAndName(this.getIdStructure(),eachNode3.getData().getTitle()).getId() +
							"&conventionId=" + aConvention.getId();
							eachNode3.buildChild(aConvention.getName(), aConvention.getId(), href);
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
				@Result(name="success",location="/WEB-INF/jsp/contents/roomPriceList_table.jsp")
				}),
		@Action(value="/findRoomPriceListItemsJson",results = {
				@Result(type ="json",name="success", params={"root","priceList"})
		})
	})
	public String findRoomPriceListItems() {
		this.setPriceList(this.getRoomPriceListService().findRoomPriceListByIdStructureAndIdSeasonAndIdRoomTypeAndIdConvention(
				this.getIdStructure(), this.getSeasonId(), this.getRoomTypeId(), this.getConventionId()));
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/updateRoomPriceListItems",results = {
				@Result(type ="json",name="success", params={"root","message"})
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