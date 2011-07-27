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

public class RoomPriceListItem implements Serializable{
	
	private Integer id;
	
	private Integer numGuests;
	private Double priceSunday = 0.0;//dayOfWeek 1
	private Double priceMonday = 0.0;//dayOfWeek 2
	private Double priceTuesday = 0.0;//dayOfWeek 3
	private Double priceWednesday = 0.0;//dayOfWeek 4
	private Double priceThursday = 0.0;//dayOfWeek 5
	private Double priceFriday = 0.0;//dayOfWeek 6
	private Double priceSaturday = 0.0;//dayOfWeek 7
	private Integer id_roomPriceList;
	
		
	public void updatePrices(RoomPriceListItem anotherItem){
		this.setPriceMonday(anotherItem.getPriceMonday());
		this.setPriceTuesday(anotherItem.getPriceTuesday());
		this.setPriceWednesday(anotherItem.getPriceWednesday());
		this.setPriceThursday(anotherItem.getPriceThursday());
		this.setPriceFriday(anotherItem.getPriceFriday());
		this.setPriceSaturday(anotherItem.getPriceSaturday());
	}
	
	public Double getPrice(Integer dayOfWeek){
		//dayOfWeek
		//1 sunday
		//2 monday
		//3 tuesady
		//4 wednesday
		//5 thursady
		//6 friday
		//7 saturday
		Double ret = 0.0;
		
		if(dayOfWeek.equals(1)){
			return this.getPriceSunday();
		}
		if(dayOfWeek.equals(2)){
			return this.getPriceMonday();
		}
		if(dayOfWeek.equals(3)){
			return this.getPriceTuesday();
		}
		if(dayOfWeek.equals(4)){
			return this.getPriceWednesday();
		}
		if(dayOfWeek.equals(5)){
			return this.getPriceThursday();
		}
		if(dayOfWeek.equals(6)){
			return this.getPriceFriday();
		}
		if(dayOfWeek.equals(7)){
			return this.getPriceSaturday();
		}
		return ret;
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
		RoomPriceListItem other = (RoomPriceListItem) obj;
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
	public Integer getNumGuests() {
		return numGuests;
	}
	public void setNumGuests(Integer numGuests) {
		this.numGuests = numGuests;
	}
	public Double getPriceSunday() {
		return priceSunday;
	}
	public void setPriceSunday(Double priceSunday) {
		this.priceSunday = priceSunday;
	}
	public Double getPriceMonday() {
		return priceMonday;
	}
	public void setPriceMonday(Double priceMonday) {
		this.priceMonday = priceMonday;
	}
	public Double getPriceTuesday() {
		return priceTuesday;
	}
	public void setPriceTuesday(Double priceTuesday) {
		this.priceTuesday = priceTuesday;
	}
	public Double getPriceWednesday() {
		return priceWednesday;
	}
	public void setPriceWednesday(Double priceWednesday) {
		this.priceWednesday = priceWednesday;
	}
	public Double getPriceThursday() {
		return priceThursday;
	}
	public void setPriceThursday(Double priceThursday) {
		this.priceThursday = priceThursday;
	}
	public Double getPriceFriday() {
		return priceFriday;
	}
	public void setPriceFriday(Double priceFriday) {
		this.priceFriday = priceFriday;
	}
	public Double getPriceSaturday() {
		return priceSaturday;
	}
	public void setPriceSaturday(Double priceSaturday) {
		this.priceSaturday = priceSaturday;
	}
	public Integer getId_roomPriceList() {
		return id_roomPriceList;
	}
	public void setId_roomPriceList(Integer id_roomPriceList) {
		this.id_roomPriceList = id_roomPriceList;
	}
	
}