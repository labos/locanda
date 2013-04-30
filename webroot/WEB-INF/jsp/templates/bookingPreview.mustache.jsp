<%--
 *
 *  Copyright 2013 - Sardegna Ricerche, Distretto ICT, Pula, Italy
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
   
<script id="bookingPreview-row-template" type="text/x-handlebars-template">
			<div class="subcolumns bookingPreview">
				<div class="type-text">
		      		<input type="hidden" name="id" value="{{id}}"/>
					<div class="c33l">
						<span><s:text name="room"/>:</span> 
		    		  		<strong>{{room.name}}</strong>
		      			<img src="images/{{room.roomType.maxGuests}}.png" alt="{{room.roomType.maxGuests}} <s:text name="guests"/>"/></strong>
					</div>
					<div class="c33l">
		    			<div class="subcl type-text">
		      				<span><s:text name="from"/>:</span> 
		      				<strong>{{dateIn}}</strong>
		    			</div>
					</div>
					<div class="c33l">
		    			<div class="subcl type-text">
		      				<span><s:text name="to"/>:</span> 
		      				<strong>{{dateOut}}</strong>
		    			</div>
					</div>
				</div>
				<hr/>
			</div>
	</script>

	<script id="bookingPreview-row-edit-template" type="text/x-handlebars-template">
		<form id="edit-form" class="full yform" role="application">
			<div class="subcolumns bookingPreview">
				<div class="type-text">
		      		<input type="hidden" name="id" value="{{id}}"/>
					{{#id}}<span class="row-sub-item-destroy"></span>{{/id}}
					<div class="c33l">
		    			<div class="subcl type-text">
		      				<span><s:text name="from"/>:</span> 
		      				<input type="text" class="datepicker required" name="dateIn" value="{{dateIn}}" style="display: inline;"/>
		    			</div>
					</div>
					<div class="c33l">
		    			<div class="subcl type-text">
		      				<span><s:text name="to"/>:</span> 
		      				<input type="text" class="datepicker required" name="dateOut" value="{{dateOut}}" style="display: inline;"/>
		    			</div>
					</div>
				</div>
            	<div class="type-button">
				<button class="btn_save"><s:text name="save"/></button>
                	<button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
           		</div>
			</div>
		</form>
	</script>

	<script id="bookingPreview-view-template" type="text/x-handlebars-template" >
		<hr/>
		<div class="subcolumns">
		      	<h3><s:text name="guestBookings"/>:</h3>
		</div>
		<div class="wrapper">
			<ul class="bookingPreviewDates-list"></ul>
			<div class="add-new">
				<!--<button class="btn_add"><s:text name="booking"/></button>-->
			</div>
		</div>
	</script>
