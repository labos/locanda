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
    $.Class.extend('Controllers.Guest', {
        init: function () {
            var self = this;
        	/* Buttons rendering and event handler attachments */
            $(".btn_g_search").button({
                icons: {
                    secondary: "ui-icon-arrowreturnthick-1-e"
                }
            });
            
            $(".btn_save_guest").button({
                icons: {
                    primary: "ui-icon-circle-plus"
                }
            });
            
            $(".btn_add_booking").button({
                icons: {
                    primary: "ui-icon-circle-plus"
                }
            });
            
            $(".btn_edit_guest").button({
                icons: {
                    primary: "ui-icon-check"
                }
            });
            $(".btn_delete_guest").click(function (event) {
                event.preventDefault();
                $(this).parents(".yform").submitForm("deleteGuest.action");
            });
            
            
            //add notify functionality as tooltip for input text
            $("#room_name_id, #max_guests_id, #price_room_id").mousedown(function () {
                $.jGrowl("close");
                $.jGrowl($(this).nextAll("span:hidden").text(), {
                    position: "top-left"
                });
            }).mouseout(function () {
                $.jGrowl("close");
            });
            
            /* Add autocomplete functionality to input text for search */
           this.getCustomers(".txt_guest_search", "findAllGuests" );
          
            
        },
        
        getCustomers : function (selector, onselectToDo) {
        	var self = this;
            var cache = {},
                lastXhr;
            var toDo = onselectToDo || null;
            $(selector).autocomplete({
                minLength: 2,
                source: function (request, response) {
                    var term = request.term;
                    if (term in cache) {
                        response(cache[term]);
                        return;
                    }
                    lastXhr = $.getJSON("findAllGuestsJson.action", request, function (data, status, xhr) {
                        //--cache[ term ] = data;
                        var result = new Array();
                        try {
                            $.each(data, function (key, value) {
                                result.push({
                                    "id": value.id,
                                    "label": value.firstName + ' ' +  value.lastName,
                                    "value": value.firstName + value.lastName
                                });
                            });
                        }
                        catch (e) {
                            //nothing. result is empty
                        }
                        cache[term] = result;
                        if (xhr === lastXhr) {
                            response(result);
                        }
                    });
                },
                select: function (event, ui) {
                    if (ui.item) {
                        if (toDo == "findAllGuests") {
                            var name = ui.item.value;
                            window.location.href = "findAllGuestsByName.action?sect=guests&term=" + name;
                        }
                        else {
                            $('input[name="booking.booker.id"]').val(ui.item.id);
                            //send an ajax call to guest details retrieving
                            Models.Guest.findOne({id: $('input[name="booking.booker.id"]').val()}, self.callback('findGuestByIdSuccess'),self.callback('findGuestByIdError'));

                        } //END ELSE tODO
                    }
                }
            });
            
            //end autocomplete
            
        },
        
        findGuestByIdSuccess: function(response){
            if (typeof response.message !== 'undefined' &&  response.message.result == "success") {
                $("#phone").val(response.guest.phone);
                $("#address").val(response.guest.address);
                $("#country").val(response.guest.country);
                $("#zipCode").val(response.guest.zipCode);
                $("#email").val(response.guest.email);
                $("#fname").val(response.guest.firstName);
                $("#lname").val(response.guest.lastName);
                $("#notes").val(response.guest.notes);
            }
            else {
                $().notify($.i18n("warning"), $.i18n("listGuestsRetrive"));
            }
        	
        	
        },
        findGuestByIdError: function(data){
            //if you cannot retrieve the list of rooms then...
            $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
        }
        
        
        
    });
    
    new Controllers.Guest();
});