$(document).ready(function() {
		
		/*change rate for room*/
	 $.fn.changeRate = function (amount, first, second) {
		 var currency = "&euro";
		 var to_replace ="Per ";
		 var result = false;
		 if( typeof amount !== "undefined" && typeof first !== "undefined" && typeof second !== "undefined")
			 {
			 
			 var infos = $(this).children("span");
		 if(infos  && typeof infos === "object"  && infos.length)
			 {
			 
			 try {
				infos.eq(0).html(amount + ' ' + currency);
				 infos.eq(1).html(' / ' + first.replace(to_replace, ""));
				 infos.eq(2).html(' / ' + second.replace(to_replace, "")); 
				 result = true;
			 }
			 
			 catch(e)
			 {
				result = false; 
				 
			 }		 		

			 }	
		 
			 }
		 
		 return result;
		 
	 }
	 
	
  	$(".btn_check_in").button({
  		icons: {
            primary: "ui-icon-check"
        }
  	});
  	
	   /* extras and pay adjustment */
	   
	   $('input[name="extra_value_adjustment[]"], input[name="pay_value_adjustment[]"]').keyup(function() {
		   var current_parent=$(this).parents(".type-text");
		   /* prepare selector string for class whit whitespaces */
		   var current_class_selector = $(this).attr("class").replace( new RegExp(" ","g"), ".");
		   /* check if current was cloned */
		   var next_sibling = current_parent.next().find("." + current_class_selector);
		   if( next_sibling  && ! next_sibling.size() > 0 )
			   {
				   var copy_parent = current_parent.clone(true);
		   copy_parent.find(".green").remove();
		   copy_parent.find("input").val("");
		   //copy_parent.find($(this)).bind('keyup',cloneEvent);
		   copy_parent.insertAfter(current_parent);	   
			   
			   }

		   /* adjust subtotal ... */
		   var new_subtotal = null;
		   if(current_class_selector.indexOf("extra_value_adjustment") >= 0)
		   { new_subtotal = parseInt($("#subtotal_room").val()); 
		   
		      /* code for calcute new subtotal */
			  $("." + current_class_selector).each( function(key, value) {
				   
				  if( $(value).valid() )
				   {
			   new_subtotal = new_subtotal + parseInt ( $(value).val() );
				   }
		  		   });   
		     $(".subtotal_room").html(new_subtotal);
		   /* end code for subtotal calculation */
		   }
		   else
		   { new_subtotal = parseInt($("#balance_room").val()); 
		      /* code for calcute new subtotal */
			  $("." + current_class_selector).each( function(key, value) {
				   
				  if( $(value).valid() )
				   {
			   new_subtotal = new_subtotal - parseInt ( $(value).val() );
				   }
		  		   });   
		     $(".balance_room").html(new_subtotal);
		   /* end code for subtotal calculation */
		   
		   
		   }
		  
		   
		   
		  //--- $(this).unbind('keyup');
		   
		 });
	   
		$(".type_rooms").hide();
		$("#change_rate").toggle(function(){
		$(".type_rooms").show();
		$(this).html("done");
		},function(){
		$(".type_rooms").hide();
		
		_first = $('input:radio[name="per_room_person"]:checked');
		 _first = (_first.val() !== "")? _first.siblings("label").html() : "error";
		_second = $('input:radio[name="per_night_week"]:checked');
		_second = (_second.val() !== "")? _second.siblings("label").html() : "error";
		_amount = $('input[name="per_value"]');
		_amount = (_amount.val() !== "")? _amount.val() : "error";
		
		if($("#rate").changeRate(_amount, _first, _second) !== false);
		$(this).html("change rate for this booking");
		});
	   
		  $(".yform").validate();
		
   var $calendar = $('#calendar');
   var id = 10;
   /* setting rooms_list array */
   var list_rooms=new Array(1, 4, 23, 35,36,37,38,39,40,41,42,43,44,45,49,52,53,54,55);
   /* setting number of rooms */
	var num_rooms=list_rooms.length;
   $calendar.weekCalendar({
      timeslotsPerHour : 1,
      use24Hour: true,
       newEventText : "Room",
      timeslotHeight: 30,
      defaultEventLength: 1 ,
      allowCalEventOverlap : false,
      overlapEventsSeparate: false,
      firstDayOfWeek : 1,
       businessHours :{start: 0, end: num_rooms, limitDisplay: true },
      daysToShow :10,
      //added by Alberto
      listRooms: list_rooms,
      buttonText:{today : "today", lastWeek : "Prev", nextWeek : "Next"},
      height : function($calendar) {
         return $(window).height() - $("h1").outerHeight() - 1;
      },
      eventRender : function(calEvent, $event) {
         if (calEvent.end.getTime() < new Date().getTime()) {
            $event.css("backgroundColor", "#aaa");
            $event.find(".wc-time").css({
               "backgroundColor" : "#999",
               "border" : "1px solid #888"
            });
         }
      },
      draggable : function(calEvent, $event) {
         return calEvent.readOnly != true;
      },
      resizable : function(calEvent, $event) {
         return calEvent.readOnly != true;
      },
      //metodo per la creazione di una nuova casella
      eventNew : function(calEvent, $event) {
         var $dialogContent = $("#event_edit_container");
         resetForm($dialogContent);
         $dialogContent.find("#date_booking").html(calEvent.start + ' - ' + calEvent.end);
         var duration = days_between(new Date(calEvent.end), new Date(calEvent.start)) + 1;
         var remainings = days_between_signed(new Date(calEvent.start), new Date());
         
         if (remainings < -1)
        	 {
        	 $(".btn_check_in").button("disable");
        	 }
         $dialogContent.find("#duration").html(' ( ' + duration + ' days )');
         var startField = calEvent.start;
         var endField = calEvent.end;
         var id_booked = calEvent.id_booked;
         var room_name =  getRoomNameById(id_booked);
         $dialogContent.find('#room_name_dialog').text(room_name);
         var titleField = $dialogContent.find("input[name='fullname']");
         var bodyField = $dialogContent.find("textarea[name='body']");
         var confirmField = $dialogContent.find("select[name='confirm']");
         getCustomers("input[name='fullname']");
         
         
         
         
         
         $dialogContent.dialog({
            modal: true,
            width:650,
            hide: "explode",
            show:"blind",
            title: "New Booking for room: " + room_name,
            close: function() {
               $dialogContent.dialog("destroy");
               $dialogContent.hide();
              $('#calendar').weekCalendar("removeUnsavedEvents");
            },
            buttons: {
               save : function() {
                if ($(".yform").valid())
              	 {
            	   calEvent.id = id_booked;
                  id++;
                  calEvent.start = new Date(startField);
                  calEvent.end = new Date(endField);
                  calEvent.title = titleField.val();
                  calEvent.body = bodyField.val();
                  calEvent.confirm = confirmField.val();

                  //--$calendar.weekCalendar("removeUnsavedEvents");
                 $calendar.weekCalendar("updateBookEvent", calEvent);
                	 $dialogContent.dialog("close");
                	 }
                  
               },
               cancel : function() {
            	 
                  $dialogContent.dialog("close");
                  
               }
            }
         }).show();

         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));

      },
      eventDrop : function(calEvent, $event) {
        
      },
      eventResize : function(calEvent, $event) {
      },
      //metodo che viene richiamato quando clicco su una casella
      eventClick : function(calEvent, $event) {

         if (calEvent.readOnly) {
            return;
         }

         var $dialogContent = $("#event_edit_container");
         resetForm($dialogContent);
         //calEvent è un tipo di dato data, che è un oggetto con degli attributi start end e title
         //per cui, se clicco in una casella che ha già un data calEvent, allora setterò con 
         //questi valori i campi di testo nella finestra di dialogo.
         
         $dialogContent.find("#date_booking").html(calEvent.start + ' - ' + calEvent.end);
         var startField = calEvent.start;
         var endField = calEvent.end;
         var id_booked = calEvent.id_booked;
         var room_name =  getRoomNameById(id_booked);
         $dialogContent.find('#room_name_dialog').text(room_name);
         var titleField = $dialogContent.find("input[name='fullname']");
         var bodyField = $dialogContent.find("textarea[name='body']");
         bodyField.val(calEvent.body);

         $dialogContent.dialog({
            modal: true,
            width: 650,
            title: "Modify Booking - " + room_name,
            close: function() {
               $dialogContent.dialog("destroy");
               $dialogContent.hide();
               $('#calendar').weekCalendar("removeUnsavedEvents");
            },
            buttons: {
               save : function() {
            	  calEvent.id =id_booked;
                  calEvent.start = new Date(startField.val());
                  calEvent.end = new Date(endField.val());
                  calEvent.title = titleField.val();
                  calEvent.body = bodyField.val();

                  $calendar.weekCalendar("updateBookEvent", calEvent);
                  $dialogContent.dialog("close");
               },
               "delete" : function() {
                  $calendar.weekCalendar("removeEvent", calEvent.id);
                  $dialogContent.dialog("close");
               },
               cancel : function() {
                  $dialogContent.dialog("close");
               }
            }
         }).show();

         var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
         var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
         $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
         setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
         $(window).resize().resize(); //fixes a bug in modal overlay size ??

      },
      eventMouseover : function(calEvent, $event) {
      },
      eventMouseout : function(calEvent, $event) {
      },
      noEvents : function() {

      },
      data : function(start, end, callback) {
         callback(getEventData());
      }
   });

   function resetForm($dialogContent) {
      $dialogContent.find("input:text").val("");
      $dialogContent.find("textarea").val("");
   }
   
   function days_between(date1, date2) {

	    // The number of milliseconds in one day
	    var ONE_DAY = 1000 * 60 * 60 * 24;

	    // Convert both dates to milliseconds
	    var date1_ms = date1.getTime();
	    var date2_ms = date2.getTime();

	    // Calculate the difference in milliseconds
	    var difference_ms = Math.abs(date1_ms - date2_ms);
	    
	    // Convert back to days and return
	    return Math.round(difference_ms/ONE_DAY);

	}
   
   function days_between_signed(date1, date2) {

	    // The number of milliseconds in one day
	    var ONE_DAY = 1000 * 60 * 60 * 24;

	    // Convert both dates to milliseconds
	    var date1_ms = date1.getTime();
	    var date2_ms = date2.getTime();

	    // Calculate the difference in milliseconds
	    var difference_ms = date1_ms - date2_ms;
	    
	    // Convert back to days and return
	    return Math.round(difference_ms/ONE_DAY);

	}
   
   
   function getCustomers(selector)
   {
		var cache = {},
		lastXhr;

	   $(selector).autocomplete({
			minLength: 2,
			source: function( request, response ) {
				var term = request.term;
				if ( term in cache ) {
					response( cache[ term ] );
					return;
				}

				lastXhr = $.getJSON( "customer.json", request, function( data, status, xhr ) {
					cache[ term ] = data.customers;
					if ( xhr === lastXhr ) {
						response( data.customers );
					}
				});
			}
		});


	   
   }

   function getRoomNameById(id)
   {
	    var input_room_id = $('input[name="id_room"]').filter(function(){
		 return ($(this).val() == id);
	    	});
	   var name  = $(input_room_id).siblings();
	   if(name.text() != undefined)
		   return name.text();
	   else
		   return '******';
	
   }
   function getEventData() {
      var year = new Date().getFullYear();
      var month = new Date().getMonth();
      var day = new Date().getDate();

      return {
         events : [
            {
               "id":1,
               "start": new Date(year, month, day, 12),
               "end": new Date(year, month, day, 13, 30),
               "title":"Giovanni Stara"
            },
            {
               "id":2,
               "start": new Date(year, month, day, 14),
               "end": new Date(year, month, day, 14, 45),
               "title":"Marc Devois"
            },
            {
               "id":3,
               "start": new Date(year, month, day + 1, 17),
               "end": new Date(year, month, day + 1, 17, 45),
               "title":"Laura Saint"
            },
            {
               "id":4,
               "start": new Date(year, month, day - 1, 8),
               "end": new Date(year, month, day - 1, 9, 30),
               "title":"George Mason"
            },
            {
               "id":5,
               "start": new Date(year, month, day + 1, 14),
               "end": new Date(year, month, day + 1, 15),
               "title":"Michele Gors"
            },
            {
               "id":6,
               "start": new Date(year, month, day, 10),
               "end": new Date(year, month, day, 11),
               "title":"Katia Solari",
               readOnly : true
            }

         ]
      };
   }


   /*
    * Sets up the start and end time fields in the calendar event
    * form for editing based on the calendar event being edited
    */
   function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

      for (var i = 0; i < timeslotTimes.length; i++) {
         var startTime = timeslotTimes[i].start;
         var endTime = timeslotTimes[i].end;
         var startSelected = "";
         if (startTime.getTime() === calEvent.start.getTime()) {
            startSelected = "selected=\"selected\"";
         }
         var endSelected = "";
         if (endTime.getTime() === calEvent.end.getTime()) {
            endSelected = "selected=\"selected\"";
         }
         $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
         $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

      }
      $endTimeOptions = $endTimeField.find("option");
      $startTimeField.trigger("change");
   }

   var $endTimeField = $("select[name='end']");
   var $endTimeOptions = $endTimeField.find("option");

   //reduces the end time options to be only after the start time options.
   $("select[name='start']").change(function() {
      var startTime = $(this).find(":selected").val();
      var currentEndTime = $endTimeField.find("option:selected").val();
      $endTimeField.html(
            $endTimeOptions.filter(function() {
               return startTime < $(this).val();
            })
            );

      var endTimeSelected = false;
      $endTimeField.find("option").each(function() {
         if ($(this).val() === currentEndTime) {
            $(this).attr("selected", "selected");
            endTimeSelected = true;
            return false;
         }
      });

      if (!endTimeSelected) {
         //automatically select an end date 2 slots away.
         $endTimeField.find("option:eq(1)").attr("selected", "selected");
      }

   });


   var $about = $("#about");

 


});