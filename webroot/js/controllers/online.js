$(function() {
    $.Class.extend('Online', /* @static */ {	
    	init: function () {
//---  EMAIL SECTION CODE   
    /* Hide/Show usable parameters */
    $("#show_usable").toggle(function () {
        $(".list_usable").show();
    }, function () {
        $(".list_usable").hide();
    });
    //---  END EMAIL SECTION CODE  
    
    //---  ONLINE BOOKINGS SECTION CODE
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
    
    	}},	{});
});