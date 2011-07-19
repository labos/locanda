<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <div class="validationErrors"></div>
		<form method="post" action="saveUpdateExtra.action" class="yform json full" role="application">
		  <fieldset>
          	<legend><s:text name="extraDetails"/></legend>
          	  <div class="c60l">
              	<input type="hidden" name="redirect_form" value="findAllExtras.action?sect=accomodation" />
                <input type="hidden" name="extra.id" value="<s:property value="extra.id"/>"/>
                <div class="c60l">
                  <div class="type-text">	
                  	<label for="extraFormName"><s:text name="extra"/> <s:text name="name"/><sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="extra.name" id="extraFormName" value="<s:property value="extra.name"/>" aria-required="true"/>
                  </div>
                  
                  <div class="subcr type-check">
                    <div class="c50l">
					  <input 
					    <s:if test="extra.timePriceType == 'per Night'">checked="checked"</s:if>
					    type="radio" name="extra.timePriceType" value="per Night"/><s:text name="extraPerNight"/><br/>
                      <input 
                        <s:if test="extra.timePriceType == 'per Week'">checked="checked"</s:if>
					    type="radio" name="extra.timePriceType" value="per Week"/><s:text name="extraPerWeek"/><br/>
                      <input 
                        <s:if test="extra.timePriceType == 'per Booking'">checked="checked"</s:if>
				        type="radio" name="extra.timePriceType" value="per Booking"/><s:text name="extraPerBooking"/>
				    </div>				 
				    <div class="c50l">
				  	  <input 
					    <s:if test="extra.resourcePriceType == 'per Room'">checked="checked"</s:if>
					    type="radio" name="extra.resourcePriceType" value="per Room"/><s:text name="extraPerRoom"/><br/>
                      <input 
                        <s:if test="extra.resourcePriceType == 'per Person'">checked="checked"</s:if>
					    type="radio" name="extra.resourcePriceType" value="per Person"/><s:text name="extraPerPerson"/><br/>
                      <input 
                        <s:if test="extra.resourcePriceType == 'per Item'">checked="checked"</s:if>
					    type="radio" name="extra.resourcePriceType" value="per Item"/><s:text name="extraPerItem"/>
				    </div>
				  </div>
				  
				  <div class="type-check ">
                    <s:checkbox id="online" key="extra.availableOnline" label="%{getText('onlineAvailable')}"/>
                  </div>
                  
				  <div class="type-text">	
                  	<label for="extraFormName"><s:text name="description"/> <s:text name="extra"/></label>
					<textarea name="extra.description"><s:property value="extra.description"/></textarea>		 
                  </div>
                  
                  <div class="type-button">
                  	<button class="btn_save"><s:text name="save"/></button>
                    <button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
                  </div>	
                </div>
              </div>
            </fieldset>   
          </form>			   