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
             	  <div class="type-select">
                  	<label for="roomType">Room Type: <sup title="This field is mandatory.">*</sup></label>            	                 	
             	     <select name="room.roomType.id" id="roomType">
                    	<s:iterator value="roomTypes" var="eachRoomType" >
                   			<option 
                   			  <s:if test="#eachRoomType.id == room.roomType.id">selected="selected"</s:if>	 
                   			  value="<s:property value="#eachRoomType.id"/>"><s:property value="#eachRoomType.name"/>
                   			</option>
                    	</s:iterator>
                    	<s:if test="room.roomType == null">
							<option selected="selected" value="-1">Select One</option>
						</s:if>
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
				   <s:iterator value="room.roomType.roomTypeFacilities" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/roomtype_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>	
					<s:if test="room == null">
					<s:iterator value="roomTypeFacility" var="each">
               		  <div class="facility">
						<img width="24" height="24" src="images/roomtype_facilities/<s:property value="#each.fileName"/>" alt="facility"/>
						<s:checkbox id="%{#each.name}" name="roomFacilitiesIds" value="roomFacilitiesIds.contains(#each.id)" fieldValue="%{#each.id}"/>
						<label for="<s:property value="name"/>_fac"><s:property value="#each.name"/></label>
					  </div>
					</s:iterator>	
					</s:if>
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
                    
        <!--  ROOM IMAGE UPLOADING  -->
           <div class="beauty">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility">Image Name:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_image" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/>
 				<form id="uploadImage" action="uploadRoomImage.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=" "/>
   				  <input type="hidden" name="room.id" value="<s:property value="room.id"/>"/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button>
    			  <div>Upload Room Photo</div>  
				</form> 
		   	  </div>
		   	</div>
		   	<div class="subcolumns">
                  <a name="bottom_anchor" />
        <ul class="thumbs">
        <s:iterator value="room.imageLists" var="eachImage" >
        					<li>
        					<h4><s:property value="#eachImage.name" /></h4>
								<a title="<s:property value="#eachImage.name" />" href="#drop" class="thumb" rel="history">
									<img alt="<s:property value="#eachImage.name" />" src="images/room_images/<s:property value="#eachImage.fileName" />" />
									</a>
									<span><s:property value="#eachImage.name" /><a class="erase_image" href="deletePhotoRoom.action?image.id=<s:property value="#eachImage.id" />&room.id=<s:property value="room.id" />"  title="erase"><img src="images/delete.png" alt="Delete Image" /></a></span>
								
							</li>
        </s:iterator>
							<li style="display: none">
								<a title="Title #1" href="#drop" class="thumb" rel="history">
									<img alt="Title #1" src="images/room_images/" />
								</a>
								<span class="name_image">__PVALUE__<a class="erase_image" href="deletePhotoRoom.action?room.id=<s:property value="room.id" />&image.id="  title="erase"><img src="images/delete.png" alt="Delete Image" /></a></span>
										
							</li>
							</ul>
						</div>
		   	
		   	    
          </div>
         
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
 				<form id="uploadFacility" action="uploadFacility.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=""/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button> 
    			  <div>Upload Facility Logo</div>  
				</form> 

		   	  </div>
		   	</div>      
          </div>	 		  