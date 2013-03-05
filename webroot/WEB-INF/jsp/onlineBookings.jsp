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
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="layout/header_menu.jsp" />
      <div id="main">
        <!-- begin: #col1 - first float column -->
        <div id="col1" role="complementary">
          <div id="col1_content" class="clearfix">
          </div>
        </div><!-- end: #col1 -->
        <!-- begin: #col3 static column -->
        <div id="col3" role="main">
          <div id="col3_content" class="clearfix">
            <div class="header_section">
              <s:url action="#" var="urlGoAddNewBooking"></s:url>
              <span class="name_section"><s:text name="onlineBooking"/></span>
            </div>
  		    <form method="post" action="#" class="yform json full" role="application">
              <fieldset>
                <legend>Widget</legend>
                <div class="c50l">
                   <div class="type-check">
					<a href="online.action?idStructure=<s:property value="idStructure"/>" target="_blank"><s:text name="onlineWidgetTry"/></a>
             	  </div> 
             	  <div class="type-check">
					<a href="mobile.action?idStructure=<s:property value="idStructure"/>" target="_blank">Mobile widget</a>
             	  </div> 
           	 	  <div class="type-text">
                  	<span><s:text name="onlineWidgetPaste"/></span>
             	  </div>
                  <div class="type-text">
                  	<textarea readonly="readonly" id="text-widget" rows="5" cols="30"> 
                  	  <iframe height="900" width="400" marginheight="0" marginwidth="0" src="<%= request.getScheme() + "://" + request.getServerName() +":"+ Integer.toString(request.getServerPort()) + request.getContextPath( )%>/goOnlineBookingCalendar.action?idStructure=<s:property value="idStructure"/>" scrolling="no"/>
        			</textarea>
             	  </div>
             	  <div class="type-check">
					<!--<a href="#" id="customizewidget"><s:text name="customizeLayout"/></a>  -->
              	  <!-- 
              	  <div class="type-check">
                  	<input type="checkbox" name="online.price" id="onlineRequire" value="1"/>
                  	<label for="onlineRequire"><s:text name="requirePayment"/></label>
             	  </div>         	 	
                  -->
                  <div class="type-button">
            		<button class="btn_save"><s:text name="save"/></button>
           		  </div>
	    		  <div id ="text-widget-edit" style="display: none">
	    			<textarea rows="5" cols="30">
	    			  <iframe height="900" width="400" marginheight="0" marginwidth="0" src="http://localhost:8080/locanda/goOnlineBookingCalendar.action" scrolling="no"/>
        			</textarea>
	    		  </div>
	    		</div>
              </fieldset>
            </form>
            <div id="event_edit_container"/>
          </div>
<jsp:include page="layout/footer.jsp" />     