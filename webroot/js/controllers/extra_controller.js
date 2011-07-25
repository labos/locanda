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
    //---  EXTRAS SECTION CODE   
    $.Class.extend('Controller.Extra', /* @prototype */ {
        init: function () {
             
        	/* Buttons rendering and event handler attachments */
            $(".btn_addExtra").button({
                icons: {
                    primary: "ui-icon-circle-plus"
                }
            });
            
            $(".btn_saveExtra").button({
                icons: {
                    primary: "ui-icon-check"
                }
            });
            
            $(".btn_cancel").button({
                icons: {
                    primary: "ui-icon-cancel"
                }
            });
            $(".btn_addExtra").click(function () {
                $(this).hide();
                $("#newExtraForm").show();
            });
            
            $(".btn_delete_extra").click(function (event) {
                event.preventDefault();
                $(this).parents("#extraForm").submitForm("deleteExtra.action");
            });
            
            $(".btn_saveExtra").click(function () {
                $("#extraForm").hide();
                $(".btn_addExtra").show();
            });
            
            $(".renameExtra").click(function () { //gestisco il rename facendo comparire il form relativo
                $(this).hide();
                $(this).siblings(".extraName").hide();

            });
            
            
            //---  END EXTRAS SECTION CODE  
        }
    });
    
    new Controller.Extra();
});