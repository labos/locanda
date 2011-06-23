<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
<!-- Start of first page -->
<div data-role="page" id="foo3">

	<div data-role="header">
		<h1>Select extras (optional)</h1>
				<a href="goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" class="ui-btn-right" rel="external" data-icon="home" data-iconpos="notext" 
           data-direction="reverse">Home</a> 
	</div><!-- /header -->

	<div data-role="content">
		<div class="contentHeader">
<p>Date Arrival: <strong><s:property value="booking.dateIn"/></strong>, <s:property value="booking.calculateNumNights()"/> nights, <s:property value="booking.nrGuests"/> persons</p>	
<p>Total Price: <strong><s:property value="booking.roomSubtotal"/></strong></p>
</div>			<form action="goOnlineBookingGuest.action" method="post">

<fieldset data-role="controlgroup">
                    <s:iterator value="extras" var="eachExtra"  status="extraStatus">
                  <legend></legend>
		<input type="checkbox" name="bookingExtrasId" id="checkbox-<s:property value="#extraStatus.index"/>" class="custom" value="<s:property value="#eachExtra.id"/>" />
		<label for="checkbox-<s:property value="#extraStatus.index"/>"><s:property value="#eachExtra.name"/> (<s:property value=""/> / <s:property value="#eachExtra.resourcePriceType"/> / <s:property value="eachExtra.timePriceType"/>)</label>
          </s:iterator>
    </fieldset>

	
<div class="c33l">
<div data-role="fieldcontain">
<p>&nbsp;</p>
<button type="submit" data-theme="b">NEXT</button>
	<!--<a href="index.html" data-role="button" data-theme="b">NEXT</a>
--></div>
</div>
</form>

         
	</div><!-- /content -->

	<div data-role="footer">
		<h4>Locanda Mobile Widget</h4>
	</div><!-- /header -->
</div><!-- /page -->