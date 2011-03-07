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
          <span class="name_section">Add New Room</span>
          <div class="right">
            </div>
          </div>
          <div>
		 <form method="post" action="" class="yform" role="application">
            <fieldset>
              <legend>
              <label for="room_name_id">Name Room:</label>
              <input id="room_name_id" name="name" type="text" value="Enter Room Name" /></legend>
             <div class="c50l">
             <div class="subcolumns">
             <span>Save room to define prices or minimum stay
</span>       </div>
                    <div class="type-select">
      		<label for="category_id">Category:</label>
    				<select id="category_id" name="roomType" size="1" aria-required="true">
					<option selected="selected" value="1">Singola</option>
					<option value="2">Doppia</option>
					<option value="3">Tripla</option>
                </select>
                </div>
                <div class="type-text" >
                <strong>&nbsp;&nbsp;OR</strong>
                </div>
                <div class="type-text" >
               <label for="category_new_id">New Category:</label> 
               <input  type="text"  id="category_new_id"  name="roomType" />
                    </div> 
              <div class="subcolumns type-text">
      <label for="price_room_id">Price</label><input type="text" class="small_input" id="price_room_id" name="price" value="30" />
             <span>&nbsp;&euro;</span>
              </div>
              <div class="subcolumns type-text">
      		<label for="max_guests_id">Max Guests:</label>
				 <input id="max_guests_id" type="text" class="small_input" name="maxGuests" value="1" />
		     </div>
			 <div class="subcolumns">
              &nbsp;
              </div>

            <div class="subcolumns type-text">
      		<label for="notes_id">Note:</label>&nbsp;
      		<textarea id="notes_id" name="notes" rows="5" cols="60"></textarea>
      		</div>
      		<div class="subcolumns">
              &nbsp;
              </div>
            <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_save">SAVE</button>
            <button class="btn_delete">CANCEL</button>
            </div>
            
            </div>
              </fieldset>
           </form>        
		</div>        
          </div>
<jsp:include page="jsp/layout/footer.jsp" />     