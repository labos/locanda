$(function () {
    //---  ROOM SECTION CODE    
    $.Class.extend('Controllers.Facility', /* @prototype */ {
        init: function () { 
        	var self = this;

            $(".facility").hover(function () {
                if ($(this).children(".hov_edit")) {

                	$(this).children(".hov_edit").show();
                }},
                function () {
                    if ($(this).children(".hov_edit")) {

                    	$(this).children(".hov_edit").hide();
                    }}
            );
            
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
			Models.Facility.update({},params, self.callback('updateFacilitySuccess'), self.callback('updateFacilityError')); };
			
			dialogButtons[$.i18n("delete")] = function() { 
				var id = $( "#dialog-facility" ).find("input:hidden['facility.id']").val();
                if (confirm($.i18n("alertCancel"))) {
                	Models.Facility.destroy(id, self.callback('destroyFacilitySuccess'), self.callback('destroyFacilityError')); 
                    $(this).dialog("close");
                };
               
                    
                };
			
		
		
	        /**
	        *
	        * Manage facility editing with image click event handler.
	        */
		$('.facility').find("img, .hov_edit").click( function() {
			self.$facilityDom = $(this).parent();
			$( "#dialog-facility:ui-dialog" ).dialog( "destroy" );
			var idFacility = $(this).siblings("input:hidden").val();
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
    		if( data && $.isPlainObject(data) ){
    			
        		this.$facilityDom.find("label").text(data.facility.name);
        		this.$facilityDom.find("img").attr("src",data.facility.fileName);
        		$().notify($.i18n("warning"), $.i18n("updateFacilitySuccess"));
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
    		this.$facilityDom.remove();
    		$().notify($.i18n("warning"), $.i18n("destroyFacilitySuccess"));
    		
    	},
    	/**
         * manage errors during destroy facility.
         */
    	destroyFacilityError: function(){
    		
    		$().notify($.i18n("warning"), $.i18n("destroyFacilityError"));
    	}
    

    
    });
    //---  END ROOM SECTION CODE 
    
    
    new Controllers.Facility();
});