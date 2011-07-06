$(function () {
    //---  FACILITY SECTION CODE    
    $.Class.extend('Controllers.Facility', /* @prototype */ {
        init: function () { 
        	var self = this;
        	
        	
        	$(".facility").live(
        	        'hover',
        	        function (ev) {
        	            if (ev.type == 'mouseenter') {
        	            	if ($(this).children(".hov_edit")) {

        	                	$(this).children(".hov_edit").show();
        	                }
        	            }

        	            if (ev.type == 'mouseleave') {
                            if ($(this).children(".hov_edit")) {

                            	$(this).children(".hov_edit").hide();
                            }
        	            }
        	        });


            
            /**
             * @attribute $facilityDom
             * Jquery Object Dom of facility in editing (private variable)
             */
            this.$facilityDom = null;
	        /**
	        *
	        * Local variable to store dialog buttons translated for facility editing and related execution code.
	        */
		var dialogButtons = {};
		
			dialogButtons[$.i18n("save")] = function() { 
				var params = $( "#dialog-facility" ).find("form").serialize();
			Models.Facility.update({},params, self.callback('updateFacilitySuccess'), self.callback('updateFacilityError'));
			$(this).dialog("close");
			};
			
			dialogButtons[$.i18n("erase")] = function() { 
				var id = $( "#dialog-facility" ).find("input:hidden['facility.id']").val();
                if (confirm($.i18n("alertDelete"))) {
                	Models.Facility.destroy(id, self.callback('destroyFacilitySuccess'), self.callback('destroyFacilityError')); 
                    $(this).dialog("close");
                };
               
                    
                };
			
		
		
	        /**
	        *
	        * Manage facility editing with image click event handler.
	        */
		$('.facility').find("img, .hov_edit").live('click',  function() {
			self.$facilityDom = $(this).parent();
			$( "#dialog-facility:ui-dialog" ).dialog( "destroy" );
			var idFacility = $(this).siblings("input[name='roomFacilitiesIds']").val();
			var nameFacility = $(this).siblings("label").text();
			var fileNameFacility = $(this).attr("src") || $(this).siblings("img").attr("src");
			var added = new EJS({url: 'js/views/facility/show.ejs'}).render({facility: {id:idFacility, name: nameFacility, fileName: fileNameFacility}, labels:{name: $.i18n("name")}});
			$( "#dialog-facility" ).html(added).dialog({
				minHeight: 140,
				minWidth: 400, 
				modal: true,
				buttons: dialogButtons

			});

		});
    	
        
        },
        
        /**
         * update a facility.
         * @param {String} .
         */
    	updateFacilitySuccess: function(data){
    		var self = this;
    		if( data && $.isPlainObject(data) && data.message.result == "success" ){
    			
        		this.$facilityDom.find("label").text(data.facility.name);
        		$().notify($.i18n("congratulation"), $.i18n("updateFacilitySuccess"));
        		return;
    		}

    		$().notify($.i18n("warning"), $.i18n("updateFacilityError"));
    		
    	},
    	/**
         * manage errors during update facility.
         */
    	updateFacilityError: function(){
    		
    		$().notify($.i18n("warning"), $.i18n("updateFacilityError"));
    	},
        /**
         * destroy a facility.
         * @param {String}.
         */
    	destroyFacilitySuccess: function(data){
    		var self = this;
    		if (data.result == "success"){
        		this.$facilityDom.remove();
        		$().notify($.i18n("congratulation"), $.i18n("destroyFacilitySuccess"));
        		return;
    		}
    		$().notify($.i18n("warning"), $.i18n("destroyFacilityError"));
    		
    	},
    	/**
         * manage errors during destroy facility.
         */
    	destroyFacilityError: function(){
    		
    		$().notify($.i18n("warning"), $.i18n("destroyFacilityError"));
    	}
    

    
    });
    //---  END FACILITY SECTION CODE 
    
    
    new Controllers.Facility();
});