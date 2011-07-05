<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><s:text name="onlineWidget"/></title>
  </head>
  <body>
    <iframe height="900" width="400" marginheight="0" marginwidth="0" src="<%=request.getContextPath( )%>/goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" scrolling="no"/>
  </body>
</html>