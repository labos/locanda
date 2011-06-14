$(function () {
    //---  PRICE LISTS SECTION CODE
    $.Class.extend('Controller.Tree', /* @prototype */ {
        init: function () {
        	
            $("#priceList_buttons").hide();
            $("#priceList_buttons").html('<button class="btn_save">SAVE</button>' + '<button class="btn_reset">CANCEL</button>');

        	
            $(".btn_save").button({
                icons: {
                    primary: "ui-icon-check"
                }
            });
            $(".btn_reset").button({
                icons: {
                    primary: "ui-icon-trash"
                }
            }).click(function (event) {
                event.preventDefault();
                var validator = $(this).parents(".yform.json").validate();
                validator.resetForm();
            });
        	

            
            $(".room_tree, .extra_tree").bind("loaded.jstree", function (event, data) {
                $(".jstree-leaf").click(function (event) {
                    event.preventDefault();
                    var url_table = $("a", this).attr("href");
                    $.ajax({
                        url: url_table,
                        context: document.body,
                        dataType: "html",
                        success: function (data) {
                            $(".priceList_table > tbody").html(data);
                        },
                        error: function (request, state, errors) {
                            $().notify($.i18n("warning"), "Problema restituzione lista...");
                        }
                    });

                    $("#priceList_edit").toggle(function () {
                        $("#priceList_form").find("input").removeClass("noBorder");
                        $("#priceList_form").find("input").removeAttr('readonly', 'readonly');
                        $("#priceList_buttons").show();
                    }, function () {
                        $("#priceList_form").find("input").addClass("noBorder");
                        $("#priceList_form").find("input").attr('readonly', 'readonly');
                        $("#priceList_buttons").hide();
                    });
                });
            });
            $(".room_tree").jstree({
                "core": {
                    "initially_open": ["root"]
                },
                "json_data": {
                    "ajax": {
                        "url": "findAllRoomPriceLists.action"
                    }
                },
                "themes": {
                    "theme": "default",
                    "dots": true,
                    "icons": true
                },
                "plugins": ["themes", "json_data"]
            });
            $(".extra_tree").jstree({
                "core": {
                    "initially_open": ["root"]
                },
                "json_data": {
                    "ajax": {
                        "url": "findAllExtraPriceLists.action"
                    }
                },
                "themes": {
                    "theme": "default",
                    "dots": true,
                    "icons": true
                },
                "plugins": ["themes", "json_data"]
            });
        }
    });
    
    new Controller.Tree();
    
});