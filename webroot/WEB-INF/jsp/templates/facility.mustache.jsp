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
				  	<div class="type-text">	
                  		<label for="FormDescr"><s:text name="description"/></label>
						<textarea name="description" id="FormDescr">{{description}}</textarea>		 
                  	</div>
                  	<div class="type-button">
                		<button class="btn_save"><s:text name="save"/></button>
                		<button class="btn_reset"><s:text name="cancel"/></button>
                    </div>	
            </div>
		</form>
		
	</script>
	
	<script id="image-view-template" type="text/x-handlebars-template">
            <div class="c50l inview">
				{{#id}}<img width="100" src="rest/file/{{id}}?rnd={{rnd}}" alt="" />{{/id}}
				{{^id}}<img src="images/image-default.png" alt="default" /><b><s:text name="insertNewImage"/></b>{{/id}}
			</div>
 	</script>
 	
    <script id="image-edit-template" type="text/x-handlebars-template">
 		<div class="subcolumns">
		   	<div class="result_facility_upload" id="result_facility_upload"></div>
		   	<div class="upload_loader">&nbsp;</div>
		   	<div class="image_preview"></div>
		</div>
        <div class="beauty">
     	  	<div class="subcolumns">
            <div class="c20l">
				<img width="100" src="rest/file/{{id}}?rnd={{rnd}}" alt="" />
			</div>
           	  	<div class="c33l">
    				<input type="hidden" name="facility_name" value="default" id="name_facility"/>
 			  	</div>
 			  	<div class="c20l">
 					<br/>
 					<form id="uploadFacility" action="rest/file" method="post" enctype="multipart/form-data">
   				  		<input type="hidden" name="caption" value=""/>
						<input type="hidden" name="id" value="{{id}}"/>
     			  		<input type="file" name="upload" multiple/>
    			  		<button>Upload</button> 
    			  		<div><s:text name="uploadFacility"/></div>  
					</form>
		   	  	</div>
		   	</div>      
        </div>
	</script>
    <script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform inview" role="application">
                <div class="c50l">
                  <div class="type-text">	
                  	<strong><s:text name="name"/></strong>
                	<span>{{name}}</span>
                  </div>
				  <div class="type-text">	
                  	<strong><s:text name="description"/></strong>
					<span>{{description}}</span>		 
                  </div>
              </div>
		</form>
	</script>

	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
			<img class="thumb" src="rest/file/{{image.file.id}}?rnd={{rnd}}" alt="" style="position:absolute;left:0px;width:30px;heigth:30px;"/>
			<ul style="margin-left:35px;">
				<li><b><s:text name="name"/>: </b>{{name}}</li>
				<li><b><s:text name="description"/>: </b>{{sub_description}}</li>
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
                  	<label for="fFormName"><s:text name="name"/></label>
                	<input type="text" name="name" id="fFormName" value="{{name}}" aria-required="true"/>
                </div>
               	<div class="type-text">           
       				<label for="fFormDescr"><s:text name="description"/></label>
                   	<input type="text" name="description" id="fFormDescr" value="{{description}}" aria-required="true"/>
      		    </div>
               	<div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
               	</div>
           	</div>
		</form>
	</script>
