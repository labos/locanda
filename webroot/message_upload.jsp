<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="net.sf.json.*" %>
<%@ page import="model.internal.Message" %>
<%@ page import="model.Image" %>
<%@ page import="model.Facility" %>
<% Message message = (Message) request.getAttribute("message");
Image image = (Image) request.getAttribute("image");
Facility roomFacility = (Facility) request.getAttribute("roomFacility");
Facility structureFacility = (Facility) request.getAttribute("structureFacility");
JSONObject obj=new JSONObject();
obj.put("message", message);
obj.put("roomFacility", roomFacility);
obj.put("image", image);
obj.put("structureFacility", structureFacility);
out.print( obj );
//JSONUtil json = new JSONUtil();
//JSONUtil.serialize(roomfac);
//JSONArray jsonArray = JSONArray.fromObject(obj);  
%>