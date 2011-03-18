<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
<%@ page import="net.sf.json.*" %>
<%@ page import="model.RoomFacility;" %>
<%
RoomFacility roomfac = (RoomFacility) request.getAttribute("roomFacility");

//JSONUtil json = new JSONUtil();
//JSONUtil.serialize(roomfac);
JSONArray jsonArray = JSONArray.fromObject(roomfac);  
System.out.println( jsonArray );  
%>
</body>
</html>