<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <s:url action="home" var="url"></s:url>

  <META HTTP-EQUIV="Refresh" CONTENT="0;URL=<s:property value="url"/>">
  <title><s:text name="redirecting"/></title>
  </head>
  <body>	
	
  </body>
</html>