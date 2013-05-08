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
                        subtotal += isNaN(parseFloat(value_contained)) ? 0 : parseFloat(value_contained);
                    } //else if an array of doms was selected by selector
                    else {
                        $(value).each(function (k, v) {
                            var value_contained = $(v).is('input') ? $(v).val() : $(v).text();
                            subtotal += isNaN(parseFloat(value_contained)) ? 0 : parseFloat(value_contained);
                        });
                    }
                });
                // permanently update the subtotal
                $(".subtotal_room").text(subtotal.toFixed(2));
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
                    subDue += isNaN(parseFloat(value_contained)) ? 0 : parseFloat(value_contained);
                } //else if an array of doms was selected by selector
                else {
                    $(due).each(function (k, v) {
                        var value_contained = $(v).is('input') ? $(v).val() : $(v).text();
                        subDue += isNaN(parseFloat(value_contained)) ? 0 : parseFloat(value_contained);
                    });
                }
                //permanently update balance
                var subtotal = isNaN(parseFloat($(".subtotal_room").text())) ? 0 : parseFloat($(".subtotal_room").text());
                var balanceDue = subtotal - subDue;
                $("#balance_room").val(balanceDue.toFixed(2));
                $(".balance_room").html(balanceDue.toFixed(2));
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
            var difference_ms = date2_ms - date1_ms;
            // Convert back to days and return
            return Math.round(difference_ms / ONE_DAY);
        }
    },
    /* @prototype */ {
        init: function (lang, patternDate) {
            var self = this,
            ONE_DAY = 1000 * 60 * 60 * 24;
            //this.guest = new Controllers.Guest();
            this.alertOK = $.i18n("congratulation");
            this.alertKO = $.i18n("warning");
            // Set variable to store if current booking form values are modified
            this.modified = false;
            /* booking section initialization */
            $(".datepicker").datepicker({
                showOn: "button",
                buttonImage: "images/calendar.gif",
                buttonImageOnly: true,
                dateFormat: patternDate,
                onClose: function (dateText, inst) {
                    var numNights = 0,
               		dateInVal = $('input[name="booking.dateIn"]').datepicker('getDate'),
               		dateOutVal = $('input[name="booking.dateOut"]').datepicker('getDate');
                    if (dateInVal && dateOutVal) {
                        numNights = Controllers.Booking.days_between_signed(dateInVal, dateOutVal);
                        if (numNights <= 0) $.jGrowl( $.i18n("dateInVsdateOut"), { theme: "notify-error", sticky: true  });
                    }

                  //--  $NumNights.val(numNights);
                }
            });
            //this.guest.getCustomers("input[name='booking.booker.lastName']", null);
            $(".btn_checked").button({
                disabled: true
            });
            $(".canc_booking").button({
                icons: {
                    primary: "ui-icon-trash"
                }
            }); 
            $(".invoice_booking").button({
                icons: {
                    primary: "ui-icon-arrowreturnthick-1-s"
                }
            }).click(function(event){
            	if (!confirm($.i18n("invoiceConfirm"))) {
            		 event.preventDefault();
            	}
            });
            
            
            
            /* Add shared submit event listener */
            $(".yform.json").submit(function (event) {
                $(this).submitForm();
                return false;
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
                	self.modified = true;
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
            	self.modified = true;
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
                	self.modified = true;
                    $(this).closest("." + selector + "_row").remove();
                });
                added.find(".extra_value_" + selector + "").keyup(function () { 
                	/* prepare selector string for class whit whitespaces */
                    var current_class_selector = $(this).attr("class").replace(new RegExp(" ", "g"), "."); 
                    /* adjust subtotal ... */
                    var new_subtotal = null;
                    if ($(this).valid()) {
                    	self.modified = true;
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
            
            // creditcard setting
            $('input[name="booking.creditCard.expYear"]').attr("min",new Date().getFullYear()).attr("max",new Date().getFullYear() + 100);
            
            //update of dateOut changing num of nights.
            $("select[name='numNights']").change(function () {
                $('input[name="booking.dateIn"]').rules("add", {
                    required: true
                });
                var dateOut = '',
                	numNights = $(this).find(":selected").val(),
               		dateInVal = $('input[name="booking.dateIn"]').datepicker('getDate'),
               		$dateInDom = $('input[name="booking.dateIn"]');
                $('input[name="booking.dateOut"]').data("prevDate", $('input[name="booking.dateOut"]').val());
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
					$.jGrowl($.i18n("nrGuestVsMaxGuest"), { theme: "notify-error", sticky: true  });
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
    					$.jGrowl($.i18n("roomRequired"), { theme: "notify-error", sticky: true  });
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
            					$.jGrowl(data_action.description, { theme: "notify-error", sticky: true  });

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
        					$.jGrowl($.i18n("bookingOverlapping"), { theme: "notify-error", sticky: true  });
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
					$.jGrowl( $.i18n("roomRequired"), { theme: "notify-error", sticky: true  });
                    return;
                }
                self.calculatePrice(this, 'updateRoom.action');
            });
            
            // store previous date on focus in datepickers
            $('#booking_duration, input:text[name="booking.dateIn"], input:text[name="booking.dateOut"]').focus(function () {
                // Store the current value on focus, before it changes
                $(this).data("prevDate", $(this).val());
                //store previous num nights
                $NumNights = $("#booking_duration");
                $NumNights.data("prevNumNights", $NumNights.val());
            }).change(function (event) {
                // check in room was selected
                // save current dom that raieses a change event
                var clicked = this;
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
					$.jGrowl( $.i18n("roomRequired"), { theme: "notify-error", sticky: true  });
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

                            if ((!$(clicked).is("select#booking_duration"))){
                                $(clicked).val($(clicked).data("prevDate"));        	
                            }else{
                                $('input:text[name="booking.dateOut"]').val($('input:text[name="booking.dateOut"]').data("prevDate")); 
                            }
                            
                            $("#booking_duration").val( $("#booking_duration").data("prevNumNights") );
        					$.jGrowl( data_action.description, { theme: "notify-error" });

                        } else {
                            event.preventDefault();
                            var validator = $(clicked).parents(".yform.json").validate();
                            validator.resetForm();
        					$.jGrowl( $.i18n("seriousErrorDescr"), { theme: "notify-error", sticky: true  });
                            //$(".validationErrors").html($.i18n("bookingOverlapping"));
                        }
                    },
                    error: function () {
                        event.preventDefault();
                        var validator = $(clicked).parents(".yform.json").validate();
    					$.jGrowl( $.i18n("bookingOverlapping"), { theme: "notify-error", sticky: true  });
                    },
                    dataType: 'json'
                });
            });
            $('#nr_guests').live('change', function () {
                // check in room was selected
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
                	 $.jGrowl( $.i18n("roomRequired"), { theme: "notify-error", sticky: true  });
                    return;
                }
                self.calculatePrice(this, 'updateNrGuests.action');
            });
            $('#convention').change(function () {
                // check in room was selected
                if (!(parseInt($('#sel_rooms_list').val()) > 0)) {
					$.jGrowl( $.i18n("roomRequired"), { theme: "notify-error", sticky: true  });
                    return;
                }
                self.calculatePrice(this, 'updateConvention.action');
            });
            
            $('#booking_status').change(function () {
            	self.modified = true;
            	
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
                            	self.modified = true;
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
                                    var $dd = $(".guests-select"),
                                    options ='';
                                    // number of guests variation
                                    for(var i = 0; i < maxGuests; i++){
                                    	options +='<option value="'+ listNumGuests[i] +'"' + 
                                            ((listNumGuests[i] == nrGuests)? 'selected="selected"' : '') +
                                             '>' + listNumGuests[i] + '</option>';
                                          
                                    }
                                    $dd.html('<label for="nr_guests">' + $.i18n("guests") + '</label><select id="nr_guests"  name="booking.nrGuests">'
                                    		+ options + '</select>' );

                                }
                                if (($clicked.is("select#booking_duration") || $clicked.is("select#nr_guests"))) {
                                    self.displayQuantitySelect($('input:checkbox[name="bookingExtraIds"]'));
                                }
                                $("#price_room").html(roomSubTotal);
                                $("#extras_room").html(extraSubTotal);
                                //--$('input:hidden[name="booking.subtotal"]').val(subTotal);
                                $("span.subtotal_room").text(subTotal);
                                $("span.balance_room").text(subTotal);
                                $("#booking_duration").val(numNights);
                                Controllers.Booking.updateSubtotal();
     
                            } else if (data_action.message.result == "error") {
        	                     $.jGrowl(data_action.message.description, {theme: "notify-error",sticky: true});
                            } else {
                                $(".validationErrors").html(data_action);
                            }
                        } catch (e) {
                            //an error in data returned...
   	                     	$.jGrowl($.i18n("seriousErrorDescr"), {theme: "notify-error",sticky: true});
                            //$(".validationErrors").html(data_action);
                        }
                    },
                    error: function () {
  	                     $.jGrowl($.i18n("seriousErrorDescr"), {theme: "notify-error",sticky: true});
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
	                     $.jGrowl($.i18n("seriousErrorDescr"), {theme: "notify-error",sticky: true});
                }
            });
        }
        //---  END BOOK SECTION CODE  
    });
    new Controllers.Booking(I18NSettings.lang, I18NSettings.datePattern);
});
