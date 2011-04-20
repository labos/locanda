<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<link rel='stylesheet' type='text/css' href='css/screen/basemod_2col_left_tree.css' />

<jsp:include page="jsp/layout/header_menu.jsp" />

<link rel='stylesheet' type='text/css' href='css/screen/basemod_2col_left_tree.css' />

      <div id="main">
      
      	<div id="col2" role="complementary">
          <div id="col2_content" class="clearfix">
          <div class="header_section">
          	  <span class="name_section">Room Price Lists</span>
      	 	</div>
          </div>
        </div>
      
        <div id="col1" role="complementary">
          <div id="col1_content" class="clearfix">
          <div class="treeContainer">
      	      <div class="tree"></div>
      	    </div>
          </div>
        </div>
        
        <div id="col3" role="main">
          <div id="col3_content" class="clearfix">
          	
          	<div class="priceTable">
          	<table class="full">
          	  <tr></tr>
          		<th>Guests number</th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th><th>Saturday</th><th>Sunday</th>
          	  </tr>
          	<s:iterator value="priceList.items" var="eachPriceListItem" >
          	  <tr>
          	  	<td><s:property value="#eachPriceListItem.numGuests"/></td>	
          	  	<td><s:property value="#eachPriceListItem.prices[0]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[1]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[2]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[3]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[4]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[5]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[6]"/></td>
          	  </tr>
          	</s:iterator>
          	</table>
          	</div>
          	
		  </div>
		</div>
	  </div>
		  
<jsp:include page="jsp/layout/footer.jsp" />     