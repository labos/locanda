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
       					<label for="FormCode"><s:text name="code"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                    	<input type="text" class="required" name="activationCode" id="FormCode" value="{{activationCode}}" aria-required="true"/>
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

    <script id="view-template" type="text/x-handlebars-template">
		<form id="view-form" class="yform inview" role="application">
          	  <div class="c50l">
                <div class="c50l">
                  <div class="type-text">	
                  	<strong><s:text name="name"/></strong>
                	<span>{{name}}</span>
                  </div>
                  <div class="type-text">           
       				<strong><s:text name="code"/></strong>
                    <span>{{activationCode}}</span>
      		      </div> 
				  <div class="type-text">	
                  	<strong><s:text name="description"/></strong>
					<span>{{description}}</span>		 
                  </div>
              </div>
			</div>
		</form>
	</script>

	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
			<ul>
				<li><b><s:text name="name"/>: </b>{{name}}</li>
				<li><b><s:text name="code"/>: </b>{{activationCode}}</li>
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
       				<label for="fFormCode"><s:text name="code"/></label>
                   	<input type="text" name="activationCode" id="fFormCode" value="{{activationCode}}" aria-required="true"/>
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
