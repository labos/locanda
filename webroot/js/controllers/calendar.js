$(function() {
    $.Class.extend('Calendar', /* @static */ {
    	init: function () {

var $calendar = $('#calendar');
	var id = 10;
	//create a Room javascript object

	function Room(room_id, room_name) {
		this.id = room_id;
		this.name = room_name;
	} /* setting rooms_list array */
	// var list_rooms=new Array(1, 4, 23, 35,36,37,38,39,40,41,42,43,44,45,49,52,53,54,55);
	//listing sample rooms to display in the planner
	//  var list_rooms=new Array(new Room(100,"bella vista"), new Room(104,"lato piazza"),new Room(123,"suite"),new Room(135,"al terrazzo"),new Room(136,"vista mare"));
	var list_rooms = []; /* setting number of rooms */
	var num_rooms = list_rooms.length;
	if ($calendar.length > 0) {
		//get real rooms list
		$.ajax({
			url: "findAllRoomsJson.action",
			context: document.body,
			success: function (data) {
				//iterate over the list
				$(data).each(function (i, val) {
					//add current room to room list 
					list_rooms.push(new Room(val.id, val.name));
				});
				//calculates the new lenght of the list
				num_rooms = list_rooms.length;
				//now load calendar 
				$calendar.LoadCalendar();
			},
			error: function () {
				//if you cannot retrieve the list of rooms then...
				$().notify($.i18n("warning"), "Problema restituzione lista camere...");
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
					end: num_rooms,
					limitDisplay: true
				},
				daysToShow: 10,
				//added by Alberto
				listRooms: list_rooms,
				buttonText: {
					today: "today",
					lastWeek: "Prev",
					nextWeek: "Next"
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
					$().getCustomers("input[name='fullname']");
					$dialogContent.load("goAddBookingFromPlanner.action", {
						'booking.room.id': id_booked,
						'booking.dateIn': startField,
						'booking.dateOut': endField
					}, function () {
						Option.init(I18NSettings.lang, I18NSettings.datePattern);
						$(".btn_save").hide();
					}).dialog({
						open: function (event, ui) {
							$(".btn_save").hide();
						},
						modal: true,
						width: 650,
						hide: "explode",
						show: "blind",
						title: "New Booking for room: " + room_name,
						close: function () {
							$dialogContent.dialog("destroy");
							$dialogContent.hide();
							$('#calendar').weekCalendar("removeUnsavedEvents");
						},
						buttons: {
							save: function () {
								if (!$dialogContent.find(".yform.json").valid()) {
									$("#accordion").accordion("option", "active", 0);
								}
								$dialogContent.find(".yform.json").submitForm();
								// $dialogContent.dialog("close");
							},
							cancel: function () {
								$dialogContent.dialog("close");
								$calendar.weekCalendar("removeEvent", calEvent.id);
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
						Option.init(I18NSettings.lang, I18NSettings.datePattern);
						$(".btn_save").hide();
					}).dialog({
						open: function (event, ui) {
							//optionsLoc.init();
						},
						modal: true,
						width: 650,
						title: "Modify Booking - " + room_name,
						close: function () {
							$dialogContent.dialog("destroy");
							$dialogContent.hide();
							// $('#calendar').weekCalendar("removeUnsavedEvents");
						},
						buttons: {
							save: function () {
								if (!$dialogContent.find(".yform.json").valid()) {
									$("#accordion").accordion("option", "active", 0);
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
								$dialogContent.dialog("close");
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
	// END WEEKCALENDAR FUNCTION

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
		return Math.round(difference_ms / ONE_DAY);
	}

	
	function getEventData() {
		var year = new Date().getFullYear();
		var month = new Date().getMonth();
		var day = new Date().getDate();
		return {
			events: [{
				"id": 100,
				"start": new Date(year, month, day, 12),
				"end": new Date(year, month, day + 1, 13, 30),
				"title": "Giovanni Stara"
			}, {
				"id": 104,
				"start": new Date(year, month, day, 14),
				"end": new Date(year, month, day, 14, 45),
				"title": "Marc Devois"
			}, {
				"id": 123,
				"start": new Date(year, month, day + 1, 17),
				"end": new Date(year, month, day + 5, 17, 45),
				"title": "Laura Molinari"
			},

				            {
				"id": 100,
				"start": new Date(year, month, day + 2, 14),
				"end": new Date(year, month, day + 2, 15),
				"title": "Michele Gors"
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
		$("select[name='start']").change(function () {
			var startTime = $(this).find(":selected").val();
			var currentEndTime = $endTimeField.find("option:selected").val();
			$endTimeField.html(
			$endTimeOptions.filter(function () {
				return startTime < $(this).val();
			}));
			var endTimeSelected = false;
			$endTimeField.find("option").each(function () {
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
		//---  END PLANNER SECTION CODE   
		
		
		

		function getRoomNameById(id) {
			var input_room_id = $('input[name="id_room"]').filter(function () {
				return ($(this).val() == id);
			});
			var name = $(input_room_id).siblings();
			if (name.text() != undefined) return name.text();
			else return '******';
		}
		
		
		
		//---  PLANNER SECTION CODE   
		// change rate for room
		$.fn.changeRate = function (amount, first, second) {
			var currency = "&euro";
			var to_replace = "Per ";
			var result = false;
			if (typeof amount !== "undefined" && typeof first !== "undefined" && typeof second !== "undefined") {
				var infos = $(this).children("span");
				if (infos && typeof infos === "object" && infos.length) {
					try {
						infos.eq(0).html(amount + ' ' + currency);
						infos.eq(1).html(' / ' + first.replace(to_replace, ""));
						infos.eq(2).html(' / ' + second.replace(to_replace, ""));
						result = true;
					} catch (e) {
						result = false;
					}
				}
			}
			return result;
		};
		
		
    	}},/* @prototype */ {});
		
		
});