<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="net.sf.json.*" %>
<%@ page import="model.internal.Message" %>
<%@ page import="model.Image" %>
<%@ page import="model.RoomFacility" %>
<%@ page import="model.StructureFacility" %>
<% Message message = (Message) request.getAttribute("message");
Image image = (Image) request.getAttribute("image");
RoomFacility roomFacility = (RoomFacility) request.getAttribute("roomFacility");
StructureFacility structureFacility = (StructureFacility) request.getAttribute("structureFacility");
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