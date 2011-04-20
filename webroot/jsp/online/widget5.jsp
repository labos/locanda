<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
<!-- Start of first page -->
<div data-role="page" id="foo5">

	<div data-role="header">
		<h1>Confirm Details</h1>
		<a href="goOnlineBookingCalendar.action" class="ui-btn-right"  rel="external" data-icon="home" data-iconpos="notext" 
           data-direction="reverse">Home</a> 
	</div><!-- /header -->

	<div data-role="content">
	<div class="alert"></div>	
	<strong>Congratulation, <s:property value="booking.guest.firstName"/>  <s:property value="booking.guest.lastName"/>,<br/> your booking request has been sent</strong>
	<div data-role="fieldcontain">
	<p class="resume_booking">
	 <b>Date Arrival: </b><s:property value="dateArrival"/><br/>
	 <b>Nights: </b><s:property value="numNight"/><br/>
	 <b>Number of peoples: </b><s:property value="numGuests"/><br/>
	 <b>Total Price: <strong style="color: red; font-size: 14px;"><s:property value="booking.roomSubtotal + booking.extraSubtotal"/></strong> &euro;</b>
	</p>	
	
	<p>
	Call <s:property value="#session.user.structure.name"/> by Phone: <a href="tel:<s:property value="#session.user.structure.phone"/>"><s:property value="#session.user.structure.phone"/></a><br/>
	</p>
		<p>
	Send email to <s:property value="#session.user.structure.name"/>: <a href="mailto:<s:property value="#session.user.structure.email"/>?subject=Info Booking (<s:property value="#session.user.structure.name"/>)"><s:property value="#session.user.structure.email"/></a><br/>
	</p>
		<p>
	<img src="http://maps.google.com/maps/api/staticmap?center=<s:property value="#session.user.structure.city"/>, <s:property value="#session.user.structure.country"/>&zoom=15&size=200x200&maptype=roadmap
&sensor=false" />
	</p>
	
	<a href="goOnlineBookingCalendar.action"  rel="external" data-role="button" data-icon="check" data-theme="b">FINISH</a>
</div>
	</div><!-- /content -->

	<div data-role="footer">
		<h4>Locanda Mobile Widget</h4>
	</div><!-- /header -->
</div><!-- /page -->