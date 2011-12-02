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
<div id="facilities" class="rcarousel">
</div>
<div id="images" class="rcarousel">
</div>
	</script>

	<script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform json full" role="application">
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
<div id="facilities" class="rcarousel">
</div>
<div id="images" class="rcarousel">
</div>
                </div>
				<div class="c50l">
                <div class="type-text">	
                  <span class="inplace-edit"></span>
                </div>
			  </div>
              </div>
		</form> 
	</script>



<script id="row-template" type="text/x-handlebars-template">
<div class="row-item">
<ul><li><b>Name: </b>{{name}}</li><li><b>Max Guests: </b>{{maxGuests}}</li><li><b>Notes: </b>{{sub_description}}</li>
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
                	<input type="text"  name="name" id="fFormName" value="{{name}}" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="fFormMax"><s:text name="maxGuests"/></label>
                    <input type="text"  name="maxGuests" id="fFormMax" value="{{maxGuests}}" aria-required="true"/>
      		      </div>
                  <div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
                </div>
              </div>
</form>
</script>

<script id="facilities-view-template" type="text/x-handlebars-template" >
<span class="sub-edit"></span>
<div class="wrapper">
	<ul>
       
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/horse_small.jpg" /></li>
	</ul>
</div>
<span class="ui-rcarousel-next"></span>
<span class="ui-rcarousel-prev disable"></span>
</script>

<script id="images-view-template" type="text/x-handlebars-template" >
<span class="sub-edit"></span>
<div class="wrapper">
	<ul>
       
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/hotel-demo.jpeg" /></li>
		<li><img src="images/horse_small.jpg" /></li>
	</ul>
</div>
<span class="ui-rcarousel-next"></span>
<span class="ui-rcarousel-prev disable"></span>
</script>




<script id="facilities-edit-template" type="text/x-handlebars-template" >
<div class="wrapper">
	<ul>
       
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/hotel-demo.jpeg" /></li>
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/hotel-demo.jpeg" /></li>
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/hotel-demo.jpeg" /></li>
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/hotel-demo.jpeg" /></li>
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/hotel-demo.jpeg" /></li>
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/hotel-demo.jpeg" /></li>
		<li><input class="save-elem" type="checkbox" name="{{}}" value="" /><img src="images/horse_small.jpg" /></li>
	</ul>
</div>
<span class="ui-rcarousel-next"></span>
<span class="ui-rcarousel-prev disable"></span>
</script>

<script id="images-edit-template" type="text/x-handlebars-template" >
<div class="wrapper">
	<ul>
       
		<li><span class="delete-elem"></span><img src="images/hotel-demo.jpeg" /></li>
		<li><span class="delete-elem"></span><img src="images/hotel-demo.jpeg" /></li>
		<li><span class="delete-elem"></span><img src="images/hotel-demo.jpeg" /></li>
		<li><span class="delete-elem"></span><img src="images/hotel-demo.jpeg" /></li>
		<li><span class="delete-elem"></span><img src="images/hotel-demo.jpeg" /></li>
		<li><span class="delete-elem"></span><img src="images/hotel-demo.jpeg" /></li>
		<li><span class="delete-elem"></span><img src="images/horse_small.jpg" /></li>
	</ul>
</div>
<span class="ui-rcarousel-next"></span>
<span class="ui-rcarousel-prev disable"></span>
 <div class="subcolumns">
		   	<div class="result_facility_upload" id="result_facility_upload" ></div>
		   	<div class="upload_loader">&nbsp;</div>
		   	<div class="image_preview"></div>
		  </div>
    
          <div class="beauty">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility"><s:text name="imageName"/>:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_facility" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/><br/>
 				<form id="uploadFacility" action="uploadRoomTypeImage.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=""/>
   				    <input type="hidden" name="id" value="{{id}}"/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button> 
    			  <div><s:text name="uploadFacility" /></div>  
				</form>
		   	  </div>
		   	</div>      
          </div>	 
</script>
