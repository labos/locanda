<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

  		  <form method="post" action="saveUpdateRoom.action" class="yform json full" role="application">
            <fieldset>
              <legend>Room details</legend>
              <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllRooms.action?sect=accomodation" />
              	<input type="hidden" name="room.id" value="<s:property value="room.id"/>"/>
           	 	<div class="c50l">
           	 	  <div class="type-text">
                  	<label for="roomName">Room name: <sup title="This field is mandatory.">*</sup></label>
                  	<input class="required" type="text" name="room.name" id="roomName" value="<s:property value="room.name"/>" size="20" />
             	  </div>
                  <div class="type-text">
                  	<label for="roomType">Room type: <sup title="This field is mandatory.">*</sup></label>
                  	<input class="required" type="text" name="room.roomType" id="roomType" value="<s:property value="room.roomType"/>" size="20" />
             	  </div>
             	  <div class="type-select">
                  	<label for="roomMaxGuests">Max Guests: <sup title="This field is mandatory.">*</sup></label>            	                 	
             	     <select name="room.maxGuests" id="roomMaxGuests" class="required">
                    	 <% 
                    	 if ( request.getAttribute("room.maxGuests") != null)
                    	 {
                    		 
                    	 
 						int max_guests= 4;
						int nr_guests = (Integer) request.getAttribute("room.maxGuests");
						   for(int i = 1; i <= max_guests; i++) {
							   %>
							    <option value="<% out.print(i) ; %>" <% if(nr_guests == i ) {out.print("selected=\"selected\"");}%>><% out.print(i) ; %></option>
							   <% 
						   }
						   }
						   else
						   {
						   %>
                       	  <option value="1">1</option>
                    	  <option value="2">2</option>
                    	  <option value="3">3</option>
                    	  <option value="4">4</option>
      							<% 
	   
						   }
						   %>
                 		</select>
             	  </div>
             	  <div class="type-text">
                  	<label for="roomNotes">Notes:</label> 
                  	<textarea name="room.notes" id="notes"><s:property value="room.notes"/></textarea>
                  </div>
         	 	
                  <div class="type-button">
            		<button class="btn_save">SAVE</button>
           		 	<button class="btn_reset btn_cancel_form">CANCEL</button>
           		  </div>
              
      		    </div>		    
              </div>
              
              <div class="c50l">
                <div class="subcr type-check">
               		<label for="">Facilities:</label>
                	<s:iterator value="roomFacilities" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/room_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
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
          
         
          <div class="beautify">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility">Facility Name:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_facility" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/>
 				<form id="uploadFacility" action="uploadFacility.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=""/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button>
    			  <div>Upload Facility Image</div>  
				</form> 
		  	 	<div class="result_facility_upload" id="result_facility_upload" ></div>
		   		<div class="upload_loader">&nbsp;</div>
		   		<div class="image_preview"></div>
		   	  </div>
		   	</div>      
          </div>	 		  