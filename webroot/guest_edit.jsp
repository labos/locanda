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
                     <p class="navigation"> <a class="home" href="<s:property value="url_findallguest"/>?sect=guests">&nbsp;</a><b>»</b> 
			<span>&nbsp;</span></p>
          <span class="name_section">Guest Details</span>
          <div class="right type-text">
          <input type="text" name="guest_search" class="txt_guest_search" /><button class="btn_g_search">SEARCH</button>
        
            <div class="search_links"><!--<span>Or browse:&nbsp;</span><a href="#">staying this week</a><span>,&nbsp;</span><a href="#">staying next week</a>,&nbsp;<a href="#">staying this month</a>,&nbsp;<a href="#">staying last month</a>,&nbsp;<a href="#">all</a>--></div>
            </div>
          </div>
          <div>
          	
 		      <jsp:include page="jsp/contents/guest_form.jsp" />    
		    
		
		<div class="beautify">
              <legend>Bookings</legend>
              <%@ page import="model.Booking;" %>
              <s:iterator value="bookings" var="eachBooking" >
                    <%
            		Double adjustmentsSubtotal = 0.0;
            		Double paymentsSubtotal = 0.0;
            		Double roomSubtotal = 0.0;
            		Double extraSubtotal = 0.0;
            		Double adjPluspay = 0.0;
                    Booking aBooking = (Booking) request.getAttribute("eachBooking");
                    		adjustmentsSubtotal = aBooking.calculateAdjustmentsSubtotal();
							paymentsSubtotal = aBooking.calculatePaymentsSubtotal();
							roomSubtotal = aBooking.getRoomSubtotal();
							extraSubtotal = aBooking.getExtraSubtotal();
							adjPluspay = adjustmentsSubtotal  + roomSubtotal + extraSubtotal;
      %>
              <div class="subcolumns">
              	<div class="c50l book_guest">
              	  <div class="c20l"><b>Room:</b> <p><s:property value="#eachBooking.room.name"/>/<s:property value="#eachBooking.room.roomType.name"/></p></div>                    
              	  <div class="c20l"><b>CheckIn:</b> <p><s:property value="#eachBooking.dateIn"/></p></div>
              	  <div class="c20l"><b>CheckOut:</b> <p><s:property value="#eachBooking.dateOut"/></p></div>
              	  <div class="c20l"><b>Subtotal:</b> <p>&euro; <% out.print(adjPluspay) ; %></p></div>
              	</div>
              	<div class="book_guest">                    
              	  <div class="c20l"><a href="goUpdateBooking.action?sect=planner&id=<s:property value="#eachBooking.id"/>"><span class="link"><s:text name="details" /></span></a></div>
              	  <div class="c20l"><a href="goAddNewBooking.action?sect=planner"><span class="link"><s:text name="newBooking" /></span></a></div><div class="c20l"><span></span></div><div class="c20l"><span></span></div>
              	</div>
              </div>
              
              </s:iterator>
              
              </div>
             </div>        
          </div>
          <jsp:include page="jsp/layout/footer.jsp" />     