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
package struts.interceptor;

import java.util.Map;

import model.User;
import model.UserAware;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserAwareInterceptor implements Interceptor{

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		UserAware action = null;
		Map<String, Object> session = null;
		User user = null;
		
		if(invocation.getAction() instanceof UserAware){
			session = invocation.getInvocationContext().getSession();
			user = (User) session.get("user");
			if(user == null){
				return "notLogged";
			}else{
				action = (UserAware)invocation.getAction();
				action.setIdStructure(user.getStructure().getId());
			}
		}
		return invocation.invoke();
	}
	
}