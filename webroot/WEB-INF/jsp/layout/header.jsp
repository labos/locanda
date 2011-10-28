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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link type='text/css' href='css/south-street/jquery-ui-1.8.9.custom.css' rel='stylesheet' />
<link type="text/css" href="css/layout_sliding_door.css" rel="stylesheet"  />
<script type='text/javascript' src='js/lib/jquery.min.js'></script>
<!--<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>-->
<script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
<script type='text/javascript' src="js/jquerymx-1.0.custom.js"></script>
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type='text/javascript' src='js/jquery.fileupload.js'></script>
<script type='text/javascript' src='js/jquery.fileupload-ui.js'></script>
<script type='text/javascript' src="js/jquery.i18n.js"></script>
<script>
      $(document).ready(function() {
      
      I18NSettings = {};
      I18NSettings.datePattern = '<s:property value="#session.datePattern"/>'.replace('yyyy', 'yy').toLowerCase();
      //to avoid undefined on pre-login phase..
      if (typeof I18NSettings.datePattern === 'undefined')
    	  {
    	  I18NSettings.datePattern ="dd/mm/yy";
    	  }

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

  <title>LOCANDA - Open Source Booking Tool</title><!-- (en) Add your meta data here -->
  <!-- (de) Fuegen Sie hier ihre Meta-Daten ein -->
  <!--[if lte IE 7]>
	<link href="css/patches/patch_sliding_door.css" rel="stylesheet" type="text/css" />
  <![endif]-->
</head>
<!--<s:url var="redirectLang" includeParams="get" escapeAmp="false"/>-->
<s:set var="redirectLang" value="#context['struts.actionMapping'].name" />
<s:url id="localeFR" namespace="/" action="locale" >
   <s:param name="request_locale" >fr</s:param>
</s:url>
<s:url id="localeEN" namespace="/" action="locale" >
   <s:param name="request_locale" >en</s:param>
   <s:param name="redirect" ><s:property value="#redirectLang"/>.action</s:param>
</s:url>
<s:url id="localeIT" namespace="/" action="locale" >
   <s:param name="request_locale" >it</s:param>
     <s:param name="redirect" ><s:property value="#redirectLang"/>.action</s:param>
</s:url>

<s:url action="goLogin" var="url_login"></s:url>
<body>
  <!-- skip link navigation -->

  <ul id="skiplinks">
    <li><a class="skip" href="#nav">Skip to navigation (Press Enter).</a></li>
    <li><a class="skip" href="#col3">Skip to main content (Press Enter).</a></li>
  </ul>

  <div class="page_margins">
    <div class="page">
      <div id="header" role="banner">
        <div id="topnav" role="contentinfo" style="height: 25px; float: left;">
        <div style="float: right;"><a class="login-top" href="<s:property value="url_login"/>"><s:text name="loginSignup" /></a></div>
        <div class="langMenu">
       	 <s:a href="%{localeIT}" cssClass="flag_it"></s:a>
         <s:a href="%{localeEN}" cssClass="flag_en"></s:a>
        </div>
        </div>
        <h1>
        <span>&nbsp;</span><em>&nbsp;</em></h1><span>&nbsp;</span>
      </div><!-- begin: main navigation #nav -->