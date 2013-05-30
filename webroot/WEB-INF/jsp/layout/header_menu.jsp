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

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<link rel='stylesheet' type='text/css' href='css/reset.css' />
	<!--<link rel='stylesheet' type='text/css' href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/start/jquery-ui.css' />-->
	<link rel="stylesheet" type="text/css" href="css/colorbox.css" />
	<link rel='stylesheet' type='text/css' href='css/south-street/jquery-ui-1.8.9.custom.css' />
	<link rel='stylesheet' type='text/css' href='css/jquery.jgrowl.css' />
	<link rel="stylesheet" type='text/css' href="css/jquery.fileupload-ui.css" />
	<link rel="stylesheet" type="text/css" href="css/layout_sliding_door.css" />
  	<link rel="shortcut icon" type="image/x-icon" href="images/favicon.png">
	<title><s:text name="titleExtended"/></title>

	<!-- (de) Fuegen Sie hier ihre Meta-Daten ein -->
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
	<s:url action="findAllFacilities" var="url_findallfacilities"></s:url>
	<s:url action="findAllImages" var="url_findallimages"></s:url>
	<s:url action="goUpdateDetails" var="url_details"></s:url>
	<s:url action="goFindAllRoomPriceLists" var="url_findallroompricelists"></s:url>
	<s:url action="goFindAllExtraPriceLists" var="url_findallextrapricelists"></s:url>
	<s:url action="goOnlineBookings" var="url_onlinebookings"></s:url>
	<s:url action="goExport" var="url_export"></s:url>
	<s:url action="goAboutInfo" var="url_about"></s:url>

	<div class="page_margins">
		<div class="page">
			<div id="header" role="banner">
				<div id="topnav" role="contentinfo">
					<span><a title="logout" class="logout" href="<s:property value="url_logout"/>"></a></span>
					<div class="langMenu"></div>
				</div>
				<h1>
					<a href="<s:property value="url_home"/>?sect=planner">
						<span>
							<img src="images/logo.png" alt="" class="left" height="100%"/>
						</span>	
					</a>
					<em>&nbsp;</em>
				</h1>
			</div>
			<!-- begin: main navigation #nav -->
			<div id="nav" role="navigation">
				<div class="hlist">
					<ul>
						<li id="planner"><a href="<s:property value="url_home"/>?sect=planner"><s:text name="planner" /></a></li>
						<li id="accomodation"><a href="#"><s:text name="accomodation" /><span class="menu-dropdown ui-button-icon-secondary ui-icon ui-icon-triangle-1-s"></span></a>
							<ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
								<li class="ui-menu-item"><a href="<s:property value="url_findallroom"/>?sect=accomodation"><s:text name="rooms" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallroomtypes"/>?sect=accomodation"><s:text name="roomTypes" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallextra"/>?sect=accomodation"><s:text name="extras" /></a></li>
							</ul>
						</li>
						<li id="guests"><a href="<s:property value="url_findallguest"/>?sect=guests"><s:text name="guests" /></a></li>
						<li id="settings"><a href="#"><s:text name="settings" /><span class="menu-dropdown ui-button-icon-secondary ui-icon ui-icon-triangle-1-s"></span></a>
							<ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
								<li class="ui-menu-item"><a href="<s:property value="url_onlinebookings"/>?sect=settings"><s:text name="onlineBooking" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallseasons"/>?sect=settings"><s:text name="seasons" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallroompricelists"/>?sect=settings"><s:text name="roomPriceLists" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallextrapricelists"/>?sect=settings"><s:text name="extraPriceLists" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallconventions"/>?sect=settings"><s:text name="conventions" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallfacilities"/>?sect=settings"><s:text name="facilities" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_findallimages"/>?sect=settings"><s:text name="images" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_details"/>?sect=settings"><s:text name="structureSettings" /></a></li>
								<li class="ui-menu-item"><a href="<s:property value="url_export"/>?sect=settings"><s:text name="exportPage" /></a></li>
							</ul>
						</li>
						<li><a href="#"><s:text name="help" /><span class="menu-dropdown ui-button-icon-secondary ui-icon ui-icon-triangle-1-s"></span></a>
							<ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
								<li class="ui-menu-item"><a href="<s:property value="url_about"/>?sect=help"><s:text name="about" /></a></li>
								<li class="ui-menu-item"><a href="#" id="support"><s:text name="feedback" /></a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<!-- end: main navigation -->
			<!-- begin: main content area #main -->