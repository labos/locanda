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
import java.util.List;
import java.util.Map;

import model.UserAware;
import model.internal.Message;
import model.listini.Convention;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.BookingService;
import service.ConventionService;
import service.StructureService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage( value="default")
@InterceptorRefs({
	@InterceptorRef("userAwareStack")    
})
@Result(name="notLogged", location="/WEB-INF/jsp/homeNotLogged.jsp")
public class ConventionAction extends ActionSupport implements SessionAware,UserAware{
	private Map<String, Object> session = null;
	private Message message = new Message();
	private List<Convention> conventions = null;
	private Convention convention = null;
	private Integer idStructure;	
	@Autowired 
	private StructureService structureService = null;
	@Autowired
	private ConventionService conventionService = null;
	@Autowired
	private BookingService bookingService = null;
	
	@Actions({
		@Action(value="/findAllConventions",results = {
				@Result(name="success",location="/WEB-INF/jsp/conventions.jsp")
		})
	})
	public String findAllConventions(){
		List<Convention> filteredConventions = null;
	
		filteredConventions = new ArrayList<Convention>();
		for(Convention each: this.getConventionService().findConventionsByIdStructure(this.getIdStructure())){
			if(!each.getActivationCode().equals("thisconventionshouldntneverberemoved")){
				filteredConventions.add(each);
			}
		}
		this.setConventions(filteredConventions);
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/goUpdateConvention",results = {
				@Result(name="success",location="/WEB-INF/jsp/convention_edit.jsp")
		})
	})
	public String goUpdateConvention() {
				
		this.setConvention(this.getConventionService().findConventionById(this.getConvention().getId())); 
		return SUCCESS;
	}
	
	@Actions({
		@Action(value="/saveUpdateConvention",results = {
				@Result(type ="json",name="success", params={"root","message"})
		})
	})
	public String saveUpdateConvention(){
		Convention oldConvention = null;
				
		oldConvention = this.getConventionService().findConventionById(this.getConvention().getId());
		if(oldConvention == null){
			//It's a new convention
			
			this.getConvention().setId_structure(this.getIdStructure());
			this.getConventionService().insertConvention(this.getConvention());
			//this.getStructureService().refreshPriceLists(structure);
			this.getStructureService().addPriceListsForConvention(this.getIdStructure(), this.getConvention().getId());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("conventionAddSuccessAction"));
			
		}else{
			//It's an existing convention
			this.getConvention().setId_structure(this.getIdStructure());
			this.getConventionService().updateConvention(this.getConvention());
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("conventionUpdateSuccessAction"));
		}
		return SUCCESS;		
	}
	
	@Actions({
		@Action(value="/deleteConvention",results = {
				@Result(type ="json",name="success", params={"root","message"}),
				@Result(type ="json",name="error", params={"root","message"})
		})
	})
	public String deleteConvention(){
		Integer count = 0;
		Integer id_convention;
		
		id_convention = this.getConvention().getId();
		count = this.getConventionService().deleteConvention(id_convention);
		
		if(this.getBookingService().countBookingsByIdConvention(id_convention) > 0){
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("conventionDeleteBookingError"));
			return ERROR;
		}
		if(count > 0){
			this.getMessage().setResult(Message.SUCCESS);
			this.getMessage().setDescription(getText("conventionDeleteSuccessAction"));
			return SUCCESS;
		}else{
			this.getMessage().setResult(Message.ERROR);
			this.getMessage().setDescription(getText("conventionDeleteErrorAction"));
			return ERROR;
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
	public StructureService getStructureService() {
		return structureService;
	}
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}
	public ConventionService getConventionService() {
		return conventionService;
	}
	public void setConventionService(ConventionService conventionService) {
		this.conventionService = conventionService;
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