package model.listini;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomPriceList {
	private Integer id;
	private String roomType;
	private Season season;
	private Convention agevolazione;
	private List<RoomPriceListItem> items;
	
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
		oldItem.setNumGuests(anItem.getNumGuests());
		oldItem.setPrices(anItem.getPrices());
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
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public Convention getAgevolazione() {
		return agevolazione;
	}
	public void setAgevolazione(Convention agevolazione) {
		this.agevolazione = agevolazione;
	}
	public List<RoomPriceListItem> getItems() {
		return items;
	}
	public void setItems(List<RoomPriceListItem> items) {
		this.items = items;
	}
	
	
	
	
	

}
