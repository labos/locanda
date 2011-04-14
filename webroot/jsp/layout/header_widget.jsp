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
	<link rel="stylesheet" href="css/jquery.mobile-1.0a4.1.css" />
	<script type="text/javascript" src="js/jquery.mobile-1.0a4.1.js"></script>

<style>
	body {
		background: #4D87C7;
		padding: 0px;
		
	}
		#largeDatepicker {

			width: 99%;
			height: 80%;
			}
	.ui-datepicker {

		width:100%;
		height: 100%;
		}
		
			.widget-booking {

		width:100%;
		height: 100%;
		text-align: left;
		}
		
		.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
		
		height:30px;
			}
			h2{
			color: #fff;
			}
			.yform label {
color:#fff;
text-align: left;
}
.title_widget {
text-align: left;
}

.yform {
margin: 0;
padding: 0;

}
.ui-widget {
margin-top: 0px;
}

.price_room_widget{
	font-size: 18px;
	}
	.price_room_widget span{
	font-size: 11px;
	}
	.subcolumns {
	background-color: #6C8DD4;
	}
	h1, h4 {
	color: #fff;
	}
</style>

<script>
	$(function() {
		
		
		$( "#largeDatepicker" ).datepicker({
			dateFormat: I18NSettings.datePattern,
			 onSelect: function(dateText, inst) {
				 var selectedData =  $.datepicker.formatDate(I18NSettings.datePattern,$(this).datepicker("getDate"));
				 if ( selectedData)
					 {
						$('input:hidden[name="dateArrival"]').val(selectedData);

					 }
				  
			 }
			
		});
		
	  	$(".btn_next").button({
	  		icons: {
	            primary: "ui-icon-seek-next"
	        }
	  	});
	  	
	  	
	  	$(".btn_next").click(function(event){
	  		//event.preventDefault();
	  		//goOnlineBookingCalendar
	  		//goOnlineBookingRooms
	  		//goOnlineBookingExtras
	  		//goOnlineBookingGuest
	  		//goOnlineBookingFinal
	  		if ( $(this).parents(".yform.json").valid() )
	  		{
	  			
	  			var formData = $(this).parents(".yform.json").serialize();
	  			var action = $(this).parents(".yform.json").attr("action");

	  			$.ajax({
	  		      url: action,
	  		      global: false,
	  		      type: "POST",
	  		      data: formData,
	  		      dataType: "html",
	  		     beforeSend: function(msg){
	  		    	$(".widget-booking").html("<img src=\"images/loading.gif\" />");
	  		    	 
	  		      },
	  		      success: function(data){
	  		    	$(".widget-booking").html(data);
	  		      },
	  		     error: function(msg){
	  		         alert(msg);
	  		      }
	  		   }
	  		)
	  			
	  		}
	  		
	  		// return false;
	  	});
	  	
	});
	
	
	</script>