$(document).ready(function () {

	
    $.Class.extend('Option', /* @static */ {
        init: function () {
       	 	 	 	       	 	
       	    //Submit form function
       	    $.fn.submitForm = function (action) {
       	        //setting for input form fields
       	        var formInput = $(this).serialize();
       	        var hrefAction = action || $(this).attr("action");
       	        var _redirectAction = $(this).find('input:hidden[name="redirect_form"]').val();
       	        _redirectAction = (_redirectAction == null) ? "home.action" : _redirectAction;
       	        //if form is valid
       	        if ($(this).valid()) {
       	            $.ajax({
       	                type: "POST",
       	                url: hrefAction,
       	                data: formInput,
       	                success: function (data_action) {
       	                    var title_notification = null;
       	                    if (data_action.result == "success") {
       	                        $().notify($.i18n("congratulation"), data_action.description, _redirectAction);
       	                    } else if (data_action.result == "error") {
       	                        $().notify($.i18n("warning"), data_action.description);
       	                    } else {
       	                        $(".validationErrors").html(data_action);
       	                    }
       	                },
       	                error: function () {
       	                    $().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
       	                }
       	            });
       	        }
       	        else{
       	        	$("#accordion,#accordion2").accordion("option", "active", 0);
       	        }
       	        return false;
       	    };
       	    
       	    

       	    //Notifier for all jsp(s)
       	    $.fn.notify = function (title, description, redirect) {
       	        //get height of the body to cover all html page
       	        var heightbody = $('body').height();
       	        if (!$(".ui-widget-overlay").is(':visible')) {
       	            $(".ui-widget-overlay").css("height", heightbody).show();
       	        }
       	        $.jGrowl(description, {
       	            beforeClose: function (e, m) {
       	                if (redirect) {
       	                    window.location.href = redirect;
       	                }
       	            },
       	            animateOpen: {
       	                height: 'show'
       	            },
       	            position: "center",
       	            speed: 1000,
       	            life: 1000,
       	            closeDuration: "fast",
       	            header: title,
       	            close: function () {
       	                $(".ui-widget-overlay").hide();
       	            }
       	        });
       	    };
       	    
       	    
       	 	
       	 	
        }
    }, /* @prototype */ {
    	
    	init: function (){
    		
        	
        	/* Add shared validators */
            $(".yform").validate();
            $(".yform.json").validate();
            
            /* Add shared submit event listener */
            $(".yform.json").submit(function (event) {
                $(this).submitForm();
                return false;
            });
            
            /* Add shared UI components */
            $("#accordion,#accordion2").accordion({
                collapsible: true,
                active: false,
                animated: 'bounceslide',
                autoHeight: true
            });
             
            $(".btn_save").button({
                icons: {
                    primary: "ui-icon-check"
                }
            });
            
            $(".btn_submit").button({
                icons: {
                    primary: "ui-icon-triangle-1-e"
                }
            });
            $(".btn_save_all").button({
                icons: {
                    primary: "ui-icon-check"
                }
            });
               
            $(".btn_add_form").button({
                icons: {
                    primary: "ui-icon-circle-plus"
                }
            }).click(function () {
                $("div.hideform").show("slide", {
                    direction: "up"
                }, 1000);
                $(this).hide();
                return false;
            });
            
            //button for form reset  
            $(".btn_reset").button({
                icons: {
                    primary: "ui-icon-trash"
                }
            }).click(function (event) {
                event.preventDefault();
                var validator = $(this).parents(".yform.json").validate();
                validator.resetForm();
            });
            //button for form hiding
            $(".btn_cancel_form").click(function (event) {
                $(this).parents(".hideform").fadeOut().hide("slide", {
                    direction: "up"
                }, 1000);
                $(".btn_add_form").show();
            });
            $(".btn_delete").button({
                icons: {
                    primary: "ui-icon-trash"
                }
            }).click(function (event) {
                event.preventDefault();
                if (confirm($.i18n("alertDelete"))) {
                    $(this).parents(".yform").submitForm();
                }
            });
            
            $('a[id*=toggle]').click(function () {
                if ($(this).hasClass('active') === true) {
                    $('a').removeClass('active');
                    $('body').removeAttr('class');
                } else {
                    $('a').removeClass('active');
                    $('body').removeAttr('class').addClass($(this).text());
                    $(this).addClass('active');
                }
            });
            $('a#formReset').click(function () {
                $('a').removeClass('active');
                $('body').removeAttr('class');
                $(this).addClass('active');
            }); 
            
            /* General settings */
            //make a new div overlay element
            $('body').append($('<div class="ui-widget-overlay"></div>'));
            $.jGrowl.defaults.position = 'center';
            
            
            /* Controllers initialization */

       	 	/* End Controllers initialization */
    		
    		
    	}
    	
    });
    
    new Option(I18NSettings.lang, I18NSettings.datePattern);
    


});