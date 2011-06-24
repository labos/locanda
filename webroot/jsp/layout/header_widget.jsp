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
<script type='text/javascript' src='js/lib/jquery.min.js'></script>
<!--
<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>
-->
<script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
<script type='text/javascript' src='js/jquery.fileupload.js'></script>
<script type='text/javascript' src='js/jquery.fileupload-ui.js'></script>
<script type='text/javascript' src="js/jquery.i18n.js"></script>
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
<script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>
<script type='text/javascript' src='js/jquery.jgrowl_minimized.js'></script>
<script type='text/javascript' src='js/jquery.form.js'></script>
<link rel="stylesheet" href="css/jquery.mobile-1.0a4.1.css" />
<script type="text/javascript" src="js/jquery.mobile-1.0a4.1.js"></script>
<style>
body {
background:#4D87C7;
padding:0;
}

#largeDatepicker {
width:99%;
height:80%;
}

.ui-datepicker {
width:100%;
height:100%;
}

.widget-booking {
width:100%;
height:100%;
text-align:left;
}

.ui-state-default,.ui-widget-content .ui-state-default,.ui-widget-header .ui-state-default {
height:30px;
}

.yform label {
color:#fff;
text-align:left;
}

.yform {
margin:0;
padding:0;
}

.ui-widget {
margin-top:0;
}

.price_room_widget {
font-size:15px;
margin:0 auto 0 3px;
}

.price_room_widget span {
font-size:11px;
}

.subcolumns {
background-color:#6C8DD4;
}

.alert {
display:none;
border:1px dotted red;
color:red;
font-weight:600;
}

.error {
color:#800;
font-weight:600;
}

.ui-content .ui-listview {
margin:0 auto;
}

h2,h1,h4 {
color:#fff;
}

.title_widget,.resume_booking {
text-align:left;
}

.ui-icon-locanda-it {
background-image:url("images/italy-icon.png");
height:18px;
width:18px;
}

.ui-icon-locanda-en {
background-image:url("images/eng-icon.png");
height:18px;
width:18px;
}

strong.red{
color: red;}
</style>

<script>
$(function () {
    $("#largeDatepicker").datepicker({
        dateFormat: I18NSettings.datePattern,
        onSelect: function (dateText, inst) {
            var selectedData = $.datepicker.formatDate(I18NSettings.datePattern, $(this).datepicker("getDate"));
            if (selectedData) {
                $('input:hidden[name="booking.dateIn"]').val(selectedData);
            }
        }
    });
    
    $('#choice-language').change(function() {
    	 
        $("#choice-language option:selected").each(function () {
        	location.href= $(this).val();
          });

    	});
    $(".btn_next").button({
        icons: {
            primary: "ui-icon-seek-next"
        }
    });
    $("#btn_widg_next").click(function (event) {
        event.preventDefault();
        if ($(this).parents("form").valid()) {
            if ($('input:hidden[name="booking.dateIn"]').val() == "") {
                $(".alert").html($.i18n("dateInRequired")).show();
                return false;
            }
            $(this).parents("form").submit();
        }
        return false;
    });
    $('[data-role=page]').live('pagebeforecreate', function (event) {
        $("#btn_guest_next").click(function (event) {
            event.preventDefault();
            $(this).validate();
            if (!$(this).parents("form").valid()) {
                return false;
            }
            $(this).parents("form").submit();
        });
    });
});
	</script>
	</head>
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