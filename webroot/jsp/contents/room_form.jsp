<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

  		  <form method="post" action="saveUpdateRoom.action" class="yform json full" role="application">
            <fieldset>
              <legend><s:text name="room" /> - <s:text name="details" /></legend>
              <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllRooms.action?sect=accomodation" />
              	<input type="hidden" name="room.id" value="<s:property value="room.id"/>"/>
           	 	  <div class="type-text">
                  	<label for="roomName"><s:text name="roomName" />: <sup title="This field is mandatory.">*</sup></label>
                  	<input class="required" type="text" name="room.name" id="roomName" value="<s:property value="room.name"/>" size="20" />
             	  </div>
             	  <div class="type-select">
                  	<label for="roomType"><s:text name="roomType" />: <sup title="This field is mandatory.">*</sup></label>            	                 	
             	     <select class="required" name="room.roomType.id" id="roomType">
             	        <s:if test="room == null">
							<option selected="selected" value="-1"><s:text name="selectOne" /></option>
						</s:if>
                    	<s:iterator value="roomTypes" var="eachRoomType" >
                   			<option 
                   			  <s:if test="#eachRoomType.id == room.roomType.id">selected="selected"</s:if>	 
                   			  value="<s:property value="#eachRoomType.id"/>"><s:property value="#eachRoomType.name"/>
                   			</option>
                    	</s:iterator>

                 	</select>
             	  </div>
             	  <div class="type-text">
                  	<label for="roomNotes"><s:text name="notes" />:</label> 
                  	<textarea name="room.notes" id="notes"><s:property value="room.notes"/></textarea>
                  </div>
         	 	
                  <div class="type-button">
            		<button class="btn_save"><s:text name="save" /></button>
           		 	<button class="btn_reset btn_cancel_form"><s:text name="cancel" /></button>
           		  </div>	    
              </div>
              
              <div class="c50l">
                <div class="wrapper-facility type-check">
               		<jsp:include page="roomTypeFacility_table.jsp" />
               		
               		
               		
					<!-- 
					<s:iterator value="roomTypeFacility" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/roomtype_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>
					<s:if test="room == null">
					</s:if>	
					 -->	
				</div>
         	  </div>
         	 </fieldset>
          </form>
                    
          
          <div class="beauty">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility"><s:text name="facilityName" />:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_facility" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/>
 				<form id="uploadFacility" action="uploadFacility.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=""/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button> 
    			  <div><s:text name="uploadFacility" /></div>  
				</form> 

		   	  </div>
		   	</div>      
          </div>	
                 <div class="subcolumns">
				<div class="result_facility_upload" id="result_facility_upload" ></div>
		   			<div class="upload_loader">&nbsp;</div>
		   			<div class="image_preview"></div>
		</div>  
                  <!-- DIALOG FACILITY --> 
         <div id="dialog-facility" title="<s:text name="facilityEdit" />">
			</div>  		  