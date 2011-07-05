package model.listini;

public class RoomPriceListItem {
	private Integer id;
	private Integer numGuests;
	private Double[] prices = new Double[7]; 
	private Double priceSunday = 0.0;//dayOfWeek 1
	private Double priceMonday = 0.0;//dayOfWeek 2
	private Double priceTuesday = 0.0;//dayOfWeek 3
	private Double priceWednesday = 0.0;//dayOfWeek 4
	private Double priceThursday = 0.0;//dayOfWeek 5
	private Double priceFriday = 0.0;//dayOfWeek 6
	private Double priceSaturday = 0.0;//dayOfWeek 7
	
	
	//prezzo lunedi = prices[0]
	//prezzo martedi = prices[1]
	//prezzo mercoledi = prices[2]
	//prezzo giovedi = prices[3]
	//prezzo venerdi = prices[4]
	//prezzo sabato = prices[5]
	//prezzo domenica = prices[6]
	
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
	
	/*
	public Double getPrice(Integer dayOfWeek){
		//dayOfWeek
		//1 domenica
		//2 lunedi
		//3 martedi
		//4 mercoledi
		//5 giovedi
		//6 venerdi
		//7 sabato
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
	}*/
	
	public Double getPrice(Integer dayOfWeek){
		//dayOfWeek
		//1 domenica
		//2 lunedi
		//3 martedi
		//4 mercoledi
		//5 giovedi
		//6 venerdi
		//7 sabato
		Double ret = 0.0;
		
		if(dayOfWeek.equals(1)){
			return prices[6];
		}
		if(dayOfWeek.equals(2)){
			return prices[0];
		}
		if(dayOfWeek.equals(3)){
			return prices[1];
		}
		if(dayOfWeek.equals(4)){
			return prices[2];
		}
		if(dayOfWeek.equals(5)){
			return prices[3];
		}
		if(dayOfWeek.equals(6)){
			return prices[4];
		}
		if(dayOfWeek.equals(7)){
			return prices[5];
		}
		return ret;
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
	public Double[] getPrices() {
		return prices;
	}
	public void setPrices(Double[] prices) {
		this.prices = prices;
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
	
	
		

}
