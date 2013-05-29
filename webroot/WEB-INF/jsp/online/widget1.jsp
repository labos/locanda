<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
	<!-- Start of first page -->
	<div data-role="page" id="foo2">
	  <div data-role="header" data-position="inline">
		<div style="position:absolute; left:1px; top:1px;">
			<a href="goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" class="ui-link" rel="external" >
				<img src="images/logo_small.png" />
			</a>
	    </div>
		<h1><s:text name="selectDateOnline"/></h1>
          <div style="position:absolute; right:1px; top:1px;">
            <select data-theme="head-locanda" data-mini="true" name="select-choice-1" id="choice-language" data-icon="locanda-<s:property value="#request.locale.getLanguage()"/>" data-native-menu="false">
			  <option value="standard"> </option>
			  <option value="<s:property escape="false" value="localeIT"/>">Italiano</option>
			  <option value="<s:property escape="false" value="localeEN"/>">English</option>
		    </select>
 		  </div>    
	  </div><!-- /header -->

	  <div data-role="content" data-theme="a">
	  <div data-role="collapsible" data-theme="b" data-content-theme="b" class="alert"></div>
		<div id='largeDatepicker'><input name="dateIn" id="dateIn" type="date" data-role="datebox"
   data-options='{"useInline": true, "useInlineHideInput": true}'></div>
   	<div data-role="fieldcontain">
		<form action="goOnlineBookingRooms.action" class="" method="post">
          <input type="hidden" name="booking.dateIn" value=""/>
		  <fieldset class="ui-grid-a">
			<div class="ui-block-a">
				<label for="select-choice-1" class="select"><s:text name="numNights"/></label>
				<select name="numNights" id="select-choice-1" class="required">
					  	<option value=""><s:text name="numNights"/></option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
				</select></div>
			<div class="ui-block-b">
				<label for="select-choice-2" class="select"><s:text name="guests"/></label>
				<select name="booking.nrGuests" id="select-choice-2" class="required">
						  <option value=""><s:text name="guests"/></option>
						  <option value="1">1</option>
						  <option value="2">2</option>
						  <option value="3">3</option>
						  <option value="4">4</option>
				      	  <option value="5">5</option>
						  <option value="6">6</option>
						  <option value="7">7</option>
				</select>
			</div>	   
		 </fieldset>
                  <button type="submit" data-theme="b" data-icon="arrow-r" id="btn_widg_next">
                      <s:text name="next" />
                  </button>
        </form>
        </div>
      </div><!-- /content -->
      
	  <div data-role="footer">
        <h4><s:text name="title"/> <s:text name="onlineWidget"/></h4>
      </div><!-- /header -->
    </div><!-- /page -->