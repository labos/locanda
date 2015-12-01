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

<script>
	Entity = {
		name : "guest",
		model : function(options) {
			return new Guest(options);
		},
		collection : function(options) {
			return new Guests( {}, options );
		},
		editView : function(options) {
			return new EditGuestView(options);
		},
		isDialog:true,
		idStructure : <s:property value="#session.user.structure.id"/>
	};
</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<link rel='stylesheet' type='text/css' href='css/reset.css' />
	<!--<link rel='stylesheet' type='text/css' href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/start/jquery-ui.css' />-->
	<link rel='stylesheet' type='text/css' href='css/south-street/jquery-ui-1.8.9.custom.css' />
	<link rel="stylesheet" type="text/css" href="css/colorbox.css" />
	<link rel='stylesheet' type='text/css' href='css/jquery.jgrowl.css' />
	<link rel="stylesheet" type='text/css' href="css/jquery.fileupload-ui.css" />
	<link rel="stylesheet" type="text/css" href="css/layout_sliding_door.css" />

	<title><s:text name="titleExtended"/></title>

	<!-- (de) Fuegen Sie hier ihre Meta-Daten ein -->
  	<!--[if lte IE 7]>
		<link href="css/patches/patch_sliding_door.css" rel="stylesheet" type="text/css" />
  	<![endif]-->
  	<style type="text/css"> 
		body {
			background: #FFFFFF;
		};
	</style>
</head>
<body>

<link rel='stylesheet' type='text/css'
	href='css/screen/basemod_2col_advanced.css' />

<div id="main">
	<!-- begin: #col1 - first float column -->
	<div id="col1" role="complementary">
		<div class="clearfix" id="col1_content">
			<div>
				<button class="btn_add_form" style="display:none;">
					<s:text name="addNew" />
				</button>
				<button class="btn_select_guest" style="display:none;" id="btnselectG">
					<s:text name="selectGuest" />
				</button>
			</div>
			<div class="subcolumns" id="row-edit-container" style="padding-bottom:40px"></div>
		</div>
	</div>
	<!-- end: #col1 -->

	<!-- begin: #col3 static column -->
	<div id="col3" role="main">
		<div class="clearfix" id="col3_content">
			<h2>
				<s:text name="guests" />
			</h2>
			<div id="toolbar-container"></div>
			<div id="main-app">
				<div id="nav-top"></div>
				<div id="row-list" class="back"></div>
				<div id="nav-bottom"></div>
			</div>
		</div>
		<div id="ie_clearing">&nbsp;</div>
		<!-- End: IE Column Clearing -->
	</div>
	<!-- end: #col3 -->
</div>

<jsp:include page="../templates/guest.mustache.jsp"/>

<%@ page import="java.util.Locale"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

				<div id="ie_clearing">&nbsp;</div>
				

<script type='text/javascript' src='js/lib/jquery.min.js'></script>
<!--<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>-->
<script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
<script type='text/javascript' src="js/lib/jquerymx-1.0.custom.js"></script>
<script type='text/javascript' src='js/lib/ftod.js'></script>
<script type="text/javascript" src="js/lib/jstree/jquery.jstree.js"></script>
<script type='text/javascript' src='js/lib/jquery.form.js'></script>
<script type='text/javascript' src="js/lib/jquery.i18n.js"></script>
<script type='text/javascript' src="js/lib/underscore-min.js"></script>
<script type='text/javascript' src="js/lib/backbone.js"></script>
<script type='text/javascript' src="js/lib/mustache.js"></script>
<script type='text/javascript' src='js/lib/jquery.overlay.min.js'></script>
<script type='text/javascript' src='js/lib/jquery.colorbox.js'></script>

<%
//Locale locale = (locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
String lang ="en";
Locale locale = ActionContext.getContext().getLocale();
if (locale != null){
	
	lang = locale.getLanguage();
}
%>

<script type='text/javascript' src='js/lang/jquery.<s:property value="#request.locale.getLanguage()" />.json'></script>
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
    	I18NSettings.datePattern = '<s:property value="#session.datePattern"/>'.replace('yyyy', 'yy').toLowerCase();
    	//to avoid undefined warning on pre-login phase...
    	if (typeof I18NSettings.datePattern === 'undefined') {
    	    I18NSettings.datePattern = "dd/mm/yy";
    	}
    	//$._.setLocale('<s:property value="#request.locale" />');
    	$._.setLocale('<s:property value="#request.locale.getLanguage()" />');
    	I18NSettings.language = '<s:property value="#request.locale.getLanguage()" />';
	});
</script>
<script type='text/javascript' src='js/lib/jquery.validate.min.js'></script>
<script type='text/javascript' src='js/lib/jquery.metadata.js'></script>
<s:if test="#request.locale.getLanguage() != 'en'">
	<script type="text/javascript" src="js/lang/messages_<s:property value="#request.locale.getLanguage()" />.js"></script>
</s:if>
<script type='text/javascript' src="js/lib/steal/steal.js?loader.js"></script>
<script type='text/javascript' src='js/lib/jquery.jgrowl_minimized.js'></script>
<!-- full skiplink functionality in webkit browsers -->
<script src="yaml/core/js/yaml-focusfix.js" type="text/javascript"></script>
<!--[if IE]>
<style>
.wc-scrollable-grid {
	width: 99% !important;
}
</style>
<![endif]-->   

</body>
</html>