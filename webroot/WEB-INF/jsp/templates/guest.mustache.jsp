<%--
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
--%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script id="municipalitySelect-template" type="text/x-handlebars-template">
	<option value="0">Scegli</option>
</script>

    <script id="edit-template" type="text/x-handlebars-template">
			<form id="edit-form" class="yform json full" role="application" style="max-height:300px; overflow-y:scroll;">
        		<div class="c50l">
                  	<div class="type-text">	
                  		<label for="FormFirstName"><s:text name="firstName"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                		<input type="text" class="required" name="firstName" id="FormFirstName" value="{{firstName}}" aria-required="true"/>
                  	</div>
                  	<div class="type-text">           
       					<label for="FormLastName"><s:text name="lastName"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                    	<input type="text" class="required" name="lastName" id="FormLastName" value="{{lastName}}" aria-required="true"/>
      		      	</div>
				  	<div class="type-select">
                  		<label for="FormGender"><s:text name="gender"/>:</label>
                		<select name="gender" id="FormGender" size="1" aria-required="true">
							{{#genders}}<option name="{{value}}" {{#selected}}selected="selected"{{/selected}}>{{value}}</option>{{/genders}}
						</select>
				  	</div>
				  	<div class="type-select">
                  		<label for="FormBirthDay"><s:text name="birthDate"/>:</label>
						<input type="text" name="birthDate" value="{{birthDate}}"  class="datepicker"/>
					</div>
					<div class="type-select">
                  		<label><s:text name="citizenship"/>:</label>
                	  	<select name="id_citizenship" id="Formid_countryOfCitizenship" size="1" aria-required="true">
							{{#availableCountriesCitizenship}}<option value="{{id}}" {{#selected}}selected="selected"{{/selected}}>{{description}}</option>{{/availableCountriesCitizenship}}
						</select>
					</div>
					<div class="type-select" id="FormIdentificationTypeContainer">
						<label><s:text name="identificationType"/>:</label>
						<select name="id_idType" id="identificationTypeSelector">
							<option value=""><s:text name="selectIDType"/></option>
							{{#availableIdentificationTypes}}<option value="{{id}}" {{#selected}}selected="selected"{{/selected}}>{{description}}</option>{{/availableIdentificationTypes}}
						</select>
						<label for="FormIdNumber"><s:text name="idNumber"/></label>
                  		<input type="text"name="idNumber" id="FormIdNumber" value="{{idNumber}}" aria-required="true"/>
					</div>
					<div class="type-select" id="FormIdentificationType">
						<input type="hidden" name="id_idPlace" id="id_idPlace" value="{{#id_idPlace}}{{id_idPlace}}{{/id_idPlace}}" />
						<div id="IdentificationTypeProvinceSelector" class="clear none">
							<label><s:text name="identificationTypeProvince"/>:</label>
							<select name="FormIdentificationTypeProvince" id="FormIdentificationTypeProvinceSelector">
								<option value=""><s:text name="selectProvince"/></option>
								{{#ITProvinces}}
									<option value="{{code}}">{{description}}</option>
								{{/ITProvinces}}
							</select>
						</div>
						<div id="IdentificationTypePlaceSelector" class="clear none">
							<label><s:text name="identificationTypePlace"/>:</label>
							<select id="FormIdentificationTypePlace" aria-required="true"></select>
	      		      	</div>
             	 	</div>
                  	<div class="type-button" style="position:absolute !important;bottom:0;">
                	  	<button class="btn_save"><s:text name="save"/></button>
                	  	<button class="btn_reset"><s:text name="cancel"/></button>
                    </div>
            	</div>
        		<div class="c50l">
					<div class="type-text">           
       				  	<label for="FormPhone"><s:text name="phone"/></label>
                      	<input type="text" class="validPhone" name="phone" id="FormPhone" value="{{phone}}" aria-required="true"/>
      		      	</div>
				  	<div class="type-text">           
       				  	<label for="FormEmail"><s:text name="email"/></label>
                      	<input type="text" class="email" name="email" id="FormEmail" value="{{email}}" aria-required="true"/>
      		      	</div>
					<div class="type-select" id="FormBirth">
						<input type="hidden" name="id_municipalityOfBirth" id="id_municipalityOfBirth" value="{{#id_municipalityOfBirth}}{{id_municipalityOfBirth}}{{/id_municipalityOfBirth}}" />
                		<label><s:text name="birthCountry"/>:</label>
                	  	<select name="id_countryOfBirth" id="Formid_countryOfBirth" size="1" aria-required="true">
							{{#availableCountriesBirth}}<option value="{{id}}" {{#selected}}selected="selected"{{/selected}}>{{description}}</option>{{/availableCountriesBirth}}
						</select>
						<div id="BirthProvinceSelector" class="clear none">
							<label><s:text name="birthProvince"/>:</label>
							<select name="FormBirthProvince" id="FormBirthProvinceSelector">
								<option value=""><s:text name="selectProvince"/></option>
								{{#ITProvinces}}
									<option value="{{code}}">{{description}}</option>
								{{/ITProvinces}}
							</select>
						</div>
						<div id="BirthPlaceSelector" class="clear none">
							<label><s:text name="birthPlace"/>:</label>
							<select id="FormBirthPlace" aria-required="true"></select>       
	      		      	</div>
             	 	</div>
             	 	<div class="type-select" id="FormResidence">
						<input type="hidden" name="id_municipalityOfResidence" id="id_municipalityOfResidence" value="{{#id_municipalityOfResidence}}{{id_municipalityOfResidence}}{{/id_municipalityOfResidence}}" />
                		<label><s:text name="residenceCountry"/>:</label>
                	  	<select name="id_countryOfResidence" id="Formid_countryOfResidence" size="1" aria-required="true">
							{{#availableCountriesResidence}}<option value="{{id}}" {{#selected}}selected="selected"{{/selected}}>{{description}}</option>{{/availableCountriesResidence}}
						</select>
						<div id="ResidenceProvinceSelector" class="clear none">
							<label><s:text name="residenceProvince"/>:</label>
							<select name="FormResidenceProvince" id="FormResidenceProvinceSelector">
								<option value=""><s:text name="selectProvince"/></option>
								{{#ITProvinces}}
									<option value="{{code}}">{{description}}</option>
								{{/ITProvinces}}
							</select>
						</div>
						<div id="ResidencePlaceSelector" class="clear none">
							<label><s:text name="residencePlace"/>:</label>
							<select id="FormResidencePlace" aria-required="true"></select>
							<div class="type-text">           
		       				 	<label for="FormAddress"><s:text name="address"/></label>
		                      	<input type="text" name="address" id="FormAddress" value="{{address}}" aria-required="true"/>
		      		      	</div>
	      		      	</div>
             	 	</div>
				  	<div class="type-text">	
                  	  	<label for="FormNotes"><s:text name="notes"/></label>
					  	<textarea name="notes" id="FormNotes">{{notes}}</textarea>		 
                  	</div>
				</div>
			</form>
	</script>

    <script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform inview" role="application">
        	<div class="c50l">
              	<div class="c50l">
                  	<div class="type-text">	
                  		<strong><s:text name="firstName"/></strong>
                		<span>{{firstName}}</span>
               		</div>
               		<div class="type-text">           
       					<strong><s:text name="lastName"/></strong>
                   		<span>{{lastName}}</span>
      		   		</div>
      		   		<div class="type-text">	
               			<strong><s:text name="notes"/></strong>
						<span>{{notes}}</span>		 
                  	</div>
              	</div>
				<div class="c50l">
					<div class="type-text">	
               			<strong><s:text name="phone"/></strong>
						<span>{{phone}}</span>		 
                  	</div>
					<div class="type-text">	
               			<strong><s:text name="email"/></strong>
						<span>{{email}}</span>		 
                  	</div>
				</div>
			</div>
		</form>
	</script>

	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
		  	<ul>
				<li><b><s:text name="firstName"/>: </b>{{firstName}}</li>
				<li><b><s:text name="lastName"/>: </b>{{lastName}}</li><li><b><s:text name="notes"/>: </b>{{sub_description}}</li>
		  		<li><input type="hidden" name="id" value="{{id}}"/></li>
		  	</ul>
		  	<span class="row-item-destroy"></span>
		</div>
	</script>

	<script id="toolbar-template" type="text/x-handlebars-template">
		<li><input id="item-autocomplete" type="text" value=""/>
		  	<div id="form-filter-container"></div>
		</li>
		<li>
		  	<button id="item-filter">&nbsp;</button>
		</li>
	</script>

    <script id="form-filter-template" type="text/x-handlebars-template">
		<form id="filter-form" class="yform json full" role="application">
		  	<span class="filter-close"></span>
          	<div class="c80l">
              	<div class="type-text">	
                  	<label for="fFormFirstName"><s:text name="firstName"/></label>
                	<input type="text" name="firstName" id="fFormFirstName" value="{{firstName}}" aria-required="true"/>
                </div>
                <div class="type-text">           
       				<label for="fFormLastName"><s:text name="lastName"/></label>
                    <input type="text" name="lastName" id="fFormLastName" value="{{lastName}}" aria-required="true"/>
      		    </div>
                <div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
                </div>
            </div>
		</form>
	</script>