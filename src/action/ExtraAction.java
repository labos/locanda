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

import java.util.List;
import java.util.Map;

import model.Extra;
import model.Structure;
import model.User;
import model.UserAware;
import model.internal.Message;
import model.listini.ExtraPriceList;
import model.listini.ExtraPriceListItem;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
import service.ExtraPriceListService;
import service.ExtraService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/homeNotLogged.jsp")
public class ExtraAction extends ActionSupport implements SessionAware,UserAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private List<Extra> extras = null;
	private Extra extra = null;
	private Integer idStructure;
	
	

	@Autowired
	private ExtraService extraService = null;
	@Autowired
	private ExtraPriceListService extraPriceListService = null;
	@Autowired
	private BookingService bookingService = null;
	
	
	@Actions({
		@Action(value="/findAllExtras",results = {
				@Result(name="success",location="/extras.jsp")
		}) 
	})
	public String findAllExtras() {
		List<Extra> extras = null;
				
		extras = this.getExtraService().findExtrasByIdStructure(this.getIdStructure());
		this.setExtras(extras);
		
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/goUpdateExtra",results = {
				@Result(name="success",location="/extra_edit.jsp")
		})		
	})
	public String goUpdateExtra() {
		
		Extra extra = null;
		
		
		extra = this.getExtraService().findExtraById(this.getExtra().getId());
		this.setExtra(extra);
		
		return SUCCESS;
	}
		
	@Actions({
		@Action(value="/saveUpdateExtra",results = {
				@Result(type ="json",name="success", params={
						"root","message"
				}),
				
				@Result(name = "input", location = "/validationError.jsp"),
				@Result(type ="json",name="error", params={
						"root","message"
				})
		})
	})
	public String saveUpdateExtra() {
		
		Extra oldExtra = null;
		
	
		
		oldExtra = this.getExtraService().findExtraById(this.getExtra().getId());
		this.getExtra().setId_structure(this.getIdStructure());
		if(oldExtra == null){
			//Si tratta di un add			
			this.getExtraService().insertExtra(this.getExtra());
			
			this.buildExtraPriceListFromExtra();
			
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("extraAddSuccessAction"));
			return SUCCESS;
		}else{
			//Si tratta di un update
			this.getExtraService().updateExtra(this.getExtra());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("extraUpdateSuccessAction"));
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
		
		Integer count = 0;
		Integer id_extra = 0;
		
		
		
		id_extra = this.getExtra().getId();
		
		if(this.getBookingService().countBookingsByIdExtra(id_extra) > 0){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription("Non è possibile cancellare l'extra perchè esistono booking associati a questo extra");
			return "error";
		}
		
		count = this.getExtraService().deleteExtra(id_extra);
		if(count > 0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("extraDeleteSuccessAction"));
			return "success";
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("extraDeleteSuccessAction"));
			return "error";
		}
		
	}
	
	public void buildExtraPriceListFromExtra() {
		
		ExtraPriceListItem newExtraPriceListItem = null;
		
		
		
		for (ExtraPriceList eachPriceList : this.getExtraPriceListService().findExtraPriceListsByIdStructure(this.getIdStructure())) {
			newExtraPriceListItem = new ExtraPriceListItem();
			//newExtraPriceListItem.setId(structure.nextKey());
			//newExtraPriceListItem.setExtra(this.getExtra());
			newExtraPriceListItem.setId_extra(this.getExtra().getId());
			newExtraPriceListItem.setPrice(0.0);
			newExtraPriceListItem.setId_extraPriceList(eachPriceList.getId());
			//eachPriceList.addItem(newExtraPriceListItem);
			this.getExtraPriceListService().insertExtraPriceListItem(newExtraPriceListItem);
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
	public ExtraService getExtraService() {
		return extraService;
	}
	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}
	public ExtraPriceListService getExtraPriceListService() {
		return extraPriceListService;
	}
	public void setExtraPriceListService(ExtraPriceListService extraPriceListService) {
		this.extraPriceListService = extraPriceListService;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}	
	public Integer getIdStructure() {
		return idStructure;
	}

	public void setIdStructure(Integer idStructure) {
		this.idStructure = idStructure;
	}
	
	
}