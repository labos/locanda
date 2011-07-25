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
    //---  ROOM SECTION CODE    
    $.Class.extend('Controllers.Room', /* @prototype */ {
        init: function () { 
        	var self = this;
        	/* Buttons rendering and event handler attachments */
            $(".btn_add_new").button({
                icons: {
                    primary: "ui-icon-circle-plus"
                }
            }).click(function () {
                window.location.href = "goAddNewRoom.action?sect=accomodation";
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
            $("#roomType").change(function () {
                var selectedId = $(this).find(":selected").val();
                if (typeof parseInt(selectedId) == "number" && parseInt(selectedId) > 0) {
                	//create a Room instance 
                	var accomodationObj = new Models.Room();
                	//set some attributes of the model.
                	accomodationObj.attr("roomType",new Models.RoomType());
                	//get all roomtypefacilities and pass the results to callback, calling a service of the model.
                	accomodationObj.getRoomTypeFacilities( selectedId, self.callback('listRoomFacilitiesSuccess'), self.callback('listRoomFacilitiesError'));
                	
                }
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
        },
        
        /**
         * Displays a list of RoomFacilities.
         * @param String.
         */
         listRoomFacilitiesSuccess: function( roomFacilitiesHtml ){
             $(".wrapper-facility").empty();
             $(".wrapper-facility").append(roomFacilitiesHtml);
         },
         
         /**
          * Display error advice for RoomFacilities.
          * @param {null}.
          */
          listRoomFacilitiesError: function(  ){
        	  $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
          }
    

    
    });
    //---  END ROOM SECTION CODE 
    
    
    new Controllers.Room();
});