$(function () {
    $.Class.extend('Controller.Online', /* @prototype */ {
        init: function () {
        	
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
        }
    });
    
    new Controller.Online();
});