<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
						<label for="">Facilities:</label>					 
						<s:iterator value="roomFacilities" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/room_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}_fac" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>
				   <s:iterator value="room.roomType.roomTypeFacilities" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/roomtype_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}_fac" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>
					<s:if test="room.id == null">
					<s:iterator value="roomTypeFacility" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/roomtype_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}_fac" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>	
					</s:if>	
					
					<!-- div facility for javascript purpose-->
				  	<div class="facility" style="display: none; border-color: red;">
					  <img  width="24" height="24" src="images/room_facilities/" alt="facility"/>
					  <input type="checkbox" id="" name="" checked="checked"/>
					  <label for=""></label>
				  	</div>
				  	<!-- end div facility for javascript purpose-->