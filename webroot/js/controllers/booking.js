$(function () {
    $.Class.extend('Booking', /* @static */ { /* update subtotal */
        updateSubtotal: function () {
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
                Booking.updateBalance();
            }
            catch (e) {
                //nothing for now -- problema nei selettori
            }
        },
        /* update balance due */
        updateBalance: function () {
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
            }
            catch (e) {
                //nothing for now -- problema nei selettori
            }
        },
        days_between_signed: function (date1, date2) {
            // The number of milliseconds in one day
            var ONE_DAY = 1000 * 60 * 60 * 24;
            // Convert both dates to milliseconds
            var date1_ms = date1.getTime();
            var date2_ms = date2.getTime();
            // Calculate the difference in milliseconds
            var difference_ms = date1_ms - date2_ms;
            // Convert back to days and return
            return Math.round(difference_ms / ONE_DAY);
        }
    }, /* @prototype */ {
        init: function (lang, patternDate) {
            this.alertOK = $.i18n("congratulation");
            this.alertKO = $.i18n("warning");
            var ONE_DAY = 1000 * 60 * 60 * 24; /* booking section initialization */
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
                        numNights = Booking.days_between_signed(otherData, selectedData);
                        if (numNights == 0) $().notify(this.alertKO, $.i18n("dateInVsdateOut"));
                    }
                    $("#booking_duration").val(numNights);
                }
            });
            
            Guest.getCustomers("input[name='booking.booker.lastName']"); 
            
            
            
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
                                            }
                                            else if (data_action.result == "error") {
                                                $().notify($.i18n("warning"), data_action.description);
                                            }
                                            else {
                                                $(".validationErrors").html(data_action);
                                            }
                                        },
                                        error: function () {
                                            $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
                                        }
                                    });
                                });
                                //END ADDING CHECKOUT LISTENER
                            }
                            else if (data_action.result == "error") {
                                $().notify($.i18n("warning"), data_action.description);
                            }
                            else {
                                $(".validationErrors").html(data_action);
                            }
                        },
                        error: function () {
                            $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
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
                        }
                        else if (data_action.result == "error") {
                            $().notify($.i18n("warning"), data_action.description);
                        }
                        else {
                            $(".validationErrors").html(data_action);
                        }
                    },
                    error: function () {
                        $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
                    }
                });
            }); /* extras adding */
            /* adjustment and payments*/
            $.fn.getSelector = function () {
                var selector = "";
                if ($(this).attr("class").indexOf("adjustment") >= 0) selector = "adjustment";
                else selector = "payment";
                return selector;
            };
            
            jQuery.validator.addMethod("validPrice", function(value, element) { 
            	  return this.optional(element) || /^[-+]?[0-9]+[.]?[0-9]*([eE][-+]?[0-9]+)?$/.test(value); 
            	  
            	}, $.i18n("validPriceAlert"));
            

         
            $(".extra_value_adjustment, .extra_value_payment").keyup(function () { /* prepare selector string for class whit whitespaces */
                //-- var current_class_selector = $(this).attr("class").replace( new RegExp(" ","g"), ".");
                /* adjust subtotal ... */
                var new_subtotal = null;
                if ($(this).valid()) {
                    if ($(this).getSelector() == "adjustment") {
                        Booking.updateSubtotal(); /* end code for subtotal calculation */
                    }
                    else {
                        Booking.updateBalance();
                    }
                }
                else {
                    //$(this).val('');
                    Booking.updateSubtotal();
                }
                //--- $(this).unbind('keyup');
            });
            $(".erase_adjustment, .erase_payment").click(function () {
                var selector = $(this).getSelector();
                $(this).parents("." + selector + "_row").find(".extra_value_" + selector + "").val(0);
                Booking.updateSubtotal();
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
                    Booking.updateSubtotal();
                    $(this).closest("." + selector + "_row").remove();
                });
                added.find(".extra_value_" + selector + "").keyup(function () { 
                	/* prepare selector string for class whit whitespaces */
                    var current_class_selector = $(this).attr("class").replace(new RegExp(" ", "g"), ".");
                    /* adjust subtotal ... */
                    var new_subtotal = null;
                    if ($(this).valid()) {
                        if ( $(this).getSelector() == "adjustment") {
                            Booking.updateSubtotal(); /* end code for subtotal calculation */
                        }
                        else {
                            Booking.updateBalance();
                        }
                    }
                    else {
                       // $(this).val('');
                        Booking.updateSubtotal();
                    }
                });
            });
            
            /*
            $('input[name="pay_value_adjustment[]"]').keyup(function () {
                var current_parent = $(this).parents(".type-text"); 
                // prepare selector string for class whit whitespaces 
                var current_class_selector = $(this).attr("class").replace(new RegExp(" ", "g"), "."); 
                // check if current was cloned 
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
                } 
                // adjust subtotal ... 
                var new_subtotal = null;
                if (current_class_selector.indexOf("extra_value_adjustment") >= 0) {
                    new_subtotal = parseInt($("#subtotal_room").val());
                    new_balance = parseInt($("#balance_room").val());
                     // code for calcute new subtotal
                    $("." + current_class_selector).each(function (key, value) {
                        if ($(value).valid()) {
                            new_subtotal = new_subtotal + parseInt($(value).val());
                        }
                    });
                    // show new subtotal value
                    Booking.updateSubtotal(); 
                    // end code for subtotal calculation
                }
                else {
                    Booking.updateBalance();
                }
            });

            */
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
                if (num_of_items >= numbermaxGuests){
                	
                	$().notify($.i18n("warning"), $.i18n("nrGuestVsMaxGuest") );
                }
                var added = $("#to_add_" + selector + "").clone().insertAfter(dd).removeAttr("id").show();
                added.html(added.html().replace(/__PVALUE__/ig, num_of_items));
                // attach listener to cloned row
                // attach erase click listener
                added.find(".erase_" + selector + "").click(function () {
                    $(this).closest("." + selector + "_row").remove();
                });
            }); /*ADD LISTENER FOR CHANGE ROOM OR DATEIN OR DATEOUT OR NUMNIGHTS FROM BOOKING*/
            $('#sel_rooms_list, #booking_duration, input:text[name="booking.dateIn"], input:text[name="booking.dateOut"], input:checkbox[name="bookingExtraIds"], #nr_guests, #convention, .quantity').change(function () {
                // check in room was selected
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                    $().notify($.i18n("warning"), $.i18n("roomRequired"));
                    return;
                }
                var formInput = $(this).parents().find(".yform.json").serialize();
                var $clicked = $(this);
                $.ajax({
                    type: "POST",
                    url: "updateBookingInMemory.action",
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
								if ($clicked.is('input:checkbox[name="bookingExtraIds"]')) {
                                    if($clicked.is(":checked")) {
										var extraID = $('input:checkbox[name="bookingExtraIds"]').attr("value");
										if (extraID == $('.quantity').attr("id")) {
											$('.extraQuantity #' + extraID).show();
										}
									}
									else {
										$('.extraQuantity #' + extraID).hide();
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
                                Booking.updateSubtotal();
                                //update subtotal
                                //  updateSubtotal();
                                //$().notify("Congratulazioni", data_action.message.description, _redirectAction);
                            }
                            else if (data_action.message.result == "error") {
                                $().notify($.i18n("warning"), data_action.message.description);
                            }
                            else {
                                $(".validationErrors").html(data_action);
                            }
                        }
                        catch (e) {
                            //an error in data returned...
                            $(".validationErrors").html(data_action);
                        }
                    },
                    error: function () {
                        $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
                    }
                });
            });
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
        }
        //---  END BOOK SECTION CODE  
    });
    
    new Booking(I18NSettings.lang, I18NSettings.datePattern);
});