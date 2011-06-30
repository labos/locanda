package model.listini;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Season implements Serializable{
	private Integer id;
	private String name;
	private Integer year;	
	private List<Period> periods;
	private Integer id_structure;
	
	public Season(){
		this.setPeriods(new ArrayList<Period>());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Season other = (Season) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	public Integer getId_structure() {
		return id_structure;
	}
	public void setId_structure(Integer id_structure) {
		this.id_structure = id_structure;
	}
	
}