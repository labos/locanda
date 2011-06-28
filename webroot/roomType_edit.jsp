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
          	<div class="header_section yform">
              <p class="navigation"> <a class="home" href="<s:property value="url_findallroomtypes"/>?sect=accomodation"></a><b>»</b> 
			  <span>&nbsp;</span></p>
              <span class="name_section">Room Type Details</span>
            </div>
          
            <div>
 		  	  <jsp:include page="jsp/contents/roomType_form.jsp" />   
 		  	  
 		  	            <!--  ROOM IMAGE UPLOADING  -->
           <div class="beauty">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility">Image Name:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_image" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/>
 				<form id="uploadImage" action="uploadRoomTypeImage.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=" "/>
   				  <input type="hidden" name="roomType.id" value="<s:property value="roomType.id"/>"/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button>
    			  <div>Upload Room Type Photo</div>  
				</form> 
		   	  </div>
		   	</div>
		   	<div class="subcolumns">
        <ul class="thumbs">
        <s:iterator value="roomType.images" var="eachImage" >
        					<li>
        					<h4><s:property value="#eachImage.name" /></h4>
								<a title="<s:property value="#eachImage.name" />" href="#drop" class="thumb" rel="history">
									<img alt="<s:property value="#eachImage.name" />" src="images/roomtype/<s:property value="#eachImage.fileName" />" />
									</a>
									<span><s:property value="#eachImage.name" /><a class="erase_image" href="deleteRoomTypeImage.action?image.id=<s:property value="#eachImage.id" />"  title="erase"><img src="images/delete.png" alt="Delete Image" /></a></span>
								
							</li>
        </s:iterator>
							<li style="display: none">
							<h4>NEW</h4>
								<a title="Title #1" href="#drop" class="thumb" rel="history">
									<img alt="Title #1" src="images/roomtype/" />
								</a>
								<span class="name_image">__PVALUE__<a class="erase_image" href="deleteRoomTypeImage.action?image.id="  title="erase"><img src="images/delete.png" alt="Delete Image" /></a></span>
										
							</li>
							</ul>
						</div>
		   	
		   	    
          </div>
         
         <div class="subcolumns">
				<div class="result_facility_upload" id="result_facility_upload" ></div>
		   			<div class="upload_loader">&nbsp;</div>
		   			<div class="image_preview"></div>
		</div>
		
		  <!--  END ROOM IMAGE UPLOADING  -->
 		  	  
 		  	  
 		  	   
            </div>        
          </div>
<jsp:include page="jsp/layout/footer.jsp" />     