package model.listini;

import java.io.Serializable;

import model.Extra;

public class ExtraPriceListItem implements Serializable{
	private Integer id;
	private Extra extra;
	private Double price;
	private Integer id_extra;
	private Integer id_extraPriceList;
	
	
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
		ExtraPriceListItem other = (ExtraPriceListItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Extra getExtra() {
		return extra;
	}
	public void setExtra(Extra extra) {
		this.extra = extra;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getId_extra() {
		return id_extra;
	}
	public void setId_extra(Integer id_extra) {
		this.id_extra = id_extra;
	}
	public Integer getId_extraPriceList() {
		return id_extraPriceList;
	}
	public void setId_extraPriceList(Integer id_extraPriceList) {
		this.id_extraPriceList = id_extraPriceList;
	}	
	
	

}
