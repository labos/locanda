package model.listini;

import java.util.ArrayList;
import java.util.List;

import model.Extra;
import model.RoomType;

public class ExtraPriceList {
	private Integer id;
	private RoomType roomType;
	private Season season;
	private Convention convention;
	private List<ExtraPriceListItem> items;
	
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
			if(each.getExtra().equals(extra)){
				return each.getPrice();
			}
		}
		return ret;
	}
	
	public Double findExtraPrice(Extra extra){
		Double ret = 0.0;
		
		for(ExtraPriceListItem each: this.getItems()){
			if(each.getExtra().equals(extra)){
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
	
}