package model.listini;

public class ItemListinoCamera {
	private Integer id;
	private Integer numGuests;
	private Integer[] prices = new Integer[7]; 
	//prezzo lun = prices[0]
	//
	//prezzo sabato = prices[5]
	//prezzo domenica = prices[6]
	
	
	
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
	public Integer[] getPrices() {
		return prices;
	}
	public void setPrices(Integer[] prices) {
		this.prices = prices;
	}
	
	

}
