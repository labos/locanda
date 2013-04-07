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
package service;

import java.util.ArrayList;
import java.util.List;

import model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.mybatis.mappers.GuestMapper;

@Service
public class GuestServiceImpl implements GuestService{
	@Autowired
	private GuestMapper guestMapper;
	@Autowired
	private MunicipalityService municipalityService;
	@Autowired
	private CountryService countryService;
	@Autowired
	private IdentificationTypeService identificationTypeService;
	
	public List<Guest> findGuestsByIdStructure(Integer id_structure) {
		List<Guest> ret = null;
		
		ret = this.getGuestMapper().findGuestsByIdStructure(id_structure);
		for(Guest each: ret){
			this.retrieveLinkedObjectsFor(each);
		}
		return ret; 
	}
	
		
	@Override
	public List<Guest> findAll() {
		List<Guest> ret = null;
		
		ret = this.getGuestMapper().findAll();
		for(Guest each: ret){
			this.retrieveLinkedObjectsFor(each);
		}
		
		return ret; 
	}
	
	@Override
	public Guest findGuestById(Integer id) {	
		Guest ret = null;
		
		ret = this.getGuestMapper().findGuestById(id);		
		this.retrieveLinkedObjectsFor(ret);
		
		return ret;
	}
	
	private void retrieveLinkedObjectsFor(Guest guest){
		guest.setMunicipalityOfBirth(this.getMunicipalityService().findById(guest.getId_municipalityOfBirth()));
		guest.setCountryOfBirth(this.getCountryService().findById(guest.getId_countryOfBirth()));
		
		guest.setMunicipalityOfResidence(this.getMunicipalityService().findById(guest.getId_municipalityOfResidence()));
		guest.setCountryOfResidence(this.getCountryService().findById(guest.getId_countryOfResidence()));
		
		guest.setCitizenship(this.getCountryService().findById(guest.getId_citizenship()));
		
		guest.setIdType(this.getIdentificationTypeService().findById(guest.getId_idType()));
		guest.setIdPlace(this.getMunicipalityService().findById(guest.getId_idPlace()));
		
	}
	
	@Override
	public Integer insertGuest(Guest guest) {
		return this.getGuestMapper().insertGuest(guest);
	}
	
	@Override
	public Integer updateGuest(Guest guest) {
		return this.getGuestMapper().updateGuest(guest);
	}

	@Override
	public Integer deleteGuest(Integer id) {
		return this.getGuestMapper().deleteGuest(id);
	}

	public GuestMapper getGuestMapper() {
		return guestMapper;
	}
	public void setGuestMapper(GuestMapper guestMapper) {
		this.guestMapper = guestMapper;
	}

	public MunicipalityService getMunicipalityService() {
		return municipalityService;
	}

	public void setMunicipalityService(MunicipalityService municipalityService) {
		this.municipalityService = municipalityService;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public IdentificationTypeService getIdentificationTypeService() {
		return identificationTypeService;
	}

	public void setIdentificationTypeService(
			IdentificationTypeService identificationTypeService) {
		this.identificationTypeService = identificationTypeService;
	}	

}