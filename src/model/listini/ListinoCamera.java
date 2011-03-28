package model.listini;

import java.util.ArrayList;
import java.util.List;

public class ListinoCamera {
	private Integer id;
	private String roomType;
	private Season season;
	private Agevolazione agevolazione;
	private List<ItemListinoCamera> items;
	
	public ListinoCamera(){
		this.setItems(new ArrayList<ItemListinoCamera>());
	}
	
	public Boolean addItem(ItemListinoCamera anItem){
		return this.getItems().add(anItem);		
	}
	
	public Boolean removeItem(ItemListinoCamera anItem){
		return this.getItems().remove(anItem);		
	}
	
	public ItemListinoCamera findItemById(Integer id){
		for(ItemListinoCamera each: this.getItems()){
			if(each.getId().equals(id)){
				return each;
			}
		}
		return null;
	}
	public Boolean updateItem(ItemListinoCamera anItem){
		ItemListinoCamera oldItem = null;
		
		oldItem = this.findItemById(anItem.getId());
		if(oldItem == null){
			return false;			
		}
		oldItem.setNumGuests(anItem.getNumGuests());
		oldItem.setPrices(anItem.getPrices());
		return true;
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
	public Agevolazione getAgevolazione() {
		return agevolazione;
	}
	public void setAgevolazione(Agevolazione agevolazione) {
		this.agevolazione = agevolazione;
	}
	public List<ItemListinoCamera> getItems() {
		return items;
	}
	public void setItems(List<ItemListinoCamera> items) {
		this.items = items;
	}
	
	
	
	
	

}
