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
import java.util.List;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Structure implements Serializable {
	
	@Field
	private Integer id;
	@Field
	private String name;
	@Field
	private String address;
	@Field
	private String city;
	@Field
	private String country;
	@Field
	private String zipCode;
	@Field
	private String phone;
	@Field
	private String fax;
	@Field
	private String email;
	@Field
	private String url;
	@Field
	private String notes;
	@Field
	private Integer id_user;
	@Field
	private String mobile;
	@Field
	private String taxNumber;
	
	private List<Image> images;
	private List<Facility> facilities;
	
	
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}
	public Integer getId_user() {
		return id_user;
	}
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTaxNumber() {
		return taxNumber;
	}
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	
	
}