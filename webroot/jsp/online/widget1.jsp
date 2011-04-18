<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
<!-- Start of first page -->
<div data-role="page" id="foo2">

	<div data-role="header">
		<h1>Select your arrival date</h1>
		<a href="goOnlineBookingCalendar.action" class="ui-btn-right" data-icon="home" data-iconpos="notext" 
           data-direction="reverse">Home</a> 
	</div><!-- /header -->

	<div data-role="content">
	<div class="alert"></div>	
	<div id='largeDatepicker'></div>
	<!--
		<p>I'm first in the source order so I'm shown as the page.</p>		
		-->
		<p>View internal page called <a href="#bar">bar</a></p>	
		            <form action="goOnlineBookingRooms.action" class="" method="post">
            <input type="hidden" name="dateArrival" value=""/>
            <div class="c33l">
            <div data-role="fieldcontain">
	<label for="select-choice-1" class="select">Nights</label>
	<select name="numNight" id="select-choice-1">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
	</select>
</div>
</div>
           
           <div class="c33l">
            <div data-role="fieldcontain">
	<label for="select-choice-2" class="select">People</label>
	<select name="numGuests" id="select-choice-2">
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
	</select>
</div>
          

            				</div>

            	 <div class="c33l">
<div data-role="fieldcontain">
<p>&nbsp;</p>
<button type="submit" data-theme="b" id="btn_widg_next">NEXT</button>
	<!--<a href="index.html" data-role="button" data-theme="b">NEXT</a>
--></div>

            </div>
           </form>
	</div><!-- /content -->

	<div data-role="footer">
		<h4>Locanda Mobile Widget</h4>
	</div><!-- /header -->
</div><!-- /page -->


