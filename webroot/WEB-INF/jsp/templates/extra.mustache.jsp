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
		<form id="edit-form" class="yform" role="application">
          	<div class="c50l">
                  	<div class="type-text">	
                  		<label for="FormName"><s:text name="name"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                		<input type="text" class="required" name="name" id="FormName" value="{{name}}" aria-required="true"/>
                 	</div>
					<div class="type-text">	
                  		<label for="FormDescription"><s:text name="description"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                		<input type="text" class="required" name="description" id="FormDescription" value="{{description}}" aria-required="true"/>
                 	</div>
					<div class="type-text">
						<div class="c50l">
							<input {{nightPriceType.checked}} id="radioNight"
					    		type="radio" name="timePriceType" value="{{nightPriceType.value}}"/><s:text name="extraPerNight"/><br/>
                        	<input {{weekPriceType.checked}} id="radioWeek"
					    		type="radio" name="timePriceType" value="{{weekPriceType.value}}"/><s:text name="extraPerWeek"/><br/>
                      		<input {{bookingPriceType.checked}} id="radioBooking"
				        		type="radio" name="timePriceType" value="{{bookingPriceType.value}}"/><s:text name="extraPerBooking"/>
				    	</div>				 
				    	<div class="c50l">
							<input {{roomPriceType.checked}} id="radioRoom"
					    		type="radio" name="resourcePriceType" value="{{roomPriceType.value}}"/><s:text name="extraPerRoom"/><br/>
                        	<input {{personPriceType.checked}} id="radioPerson"
					    		type="radio" name="resourcePriceType" value="{{personPriceType.value}}"/><s:text name="extraPerPerson"/><br/>
                        	<input {{itemPriceType.checked}} id="radioItem"
					    		type="radio" name="resourcePriceType" value="{{itemPriceType.value}}"/><s:text name="extraPerItem"/><br/>
				    	</div>
					</div>
                  	<div class="type-button">
                		<button class="btn_save"><s:text name="save"/></button>
                		<button class="btn_reset"><s:text name="cancel"/></button>
                    </div>
            </div>
          	<div class="c50l">
               <div class="type-text">
                <!--  <input type="checkbox" {{#availableOnline}}checked="checked"{{/availableOnline}} name="availableOnline" id="FormAvailableOnline" value="true" />
                  <s:text name="onlineAvailable"/>
				-->
               </div>
			</div>
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
                  		<strong><s:text name="description"/>:</strong>
                		<span>{{description}}</span>
                  	</div>
					<div class="type-text">	
                  		<strong><s:text name="extraPriceType"/>:</strong>
                		<span>{{timePriceType.label}}/{{resourcePriceType.label}}</span>
                  	</div>
              	</div>
			</div>
          	<div class="c50l">
               <div class="type-text">
                 <!-- {{#availableOnline}}<b><s:text name="onlineAvailable"/></b>{{/availableOnline}}-->
               </div>
			</div>
		</form>
	</script>

	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
		<ul>
			<li><b><s:text name="name"/>: </b>{{name}}</li>
			<li><b><s:text name="description"/>: </b>{{description}}</li>
			<li><b><s:text name="extraPriceType"/>: </b>{{timePriceType.label}}/{{resourcePriceType.label}}</li>
			<li><input type="hidden" name="id" value="{{id}}"/></li>
			<li>&nbsp;</li>
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
		<form id="filter-form" class="yform" role="application">
			<span class="filter-close"></span>
          	<div class="c80l">
               	<div class="type-text">	
               		<label for="fFormName"><s:text name="name"/></label>
              		<input type="text" name="name" id="fFormName" value="{{name}}" aria-required="true"/>
               	</div>
				<div class="type-text">	
               		<label for="fFormDescription"><s:text name="description"/></label>
              		<input type="text" name="description" id="fFormDescription" value="{{description}}" aria-required="true"/>
               	</div>
               	<div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
               	</div>
           	</div>
		</form>
	</script>