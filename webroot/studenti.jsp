<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include page="jsp/layout/header.jsp" />
      <div id="home">
      	<h1>Studenti</h1>
      		<table border="1">
      			<tr>
      				<td>Matricola</td>
      				<td>Nome</td>
      			</tr>
      		
      		<s:iterator value="studenti" var="each">
      			<tr>
      				<td><s:property value="#each.matricola"></s:property></td>
      				<td><s:property value="#each.nome"></s:property></td>
      			</tr>
      		</s:iterator>
      		</table>
      	
      </div><!-- end: #home -->
<jsp:include page="jsp/layout/footer.jsp" />         