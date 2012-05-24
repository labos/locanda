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
package model.listini;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)

public class Period implements Serializable{
	
	private Integer id = null;
	private Date startDate;
	private Date endDate;
	private Integer id_season;
	
	public Boolean checkDates() {
		Boolean startDateBeforeEndDate = DateUtils.truncatedCompareTo(this.getEndDate(), this.getStartDate(), Calendar.DAY_OF_MONTH) >= 0;
		Boolean equalYears = this.getStartYear().equals(this.getEndYear());
		Boolean ret = true;
		
		if (!startDateBeforeEndDate || !equalYears) {
			ret = false;
		}
		return ret;
	}
	
	public Integer getStartYear() {
		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(this.getStartDate());
		return calendar.get(Calendar.YEAR);
	}
	
	public Integer getEndYear() {
		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(this.getEndDate());
		return calendar.get(Calendar.YEAR);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getId_season() {
		return id_season;
	}
	public void setId_season(Integer id_season) {
		this.id_season = id_season;
	}	
	
}