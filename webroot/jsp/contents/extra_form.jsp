<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		<form method="post" action="saveUpdateExtra.action" class="yform json full" role="application">
		  <fieldset>
          	<legend>Extras details</legend>
          	  <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllExtras.action?sect=accomodation" />
                <input type="hidden" name="extra.id" value="<s:property value="extra.id"/>"/>
                <div class="c50l">
                  <div class="type-text">	
                  	<label for="extraFormName">Extra name <sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="extra.name" id="extraFormName" value="<s:property value="extra.name"/>" aria-required="true"/>
                  </div> 
                  <div class="subcr type-check">
                  <div class="c50l">
					<input 
					  <s:if test="extra.timePriceType == 'per Night'">checked="checked"</s:if>
					  type="radio" name="extra.timePriceType" value="per Night"/>per Night<br/>
                    <input 
                      <s:if test="extra.timePriceType == 'per Week'">checked="checked"</s:if>
					  type="radio" name="extra.timePriceType" value="per Week"/>per Week<br/>
                    <input 
                      <s:if test="extra.timePriceType == 'per Booking'">checked="checked"</s:if>
				      type="radio" name="extra.timePriceType" value="per Booking"/>per Booking
				    </div>
							 
				  <div class="c50l">
				  	<input 
					  <s:if test="extra.resourcePriceType == 'per Room'">checked="checked"</s:if>
					  type="radio" name="extra.resourcePriceType" value="per Room" />per Room<br/>
                    <input 
                      <s:if test="extra.resourcePriceType == 'per Person'">checked="checked"</s:if>
					  type="radio" name="extra.resourcePriceType" value="per Person" />per Person<br/>
                    <input 
                      <s:if test="extra.resourcePriceType == 'per Item'">checked="checked"</s:if>
					  type="radio" name="extra.resourcePriceType" value="per Item" />per Item
				  </div>
				  
				  </div>
				  <div class="type-text">	
                  	<label for="extraFormName">Extra Description</label>
					<textarea name="extra.description"><s:property value="extra.description"/></textarea>		 
                  </div>
                  <div class="type-button">
                  	<button class="btn_save">SAVE</button>
                    <button class="btn_reset btn_cancel_form">CANCEL</button>
                  </div>	
                </div>
              </div>
            </fieldset>   
          </form>
                      
                      
					   