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
                <div class="c50l">
                  	<div class="type-text">	
                  		<label for="FormName"><s:text name="name"/><sup title="This field is mandatory.">*</sup></label>
                		<input type="text" class="required" name="name" id="FormName" value="{{name}}" aria-required="true"/>
                  	</div>
                  	<div class="type-text">           
       					<label for="FormMax"><s:text name="maxGuests"/><sup title="This field is mandatory.">*</sup></label>
                    	<input type="text" class="required" name="maxGuests" id="FormMax" value="{{maxGuests}}" aria-required="true"/>
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
            </div>
		</form>
	</script>

	<script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform json inview" role="application">
          	<div class="c50l">
            	<div class="c50l">
                  	<div class="type-text">	
                  		<strong><s:text name="name"/></strong>
						<span>{{name}}</span>
                  	</div>
                  	<div class="type-text">           
       					<strong><s:text name="maxGuests"/></strong>
						<span>{{maxGuests}}</span>
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
            </div>
		</form>
	</script>

	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
			<ul>
				<li><b><s:text name="name"/>: </b>{{name}}</li>
				<li><b><s:text name="maxGuests"/>: </b>{{maxGuests}}</li>
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
       				<label for="fFormMax"><s:text name="maxGuests"/></label>
                   	<input type="text" name="maxGuests" id="fFormMax" value="{{maxGuests}}" aria-required="true"/>
      		    </div>
                <div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
                </div>
            </div>
		</form>
	</script>

	<script id="facility-row-template" type="text/x-handlebars-template">
		<span class="title-elem">{{caption}}</span><img src="<%=request.getContextPath( )%>/rest/file/{{facility.image.file.id}}"/>
	</script>

	<script id="facility-row-edit-template" type="text/x-handlebars-template">
		<input class="choose-elem" checked="checked" type="checkbox" name="facilities[]" value="{{id}}"/>
		<img src="<%=request.getContextPath( )%>/rest/file/{{facility.image.file.id}}"/>
	</script>

	<script id="image-row-template" type="text/x-handlebars-template">
		<span class="title-elem">{{caption}}</<%=request.getContextPath( )%>/rest/facilities/roomType/{{id}}span>
		<img src="<%=request.getContextPath( )%>/rest/images/{{id}}"/>
	</script>

	<script id="image-row-edit-template" type="text/x-handlebars-template">
		<span class="title-elem">{{caption}}</span>
		<span class="delete-elem"></span>
		<img src="<%=request.getContextPath( )%>/rest/images/{{id}}"/>
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
			<a hfref="" class="btn_add">Edit <s:text name="images"/></a>
		</div>
		<div class="wrapper">
			<ul></ul>
		</div>
		<span class="ui-rcarousel-next"></span>
		<span class="ui-rcarousel-prev disable"></span>
 		<div class="subcolumns">
		   	<div class="result_facility_upload" id="result_facility_upload"></div>
		   	<div class="upload_loader">&nbsp;</div>
		   	<div class="image_preview"></div>
		</div>
        <div class="beauty">
     	  	<div class="subcolumns">
           	  	<div class="c33l">
    				<label for="name_facility"><s:text name="imageName"/>:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_facility" class="require"/>
 			  	</div>
 			  	<div class="c20l">
 					<br/>
 					<form id="uploadFacility" action="rest/roomType/{{id_parent}}" method="post" enctype="multipart/form-data">
   				  		<input type="hidden" name="caption" value=""/>
						<input type="hidden" name="id" value="{{id_parent}}"/>
   				    	<input type="hidden" name="idStructure" value="{{id_structure}}"/>
     			  		<input type="file" name="upload" multiple/>
    			  		<button>Upload</button> 
    			  		<div><s:text name="uploadRoomTypeImage"/></div>  
					</form>
		   	  	</div>
		   	</div>      
        </div>	 
	</script>
