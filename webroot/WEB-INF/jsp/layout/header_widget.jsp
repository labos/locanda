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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<link rel='stylesheet' type='text/css' href='css/locanda-theme.min.css' />
<link rel="stylesheet" type="text/css" href="css/jquery.mobile.datebox.min.css" /> 

<link rel='stylesheet' type='text/css' href='css/jquery.jgrowl.css' />
<link rel="stylesheet" href="css/jquery.mobile.structure-1.0.1.min.css" />
<script type='text/javascript' src='js/lib/jquery.min.js'></script>
<script type='text/javascript' src="js/lib/jquery.i18n.js"></script>
<script type='text/javascript' src='js/lang/jquery.<s:property value="#request.locale.getLanguage()" />.json'></script>
      <script>
      $(document).ready(function() {
      I18NSettings = {};
      I18NSettings.datePattern = '<s:property value="#session.datePattern"/>'.replace('yyyy', 'yy').toLowerCase();
      //to avoid undefined on pre-login phase..
      if (typeof I18NSettings.datePattern === 'undefined')
    	  {
    	  I18NSettings.datePattern ="dd/mm/yy";
    	  }
      $._.setLocale('<s:property value="#request.locale.getLanguage()" />');
      
    /*  $("#largeDatepicker").datepicker({
          dateFormat: I18NSettings.datePattern,
          onSelect: function (dateText, inst) {
              var selectedData = $.datepicker.formatDate(I18NSettings.datePattern, $(this).datepicker("getDate"));
              if (selectedData) {
                  $('input:hidden[name="booking.dateIn"]').val(selectedData);
              }
          }
      });
  */    
  
  jQuery.extend(jQuery.mobile.datebox.prototype.options, {
	    'dateFormat': I18NSettings.datePattern
	});
	  $('#dateIn').bind('datebox', function(e, p) {
	    if ( p.method === 'set' ) {
	      e.stopImmediatePropagation()
	      //DO SOMETHING//
	     var selectedData = $.datepicker.formatDate(I18NSettings.datePattern, $('#dateIn').data('datebox').theDate);

            if (selectedData) {
                $('input:hidden[name="booking.dateIn"]').val(selectedData);
                $(".alert").hide();
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
      
      $.ajaxSetup({cache: false});

      });
</script>
<script type='text/javascript' src='js/lib/jquery.validate.min.js'></script>
<script type='text/javascript' src='js/lib/jquery.metadata.js'></script>
<s:if test="#request.locale.getLanguage() != 'en'">
<script type="text/javascript" src="js/lang/messages_<s:property value="#request.locale.getLanguage()" />.js"></script>
</s:if>
<script type='text/javascript' src='js/lib/jquery.form.js'></script>
<script type="text/javascript" src="js/lib/jquery.mobile-1.0.1.min.js"></script>
<script type="text/javascript" src="js/lib/jquery-ui-datepicker.custom.min.js"></script>
<script type="text/javascript" src="js/lib/jquery.mobile.datebox.min.js"></script>
<script type="text/javascript" src="js/lang/jquery.mobile.datebox.i8n.<s:property value="#request.locale.getLanguage()" />.js"></script>
<script type="text/javascript" src="js/controllers/online_controller.js"></script>
<style>

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
color:red;
font-weight:600;
-webkit-border-radius: 4px;
-moz-border-radius: 4px;
border-radius: 4px;
text-shadow:0 -1px 2px #000000;
box-shadow: 0 5px 6px rgba(0, 0, 0, 0.3);
color: #fff;
font-size: 100%;
font-weight: bold;
margin-left: auto;
margin-right: auto;
padding: 10px 20px 10px 30px;
text-align: center;
text-decoration: none;
text-shadow: 0 1px 1px grey;
background: #efc5ca; /* Old browsers */
background: -moz-linear-gradient(top, #efc5ca 0%, #d24b5a 50%, #ba2737 51%, #f18e99 100%); /* FF3.6+ */
background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#efc5ca), color-stop(50%,#d24b5a), color-stop(51%,#ba2737), color-stop(100%,#f18e99)); /* Chrome,Safari4+ */
background: -webkit-linear-gradient(top, #efc5ca 0%,#d24b5a 50%,#ba2737 51%,#f18e99 100%); /* Chrome10+,Safari5.1+ */
background: -o-linear-gradient(top, #efc5ca 0%,#d24b5a 50%,#ba2737 51%,#f18e99 100%); /* Opera 11.10+ */
background: -ms-linear-gradient(top, #efc5ca 0%,#d24b5a 50%,#ba2737 51%,#f18e99 100%); /* IE10+ */
background: linear-gradient(top, #efc5ca 0%,#d24b5a 50%,#ba2737 51%,#f18e99 100%); /* W3C */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#efc5ca', endColorstr='#f18e99',GradientType=0 ); /* IE6-9 */ 
}

.error {
color:#800;
font-weight:600;
}

.ui-content .ui-listview {
margin:0 auto;
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

.ui-grid-a label.ui-select{
text-align:center;
}

.ui-btn-up-head-locanda {
	background: none;
    border: none;
    color: #000000;
    font-weight: bold;
    text-shadow: 0 1px 1px #EEEEEE;
}
p.resume {
    color: #620820;
    font-family: Arial,sans-serif;
    font-size: 12px;
    margin: 0 0 2px;
}
</style>

	</head>
	<s:set var="redirectLang" value="#context['struts.actionMapping'].name" />
<s:url id="localeFR" namespace="/" action="locale" >
   <s:param name="request_locale" >fr</s:param>
</s:url>
<s:url id="localeEN" namespace="/" action="locale" >
   <s:param name="request_locale" >en</s:param>
   <s:param name="redirect" ><s:property value="#redirectLang"/>.action?idStructure=<s:property value="structure.id"/></s:param>
</s:url>
<s:url id="localeIT" namespace="/" action="locale" >
   <s:param name="request_locale" >it</s:param>
     <s:param name="redirect" ><s:property value="#redirectLang"/>.action?idStructure=<s:property value="structure.id"/></s:param>
</s:url>