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
                           <button class="btn_addExtra" role="button" aria-disabled="false">ADD NEW</button>
                       </div>
					   
                       <form method="post" action="addNewExtra.action" class="yform json" id="newExtraForm" role="application">
                           <div class="type-text">
                               <label for="extraFormName">Extra name <sup title="This field is mandatory.">*</sup></label>
                               <input type="text" class="required" name="extra.name" id="extraFormName" aria-required="true"/>
                               
                               <label for="extraFormPrice">Extra price <sup title="This field is mandatory.">*</sup></label>
                               <input type="text" class="required" name="extra.price" id="extraFormPrice" aria-required="true"/>
                               
                               <input type="hidden" name="redirect_form" value="findAllExtras.action?sect=accomodation" />
                               <input type="hidden" name="extra.id" value="<s:property value="extra.id"/>"/>
                           </div>
                           <div class="type-button">
                               <button class="btn_saveExtra" aria-disabled="false">SAVE</button>
                               <button class="btn_cancel" role="button" type="button" aria-disabled="false">CANCEL</button>
                           </div>	   
                      </form>
                      
                      
					   <s:iterator value="extras" var="eachExtra" >
					   
					   <form method="post" action="updateExtra.action" class="yform json full" id="extraForm" role="application">
                         <div id="extraList">
                           <div class="newExtra" id="newExtra">
                             <hr/>
                             <span class="extraName"><s:property value="#eachExtra.name"/></span>
							 <input type="text" class="renameExtraForm required" name="extra.name" value="<s:property value="#eachExtra.name"/>"/>
							 <a href="#" class="renameExtra" title="rename">Rename</a>
							 <input type="hidden" name="redirect_form" value="findAllExtras.action?sect=accomodation" />
                             <input type="hidden" name="extra.id" value="<s:property value="#eachExtra.id"/>"/>
                               
							 <div class="type-button">
							   <button class="btn_saveExtra" aria-disabled="false">SAVE</button>
                               <button class="btn_delete btn_delete_extra">DELETE</button>
                             </div>	 
							 
                           	 <div class="radiogroup right">
                           	 
							 <input type="radio" name="extra.timePriceType" value="<s:property value="#eachExtra.timePriceType"/>" />per Night
                             <br/>
                             <input type="radio" name="extra.timePriceType" value="<s:property value="#eachExtra.timePriceType"/>" />per Week
                             <br/>
                             <input type="radio" name="extra.timePriceType" value="<s:property value="#eachExtra.timePriceType"/>" />per Booking
							 </div>
							 <div class="radiogroup right">
							 <input type="radio" name="extra.resourcePriceType" value="per Room" />per Room
                             <br/>
                             <input type="radio" name="extra.resourcePriceType" value="per Person" />per Person
                             <br/>
                             <input type="radio" name="extra.resourcePriceType" value="per Item" />per Item
							 </div>
							 <div class="priceExtraForm right">
							 	Price - &euro;
							   <input type="text" name="extra.price" class="required number" value="<s:property value="#eachExtra.price"/>"/>
							 </div> 
                       	   </div>
                         </div>
                       </form>
                         
                       </s:iterator>
                       
                   </fieldset>
               </span>
		  
		  </div><!-- end: #col3_content-->
		  
                    <jsp:include page="jsp/layout/footer.jsp" />     