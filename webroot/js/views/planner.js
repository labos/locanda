/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/

$(function () {
    /*  
     * @class Room 
     * @constructor
     * Creates a new Room minimal object for calendar.
     * @param {Number} room id
     * @param {String} room name
     */

    function Room(room_id, room_name, max_guests) {
        this.id = room_id;
        this.name = room_name;
        this.maxGuests = max_guests;
    }
    /*
     * @class Calendar
     * @parent Class
     * @constructor
     * Create a new Calendar instance.
     * @tag controllers
     * @author LabOpenSource
     */
    
    
    var EditPlannerView = Backbone.View.extend({


    	  initialize: function() {
    		  
    		  _.bindAll(this, "loadCalendar", "listRoomsSuccess", "listRoomsError");
              /**
               * @attribute booking
               * Instance of booking for adding new booking action or editing it
               */
              this.booking = null;
              /**
               * @attribute list_room
               * Array to contain list of rooms retrieved by json action response
               */
              this.list_rooms = [];
              /**
               * @attribute num_room
               * @type Number
               * Number of rooms retrieved by json action response
               */
              this.num_rooms = 0;
              /**
               * @attribute $calendar 
               * @type Object
               * Jquery Object Dom in which to rendering the calendar (private variable)
               */
              this.$calendar = $('#calendar');
              if (this.$calendar.length > 0) {
            	  var self = this;
                  //get real rooms list
               //--   Models.Room.findAll({'rnd': Math.floor(Math.random()*10000)}, self.callback('listRoomsSuccess'), self.callback('listRoomsError'));
          		$.ajax({
        			url: 'findAllRoomsJson.action',
        			type: 'get',
        			dataType: 'json',
        			data: {},
        			success: self.listRoomsSuccess,
        			error: self.listRoomsError
        		});
            	  
            	  $(".type_rooms").hide();
            	  this.render();
              }
    		  
    	  },

    	  render: function() {
    	   
    	  },
    	  
          /*
           * Attach a LoadCalendar function to Jquery object Dom $calendar.
           */
          loadCalendar: function () {
              var self = this;
              this.$calendar.weekCalendar({
                  timeslotsPerHour: 1,
                  use24Hour: true,
                  newEventText: "Room",
                  timeslotHeight: 30,
                  defaultEventLength: 1,
                  allowCalEventOverlap: false,
                  overlapEventsSeparate: false,
                  firstDayOfWeek: 1,
                  businessHours: {
                      start: 0,
                      end: self.num_rooms,
                      limitDisplay: true
                  },
                  daysToShow: 10,
                  listRooms: self.list_rooms,
                  buttonText: {
                  	  go: $.i18n("goOnADate"),
                      today: $.i18n("today"),
                      lastWeek: $.i18n("prev"),
                      nextWeek: $.i18n("next")
                  },
                  height: function () {            	  
                	  return $(".wc-time-slots").height() + 140;
                	  //uncomment following line if you want a scroll activation.
                      //return $(window).height() - $("h1").outerHeight() - 1;
                  },
                  eventRender: function (calEvent, $event) {
                      if (calEvent.end.getTime() < new Date().getTime()) {
                          $event.css("backgroundColor", "#aaa");
                          $event.find(".wc-time").css({
                              "backgroundColor": "#999",
                              "border": "1px solid #888"
                          });
                      }
                      
                      
                  },
                  calendarAfterLoad : function(calendar) {
                	  // set scrolling calendar
                
                		  if($("div.wc-scrollable-grid").height() > 300){ 
                			  
                			  var self = $("div.wc-scrollable-grid").height(300).css("overflow", "hidden"); 
                			  self.slimScroll({
                			        height: '300px'
                			    });
                			  }

                  },
                  draggable: function (calEvent, $event) {
                      return calEvent.readOnly != true;
                  },
                  resizable: function (calEvent, $event) {
                      return calEvent.readOnly != true;
                  },
                  /*
                   * Event that occurs when an user click or drag on the calendar.
                   * @param {Object} a calendar event object.
                   * @param {Object} an Jquery Dom object .
                   */
                  eventNew: function (calEvent, $event) {
                      var $dialogContent = $("#event_edit_container");
                      var startField = calEvent.start;
                      var endField = calEvent.end;
                      var id_booked = calEvent.id_booked;
                      var id_room = calEvent.id;
                      var room_name = self.getRoomNameById(id_booked);
                      $dialogContent.load("goAddBookingFromPlanner.action", {
                          'booking.room.id': id_booked,
                          'booking.dateIn': startField,
                          'booking.dateOut': endField
                      }, function () {
                          new Main(I18NSettings.lang, I18NSettings.datePattern);
                          delete (self.booking);
                          self.booking = new Controllers.Booking(I18NSettings.lang, I18NSettings.datePattern);
                          $(".btn_save").hide();
                          $(".canc_booking").hide();
                          $(".btn_check_in").hide();
                          //handle form submit event 
                          $dialogContent.find(".yform.json").bind('submitForm', function(e,data){
                        	  if(data.type == "success"){
                        		  $(this).unbind('submitForm');
                        		  self.booking.modified = false;
                                  $dialogContent.dialog("close");
                        	  }});
                      }).dialog({
                          open: function (event, ui) {
                              $(".btn_save").hide();
                          },
                          modal: true,
                          width: 790,
                          show: "blind",
                          closeOnEscape: true,
                          title: $.i18n("newBookingForRoom") + ": " + room_name,
                          beforeClose: function( event, ui ) {
                              if (self.booking.modified)  {
                            	  if(confirm($.i18n("alertCancel"))){
                                      $dialogContent.dialog("destroy");
                                      self.$calendar.weekCalendar("removeUnsavedEvents");
                                      self.$calendar.weekCalendar("refresh");  
                            	  }else{
                            		  return false;
                            	  }
                              }
                                  else{
                                      $dialogContent.dialog("destroy");
                                      self.$calendar.weekCalendar("removeUnsavedEvents");
                                      self.$calendar.weekCalendar("refresh");
                                  }
                          },
                          buttons: [
                                   {text: $.i18n("save"),
                                    click: function() {
                                  	  if(!$dialogContent.find(".yform.json").valid()) {
                                  		  $("#accordion,#accordion2").accordion("option", "active", 0);
                                  	  }
                                    $dialogContent.find(".yform.json").submitForm(); }
                                   },
                                   {text: $.i18n("erase"),
                                    click: function() {      
                                
                                            $dialogContent.dialog("close");
                                            self.$calendar.weekCalendar("removeEvent", calEvent.id);

                                     }
                                    }
                                    ]
                      }).show();
                  },
                  eventDrop: function (calEvent, $event) {},
                  eventResize: function (calEvent, $event) {},
                  //method that is invoked when I click on a box
                  eventClick: function (calEvent, $event) {
                      if (calEvent.readOnly) {
                          return;
                      }
                      var $dialogContent = $("#event_edit_container");
                      //calEvent is a "data" jquery data. It's an object with start, end, and title properties
                      var startField = calEvent.start;
                      var endField = calEvent.end;
                      var id_booked = calEvent.bookId;
                      var id_room = calEvent.id;
                      var room_name = self.getRoomNameById(id_room);
                      $dialogContent.addClass("loaderback").load("goUpdateBookingFromPlanner.action", {
                          id: id_booked
                      }, function () {
                          $(this).removeClass("loaderback");
                          new Main(I18NSettings.lang, I18NSettings.datePattern);
                          delete (self.booking);
                          self.booking = new Controllers.Booking(I18NSettings.lang, I18NSettings.datePattern);
                          $(".btn_save").hide();
                          $(".canc_booking").hide();
                          //handle form submit event 
                          $dialogContent.find(".yform.json").bind('submitForm', function(e,data){
                        	  if(data.type == "success"){
                        		  $(this).unbind('submitForm');
                        		  self.booking.modified = false;
                                  $dialogContent.dialog("close");
                        	  }});
                      }).dialog({
                          open: function (event, ui) {               	  
                          },
                          modal: true,
                          width: 790,
                          position: 'top',
                          closeOnEscape: false,
                          title: $.i18n("modifyBooking") + " - " + room_name,
                          beforeClose: function( event, ui ) {
                              if (self.booking.modified)  {
                            	  if(confirm($.i18n("alertCancel"))){
                                      $dialogContent.dialog("destroy");
                                      self.$calendar.weekCalendar("removeUnsavedEvents");
                                      self.$calendar.weekCalendar("refresh");  
                            	  }else{
                            		  return false;
                            	  }
                              }
                              else{
                                  $dialogContent.dialog("destroy");
                                  self.$calendar.weekCalendar("removeUnsavedEvents");
                                  self.$calendar.weekCalendar("refresh");
                              }
                          },
                          buttons: [
                                    {
                                        text: $.i18n("save"),
                                        click: function() {
                                            if (!$dialogContent.find(".yform.json").valid()) {
                                                $("#accordion,#accordion2").accordion("option", "active", 0);
                                            }
                                            $dialogContent.find(".yform.json").submitForm();
                                        }
                                    },
                                    {
                                        text: $.i18n("erase"),
                                        click: function() {
                                            if (confirm($.i18n("alertDelete"))) {
                                                $dialogContent.find(".yform.json").submitForm("deleteBooking.action");
                                                
                                            }
                                        }
                                    },
                                    {
                                        text: $.i18n("close"),
                                        click: function() {
                                                $dialogContent.dialog("close");
                                        }
                                    }
                                    ]                        
                          
                        
                      }).show();
                      $(window).resize().resize(); //fixes a bug in modal overlay size ??
                  },
                  eventMouseover: function (calEvent, $event) {},
                  eventMouseout: function (calEvent, $event) {},
                  noEvents: function () {},
                  data: "findAllBookingsByStartDateAndLengthOfStay.action"
              });
          },
          /**
           * Displays a list of Rooms.
           * @param {String} list of json Room objects.
           */
          listRoomsSuccess: function (data) {
              var self = this;
              //iterate over the list
              $(data).each(function (i, val) {
                  //add current room to room list 
                  self.list_rooms.push(new Room(val.id, val.name,val.roomType.maxGuests));
              });
              //calculates the new lenght of the list
              this.num_rooms = this.list_rooms.length;
              //now load calendar 
              this.loadCalendar();
          },
          /**
           * Show a notification.
           * @param {String} list of json Room objects.
           */
          listRoomsError: function () {
              $.jGrowl($.i18n("listRoomsRetrieve"), { theme: "notify-error",sticky: true});
          },
          /*
           * Get number of days between two dates.
           * @param {Date} first date.
           * @param {Date} second date.
           * @return {Number} number of days
           */
          days_between: function (date1, date2) {
              // The number of milliseconds in one day
              var ONE_DAY = 1000 * 60 * 60 * 24;
              // Convert both dates to milliseconds
              var date1_ms = date1.getTime();
              var date2_ms = date2.getTime();
              // Calculate the difference in milliseconds
              var difference_ms = Math.abs(date1_ms - date2_ms);
              // Convert back to days and return
              return Math.round(difference_ms / ONE_DAY);
          },
          /*
           * Get Room name by id value.
           * @param {Number} id number.
           * @return {String} name of room
           */
          getRoomNameById: function (id) {
              var input_room_id = $('input[name="id_room"]').filter(function () {
                  return ($(this).val() == id);
              });
              var name = $(input_room_id).siblings().first();
              if (name.text() != undefined) return name.text();
              else return '******';
          }
    	});



new EditPlannerView();

});