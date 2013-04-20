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
import java.util.Date;

import model.questura.HousedType;

import javax.xml.bind.annotation.XmlRootElement;



import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Housed implements Serializable{
	@Field
	private Integer id;
	@Field
	private Integer id_booking;
	private Guest guest;
	private Integer id_guest;
	private HousedType housedType;
	private Integer id_housedType;
	private Date checkInDate;
	private Date checkOutDate;
	
	private Boolean deleted = false;
	private Boolean touristTax = false;
	
	private Integer id_tourismType;
	private Integer id_transport;
	
	
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
		Housed other = (Housed) obj;
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
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public Integer getId_guest() {
		return id_guest;
	}
	public void setId_guest(Integer id_guest) {
		this.id_guest = id_guest;
	}
	public HousedType getHousedType() {
		return housedType;
	}
	public void setHousedType(HousedType type) {
		this.housedType = type;
	}
	public Integer getId_housedType() {
		return id_housedType;
	}
	public void setId_housedType(Integer id_housedType) {
		this.id_housedType = id_housedType;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date date) {
		this.checkInDate = date;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date date) {
		this.checkOutDate = date;
	}
	public Integer getId_booking() {
		return id_booking;
	}
	public void setId_booking(Integer id_booking) {
		this.id_booking = id_booking;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Boolean getTouristTax() {
		return touristTax;
	}
	public void setTouristTax(Boolean touristTax) {
		this.touristTax = touristTax;
	}
	public Integer getId_tourismType() {
		return id_tourismType;
	}
	public void setId_tourismType(Integer id_tourismType) {
		this.id_tourismType = id_tourismType;
	}
	public Integer getId_transport() {
		return id_transport;
	}
	public void setId_transport(Integer id_transport) {
		this.id_transport = id_transport;
	}
	
}