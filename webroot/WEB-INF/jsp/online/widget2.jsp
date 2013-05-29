<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" /><!-- Start of first page -->
<div data-role="page" id="foo">
  <div data-role="header">
	<div style="position:absolute; left:1px; top:1px;">
		<a href="goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" class="ui-link" rel="external" >
			<img src="images/logo_small.png" />
		</a>
    </div>
    <h1><s:text name="roomSelect"/></h1><a href="goOnlineBookingCalendar.action?idStructure=<s:property value="structure.id"/>" class="ui-btn-right" rel="external" data-icon="home" data-iconpos="notext" data-direction="reverse"><s:text name="home"/></a>
  </div>
  <!-- /header -->
  <div data-role="content">
    <div class="contentHeader">
    <ul data-role="listview" data-theme="a" data-inset="true">
	    <li>
	      <p class="resume"><s:text name="dateIn" />: <strong><s:property value="booking.dateIn"/></strong>, <s:text name="nights"/>: <strong><s:property value="booking.calculateNumNights()"/></strong>&nbsp;, <s:text name="persons"/>: <strong><s:property value="booking.nrGuests"/></strong><img src="images/<s:property value="booking.nrGuests"/>.png" alt="<s:property value="booking.nrGuests"/>" />
	      </p>
	    </li>
	</ul>
	<p class="resume">&nbsp;</p>
    </div>
    <ul data-role="listview" data-theme="b"  data-inset="true">
      <s:iterator value="rooms" var="eachRoom">
        <li data-role="list-divider">
          <div class="ui-grid-b">
            <form action="goOnlineBookingExtras.action" method="post">
              <div class="ui-block-a">
                <input type="hidden" name="booking.room.id" value="<s:property value="#eachRoom.id"/>"/>
                <img width="90" height="75" 
                src="rest/file/<s:property value="#eachRoom.images[0].file.id"/>"
                alt="room photo"/>
              </div>
                <div class="ui-block-b">
                  <p class="price_room_widget">
                    <s:property value="#eachRoom.roomType.name"/>
                  </p>
                  <p class="price_room_widget">
                    &euro; <s:property value="#eachRoom.price"/><span></span>
                  </p>
                </div>
                <div class="ui-block-c">
                  <button type="submit" data-theme="b" data-icon="arrow-r">
                    <s:text name="select"/>
                  </button>
                </div>
            </form>
          </div>
        </li>
      </s:iterator>
    </ul>
    <s:if test="rooms.size() == 0">
      <h3><s:text name="roomsNotAvailableOnlineAction" /></h3><a href="goOnlineBookingCalendar.action?idStructure=<s:property value="structure.id"/>" rel="external" data-role="button" data-icon="alert" data-theme="b"><s:text name="back"/></a>
    </s:if>
  </div><!-- /content -->
  
  <div data-role="footer">
    <h4><s:text name="title"/> <s:text name="onlineWidget"/></h4>
  </div><!-- /header -->
</div>