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
          <span class="name_section">Manage Rooms</span>
          <div class="right">
		    <button class="btn_add_new">ADD NEW</button>
            <button class="btn_save_all btn_save_all_rooms">SAVE ALL</button>
            </div>
          </div>
        
         <s:iterator value="rooms" var="each">
          <div>
         
          
         
          
		 <form method="post" action="updateRoom.action" class="yform json full" role="application">
            <fieldset>
              <legend>
              <input type="hidden" name="redirect_form" value="findAllRooms.action" />
                <input class="describe required" style="width:60px; display: inline;" readonly="true" type="text" name="room.name" value="<s:property value="name"/>"  />
            	<a class="describe_edit" href="#" title="describe"><img src="images/sign-up-icon.png" alt="edit" /></a>
            	</legend>
				<div class="subcolumns type-text">
				<input type="hidden" name="room.id" value="<s:property value="id"/>"/>
              <div class="">
              <span>Room type:&nbsp;</span>
              <input class="describe  required" style="width:60px; display: inline;" readonly="true" type="text" name="room.roomType" value="<s:property value="roomType"/>"  />
            	<a class="describe_edit" href="#" title="describe"><img src="images/sign-up-icon.png" alt="edit" /></a>
              </div>
              </div>
             <div class="subcolumns type-text">
      <span class="title_season">Price</span>&nbsp; <input type="text" class="small_input  required number" id="price" name="room.price" value="<s:property value="price"/>" />
             <span>&nbsp;&euro;</span>
              </div>
               <div class="subcolumns">
              &nbsp;
              </div>
              <div class="subcolumns type-text">
            <div class="c10l">
      		<span class="title_season">Max Guests:</span>&nbsp;
      		</div>
      		<div class="c10l">
      		        <div class="type_rooms">
                      <input type="input" class="small_input  required number" name="room.maxGuests" value="<s:property value="maxGuests"/>" />
                    </div>                
                    </div>
              </div>
			 <div class="subcolumns">
              &nbsp;
              </div>
            <div class="subcolumns type-text">
            <div class="c50l">
      		<span class="title_season">Notes:</span>&nbsp;
      		<textarea name="room.notes" rows="5" cols="60"><s:property value="notes"/></textarea>
      		</div>
      		</div>
      		<div class="subcolumns">
              &nbsp;
              </div>
            <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_save btn_update_room">SAVE</button>
            <button class="btn_delete btn_delete_room">DELETE</button>
            </div>
              </fieldset>
           </form>        
		</div>  
		
		 </s:iterator>      
          </div>
<jsp:include page="jsp/layout/footer.jsp" />     