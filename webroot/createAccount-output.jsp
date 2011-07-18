<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		

<s:url action="createAccount" var="url_createAccount"></s:url>
<jsp:include page="jsp/layout/header.jsp"/>
      <div id="home">
      	<h1>Locanda - Account creato con successo</h1>
      	<p><s:text name="homeWelcomeMessage"/>.</p>
      	<h3>Complimenti! Account creato con successo.</h3>
      	<h3>Ora puoi accedere al sistema utilizzando le seguenti credenziali:</h3>
      	<p>
      	Email: <s:property value="user.email" /><br></br>
      	Password: locanda
      	</p>
      	<p><a class="login-bottom" href="<s:property value="url_login"/>"><s:text name="login" /></a></p>
      	<p id="home_images"></p>
      </div>
<jsp:include page="jsp/layout/footer.jsp" />     