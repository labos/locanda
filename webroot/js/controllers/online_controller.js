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

        	
            /* Hide/Show usable parameters */
            $("#show_usable").toggle(function () {
                $(".list_usable").show();
            }, function () {
                $(".list_usable").hide();
            });

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

});