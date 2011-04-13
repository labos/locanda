<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
   <link href="css/layout_sliding_door.css" rel="stylesheet" type="text/css" />
  
  <!--[if lte IE 7]>
	<link href="css/patches/patch_sliding_door.css" rel="stylesheet" type="text/css" />
  <![endif]-->   
<script type='text/javascript' src='js/lib/jquery-1.4.4.min.js'></script>
<!--
<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>
-->

<script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
<script type='text/javascript' src='js/jquery.fileupload.js'></script>
<script type='text/javascript' src='js/jquery.fileupload-ui.js'></script>
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
<script type='text/javascript' src='js/ftod.js'></script>
<script type='text/javascript' src='js/jquery.validate.min.js'></script>
<script type='text/javascript' src='js/jquery.weekcalendar.js'></script>
<script type='text/javascript' src='js/main.js'></script>
<script type='text/javascript' src='js/jquery.jgrowl_minimized.js'></script>
<script type='text/javascript' src='js/jquery.form.js'></script>