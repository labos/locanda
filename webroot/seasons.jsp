<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />
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
          <span class="name_section">Seasons</span>
          </div>
           <div>
              <button class="btn_add_form">ADD NEW SEASON</button>
            </div>
             <div class="yform hideform">
          	  <jsp:include page="jsp/contents/season_form.jsp" />
            </div>
            
 <!-- Seasons iteration -->
 
 <s:iterator value="seasons" var="eachSeason" >
 <div>
 <form method="post" action="deleteSeason.action" class="yform json" role="application"> 
  	<fieldset>
  		<legend>
  				<a href="goUpdateSeason.action?sect=settings&id=<s:property value="#eachSeason.id"/>"><s:property value="#eachSeason.name"/></a>
    			<a href="goUpdateSeason.action?sect=settings&id=<s:property value="#eachSeason.id"/>"><img src="images/sign-up-icon.png" alt="edit" /></a>
    	</legend>
  	 	 <input type="hidden" name="redirect_form" value="findAllSeasons.action?sect=settings" />
  		 <input type="hidden" name="season.id" value="<s:property value="#eachSeason.id"/>" />	
               <div class="subcolumns">
      		 	<div class="c40l">
                    <div class="type_rooms">
					  <ul>
					  <li>
					  <ul>
			<s:iterator value="#eachSeason.periods" var="eachPeriod" status="periodStatus">
			<li>
						<b>From:</b>&nbsp;<s:date name="#eachPeriod.startDate" format="%{#session.datePattern}" />
					    <b>To:</b>&nbsp;<s:date name="#eachPeriod.endDate" format="%{#session.datePattern}" />
			</li>

</s:iterator>		  
					</ul> 
					</li> 
					  </ul>
                    </div>                  
                </div>
             </div>           
             <div class="type-button">
             	<button class="btn_delete">DELETE</button>
             </div>
               </fieldset>
 </form>
 </div>
 </s:iterator>
 </div>

<jsp:include page="jsp/layout/footer.jsp" />   