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