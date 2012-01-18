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
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomType implements Serializable{
	
	@Field
	private Integer id;
	@Field
	private String name;
	@Field
	private Integer maxGuests;
	@Field
	private String notes;
	private List<Image> images;
	private List<Facility> facilities;
	@Field
	private Integer id_structure;
	
	
	public RoomType(){
		this.images = new ArrayList<Image>();
		this.facilities = new ArrayList<Facility>();
	}
	
	public Boolean addRoomTypeImage(Image roomTypeImage){
		return this.getImages().add(roomTypeImage);
	}
	
	public boolean deleteImage(Image aImage) {
		return this.getImages().remove(aImage);
	}
	
	public Boolean addRoomTypeFacility(Facility roomTypeFacility){
		return this.getFacilities().add(roomTypeFacility);
	}
	
	public boolean deleteFacility(Facility roomTypeFacility) {
		return this.getFacilities().remove(roomTypeFacility);
	}
	
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
		RoomType other = (RoomType) obj;
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
	public Integer getMaxGuests() {
		return maxGuests;
	}
	public void setMaxGuests(Integer maxGuests) {
		this.maxGuests = maxGuests;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	public List<Facility> getFacilities() {
		return facilities;
	}
	public void setFacilities(List<Facility> facilityList) {
		this.facilities = facilityList;
	}
	public Integer getId_structure() {
		return id_structure;
	}
	public void setId_structure(Integer id_structure) {
		this.id_structure = id_structure;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}