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

import model.Structure;
import model.Studente;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import service.StudenteService;
import service.StudenteServiceImpl;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class StudenteAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private List<Studente> studenti;
	@Autowired
	StudenteService studenteService = null;
	
	@Actions({
		@Action(value="/findAllStudenti",results = {
				@Result(name="success",location="/studenti.jsp")
		}) 
		
	})
	public String findAllStudenti(){
		
		
		this.setStudenti(this.getStudenteService().findAll());
		return SUCCESS;		
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public List<Studente> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<Studente> studenti) {
		this.studenti = studenti;
	}

	
	public StudenteService getStudenteService() {
		return studenteService;
	}

	public void setStudenteService(StudenteService studenteService) {
		this.studenteService = studenteService;
	}
	
	
	

}
