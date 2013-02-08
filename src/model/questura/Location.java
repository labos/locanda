package model.questura;

import java.util.Date;

public interface Location {

	public Integer getPoliceCode();
	public void setPoliceCode(Integer code);
	public String getDescription();
	public void setDescription(String description);
	public String getProvince();
	public void setProvince(String province);
	public Date getExpiryDate();
	public void setExpiryDate(Date date);
	
}
