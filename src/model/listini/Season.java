package model.listini;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Season {
	private Integer id;
	private String name;
	private Integer year;	
	private List<Period> periods;
	
	public Season(){
		this.setPeriods(new ArrayList<Period>());
	}
	
	public Boolean addPeriod(Period aPeriod){
		return this.getPeriods().add(aPeriod);
	}
	
	public Boolean includesDate(Date date){
		for(Period each: this.getPeriods()){
			if(each.includesDate(date)){
				return true;
			}
		}		
		return false;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(List<Period> periods) {
		this.periods = periods;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
