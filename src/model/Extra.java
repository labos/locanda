package model;

public class Extra {
	
	private Integer id;
	private String name;
	private String timePriceType;
	private String resourcePriceType;
	private String description;
	
	
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
		Extra other = (Extra) obj;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimePriceType() {
		return timePriceType;
	}
	public void setTimePriceType(String timePriceType) {
		this.timePriceType = timePriceType;
	}
	public String getResourcePriceType() {
		return resourcePriceType;
	}
	public void setResourcePriceType(String resourcePriceType) {
		this.resourcePriceType = resourcePriceType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
