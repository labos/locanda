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