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
import java.util.ArrayList;
import java.util.List;

import model.RoomType;

public class RoomPriceList implements Serializable{
	
	private Integer id;
	
	
	private RoomType roomType;
	private Season season;
	private Convention convention;
	private List<RoomPriceListItem> items;
	private Integer id_roomType;
	private Integer id_season;
	private Integer id_convention;
	private Integer id_structure;
	
	
	public RoomPriceList(){
		this.setItems(new ArrayList<RoomPriceListItem>());
	}
	
	public Boolean addItem(RoomPriceListItem anItem){
		return this.getItems().add(anItem);
	}
	
	public Boolean removeItem(RoomPriceListItem anItem){
		return this.getItems().remove(anItem);
	}
	
	public RoomPriceListItem findItemById(Integer id){
		for(RoomPriceListItem each: this.getItems()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}
	
	public Boolean updateItem(RoomPriceListItem anItem){
		RoomPriceListItem oldItem = null;
		oldItem = this.findItemById(anItem.getId());
		if(oldItem == null){
			return false;			
		}
		oldItem.updatePrices(anItem);		
		//oldItem.setPrices(anItem.getPrices());
		return true;
	}
	
	public Double findRoomPrice(Integer numGuests, Integer dayOfWeek){
		Double ret = 0.0;
		for(RoomPriceListItem each: this.getItems()){
			if(each.getNumGuests().equals(numGuests)){
				return each.getPrice(dayOfWeek);
			}
		}
		return ret;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public RoomType getRoomType() {
		return roomType;
	}
	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	public Convention getConvention() {
		return convention;
	}
	public void setConvention(Convention convention) {
		this.convention = convention;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public List<RoomPriceListItem> getItems() {
		return items;
	}
	public void setItems(List<RoomPriceListItem> items) {
		this.items = items;
	}
	public Integer getId_roomType() {
		return id_roomType;
	}
	public void setId_roomType(Integer id_roomType) {
		this.id_roomType = id_roomType;
	}
	public Integer getId_season() {
		return id_season;
	}
	public void setId_season(Integer id_season) {
		this.id_season = id_season;
	}
	public Integer getId_convention() {
		return id_convention;
	}
	public void setId_convention(Integer id_convention) {
		this.id_convention = id_convention;
	}
	public Integer getId_structure() {
		return id_structure;
	}
	public void setId_structure(Integer id_structure) {
		this.id_structure = id_structure;
	}
	
}