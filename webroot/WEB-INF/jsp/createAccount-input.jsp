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



<s:url action="createAccount" var="url_createAccount"></s:url>
<jsp:include page="layout/header.jsp" />
<div id="home">
	<h1><s:text name="title"/> - <s:text name="createAccount"/></h1>
	<p><s:text name="homeWelcomeMessage"/>.</p>
	<s:actionerror/><s:fielderror></s:fielderror>

	<form role="application" class="yform" action="<s:property value="url_createAccount"/>" method="post">
	  <fieldset>
		<legend><s:text name="insertyourData" /></legend>
		<div class="type-text">
		  <label for="firstname"><s:text name="name"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
		  <input type="text" size="20" id="firstname" name="user.name" class="beauty required" value="<s:property value="user.name"/>"/>
		</div>
		<div class="type-text">
		  <label for="surname"><s:text name="lastName"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
		  <input type="text" size="20" id="surname" name="user.surname" class="beauty required" value="<s:property value="user.surname"/>"/>
		</div>
		<div class="type-text">
		  <label for="email"><s:text name="email"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
		  <input type="text" aria-required="true" size="20" id="email" name="user.email" class="beauty required email" value="<s:property value="user.email"/>"/>
		</div>
		<div class="type-text">
		  <label for="phone"><s:text name="phone"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
		  <input type="text" aria-required="true" size="20" id="phone" name="user.phone" class="beauty required validPhone" value="<s:property value="user.phone"/>"/>
		</div>
	  </fieldset>
	  <div class="type-button">
		<button class="btn_submit" type="submit" role="button" aria-disabled="false"><s:text name="createAccount"/></button>
	  </div>
	</form>

	<p id="home_images"></p>
</div>
<jsp:include page="layout/footer.jsp" />