<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />
      <div id="main">
        <!-- begin: #col1 - first float column -->

        <div id="col1" role="complementary">
          <div id="col1_content" class="clearfix">
          </div>
        </div><!-- end: #col1 -->
        <!-- begin: #col3 static column -->

        <div id="col3" role="main">
          <div id="col3_content" class="clearfix">
          <div class="header_section">
          <s:url action="goAddNewBooking.action?sect=planner" var="urlGoAddNewBooking"></s:url>
          
          <span class="name_section">Planner</span>
               <a class="btn_right" href="<s:property value="urlGoAddNewBooking"/>" title="Add new booking" /></a>
               </div>
            <div id='calendar'></div>

            <div id="event_edit_container">
            </div>
          </div>
<jsp:include page="jsp/layout/footer.jsp" />     