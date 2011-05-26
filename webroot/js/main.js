$(document).ready(function () {
	overlay = $('<div id="fancybox-overlay"></div>');
	var Option = function (lang, patternDate) {
			this.datePattern = patternDate;
			this.alertOK = $.i18n("congratulation");
			this.alertKO = $.i18n("warning");
			var ONE_DAY = 1000 * 60 * 60 * 24;
			this.init = function () {
				$(".yform").validate();
				$(".yform.json").validate(); /* booking section initialization */
				$(".datepicker").datepicker({
					showOn: "button",
					buttonImage: "images/calendar.gif",
					buttonImageOnly: true,
					dateFormat: patternDate,
					onClose: function (dateText, inst) {
						var numNights = 0;
						var closerDateInput = $(".datepicker").not($(this));
						var otherData = closerDateInput.datepicker("getDate");
						// var selectedData = new Date(dateText);
						var selectedData = $(this).datepicker("getDate");
						if (selectedData && otherData) {
							numNights = days_between_signed(otherData, selectedData);
							if (numNights == 0) $().notify(optionsLoc.alertKO, "Attenzione, la data di inizio non può essere uguale a quella di fine");
						}
						$("#booking_duration").val(numNights);
					}
				});
				getCustomers("input[name='booking.booker.lastName']"); /* end booking section initialization */
				$(".yform.json").submit(function (event) {
					$(this).submitForm();
					return false;
				});
				$("#accordion").accordion({
					collapsible: true,
					active: false,
					animated: 'bounceslide',
					autoHeight: true
				});
				$(".btn_checked").button({
					disabled: true
				});
				$(".btn_check_in").button({
					icons: {
						primary: "ui-icon-check"
					}
				}).click(function (event) {
					event.preventDefault();
					var hrefAction = "checkInBooking.action",
						$this = $(this),
						idBooking = (typeof parseInt($('input:hidden[name="booking.id"]').val()) === 'number') ? parseInt($('input:hidden[name="booking.id"]').val()) : null;
					if (idBooking !== null && idBooking > 0) {
						$.ajax({
							type: "POST",
							url: hrefAction,
							data: {
								id: idBooking
							},
							success: function (data_action) {
								var title_notification = null;
								if (data_action.result == "success") {
									$().notify($.i18n("congratulation"), data_action.description);
									//UPDATE BOOKING STATUS HIDDEN FIELD
									$("input:hidden[name='booking.status']").val("checkin");
									//ADD CHECKOUT LISTENER
									$this.attr("class", "btn_check_out").button("destroy").button({
										label: "CHECK OUT",
										icons: {
											primary: "ui-icon-check"
										}
									}).click(function (event) {
										event.preventDefault();
										var hrefAction = "checkOutBooking.action",
											$this = $(this),
											idBooking = (typeof parseInt($('input:hidden[name="booking.id"]').val()) === 'number') ? parseInt($('input:hidden[name="booking.id"]').val()) : null;
										$.ajax({
											type: "POST",
											url: hrefAction,
											data: {
												id: idBooking
											},
											success: function (data_action) {
												var title_notification = null;
												if (data_action.result == "success") {
													$().notify($.i18n("congratulation"), data_action.description);
													// UPDATE BUTTON CHECKING
													$this.button({
														disabled: true,
														label: "CHECKED"
													});
													// END UPDATE BUTTON CHECKING
													//UPDATE BOOKING STATUS HIDDEN FIELD
													$("input:hidden[name='booking.status']").val("checkout");
												} else if (data_action.result == "error") {
													$().notify($.i18n("warning"), data_action.description);
												} else {
													$(".validationErrors").html(data_action);
												}
											},
											error: function () {
												$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
											}
										});
									});
									//END ADDING CHECKOUT LISTENER
								} else if (data_action.result == "error") {
									$().notify($.i18n("warning"), data_action.description);
								} else {
									$(".validationErrors").html(data_action);
								}
							},
							error: function () {
								$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
							}
						});
					}
				});
				$(".btn_check_out").button({
					icons: {
						primary: "ui-icon-check"
					}
				}).click(function (event) {
					event.preventDefault();
					var hrefAction = "checkOutBooking.action",
						$this = $(this),
						idBooking = (typeof parseInt($('input:hidden[name="booking.id"]').val()) === 'number') ? parseInt($('input:hidden[name="booking.id"]').val()) : null;
					$.ajax({
						type: "POST",
						url: hrefAction,
						data: {
							id: idBooking
						},
						success: function (data_action) {
							var title_notification = null;
							if (data_action.result == "success") {
								$().notify($.i18n("congratulation"), data_action.description);
								$this.button({
									disabled: true,
									label: "CHECKED"
								});
								$("input:hidden[name='booking.status']").val("checkout");
							} else if (data_action.result == "error") {
								$().notify($.i18n("warning"), data_action.description);
							} else {
								$(".validationErrors").html(data_action);
							}
						},
						error: function () {
							$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
						}
					});
				}); /* extras adding */

				/* adjustment and payments*/
				$.fn.getSelector = function () {
					var selector = "";
					if ($(this).attr("class").indexOf("adjustment") >= 0) selector = "adjustment";
					else selector = "payment";
					return selector;
				}
				$(".extra_value_adjustment, .extra_value_payment").keyup(function () { /* prepare selector string for class whit whitespaces */
					//-- var current_class_selector = $(this).attr("class").replace( new RegExp(" ","g"), ".");
					/* adjust subtotal ... */
					var new_subtotal = null;
					if ($(this).valid()) {
						if ($(this).getSelector() == "adjustment") {
							updateSubtotal(); /* end code for subtotal calculation */
						} else {
							updateBalance();
						}
					} else {
						$(this).val('');
						updateSubtotal();
					}
					//--- $(this).unbind('keyup');
				});
				$(".erase_adjustment, .erase_payment").click(function () {
					var selector = $(this).getSelector();
					$(this).parents("." + selector + "_row").find(".extra_value_" + selector + "").val(0);
					updateSubtotal();
					$(this).closest("." + selector + "_row").remove();
				});
				$(".add_adjustment, .add_payment").click(function () {
					var selector = $(this).getSelector();
					//count the number of periods already added
					var formParent = $(this).parents(".type-text");
					var num_of_items = formParent.siblings(".adjustment_row").size();
					// get last subcolumns
					var dd = formParent.siblings("." + selector + "_row:last").length ? formParent.siblings("." + selector + "_row:last") : formParent;
					// setup of cloned row to add
					var added = $("#to_add_" + selector + "").clone().insertAfter(dd).removeAttr("id").show();
					added.html(added.html().replace(/__PVALUE__/ig, num_of_items));
					// attach listener to cloned row
					// attach erase click
					added.find(".erase_" + selector + "").click(function () {
						$(this).parents("." + selector + "_row").find(".extra_value_" + selector + "").val(0);
						updateSubtotal();
						$(this).closest("." + selector + "_row").remove();
					});
					added.find(".extra_value_" + selector + "").keyup(function () { /* prepare selector string for class whit whitespaces */
						var current_class_selector = $(this).attr("class").replace(new RegExp(" ", "g"), "."); /* adjust subtotal ... */
						var new_subtotal = null;
						if ($(this).valid()) {
							if (current_class_selector.indexOf("extra_value_" + selector + "") >= 0) {
								updateSubtotal(); /* end code for subtotal calculation */
							} else {
								updateBalance();
							}
						} else {
							$(this).val('');
							updateSubtotal();
						}

					});
				});
				$('input[name="pay_value_adjustment[]"]').keyup(function () {
					var current_parent = $(this).parents(".type-text"); /* prepare selector string for class whit whitespaces */
					var current_class_selector = $(this).attr("class").replace(new RegExp(" ", "g"), "."); /* check if current was cloned */
					var next_sibling = current_parent.next().find(".extra_value_adjustment");
					var prova = next_sibling.size();
					if (next_sibling && !next_sibling.size() > 0) {
						var copy_parent = current_parent.clone(true);
						var indexOfArray = $(this).attr("name");
						copy_parent.find(".green").remove();
						//copy_parent.find(this).val("0.0");
						copy_parent.find("input").val("");
						//copy_parent.find($(this)).bind('keyup',cloneEvent);
						copy_parent.insertAfter(current_parent);
						// $(this).unbind('keyup');
					} /* adjust subtotal ... */
					var new_subtotal = null;
					if (current_class_selector.indexOf("extra_value_adjustment") >= 0) {
						new_subtotal = parseInt($("#subtotal_room").val());
						new_balance = parseInt($("#balance_room").val()); /* code for calcute new subtotal */
						$("." + current_class_selector).each(function (key, value) {
							if ($(value).valid()) {
								new_subtotal = new_subtotal + parseInt($(value).val());
							}
						});
						// show new subtotal value
						updateSubtotal(); /* end code for subtotal calculation */
					} else {

						updateBalance();
					}
					
				});

				//---  ADD ROOMS SECTION CODE   
				$(".btn_save").button({
					icons: {
						primary: "ui-icon-check"
					}
				});
				//update of dateOut changing num of nights.
				$("select[name='numNights']").change(function () {
					$('input[name="booking.dateIn"]').rules("add", {
						required: true
					});
					var dateOut = '';
					var numNights = $(this).find(":selected").val();
					var dateInVal = $('input[name="booking.dateIn"]').datepicker('getDate');
					var $dateInDom = $('input[name="booking.dateIn"]');
					if (dateInVal !== '' && $dateInDom.valid()) {
						var dateInDate = new Date(dateInVal);
						var millisO = dateInDate.getTime();
						var dateOutDateMill = dateInDate.getTime() + (ONE_DAY * numNights);
						// dateOut = new dateOutDate.toString('dd/mm/yyyy');
						var dateOutDate = new Date(dateOutDateMill);
						dateOut = $.datepicker.formatDate(I18NSettings.datePattern, dateOutDate);
					}
					$('input[name="booking.dateOut"]').datepicker("setDate", dateOut);
				});
				$(".erase_guest").click(function () {
					$(this).closest("." + "guest" + "_row").remove();
				});
				$(".add_guest").click(function () {
					//count the number max of guests to select
					var $formParent = $(".guests-select");
					var max = 0;
					$(".guests-select option").each(function () {
						if (max < $(this).val()) {
							max = $(this).val();
						}
					});
					var numbermaxGuests = parseInt(max);
					//update number of rows to add guests
					var selector = "guest";
					var num_of_items = $formParent.siblings(".guest_row").size();
					// get last subcolumns
					var dd = $formParent.siblings("." + selector + "_row:last").length ? $formParent.siblings("." + selector + "_row:last") : $formParent;
					// setup of cloned row to add
					if (num_of_items >= numbermaxGuests) $().notify(optionsLoc.alertKO, "Attenzione, stai aggiungendo un numero di ospiti maggiore della capacità della stanza");
					var added = $("#to_add_" + selector + "").clone().insertAfter(dd).removeAttr("id").show();
					added.html(added.html().replace(/__PVALUE__/ig, num_of_items));
					// attach listener to cloned row
					// attach erase click listener
					added.find(".erase_" + selector + "").click(function () {
						$(this).closest("." + selector + "_row").remove();
					});
				}); /*ADD LISTENER FOR CHANGE ROOM OR DATEIN OR DATEOUT OR NUMNIGHTS FROM BOOKING*/
				$('#sel_rooms_list, #booking_duration, input:text[name="booking.dateIn"], input:text[name="booking.dateOut"], input:checkbox[name="bookingExtraIds"], #nr_guests, #convention').change(function () {
					// check in room was selected
					if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
						$().notify($.i18n("warning"), "Devi selezionare una room");
						return;
					}
					var formInput = $(this).parents().find(".yform.json").serialize();
					var $clicked = $(this);
					$.ajax({
						type: "POST",
						url: "calculatePrices.action",
						data: formInput,
						success: function (data_action) {
							var title_notification = null;
							try {
								if (data_action.message.result == "success") {
									//update dom values here
									var roomSubTotal = data_action.booking.roomSubtotal;
									var extraSubTotal = data_action.booking.extraSubtotal;
									var numNights = data_action.numNights;
									var priceRoom = 0;
									var subTotal = roomSubTotal + extraSubTotal;
									var maxGuests = data_action.booking.room.roomType.maxGuests;
									if (maxGuests !== null && parseInt(maxGuests) > 0 && $clicked.is("select#sel_rooms_list")) {
										var numbermaxGuests = parseInt(maxGuests);
										$("#nr_guests").empty();
										for (var i = 1; i <= numbermaxGuests; i++) {
											$("#nr_guests").append('<option value="' + i + '">' + i + '</option');
										}
									}
									if (maxGuests !== null && parseInt(maxGuests) > 0 && ($clicked.is("select#sel_rooms_list") || $clicked.is("select#nr_guests"))) {
										var numbermaxGuests = parseInt(maxGuests);
										//update number of rows to add guests
										var selector = "guest";
										//count the number of guests already added
										var formParent = $(".guests-select");
										var num_of_items = formParent.siblings(".guest_row").size();
										// get last subcolumns
										var dd = formParent.siblings("." + selector + "_row:last").length ? formParent.siblings("." + selector + "_row:last") : formParent;
										// setup of cloned row to add
										for (var i = num_of_items; i < numbermaxGuests; i++) {
											var added = $("#to_add_" + selector + "").clone().insertAfter(dd).removeAttr("id").show();
											added.html(added.html().replace(/__PVALUE__/ig, num_of_items));
											// attach listener to cloned row
											// attach erase click
											added.find(".erase_" + selector + "").click(function () {
												$(this).closest("." + selector + "_row").remove();
											});
										}
									}
									$("#price_room").html(roomSubTotal);
									$("#extras_room").html(extraSubTotal);
									$('input:hidden[name="booking.subtotal"]').val(subTotal);
									$("span.subtotal_room").text(subTotal);
									$("span.balance_room").text(subTotal);
									$("#booking_duration").val(numNights);
									updateSubtotal();
									//update subtotal
									//  updateSubtotal();
									//$().notify("Congratulazioni", data_action.message.description, _redirectAction);
								} else if (data_action.message.result == "error") {
									$().notify($.i18n("warning"), data_action.message.description);
								} else {
									$(".validationErrors").html(data_action);
								}
							} catch (e) {
								//an error in data returned...
								$(".validationErrors").html(data_action);
							}
						},
						error: function () {
							$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
						}
					});
				})
			};
		};
	var optionsLoc = new Option(I18NSettings.lang, I18NSettings.datePattern);
	optionsLoc.init();
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
	$.fn.addImageObject = function (responseObject, actionName) {
		if (responseObject && typeof actionName !== "undefined" && actionName && typeof actionName === "string") {
			if (responseObject.roomFacility && actionName.indexOf("uploadFacility") >= 0) {
				//get the name of the facility
				var name_facility = responseObject.roomFacility.name;
				//get the file name of the facility
				var file_facility = responseObject.roomFacility.fileName;
				//get the id of the facility
				var id_facility = responseObject.roomFacility.id;
				//clone the html portion to replicate
				var facility_row_checked_cloned = $(".facility:hidden").clone();
				//set src file name
				var src = facility_row_checked_cloned.find('img').attr("src") + file_facility;
				//add src file name
				facility_row_checked_cloned.find('img').attr("src", src);
				//add checkbox id
				facility_row_checked_cloned.find('input:checkbox').attr("id", id_facility + "_fac");
				//add checkbox name
				facility_row_checked_cloned.find('input:checkbox').attr("name", "roomFacilitiesIds");
				//add checkbox value
				facility_row_checked_cloned.find('input:checkbox').attr("value", id_facility);
				//add label text
				facility_row_checked_cloned.find('label').attr("for", id_facility + "_fac").text(name_facility);
				facility_row_checked_cloned.insertAfter($(".facility:last")).show();
				facility_row_checked_cloned.animate({
					backgroundColor: "#A2D959",
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.roomFacility && actionName.indexOf("uploadRoomTypeFacility") >= 0) {
				//get the name of the facility
				var name_facility = responseObject.roomFacility.name;
				//get the file name of the facility
				var file_facility = responseObject.roomFacility.fileName;
				//get the id of the facility
				var id_facility = responseObject.roomFacility.id;
				//clone the html portion to replicate
				var facility_row_checked_cloned = $(".facility:hidden").clone();
				//set src file name
				var src = facility_row_checked_cloned.find('img').attr("src") + file_facility;
				//add src file name
				facility_row_checked_cloned.find('img').attr("src", src);
				//add checkbox id
				facility_row_checked_cloned.find('input:checkbox').attr("id", id_facility + "_fac");
				//add checkbox name
				facility_row_checked_cloned.find('input:checkbox').attr("name", "roomTypeFacilitiesIds");
				//add checkbox value
				facility_row_checked_cloned.find('input:checkbox').attr("value", id_facility);
				//add label text
				facility_row_checked_cloned.find('label').attr("for", id_facility + "_fac").text(name_facility);
				facility_row_checked_cloned.insertAfter($(".facility:last")).show();
				facility_row_checked_cloned.animate({
					backgroundColor: "#A2D959",
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.image && actionName.indexOf("uploadStructureImage") >= 0) {
				//get the name of the image
				var name_image = responseObject.image.name;
				//get the file name of the image
				var file_image = responseObject.image.fileName;
				//get the id of the image
				var id_image = responseObject.image.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_image;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_image);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_image;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.structureFacility && actionName.indexOf("uploadStructureFacility") >= 0) {
				//get the name of the facility
				var name_facility = responseObject.structureFacility.name;
				//get the file name of the facility
				var file_facility = responseObject.structureFacility.fileName;
				//get the id of the facility
				var id_facility = responseObject.structureFacility.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs_facility li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_facility;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_facility);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_facility;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs_facility li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.image && actionName.indexOf("uploadRoomImage") >= 0) {
				//get the name of the image
				var name_image = responseObject.image.name;
				//get the file name of the image
				var file_image = responseObject.image.fileName;
				//get the id of the image
				var id_image = responseObject.image.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_image;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_image);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_image;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.image && actionName.indexOf("uploadRoomTypeImage") >= 0) {
				//get the name of the image
				var name_image = responseObject.image.name;
				//get the file name of the image
				var file_image = responseObject.image.fileName;
				//get the id of the image
				var id_image = responseObject.image.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_image;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_image);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_image;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			}
		} else {
			//nothing
		}
	};
	//make a new div overlay element
	$('body').append($('<div class="ui-widget-overlay"></div>'));
	$.jGrowl.defaults.position = 'center';
	//notifier for all jsp
	$.fn.notify = function (title, description, redirect) {
		//get height of the body to cover all html page
		var heightbody = $('body').height();
		if (!$(".ui-widget-overlay").is(':visible')) {
			$(".ui-widget-overlay").css("height", heightbody).show();
		}
		$.jGrowl(description, {
			beforeClose: function (e, m) {
				if (redirect) {
					window.location.href = redirect;
				}
			},
			animateOpen: {
				height: 'show'
			},
			position: "center",
			speed: 1000,
			life: 1000,
			header: title,
			close: function () {
				$(".ui-widget-overlay").hide();
			}
		});
	};
	$.fn.submitForm = function (action) {
		//setting for input form fields
		var formInput = $(this).serialize();
		var hrefAction = action || $(this).attr("action");
		var _redirectAction = $(this).find('input:hidden[name="redirect_form"]').val();
		_redirectAction = (_redirectAction == null) ? "home.action" : _redirectAction;
		//if form is valid
		if ($(this).valid()) {
			$.ajax({
				type: "POST",
				url: hrefAction,
				data: formInput,
				success: function (data_action) {
					var title_notification = null;
					if (data_action.result == "success") {
						$().notify($.i18n("congratulation"), data_action.description, _redirectAction);
					} else if (data_action.result == "error") {
						$().notify($.i18n("warning"), data_action.description);
					} else {
						$(".validationErrors").html(data_action);
					}
				},
				error: function () {
					$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
				}
			});
		}
		return false;
	}; /* update subtotal */
	var updateSubtotal = function () {
			var subtotal = 0;
			//-- var payments = ["#price_room",  "#extras_room", "input.extra_value_adjustment" ];
			var payments = ["#extras_room", "input.extra_value_adjustment", '#price_room'];
			try {
				$.each(payments, function (key, value) {
					if ($(value).size() == 1) {
						var value_contained = $(value).is('input') ? $(value).val() : $(value).text();
						subtotal += isNaN(parseInt(value_contained)) ? 0 : parseInt(value_contained);
					} //else if an array of doms was selected by selector
					else {
						$(value).each(function (k, v) {
							var value_contained = $(v).is('input') ? $(v).val() : $(v).text();
							subtotal += isNaN(parseInt(value_contained)) ? 0 : parseInt(value_contained);
						});
					}
				});
				// now update permanently subtotal
				$(".subtotal_room").text(subtotal);
				//---  $("#subtotal_room").val(subtotal);
				updateBalance();
			} catch (e) {
				//nothing for now -- problema nei selettori
			}
		}; /* update balance due */
	var updateBalance = function () {
			var subDue = 0;
			var due = ".extra_value_payment";
			try {
				if ($(due).size() == 1) {
					var value_contained = $(due).is('input') ? $(due).val() : $(due).text();
					subDue += isNaN(parseInt(value_contained)) ? 0 : parseInt(value_contained);
				} //else if an array of doms was selected by selector
				else {
					$(due).each(function (k, v) {
						var value_contained = $(v).is('input') ? $(v).val() : $(v).text();
						subDue += isNaN(parseInt(value_contained)) ? 0 : parseInt(value_contained);
					});
				}
				// now update permanently balance
				var subtotal = isNaN(parseInt($(".subtotal_room").text())) ? 0 : parseInt($(".subtotal_room").text());
				var balanceDue = subtotal - subDue;
				$("#balance_room").val(balanceDue);
				$(".balance_room").html(balanceDue);
			} catch (e) {
				//nothing for now -- problema nei selettori
			}
		};
	




	//---  LOGIN SECTION CODE   
	$(".btn_submit").button({
		icons: {
			primary: "ui-icon-triangle-1-e"
		}
	});
	//---  END LOGIN SECTION CODE   
	//---  ADD ROOMS SECTION CODE   
	//select room types
	$("#roomtype_id").autocomplete({
		minLength: 2,
		source: function (request, response) {
			var term = request.term;
			lastXhr = $.getJSON("findAllRoomTypes.action", request, function (data, status, xhr) {
				response(data);
			});
		}
	});
	//submit management for add room form
	//--  $(".btn_add_room").submitForm("findAllRooms.action?section=accomodation", null);
	//add notify functionality as tooltip for input text
	$("#room_name_id, #roomtype_id, #max_guests_id, #price_room_id").mousedown(function () {
		$.jGrowl("close");
		$.jGrowl($(this).nextAll("span:hidden").text(), {
			position: "top-left"
		});
	}).mouseout(function () {
		$.jGrowl("close");
	});
	//button for form reset  
	$(".btn_reset").button({
		icons: {
			primary: "ui-icon-trash"
		}
	}).click(function (event) {
		event.preventDefault();
		var validator = $(this).parents(".yform.json").validate();
		validator.resetForm();
	});
	//button for form hiding
	$(".btn_cancel_form").click(function (event) {
		$(this).parents(".hideform").fadeOut().hide("slide", {
			direction: "up"
		}, 1000);
		$(".btn_add_form").show();
	});
	$.fn.deprecatedBrowser = function () {
		var deprecated = false;
		if ($.browser.msie || $.browser.opera || ($.browser.mozilla && $.browser.version.slice(0, 3) == "1.9" && parseInt($.browser.version.slice(4, 5)) < 2)) {
			deprecated = true;
		}
		return deprecated;
	};
	$('#uploadFacility, #uploadImage, #uploadStructFacility').fileUploadUI({
		uploadTable: $('#result_facility_upload'),
		downloadTable: $('#result_facility_upload'),
		url: function (form) {
			var actionUrl = form.attr('action');
			var splittedUrl = ["", ""];
			splittedUrl = actionUrl.split(".");
			if ($().deprecatedBrowser()) {
				actionUrl = splittedUrl[0] + 'IF.' + splittedUrl[1];
			}
			return actionUrl;
		},
		onProgress: function (event, files, index, xhr, handler) {
			if (handler.progressbar) {
				handler.progressbar.progressbar('value', parseInt(event.loaded / event.total * 100, 10));
			}
		},
		buildUploadRow: function (files, index) {
			return $('<tr><td>' + files[index].name + '<\/td>' + '<td class="file_upload_progress"><div><\/div><\/td>' + '<td class="file_upload_cancel">' + '<button class="ui-state-default ui-corner-all" title="Cancel">' + '<span class="ui-icon ui-icon-cancel">Cancel<\/span>' + '<\/button><\/td><\/tr>');
		},
		buildDownloadRow: function (file) {
			var resultRow = "";
			if (typeof file !== "undefined") {
				resultRow = file.message.result;
			} else {
				resultRow = "";
			}
			return $('<tr><td>' + resultRow + '<\/td><\/tr>');
		},
		onComplete: function (event, files, index, xhr, handler) {
			var json = handler.response;
			if (typeof json !== "undefined" && typeof json.message !== "undefined" && json.message.result == "success") {
				var action = this.uploadForm.attr("action");
				try {
					$().addImageObject(json, action);
				} catch (e) {
					//var log = e;
				}
				$().notify(optionsLoc.alertOK, json.message.description);
			} else if (typeof json !== "undefined" && typeof json.message !== "undefined" && json.message.result == "error") {
				$().notify(optionsLoc.alertKO, json.description);
			} else {
				$().notify(optionsLoc.alertKO, "Errore nei dati restituiti");
			}
		},
		onAbort: function (event, files, index, xhr, handler) {
			$().notify(optionsLoc.alertKO, "E'stato interrotto l'upload");
			handler.removeNode(handler.uploadRow);
		},
		onError: function (event, files, index, xhr, handler) {
			if (handler.originalEvent) {
				// handle JSON parsing errors 
			} else {
				// handle XHR upload errors ... 
			}
		},
		parseResponse: function (xhr, handler) {
			//var prova = xhr.contents();
			if (typeof xhr.responseText !== 'undefined') {
				return $.parseJSON(xhr.responseText);
			} else {
				// Instead of an XHR object, an iframe is used for legacy browsers:
				return $.parseJSON(xhr.contents().text());
			}
		},
		beforeSend: function (event, files, index, xhr, handler, callBack) {
			var facility_name = handler.uploadForm.parents(".beauty").find('input[name="facility_name"]').val();
			var type_img = files.type;
			if (facility_name.length > 2) {
				handler.uploadForm.find('input:hidden[name="name"]').val(facility_name);
				callBack();
			} else {
				handler.uploadForm.parents(".beauty").find('input[name="facility_name"]').addClass("error").after('<label for="name_facility"  class="error">This field is required.</label>');
				var readyState = xhr.readyState;
				xhr.abort();
				// If readyState is below 2, abort() has no effect:
				if (isNaN(readyState) || readyState < 2) {
					handler.onAbort(event, files, index, xhr, handler);
				}
				$().notify(optionsLoc.alertKO, "Devi inserire il nome della facility");
			}
		},
		previewSelector: ".image_preview",
		imageTypes: '/^image\/(gif|jpeg|png)$/'
	});
	$(".btn_delete").button({
		icons: {
			primary: "ui-icon-trash"
		}
	}).click(function (event) {
		event.preventDefault();
		if (confirm("Do you REALLY want to delete it?")) {
			$(this).parents(".yform").submitForm();
		}
	});; /* describe editing handler */
	$(".describe_edit").toggle(function () {
		var prova = $(this).siblings(":input");
		$(this).siblings("input:text").removeClass("describe").attr('readonly', false);
	}, function () {
		$(this).siblings(":input").addClass("describe").attr('readonly', true);
	}); /* hide number of rooms  for default... */
	$(".num_of_rooms").hide();
	$('input[name="several_rooms"]').click(function () {
		if ($(this).is(":checked")) {
			$(".num_of_rooms").show();
			$(this).attr("checked", "checked");
		} else {
			$(".num_of_rooms").hide();
			$(this).removeAttr('checked');
		}
	});
	$('input[name="room_name"]').focus(function () {
		var edited_class = "edited_room_name";
		if (!$(this).hasClass(edited_class)) {
			$(this).val("");
			$(this).addClass(edited_class);
			$(this).effect("highlight", {}, 3000);
		}
		$(this).effect("pulsate", {
			times: 3
		}, 1000);
	});
	//---  END ADD ROOMS SECTION CODE  
	//---  SEASONS SECTION CODE   
	$(".btn_season").button({
		icons: {
			primary: "ui-icon-circle-plus"
		}
	});
	$(".btn_save_all").button({
		icons: {
			primary: "ui-icon-check"
		}
	}); /* Hide/Show season name change */
	$(".btn_season").click(function (event) {
		event.preventDefault();
		$(this).parents(".yform").submitForm();
	});
	$(".rename_season").toggle(function () {
		$(this).siblings('input[name="season.name"]').focus().css("border", "1px solid").removeAttr("readonly");
	}, function () {
		$('input[name="season.name"]').css("border", "none").attr("readonly", "true");
	}); /* Add period management */
	$(".add_period").click(function () {
		//count the number of periods already added
		var formParent = $(this).parents(".subcolumns");
		var num_of_periods = formParent.siblings(".subcolumns.period").size();
		// get last subcolumns
		var dd = formParent.siblings(".subcolumns:last");
		// setup of cloned row to add
		var added = $("#to_add_period").clone().insertAfter(dd).removeAttr("id").show();
		added.html(added.html().replace(/__PVALUE__/ig, num_of_periods));
		// attach listener to cloned row
		// attach erase click
		added.find(".erase_period").click(function () {
			$(this).closest(".subcolumns").remove();
		});
		$(".rename_season").toggle(function () {
			$(this).siblings('input[name="season.name"]').focus().css("border", "1px solid").removeAttr("readonly");
		}, function () {
			$('input[name="season.name"]').css("border", "none").attr("readonly", "true");
		});
		// attack datepickers
		added.find(".adddatepicker").removeClass('hasDatepicker').datepicker("destroy");
		added.find(".adddatepicker").datepicker({
			showOn: "button",
			buttonImage: "images/calendar.gif",
			buttonImageOnly: true,
			dateFormat: I18NSettings.datePattern
		});
		return false;
	});
	$(".erase_period").click(function () {
		$(this).closest(".subcolumns").remove();
	});
	//---  END SEASONS SECTION CODE  
	//---  GUESTS SECTION CODE   

	$(".btn_add_form").button({
		icons: {
			primary: "ui-icon-circle-plus"
		}
	}).click(function () {
		$("div.hideform").show("slide", {
			direction: "up"
		}, 1000);
		$(this).hide();
		return false;
	});


	//---  DETAILS SECTION CODE   
	var addEventDeleteImage = function (event) {
			event.preventDefault();
			var $this = $(event.currentTarget);
			//--  $(this).closest("." + "guest" + "_row").remove();
			var urlAction = $this.attr("href");
			if (confirm("Do you REALLY want to delete it?")) {
				$.ajax({
					type: 'POST',
					url: urlAction,
					success: function (data_action) {
						var title_notification = null;
						if (data_action.result == "success") {
							$().notify($.i18n("congratulation"), data_action.description);
							$this.parents("li").remove();
						} else if (data_action.result == "error") {
							$().notify($.i18n("warning"), data_action.description);
						} else {
							$(".validationErrors").html(data_action);
						}
					},
					error: function () {
						$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
					}
				});
			}
		};
	$("a.erase_image").click(function (event) {
		addEventDeleteImage(event)
	});
	$('a[id*=toggle]').click(function () {
		if ($(this).hasClass('active') === true) {
			$('a').removeClass('active');
			$('body').removeAttr('class');
		} else {
			$('a').removeClass('active');
			$('body').removeAttr('class').addClass($(this).text());
			$(this).addClass('active');
		}
	});
	$('a#formReset').click(function () {
		$('a').removeClass('active');
		$('body').removeAttr('class');
		$(this).addClass('active');
	}); /* Hide/Show password change */
	$("#change_pwd").toggle(function () {
		$(".chng_pwd").show();
	}, function () {
		$(".chng_pwd").hide();
	});
	//---  END DETAILS SECTION CODE  
	//---  CUSTOMER SECTION CODE   
	//---  END CUSTOMER SECTION CODE  
	//---  BOOK SECTION CODE   
	$.ajaxSetup({
		beforeSend: function (xhr) {
			if (xhr.overrideMimeType) {
				xhr.overrideMimeType("application/json");
			}
		},
		cache: false
	});

	var cache = {},
		lastXhr;
	$('input[name="fullname"]').autocomplete({
		minLength: 2,
		source: function (request, response) {
			var term = request.term;
			if (term in cache) {
				response(cache[term]);
				return;
			}
			lastXhr = $.getJSON("customer.json", request, function (data, status, xhr) {
				cache[term] = data.customers;
				if (xhr === lastXhr) {
					response(data.customers);
				}
			});
		}
	}); 
	//---  END BOOK SECTION CODE  
	//---  EMAIL SECTION CODE   
	/* Hide/Show usable parameters */
	$("#show_usable").toggle(function () {
		$(".list_usable").show();
	}, function () {
		$(".list_usable").hide();
	});
	//---  END EMAIL SECTION CODE  
	//---  ACCOMODATION SECTION CODE    
	
	$(".btn_add_facility_room").button({
		icons: {
			primary: "ui-icon-circle-plus"
		}
	}).click(function (event) {
		//-- event.preventDefault();
		var url_action_facility = "goRoomFacilities_edit";
		var id_room = $(this).parents(".yform").find('input:hidden[name="room.id"]').val();
		var name_room = $(this).parents(".yform").find('input:text[name="room.name"]').val();
		$.ajax({
			type: 'POST',
			url: url_action_facility,
			data: {
				idRoom: id_room
			},
			success: function (data) {
				$("#facility_edit_dialog").html(data);
				$("#facility_edit_dialog").dialog({
					title: "Add Facility for room: " + name_room,
					modal: true,
					buttons: {
						"Save": function () {
							$(this).find(".yform.json").submitForm();
							$(this).dialog("close");
						},
						cancel: function () {
							$(this).dialog("close");
						}
					}
				});
			},
			error: function () {
				$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
			}
		});
		return false;
	});
	$(".btn_delete_room").click(function (event) {

		$(this).parents(".yform").attr("action", "deleteRoom.action");
	});
	//button click handler for delete all rooms
	$(".btn_save_all_rooms").click(function (event) {
		var allRedirectInputs = $(".yform.json").find('input:hidden[name="redirect_form"]');
		var redirectOld = allRedirectInputs.last().val();
		allRedirectInputs.val("");
		$(".yform.json").submit();
		allRedirectInputs.val(redirectOld);
	});
	$(".min_stay").click(function () {
		if ($(this).hasClass("clickable")) {
			$(".price_show").addClass("clickable");
			$(this).removeClass("clickable");
			$(".price_type").hide();
		}
	});
	$(".price_show").click(function () {
		if ($(this).hasClass("clickable")) {
			$(".min_stay").addClass("clickable");
			$(this).removeClass("clickable");
			$(".price_type").show();
		}
	});
	$(".btn_add_new").button({
		icons: {
			primary: "ui-icon-circle-plus"
		}
	}).click(function () {
		window.location.href = "goAddNewRoom.action?sect=accomodation";
		return false;
	});
	$("#roomType").change(function () {
		var selectedId = $(this).find(":selected").val();
		if (typeof parseInt(selectedId) == "number" && parseInt(selectedId) > 0) {
			var url_table = "findRoomTypesForRoom.action?room.roomType.id=" + selectedId;
			$.ajax({
				url: url_table,
				context: document.body,
				dataType: "html",
				success: function (data) {
					$(".wrapper-facility").empty();
					$(".wrapper-facility").append(data);
				},
				error: function (request, state, errors) {
					$().notify($.i18n("warning"), "Problema restituzione lista Facility delle room types...");
				}
			});
		}
	});
	
	$(".accomodation_tree").jstree({
		"core": {
			"initially_open": ["root"]
		},
		"json_data": {
			"ajax": {
				"url": "findAllTreeRoomsJson.action"
			}
		},
		"themes": {
			"theme": "default",
			"dots": true,
			"icons": true
		},
		"plugins": ["themes", "json_data"]
	});
	
	
	//---  END ACCOMODATION SECTION CODE   
	//---  ONLINE BOOKINGS SECTION CODE
	$("#customizewidget").click(function () {
		$("#text-widget-edit").dialog({
			close: function (event, ui) {},
			buttons: {
				"Ok": function () {
					$("#text-widget").text($(this).find("textarea").val());
					$(this).dialog("close");
				},
				"Cancel": function () {
					$(this).dialog("close");
				}
			}
		});
	});
	//---  END ONLINE BOOKINGS SECTION CODE
	//---  PRICE LISTS SECTION CODE
	$(".room_tree, .extra_tree").bind("loaded.jstree", function (event, data) {
		$(".jstree-leaf").click(function (event) {
			event.preventDefault();
			//$(".room_tree").jstree("refresh");
			//$(".room_tree").jstree("refresh", $(".jstree-clicked"));
			//$(this).refresh();
			var url_table = $("a", this).attr("href");
			$.ajax({
				url: url_table,
				context: document.body,
				dataType: "html",
				success: function (data) {
					$(".priceList_table > tbody").html(data);
				},
				error: function (request, state, errors) {
					$().notify($.i18n("warning"), "Problema restituzione lista...");
				}
			});
			$("#priceList_buttons").hide();
			$("#priceList_buttons").html('<button class="btn_save">SAVE</button>' + '<button class="btn_reset">CANCEL</button>');
			$(".btn_save").button({
				icons: {
					primary: "ui-icon-check"
				}
			});
			$(".btn_reset").button({
				icons: {
					primary: "ui-icon-trash"
				}
			}).click(function (event) {
				event.preventDefault();
				var validator = $(this).parents(".yform.json").validate();
				validator.resetForm();
			});
			$("#priceList_edit").toggle(function () {
				$("#priceList_form").find("input").removeClass("noBorder");
				$("#priceList_form").find("input").removeAttr('readonly', 'readonly');
				$("#priceList_buttons").show();
			}, function () {
				$("#priceList_form").find("input").addClass("noBorder");
				$("#priceList_form").find("input").attr('readonly', 'readonly');
				$("#priceList_buttons").hide();
			});
		});
	});
	$(".room_tree").jstree({
		"core": {
			"initially_open": ["root"]
		},
		"json_data": {
			"ajax": {
				"url": "findAllRoomPriceLists.action"
			}
		},
		"themes": {
			"theme": "default",
			"dots": true,
			"icons": true
		},
		"plugins": ["themes", "json_data"]
	});
	$(".extra_tree").jstree({
		"core": {
			"initially_open": ["root"]
		},
		"json_data": {
			"ajax": {
				"url": "findAllExtraPriceLists.action"
			}
		},
		"themes": {
			"theme": "default",
			"dots": true,
			"icons": true
		},
		"plugins": ["themes", "json_data"]
	});
	//---  END PRICE LISTS SECTION CODE 	
});