package struts.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class CustomDoubleTypeConverter extends StrutsTypeConverter {

	public Object convertFromString(Map context, String[] values, Class toClass) {
		String userString = values[0];
		Double newDouble = Double.parseDouble(userString);
		return newDouble;
	}

	public String convertToString(Map context, Object arg1) {
		Double convertedDouble = (Double) arg1;
		String userString = convertedDouble.toString();
		return userString;
	}
	
}
