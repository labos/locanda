/**
 * 
 */
package utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;


public final class I18nUtils {
	private static Logger logger = Logger.getLogger(Logger.class);

    // private constructor to avoid unnecessary instantiation of the class
    private I18nUtils() {
    }

	public static String getProperty(String key)
    {
        String value = null;
        String lang = null;
        
        Object langObject = ActionContext.getContext().getSession().get("WW_TRANS_I18N_LOCALE");
        lang = ( langObject == null)? ActionContext.getContext().getLocale().getLanguage() : langObject.toString();
        
        ResourceBundle resourceBundle = ResourceBundle.getBundle("global_" + lang); 
		        
        try
        {
            value = resourceBundle.getString(key);
        }
        catch (MissingResourceException missingResourceException)
        {
                    logger.info("Resource Bundle not found " +  missingResourceException.getMessage());
        }
        return value;
    }

}
