<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" /><!-- Start of first page -->
<div data-role="page" id="foo4">

  <script type="text/javascript">
    <!--
    /*$(document).bind("mobileinit", function()
     {
     $.extend(  $.mobile , {
     ajaxEnabled: false
     });
     });
     */
  </script>
  
  <div data-role="header">
    <h1><s:text name="bookerDetails"/></h1>
    <a href="goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" class="ui-btn-right" rel="external" data-icon="home" data-iconpos="notext" data-direction="reverse"><s:text name="home"/></a>
  </div>
  <!-- /header -->
  <div data-role="content">
    <div class="contentHeader">
      <ul data-role="listview" data-theme="c">
        <li>
          <s:text name="dateIn"/>: <strong><s:property value="booking.dateIn"/></strong>, <s:property value="booking.calculateNumNights()"/><s:text name="nights"/>, <s:property value="booking.nrGuests"/><s:text name="persons"/>
        </li>
        <li>
          <s:text name="subtotalRoom"/>: &euro;<strong><s:property value="booking.roomSubtotal"/></strong>
        </li>
        <li>
          <s:text name="subtotalExtra"/>: &euro;<strong><s:property value="booking.extraSubtotal"/></strong>
        </li>
        <li>
          <s:text name="subtotal"/>: &euro;<strong class="red"><s:property value="booking.roomSubtotal + booking.extraSubtotal"/></strong>
        </li>
      </ul>
    </div>
    <form action="goOnlineBookingFinal.action" method="post">
      <div id="firstNameDiv" data-role="fieldcontain">
        <label for="firstNo"><s:text name="firstName"/>*</label>
        <input id="firstNo" name="booking.booker.firstName" type="text" class="required"/>
      </div>
      <div id="lastNameDiv" data-role="fieldcontain">
        <label for="lastNo">
          <s:text name="lastName"/>*</label>
          <input id="lastNo" name="booking.booker.lastName" type="text" class="required"/>
      </div>
      <div id="phoneNameDiv" data-role="fieldcontain">
        <label for="phoneNo"><s:text name="phone"/>*</label>
        <input id="phoneNo" name="booking.booker.phone" type="text" class="required"/>
      </div>
      <div id="emailNameDiv" data-role="fieldcontain">
        <label for="emailNo"><s:text name="email"/>*</label>
        <input id="emailNo" name="booking.booker.email" type="text" class="required"/>
      </div>
      <div data-role="fieldcontain">
        <button type="submit" data-theme="b" id="btn_guest_next">
          <s:text name="sendBooking"/>
        </button>
      </div>
    </form>
  </div><!-- /content -->
  
  <div data-role="footer">
    <h4>Locanda <s:text name="onlineWidget"/></h4>
  </div><!-- /header -->
</div><!-- /page -->