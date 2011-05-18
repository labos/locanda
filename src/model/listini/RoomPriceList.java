package model.listini;

import java.util.ArrayList;
import java.util.List;

import model.RoomType;

public class RoomPriceList {
	private Integer id;
	private RoomType roomType;
	private Season season;
	private Convention convention;
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
	
}
