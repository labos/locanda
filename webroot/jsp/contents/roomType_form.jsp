<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		<form method="post" action="saveUpdateRoomType.action" class="yform json full" role="application">
		  <fieldset>
          	<legend>Room Types details</legend>
          	  <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllRoomTypes.action?sect=accomodation" />
                <input type="hidden" name="roomType.id" value="<s:property value="roomType.id"/>"/>
                <div class="c50l">
                  <div class="type-text">	
                  	<label for="roomTypeName">Room Type name <sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="extra.name" id="roomTypeName" value="<s:property value="roomType.name"/>" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="roomTypeMaxGuests">Room Type price <sup title="This field is mandatory.">*</sup></label>
                    <input type="text" class="required number" name="roomType.maxGuests" id="roomTypeMaxGuests" value="<s:property value="roomType.maxGuests"/>" aria-required="true"/>
      		      </div> 
                  
                  <div class="type-button">
                  	<button class="btn_save">SAVE</button>
                    <button class="btn_reset btn_cancel_form">CANCEL</button>
                  </div>	
                </div>
              </div>
            </fieldset>   
          </form>
                      
                      
					   