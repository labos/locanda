$(function () {
    //---  EXTRAS SECTION CODE   
    $.Class.extend('Extra', /* @static */ {
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
                $(this).siblings(".renameExtraForm").show();
                $(this).siblings(".renameExtraForm").select();
            });
            
            $(".renameExtraForm").blur(function () { //gestisco il blur per salvare la rinomina dell'extra
					//memorizzo l'indice del div corrente usando il nome dell'extra
                var newName = $(this).val(); //memorizzo il nome dell'extra modificato
                $(this).hide();
                $(".renameExtra").show(); //mostro il link di rinomina
                $(this).siblings(".extraName").text(newName); //setto il nome dell'extra modificato
                $(".extraName").show(); //mostro il nome dell'extra modificato
            });
            
            //---  END EXTRAS SECTION CODE  
        }
    }, {});
});