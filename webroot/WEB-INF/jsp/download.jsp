<%--
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
--%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		

<s:url action="download" var="url_download"></s:url>
<jsp:include page="layout/header.jsp"/>
      <div id="home">
      	<h1>Locanda - Download</h1>
      	<p><s:text name="homeWelcomeMessage"/>.</p>
      	<form role="application" class="yform" action="<s:property value="url_download"/>" method="post">
            <fieldset>
              <legend>Inserisci i tuoi dati</legend>
              <div class="type-text">
                <label for="firstname">Nome</label>
                <input type="text" size="20" id="firstname" name="name" class="required" />
              </div>
              <div class="type-text">
                <label for="firstname">Cognome</label>
                <input type="text" size="20" id="firstname" name="surname" class="required" />
              </div>
              <div class="type-text">
                <label for="email">E-Mail <sup title="This field is mandatory.">*</sup></label>
                <input type="text" aria-required="true" size="20" id="email" name="email" class="required"/>
              </div>
                <div class="type-text">
                <label for="phone">Telefono <sup title="This field is mandatory.">*</sup></label>
                <input type="text" aria-required="true" size="20" id="phone" name="phone" class="required"/>
              </div>
            </fieldset>
            <div class="type-button">
              <button><img  src="images/download.png" alt="download"/></button>
            </div>
          </form>
      	
      	
      	<p id="home_images">
      	</p>
      </div>
<jsp:include page="layout/footer.jsp" />     