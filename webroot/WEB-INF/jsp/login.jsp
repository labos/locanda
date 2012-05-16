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
<jsp:include page="layout/header.jsp" />
      <div id="home">
      	<h1><s:text name="login"/></h1>
      	
      	<s:url action="login" var="url"></s:url>
      	<s:url action="goCreateAccount" var="url_account" />
      	<s:actionerror /><s:fielderror></s:fielderror>
      	<form method="post" action="<s:property value="url"/>" class="yform" role="application" autocomplete="off">
            <fieldset>
              <legend><s:text name="loginData" /></legend>
              <div class="type-text">
                <label for="email"><s:text name="email"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                <input type="text" class="required email beauty" name="email" id="email" size="20"  aria-required="true"/>
              </div>
              <div class="type-text">
                <label for="password"><s:text name="password"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
                <input type="password" class="required beauty" name="password" id="password" size="20"  aria-required="true"/>
              </div>
            <div class="type-button">
          	  &nbsp;&nbsp;<button class="btn_submit" type="submit" role="button" aria-disabled="false"><s:text name="login"/></button>
            </div>
            </fieldset>
          </form>
            <div  id="signup" class="type-text">
            <ul><li><strong><s:text name="notRegistered"/></strong></li><li><a href="<s:property value="url_account"/>"><s:text name="signup"/></a></li></ul>
            </div>
      </div><!-- end: #home -->
<jsp:include page="layout/footer.jsp" />         