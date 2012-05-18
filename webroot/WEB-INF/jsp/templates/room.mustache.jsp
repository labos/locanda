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
                		<input type="text" class="required" name="name" id="FormName" value="{{name}}" aria-required="true"/>
                  	</div>
                  	<div class="type-select">           
       					<label for="FormRoomType"><s:text name="roomType"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                    	<select name="id_roomType">
							{{#availableRoomTypes}}<option value="{{value_id}}" {{#selected}}selected="selected"{{/selected}}>{{value_name}}</option>{{/availableRoomTypes}}
						</select>
      		      	</div> 
				  	<div class="type-text">	
                  		<label for="FormNotes"><s:text name="notes"/></label>
						<textarea name="notes" id="FormNotes">{{notes}}</textarea>		 
                  	</div>
                  	<div class="type-button">
						<button class="btn_save"><s:text name="save"/></button>
                		<button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
                    </div>	
                </div>
		</form>
	</script>

	<script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform json inview" role="application">
            	<div class="c50l">
                  	<div class="type-text">	
                  		<strong><s:text name="name"/></strong>
						<span>{{name}}</span>
                  	</div>
                  	<div class="type-text">           
       					<strong><s:text name="roomType"/></strong>
						<span>{{roomType.name}}</span>
      		      	</div> 
				  	<div class="type-text">	
                  		<strong><s:text name="notes"/></strong>
						<span>{{notes}}</span> 
                  	</div>
                </div>
				<div class="c50l">
                	<div class="type-text">	
                	</div>
			  	</div>
		</form>
	</script>
	<script id="no-roomtype-template" type="text/x-handlebars-template">
		<h3><strong><s:text name="roomTypesNotPresent" /></strong></h3>
		<a href="<s:property value="url_findallroomtypes"/>?sect=accomodation"><img src="images/add-icon.png" alt="<s:text name="roomType" />"/><s:text name="roomType" /></a>
	</script>
	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
			<ul>
				<li><b><s:text name="name"/>: </b>{{name}}</li>
				<li><b><s:text name="roomType"/>: </b>{{roomType.name}}</li>
				<li><b><s:text name="notes"/>: </b>{{sub_description}}</li>
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
		<form  id="filter-form" class="yform json full" role="application">
			<span class="filter-close"></span>          	
  			<div class="c80l">
               	<div class="type-text">	
                  	<label for="fFormName"><s:text name="name"/></label>
                	<input type="text" name="name" id="fFormName" value="{{name}}" aria-required="true"/>
               	</div>
               	<div class="type-text">           
       				<label for="fFormType"><s:text name="roomType"/></label>
                   	<input type="text" name="roomType" id="fFormType" value="{{roomType}}" aria-required="true"/>
      		    </div>
                <div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
                </div>
            </div>
		</form>
	</script>

	<script id="facility-row-template" type="text/x-handlebars-template">
		{{#id}}<span class="title-elem">{{image.caption}}</span><img src="<%=request.getContextPath( )%>/rest/file/{{image.file.id}}"/>{{/id}}
	</script>

	<script id="facility-row-edit-template" type="text/x-handlebars-template">
		<input class="choose-elem" {{#id}}checked="checked"{{/id}} type="checkbox" name="facilities[]" value="{{id}}"/>
		<span class="title-elem">{{facility.name}}</span>	
		<img src="<%=request.getContextPath( )%>/rest/file/{{facility.image.file.id}}"/>
	</script>

	<script id="image-row-template" type="text/x-handlebars-template">
		{{#id}}<span class="title-elem">{{caption}}</span>
		<img width="100" src="<%=request.getContextPath( )%>/rest/file/{{file.id}}"/>{{/id}}
	</script>

	<script id="image-row-edit-template" type="text/x-handlebars-template">
		<input class="choose-elem" {{#id}}checked="checked"{{/id}} type="checkbox" name="images[]" value="{{id}}"/>
		<span class="title-elem">{{image.caption}}</span>
		<img width="100" src="<%=request.getContextPath( )%>/rest/file/{{image.file.id}}"/>
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