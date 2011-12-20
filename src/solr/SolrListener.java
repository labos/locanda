package solr;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.solr.core.CoreContainer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SolrListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
				
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = null;
		WebApplicationContext springContext = null;
		CoreContainer multicoreContainer = null;
		
		servletContext = servletContextEvent.getServletContext();
		springContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		multicoreContainer = (CoreContainer)springContext.getBean("multiCoreContainer");
		
		if(multicoreContainer!=null){
			multicoreContainer.shutdown();
		}
	}

}
