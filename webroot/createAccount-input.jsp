<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		

<s:url action="createAccount" var="url_createAccount"></s:url>
<jsp:include page="jsp/layout/header.jsp"/>
      <div id="home">
      	<h1>Locanda - Create Account</h1>
      	<p><s:text name="homeWelcomeMessage"/>.</p>
      	<form role="application" class="yform" action="<s:property value="url_createAccount"/>" method="post">
            <fieldset>
              <legend>Inserisci i tuoi dati</legend>
              <div class="type-text">
                <label for="firstname">Nome</label>
                <input type="text" size="20" id="firstname" name="user.name" class="required" />
              </div>
              <div class="type-text">
                <label for="surname">Cognome</label>
                <input type="text" size="20" id="surname" name="user.surname" class="required" />
              </div>
              <div class="type-text">
                <label for="email">E-Mail <sup title="This field is mandatory.">*</sup></label>
                <input type="text" aria-required="true" size="20" id="email" name="user.email" class="required"/>
              </div>
                <div class="type-text">
                <label for="phone">Telefono <sup title="This field is mandatory.">*</sup></label>
                <input type="text" aria-required="true" size="20" id="phone" name="user.phone" class="required"/>
              </div>
            </fieldset>
            <div class="type-button">
          	  <button class="btn_submit" type="submit" role="button" aria-disabled="false">Create Account</button>
            </div>
          </form>
      	
      	
      	<p id="home_images">
      	</p>
      </div>
<jsp:include page="jsp/layout/footer.jsp" />     