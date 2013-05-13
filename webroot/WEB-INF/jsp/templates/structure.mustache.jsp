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
   
    <script id="edit-template" type="text/x-handlebars-template">
		<form id="edit-form" class="yform json full" role="application">
          	<div class="c50l">   
            	  <div class="type-text">
                  	<label for="FormName"><s:text name="name"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                  	<input type="text" class="required" name="name" id="FormName" value="{{name}}" aria-required="true"  />
					<input type="hidden" name="redirect_form" value="false" />
                  </div>
                  <div class="type-text">
               	 	<label for="FormAddress"><s:text name="address"/></label>
                  	<input type="text" name="address" id="FormAddress" value="{{address}}" aria-required="true" />
              	  </div>
             	  <div class="type-text">
               	 	<label for="FormCity"><s:text name="city"/></label>
                  	<input type="text" name="city" id="FormCity" value="{{city}}" aria-required="true" />
              	  </div>
              	  <div class="type-text">
               	 	<label for="FormZipCode"><s:text name="zipCode"/></label>
                  	<input type="text" name="zipCode" id="FormZipCode" value="{{zipCode}}" aria-required="true" />
              	  </div>
              	  <div class="type-select">
                	<label for="FormCountry"><s:text name="country"/>:</label>
                  	<select  name="country" id="FormCountry" size="1" aria-required="true">
						{{#availableCountries}}<option value="{{code}}" {{#selected}}selected="selected"{{/selected}}>{{name}}</option>{{/availableCountries}}
					</select>
           	 	  </div>
				<div class="type-text">
					<label for="FormEmail"><s:text name="email"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                	<input type="text" class="required email" name="email" id="FormEmail" value="{{email}}" aria-required="true" size="20" />
              	  </div>
				  <div class="type-button">
                  	<button class="btn_save"><s:text name="save"/></button>
                    <button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
              	  </div>
			</div>
			<div class="c50l">
              	  <div class="type-text">
                		<label for="FormUrl"><s:text name="url"/></label>
                		<input type="text" name="url" id="FormUrl" value="{{url}}" aria-required="true" />
              	  </div>
              	  <div class="type-text">
					<label for="FormPhone"><s:text name="phone"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
               	 	<input type="text" class="required validPhone" name="phone" id="FormPhone" value="{{phone}}" aria-required="true" />
              	  </div>
              	  <div class="type-text">
                  	<label for="FormMobile"><s:text name="mobile"/></label> 
                		<input type="text" class="validPhone" name="mobile" id="FormMobile" value="{{mobile}}" />
                  </div> 
              	  <div class="type-text">
					<label for="FormFax"><s:text name="fax"/></label>
                		<input type="text" class="validPhone" name="fax" id="FormFax" value="{{fax}}" aria-required="true" />
              	  </div>
              	  <div class="type-text">
                  	<label for="FormTaxNumber"><s:text name="taxNumber"/></label> 
                		<input type="text" name="taxNumber" id="FormTaxNumber" value="{{taxNumber}}" />
                  </div> 
            	  		<div class="type-text">
                  	<label for="FormNotes"><s:text name="notes"/></label> 
                  	<textarea name="notes" id="FormNotes">{{notes}}</textarea>
                  </div>   	  	             
            </div>
        </form>

        <form method="post" action="updateAccount.action" id="password-form" class="yform json full" role="application">
          <fieldset>
          	<legend><s:text name="passwordChange"></s:text></legend>
			<input type="hidden" name="redirect_form" value="false" />
          	<div class="c50l">
              <div class="type-text">
              	<label for="FormPassword"><s:text name="password"></s:text><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                <input type="password" class="required" name="password" id="FormPassword" aria-required="true" />
              </div>
              <div class="type-text">
                <label for="FormRetyped"><s:text name="reTypePassword"></s:text> <sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                <input type="password" class="required" name="reTyped" id="FormRetyped"  aria-required="true" />
              </div>
              <div class="type-button">
                <button class="btn_save"><s:text name="save"/></button>
                <button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
              </div>
            </div>
          </fieldset>
        </form>
	</script>

    <script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform inview" role="application">
          	<div class="c50l">
                <div class="c50l">
                  	<div class="type-text">           
       					<strong><s:text name="name"/>:</strong>
                    	<span>{{name}}</span>
      		      	</div> 
                  	<div class="type-text">	
                  		<strong><s:text name="address"/>:</strong>
                		<span>{{address}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="city"/>:</strong>
                		<span>{{city}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="zipCode"/>:</strong>
                		<span>{{zipCode}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="country"/>:</strong>
                		<span>{{country}}</span>
                  	</div>
                  	<div class="type-text">           
       					<strong><s:text name="email"/>:</strong>
                    	<span>{{email}}</span>
      		      	</div>
              	</div>
				<div class="c50l">
                  	<div class="type-text">	
                  		<strong><s:text name="url"/>:</strong>
                		<span>{{url}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="phone"/>:</strong>
                		<span>{{phone}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="mobile"/>:</strong>
                		<span>{{mobile}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="fax"/>:</strong>
                		<span>{{fax}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="taxNumber"/>:</strong>
                		<span>{{taxNumber}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="notes"/>:</strong>
                		<span>{{notes}}</span>
                  	</div>
              	</div>
			</div>
		</form>
	</script>
	
	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
		  	<ul>
				<li><b><s:text name="name"/>: </b>{{name}}</li>
				<li><b><s:text name="email"/>: </b>{{email}}</li>
				<li><b><s:text name="notes"/>: </b>{{sub_description}}</li>
		  		<li><input type="hidden" name="id" value="{{id}}"/></li>
		  	</ul>
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
                  	<label for="fFormName"><s:text name="name"/></label>
                	<input type="text" name="name" id="fFormName" value="{{name}}" aria-required="true"/>
                </div>
				<div class="type-text">
               	 	<label for="FormAddress"><s:text name="address"/></label>
                  	<input type="text" name="address" id="FormAddress" value="{{address}}" aria-required="true" />
              	</div>
             	<div class="type-text">
               	 	<label for="FormCity"><s:text name="city"/></label>
                  	<input type="text" name="city" id="FormCity" value="{{city}}" aria-required="true" />
              	</div>
              	<div class="type-text">
               	 	<label for="FormZipCode"><s:text name="zipCode"/></label>
                  	<input type="text" name="zipCode" id="FormZipCode" value="{{zipCode}}" aria-required="true" />
              	</div>
				<div class="type-text">
               	 	<label for="FormCountry"><s:text name="country"/></label>
                  	<input type="text" name="country" id="FormCountry" value="{{country}}" aria-required="true" />
              	</div>
               	<div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
               	</div>
           	</div>
		</form>
	</script>
	
	<script id="facility-row-template" type="text/x-handlebars-template">
		{{#id}}<span class="title-elem">{{name}}</span><img src="<%=request.getContextPath( )%>/rest/file/{{image.file.id}}"/>{{/id}}
	</script>

	<script id="facility-row-edit-template" type="text/x-handlebars-template">
		<input class="choose-elem" {{#id}}checked="checked"{{/id}} type="checkbox" name="facilities[]" value="{{id}}"/>
		<span class="title-elem">{{facility.name}}</span>		
		<img src="<%=request.getContextPath( )%>/rest/file/{{facility.image.file.id}}"/>
	</script>

	<script id="image-row-template" type="text/x-handlebars-template">
		{{#id}}<span class="title-elem">{{caption}}</span>
		<img src="<%=request.getContextPath( )%>/rest/file/{{file.id}}"/>{{/id}}
	</script>

	<script id="image-row-edit-template" type="text/x-handlebars-template">
		<input class="choose-elem" {{#id}}checked="checked"{{/id}} type="checkbox" name="images[]" value="{{id}}"/>
		<span class="title-elem">{{image.caption}}</span>
		<img src="<%=request.getContextPath( )%>/rest/file/{{image.file.id}}"/>
	</script>

	<script id="facilities-view-template" type="text/x-handlebars-template">
		<div class="wrapper inview">
			<ul></ul>
		</div>
		<span class="ui-rcarousel-next"></span>
		<span class="ui-rcarousel-prev disable"></span>
	</script>

	<script id="images-view-template" type="text/x-handlebars-template">
		<div class="wrapper inview">
			<ul></ul>
		</div>
		<span class="ui-rcarousel-next"></span>
		<span class="ui-rcarousel-prev disable"></span>
	</script>

	<script id="facilities-edit-template" type="text/x-handlebars-template" >
		<div class="add-new">
			<a href="<%=request.getContextPath( )%>/findAllFacilities.action?sect=settings" class="btn_add"><s:text name="facilityEdit" /></a>
		</div>
		<div class="wrapper">
			<ul></ul>
		</div>
		<span class="ui-rcarousel-next"></span>
		<span class="ui-rcarousel-prev disable"></span>
	</script>

	<script id="images-edit-template" type="text/x-handlebars-template" >
		<div class="add-new">
			<a href="<%=request.getContextPath( )%>/findAllImages.action?sect=settings" class="btn_add"><s:text name="editImages" /></a>
		</div>
		<div class="wrapper">
			<ul></ul>
		</div>
		<span class="ui-rcarousel-next"></span>
		<span class="ui-rcarousel-prev disable"></span>
	</script>
