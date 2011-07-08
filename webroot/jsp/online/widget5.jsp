<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" /><!-- Start of first page -->
<div data-role="page" id="foo5">
  <div data-role="header">
    <h1><s:text name="resumeBooking" /></h1>
    <a href="goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" class="ui-btn-right" rel="external" data-icon="home" data-iconpos="notext" data-direction="reverse"><s:text name="home"/></a>
  </div>
  <!-- /header -->
  <div data-role="content">
    <div class="alert">
    </div>
    <strong><s:text name="congratulation" />, <s:property value="booking.booker.firstName"/> <s:property value="booking.booker.lastName"/>,
      <br/>
      <s:text name="onlineBookingSuccess" />.
    </strong>
    <div data-role="fieldcontain">
      <ul data-role="listview" data-theme="c">
        <li>
          <b><s:text name="dateIn"/>:</b><strong><s:property value="booking.dateIn"/></strong>
        </li>
        <li>
          <b><s:text name="nights"/>:</b><strong><s:property value="booking.calculateNumNights()"/></strong>
        </li>
        <li>
          <b><s:text name="persons"/>:</b><strong><s:property value="booking.nrGuests"/></strong>
        </li>
        <li>
          <b><s:text name="subtotalRoom"/>:</b> &euro;<strong><s:property value="booking.roomSubtotal"/></strong>
        </li>
        <li>
          <b><s:text name="subtotalExtra"/>:</b> &euro;<strong><s:property value="booking.extraSubtotal"/></strong>
        </li>
        <li>
          <b><s:text name="subtotal"/>:</b> &euro;<strong class="red"><s:property value="booking.roomSubtotal + booking.extraSubtotal"/></strong>
        </li>
      </ul>
      <p>
        <s:text name="onlineCall"/>: <s:property value="structure.name"/> <s:text name="onlineCallByPhone"/>: <a href="tel:<s:property value="structure.phone"/>"><s:property value="structure.phone"/></a>
        <br/>
      </p>
      <p>
        <s:text name="onlineSendEmail"/>: <s:property value="structure.name"/>: <a href="mailto:<s:property value="structure.email"/>?subject=Info Booking (<s:property value="structure.name"/>)"><s:property value="structure.email"/></a>
        <br/>
      </p>
      <p>
        <img src="http://maps.google.com/maps/api/staticmap?center=<s:property value="structure.city"/>, <s:property value="structure.country"/>&zoom=15&size=200x200&maptype=roadmap&sensor=false" />
      </p><a href="goOnlineBookingCalendar.action?idStructure=<s:property value="structure.id"/>" rel="external" data-role="button" data-icon="check" data-theme="b"><s:text name="finish"/></a>
    </div>
  </div><!-- /content -->
  <div data-role="footer">
    <h4>Locanda <s:text name="onlineWidget"/></h4>
  </div><!-- /header -->
</div><!-- /page -->