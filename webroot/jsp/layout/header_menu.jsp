<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<script type='text/javascript' src='js/ftod.js'></script>
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type='text/javascript' src='js/jquery.form.js'></script>
<script type='text/javascript' src='js/jquery.fileupload.js'></script>
<script type='text/javascript' src='js/jquery.fileupload-ui.js'></script>
<script type='text/javascript' src="js/jquery.fileupload-uix.js"></script>
<script type='text/javascript' src="js/jquery.i18n.properties-min.js"></script>
<script>
      $(document).ready(function() {
      <%
      	//code for menu tabs activation
      	String dPageDefault = "planner";
 		String dPage = request.getParameter("sect");
 		dPage = (dPage == null)? dPageDefault  : dPage ;
		out.println("\n var section= \'" + dPage + "\';") ;
      %>
      var text_tab = $("#"+section).children("a").hide().text();
      $("#"+section).addClass("active").prepend("<strong>" + text_tab + "</strong>");
      I18NSettings = {};
      I18NSettings.datePattern = '<s:property value="#session.datePattern"/>'.toLowerCase();
      I18NSettings.ita = "ita";
      //to avoid undefined on pre-login phase..
      if (typeof I18NSettings.datePattern === 'undefined')
    	  {
    	  I18NSettings.datePattern ="dd/mm/yy";
    	  }

      });
</script>
<script type='text/javascript' src='js/jquery.weekcalendar.js'></script>
<script type='text/javascript' src='js/jquery.validate.min.js'></script>
<script type='text/javascript' src='js/main.js'></script>
<script type='text/javascript' src='js/jquery.jgrowl_minimized.js'></script>
  
  <title>LOCANDA - Open Source Booking Tool</title><!-- (en) Add your meta data here -->
  <!-- (de) Fuegen Sie hier ihre Meta-Daten ein -->
  <link href="css/layout_sliding_door.css" rel="stylesheet" type="text/css" />
  
  <!--[if lte IE 7]>
	<link href="css/patches/patch_sliding_door.css" rel="stylesheet" type="text/css" />
  <![endif]-->
</head>
<s:url id="localeFR" namespace="/" action="locale" >
   <s:param name="request_locale" >fr</s:param>
</s:url>
<s:url id="localeEN" namespace="/" action="locale" >
   <s:param name="request_locale" >en</s:param>
</s:url>
<s:url id="localeIT" namespace="/" action="locale" >
   <s:param name="request_locale" >it</s:param>
</s:url>
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

  <div class="page_margins">
    <div class="page">
      <div id="header" role="banner">
        <div id="topnav" role="contentinfo">
          <span><a title="logout" class="logout" href="<s:property value="url_logout"/>"></a></span>
         <span>
        <s:a href="%{localeIT}" cssClass="flag_it"></s:a>
        <s:a href="%{localeEN}" cssClass="flag_en"></s:a>
          </span>
        </div>

        <h1>
        <span>&nbsp;</span><em>&nbsp;</em></h1><span></span>
      </div><!-- begin: main navigation #nav -->
      <div id="nav" role="navigation">
        <div class="hlist">
          <ul>
            <li id="planner"><a href="<s:property value="url_home"/>?sect=planner"><s:text name="planner" /></a></li>
            <li id="accomodation"><a href="#"><s:text name="accomodation" /></a>
              <ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
    			<li class="ui-menu-item"><a href="<s:property value="url_findallroom"/>?sect=accomodation"><s:text name="accomodation" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_findallroomtypes"/>?sect=accomodation"><s:text name="roomType" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_findallextra"/>?sect=accomodation"><s:text name="extras" /></a></li>
  			  </ul>
            </li>
            <li id="guests"><a href="<s:property value="url_findallguest"/>?sect=guests"><s:text name="guests" /></a></li>
            <li id="reports"><a href="#"><s:text name="reports" /></a></li>
            <li id="settings"><a href="#"><s:text name="settings" /></a>
              <ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
    			<li class="ui-menu-item"><a href="<s:property value="url_onlinebookings"/>?sect=settings"><s:text name="onlineBooking" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_findallseasons"/>?sect=settings"><s:text name="seasons" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_findallroompricelists"/>?sect=settings"><s:text name="roomPriceList" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_findallextrapricelists"/>?sect=settings"><s:text name="extraPriceList" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_findallconventions"/>?sect=settings"><s:text name="conventions" /></a></li>
    			<li class="ui-menu-item"><a href="emails.jsp?sect=settings"><s:text name="emails" /></a></li>
    			<li class="ui-menu-item"><a href="<s:property value="url_details"/>?sect=settings"><s:text name="structureDetails" /></a></li>
  			  </ul>
            </li>         
            <li><a href="#"><s:text name="help" /></a></li>
          </ul>
        </div>
      </div><!-- end: main navigation -->
      <!-- begin: main content area #main -->