/*
 * jQuery.weekCalendar v1.2.3-pre
 * http://www.redredred.com.au/
 *
 * Requires:updateBookEvent
 * - jquery.weekcalendar.css
 * - jquery 1.3.x
 * - jquery-ui 1.7.x (widget, drag, drop, resize)
 *
 * Copyright (c) 2009 Rob Monie
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *   
 *   If you're after a monthly calendar plugin, check out http://arshaw.com/fullcalendar/
 */


$(function() {


   $.widget("ui.weekCalendar", {

       options : {
         date: new Date(),
         timeFormat : "h:i a",
         dateFormat : "M d, Y",
         alwaysDisplayTimeMinutes: true,
         use24Hour : false,
         daysToShow : 7,
         firstDayOfWeek : 0, // 0 = Sunday, 1 = Monday, 2 = Tuesday, ... , 6 = Saturday
         useShortDayNames: false,
         timeSeparator : " to ",
         startParam : "start",
         endParam : "end",
         businessHours : {start: 0, end: 58, limitDisplay : false},
         newEventText : "New Event",
         timeslotHeight: 20,
         defaultEventLength : 2,
         defaultEventWidth: 88,
         timeslotsPerHour : 4,
         buttons : true,
         buttonText : {
        	go: "by date",
            today : "today",
            lastWeek : "&nbsp;&lt;&nbsp;",
            nextWeek : "&nbsp;&gt;&nbsp;"
         },
         scrollToHourMillis : 500,
         allowCalEventOverlap : false,
         overlapEventsSeparate: false,
         readonly: false,
         draggable : function(calEvent, element) {
            return true;
         },
         resizable : function(calEvent, element) {
            return true;
         },
         eventClick : function() {
         },
         eventRender : function(calEvent, element) {
            return element;
         },
         eventAfterRender : function(calEvent, element) {
            return element;
         },
         eventDrag : function(calEvent, element) {
         },
         eventDrop : function(calEvent, element) {
         },
         eventResize : function(calEvent, element) {
         },
         eventNew : function(calEvent, element) {
         },
         eventMouseover : function(calEvent, $event) {
         },
         eventMouseout : function(calEvent, $event) {
         },
         calendarBeforeLoad : function(calendar) {
         },
         calendarAfterLoad : function(calendar) {
         },
         noEvents : function() {
         },
         shortMonths : [$.i18n("jan"), $.i18n("feb"), $.i18n("mar"), $.i18n("apr"), $.i18n("may"), $.i18n("jun"), $.i18n("jul"), $.i18n("aug"), $.i18n("sep"), $.i18n("oct"), $.i18n("nov"), $.i18n("dec")],
         longMonths : ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
         shortDays : ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
         longDays : [$.i18n("sunday"), $.i18n("monday"), $.i18n("tuesday"), $.i18n("wednesday"), $.i18n("thursday"), $.i18n("friday"), $.i18n("saturday")]
      },
      
      clicked : false,

      /***********************
       * Initialise calendar *
       ***********************/
      _create : function() {

         var self = this;
         self._computeOptions();
        // self._setupEventDelegation();

         self._renderCalendar();

         self._loadCalEvents();

         self._resizeCalendar();
         //--self._scrollToHour(self.options.date.getHours());

         $(window).unbind("resize.weekcalendar");
         $(window).bind("resize.weekcalendar", function() {
            self._resizeCalendar();
         });

      },


      /********************
       * public functions *
       ********************/
      /*
       * Refresh the events for the currently displayed week.
       */
      refresh : function() {
         this._loadCalEvents(this.element.data("startDate")); //reload with existing week
      },

      /*
       * Clear all events currently loaded into the calendar
       */
      clear : function() {
         this._clearCalendar();
      },

      /*
       * Go to this week
       */
      today : function() {
         this._clearCalendar();
         this._loadCalEvents(new Date());
      },

      /*
       * Go to the previous week relative to the currently displayed week
       */
      prevWeek : function() {
         //minus more than 1 day to be sure we're in previous week - account for daylight savings or other anomolies
         var newDate = new Date(this.element.data("startDate").getTime() - (MILLIS_IN_WEEK / 6));
         this._clearCalendar();
         this._loadCalEvents(newDate);
      },

      /*
       * Go to the next week relative to the currently displayed week
       */
      nextWeek : function() {
         //add 8 days to be sure of being in prev week - allows for daylight savings or other anomolies
         var newDate = new Date(this.element.data("startDate").getTime() + MILLIS_IN_WEEK + (MILLIS_IN_WEEK / 7));
         this._clearCalendar();
         this._loadCalEvents(newDate);
      },

      /*
       * Reload the calendar to whatever week the date passed in falls on.
       */
      gotoWeek : function(date) {
         this._clearCalendar();
         this._loadCalEvents(date);
      },

      /*
       * Remove an event based on it's id
       */
      removeEvent : function(eventId) {

         var self = this;

         self.element.find(".wc-cal-event").each(function() {
            if ($(this).data("calEvent").id === eventId) {
               $(this).remove();
               return false;
            }
         });

         //this could be more efficient rather than running on all days regardless...
         self.element.find(".wc-day-column-inner").each(function() {
            self._adjustOverlappingEvents($(this));
         });
      },

      /*
       * Removes any events that have been added but not yet saved (have no id).
       * This is useful to call after adding a freshly saved new event.
       */
      removeUnsavedEvents : function() {

         var self = this;

         self.element.find(".wc-new-cal-event").each(function() {
            $(this).remove();
         });

         //this could be more efficient rather than running on all days regardless...
         self.element.find(".wc-day-column-inner").each(function() {
            self._adjustOverlappingEvents($(this));
         });
         
         self._removeAllHoverRoomDiv();
         $(".wc-day-column-inner").css({cursor:"pointer"});
      },

      /*
       * update an event in the calendar. If the event exists it refreshes
       * it's rendering. If it's a new event that does not exist in the calendar
       * it will be added.
       */
      updateEvent : function (calEvent) {
         this._updateEventInCalendar(calEvent);
      },
      
      updateBookEvent : function (calEvent) {
          this._updateBookInCalendar(calEvent);
       },

      /*
       * Returns an array of timeslot start and end times based on
       * the configured grid of the calendar. Returns in both date and
       * formatted time based on the 'timeFormat' config option.
       */
      getTimeslotTimes : function(date) {
         var options = this.options;
         var firstHourDisplayed = options.businessHours.limitDisplay ? options.businessHours.start : 0;
         var startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate(), firstHourDisplayed);

         var times = []
         var startMillis = startDate.getTime();
         for (var i = 0; i < options.timeslotsPerDay; i++) {
            var endMillis = startMillis + options.millisPerTimeslot;
            times[i] = {
               start: new Date(startMillis),
               startFormatted: this._formatDate(new Date(startMillis), options.timeFormat),
               end: new Date(endMillis),
               endFormatted: this._formatDate(new Date(endMillis), options.timeFormat)
            };
            startMillis = endMillis;
         }
         return times;
      },

      formatDate : function(date, format) {
         if (format) {
            return this._formatDate(date, format);
         } else {
            return this._formatDate(date, this.options.dateFormat);
         }
      },

      formatTime : function(date, format) {
         if (format) {
            return this._formatDate(date, format);
         } else {
            return this._formatDate(date, this.options.timeFormat);
         }
      },

      /*********************
       * private functions *
       *********************/


      _setOption: function(key, value) {
         var self = this;
         if(self.options[key] != value) {

            // this could be made more efficient at some stage by caching the
            // events array locally in a store but this should be done in conjunction
            // with a proper binding model.

            var currentEvents = $.map(self.element.find(".wc-cal-event"), function() {
               return $(this).data("calEvent");
            });

            var newOptions = {};
            newOptions[key] = value;
            self._renderEvents({events:currentEvents, options: newOptions}, self.element.find(".wc-day-column-inner"))
        }

	   },
      
	   /*
	    * Compute dynamic options based on other config values.
	    */
      _computeOptions : function() {

         var options = this.options;

         if (options.businessHours.limitDisplay) {
            options.timeslotsPerDay = options.timeslotsPerHour * (options.businessHours.end - options.businessHours.start);
            options.millisToDisplay = (options.businessHours.end - options.businessHours.start) * 60 * 60 * 1000;
            options.millisPerTimeslot = options.millisToDisplay / options.timeslotsPerDay;
         } else {
            options.timeslotsPerDay = options.timeslotsPerHour * 24;
            options.millisToDisplay = MILLIS_IN_DAY;
            options.millisPerTimeslot = MILLIS_IN_DAY / options.timeslotsPerDay;
         }
      },

      /*
       * Resize the calendar scrollable height based on the provided function in options.
       */
      _resizeCalendar : function () {

         var options = this.options;
         if (options && $.isFunction(options.height)) {
            var calendarHeight = options.height(this.element);
            var headerHeight = this.element.find(".wc-header").outerHeight();
            var navHeight = this.element.find(".wc-nav").outerHeight();
            this.element.find(".wc-scrollable-grid").height(calendarHeight - navHeight - headerHeight);
         }
      },

      /*
       * Configure calendar interaction events that are able to use event
       * delegation for greater efficiency
       */
      _setupEventDelegation : function() {
         var self = this;
         var options = this.options;
         //check for preventClick data already saved
         this.element.click(function(event) {
            var $target = $(event.target);
            if ($target.data("preventClick")) {
               return;
            }
            //setting start-date and end-date if a booking box is pushed
            start_date_click = $.datepicker.formatDate(I18NSettings.datePattern,new Date($target.data("calEvent").start));
            end_date_click = $.datepicker.formatDate(I18NSettings.datePattern,new Date($target.data("calEvent").end));

            //check if target has wc-cal-event css class
               if ($target.hasClass("wc-cal-event")) {
               options.eventClick({start: start_date_click, end: end_date_click}, $target, event);
            } else if ($target.parent().hasClass("wc-cal-event")) {
               options.eventClick($target.parent().data("calEvent"), $target.parent(), event);
            }
         }).mouseover(function(event) {
            var $target = $(event.target);

            if (self._isDraggingOrResizing($target)) {
               return;
            }

            if ($target.hasClass("wc-cal-event")) {
               options.eventMouseover($target.data("calEvent"), $target, event);
            }
         }).mouseout(function(event) {
            var $target = $(event.target);
            if (self._isDraggingOrResizing($target)) {
               return;
            }
            if ($target.hasClass("wc-cal-event")) {
               if ($target.data("sizing")) return;
               options.eventMouseout($target.data("calEvent"), $target, event);

            }
         });
      },

      /*
       * check if a ui draggable or resizable is currently being dragged or resized
       * @param {Jquery DOM} dom object in dragging.
       */
      _isDraggingOrResizing : function ($target) {
         return $target.hasClass("ui-draggable-dragging") || $target.hasClass("ui-resizable-resizing");
      },

      /*
       * Render the main calendar layout
       */
      _renderCalendar : function() {

         var $calendarContainer, calendarNavHtml, calendarHeaderHtml, calendarBodyHtml, $weekDayColumns;
         var self = this;
         var options = this.options;

         $calendarContainer = $("<div class=\"wc-container\">").appendTo(self.element);
        

         if (options.buttons) {
            calendarNavHtml = "<div class=\"wc-nav\"><div  id=\"wc-search-date\"><span id=\"search-datepicker\"></span></div><span id=\"toolbar\" class=\"ui-widget-header ui-corner-all\">\
                    <button class=\"wc-search-date\">" + options.buttonText.go + "</button>\
                    <button class=\"wc-today\">" + options.buttonText.today + "</button>\
                    <button class=\"wc-prev\">" + options.buttonText.lastWeek + "</button>\
                    <button class=\"wc-next\">" + options.buttonText.nextWeek + "</button>\
                    </span></div>";


            
            $(calendarNavHtml).appendTo($calendarContainer);
            
            $(".wc-search-date").button({
                icons: {
                    primary: "ui-icon-search"
                }}).next().button({
                icons: {
                    primary: "ui-icon-pin-s"
                }}).next().button({
                    icons: {
                        primary: "ui-icon-triangle-1-w"
                    }
                }).next().button({
                    icons: {
                             secondary: "ui-icon-triangle-1-e"
                    }
                });

			$( "#search-datepicker" ).datepicker(
    				{onSelect: function(dateText, inst) { self.element.weekCalendar("gotoWeek",new Date(dateText));  }}	
    			);
           	$( '#wc-search-date' ).dialog({
           		width: $( "#search-datepicker" ).width() + 35,
           		height: $( "#search-datepicker" ).height() + 100,
           		autoOpen: false,
    			dialogClass:"alert", 
    			create: function(event, ui) {}
    			
    		});
            $calendarContainer.find(".wc-search-date").click(function() {
            	
               	$( '#wc-search-date' ).dialog({
               		autoOpen: true,
        			show: "blind",
        			hide: "explode"});
            	/*
            	$( '#wc-search-date' ).datepicker({
        			onSelect: function(dateText, inst) { self.element.weekCalendar("gotoWeek",new Date(dateText));  }
        		});
            	*/
                return false;
             });            

            $calendarContainer.find(".wc-nav .wc-today").click(function() {
               self.element.weekCalendar("today");
               return false;
            });

            $calendarContainer.find(".wc-nav .wc-prev").click(function() {
               self.element.weekCalendar("prevWeek");
               return false;
            });

            $calendarContainer.find(".wc-nav .wc-next").click(function() {
               self.element.weekCalendar("nextWeek");
               return false;
            });

         }

         //render calendar header
         calendarHeaderHtml = "<table class=\"wc-header\"><tbody><tr><td class=\"wc-time-column-header\">&nbsp;"+ $.i18n("rooms")+ "</td>";
         for (var i = 1; i <= options.daysToShow; i++) {
         //create header of day as a column.
            calendarHeaderHtml += "<td class=\"wc-day-column-header wc-day-" + i + "\"></td>";
         }
         calendarHeaderHtml += "<td class=\"wc-scrollbar-shim\"></td></tr></tbody></table>";

         //render calendar body
         calendarBodyHtml = "<div class=\"wc-scrollable-grid\">\
                <table class=\"wc-time-slots\">\
                <tbody>\
                <tr>\
                <td class=\"wc-grid-timeslot-header\"></td>\
                <td colspan=\"" + options.daysToShow + "\">\
                <div class=\"wc-time-slot-wrapper\">\
                <div class=\"wc-time-slots\">";

         var start = options.businessHours.limitDisplay ? options.businessHours.start : 0;
         var end = options.businessHours.limitDisplay ? options.businessHours.end : 44;

         for (var i = start; i < end; i++) {
            for (var j = 0; j < options.timeslotsPerHour - 1; j++) {
               calendarBodyHtml += "<div class=\"wc-time-slot\"></div>";
            }
            calendarBodyHtml += "<div class=\"wc-time-slot wc-hour-end\"></div>";
         }

         calendarBodyHtml += "</div></div></td></tr><tr><td class=\"wc-grid-timeslot-header\">";

         for (var i = start; i < end; i++) {

            var bhClass = (options.businessHours.start <= i && options.businessHours.end > i) ? "wc-business-hours" : "";
            calendarBodyHtml += "<div class=\"wc-hour-header " + bhClass + "\">"
            //create room column
            if (options.use24Hour) {
               calendarBodyHtml += "<div class=\"wc-time-header-cell\" id=\"" + i + "\"><span>&nbsp;" + self._24HourForIndex(i) + "</span><input type=\"hidden\" name=\"id_room\" value=\""+ options.listRooms[i].id + "\" /><img src=\"images/" + options.listRooms[i].maxGuests + ".png\" /><span class=\"number-guests\">" + options.listRooms[i].maxGuests  +"</span></div>";
            } else {
               calendarBodyHtml += "<div class=\"wc-time-header-cell\" id=\"" + i + "\"><span>&nbsp;" + self._hourForIndex(i) + "</span><span class=\"wc-am-pm\">" + self._amOrPm(i) + "</span></div>";
            }
            calendarBodyHtml += "</div>";
         }

         calendarBodyHtml += "</td>";




         for (var i = 1; i <= options.daysToShow; i++) {
        	 //aggiungiamo la colonna relativa al giorno corrente, ed aggiungiamo anche un campo nascosto contenente la data 
        	 //corrente.
            calendarBodyHtml += "<td class=\"wc-day-column day-" + i + "\">" +
            "<div class=\"wc-day-column-inner\"></div></td>"
      
         }

         calendarBodyHtml += "</tr></tbody></table></div>";

         //append all calendar parts to container
         $(calendarHeaderHtml + calendarBodyHtml).appendTo($calendarContainer);
         
         //create add new room button (in progress...)
         /*$('<a class="wc-add" href="findAllRooms.action?sect=accomodation">ADD ROOM</a>').appendTo($calendarContainer);
         $(".wc-add").button({
             icons: {
                 secondary: "ui-icon-circle-plus"
             }});
          */
         $weekDayColumns = $calendarContainer.find(".wc-day-column-inner");
         $weekDayColumns.each(function(i, val) {
            $(this).height(options.timeslotHeight * options.timeslotsPerDay);
            if (!options.readonly) {
               self._addDroppableToWeekDay($(this));
				self._setupEventCreationForRoom($(this));
            }
         });

         $calendarContainer.find(".wc-time-slot").height(options.timeslotHeight - 1); //account for border

         $calendarContainer.find(".wc-time-header-cell").css({
            height :  (options.timeslotHeight * options.timeslotsPerHour) - 11
         });


      },
      
/*******************************************************************************************/    
/************************************ room checkin selection *******************************/      
/*******************************************************************************************/
      /*
       * Manage mouse actions into the planner/tableau.
       * @param {JqueryDOM} jquery object representing a planner column.
       */
      _setupEventCreationForRoom : function($weekDay) {
         var self = this;
         var options = this.options;
         // set a new array containing <td> column of booked days
         self.day_booked = new Array();       
         
         // Add mousedown event listener
         $weekDay.mousedown(function(event) {
            var $target = $(event.target);
            var number_slots=0;
				self.day_booked = new Array();		
				self._mouseClicked(true);
				
            if ($target.hasClass("wc-day-column-inner")) {
               // start to build a new div for the new booking
               var $newEvent = $("<div class=\"wc-cal-event wc-new-cal-event wc-new-cal-event-creating\"></div>");
			   // set css target
               $target.css({lineHeight: (options.timeslotHeight - 15) + "px", fontSize: (options.timeslotHeight / 2) + "px"});
               $(".wc-day-column-inner").css({cursor:"e-resize"});
               // append new div booking to the target
               $target.append($newEvent);
               
               	//add column header hover effect
            	  var classDay = $target.parent().attr("class");
            	  var day = classDay.split(/ day-/)[1];
            	  $(".wc-day-" + day, ".wc-header").addClass("hover-room-column");
             
               //offset() is a jquery function to get dom position parameters.
               var columnOffset = $target.offset().top;
               //Y è un intero che rappresenta il valore in pixel della coordinata Y del puntatore del mouse, relativamente all'intero 						documento
               var clickY = event.pageY - columnOffset;
               var clickYRounded = (clickY - (clickY % options.timeslotHeight)) / options.timeslotHeight;
               //ora sistemiamo la posizione del div dell'appuntamento arrotondato. In realtù topPosition è dato dal
               //numero intero di timeslots.
               var topPosition = clickYRounded * options.timeslotHeight;
               $newEvent.css({top: topPosition});
               number_slots=1;
               
               self._hoverRoomDivByPosition(self._checkRoomByTop(topPosition));  
                /****************************************************************/  
					/* ADESSO SETTIAMO PER LE SELEZIONI ORIZZONTALI MULTIPLE */

					//ora assegno l'evento mousemove. Quindi se oltre avere cliccato su una casella, con il mousedown,
					//muovo il mouse, vuol dire che voglio settare più appuntamenti di seguito e vado a vedere dove si trova
					//la cordinata corrente X del puntatore, per poi verificare quanti giorni ho selezionato.
               $target.parent().parent().bind("mousemove.newevent", function(event) {
            	   
               //ora che muovo il mouse come effetto mostro il div dell'evento.
                  $newEvent.show();
                  
                  //rendo il div anche redimensionabile.
                  $newEvent.addClass("ui-resizable-resizing");
                  /* ora effettuo alcune operazioni nel caso di selezione verticale */
                  //adesso calcolo la lunghezza corrente del tratto verticale selezionato.
                  var height = Math.round(event.pageY - columnOffset - topPosition);
                  //adesso calcolo la porzione di timeslots che avanzano nel tratto verticale selezionato.
                  var remainder = height % options.timeslotHeight;
                  
                  //snap to closest timeslot
                  //se la porzione di timeslot che rimane è minore della metà del tratto selezionato
                  if (remainder < (height / 2)) {
                  //ora calcolo la lunghezza intera del tratto selezionato. in cui ci stanno esattamente un numero intero di appuntamenti
                  
                     var useHeight = height - remainder;
                     $newEvent.css("height", useHeight < options.timeslotHeight ? options.timeslotHeight : useHeight);
                  } else {
                     $newEvent.css("height", height + (options.timeslotHeight - remainder));
                  }
                  
                  /******* ora effettuo una serie di operazioni per gestire lo spostamento a destra/sinistra del mousee ********/
				 //trovo la posizione del lato sinistro della casella in cui ho cliccato                                  
				var rowOffset = $target.offset().left;        
				//calcolo la distanza tra la posizione del click del mouse ed il lato sinistro della casella cliccata.
				var clickX = event.pageX - rowOffset; 
				//ora calcoliamo una lunghezza. Quante volte questa lunghezza è contenuta nella distanza 
				//dal lato sinistro alla posizione del mouse, mi dà il numero di caselle che aggiungo.
				var halfWidthEvent = Math.round(options.defaultEventWidth/2);
				//se lo spostamento con il mouse è almeno la metà della lunghezza di default di una casella, allora....
				
				     if (clickX > halfWidthEvent) {
					//trovo sempre il parent della casella cliccata.
				    	
					self.day_booked[0]=$target.parent();
				if( Math.floor(clickX/halfWidthEvent)>number_slots )
				{
					

				var $newEventHor2 = $("<div class=\"wc-cal-event wc-new-cal-event wc-new-cal-event-creating\"></div>");
					//adesso regoliamo il css
               //--$newEventHor2.css( "width", options.defaultEventWidth+"px");
   				$newEventHor2.css( "height",  options.timeslotHeight);
   				$newEventHor2.css({top: topPosition});
   				
                  
   				
               //adesso appendo il div creato dell'appuntamento.
              //-- var next= $target.siblings();
              //-- next= next.prevObject;
   				//--console.log (self.day_booked);
   				// -- console.log ("number_slots " + number_slots);
   				//ora assegno come elemento dell'array il td successivo a quello corrente.
            self.day_booked[number_slots]= self.day_booked[number_slots-1].next();
              //ora aggiungo il div evento al td che ho appena aggiunto.
               self.day_booked[number_slots].children().append($newEventHor2);
                //rendo il div anche redimensionabile.
                  $newEventHor2.addClass("ui-resizable-resizing");	
                 //-- $newEventHor2.addClass("ui-corner-all");
                  
                  
                  $(".wc-day-" + ++day, ".wc-header").addClass("hover-room-column");
                  $(".wc-day-column-inner").css({cursor:"e-resize"});
                  
                     $newEventHor2.show();								        					

                     number_slots= Math.floor(clickX/halfWidthEvent);	
                     
                    
					}// se invece il mouse lo stò spostando in una posizione all'indietro...
				else if (number_slots > 0 && Math.floor(clickX/halfWidthEvent)<number_slots)
					{
					$(".wc-day-column-inner").css({cursor:"w-resize"});
					//ora ciclo a partire dall'ultimo giorno selezionato sino al punto mouse in cui mi sono fermato
					var i;
					for(i=self.day_booked.length; i>Math.floor(clickX/halfWidthEvent); i--){
						/*var prova = self.day_booked[i].children().children(".wc-cal-event").find(".wc-time");*/
						try{
							self.day_booked[i - 1].children().children(".wc-cal-event:empty").remove();

			                  $(".wc-day-" + day--, ".wc-header").removeClass("hover-room-column");
			                 
						}
						catch(e)
						{
							var lunghezza = number_slots;
							var array = self.day_booked;
							var clicco = clickX;
							//--console.log("Problem at remove event moving the mouse back " + self.day_booked);
						}
					
					}
					self.day_booked = self.day_booked.slice(0, Math.floor(clickX/halfWidthEvent));
					//---number_slots= Math.floor(clickX/halfWidthEvent);
					number_slots= self.day_booked.length;
					//una volta che ho cancellato gli eventi tra l'ultimo giorno selezionato e quello in cui mi sono
					//fermato tornando indietro, allora ridimensiono l'array dei giorni bookati.

					}
					}
				     else {
				    	 //sono andato con il mouse prima del target sul quale ho cliccato e mi sono mosso, ovvero sono andato a sinistra tutto
				    	 
				    	 return;
				     }
				
				
               }).mouseup(function() {
               //ora che rilascio il pulsante del mouse tolgo l'ascolto all'evento del movimento del mouse.
                  $target.parent().parent().unbind("mousemove.newevent");
                  $newEvent.addClass("ui-corner-all");

                  self._mouseClicked(false);
                  
 $.each(self.day_booked, function(key, value) {

 value.children().children(".wc-cal-event").addClass("ui-corner-all");
 });

               });
            }
            
            //END CODE FOR NEW BOOKING MOUSEDOWN

         }).mouseup(function(event) {
        	 
        	 self._mouseClicked(false);
             var $target = $(event.target);
        	 var $weekDay;
        	 var $renderCalEvent;
        	 
             //se il target del click  ha la classe wc-cal-event, allora 
             if ($target.hasClass("wc-time") || $target.hasClass("wc-title")) {
             	
             //var id_book_room= $target.find('input[name="id_booked_room"]').val();	
             //options.eventClick({start: self.formatDate(new Date($target.parent().parent().data("startDate")), "M/d/Y"  ), end: self.formatDate(new Date($target.parent().parent().data("startDate")),"M/d/Y" ), id_booked:id_book_room}, $target, event);
           
             options.eventClick($target.parent().data("calEvent"), $target.parent(), event);

             	return;
              }
        	 
        	 
         //ora controllo il rilascio del pulsante del mouse nel caso in cui io ho cliccato ma non mosso il mouse.
        	 if($.isArray(self.day_booked)  && self.day_booked.length === 0)
        		 {
        		 
        		     
				//ora vado al parent dell'elemento cliccato
            $weekDay = $target.closest(".wc-day-column-inner");
            //ora che sono arrivato al parent, cerco il div dell'evento in via di creazione
            var $newEvent = $weekDay.find(".wc-new-cal-event-creating");
			
            if ($newEvent.length) {
               //if even created from a single click only, default height
               if (!$newEvent.hasClass("ui-resizable-resizing")) {
                 //---- $newEvent.css({height: options.timeslotHeight * options.defaultEventLength}).show();
                   $newEvent.css({width: 288}).show();
               }
               var top = parseInt($newEvent.css("top"));
               var eventDuration = self._getEventDurationFromPositionedEventElement($weekDay, $newEvent, top);

               $newEvent.remove();
               var newCalEvent = {readOnly: true, start: eventDuration.start, end: eventDuration.end, title: options.newEventText, top:eventDuration.topY};
              $renderedCalEvent = self._renderEvent(newCalEvent, $weekDay);

               if (!options.allowCalEventOverlap) {
                  self._adjustForEventCollisions($weekDay, $renderedCalEvent, newCalEvent, newCalEvent);
                  self._positionEvent($weekDay, $renderedCalEvent);
               } else {
                  self._adjustOverlappingEvents($weekDay);
               }

              //-- options.eventNew(eventDuration, $renderedCalEvent);
            } 
        		 
        		 }
 
            
/******************************************************************/
//ora scorriamo i vari div di day_booked e settiamo definitivamente
/******************************************************************/
$.each(self.day_booked, function(key,value){

				//ora vado al parent dell'elemento cliccato
            $weekDay = value.children();

            //ora che sono arrivato al parent, cerco il div dell'evento in via di creazione
            var $newEvent = $weekDay.find(".wc-new-cal-event-creating");
			
            if ($newEvent.length) {			
               //if even created from a single click only, default height
               if (!$newEvent.hasClass("ui-resizable-resizing")) {
                 //---- $newEvent.css({height: options.timeslotHeight * options.defaultEventLength}).show();
                   $newEvent.css({width: 288}).show();
               }
               var top = parseInt($newEvent.css("top"));
               var eventDuration = self._getEventDetails($weekDay, $newEvent, top);

               $newEvent.remove();
               var newCalEvent = {readOnly: true,start: eventDuration.start, end: eventDuration.end, title: options.newEventText, top:eventDuration.topY};
               $renderedCalEvent = self._renderEvent(newCalEvent, $weekDay);

               if (!options.allowCalEventOverlap) {
                  self._adjustForEventCollisions($weekDay, $renderedCalEvent, newCalEvent, newCalEvent);
                  self._positionEvent($weekDay, $renderedCalEvent);
               } else {
                  self._adjustOverlappingEvents($weekDay);
               }

              //--options.eventNew(eventDuration, $renderedCalEvent);
            }
            

            
});
/******************************************************************/
/******************************************************************/
//ORA APRO LA FINESTRA DI DIALOGO CON LA QUALE EFFETTUO IL BOOKING E SULLA QUALE PRENDERÒ IN INGRESSO
//1)LA DATA DI INIZIO DEL BOOKING, 2)LA DATA DI FINE DEL BOOKING, 3)L'ID DELLA CAMERA SULLA QUALE FACCIO IL BOOKING.
var start_booking=new Date();
var end_booking=new Date();
var id_book_room=0;
var ONE_DAY = 1000 * 60 * 60 * 24;
var day_booked_lenght = self.day_booked.length;
if($.isArray(self.day_booked) && day_booked_lenght>0)
{
try{
	start_booking= self.day_booked[0].children().data("startDate");
	//erase empty element. Occurring only for dragging at the end of calendar..
	self.day_booked = $.grep(self.day_booked,function(n,i){
	    return(n.length > 0);
	});

	if ( self.day_booked[self.day_booked.length-1].length >0)
		{
			end_booking = self.day_booked[self.day_booked.length-1].children().data("startDate");
		}
	else
		{
		  self.element.weekCalendar("nextWeek");
		//end_booking = new Date (start_booking.getTime() +  (self.day_booked.length -1) * ONE_DAY) ;
		
		}

	id_book_room= $renderedCalEvent.find('input[name="id_booked_room"]').val();
}
catch(e){
//gestisci eccezzioni
	
}
}

else {
	try{
	//siamo nel caso in cui non abbiamo fatto un mousemove perchè abbiamo cliccato su un solo div
		start_booking= $weekDay.data("startDate");
		end_booking = $weekDay.data("startDate");
	id_book_room= $renderedCalEvent.find('input[name="id_booked_room"]').val();
	}
	catch(e){
		//gestisci eccezzioni
			//--console.log ("problem at startDate settings...");
		}
	
}

/**********************************************
 * Ora settiamo una struttare dati, un oggetto che contiene:
 * start: --> la data di inizio del booking
 * end: ---> la data di fine del booking
 * id: ---> l'id della camera da bookare
 */

//prima controlliamo che non ci siano overlapping di bookings
 // The number of milliseconds in one day
 var ONE_DAY = 1000 * 60 * 60 * 24;
 //add a day for graphical purpose
 end_booking = end_booking.getTime() + ONE_DAY;
var dateInNewBook = $.datepicker.formatDate(I18NSettings.datePattern,new Date(start_booking));
var dateOutNewBook = $.datepicker.formatDate(I18NSettings.datePattern,new Date(end_booking));
/*var dateInNewBook = self.formatDate(new Date(start_booking) ,"dd/mm/yy" );
var dateOutNewBook = self.formatDate(new Date(end_booking) ,"dd/mm/yy" );*/
$.ajax({
	  type: 'POST',
	  url: "checkBookingDates.action",
	  data: {'booking.room.id':id_book_room,  'booking.dateIn': dateInNewBook , 'booking.dateOut': dateOutNewBook, 'booking.booker.id': "-1"},
	  success: function(data_action){
		   if (data_action.result == "success")
			   {	
		
			options.eventNew({start: dateInNewBook , end: dateOutNewBook, id_booked:id_book_room}, $renderedCalEvent); 
			    				    
			   }
	    
	     else if (data_action.result == "error")
	    	 {
	    	 	alert($.i18n("warning") + ' ' +  data_action.description);
	    	 	self.element.weekCalendar("removeUnsavedEvents");
	    	 }
	   	else{
	   		$(".validationErrors").html(data_action);
	   		}  
	  },
	  error: function() {
		  alert ($.i18n("bookingOverlapping"));
		  self.refresh();
	  },
	  dataType: 'json'
	});

       
//---options.eventNew({start: dateInNewBook , end: dateOutNewBook, id_booked:id_book_room}, $renderedCalEvent); 
         
            
            
            
            
         });
      },
      
      
      /*
       * get list of booking days from day_booked
       * 
       */     
      _getDaysByTd : function(){
    	  var startDay=0;
    	  var endDay=0;
    	  $.each(self.day_booked, function(key,value){
    	  value.find('input[name=="current_day"]').val();
    		  
    	  
    	  });
    	  return {start: startDay,end: endDay};
    	  },
      
      
      
      
      
      
      

      /*
       * load calendar events for the week based on the date provided
       */
      _loadCalEvents : function(dateWithinWeek) {

         var date, weekStartDate, endDate, $weekDayColumns;
         var self = this;
 	    // The number of milliseconds in one day
 	    var ONE_DAY = 1000 * 60 * 60 * 24;


         var options = this.options;
         date = dateWithinWeek || options.date;
         weekStartDate = self._dateFirstDayOfWeek(date);

         weekEndDate = self._dateLastMilliOfWeek(date);

         options.calendarBeforeLoad(self.element);

         self.element.data("startDate", weekStartDate);
         self.element.data("endDate", weekEndDate);

         $weekDayColumns = self.element.find(".wc-day-column-inner");

         self._updateDayColumnHeader($weekDayColumns);

         //load events by chosen means
         if (typeof options.data == 'string') {
            if (options.loading) options.loading(true);
            var jsonOptions = {};
            jsonOptions[options.startParam || 'start'] = Math.round(weekStartDate.getTime() / 1000);
            jsonOptions[options.endParam || 'end'] = Math.round(weekEndDate.getTime() / 1000);
            $.getJSON(options.data, jsonOptions, function(data) {
            	var list_bookings = new Array();
                var year = new Date().getFullYear();
                var month = new Date().getMonth();
                var day = new Date().getDate();
   		     //iterate over the list
   		     $(data).each(function(i, val)
   		    		 {
   		    	 //add current room to room list 

   		    	var date_in = $.weekCalendar.parseISO8601(val.dateIn,true);
   			    var date_end_ms = $.weekCalendar.parseISO8601(val.dateOut,true).getTime();		    
   			    var date_end_rendered_ms = date_end_ms - ONE_DAY;
   			    var date_end = new Date( date_end_rendered_ms);
   			    //check if room is valid
   			    if ( val.room ){
   			    	var title ='',
   			    		bookerPhone='',
   			    		bookerAddress='',
   			    		bookerEmail='';
   			    	if(val.booker && val.booker.lastName && val.booker.firstName ){
   			    		title = val.booker.lastName  + ' ' + val.booker.firstName;
   			    		bookerPhone = val.booker.phone;
   			    		bookerAddress= val.booker.address;
   			    		bookerEmail= val.booker.email;
   			    	}
      		    	 list_bookings.push( {
         	               "id":val.room.id,
         	               "start": date_in,
         	               "end": date_end ,
         	               "title":(title).replace(/<\/?[^>]+>/gi, ''),
         	               "bookId": val.id,
         	               "confirmed": val.status,
      	               "bookerPhone": bookerPhone,
      	               "bookerAddress": bookerAddress,
      	               "bookerEmail": bookerEmail
      		    	 		
         	            });
   			    	
   			    }

   		    	 		
   		    		 });
   		     
               self._renderEvents(list_bookings, $weekDayColumns);
               $.jGrowl.defaults.pool = 1;
               /*
               $(".wc-cal-event").hover(function(){
            	   var bookingData = $(this).data("calEvent");
            	   var growlid = null;
            	   if ( $('div.jGrowl-notification').length > 1 ){
            		   
            		   growlid = $('div.jGrowl-notification:parent').data("jGrowl").growlID;
            	   }
            	   if(growlid != bookingData.bookId ) {
            		  // $('div.jGrowl-notification').trigger('jGrowl.close');
            		   $('#jGrowl').jGrowl('shutdown').remove();
                	  // var htmlBooker = new EJS({url: 'js/views/booker/show.ejs'}).render({booker: bookingData, labels:{name: $.i18n("name"), phone: $.i18n("phone"), address: $.i18n("address")}});
                	  // $.jGrowl(htmlBooker, {growlID: bookingData.bookId, sticky: true, header: $.i18n("bookerData"), position: 'top-right', life: 1000 });
                	               		   
            	   }
            	   
            	}, function(){});
               */
               
               
               $(".wc-day-column").hover(function(e){
            	   	
            	if(!self.clicked){
        			//offset() è una funzione di jquery che dà la posizione di un elemento.
                  /*  var columnOffset = $(this).offset().top;
                    //Y è un intero che rappresenta il valore in pixel della coordinata Y del puntatore del mouse, relativamente all'intero 						documento
                    var clickY = e.pageY - columnOffset;
                    var clickYRounded = (clickY - (clickY % options.timeslotHeight)) / options.timeslotHeight;
                    //ora sistemiamo la posizione del div dell'appuntamento arrotondato. In realtù topPosition è dato dal
                    //numero intero di timeslots.
                    var topPosition = clickYRounded * options.timeslotHeight;
                    
                   self._hoverRoomDivByPosition(self._checkRoomByTop(topPosition));  
            		*/
             		   var classDay = $(this).attr("class");
                 	  var day = classDay.split(/ day-/)[1];
                 	  $(".wc-day-" + day, ".wc-header").addClass("hover-room-column");
           		}             	  
            	}, function(e){
            		if(!self.clicked){
            		//	self._removeAllHoverRoomDiv(self._checkRoomByTop()); 
            		   var classDay = $(this).attr("class");
                    	  var day = classDay.split(/ day-/)[1];
                    	  $(".wc-day-" + day, ".wc-header").removeClass("hover-room-column");	
            		}    		
            	});
               
               
               if (options.loading) options.loading(false);
            });
         }
         else if ($.isFunction(options.data)) {
            options.data(weekStartDate, weekEndDate,
                  function(data) {
                     self._renderEvents(data, $weekDayColumns);
                  });
         }
         else if (options.data) {
               self._renderEvents(options.data, $weekDayColumns);
            }

         self._disableTextSelect($weekDayColumns);


      },
      _mouseClicked: function (clicked){
    	  (clicked)? this.clicked = true : this.clicked = false;
      },
      

      /*
       * update the display of each day column header based on the calendar week
       */
      _updateDayColumnHeader : function ($weekDayColumns) {
         var self = this;
         var options = this.options;
         var currentDay = self._cloneDate(self.element.data("startDate"));

         self.element.find(".wc-header td.wc-day-column-header").each(function(i, val) {

            var dayName = options.useShortDayNames ? options.shortDays[currentDay.getDay()] : options.longDays[currentDay.getDay()];
            //ora aggiungiamo anche un campo nascosto che contiene il valore della data corrente:
            var current_day_number=i+1;
            $(this).html(dayName + "<br/>" + self._formatDate(currentDay, options.dateFormat)+
             '<input type="hidden" name="day-'+ current_day_number + '" value="' + self._formatDate(currentDay, options.dateFormat) + '" />'		
            );
            
           
            if (self._isToday(currentDay)) {
               $(this).addClass("wc-today");
            } else {
               $(this).removeClass("wc-today");
            }
            currentDay = self._addDays(currentDay, 1);

         });

         currentDay = self._dateFirstDayOfWeek(self._cloneDate(self.element.data("startDate")));

         $weekDayColumns.each(function(i, val) {

            $(this).data("startDate", self._cloneDate(currentDay));
            $(this).data("endDate", new Date(currentDay.getTime() + (MILLIS_IN_DAY)));
            if (self._isToday(currentDay)) {
               $(this).parent().addClass("wc-today");
            } else {
               $(this).parent().removeClass("wc-today");
            }

            currentDay = self._addDays(currentDay, 1);
         });

      },

      /*
       * Render the events into the calendar
       */
      _renderEvents : function (data, $weekDayColumns) {

         this._clearCalendar();

         var self = this;
         var options = this.options;
         var eventsToRender;
			/********************************************/
			/**** ORA SETTO eventsToRender  che contiene gli eventi ripuliti ***********/
         if ($.isArray(data)) {
            eventsToRender = self._cleanEvents(data);
         } else if (data.events) {
            eventsToRender = self._cleanEvents(data.events);
         }
          
         if (data.options) {

            var updateLayout = false;
            //update options
            $.each(data.options, function(key, value) {
               if (value !== options[key]) {
                  options[key] = value;
                  updateLayout = true;
               }
            });

            self._computeOptions();
				// se effettivamente le opzioni hanno dei valori nuovi, allora aggiorniamo...
            if (updateLayout) {
               self.element.empty();
               self._renderCalendar();
               $weekDayColumns = self.element.find(".wc-time-slots .wc-day-column-inner");
               self._updateDayColumnHeader($weekDayColumns);
               self._resizeCalendar();
            }

         }


         $.each(eventsToRender, function(i, calEvent) {
        	//se data end è maggiore di data start, allora il booking coinvolge più
        	 //giorni, per cui bisogna iterare.
     	    // The number of milliseconds in one day
     	     var ONE_DAY = 1000 * 60 * 60 * 24;
        	 var number_of_days = self._days_between(calEvent.end,  calEvent.start) + 1;
        	 begin_day = calEvent.start;
        	 if(number_of_days > 200)
        		 {
        		 	alert ("Warning!! Number of booking days too high for booking " + calEvent.bookId);
        		 	return;
        		 }
        	 for (i = 0 ;  i < number_of_days; i++)
        		 {
        		 	
        		 calEvent.start = new Date(begin_day.getTime() + i* ONE_DAY );
        		 calEvent.end = new Date(calEvent.start.getTime() +1);
        		 	
            	 var $weekDay = self._findWeekDayForEvent(calEvent, $weekDayColumns);

                 if ($weekDay) {
                    calEvent.top= self._getRoomTopById(calEvent.id);
                    self._renderEvent(calEvent, $weekDay);
                 }
        		 }

         });

         $weekDayColumns.each(function(){
            self._adjustOverlappingEvents($(this));
         });

         options.calendarAfterLoad(self.element);

         if (!eventsToRender.length) {
            options.noEvents();
         }

      },
      
      _days_between: function(date1, date2) {

  	    // The number of milliseconds in one day
  	    var ONE_DAY = 1000 * 60 * 60 * 24;

  	    // Convert both dates to milliseconds
  	    var date1_ms = date1.getTime();
  	    var date2_ms = date2.getTime();

  	    // Calculate the difference in milliseconds
  	    var difference_ms = Math.abs(date1_ms - date2_ms);
  	    
  	    // Convert back to days and return
  	    return Math.round(difference_ms/ONE_DAY);

  	},

      /*
       * Render a specific event into the day provided. Assumes correct
       * day for calEvent date
       */
      _renderEvent: function (calEvent, $weekDay) {
         var self = this;
         var options = this.options;
 
         var eventClass, eventHtml, $calEvent, $modifiedEvent;

         eventClass = calEvent.id ? "wc-cal-event" : "wc-cal-event wc-new-cal-event";
         eventHtml = "<div class=\"" + eventClass + " ui-corner-all\">\
                <div class=\"wc-time ui-corner-all\"></div>\
                <div class=\"wc-title\"></div></div>";
			//costruiamo un oggetto jquery che rappresenta un dom da un oggetto javascript DOM o da una stringa che rappresenta un tag dom
         $calEvent = $(eventHtml);
         /***************************************************/
         /***************************************************/
         //ADESSO RICHIAMIAMO UNA FUNZIONE CHE EFFETTUA AZIONI SULL'ASPETTO DEL DIV DELL'EVENTO
         $modifiedEvent = options.eventRender(calEvent, $calEvent);
         $calEvent = $modifiedEvent ? $modifiedEvent.appendTo($weekDay) : $calEvent.appendTo($weekDay);
         $calEvent.css({lineHeight: (options.timeslotHeight - 15) + "px", fontSize: (options.timeslotHeight / 2) + "px"});
         // ADESSO CON IL METODO EVENTRENDER SETTIAMO L'EVENTO, IN PARTICOLARE AGGIUNGIAMO DEL TESTO DENTRO I DIVs..
         //INOLTRE INSERIAMO IL CALEVENT DENTRO L'HTML CON DATA
         self._refreshEventBooked(calEvent, $calEvent);
         self._positionEvent($weekDay, $calEvent);
         $calEvent.show();
         
         if (!options.readonly && options.resizable(calEvent, $calEvent)) {
         //---   self._addResizableToCalEvent(calEvent, $calEvent, $weekDay)
         }
         if (!options.readonly && options.draggable(calEvent, $calEvent)) {
         //---   self._addDraggableToCalEvent(calEvent, $calEvent);
         }

         
         
         
         options.eventAfterRender(calEvent, $calEvent);

         return $calEvent;

      },
      
       /*
       * Refresh the displayed details of a booked day for a room
       */
      _refreshEventBooked : function(calEvent, $calEvent) {
         var self = this;
         var options = this.options;

           $calEvent.find(".wc-time").html("booked");
           //ora calcolo l'id della camera da bookare considerando l'altezza della casella in cui ho bookato
           //e facendo riferimento alla prima colonna del planner, dove è contenuto l'id di ogni camera.
           var position= (calEvent.top/options.timeslotHeight);
           //add room id and booking id into DOM
          var id_room= $('div.wc-time-header-cell#' + position + '  > input[name="id_room"]').val();
          var id_booking = (typeof calEvent.bookId === 'undefined' ) ? -1 : calEvent.bookId;
          
          
	     if((typeof calEvent.confirmed !== 'undefined' )) {
	    	 
	    	 if(calEvent.confirmed== "provisional") {
	    		 $calEvent.find(".wc-time").css({"background-color": "#FE9A2E"});
	    		 $calEvent.find(".wc-time").html("provisional");
	    	 
	    	 	}
	    	 else if(calEvent.confirmed== "online") {
	    		 $calEvent.find(".wc-time").css({"background-color": "#D692A3"});
	    		 $calEvent.find(".wc-time").html("online");
	    	 
	    	 	}
	    	 
	     }
	        	 
           $calEvent.find(".wc-time").append('<input type="hidden" name="id_booked_room"  value="' +  id_room  + '" />'+
        		   '<input type="hidden" name="id_booking"  value="' +  id_booking  + '" />');
          
           //possiamo aggiungere altre informazioni, quali ad esempio l'id del booking
           
         $calEvent.find(".wc-title").html(calEvent.title);
         //con la seguente istruzione inseriamo in data l'evento del calendario. (potrebbe essere ridondante visto che stiamo aggiungendo input hidden)
         $calEvent.data("calEvent", calEvent);
         if(!self._checkRoomByTop(calEvent.top))
         alert('problem div top value');
      },
      
 /* get room ID by top */     
      _checkRoomByTop : function(top) {
         var self = this;
         var options = this.options;
      var room_divs= $(".wc-hour-header");
           //-- alert (top/options.timeslotHeight);
           var return_value = false;
      $.each(room_divs, function(i, val){
   //--current_top= val.position().top;
      event_position=top/options.timeslotHeight;
      var value= $(val).children(".wc-time-header-cell").attr("id");
      if (value == event_position)
      return_value=value;
      });
      
      return return_value;
      },
      
      
      
 /* get room ID by top */     
      _getRoomTopById : function(id) {
         var self = this;
         var options = this.options;
      var room_divs= $(".wc-hour-header");
           //-- alert (top/options.timeslotHeight);
           var return_value = 0;
           var position=0;
      $.each(room_divs, function(i, val){
   //--current_top= val.position().top;
   
    var value= $(val).children('div.wc-time-header-cell').children('input[name="id_room"]').val();

    //var value= $(val).children(".wc-time-header-cell").attr("id");
      if (value == id)
      {
      return_value=position*options.timeslotHeight;
      return return_value;
      }
  	position++;
      });      
      
      
      
      return return_value;
      },      
      
    _hoverRoomDivByPosition : function(position){	   
    	  $(".wc-hour-header > #" + position).addClass("hover-room-column");
      },
     _removeAllHoverRoomDiv : function(){	   
    	  $(".hover-room-column").removeClass("hover-room-column");
      },    
      
      _adjustOverlappingEvents : function($weekDay) {
         var self = this;
         if (self.options.allowCalEventOverlap) {
            var groupsList = self._groupOverlappingEventElements($weekDay);
            $.each(groupsList, function() {
               var curGroups = this;
               $.each(curGroups, function(groupIndex) {
                  var curGroup = this;

                  // do we want events to be displayed as overlapping
                  if (self.options.overlapEventsSeparate) {
                     var newWidth = 100 / curGroups.length;
                     var newLeft = groupIndex * newWidth;
                  } else {
                     // TODO what happens when the group has more than 10 elements
                     var newWidth = 100 - ( (curGroups.length - 1) * 10 );
                     var newLeft = groupIndex * 10;
                  }
                  $.each(curGroup, function() {
                     // bring mouseovered event to the front
                     if (!self.options.overlapEventsSeparate) {
                        $(this).bind("mouseover.z-index", function() {
                           var $elem = $(this);
                           $.each(curGroup, function() {
                              $(this).css({"z-index":  "1"});
                           });
                           $elem.css({"z-index": "3"});
                        });
                     }
                     $(this).css({width: newWidth + "%", left:newLeft + "%", right: 0});
                  });
               });
            });
         }
      },


      /*
       * Find groups of overlapping events
       */
      _groupOverlappingEventElements : function($weekDay) {
         var $events = $weekDay.find(".wc-cal-event:visible");
         var sortedEvents = $events.sort(function(a, b) {
            return $(a).data("calEvent").start.getTime() - $(b).data("calEvent").start.getTime();
         });

         var lastEndTime = new Date(0, 0, 0);
         var groups = [];
         var curGroups = [];
         var $curEvent;
         $.each(sortedEvents, function() {
            $curEvent = $(this);
            //checks, if the current group list is not empty, if the overlapping is finished
            if (curGroups.length > 0) {
               if (lastEndTime.getTime() <= $curEvent.data("calEvent").start.getTime()) {
                  //finishes the current group list by adding it to the resulting list of groups and cleans it

                  groups.push(curGroups);
                  curGroups = [];
               }
            }

            //finds the first group to fill with the event
            for (var groupIndex = 0; groupIndex < curGroups.length; groupIndex++) {
               if (curGroups[groupIndex].length > 0) {
                  //checks if the event starts after the end of the last event of the group
                  if (curGroups[groupIndex][curGroups [groupIndex].length - 1].data("calEvent").end.getTime() <= $curEvent.data("calEvent").start.getTime()) {
                     curGroups[groupIndex].push($curEvent);
                     if (lastEndTime.getTime() < $curEvent.data("calEvent").end.getTime()) {
                        lastEndTime = $curEvent.data("calEvent").end;
                     }
                     return;
                  }
               }
            }
            //if not found, creates a new group
            curGroups.push([$curEvent]);
            if (lastEndTime.getTime() < $curEvent.data("calEvent").end.getTime()) {
               lastEndTime = $curEvent.data("calEvent").end;
            }
         });
         //adds the last groups in result
         if (curGroups.length > 0) {
            groups.push(curGroups);
         }
         return groups;
      },


      /*
       * find the weekday in the current calendar that the calEvent falls within
       */
      _findWeekDayForEvent : function(calEvent, $weekDayColumns) {

         var $weekDay;

         $weekDayColumns.each(function() {
             var uu=calEvent.start.getTime();
             var ff= $(this).data("startDate").getTime();
             var ii=$(this).data("endDate").getTime() ;
             var gg=calEvent.end.getTime();
            //se mi trovo all'interno del range della data corrente del weekday corrente, allora restituisci il dom della colonna della data dell'iterazione corrente... 
            if ($(this).data("startDate").getTime() <= calEvent.start.getTime() && $(this).data("endDate").getTime() >= calEvent.end.getTime()) {
               $weekDay = $(this);
               return false;
            }
         });
         return $weekDay;
      },

      /*
       * update the events rendering in the calendar. Add if does not yet exist.
       */
      _updateEventInCalendar : function (calEvent) {
         var self = this;
         var options = this.options;
         self._cleanEvent(calEvent);

         if (calEvent.id) {
            self.element.find(".wc-cal-event").each(function() {
               if ($(this).data("calEvent").id === calEvent.id || $(this).hasClass("wc-new-cal-event")) {
                  $(this).remove();
                  return false;
               }
            });
         }

         var $weekDay = self._findWeekDayForEvent(calEvent, self.element.find(".wc-time-slots .wc-day-column-inner"));
         if ($weekDay) {
            var $calEvent = self._renderEvent(calEvent, $weekDay);
            self._adjustForEventCollisions($weekDay, $calEvent, calEvent, calEvent);
            self._refreshEventDetails(calEvent, $calEvent);
            self._positionEvent($weekDay, $calEvent);
            self._adjustOverlappingEvents($weekDay);
         }
      },
      /*
       * renderizza i booking effettuati dopo che si chiude la finestra di dialogo con il save
       */
      _updateBookInCalendar : function (calEvent) {
          var self = this;
          var options = this.options;
          self._cleanEvent(calEvent);


          	//ora individuiamo i giorni che sono presenti nell'intervallo start ed end del booking
          var intervalDays = self._getWeekDaysForInterval(calEvent, self.element.find(".wc-time-slots .wc-day-column-inner"));
          //ora, per ogni giorno, controllo che i giorni presenti nell'intervallo di giorni
          //che coinvolgono il booking e li confronto con i div wc-new-cal-event segnalati 
          //nel planner, per vedere se ci sono inconsistenze.
/*          self.element.find(".wc-new-cal-event").each(function() {
              $(this).remove();
           });*/
          
          var booked_slots= $(intervalDays).find(".wc-cal-event").filter(function(){
        	 //--var valore=$(this).find('input[name="id_booked_room"]').val();
        	  return $(this).find('input[name="id_booked_room"]').val() == calEvent.id;
        	  });
          
          
          if ($.isArray(intervalDays) && intervalDays.length===self.element.find(".wc-new-cal-event").size()) {
        	  $.each(booked_slots, function(key, day_column){
        		 //--- var pippo = $(day_column).find('input[name="id_booked_room"]');
        	//---var $calEvent = self._renderEvent(calEvent, day_column);
            //-- self._adjustForEventCollisions($day, $calEvent, calEvent, calEvent);
            //------- self._refreshEventDetails(calEvent, $calEvent);
            //--- self._positionEvent($day, $calEvent);
           //---  self._adjustOverlappingEvents($day);  
        	         $(day_column).find(".wc-title").html(calEvent.title);
        	         $(day_column).data("calEvent", calEvent);  
        	         $(day_column).removeClass("wc-new-cal-event");
        	         //ora controlliamo che il booking sia stato confermato o meno
        	         if(calEvent.confirm != 'undefined' && calEvent.confirm==0)
        	        	 $(day_column).find(".wc-time").css({"background-color": "#FE9A2E"});
        	        	 
        		  
        	  });

          }
       },
       
       /*
        * find the weekday in the current calendar that the calEvent falls within
        */
       _getWeekDaysForInterval : function(calEvent, $weekDayColumns) {

          var $weekDays=new Array();

          $weekDayColumns.each(function() {
              var ff= $(this).data("startDate");
              var uu=calEvent.start;
            
              var gg=calEvent.end;
        	  	//ora controlliamo che la data ad ogni colpo di ciclo sia interna all'intervallo delle date del booking
             if ($(this).data("startDate").getTime() >= calEvent.start.getTime() && $(this).data("startDate").getTime() <= calEvent.end.getTime()) {
                $weekDays.push(this);
               
             }
          });
          return $weekDays;
       },
       

      /*
       * Position the event element within the weekday based on it's start / end dates.
       */
      _positionEvent : function($weekDay, $calEvent) {
         var options = this.options;
         var calEvent = $calEvent.data("calEvent");
         var pxPerMillis = $weekDay.height() / options.millisToDisplay;
         var firstHourDisplayed = options.businessHours.limitDisplay ? options.businessHours.start : 0;
    var startMillis = calEvent.start.getTime() - new Date(calEvent.start.getFullYear(), calEvent.start.getMonth(), calEvent.start.getDate(), firstHourDisplayed).getTime();
         
  /*     
    var startMillis = $weekDay.data("startDate").getTime() + startOffsetMillis + Math.round(top / options.timeslotHeight) * options.millisPerTimeslot;
         */
      
         
         var eventMillis = calEvent.end.getTime() - calEvent.start.getTime();
      //--   var pxTop = pxPerMillis * startMillis;
var pxTop = calEvent.top;
       //--  alert('pxpermillis: ' + pxPerMillis + ' startMillis: '+ startMillis + 'calEvent.end.getTime() ' + calEvent.end.getTime() + ' $weekDay.height(): ' +$weekDay.height() );
         var pxHeight = options.timeslotHeight;
         $calEvent.css({top: pxTop, height: pxHeight});
      },

      /*
       * Determine the actual start and end times of a calevent based on it's
       * relative position within the weekday column and the starting hour of the
       * displayed calendar.
       */
      _getEventDurationFromPositionedEventElement : function($weekDay, $calEvent, top) {
         var options = this.options;
         var startOffsetMillis = options.businessHours.limitDisplay ? options.businessHours.start * 60 * 60 * 1000 : 0;
         var start = new Date($weekDay.data("startDate").getTime() + startOffsetMillis + Math.round(top / options.timeslotHeight) * options.millisPerTimeslot);
         var end = new Date(start.getTime() + ($calEvent.height() / options.timeslotHeight) * options.millisPerTimeslot);
// SETTIAMO LA POSIZIONE DEL DIV
         
         
         return {start: start, end: end, topY:top };
      },
      
      _getEventDetails : function($weekDay, $calEvent, top) {
    	  var start = new Date($weekDay.data("startDate"));
          var end = new Date(start.getTime());
                 
          return {start: start, end: end, topY:top };
       },

      /*
       * If the calendar does not allow event overlap, adjust the start or end date if necessary to
       * avoid overlapping of events. Typically, shortens the resized / dropped event to it's max possible
       * duration  based on the overlap. If no satisfactory adjustment can be made, the event is reverted to
       * it's original location.
       */
      _adjustForEventCollisions : function($weekDay, $calEvent, newCalEvent, oldCalEvent, maintainEventDuration) {
         var options = this.options;

         if (options.allowCalEventOverlap) {
            return;
         }
         var adjustedStart, adjustedEnd;
         var self = this;

         $weekDay.find(".wc-cal-event").not($calEvent).each(function() {
            var currentCalEvent = $(this).data("calEvent");

            //has been dropped onto existing event overlapping the end time
            if (newCalEvent.start.getTime() < currentCalEvent.end.getTime()
                  && newCalEvent.end.getTime() >= currentCalEvent.end.getTime()) {

               adjustedStart = currentCalEvent.end;
            }


            //has been dropped onto existing event overlapping the start time
            if (newCalEvent.end.getTime() > currentCalEvent.start.getTime()
                  && newCalEvent.start.getTime() <= currentCalEvent.start.getTime()) {

               adjustedEnd = currentCalEvent.start;
            }
            //has been dropped inside existing event with same or larger duration
            if (oldCalEvent.resizable == false || (newCalEvent.end.getTime() <= currentCalEvent.end.getTime()
                  && newCalEvent.start.getTime() >= currentCalEvent.start.getTime())) {

               adjustedStart = oldCalEvent.start;
               adjustedEnd = oldCalEvent.end;
               return false;
            }

         });


         newCalEvent.start = adjustedStart || newCalEvent.start;

         if (adjustedStart && maintainEventDuration) {
            newCalEvent.end = new Date(adjustedStart.getTime() + (oldCalEvent.end.getTime() - oldCalEvent.start.getTime()));
            self._adjustForEventCollisions($weekDay, $calEvent, newCalEvent, oldCalEvent);
         } else {
            newCalEvent.end = adjustedEnd || newCalEvent.end;
         }


         //reset if new cal event has been forced to zero size
         if (newCalEvent.start.getTime() >= newCalEvent.end.getTime()) {
            newCalEvent.start = oldCalEvent.start;
            newCalEvent.end = oldCalEvent.end;
         }

         $calEvent.data("calEvent", newCalEvent);
      },
      
  /**********************************************************************/
  /*************    GESTISCI LE COLLISIONI DI BOOKING *****************/
  /**********************************************************************/
      
      _adjustForBookingCollisions : function($weekDay, $calEvent, newCalEvent, oldCalEvent, maintainEventDuration) {
         var options = this.options;

         if (options.allowCalEventOverlap) {
            return;
         }
         var adjustedStart, adjustedEnd;
         var self = this;

         $weekDay.find(".wc-cal-event").not($calEvent).each(function() {
            var currentCalEvent = $(this).data("calEvent");

            //has been dropped onto existing event overlapping the end time
            if (newCalEvent.start.getTime() < currentCalEvent.end.getTime()
                  && newCalEvent.end.getTime() >= currentCalEvent.end.getTime()) {

               adjustedStart = currentCalEvent.end;
            }


            //has been dropped onto existing event overlapping the start time
            if (newCalEvent.end.getTime() > currentCalEvent.start.getTime()
                  && newCalEvent.start.getTime() <= currentCalEvent.start.getTime()) {

               adjustedEnd = currentCalEvent.start;
            }
            //has been dropped inside existing event with same or larger duration
            if (oldCalEvent.resizable == false || (newCalEvent.end.getTime() <= currentCalEvent.end.getTime()
                  && newCalEvent.start.getTime() >= currentCalEvent.start.getTime())) {

               adjustedStart = oldCalEvent.start;
               adjustedEnd = oldCalEvent.end;
               return false;
            }

         });


         newCalEvent.start = adjustedStart || newCalEvent.start;

         if (adjustedStart && maintainEventDuration) {
            newCalEvent.end = new Date(adjustedStart.getTime() + (oldCalEvent.end.getTime() - oldCalEvent.start.getTime()));
            self._adjustForEventCollisions($weekDay, $calEvent, newCalEvent, oldCalEvent);
         } else {
            newCalEvent.end = adjustedEnd || newCalEvent.end;
         }


         //reset if new cal event has been forced to zero size
         if (newCalEvent.start.getTime() >= newCalEvent.end.getTime()) {
            newCalEvent.start = oldCalEvent.start;
            newCalEvent.end = oldCalEvent.end;
         }

         $calEvent.data("calEvent", newCalEvent);
      },
      
      
      /*
       * Add draggable capabilities to an event
       */
      _addDraggableToCalEvent : function(calEvent, $calEvent) {
         var self = this;
         var options = this.options;
         var $weekDay = self._findWeekDayForEvent(calEvent, self.element.find(".wc-time-slots .wc-day-column-inner"));
         $calEvent.draggable({
            handle : ".wc-time",
            containment: ".wc-scrollable-grid",
            revert: 'valid',
            opacity: 0.5,
            grid : [$calEvent.outerWidth() + 1, options.timeslotHeight ],
            start : function(event, ui) {
               var $calEvent = ui.draggable;
               options.eventDrag(calEvent, $calEvent);
            }
         });

      },

      /*
       * Add droppable capabilites to weekdays to allow dropping of calEvents only
       */
      _addDroppableToWeekDay : function($weekDay) {
         var self = this;
         var options = this.options;
         $weekDay.droppable({
            accept: ".wc-cal-event",
            drop: function(event, ui) {
               var $calEvent = ui.draggable;
               var top = Math.round(parseInt(ui.position.top));
               var eventDuration = self._getEventDurationFromPositionedEventElement($weekDay, $calEvent, top);
               var calEvent = $calEvent.data("calEvent");

                

               var newCalEvent = $.extend(true, {}, calEvent, {start: eventDuration.start, end: eventDuration.end});
               self._adjustForEventCollisions($weekDay, $calEvent, newCalEvent, calEvent, true);
               var $weekDayColumns = self.element.find(".wc-day-column-inner");

                //trigger drop callback
               options.eventDrop(newCalEvent, calEvent, $newEvent);

               var $newEvent = self._renderEvent(newCalEvent, self._findWeekDayForEvent(newCalEvent, $weekDayColumns));
               $calEvent.hide();
              
               $calEvent.data("preventClick", true);

               var $weekDayOld = self._findWeekDayForEvent($calEvent.data("calEvent"), self.element.find(".wc-time-slots .wc-day-column-inner"));

               if ($weekDayOld.data("startDate") != $weekDay.data("startDate")) {
                  self._adjustOverlappingEvents($weekDayOld);
               }
               self._adjustOverlappingEvents($weekDay);

               setTimeout(function() {
                  $calEvent.remove();
               }, 1000);

            }
         });
      },

      /*
       * Add resizable capabilities to a calEvent
       */
      _addResizableToCalEvent : function(calEvent, $calEvent, $weekDay) {
         var self = this;
         var options = this.options;
         $calEvent.resizable({
            grid: options.timeslotHeight,
            containment : $weekDay,
            handles: "s",
            minHeight: options.timeslotHeight,
            stop :function(event, ui) {
               var $calEvent = ui.element;
               var newEnd = new Date($calEvent.data("calEvent").start.getTime() + ($calEvent.height() / options.timeslotHeight) * options.millisPerTimeslot);
               var newCalEvent = $.extend(true, {}, calEvent, {start: calEvent.start, end: newEnd});
               self._adjustForEventCollisions($weekDay, $calEvent, newCalEvent, calEvent);

               self._refreshEventDetails(newCalEvent, $calEvent);
               self._positionEvent($weekDay, $calEvent);
               self._adjustOverlappingEvents($weekDay);
               //trigger resize callback
               options.eventResize(newCalEvent, calEvent, $calEvent);
               $calEvent.data("preventClick", true);
               setTimeout(function() {
                  $calEvent.removeData("preventClick");
               }, 500);
            }
         });
      },

      /*
       * Refresh the displayed details of a calEvent in the calendar
       */
      _refreshEventDetails : function(calEvent, $calEvent) {
         var self = this;
         var options = this.options;
         var one_hour = 3600000;
         var displayTitleWithTime = calEvent.end.getTime()-calEvent.start.getTime() <= (one_hour/options.timeslotsPerHour);
         if (displayTitleWithTime){
           $calEvent.find(".wc-time").html(self._formatDate(calEvent.start, options.timeFormat) + ": " + calEvent.title);
         }
         else {
           $calEvent.find(".wc-time").html(self._formatDate(calEvent.start, options.timeFormat) + options.timeSeparator + self._formatDate(calEvent.end, options.timeFormat));
         }
         $calEvent.find(".wc-title").html(calEvent.title);
         $calEvent.data("calEvent", calEvent);
      },

      /*
       * Clear all cal events from the calendar
       */
      _clearCalendar : function() {
        this.element.find(".wc-day-column-inner div").remove();
      },

      /*
       * Scroll the calendar to a specific hour
       */
      _scrollToHour : function(hour) {
         var self = this;
         var options = this.options;
         var $scrollable = this.element.find(".wc-scrollable-grid");
         var slot = hour;
         if (self.options.businessHours.limitDisplay) {
            if (hour <= self.options.businessHours.start) {
               slot = 0;
            } else if (hour > self.options.businessHours.end) {
               slot = self.options.businessHours.end -
               self.options.businessHours.start - 1;
            } else {
               slot = hour - self.options.businessHours.start;
            }
            
         }

         var $target = this.element.find(".wc-grid-timeslot-header .wc-hour-header:eq(" + slot + ")");

         $scrollable.animate({scrollTop: 0}, 0, function() {
            var targetOffset = $target.offset().top;
            var scroll = targetOffset - $scrollable.offset().top - $target.outerHeight();
            $scrollable.animate({scrollTop: scroll}, options.scrollToHourMillis);
         });
      },

      /*
       * find the hour (12 hour day) for a given hour index
       */
      _hourForIndex : function(index) {
         if (index === 0) { //midnight
            return 12;
         } else if (index < 13) { //am
            return index;
         } else { //pm
            return index - 12;
         }
      },

      _24HourForIndex : function(index) {
    	  
    	  var options = this.options;
    	  //check if is set the name of room
    	  if(typeof  options.listRooms !=='undefined' && options.listRooms[index].name !=='')
          {
    		  return options.listRooms[index].name;
          }
    		  return  'camera' + index ;

      },

      _amOrPm : function (hourOfDay) {
         return hourOfDay < 12 ? "AM" : "PM";
      },

      _isToday : function(date) {
         var clonedDate = this._cloneDate(date);
         this._clearTime(clonedDate);
         var today = new Date();
         this._clearTime(today);
         return today.getTime() === clonedDate.getTime();
      },

      /*
       * Clean events to ensure correct format
       */
      _cleanEvents : function(events) {
         var self = this;
         $.each(events, function(i, event) {
            self._cleanEvent(event);
         });
         return events;
      },

      /*
       * Clean specific event
       */
      _cleanEvent : function (event) {
         if (event.date) {
            event.start = event.date;
         }
         event.start = this._cleanDate(event.start);
         event.end = this._cleanDate(event.end);
         if (!event.end) {
            event.end = this._addDays(this._cloneDate(event.start), 1);
         }
      },

      /*
       * Disable text selection of the elements in different browsers
       */
      _disableTextSelect : function($elements) {
         $elements.each(function() {
            if ($.browser.mozilla) {//Firefox
               $(this).css('MozUserSelect', 'none');
            } else if ($.browser.msie) {//IE
               $(this).bind('selectstart', function() {
                  return false;
               });
            } else {//Opera, etc.
               $(this).mousedown(function() {
                  return false;
               });
            }
         });
      },

      /*
       * returns the date on the first millisecond of the week
       */

      _dateFirstDayOfWeek : function(date) {
         var self = this;
         var midnightCurrentDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
         var adjustedDate = new Date(midnightCurrentDate);
         adjustedDate.setDate(adjustedDate.getDate() - self._getAdjustedDayIndex(midnightCurrentDate));

         return adjustedDate;

      },

       /*
       * returns the date on the first millisecond of the last day of the week
       */
       _dateLastDayOfWeek : function(date) {


         var self = this;
         var midnightCurrentDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
         var adjustedDate = new Date(midnightCurrentDate);
         adjustedDate.setDate(adjustedDate.getDate() + (6 - this._getAdjustedDayIndex(midnightCurrentDate)));

         return adjustedDate;
          
       },

      /*
       * gets the index of the current day adjusted based on options
       */
      _getAdjustedDayIndex : function(date) {

         var midnightCurrentDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
         var currentDayOfStandardWeek = midnightCurrentDate.getDay();
         var days = [0,1,2,3,4,5,6];
         this._rotate(days, this.options.firstDayOfWeek);
         return days[currentDayOfStandardWeek];
      },

      /*
       * returns the date on the last millisecond of the week
       */
      _dateLastMilliOfWeek : function(date) {
         var lastDayOfWeek = this._dateLastDayOfWeek(date);
         return new Date(lastDayOfWeek.getTime() + (MILLIS_IN_DAY));

      },

      /*
       * Clear the time components of a date leaving the date
       * of the first milli of day
       */
      _clearTime : function(d) {
         d.setHours(0);
         d.setMinutes(0);
         d.setSeconds(0);
         d.setMilliseconds(0);
         return d;
      },

      /*
       * add specific number of days to date
       */
      _addDays : function(d, n, keepTime) {
         d.setDate(d.getDate() + n);
         if (keepTime) {
            return d;
         }
         return this._clearTime(d);
      },

      /*
       * Rotate an array by specified number of places.
       */
      _rotate : function(a /*array*/, p /* integer, positive integer rotate to the right, negative to the left... */) {
         for (var l = a.length, p = (Math.abs(p) >= l && (p %= l),p < 0 && (p += l),p), i, x; p; p = (Math.ceil(l / p) - 1) * p - l + (l = p)) {
            for (i = l; i > p; x = a[--i],a[i] = a[i - p],a[i - p] = x);
         }
         return a;
      },

      _cloneDate : function(d) {
         return new Date(d.getTime());
      },

      /*
       * return a date for different representations
       */
      _cleanDate : function(d) {
         if (typeof d == 'string') {
            return $.weekCalendar.parseISO8601(d, true) || Date.parse(d) || new Date(parseInt(d));
         }
         if (typeof d == 'number') {
            return new Date(d);
         }
         return d;
      },

      /*
       * date formatting is adapted from
       * http://jacwright.com/projects/javascript/date_format
       */
      _formatDate : function(date, format) {
         var options = this.options;
         var returnStr = '';
         for (var i = 0; i < format.length; i++) {
            var curChar = format.charAt(i);
            if ($.isFunction(this._replaceChars[curChar])) {
	           var res = this._replaceChars[curChar](date, options);

	           if (res === '00' && options.alwaysDisplayTimeMinutes === false) {
		          //remove previous character
		          returnStr = returnStr.slice(0, -1);
		        } else {
                 
	               returnStr += res;
	           }
            } else {
               returnStr += curChar;
            }
         }

         return returnStr;
      },

      _replaceChars : {

         // Day
         d: function(date) {
            return (date.getDate() < 10 ? '0' : '') + date.getDate();
         },
         dd: function(date) {
             return (date.getDate() < 10 ? '0' : '') + date.getDate();
          },
         D: function(date, options) {
            return options.shortDays[date.getDay()];
         },
         j: function(date) {
            return date.getDate();
         },
         l: function(date, options) {
            return options.longDays[date.getDay()];
         },
         N: function(date) {
            return date.getDay() + 1;
         },
         S: function(date) {
            return (date.getDate() % 10 == 1 && date.getDate() != 11 ? 'st' : (date.getDate() % 10 == 2 && date.getDate() != 12 ? 'nd' : (date.getDate() % 10 == 3 && date.getDate() != 13 ? 'rd' : 'th')));
         },
         w: function(date) {
            return date.getDay();
         },
         z: function(date) {
            return "Not Yet Supported";
         },
         // Week
         W: function(date) {
            return "Not Yet Supported";
         },
         // Month
         F: function(date, options) {
            return options.longMonths[date.getMonth()];
         },
         m: function(date) {
            return (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1);
         },
         mm: function(date) {
             return (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1);
          },
         M: function(date, options) {
            return options.shortMonths[date.getMonth()];
         },
         n: function(date) {
            return date.getMonth() + 1;
         },
         t: function(date) {
            return "Not Yet Supported";
         },
         // Year
         L: function(date) {
            return "Not Yet Supported";
         },
         o: function(date) {
            return "Not Supported";
         },
         Y: function(date) {
            return date.getFullYear();
         },
         yy: function(date) {
             return date.getFullYear();
          },
         y: function(date) {
            return ('' + date.getFullYear()).substr(2);
         },
         // Time
         a: function(date) {
            return date.getHours() < 12 ? 'am' : 'pm';
         },
         A: function(date) {
            return date.getHours() < 12 ? 'AM' : 'PM';
         },
         B: function(date) {
            return "Not Yet Supported";
         },
         g: function(date) {
            return date.getHours() % 12 || 12;
         },
         G: function(date) {
            return date.getHours();
         },
         h: function(date) {
            return ((date.getHours() % 12 || 12) < 10 ? '0' : '') + (date.getHours() % 12 || 12);
         },
         H: function(date) {
            return (date.getHours() < 10 ? '0' : '') + date.getHours();
         },
         i: function(date) {
            return (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
         },
         s: function(date) {
            return (date.getSeconds() < 10 ? '0' : '') + date.getSeconds();
         },
         // Timezone
         e: function(date) {
            return "Not Yet Supported";
         },
         I: function(date) {
            return "Not Supported";
         },
         O: function(date) {
            return (date.getTimezoneOffset() < 0 ? '-' : '+') + (date.getTimezoneOffset() / 60 < 10 ? '0' : '') + (date.getTimezoneOffset() / 60) + '00';
         },
         T: function(date) {
            return "Not Yet Supported";
         },
         Z: function(date) {
            return date.getTimezoneOffset() * 60;
         },
         // Full Date/Time
         c: function(date) {
            return "Not Yet Supported";
         },
         r: function(date) {
            return date.toString();
         },
         U: function(date) {
            return date.getTime() / 1000;
         }
      }

   });

   $.extend($.ui.weekCalendar, {
      version: '1.2.2-pre'

   });

   var MILLIS_IN_DAY = 86400000;
   var MILLIS_IN_WEEK = MILLIS_IN_DAY * 7;

   $.weekCalendar = function() {
      return {
         parseISO8601 : function(s, ignoreTimezone) {

            // derived from http://delete.me.uk/2005/03/iso8601.html
            var regexp = "([0-9]{4})(-([0-9]{2})(-([0-9]{2})" +
                         "(T([0-9]{2}):([0-9]{2})(:([0-9]{2})(\.([0-9]+))?)?" +
                         "(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?";
            var d = s.match(new RegExp(regexp));
            if (!d) return null;
            var offset = 0;
            var date = new Date(d[1], 0, 1);
            if (d[3]) {
               date.setMonth(d[3] - 1);
            }
            if (d[5]) {
               date.setDate(d[5]);
            }
            if (d[7]) {
               date.setHours(d[7]);
            }
            if (d[8]) {
               date.setMinutes(d[8]);
            }
            if (d[10]) {
               date.setSeconds(d[10]);
            }
            if (d[12]) {
               date.setMilliseconds(Number("0." + d[12]) * 1000);
            }
            if (!ignoreTimezone) {
               if (d[14]) {
                  offset = (Number(d[16]) * 60) + Number(d[17]);
                  offset *= ((d[15] == '-') ? 1 : -1);
               }
               offset -= date.getTimezoneOffset();
            }
            return new Date(Number(date) + (offset * 60 * 1000));
         }
      };
   }();


});
