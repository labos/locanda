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
     * @class Booking
     * @parent Class
     * @constructor
     * Create or edit a booking.
     * @tag controllers
     * @author LabOpenSource
     */
    $.Class.extend('Controllers.Booking', /* @static */ { 
    	/* update subtotal */
        updateSubtotal: function () {
            var subtotal = 0,
            	payments = ["#extras_room", "input.extra_value_adjustment", '#price_room'];
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
                // permanently update the subtotal
                $(".subtotal_room").text(subtotal);
                Controllers.Booking.updateBalance();
            } catch (e) {
                //nothing for now -- selector exceptions management
            }
        },
        /* update balance due */
        updateBalance: function () {
            var subDue = 0,
            	due = ".extra_value_payment";
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
                //permanently update balance
                var subtotal = isNaN(parseInt($(".subtotal_room").text())) ? 0 : parseInt($(".subtotal_room").text());
                var balanceDue = subtotal - subDue;
                $("#balance_room").val(balanceDue);
                $(".balance_room").html(balanceDue);
            } catch (e) {
                //nothing for now -- 
            }
        },
        /*
         * Calculate.
         * @param {Object} a calendar event object.
         * @param {Object} an Jquery Dom object .
         */
        days_between_signed: function (date1, date2) {
            // The number of milliseconds in one day
            var ONE_DAY = 1000 * 60 * 60 * 24;
            // Convert both dates to milliseconds
            var date1_ms = date1.getTime(),
            	date2_ms = date2.getTime();
            // Calculate the difference in milliseconds
            var difference_ms = date1_ms - date2_ms;
            // Convert back to days and return
            return Math.round(difference_ms / ONE_DAY);
        }
    },
    /* @prototype */ {
        init: function (lang, patternDate) {
            var self = this,
            ONE_DAY = 1000 * 60 * 60 * 24;
            this.guest = new Controllers.Guest();
            this.alertOK = $.i18n("congratulation");
            this.alertKO = $.i18n("warning");
           
            /* booking section initialization */
            $(".datepicker").datepicker({
                showOn: "button",
                buttonImage: "images/calendar.gif",
                buttonImageOnly: true,
                dateFormat: patternDate,
                onClose: function (dateText, inst) {
                    var numNights = 0;
                    var closerDateInput = $(".datepicker").not($(this));
                    var otherData = closerDateInput.datepicker("getDate");
                    
                    var selectedData = $(this).datepicker("getDate");
                    if (selectedData && otherData) {
                        numNights = Controllers.Booking.days_between_signed(otherData, selectedData);
                        if (numNights == 0) $().notify(this.alertKO, $.i18n("dateInVsdateOut"));
                    }
                    $("#booking_duration").val(numNights);
                }
            });
            this.guest.getCustomers("input[name='booking.booker.lastName']", null);
            $(".btn_checked").button({
                disabled: true
            });
            $(".canc_booking").button({
                icons: {
                    primary: "ui-icon-trash"
                }
            }); 
            /* adjustment and payments*/
            $.fn.getSelector = function () {
                var selector = "";
                if ($(this).attr("class").indexOf("adjustment") >= 0) selector = "adjustment";
                else selector = "payment";
                return selector;
            };
            
            // set decimal format validation
            jQuery.validator.addMethod("validPrice", function (value, element) {
                return this.optional(element) || /^[-+]?[0-9]+[.]?[0-9]*([eE][-+]?[0-9]+)?$/.test(value);
            }, $.i18n("validPriceAlert"));
            $(".extra_value_adjustment, .extra_value_payment").keyup(function () { 
            	/* prepare selector string for class with whitespaces and adjust subtotal or balance*/
                var new_subtotal = null;
                if ($(this).valid()) {
                    if ($(this).getSelector() == "adjustment") {
                        Controllers.Booking.updateSubtotal(); /* end code for subtotal calculation */
                    } else {
                        Controllers.Booking.updateBalance();
                    }
                } else {
                    
                	Controllers.Booking.updateSubtotal();
                }
               
            });
            
            // bind onclick erase adjustment/payment
            $(".erase_adjustment, .erase_payment").click(function () {
                var selector = $(this).getSelector();
                $(this).parents("." + selector + "_row").find(".extra_value_" + selector + "").val(0);
                Controllers.Booking.updateSubtotal();
                $(this).closest("." + selector + "_row").remove();
            });
            
            //bind onclick add adjustment/payment
            $(".add_adjustment, .add_payment").click(function () {
                var selector = $(this).getSelector();
                //count the number of periods already added
                var formParent = $(this).parents(".type-text");
                var num_of_items = (selector == 'adjustment') ? formParent.siblings(".adjustment_row").size() : formParent.siblings(".payment_row").size();
                // get last subcolumns
                var dd = formParent.siblings("." + selector + "_row:last").length ? formParent.siblings("." + selector + "_row:last") : formParent;
                // setup of cloned row to add
                var added = $("#to_add_" + selector + "").clone().insertAfter(dd).removeAttr("id").show();
                added.html(added.html().replace(/__PVALUE__/ig, num_of_items));
                // attach listener to cloned row
                // attach erase click
                added.find(".erase_" + selector + "").click(function () {
                    $(this).parents("." + selector + "_row").find(".extra_value_" + selector + "").val(0);
                    Controllers.Booking.updateSubtotal();
                    $(this).closest("." + selector + "_row").remove();
                });
                added.find(".extra_value_" + selector + "").keyup(function () { 
                	/* prepare selector string for class whit whitespaces */
                    var current_class_selector = $(this).attr("class").replace(new RegExp(" ", "g"), "."); 
                    /* adjust subtotal ... */
                    var new_subtotal = null;
                    if ($(this).valid()) {
                        if ($(this).getSelector() == "adjustment") {
                        	Controllers.Booking.updateSubtotal(); 
                        } else {
                        	Controllers.Booking.updateBalance();
                        }
                    } else {
                        
                    	Controllers.Booking.updateSubtotal();
                    }
                });
            });
            //update of dateOut changing num of nights.
            $("select[name='numNights']").change(function () {
                $('input[name="booking.dateIn"]').rules("add", {
                    required: true
                });
                var dateOut = '',
                	numNights = $(this).find(":selected").val(),
               		dateInVal = $('input[name="booking.dateIn"]').datepicker('getDate'),
               		$dateInDom = $('input[name="booking.dateIn"]');
                if (dateInVal !== '' && $dateInDom.valid()) {
                    var dateInDate = new Date(dateInVal),
                    	millisO = dateInDate.getTime(),
                    	dateOutDateMill = dateInDate.getTime() + (ONE_DAY * numNights),
                    	dateOutDate = new Date(dateOutDateMill);
                    dateOut = $.datepicker.formatDate(I18NSettings.datePattern, dateOutDate);
                }
                $('input[name="booking.dateOut"]').datepicker("setDate", dateOut);
            });
            $(".erase_guest").click(function () {
                $(this).closest("." + "guest" + "_row").remove();
            });
            $(".add_guest").click(function () {
                //count the number max of guests to select
                var $formParent = $(".guests-select"),
                	max = 0;
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
                if (num_of_items >= numbermaxGuests) {
                    $().notify($.i18n("warning"), $.i18n("nrGuestVsMaxGuest"));
                }
                var added = $("#to_add_" + selector + "").clone().insertAfter(dd).removeAttr("id").show();
                added.html(added.html().replace(/__PVALUE__/ig, num_of_items));
                // attach listener to cloned row
                // attach erase click listener
                added.find(".erase_" + selector + "").click(function () {
                    $(this).closest("." + selector + "_row").remove();
                });
            });
            
            /*ADD LISTENER FOR CHANGE ROOM OR DATEIN OR DATEOUT OR NUM NIGHTS FROM BOOKING*/
            $.fn.eventExtraChange = function () {
                $(this).focus(function () {
                    // Store the current value on focus, before it changes
                    $(this).data("prevExtraValue", $(this).val());
                    $(this).is(":checked") ? $(this).data("prevExtraValue", true) : $(this).data("prevExtraValue", false);
                }).change(function (event) {
                    var clicked = this;
                    // check if room was selected
                    if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                        $().notify($.i18n("warning"), $.i18n("roomRequired"));
                        return;
                    }
                    var formInput = $(clicked).parents().find(".yform.json").serialize();
                    $.ajax({
                        type: 'POST',
                        url: "checkBookingDatesNotNull.action",
                        data: formInput,
                        success: function (data_action) {
                            if (data_action.result == "success") {
                                self.calculatePrice(clicked, 'updateExtras.action');
                            } else if (data_action.result == "error") {
                                event.preventDefault();
                                var validator = $(clicked).parents(".yform.json").validate();
                                if ($(clicked).data("prevExtraValue") == true) {
                                    $(clicked).attr("checked", "checked");
                                } else {
                                    $(clicked).removeAttr('checked');
                                }
                                $().notify($.i18n("warning"), data_action.description);
                            } else {
                                event.preventDefault();
                                var validator = $(clicked).parents(".yform.json").validate();
                                validator.resetForm();
                                $(".validationErrors").html($.i18n("bookingOverlapping"));
                            }
                        },
                        error: function () {
                            event.preventDefault();
                            var validator = $(clicked).parents(".yform.json").validate();
                            $().notify($.i18n("warning"), $.i18n("bookingOverlapping"));
                        },
                        dataType: 'json'
                    });
                });
            };
            
            // bind on extras changes event
            $('input:checkbox[name="bookingExtraIds"], .quantity').eventExtraChange();
            $('#sel_rooms_list').change(function () {
                // check in room was selected
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                    $().notify($.i18n("warning"), $.i18n("roomRequired"));
                    return;
                }
                self.calculatePrice(this, 'updateRoom.action');
            });
            
            // store previous date on focus in datepickers
            $('#booking_duration, input:text[name="booking.dateIn"], input:text[name="booking.dateOut"]').focus(function () {
                // Store the current value on focus, before it changes
                $(this).data("prevDate", $(this).val());
            }).change(function (event) {
                // check in room was selected
                // save current dom that raieses a change event
                var clicked = this;
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                    $().notify($.i18n("warning"), $.i18n("roomRequired"));
                    return;
                }
                var formInput = $(clicked).parents().find(".yform.json").serialize();
                $.ajax({
                    type: 'POST',
                    url: "checkBookingDates.action",
                    data: formInput,
                    success: function (data_action) {
                        if (data_action.result == "success") {
                            self.calculatePrice(clicked, 'updateBookingDates.action');
                        } else if (data_action.result == "error") {
                            event.preventDefault();
                            var validator = $(clicked).parents(".yform.json").validate();
                            // check if previous date is null
                            $(clicked).val($(clicked).data("prevDate"));
                            $().notify($.i18n("warning"), data_action.description);
                        } else {
                            event.preventDefault();
                            var validator = $(clicked).parents(".yform.json").validate();
                            validator.resetForm();
                            $(".validationErrors").html($.i18n("bookingOverlapping"));
                        }
                    },
                    error: function () {
                        event.preventDefault();
                        var validator = $(clicked).parents(".yform.json").validate();
                        $().notify($.i18n("warning"), $.i18n("bookingOverlapping"));
                    },
                    dataType: 'json'
                });
            });
            $('#nr_guests').live('change', function () {
                // check in room was selected
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                    $().notify($.i18n("warning"), $.i18n("roomRequired"));
                    return;
                }
                self.calculatePrice(this, 'updateNrGuests.action');
            });
            $('#convention').change(function () {
                // check in room was selected
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                    $().notify($.i18n("warning"), $.i18n("roomRequired"));
                    return;
                }
                self.calculatePrice(this, 'updateConvention.action');
            });
        },
        /**
         * Get new prices for booking.
         * @param {String} selector (an html class name or an id value ) .
         * @param {String} action url to call for booking json retrieval.
         */
        calculatePrice: function (clicked, urlValue) {
            var self = this;
            var formInput = $(clicked).parents().find(".yform.json").serialize();
            var $clicked = $(clicked);
            var urlAction = urlValue || null;
            if (urlAction) {
                $.ajax({
                    type: "POST",
                    url: urlAction,
                    data: formInput,
                    success: function (data_action) {
                        var title_notification = null;
                        try {
                            if (data_action.message.result == "success") {
                                //update dom values here
                                var roomSubTotal = data_action.booking.roomSubtotal;
                                var extraSubTotal = data_action.booking.extraSubtotal;
                                var numNights = data_action.numNights;
                                var listNumGuests = data_action.listNumGuests;
                                var nrGuests = data_action.booking.nrGuests;
                                var priceRoom = 0;
                                var subTotal = roomSubTotal + extraSubTotal;
                                var maxGuests = data_action.booking.room.roomType.maxGuests;
                                var extraCheckBoxNumber = $('input:checkbox[name="bookingExtraIds"]').length;
                                for (var extraID = 1; extraID <= extraCheckBoxNumber; extraID++) {
                                    var currentCheckbox = 'input:checkbox#' + extraID + '_extraCheckBox';
                                    if ($clicked.is(currentCheckbox)) {
                                        if ($('select#' + extraID + '_extraQuantity').length != 0) {
                                            if ($clicked.is(":checked")) {
                                                $('select#' + extraID + '_extraQuantity').show();
                                            } else {
                                                $('select#' + extraID + '_extraQuantity').hide();
                                            }
                                        } else {
                                            self.displayQuantitySelect($('input:checkbox[name="bookingExtraIds"]'));
                                        }
                                    }
                                }
                                $('input:checkbox[name="bookingExtraIds"]').each(function () {
                                    var extraID = $(this).val();
                                    var currentCheckbox = 'input:checkbox#' + extraID + '_extraCheckBox';
                                    if ($clicked.is(currentCheckbox)) {
                                        if ($('select#' + extraID + '_extraQuantity').length != 0) {
                                            if ($clicked.is(":checked")) {
                                                $('select#' + extraID + '_extraQuantity').show();
                                            } else {
                                                $('select#' + extraID + '_extraQuantity').hide();
                                            }
                                        } else {
                                            self.displayQuantitySelect($('input:checkbox[name="bookingExtraIds"]'));
                                        }
                                    }
                                });
                                if (maxGuests !== null && parseInt(maxGuests) > 0 && ($clicked.is("select#sel_rooms_list"))) {
                                    var $dd = $(".guests-select");
                                    // number of guests variation
                                    var added = new EJS({
                                        url: 'js/views/booking/listNumGuests.ejs'
                                    }).render({
                                        lNumGuests: listNumGuests,
                                        nGuests: nrGuests,
                                        labels: {
                                            labelNrGuests: $.i18n("guests")
                                        }
                                    });
                                    $dd.html(added);
                                }
                                if (($clicked.is("select#booking_duration") || $clicked.is("select#nr_guests"))) {
                                    self.displayQuantitySelect($('input:checkbox[name="bookingExtraIds"]'));
                                }
                                $("#price_room").html(roomSubTotal);
                                $("#extras_room").html(extraSubTotal);
                                $('input:hidden[name="booking.subtotal"]').val(subTotal);
                                $("span.subtotal_room").text(subTotal);
                                $("span.balance_room").text(subTotal);
                                $("#booking_duration").val(numNights);
                                Booking.updateSubtotal();
     
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
                        $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescr"));
                    }
                });
            } //end ajax calling code
        },
        
        /**
         * Get renewed select for extras.
         * @param {String} selector (an html class name or an id value ) .
         */
        displayQuantitySelect: function (clicked) {
            var formInput = $(clicked).parents().find(".yform.json").serialize();
            var $clicked = $(clicked);
            var self = this;
            $.ajax({
                url: "displayQuantitySelect.action",
                type: "POST",
                context: document.body,
                dataType: "html",
                data: formInput,
                success: function (data_action) {
                    $(".type-select.extraCheckList").html(data_action);
                    $('input:checkbox[name="bookingExtraIds"], .quantity').eventExtraChange();
                },
                error: function () {
                    $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescr"));
                }
            });
        }
        //---  END BOOK SECTION CODE  
    });
    new Controllers.Booking(I18NSettings.lang, I18NSettings.datePattern);
});
