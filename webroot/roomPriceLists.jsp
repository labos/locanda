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
      	  <div class="room_tree"></div>
      	</div>
      </div>
    </div>
        
    <div id="col3" role="main">
      <div id="col3_content" class="clearfix">
          	
      <div class="priceTable">
        <form class="yform json full noBorder" id="priceList_form" action="updateRoomPriceListItems.action">
	  	  <fieldset>
          	<legend>Click to edit the Price List -> <a href="#" id="priceList_edit"><img src="images/sign-up-icon.png" alt="edit" /></a> </legend>
          	<table class="full priceList_table">
          	  <thead>
          	  	<tr>
          		  <th>Guests number</th><th>Monday</th><th>Tuesday</th><th>Wednesday</th><th>Thursday</th><th>Friday</th><th>Saturday</th><th>Sunday</th>
          		</tr>
          	  </thead>
          	  <tbody>
          	  <!-- here an AJAX call puts the table rows-->
          	 </tbody>
          	</table>
          	<div class="type-button right"  id=priceList_buttons >
              
            </div>
          </fieldset>
        </form>
      </div>
          	
	  </div>
    </div>
  </div>
		  
<jsp:include page="jsp/layout/footer.jsp" />     