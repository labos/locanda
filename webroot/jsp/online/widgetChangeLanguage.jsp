<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
<!-- Start of first page -->
<div data-role="page" id="foo">

	<div data-role="header">
		<h1>Select Language</h1>
	</div><!-- /header -->

	<div data-role="content">
<ul data-role="listview" data-theme="g">


	<li><a data-inline="true"  href="<s:property escape="false" value="localeIT"/>"  rel="external" data-icon="locanda-ita" data-iconpos="notext" 
           data-direction="reverse">Lang</a></li>
	<li><a data-inline="true" href="<s:property escape="false" value="localeEN"/>"  rel="external" data-icon="locanda-eng" data-iconpos="notext" 
           data-direction="reverse">Lang</a></li>
</ul>

	</div><!-- /content -->

	<div data-role="footer">
		<h4>Locanda Mobile Widget</h4>
	</div><!-- /header -->
</div>