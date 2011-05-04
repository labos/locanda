<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />
  <div id="main">
    <!-- begin: #col1 - first float column -->
    <div id="col1" role="complementary">
      <div id="col1_content" class="clearfix"></div>
    </div><!-- end: #col1 -->
    <!-- begin: #col3 static column -->
    <div id="col3" role="main">
      <div id="col3_content" class="clearfix">
        <div class="header_section yform">
          <span class="name_section">Manage Room Types</span>    
        </div>
          
        <div>
          <button class="btn_add_form">ADD NEW</button>
        </div>
        <div class="yform hideform">
       	  <jsp:include page="jsp/contents/roomType_form.jsp" />
        </div>
          
        <s:iterator value="roomTypes" var="eachRoomType" >
          
          <div>
		    <form method="post" action="deleteRoomType.action" class="yform json full" role="application">
              <fieldset>
                <input type="hidden" name="redirect_form" value="findAllRoomTypes.action?sect=accomodation" />
                <input type="hidden" name="roomType.id" value="<s:property value="#eachRoomType.id"/>"/>
                <legend class="title_season">
                	<a href="goUpdateRoomType.action?sect=accomodation&roomType.id=<s:property value="#eachRoomType.id"/>"><s:property value="#eachRoomType.name"/></a>
                	<a href="goUpdateRoomType.action?sect=accomodation&roomType.id=<s:property value="#eachRoomType.id"/>"><img src="images/sign-up-icon.png" alt="edit" /></a>
                </legend>
		    	<div class="subcolumns">
      		 	  <div class="c40l">
                    <div class="type_rooms">
					  <ul>
				        <li><b>Max Guests:</b> <s:property value="#eachRoomType.maxGuests"/></li>
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