<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
  <!-- Start of first page -->
	<div data-role="page" id="foo3">

	  <div data-role="header">
	  	  <div style="position:absolute; left:1px; top:1px;">
			<a href="goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" class="ui-link" rel="external" >
				<img src="images/logo_small.png" /></a>
          </div>
		<h1><s:text name="extrasSelect" /></h1>
		<a href="goOnlineBookingCalendar.action?idStructure=<s:property value="structure.id"/>" class="ui-btn-right" rel="external" data-icon="home" data-iconpos="notext" data-direction="reverse">Home</a> 
	  </div><!-- /header -->

	  <div data-role="content">
	  <div class="contentHeader">
	    <ul data-role="listview" data-theme="a" data-inset="true">
		    <li>
		      <p class="resume"><s:text name="dateIn" />: <strong><s:property value="booking.dateIn"/></strong>, <s:text name="nights"/>: <strong><s:property value="booking.calculateNumNights()"/></strong>&nbsp;, <s:text name="persons"/>: <strong><s:property value="booking.nrGuests"/></strong><img src="images/<s:property value="booking.nrGuests"/>.png" alt="<s:property value="booking.nrGuests"/>" /></p>
		      <p class="resume"><s:text name="subtotal" />: <strong><s:property value="booking.roomSubtotal"/></strong></p>
			</li>
		</ul>
	</div>			
		<form action="goOnlineBookingGuest.action" method="post">
		  <fieldset data-role="controlgroup">
          	<s:iterator value="extras" var="eachExtra"  status="extraStatus">
              <legend></legend>
			  <input type="checkbox" name="bookingExtrasId" id="checkbox-<s:property value="#extraStatus.index"/>" class="custom" value="<s:property value="#eachExtra.id"/>" />
			  <label for="checkbox-<s:property value="#extraStatus.index"/>"><s:property value="#eachExtra.name"/> (<s:property value="#eachExtra.resourcePriceType"/> / <s:property value="#eachExtra.timePriceType"/>)</label>
            </s:iterator>
    	  </fieldset>
		  <div class="c33l">
			<div data-role="fieldcontain">
			  <p>&nbsp;</p>
			  <button type="submit" data-theme="b" data-icon="arrow-r"><s:text name="next" /></button>
			  <!--<a href="index.html" data-role="button" data-theme="b">NEXT</a>-->
			</div>
		  </div>
		</form>
	  </div><!-- /content -->

	  <div data-role="footer">
		<h4><s:text name="title"/> <s:text name="onlineWidget"/></h4>
	  </div><!-- /header -->
	</div><!-- /page -->