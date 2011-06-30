<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />

  <div id="main">
    <!-- begin: #col1 - first float column -->
    <div id="col1" role="complementary">
      <div id="col1_content" class="clearfix"></div>
    </div><!-- end: #col1 -->
    <!-- begin: #col3 static column -->
    <div id="col3" role="main">
      <div id="col3_content" class="clearfix">
      
        <div class="header_section yform">
          <p class="navigation"> <a class="home" href="<s:property value="url_findallroom"/>?sect=accomodation"></a><b>»</b> 
		  <span>&nbsp;</span></p>
          <span class="name_section"><s:text name="roomDetails" /></span>
        </div>
        
        <div>	
 		  <jsp:include page="jsp/contents/room_form.jsp" />
 		  
 		  <!--  ROOM IMAGE UPLOADING  -->
           <div class="beauty">
     	  	<div class="subcolumns">
           	  <div class="c33l">
    			<label for="name_facility"><s:text name="imageName" />:</label>&nbsp;<input type="text" name="facility_name" value="" id="name_image" class="require"/>
 			  </div>
 			  <div class="c20l">
 				<br/>
 				<form id="uploadImage" action="uploadRoomImage.action" method="post" enctype="multipart/form-data">
   				  <input type="hidden" name="name" value=" "/>
   				  <input type="hidden" name="room.id" value="<s:property value="room.id"/>"/>
     			  <input type="file" name="upload" multiple/>
    			  <button>Upload</button>
    			  <div><s:text name="uploadRoomImage" /></div>  
				</form> 
		   	  </div>
		   	</div>
		   	<div class="subcolumns">
        <ul class="thumbs">
        <s:iterator value="room.images" var="eachImage" >
        					<li>
        					<h4><s:property value="#eachImage.name" /></h4>
								<a title="<s:property value="#eachImage.name" />" href="#drop" class="thumb" rel="history">
									<img alt="<s:property value="#eachImage.name" />" src="images/room_images/<s:property value="#eachImage.fileName" />" />
									</a>
									<span><s:property value="#eachImage.name" /><a class="erase_image" href="deleteRoomImage.action?image.id=<s:property value="#eachImage.id" />"  title="erase"><img src="images/delete.png" alt="Delete Image" /></a></span>
								
							</li>
        </s:iterator>
							<li style="display: none">
							<h4><s:text name="new" /></h4>
								<a title="Title #1" href="#drop" class="thumb" rel="history">
									<img alt="Title #1" src="images/room_images/" />
								</a>
								<span class="name_image">__PVALUE__<a class="erase_image" href="deleteRoomImage.action?image.id="  title="erase"><img src="images/delete.png" alt="Delete Image" /></a></span>
										
							</li>
							</ul>
						</div>
		   	
		   	    
          </div>
          <!-- END ROOM IMAGE UPLOADING  -->
        </div>
                   
      </div>
          
<jsp:include page="jsp/layout/footer.jsp" />     