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

import model.Documento;
import model.questura.Comune;
import model.questura.Nazione;

import javax.xml.bind.annotation.XmlRootElement;



import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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
	private String birthPlace;
	@Field
	private String email;
	@Field
	private String phone;
	@Field
	private String address;
	@Field
	private String country;
	@Field
	private String zipCode;
	@Field
	private String notes;
	@Field
	private String idNumber;
	@Field
	private Integer id_structure;

	
	
	private Comune comuneDiNascita; //comune e prov 
	
	private Nazione statoNascita; //
	
	private Nazione cittadinanza;
	private Integer id_cittadinanza; 
	
	private Comune residenza; //comune e prov
	
	private Nazione statoResidenza;
	
	
	
	
	
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
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public Nazione getStatoNascita() {
		return statoNascita;
	}
	public void setStatoNascita(Nazione statoNascita) {
		this.statoNascita = statoNascita;
	}
	public Nazione getCittadinanza() {
		return cittadinanza;
	}
	public void setCittadinanza(Nazione cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
	public Comune getResidenza() {
		return residenza;
	}
	public void setResidenza(Comune residenza) {
		this.residenza = residenza;
	}
	public Nazione getStatoResidenza() {
		return statoResidenza;
	}
	public void setStatoResidenza(Nazione statoResidenza) {
		this.statoResidenza = statoResidenza;
	}
	public Comune getComuneDiNascita() {
		return comuneDiNascita;
	}
	public void setComuneDiNascita(Comune comuneDiNascita) {
		this.comuneDiNascita = comuneDiNascita;
	}
	public Integer getId_cittadinanza() {
		return id_cittadinanza;
	}
	public void setId_cittadinanza(Integer id_cittadinanza) {
		this.id_cittadinanza = id_cittadinanza;
	}
	
	
	
}