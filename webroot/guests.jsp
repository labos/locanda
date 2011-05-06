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
        <div class="header_section">
          <span class="name_section">Manage Guests</span>   
          <div class="right type-text">
            <input type="text" name="guest_search" class="txt_guest_search" /><button class="btn_g_search">SEARCH</button>      
            <div class="search_links">
              <span>Or browse:&nbsp;</span><a href="#">staying this week</a><span>,&nbsp;</span><a href="#">staying next week</a>,&nbsp;<a href="#">staying this month</a>,&nbsp;<a href="#">staying last month</a>,&nbsp;<a href="#">all</a>
            </div>
          </div>  
        </div>
          
        <div>
          <button class="btn_add_form">ADD NEW</button>
        </div>
        <div class="yform hideform">
       	  <jsp:include page="jsp/contents/guest_form.jsp" />
        </div>
          
        <s:iterator value="guests" var="eachGuest" >
          
          <div>
		    <form method="post" action="deleteGuest.action" class="yform json full" role="application">
              <fieldset>
                <input type="hidden" name="redirect_form" value="findAllGuests.action?sect=guests" />
                <input type="hidden" name="id" value="<s:property value="id"/>"/>
                <legend class="title_season">
                	<a href="goUpdateGuest.action?sect=guests&id=<s:property value="#eachGuest.id"/>"><s:property value="#eachGuest.firstName"/> <s:property value="#eachGuest.lastName"/></a>
                	<a href="goUpdateGuest.action?sect=guests&id=<s:property value="#eachGuest.id"/>"><img src="images/sign-up-icon.png" alt="edit" /></a>
                </legend>
		    	<div class="subcolumns">
      		 	  <div class="c40l">
                    <div class="type_rooms">
					  <ul>
				        <li><b>Address:</b> <s:property value="#eachGuest.address"/> - <s:property value="#eachGuest.zipCode"/> <s:property value="#eachGuest.country"/></li>
				        <li><b>Phone:</b> <s:property value="#eachGuest.phone"/></li>
				        <li><b>Notes:</b> <s:property value="#eachGuest.notes"/></li>
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