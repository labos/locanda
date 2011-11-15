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
              <input type="hidden" name="id" value="<s:property value="convention.id"/>"/>
                <div class="c50l">
                  <div class="type-text">	
                  	<label for="FormName"><s:text name="name"/><sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="name" id="FormName" value="{{name}}" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="FormMax"><s:text name="maxGuests"/><sup title="This field is mandatory.">*</sup></label>
                    <input type="text" class="required" name="activationCode" id="FormMax" value="{{maxGuests}}" aria-required="true"/>
      		      </div> 
				  <div class="type-text">	
                  	<label for="FormNotes"><s:text name="notes"/></label>
					<textarea name="description" id="FormNotes">{{notes}}</textarea>		 
                  </div>
                  <div class="type-button">
					<input type="submit" value="<s:text name="save"/>">
					<input type="reset" value="<s:text name="cancel"/>">
                    </div>	
                </div>
              </div>
</form>
</script>
    <script id="row-template" type="text/x-handlebars-template">
<div class="item_list">
<ul><li><b>Name: </b>{{name}}</li><li><b>Max Guests: </b>{{maxGuests}}</li><li><b>Notes: </b>{{sub_description}}</li>
<li><input type="hidden" name="id" value="{{id}}"/></li>
</ul>
<span class="item-destroy"></span>
<a href="#edit/{{id}}">Edit</a>
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
		<form class="yform json full" role="application">
          	  <div class="c80l">

                  <div class="type-text">	
                  	<label for="fFormName"><s:text name="name"/></label>
                	<input type="text"  name="name" id="fFormName" value="{{name}}" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="fFormMax"><s:text name="maxGuests"/></label>
                    <input type="text"  name="maxGuests" id="fFormMax" value="{{maxGuests}}" aria-required="true"/>
      		      </div>
                  <div class="type-button">
					<input type="submit" value="<s:text name="search"/>">
                </div>
              </div>
</form>
</script>