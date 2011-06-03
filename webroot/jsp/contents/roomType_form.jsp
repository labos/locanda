<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		<form method="post" action="saveUpdateRoomType.action" class="yform json full" role="application">
		  <fieldset>
          	<legend><s:text name="roomTypeDetails" /></legend>
          	  <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllRoomTypes.action?sect=accomodation" />
                <input type="hidden" name="roomType.id" value="<s:property value="roomType.id"/>"/>
                <div class="c50l">
                  <div class="type-text">	
                  	<label for="roomTypeName"><s:text name="roomTypeName" />: <sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="roomType.name" id="roomTypeName" value="<s:property value="roomType.name"/>" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="roomTypeMaxGuests"><s:text name="roomTypeMaxGuests" />: <sup title="This field is mandatory.">*</sup></label>
                    <input type="text" class="required number" name="roomType.maxGuests" id="roomTypeMaxGuests" value="<s:property value="roomType.maxGuests"/>" aria-required="true"/>
      		      </div> 
                  
                  <div class="type-button">
                  	<button class="btn_save"><s:text name="save" /></button>
                    <button class="btn_reset btn_cancel_form"><s:text name="delete" /></button>
                  </div>	
                </div>
              </div>
              <div class="c50l">
                <div class="subcr type-check">
               		<label for="">Facilities:</label>
                	<s:iterator value="roomTypeFacilities" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/room_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}" name="roomTypeFacilitiesIds" value="roomTypeFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>
				    <!-- div facility for javascript purpose-->
				  	<div class="facility" style="display: none; border-color: red;">
					  <img  width="24" height="24" src="images/room_facilities/" alt="facility"/>
					  <input type="checkbox" id="" name="" checked="checked"/>
					  <label for=""></label>
				  	</div>
				  	<!-- end div facility for javascript purpose-->
				</div>
         	  </div>
              
              
              
            </fieldset>   
          </form>
           <div class="subcolumns">
				<div class="result_facility_upload" id="result_facility_upload" ></div>
		   			<div class="upload_loader">&nbsp;</div>
		   			<div class="image_preview"></div>
		</div>
          <div class="beauty">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility">Facility Name:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_facility" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/>
 				<form id="uploadFacility" action="uploadRoomTypeFacility.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=""/>
   				    <input type="hidden" name="roomType.id" value="<s:property value="roomType.id"/>"/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button> 
    			  <div>Upload Facility Logo</div>  
				</form> 

		   	  </div>
		   	</div>      
          </div>	 		  
          
                      
                      
					   