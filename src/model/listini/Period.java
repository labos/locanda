package model.listini;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;



public class Period implements Serializable{
	private Integer id = null;
	private Date startDate;
	private Date endDate;
	private Integer id_season;
	
	
	public Boolean includesDate(Date date){
		
		if((DateUtils.truncatedCompareTo(date,startDate, Calendar.DAY_OF_MONTH) >= 0) &&
				(DateUtils.truncatedCompareTo(date, endDate, Calendar.DAY_OF_MONTH) <= 0)	){
			return true;
		}		
		return false;
	}	
	
	public Boolean checkDates() {
		Boolean ret = true;

		if (DateUtils.truncatedCompareTo(this.getEndDate(), this.getStartDate(),
				Calendar.DAY_OF_MONTH) <= 0) {
			return false;
		}
		return ret;
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
