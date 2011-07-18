<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		

<s:url action="createAccount" var="url_createAccount"></s:url>
<jsp:include page="jsp/layout/header.jsp"/>
      <div id="home">
      	<h1>Locanda - <s:text name="createAccount"/></h1>
      	<p><s:text name="homeWelcomeMessage"/>.</p>
      	 <s:actionerror />
      	<form role="application" class="yform" action="<s:property value="url_createAccount"/>" method="post">
            <fieldset>
              <legend><s:text name="insertyourData"/></legend>
              <div class="type-text">
                <label for="firstname"><s:text name="name"/></label>
                <input type="text" size="20" id="firstname" name="user.name" class="required" value = "<s:property value="user.name"/>"/>
              </div>
              <div class="type-text">
                <label for="surname"><s:text name="lastName"/></label>
                <input type="text" size="20" id="surname" name="user.surname" class="required" value="<s:property value="user.surname"/>" />
              </div>
              <div class="type-text">
                <label for="email">E-Mail <sup title="This field is mandatory.">*</sup></label>
                <input type="text" aria-required="true" size="20" id="email" name="user.email" class="required" value="<s:property value="user.email"/>"/>
              </div>
                <div class="type-text">
                <label for="phone"><s:text name="phone"/> <sup title="This field is mandatory.">*</sup></label>
                <input type="text" aria-required="true" size="20" id="phone" name="user.phone" class="required" value="<s:property value="user.phone"/>"/>
              </div>
            </fieldset>
            <div class="type-button">
          	  <button class="btn_submit" type="submit" role="button" aria-disabled="false"><s:text name="createAccount"/></button>
            </div>
          </form>
      	
      	
      	<p id="home_images">
      	</p>
      </div>
<jsp:include page="jsp/layout/footer.jsp" />     