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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import model.Structure;
import model.User;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
import service.ConventionService;
import service.ExtraPriceListService;
import service.ExtraService;
import service.GuestService;
import service.RoomPriceListService;
import service.RoomService;
import service.RoomTypeService;
import service.SeasonService;
import service.StructureService;
import service.UserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "default")
public class LoginAction extends ActionSupport implements SessionAware {
	private Map<String, Object> session = null;
	private String email = null;
	private String password;
	@Autowired
	private SeasonService seasonService = null;
	@Autowired
	private ExtraService extraService = null;
	@Autowired
	private GuestService guestService = null;
	@Autowired
	private StructureService structureService = null;
	@Autowired
	private BookingService bookingService = null;
	@Autowired
	private RoomTypeService roomTypeService = null;
	@Autowired
	private RoomService roomService = null;
	@Autowired
	private ConventionService conventionService = null;
	@Autowired
	private RoomPriceListService roomPriceListService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
	private UserService userService = null;

	@Actions(value = { @Action(value = "/login", results = {
			@Result(name = "input", location = "/WEB-INF/jsp/login.jsp"),
			@Result(name = "loginSuccess", location = "/WEB-INF/jsp/homeLogged.jsp"),
			@Result(name = "loginError", location = "/WEB-INF/jsp/login.jsp"),
			@Result(name = "nullpointer", location = "/WEB-INF/jsp/login.jsp") 
			}) 
	})
	public String execute() {
		String ret = null;
		User user = null;
		Structure structure = null;
		Locale locale = null;
		SimpleDateFormat sdf = null;
		String datePattern = null;
		
		try{
			user = this.getUserService().findUserByEmail(this.getEmail().trim());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if (user != null && user.getPassword().equals(this.getPassword().trim())) {
			structure = this.getStructureService().findStructureByIdUser(user.getId());
			if(structure == null){
				this.initializeStructureForUser(user.getId());
				structure = this.getStructureService().findStructureByIdUser(user.getId());
				if(structure == null){
					this.getSession().put("user", null);
					ret = "loginError";
				}
			}
			user.setStructure(structure);			
			this.getSession().put("user", user);
			
			locale = this.getLocale();
			sdf = (SimpleDateFormat) DateFormat.getDateInstance(
					DateFormat.SHORT, locale);
			datePattern = sdf.toPattern();
			this.getSession().put("datePattern", datePattern);
			ret = "loginSuccess";
		} else {
			this.getSession().put("user", null);
			addActionError(getText("loginError"));
			ret = "loginError";
		}
		return ret;
	}
	
	private Integer initializeStructureForUser(Integer id_user){
		Integer ret = 0;
		Structure structure = null;
		
		structure = new Structure();
		structure.setName("Locanda");
		structure.setEmail("labopensource@gmail.com");
		structure.setPhone("+39 070 92432684");
		structure.setNotes("Example structure. Please overwrite data with your own!");
		structure.setId_user(id_user);
		
		ret = this.getStructureService().insertStructure(structure);
		return ret;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public SeasonService getSeasonService() {
		return seasonService;
	}
	public void setSeasonService(SeasonService seasonService) {
		this.seasonService = seasonService;
	}
	public ExtraService getExtraService() {
		return extraService;
	}
	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}
	public GuestService getGuestService() {
		return guestService;
	}
	public void setGuestService(GuestService guestService) {
		this.guestService = guestService;
	}
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	public BookingService getBookingService() {
		return bookingService;
	}
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	public RoomTypeService getRoomTypeService() {
		return roomTypeService;
	}
	public void setRoomTypeService(RoomTypeService roomTypeService) {
		this.roomTypeService = roomTypeService;
	}
	public RoomService getRoomService() {
		return roomService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public ConventionService getConventionService() {
		return conventionService;
	}
	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
	}
	public RoomPriceListService getRoomPriceListService() {
		return roomPriceListService;
	}
	public void setRoomPriceListService(
			RoomPriceListService roomPriceListService) {
		this.roomPriceListService = roomPriceListService;
	}
	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}
	public void setExtraPriceListService(
			ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}