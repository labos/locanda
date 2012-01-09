/*
 * @class ImagesFacilitiesView
 * @parent Backbone.View
 * @constructor
 * Class to show a single facility
 * @tag views
 * @author LabOpenSource
 */  
window.FacilityRowView = Backbone.View.extend({
     //... is a list tag.
     tagName: "li",
     indexTemplate: $("#facility-row-template"),
     // The DOM events specific to an row.
     events: {
         "click span.row-item-destroy": "remove",
         "click .row-item": "edit",
         "click input.choose-elem": "choose"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
         this.model.bind('destroy', this.unrender, this);
     },
     // Re-render the contents of the todo item.
     render: function () {
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
         return this;
     },
     // Switch this view into `"editing"` mode, displaying the input field.
     edit: function (event) {
         var $target = $(event.target);
         if (!$target.is("span.row-item-destroy")) {
             this.trigger("row:edit", this);
         }},
         // add a facility to the parent model
     choose: function (event) {
        	 //send cheched/unchecked roomTypeFacility
         var $target = $(event.target);
         if (!$target.is(":checked")) {
        	  this.model.destroy({
                  success: function () {
                     
                      $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
                  },
                  error: function (jqXHR, textStatus, errorThrown) {
                      textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                      $.jGrowl(textStatus.responseText, { header: this.alertKO, theme: "notify-error"  });
                      
                  }
              });
         }
         else{
       	  this.model.save({
              success: function () {
                 
                  $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
              },
              error: function (jqXHR, textStatus, errorThrown) {
                  textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                  $.jGrowl(textStatus.responseText, { header: this.alertKO, theme: "notify-error"  });
                  
              }
          }); 
        	 
         }
           

     },
     unrender: function () {
         //clean up events raised from the view
         this.unbind();
         //clean up events from the DOM
         $(this.el).remove();
     },
     // Remove this view from the DOM.
     remove: function () {
         if (confirm($.i18n("alertDelete"))) {
             this.model.destroy({
                 success: function () {
                    
                     $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
                 },
                 error: function (jqXHR, textStatus, errorThrown) {
                     textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                     $.jGrowl(textStatus.responseText, { header: this.alertKO, theme: "notify-error"  });
                     
                 }
             });
         }
     },
     // clear all attributes from the model
     clear: function () {
         this.model.clear();
     },
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "facility-row-template") ? $("#facility-row-edit-template") : $("#facility-row-template");
         this.render();
     }
 });    
   

   /*
    * @class ImageRowView
    * @parent Backbone.View
    * @constructor
    * Class to show a single image
    * @tag views
    * @author LabOpenSource
    */
window.ImageRowView = Backbone.View.extend({
     //... is a list tag.
     tagName: "li",
     indexTemplate: $("#image-row-template"),
     // The DOM events specific to an row.
     events: {
         "click span.delete-elem": "remove",
         "click .row-item": "edit"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
         this.model.bind('destroy', this.unrender, this);
     },
     // Re-render the contents of the todo item.
     render: function () {
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
         return this;
     },
     // Switch this view into `"editing"` mode, displaying the input field.
     edit: function (event) {
         var $target = $(event.target);
         if (!$target.is("span.row-item-destroy")) {
             this.trigger("row:edit", this);
         }
     },
     unrender: function () {
         //clean up events raised from the view
         this.unbind();
         //clean up events from the DOM
         $(this.el).remove();
     },
     // Remove this view from the DOM.
     remove: function () {
         if (confirm($.i18n("alertDelete"))) {
             this.model.destroy({
                 success: function () {
                    
                     $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
                 },
                 error: function (jqXHR, textStatus, errorThrown) {
                     textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                     $.jGrowl(textStatus.responseText, { header: this.alertKO, theme: "notify-error"  });
                     
                 }
             });
         }
     },
     // clear all attributes from the model
     clear: function () {
         this.model.clear();
     },
     
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "image-row-template") ? $("#image-row-edit-template") : $("#image-row-template");
         this.render();
     }
 }); 



/*
 * @class ImagesFacilitiesView
 * @parent Backbone.View
 * @constructor
 * Class for listing of the images or facilities.
 * @tag views
 * @author LabOpenSource
 */
