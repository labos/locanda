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
                  	<div class="type-select">           
       					<label for="FormYear"><s:text name="year"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
						<select name="year">
							{{#availableYears}}<option name="{{value}}" {{#selected}}selected="selected"{{/selected}}>{{value}}</option>{{/availableYears}}
						</select>
      		      	</div> 
                  	<div class="type-text">	
                  		<label for="FormName"><s:text name="name"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                		<input type="text" class="required" name="name" id="FormName" value="{{name}}" aria-required="true"/>
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
                  	<div class="type-text">           
       					<strong><s:text name="year"/>:</strong>
                    	<span>{{year}}</span>
      		      	</div> 
                  	<div class="type-text">	
                  		<strong><s:text name="name"/>:</strong>
                		<span>{{name}}</span>
                  	</div>
              	</div>
		</form>
	</script>

	<script id="row-template" type="text/x-handlebars-template">
		<div class="row-item">
		<ul>
			<li><b><s:text name="name"/>: </b>{{name}}</li>
			<li><b><s:text name="year"/>: </b>{{year}}</li>
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
      				<label for="fFormYear"><s:text name="year"/></label>
                  	<input type="text" name="year" id="fFormYear" value="{{year}}" aria-required="true"/>
   		      	</div>
               	<div class="type-button">
					<button class="btn_submit"><s:text name="search"/></button>
               	</div>
           	</div>
		</form>
	</script>

	<script id="period-row-template" type="text/x-handlebars-template">
		<div class="subcolumns inview">
			<div class="c25l">
		    	<div class="subcl type-text">
		      		<strong>{{startDate}}</strong>
		    	</div>
			</div>
			<div class="c25l">
		    	<div class="subcl type-text">
		      		<strong>{{endDate}}</strong>
		    	</div>
			</div>
		</div>
	</script>

	<script id="period-row-edit-template" type="text/x-handlebars-template">
		<form id="edit-form" class="full yform" role="application">
			<div class="subcolumns period">
				<div class="type-text">
		      		<input type="hidden" name="id_season" value="{{id_season}}"/>
					{{#id}}<span class="row-sub-item-destroy"></span>{{/id}}
					<div class="c33l">
		    			<div class="subcl type-text">
		      				<span><s:text name="from"/>:</span> 
		      				<input type="text" class="datepicker required" name="startDate" value="{{startDate}}" style="display: inline;"/>
		    			</div>
					</div>
					<div class="c33l">
		    			<div class="subcl type-text">
		      				<span><s:text name="to"/>:</span> 
		      				<input type="text" class="datepicker required" name="endDate" value="{{endDate}}" style="display: inline;"/>
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

	<script id="periods-view-template" type="text/x-handlebars-template" >
		<div class="subcolumns period">
			<div class="c25l">
		      	<strong><s:text name="from"/>:</strong>
			</div>
			<div class="c33l">
		      <strong><s:text name="to"/>:</strong>
			</div>
		</div>
		<div class="wrapper">
			<ul class="periodDates-list"></ul>
			<div class="add-new">
				<button class="btn_add"><s:text name="periodAddNew"/></button>
			</div>
		</div>
	</script>
