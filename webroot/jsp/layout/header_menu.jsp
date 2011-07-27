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
<%@ page import="java.util.Locale" %>
<%@ page import = "com.opensymphony.xwork2.ActionContext;"%> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel='stylesheet' type='text/css' href='css/reset.css' />
<!--
<link rel='stylesheet' type='text/css' href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/start/jquery-ui.css' />
-->
<link rel='stylesheet' type='text/css' href='css/south-street/jquery-ui-1.8.9.custom.css' />
<link rel='stylesheet' type='text/css' href='css/jquery.weekcalendar.css' />
<link rel='stylesheet' type='text/css' href='css/calendar.css' />
<link rel='stylesheet' type='text/css' href='css/jquery.jgrowl.css' />
<link rel="stylesheet" type='text/css' href="css/jquery.fileupload-ui.css" />
    
<script type='text/javascript' src='js/lib/jquery.min.js'></script>
<!--<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>-->
<script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
<script type='text/javascript' src="js/jquerymx-1.0.custom.js"></script>
<script type='text/javascript' src='js/ftod.js'></script>
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type='text/javascript' src='js/jquery.form.js'></script>
<script type='text/javascript' src='js/jquery.fileupload.js'></script>
<script type='text/javascript' src='js/jquery.fileupload-ui.js'></script>
<script type='text/javascript' src="js/jquery.fileupload-uix.js"></script>
<script type='text/javascript' src="js/jquery.i18n.js"></script>
<%
//Locale locale = (locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
String lang ="en";
Locale locale = ActionContext.getContext().getLocale();
if (locale != null){
	
	lang = locale.getLanguage();
}
%>
<script type='text/javascript' src='lang/jquery.<s:property value="#request.locale.getLanguage()" />.json'></script>
<script>
$(document).ready(function () { <%
    //code for menu tabs activation
    String dPageDefault = "planner";
    String dPage = request.getParameter("sect");
    dPage = (dPage == null) ? dPageDefault : dPage;
    out.println("\n var section= \'" + dPage + "\';"); 
    %>
    var text_tab = $("#" + section).children("a").hide().text();
    $("#" + section).addClass("active").prepend("<strong>" + text_tab + "</strong>");
    I18NSettings = {};
    I18NSettings.datePattern = '<s:property value="#session.datePattern"/>'.toLowerCase();
    I18NSettings.ita = "ita";
    //to avoid undefined warning on pre-login phase...
    if (typeof I18NSettings.datePattern === 'undefined') {
        I18NSettings.datePattern = "dd/mm/yy";
    }
    //$._.setLocale('<s:property value="#request.locale" />');
    $._.setLocale('<s:property value="#request.locale.getLanguage()" />');
   
  
});
</script>
<script type='text/javascript' src='js/jquery.weekcalendar.js'></script>
<script type='text/javascript' src='js/jquery.validate.min.js'></script>
<script type='text/javascript' src='js/jquery.metadata.js'></script>
<s:if test="#request.locale.getLanguage() != 'en'">
<script type="text/javascript" src="lang/messages_<s:property value="#request.locale.getLanguage()" />.js"></script>
</s:if>
<script type='text/javascript' src="js/steal/steal.js?loader.js"></script>
<script type='text/javascript' src='js/jquery.jgrowl_minimized.js'></script>
<script type='text/javascript' src='js/ejs_production.js'></script>
<script type='text/javascript' src='js/view.js'></script>    
  <title>LOCANDA - Open Source Booking Tool</title><!-- (en) Add your meta data here -->
  <!-- (de) Fuegen Sie hier ihre Meta-Daten ein -->
  <link href="css/layout_sliding_door.css" rel="stylesheet" type="text/css" />
  
  <!--[if lte IE 7]>
	<link href="css/patches/patch_sliding_door.css" rel="stylesheet" type="text/css" />
  <![endif]-->
</head>
<body>
  <!-- skip link navigation -->

  <ul id="skiplinks">
    <li><a class="skip" href="#nav">Skip to navigation (Press Enter).</a></li>
    <li><a class="skip" href="#col3">Skip to main content (Press Enter).</a></li>
  </ul>
  
<s:url action="logout" var="url_logout"></s:url>
<s:url action="home" var="url_home"></s:url>
<s:url action="findAllRooms" var="url_findallroom"></s:url>
<s:url action="findAllRoomTypes" var="url_findallroomtypes"></s:url>
<s:url action="findAllGuests" var="url_findallguest"></s:url>
<s:url action="findAllExtras" var="url_findallextra"></s:url>
<s:url action="findAllSeasons" var="url_findallseasons"></s:url>
<s:url action="findAllConventions" var="url_findallconventions"></s:url>
<s:url action="goUpdateDetails" var="url_details"></s:url>
<s:url action="goFindAllRoomPriceLists" var="url_findallroompricelists"></s:url>
<s:url action="goFindAllExtraPriceLists" var="url_findallextrapricelists"></s:url>
<s:url action="goOnlineBookings" var="url_onlinebookings"></s:url>
<s:url action="download" var="url_download"></s:url>

<div class="page_margins">
<div class="page">
<div id="header" role="banner">
<div id="topnav" role="contentinfo"><span><a
	title="logout" class="logout" href="<s:property value="url_logout"/>"></a></span>
<div class="langMenu"></div>
</div>

<h1><a href="<s:property value="url_home"/>?sect=planner" ><span>&nbsp;</span></a><em>&nbsp;</em></h1>
<span></span></div>
<!-- begin: main navigation #nav -->
<div id="nav" role="navigation">
<div class="hlist">
<ul>
	<li id="planner"><a
		href="<s:property value="url_home"/>?sect=planner"><s:text
		name="planner" /></a></li>
	<li id="accomodation"><a href="#"><s:text name="accomodation" /></a>
	<ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallroom"/>?sect=accomodation"><s:text
			name="accomodation" /></a></li>
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallroomtypes"/>?sect=accomodation"><s:text
			name="roomTypes" /></a></li>
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallextra"/>?sect=accomodation"><s:text
			name="extras" /></a></li>
	</ul>
	</li>
	<li id="guests"><a
		href="<s:property value="url_findallguest"/>?sect=guests"><s:text
		name="guests" /></a></li>
	<li id="reports"><a href="#"><s:text name="reports" /></a></li>
	<li id="settings"><a href="#"><s:text name="settings" /></a>
	<ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
		<li class="ui-menu-item"><a
			href="<s:property value="url_onlinebookings"/>?sect=settings"><s:text
			name="onlineBooking" /></a></li>
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallseasons"/>?sect=settings"><s:text
			name="seasons" /></a></li>
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallroompricelists"/>?sect=settings"><s:text
			name="roomPriceList" /></a></li>
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallextrapricelists"/>?sect=settings"><s:text
			name="extraPriceList" /></a></li>
		<li class="ui-menu-item"><a
			href="<s:property value="url_findallconventions"/>?sect=settings"><s:text
			name="conventions" /></a></li>
		<!--<li class="ui-menu-item"><a href="emails.jsp?sect=settings"><s:text name="email"/></a></li>
    			-->
		<li class="ui-menu-item"><a
			href="<s:property value="url_details"/>?sect=settings"><s:text
			name="structureDetails" /></a></li>
	</ul>
	</li>
	<li><a href="#"><s:text name="help" /></a>
	<!--<ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
		<li class="ui-menu-item"><a
			href="<s:property value="url_download"/>?sect=help">Download</a></li>
	</ul>

	--></li>
</ul>
</div>
</div>
<!-- end: main navigation --> <!-- begin: main content area #main -->