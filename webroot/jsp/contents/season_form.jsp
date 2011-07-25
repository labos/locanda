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
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

  <form method="post" action="saveUpdateSeason.action" class="yform json" role="application"><input type="hidden" name="redirect_form" value="findAllSeasons.action?sect=settings" /> 
	<input type="hidden" name="season.id" value="<s:property value="season.id"/>"/>
	<fieldset>
	  <legend><s:text name="seasons"/></legend>
		<div class="subcolumns"><a href="#top_anchor" class="add_period" title="add period"><s:text name="periodAddNew"/></a></div>
		<div class="subcolumns">
		  <div class="c33l">
		    <div class="subcl type-select">
		      <s:select label="%{getText('year')}" name="season.year" headerKey="season.year" list="#{'2011':'2011', '2012':'2012', '2013':'2013','2014':'2014','2015':'2015'}" required="true"/>
		    </div>
		  </div>
		</div>	
		<div class="subcolumns">
		  <div class="c33l">
		    <div class="subcl type-text">
		      <span><s:text name="name"/>:</span> <input type="text" name="season.name" class="required" value="<s:property value="season.name"/>" style="display: inline;"/>
		    </div>
		  </div>
		</div>
		<div class="subcolumns period">
		  <div class="c25l">
		    <div class="subcl type-text">
		      <span><s:text name="from"/>:</span> 
		      <input type="text" class="datepicker required" name="periods[0].startDate" value="<s:date name="season.periods[0].startDate" format="%{#session.datePattern}"/>" style="display: inline;"/>
		    </div>
		  </div>
		  <div class="c20l">
		    <div class="subcl type-text">
		      <span><s:text name="to"/>:</span> 
		      <input type="text"class="datepicker required" name="periods[0].endDate" value="<s:date name="season.periods[0].endDate" format="%{#session.datePattern}"/>"style="display: inline;"/>
		    </div>
		  </div>
		  <div class="c10l">
		    <label>&nbsp;</label> 
		    <input type="hidden" class="idPeriod" name="periods[0].id" value="<s:property value="season.periods[0].id"/>"/>
		    <a href="#seas_anchor_<s:property value="season.periods[0].id"/>" class="erase_period" title="erase"><s:text name="periodDelete"/></a>
		  </div>
		</div>
	  <!-- ITERATION OF PERIODS  -->
		<s:iterator value="season.periods" var="eachPeriod" status="periodStatus">
		  <s:if test="#periodStatus.index > 0">
		    <div class="subcolumns period">
		 	  <div class="c25l">
				<div class="subcl type-text">
				  <span>From:</span>
				  <input type="text" class="datepicker required" name="periods[<s:property value="#periodStatus.index"/>].startDate" value="<s:date name="#eachPeriod.startDate" format="%{#session.datePattern}"/>" style="display: inline;"/>
				</div>
			  </div>
			  <div class="c20l">
				<div class="subcl type-text">
				  <span>To:</span>
				    <input type="text" class="datepicker required" name="periods[<s:property value="#periodStatus.index"/>].endDate" value="<s:date name="#eachPeriod.endDate" format="%{#session.datePattern}"/>" style="display: inline;"/>
				</div>
			  </div>
			  <div class="c10l"><label>&nbsp;</label> 
			    <s:if test="#periodStatus.index > 0">
				  <input type="hidden" class="idPeriod" name="periods[<s:property value="#periodStatus.index"/>].id" value="<s:property value="#eachPeriod.id"/>"/>
				  <a href="#seas_anchor_<s:property value="#eachSeason.id"/>" class="erase_period" title="erase"><s:text name="periodDelete"/></a>
				</s:if>
			  </div>
			</div>
		  </s:if>
		</s:iterator>					
		
		<div class="type-button">
		  <button class="btn_save"><s:text name="save"/></button>
		  <button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
		</div>
		
		<a name="top_anchor"></a>
	</fieldset>
  </form>