window.ImagesFacilitiesView = Backbone.View.extend({
     
     indexTemplate: null,
     events: {
         "click .ui-rcarousel-next": "next",
         "click .ui-rcarousel-prev": "prev",
         "click .save-elem": "saveElement",
        // "click span.sub-edit" :"switchMode",
         "click div" :"switchMode"
     },
     initialize: function (options) {
    	 _.bindAll(this, "next", "prev", "removeOne","addOne");
    	 this.page = 0;
    	 this.rowViews = [];
         this.render();
     },
     render: function () {
         $(this.el).html(this.indexTemplate.html());
         if(this.$("#uploadFacility").length){
             	 this.$("#uploadFacility").uploadImage( );
         }
         $(".btn_add").button({
             icons: {
                 primary: "ui-icon-plusthick"
             }
         });
         this.addAll();
         this.delegateEvents();
         return this;
     },
     // Add all items in the collection at once.
     addAll: function () {
         $.each(this.rowViews, function (index, value) {
             this.unrender();
         });
         this.rowViews = [];
         this.collection.each(this.addOne);
     },
     
     addOne: function (item) {
         var view = new RowView({
             model: item
         });
         view.bind("row:edit", this.editOne);
         view.model.collection = this.collection;
         this.rowViews.push(view);
         $(this.el).append(view.render().el);
     },
     next: function () {
    	 this.page--;
			var slideAmount =  this.page * $(".wrapper").width() / 2 ;
	    	// $(".wrapper ul",this.el).css("left", + this.page + "px");
	    	 
	    	 $(".wrapper ul",this.el).animate({
	    		    opacity: 0.25,
	    		    left: '+='+slideAmount
	    		  }, 1000, 'linear', function() {
	    		   
	    			  $(this).css("opacity", 1);
	    			  $(".ui-rcarousel-prev").removeClass("disable");
	    		  });
     },
     prev: function (event) {        
    	    if(this.page < 0 ){
    	    		var self = this,
    				slideAmount =  this.page * $(".wrapper").width() / 2 ;
    		    	// $(".wrapper ul",this.el).css("left", + this.page + "px");
    				 
    		    	 $(".wrapper ul",this.el).animate({
    		    		    opacity: 0.25,
    		    		    left: '-='+slideAmount
    		    		  }, 1000, 'linear', function() {
    		    		   
    		    			  $(this).css("opacity", 1);
    		    			  ( self.page < 0 )? $(".ui-rcarousel-prev").removeClass("disable") : $(".ui-rcarousel-prev").addClass("disable");
    		    			  self.page++;
    		    		  });
    	    }

    	 
     },
     addElement: function(){
    	 
     },
     saveElement: function(){
    	 
    	 
     },
     removeOne: function(){
    	 
     },
     editOne: function(){
    	 
     },
     switchMode: function () {
     }
 });

