<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />

        <!-- begin: #col3 static column -->
<script>
	$(function() {
		
		
		$( "#largeDatepicker" ).datepicker({
			dateFormat: I18NSettings.datePattern,
			 onSelect: function(dateText, inst) {
				 var selectedData =  $.datepicker.formatDate(I18NSettings.datePattern,$(this).datepicker("getDate"));
				 if ( selectedData)
					 {
						$('input:hidden[name="dateArrival"]').val(selectedData);

					 }
				  
			 }
			
		});
		
	  	$(".btn_next").button({
	  		icons: {
	            primary: "ui-icon-seek-next"
	        }
	  	});
	  	
	  	
	  	$(".btn_next").click(function(event){
	  		//event.preventDefault();
	  		//goOnlineBookingCalendar
	  		//goOnlineBookingRooms
	  		//goOnlineBookingExtras
	  		//goOnlineBookingGuest
	  		//goOnlineBookingFinal
	  		if ( $(this).parents(".yform.json").valid() )
	  		{
	  			
	  			var formData = $(this).parents(".yform.json").serialize();
	  			var action = $(this).parents(".yform.json").attr("action");

	  			$.ajax({
	  		      url: action,
	  		      global: false,
	  		      type: "POST",
	  		      data: formData,
	  		      dataType: "html",
	  		     beforeSend: function(msg){
	  		    	$(".widget-booking").html("<img src=\"images/loading.gif\" />");
	  		    	 
	  		      },
	  		      success: function(data){
	  		    	$(".widget-booking").html(data);
	  		      },
	  		     error: function(msg){
	  		         alert(msg);
	  		      }
	  		   }
	  		)
	  			
	  		}
	  		
	  		// return false;
	  	});
	  	
	});
	
	
	</script>
	<style>
	body {
		background: #4D87C7;
	}
		#largeDatepicker {

			width: 99%;
			height: 80%;
			}
	.ui-datepicker {

		width:100%;
		height: 100%;
		}
		
			.widget-booking {

		width:100%;
		height: 100%;
		}
		
		.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
		
		height:30px;
			}
			h2{
			color: #fff;
			}
			.yform label {
color:#fff;
text-align: left;
}
	</style>
			<div class="widget-booking">
			<div><h2>Select your arrival date</h2></div>
            <div id='largeDatepicker'></div>
            <form action="goOnlineBookingRooms.action" class="yform json">
            <input type="hidden" name="dateArrival" value="" />
            <div class="c33l">
            <div class="subcl type-select">
               				 <label for="sel_rooms_list">Nights<sup title="This field is mandatory.">*</sup> </label>
                			 <select name="booking.room.id" id="sel_rooms_list" size="1">
                      	<option value="1">1</option>
                      	<option value="2">2</option>
                      	<option value="3">3</option>
                    		</select>
            				</div>
            				</div>
            				            <div class="c33l">
            <div class="subcl type-select">
               				 <label for="sel_rooms_list">People<sup title="This field is mandatory.">*</sup> </label>
                			 <select name="booking.room.id" id="sel_rooms_list" size="1">
                      	<option value="1">1</option>
                      	<option value="2">2</option>
                      	<option value="3">3</option>
                    		</select>
            				</div>
            				</div>
            	 <div class="c33l">
            <div class="subcl type-select">
            <button class="btn_next">NEXT</button>
            </div>
            </div>
           </form>
</div>
