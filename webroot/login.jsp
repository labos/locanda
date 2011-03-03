<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include page="jsp/layout/header.jsp" />
      <div id="home">
      	<h1>Log In</h1>
      	<p>Not yet registered? <a href="signup.jsp">Signup</a></span>.</p>
      	
      	<s:url action="login" var="url"></s:url>
      	
      	<form method="post" action="<s:property value="url"/>" class="yform" role="application">
            <fieldset>
              <legend>Log In data</legend>
              <div class="type-text">
                <label for="email">E-Mail <sup title="This field is mandatory.">*</sup></label>
                <input type="text" class="required email" name="email" id="email" size="20"  aria-required="true"/>
              </div>
              <div class="type-text">
                <label for="password">Password <sup title="This field is mandatory.">*</sup></label>
                <input type="password" class="required" name="password" id="password" size="20"  aria-required="true"/>
              </div>
            </fieldset>
            <div class="type-button">
          	  <button class="btn_submit" type="submit" role="button" aria-disabled="false">Log In</button>
            </div>
          </form>
      </div><!-- end: #home -->
<jsp:include page="jsp/layout/footer.jsp" />         