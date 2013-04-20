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

	<script id="selectbooker-template" type="text/x-handlebars-template">
		<a class="set_booker" href="#"><s:text name="selectBooker" /></a>
		{{#data}}
			<div class="clear bookerdetails">
				<ul>
					<li>
						<b><s:text name="firstName"/>:</b> {{firstName}}<br/>
					</li>
					<li>
						<b><s:text name="lastName"/>:</b> {{lastName}}<br/>
					</li>
					<li>
						<b><s:text name="phone"/>:</b> {{phone}}<br/>
					</li>
					<li>
						<b><s:text name="email"/>:</b> {{email}}<br/>
					</li>
					<li>
						<b><s:text name="notes"/>:</b> {{notes}}<br/>
					</li>
				</ul>
			</div>
		{{/data}}
		{{^data}}
			<div class="clear"><s:text name="bookerAbsent" /></div>
		{{/data}}
	</script>
	
	<script id="selectgroupleader-template" type="text/x-handlebars-template">
		<a class="set_groupleader" href="#"><s:text name="selectGroupLeader" /></a>
		{{#data}}
			<div class="clear">&nbsp;</div>
				<div class="clear">
					<div class="float_left">
	  					<input type="radio" name="groupType" value="CAPOFAMIGLIA" {{#data.family}}checked="checked"{{/data.family}} /> <s:text name="family" /><br/>
						<input type="radio" name="groupType" value="CAPOGRUPPO" {{#data.group}}checked="checked"{{/data.group}} /> <s:text name="group" />
	  				</div>
	  				<div class="float_left groupleaderdetails">
						<ul>
							<li>
								<b><s:text name="firstName"/>:</b> {{firstName}}<br/>
							</li>
							<li>
								<b><s:text name="lastName"/>:</b> {{lastName}}<br/>
							</li>
							<li>
								<b><s:text name="phone"/>:</b> {{phone}}<br/>
							</li>
							<li>
								<b><s:text name="email"/>:</b> {{email}}<br/>
							</li>
							<li>
								<b><s:text name="notes"/>:</b> {{notes}}<br/>
							</li>
						</ul>
					</div>
					<div class="float_left">
						<img title="Rimuovi" alt="Rimuovi" src="images/delete.png" class="groupleader_clear">
					</div>
				</div>
		{{/data}}
		{{^data}}
			<div class="clear"><s:text name="groupleaderAbsent" /></div>
		{{/data}}
	</script>
	
	<script id="selecthouseds-template" type="text/x-handlebars-template">
		<ul>
			{{#hs}}
				<li>
					<div class="row-item">
						<input type="hidden" name="housedid" value="{{id}}"/>
						<input type="hidden" name="guestid" value="{{guest.id}}"/>
					  	<ul>
							<li class="houseddetails"><b><s:text name="firstName"/>: </b>{{guest.firstName}}</li>
							<li class="houseddetails"><b><s:text name="lastName"/>: </b>{{guest.lastName}}</li>
							<li class="houseddetails"><b><s:text name="notes"/>: </b>{{guest.notes}}</li>
					  		<li>
					  			<div class="housed_accordion">
					  				<h2><a href="#"><s:text name="selectHousedDates" /></a></h2>
					  				<div class="clear">
						  				<div class="clear">
						  					<b>Check-in:</b><br/>
						  					<input type="text" name="datecheckin" value="{{checkInDate}}" readonly="readonly" class="housed_inputdate"/>
						  					<img class="housed_selectdate" src="images/calendar.gif" alt="..." title="...">
						  					<img class="housed_cleardate" src="images/delete.png" alt="Annulla" title="Annulla">
						  				</div>
						  				<div class="clear">
						  					<b>Check-out:</b><br/>
						  					<input type="text" name="datecheckout" value="{{checkOutDate}}" readonly="readonly" class="housed_inputdate"/>
						  					<img class="housed_selectdate" src="images/calendar.gif" alt="..." title="...">
						  					<img class="housed_cleardate" src="images/delete.png" alt="Annulla" title="Annulla">
						  				</div>
						  			</div>
					  			</div>
					  		</li>
					  		<li>
					<div class="housed_options_accordion">
                		<h2><a href="#top_accordion"><s:text name="housedOptions"/></a></h2>
					<div class="clear">
					<div class="type-select">
                  		<label><s:text name="tourismType"/>:</label>
                	  		<select name="id_tourismType" id="Formid_tourismType" size="1" aria-required="true">
							<option value="0"><s:text name="selectOne"/></option>
							{{#availableTourismTypes}}<option value="{{id}}" {{#selected}}selected="selected"{{/selected}}>{{name}}</option>{{/availableTourismTypes}}
						</select>
					</div>
					<div class="type-select">
                  		<label><s:text name="transport"/>:</label>
                	  		<select name="id_transport" id="Formid_transport" size="1" aria-required="true">				
							<option value="0"><s:text name="selectOne"/></option>
							{{#availableTransports}}<option value="{{id}}" {{#selected}}selected="selected"{{/selected}}>{{name}}</option>{{/availableTransports}}
						</select>
					</div>
					<div class="type-check">
						<input type="checkbox" {{#touristTax}}checked="checked"{{/touristTax}} value="true" id="Formid_touristTax" />
                  		<b><s:text name="touristTax" /></b>
					</div>
					</div>
					</div>
					  		</li>
					  	</ul>
						<span class="housed-row-tag"></span>
					  	<span class="row-item-destroy"></span>
					</div>
				</li>
			{{/hs}}
		</ul>
		<a class="set_guests" href="#"><s:text name="selectGuests" /></a>
	</script>
