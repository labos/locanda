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

import model.questura.IdentificationType;
import model.questura.Municipality;
import model.questura.Country;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import utils.JsonDateDeserializer;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class Guest implements Serializable{
	@Field
	private Integer id;
	@Field
	private String firstName;
	@Field
	private String lastName;
	private String gender;
	private Date birthDate;
	@Field
	private String email;
	@Field
	private String phone;
	@Field
	private String address;
	@Field
	private String zipCode;
	@Field
	private String notes;
	@Field
	private Integer id_structure;

	private Country countryOfBirth;
	private Integer id_countryOfBirth;
	private Municipality municipalityOfBirth;
	private Integer id_municipalityOfBirth;
	private Country countryOfResidence;
	private Integer id_countryOfResidence;
	private Municipality municipalityOfResidence; 
	private Integer id_municipalityOfResidence;
	private Country citizenship;
	private Integer id_citizenship;
	
	@Field
	private String idNumber;
	private IdentificationType idType;
	private Integer id_idType;
	private Municipality idPlace;
	private Integer id_idPlace;
	
	
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
		Guest other = (Guest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Boolean canBeMember() {
		Boolean ret = true;
		if (this.id_countryOfBirth == 1 && 			//Born in Italy 
				(this.id_municipalityOfBirth == null || this.id_municipalityOfBirth.equals(0))
				) {
			ret = false;
		}
		if (this.id_countryOfResidence == 1 && 		//Lives in Italy
				(this.id_municipalityOfResidence == null || this.id_municipalityOfResidence.equals(0))
				) {
			ret = false;
		}
		return ret;
	}
	
	public Boolean canBeSingleOrLeader() {
		Boolean ret = true;
		if (this.getId_citizenship() == 1) {  	//Italian citizenship
			if (!this.canBeMember() || this.getId_idType() == null || this.getId_idPlace() == null || this.getIdNumber().equals("")) {
				ret = false;
			}
		}else { 								//foreign citizens
			if (!this.canBeMember() || this.getId_idType() == null || this.getIdNumber().equals("")) {
				ret = false;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Integer getId_structure() {
		return id_structure;
	}
	public void setId_structure(Integer id_Structure) {
		this.id_structure = id_Structure;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	@JsonDeserialize(using=JsonDateDeserializer.class)
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Country getCountryOfBirth() {
		return countryOfBirth;
	}
	public void setCountryOfBirth(Country countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
	public Municipality getMunicipalityOfBirth() {
		return municipalityOfBirth;
	}
	public void setMunicipalityOfBirth(Municipality municipalityOfBirth) {
		this.municipalityOfBirth = municipalityOfBirth;
	}
	public Country getCountryOfResidence() {
		return countryOfResidence;
	}
	public void setCountryOfResidence(Country countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}
	public Municipality getMunicipalityOfResidence() {
		return municipalityOfResidence;
	}
	public void setMunicipalityOfResidence(Municipality municipalityOfResidence) {
		this.municipalityOfResidence = municipalityOfResidence;
	}
	public Integer getId_countryOfBirth() {
		return id_countryOfBirth;
	}
	public void setId_countryOfBirth(Integer id_countryOfBirth) {
		this.id_countryOfBirth = id_countryOfBirth;
	}
	public Integer getId_municipalityOfBirth() {
		return id_municipalityOfBirth;
	}
	public void setId_municipalityOfBirth(Integer id_municipalityOfBirth) {
		this.id_municipalityOfBirth = id_municipalityOfBirth;
	}
	public Integer getId_countryOfResidence() {
		return id_countryOfResidence;
	}
	public void setId_countryOfResidence(Integer id_countryOfResidence) {
		this.id_countryOfResidence = id_countryOfResidence;
	}
	public Integer getId_municipalityOfResidence() {
		return id_municipalityOfResidence;
	}
	public void setId_municipalityOfResidence(Integer id_municipalityOfResidence) {
		this.id_municipalityOfResidence = id_municipalityOfResidence;
	}
	public Country getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(Country citizenship) {
		this.citizenship = citizenship;
	}
	public Integer getId_citizenship() {
		return id_citizenship;
	}
	public void setId_citizenship(Integer id_citizenship) {
		this.id_citizenship = id_citizenship;
	}
	public IdentificationType getIdType() {
		return idType;
	}
	public void setIdType(IdentificationType idType) {
		this.idType = idType;
	}
	public Municipality getIdPlace() {
		return idPlace;
	}
	public void setIdPlace(Municipality idPlace) {
		this.idPlace = idPlace;
	}
	public Integer getId_idType() {
		return id_idType;
	}
	public void setId_idType(Integer id_idType) {
		this.id_idType = id_idType;
	}
	public Integer getId_idPlace() {
		return id_idPlace;
	}
	public void setId_idPlace(Integer id_idPlace) {
		this.id_idPlace = id_idPlace;
	}
	
}