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
$(document).ready(function () {

	
    $.Class.extend('Main', /* @static */ {
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
       	    
       	 //serialize a form to json   
       	 $.fn.serializeObject = function()
       	{
       	    var o = {};
       	    var a = this.serializeArray();
       	    $.each(a, function() {
       	        if (o[this.name] !== undefined) {
       	            if (!o[this.name].push) {
       	                o[this.name] = [o[this.name]];
       	            }
       	            o[this.name].push(this.value || '');
       	        } else {
       	            o[this.name] = this.value || '';
       	        }
       	    });
       	    return o;
       	};
       	    //Notifier for all jsp(s)
       	    $.fn.notify = function (title, description, redirect) {
          	     $('#jGrowl').jGrowl('shutdown').remove();
       	        //get height of the body to cover all html page
       	        var heightbody = $('body').height();
       	        if (!$(".ui-widget-overlay").is(':visible')) {
       	            $(".ui-widget-overlay").css("height", heightbody).show();
       	        }

       	        $.jGrowl(description, {
       	            beforeClose: function (e, m) {
       	                if (redirect && redirect !=='false') {
       	                    window.location.href = redirect;
       	                }
       	            },
       	            animateOpen: {
       	                height: 'show'
       	            },
       	            position: "center",
       	            speed: 2000,
       	            life: 80,
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
    		
        	/* Add validation rules */
      
            jQuery.validator.addMethod("validPricePositive", function (value, element) {
                return this.optional(element) || /^[0-9]+[.]?[0-9]*([eE][0-9]+)?$/.test(value);
            }, $.i18n("validPriceAlert"));
            
            // set decimal format validation
            jQuery.validator.addMethod("validPhone", function (value, element) {
                return this.optional(element) || /(^[\d\s\/\+]{5,})$/.test(value);
            }, $.i18n("validPhoneAlert"));
            
        	/* Add shared validators */
    		$.metadata.setType("attr", "validate");
            $(".yform").validate();
            $(".yform.json").validate();
            
            /* Add shared submit event listener */
            /*
             $(".yform.json").submit(function (event) {
                $(this).submitForm();
                return false;
            });
            */
            /* Add shared UI components */
            $("#accordion").accordion({
                collapsible: true,
                active: false,
                animated: 'bounceslide',
                autoHeight: false
            });
            $("#accordion2").accordion({
                collapsible: true,
                active: 0,
                animated: 'bounceslide',
                autoHeight: false
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
            });
            
            //button for form reset  
            $(".btn_reset").button({
                icons: {
                    primary: "ui-icon-arrowreturnthick-1-w"
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
            
            //Binds all event handler methods
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
          //---  $('body').append($('<div class="ui-widget-overlay"></div>'));
            $.jGrowl.defaults.position = 'center';
            $.jGrowl.defaults.life = 300;
            $.ajaxSetup({cache: false});
            		  }
            		});
    $('.item_list').live("mouseover mouseout", function(event) {
  	  if ( event.type == "mouseover" ) {
        	$(this).addClass("shadow_box");
  		  } else {
              	$(this).removeClass("shadow_box");

  		  }
  		});
    
    new Main(I18NSettings.lang, I18NSettings.datePattern);
});