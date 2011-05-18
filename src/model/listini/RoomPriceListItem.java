package model.listini;

public class RoomPriceListItem {
	private Integer id;
	private Integer numGuests;
	private Double[] prices = new Double[7]; 
	
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
		

}
