$(function () {
	
	/*
	 * @class Calendar
	 * @parent Class
	 * @constructor
	 * Create a new Calendar instance.
	 * @tag controllers
	 * @author LabOpenSource
	 */
    $.Class.extend('Controllers.Calendar', /* @prototype */ {
        init: function () {
            var self = this;
            
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
            var $calendar = $('#calendar');
            
            /*  
             * @class Room 
             * @constructor
             * Creates a new Room minimal object for calendar.
             * @param {Number} room id
             * @param {String} room name
             */
            function Room(room_id, room_name) {
                this.id = room_id;
                this.name = room_name;
            }

            if ($calendar.length > 0) {
                //get real rooms list
                $.ajax({
                    url: "findAllRoomsJson.action",
                    context: document.body,
                    success: function (data) {
                        //iterate over the list
                        $(data).each(function (i, val) {
                            //add current room to room list 
                            self.list_rooms.push(new Room(val.id, val.name));
                        });
                        //calculates the new lenght of the list
                        self.num_rooms = self.list_rooms.length;
                        //now load calendar 
                        $calendar.LoadCalendar();
                    },
                    error: function () {
                        //if you cannot retrieve the list of rooms then...
                        $().notify($.i18n("warning"), $.i18n("listRoomsRetrieve"));
                    }
                });
                $(".type_rooms").hide();
                $.fn.LoadCalendar = function () {
                    $(this).weekCalendar({
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
                            today: $.i18n("today"),
                            lastWeek: $.i18n("prev"),
                            nextWeek: $.i18n("next")
                        },
                        height: function ($calendar) {
                            return $(window).height() - $("h1").outerHeight() - 1;
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
                        draggable: function (calEvent, $event) {
                            return calEvent.readOnly != true;
                        },
                        resizable: function (calEvent, $event) {
                            return calEvent.readOnly != true;
                        },
                        //metodo per la creazione di una nuova casella
                        eventNew: function (calEvent, $event) {
                            var $dialogContent = $("#event_edit_container");
                            var startField = calEvent.start;
                            var endField = calEvent.end;
                            var id_booked = calEvent.id_booked;
                            var id_room = calEvent.id;
                            var room_name = getRoomNameById(id_booked);
                            $dialogContent.load("goAddBookingFromPlanner.action", {
                                'booking.room.id': id_booked,
                                'booking.dateIn': startField,
                                'booking.dateOut': endField
                            }, function () {
                                new Main(I18NSettings.lang, I18NSettings.datePattern);
                                this.booking = new Booking(I18NSettings.lang, I18NSettings.datePattern);
                                $(".btn_save").hide();
                                $(".canc_booking").hide();
                                $(".btn_check_in").hide();
                            }).dialog({
                                open: function (event, ui) {
                                    $(".btn_save").hide();
                                },
                                modal: true,
                                width: 790,
                                hide: "explode",
                                show: "blind",
                                title: $.i18n("newBookingForRoom") + ": " + room_name,
                                close: function () {
                                    $dialogContent.dialog("destroy");
                                    $dialogContent.hide();
                                    $('#calendar').weekCalendar("removeUnsavedEvents");
                                },
                                buttons: {
                                    save: function () {
                                        if (!$dialogContent.find(".yform.json").valid()) {
                                            $("#accordion,#accordion2").accordion("option", "active", 0);
                                        }
                                        $dialogContent.find(".yform.json").submitForm();
                                        // $dialogContent.dialog("close");
                                    },
                                    cancel: function () {
                                        if (confirm($.i18n("alertCancel"))) {
                                            $dialogContent.dialog("close");
                                            $calendar.weekCalendar("removeEvent", calEvent.id);
                                        }
                                    }
                                }
                            }).show();
                        },
                        eventDrop: function (calEvent, $event) {},
                        eventResize: function (calEvent, $event) {},
                        //metodo che viene richiamato quando clicco su una casella
                        eventClick: function (calEvent, $event) {
                            if (calEvent.readOnly) {
                                return;
                            }
                            var $dialogContent = $("#event_edit_container");
                            //calEvent è un tipo di dato data, che è un oggetto con degli attributi start end e title
                            //per cui, se clicco in una casella che ha già un data calEvent, allora setterò con 
                            //questi valori i campi di testo nella finestra di dialogo.
                            var startField = calEvent.start;
                            var endField = calEvent.end;
                            var id_booked = calEvent.bookId;
                            var id_room = calEvent.id;
                            var room_name = getRoomNameById(id_room);
                            $dialogContent.addClass("loaderback").load("goUpdateBookingFromPlanner.action", {
                                id: id_booked
                            }, function () {
                                $(this).removeClass("loaderback");
                                new Main(I18NSettings.lang, I18NSettings.datePattern);
                                this.booking = new Booking(I18NSettings.lang, I18NSettings.datePattern);
                                $(".btn_save").hide();
                                $(".canc_booking").hide();
                            }).dialog({
                                open: function (event, ui) {
                                    //optionsLoc.init();
                                },
                                modal: true,
                                width: 790,
                                position: 'top',
                                title: $.i18n("modifyBooking") + " - " + room_name,
                                close: function () {
                                    $dialogContent.dialog("destroy");
                                    $dialogContent.hide();
                                    // $('#calendar').weekCalendar("removeUnsavedEvents");
                                },
                                buttons: {
                                    save: function () {
                                        if (!$dialogContent.find(".yform.json").valid()) {
                                            $("#accordion,#accordion2").accordion("option", "active", 0);
                                        }
                                        $dialogContent.find(".yform.json").submitForm();
                                        // $dialogContent.dialog("close");
                                    },
                                    "delete": function () {
                                        if (confirm($.i18n("alertDelete"))) {
                                            $dialogContent.find(".yform.json").submitForm("deleteBooking.action");
                                            //$calendar.weekCalendar("removeEvent", calEvent.id);
                                            $dialogContent.dialog("close");
                                        }
                                    },
                                    cancel: function () {
                                        //--  $calendar.weekCalendar("removeEvent", calEvent.id);
                                        if (confirm($.i18n("alertCancel"))) {
                                            $dialogContent.dialog("close");
                                            $calendar.weekCalendar("refresh");
                                        }
                                    }
                                }
                            }).show();
                            $(window).resize().resize(); //fixes a bug in modal overlay size ??
                        },
                        eventMouseover: function (calEvent, $event) {},
                        eventMouseout: function (calEvent, $event) {},
                        noEvents: function () {},
                        data: "findAllBookingsJson.action"
                    });
                };
            }

            
            /*
             * Get number of days between two dates.
             * @param {Date} first date.
             * @param {Date} second date.
             * @return {Number} number of days
             */
            function days_between(date1, date2) {
                // The number of milliseconds in one day
                var ONE_DAY = 1000 * 60 * 60 * 24;
                // Convert both dates to milliseconds
                var date1_ms = date1.getTime();
                var date2_ms = date2.getTime();
                // Calculate the difference in milliseconds
                var difference_ms = Math.abs(date1_ms - date2_ms);
                // Convert back to days and return
                return Math.round(difference_ms / ONE_DAY);
            }
            
            /*
             * Get Room name by id value.
             * @param {Number} id number.
             * @return {String} name of room
             */
            function getRoomNameById(id) {
                var input_room_id = $('input[name="id_room"]').filter(function () {
                    return ($(this).val() == id);
                });
                var name = $(input_room_id).siblings();
                if (name.text() != undefined) return name.text();
                else return '******';
            }
        }
    });
    new Controllers.Calendar();
});