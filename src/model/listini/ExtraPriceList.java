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

import model.Extra;
import model.RoomType;

public class ExtraPriceList implements Serializable{
	
	private Integer id;
	
	private RoomType roomType;
	private Season season;
	private Convention convention;
	private List<ExtraPriceListItem> items;
	private Integer id_roomType;
	private Integer id_season;
	private Integer id_convention;
	private Integer id_structure;
	
	
	public ExtraPriceList(){
		this.setItems(new ArrayList<ExtraPriceListItem>());
	}
	
	public Boolean addItem(ExtraPriceListItem anItem){
		return this.getItems().add(anItem);
	}
	
	public Boolean removeItem(ExtraPriceListItem anItem){
		return this.getItems().remove(anItem);
	}
	
	public ExtraPriceListItem findItemById(Integer id){
		for(ExtraPriceListItem each: this.getItems()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}
	
	public Boolean updateItem(ExtraPriceListItem anItem){
		ExtraPriceListItem oldItem = null;
		
		oldItem = this.findItemById(anItem.getId());
		if(oldItem == null){
			return false;			
		}
		oldItem.setPrice(anItem.getPrice());
		return true;
	}
	
	public Double findExtraPrice(Extra extra){
		Double ret = 0.0;
		
		for(ExtraPriceListItem each: this.getItems()){
			if(each.getId_extra().equals(
					extra.getId())){
				return each.getPrice();
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
	public List<ExtraPriceListItem> getItems() {
		return items;
	}
	public void setItems(List<ExtraPriceListItem> items) {
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