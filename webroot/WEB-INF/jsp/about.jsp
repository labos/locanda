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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		
<jsp:include page="layout/header_menu.jsp"/>
      <div id="home">
      	<h1><s:text name="title"/></h1>
      	<h3><s:text name="homeWelcomeMessage"/></h3>
   		<p><s:text name="version"/> <s:text name="currentVersion"/></p>
   		<p><b>Web:</b> <a href="http://locanda.sourceforge.net">http://locanda.sourceforge.net</a></p>
   		<p><b>GitHub:</b> <a href="https://github.com/labos/locanda">https://github.com/labos/locanda</a></p>
      </div>
<jsp:include page="layout/footer.jsp" />     