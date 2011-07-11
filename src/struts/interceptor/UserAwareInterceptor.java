package struts.interceptor;

import java.util.Map;

import model.User;
import model.UserAware;

import com.opensymphony.xwork2.Action;
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
