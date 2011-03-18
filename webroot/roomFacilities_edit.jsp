<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<div class="book_details">
<form action="roomFacilities_edit.action" class="yform json" role="application">
<input type="hidden" name="redirect_form" value="findAllRooms.action?sect=accomodation" />
<s:hidden value="%{room.id}" name="idRoom"/>
                <s:iterator value="roomFacilities" var="each">
               		<div class="facility">
					<img width="24" height="24" src="images/room_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
					<s:checkbox id="%{#each.name}" name="facilities"  value="facilities.contains(#each.id)" fieldValue="%{#each.id}" />
					<label for="<s:property value="#each.name"/>_fac"><s:property value="#each.name"/></label>
					</div>
				</s:iterator>
</form>
</div>