/*
 * @class FacilitiesListView
 * @parent Backbone.View
 * @constructor
 * Class to show list of facilities.
 * @tag views
 * @author LabOpenSource
 */
 window.FacilitiesListView = ImagesFacilitiesView.extend({
     initialize: function (options) {
    	 options['mode'] || ( options['mode'] = "view");
    	 this.indexTemplate  = $("#facilities-" + options['mode'] + "-template");
    	 _.bindAll(this, "next", "prev","addOne");
         this.collection.bind('reset', this.render, this);
         this.collection.bind('remove', this.render, this);
    	 this.rowViews = [];
    	 this.page = 0;
     },
     
     saveElement: function(){ 
    	 
     },
     addOne: function (item) {
         var view = new FacilityRowView({
             model: item
         });
         view.bind("row:edit", this.editOne);
         view.model.collection = this.collection;
         this.rowViews.push(view);
         this.$("ul").append(view.render().el);
     },
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "facilities-view-template") ? $("#facilities-edit-template") : $("#facilities-view-template");
         this.render();
         var self = this;
    	 $.each(this.rowViews, function(index,aView){
    		 aView.switchMode();
    	 });
         $(this.el).undelegate("div", "click");
         $('<div></div>').overlay({
             effect: 'fade',
             onShow: function() {
            	 var overlay = this;
                 $(self.el).addClass("edit-state-box");
                 $(this).click( function (){
              	   if(confirm($.i18n( "alertExitEditState" ))){
              		 $(self.el).removeClass("edit-state-box");
              		self.indexTemplate = $("#facilities-view-template");
   					self.render();
   					$(overlay).remove();
   					$($.fn.overlay.defaults.container).css('overflow', 'auto');

               	   }
                	 
                 });
               }
           });
}
 });
 
 /*
  * @class ImagesListView
  * @parent Backbone.View
  * @constructor
  * Class to show list of images.
  * @tag views
  * @author LabOpenSource
  */
 window.ImagesListView = ImagesFacilitiesView.extend({
     initialize: function (options) {
    	 options['mode'] || ( options['mode'] = "view");
    	 this.indexTemplate  = $("#images-" + options['mode'] + "-template");
    	 _.bindAll(this, "next", "prev","addOne");
         this.collection.bind('reset', this.render, this);
         this.collection.bind('remove', this.removeOne, this);
         this.rowViews = [];
    	 this.page = 0;
     },
     removeOne: function(){
         if (confirm($.i18n("alresetertDelete"))) {
        	 this.trigger("image:remove", this);
         } 
     },
     saveElement: function(){
    	 //save element
    	 
     },
     addOne: function (item) {
         var view = new ImageRowView({
             model: item
         });
         view.bind("row:edit", this.editOne);
         view.model.collection = this.collection;
         this.rowViews.push(view);
         this.$("ul").append(view.render().el);
     },
     
     switchMode: function () {
    	 // change in edit mode template
         this.indexTemplate = (this.indexTemplate.attr("id") == "images-view-template") ? $("#images-edit-template") : $("#images-view-template");
         this.render();
         var self = this;
         // unbind div click event in edit mode in the current div overlapped
         $(this.el).undelegate("div", "click");
         //change list of rows in edit mode
    	 $.each(this.rowViews, function(index,aView){
    		 aView.switchMode();
    	 });
         
         $('<div></div>').overlay({
             effect: 'fade',
             onShow: function() {
            	 var overlay = this;
                 $(self.el).addClass("edit-state-box");
                 $(this).click( function (){
              	   if(confirm($.i18n( "alertExitEditState" ))){
              		 $(self.el).removeClass("edit-state-box");
      					self.indexTemplate = $("#images-view-template");
   					self.render();
   					$(overlay).remove();
   					$($.fn.overlay.defaults.container).css('overflow', 'auto');

               	   }
                	 
                 });
               }
           });


    
     }
 });
 
 
 /*
  * @class EditImagesFacilitiesView
  * @parent Backbone.View
  * @constructor
  * Edit a row selected in the listing.
  * @tag views
  * @author LabOpenSource
  */
 window.EditImagesFacilitiesView = EditView.extend({
     events: {
         "submit form": "save",
         "click div": "switchMode"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
        	 this.facilitiesListView = new FacilitiesListView( { collection: new RoomTypeFacilities( )});
        	 this.imagesListView = new ImagesListView( { collection: new Images( ) } );
        	 this.id = null;
     },
     
     render: function () {
    	 // render main edit view
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
         this.$(".yform.json.full").validate();
         $(".btn_save").button({
             icons: {
                 primary: "ui-icon-check"
             }
         });
         //button for form reset  
         $(".btn_reset").button({
             icons: {
                 primary: "ui-icon-trash"
             }
         }).click(function (event) {
             var validator = $(this).parents(".yform.json").validate();
             validator.resetForm();
             return false;
         });
         
         // call for render associated views
         this.renderAssociated();       
         this.delegateEvents();
         return this;
     },
     renderAssociated: function (){
    	 // check if model has changed, then update collections in associated views
    	 if (this.model.get("id")  != this.id){
             //set collection for associated views
             this.facilitiesListView.collection.reset(this.model.get("images") );
             this.imagesListView.collection.reset( this.model.get("images"));
             this.id = this.model.get("id");
             // now render associated views
             if( $("#facilities").is(':empty') ) {
                 $("#facilities").html( this.facilitiesListView.el ) ;
                 $("#images").html( this.imagesListView.el);
             }

    	 }


    	 
     }
	 
 });