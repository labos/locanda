<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
	<!-- Start of first page -->
	<div data-role="page" id="foo3">

	  <div data-role="header">
		<h1><s:text name="extrasSelect"/></h1>
		<a href="goOnlineBookingCalendar.action" class="ui-btn-right" data-icon="home" data-iconpos="notext" data-direction="reverse"><s:text name="home"/></a> 
	  </div><!-- /header -->

	  <div data-role="content">
      	<s:actionerror />
      	<s:fielderror></s:fielderror>         
	  </div><!-- /content -->

	  <div data-role="footer">
		<h4><s:text name="title"/> <s:text name="onlineWidget"/></h4>
	  </div><!-- /header -->
	  
    </div><!-- /page -->