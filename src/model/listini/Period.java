package model.listini;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;



public class Period {
	private Integer id;
	private Date startDate;
	private Date endDate;
	
	
	public Boolean includesDate(Date date){
		
		if((DateUtils.truncatedCompareTo(date,startDate, Calendar.DAY_OF_MONTH) >= 0) &&
				(DateUtils.truncatedCompareTo(date, endDate, Calendar.DAY_OF_MONTH) <= 0)	){
			return true;
		}		
		return false;
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

}
