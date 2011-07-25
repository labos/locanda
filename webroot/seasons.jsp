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

<jsp:include page="jsp/layout/header_menu.jsp"/>
    <div id="main">
      <!-- begin: #col1 - first float column -->
      <div id="col1" role="complementary">
        <div id="col1_content" class="clearfix"></div>
      </div><!-- end: #col1 -->
      <!-- begin: #col3 static column -->
      <div id="col3" role="main">
        <div id="col3_content" class="clearfix">
          <div class="header_section">
            <span class="name_section"><s:text name="seasons"/></span>
          </div>
          <div>
            <button class="btn_add_form"><s:text name="addNewSeason"/></button>
          </div>
          <div class="yform hideform">
            <jsp:include page="jsp/contents/season_form.jsp"/>
          </div>
            
 		<!-- Seasons iteration -->
 		  <s:iterator value="seasons" var="eachSeason">
 			<div>
 			  <form method="post" action="deleteSeason.action" class="yform json" role="application"> 
  				<fieldset>
  				<legend>
  				  <a href="goUpdateSeason.action?sect=settings&id=<s:property value="#eachSeason.id"/>"><s:property value="#eachSeason.name"/></a>
    			  <a href="goUpdateSeason.action?sect=settings&id=<s:property value="#eachSeason.id"/>"><img src="images/sign-up-icon.png" alt="edit"/></a>
    			</legend>
  	 	 		<input type="hidden" name="redirect_form" value="findAllSeasons.action?sect=settings"/>
  		 		<input type="hidden" name="season.id" value="<s:property value="#eachSeason.id"/>"/>	
               	  <div class="subcolumns">
      		 		<div class="c40l">
                      <div class="type_rooms">
					    <ul>
					      <li>
					  	    <ul>
						    <s:iterator value="#eachSeason.periods" var="eachPeriod" status="periodStatus">
						      <li>
						        <b><s:text name="from"/>:</b>&nbsp;<s:date name="#eachPeriod.startDate" format="%{#session.datePattern}"/>
					    	    <b><s:text name="to"/>:</b>&nbsp;<s:date name="#eachPeriod.endDate" format="%{#session.datePattern}"/>
						      </li>
						    </s:iterator>		  
					        </ul> 
						  </li> 
					  	</ul>
                      </div>                  
                    </div>
                  </div>           
                  <div class="type-button">
             		<button class="btn_delete"><s:text name="delete"/></button>
                  </div>
                </fieldset>
 			  </form>
 			</div>
 		  </s:iterator>
 		</div>
<jsp:include page="jsp/layout/footer.jsp" />   