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
          	  <span class="name_section">Manage Extras</span>
      	 	</div>
      	
               <span class="yform">
                   <fieldset>
                       <legend>
                           Insert and configure extras here
                       </legend>
                       <div>
                           <button class="btn_addExtra" role="button" aria-disabled="false">
                               Add Extra
                           </button>
                       </div>
					   
                       <form method="post" action="#"  id="extraForm" role="application">
                           <div class="type-text">
                               <label for="extraFormName">
                                   Extra name <sup title="This field is mandatory.">*</sup>
                               </label>
                               <input type="text" class="required" name="extraFormName" id="extraFormName" aria-required="true"/>
                           </div>
                           <div class="type-button">
                               <button class="btn_saveExtra" role="button" type="button" aria-disabled="false">
                                   Save
                               </button>
                               <button class="btn_cancel" role="button" type="button" aria-disabled="false">
                                   Cancel
                               </button>
                           </div>	   
                       </form>
					   
                       <div id="extraList">
                           <div class="newExtra" id="newExtra">
                             <hr/>
							 <input type="text" class="renameExtraForm required" name="renameExtraForm" value=""/>
							 <a href="#" class="renameExtra" title="rename">Rename</a>
							 <a href="#" class="deleteExtra" title="delete">Delete</a>
							 
                           	 <div class="radiogroup right">
							 <input type="radio" name="rate2" value="night" checked="checked" />per Night
                             <br/>
                             <input type="radio" name="rate2" value="week" />per Week
                             <br/>
                             <input type="radio" name="rate2" value="booking" />per Booking
							 </div>
							 <div class="radiogroup right">
							 <input type="radio" name="rate1" value="room" checked="checked" />per Room
                             <br/>
                             <input type="radio" name="rate1" value="person" />per Person
                             <br/>
                             <input type="radio" name="rate1" value="item" />per Item
							 </div>
							 <div class="priceExtraForm right">
							 	Price - &euro;
							   <input type="text" name="priceExtraForm" class="required number" value=""/>
							 </div> 
                       	   </div>
                       </div>
                   </fieldset>
               </span>
		  
		  </div><!-- end: #col3_content-->
		  
                    <jsp:include page="jsp/layout/footer.jsp" />     