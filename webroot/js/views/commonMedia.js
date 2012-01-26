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
/*
 * @class ImagesFacilitiesView
 * @parent Backbone.View
 * @constructor
 * Class to show a single facility
 * @tag views
 * @author LabOpenSource
 */  
window.FacilityRowView = Backbone.View.extend({
     //list of tags.
     tagName: "li",
     indexTemplate: $("#facility-row-template"),
     // The DOM events specific to an row.
     events: {
         "click input.choose-elem": "choose"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
         this.model.bind('destroy', this.unrender, this);
     },
  	/**
   	 * Re-render the contents of the facility item.
   	 */
     render: function () {
    	 if( this.model.isNew()){
    		 this.indexTemplate.find(".choose-elem").prop("checked", "");
    	 }
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
         return this;
     },
         // 
 	/**
  	 * Assign or de-assign facility to the parent model by checkbox click
  	 * @param {Object} event triggered from checkbox pushed.
  	 */
     choose: function (event) {
        	 //send cheched/unchecked roomTypeFacility
         var $target = $(event.target),
         self = this;
         if (!$target.is(":checked")) {
        	  this.model.destroy({
                  success: function () {

                      $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
                  },
                  error: function (jqXHR, textStatus, errorThrown) {
                	  // re-check if destroy fail
                	  $target.prop("checked","checked");
                      textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                      $.jGrowl(textStatus.responseText, { header: this.alertKO, theme: "notify-error"  });
                      
                  }
              });
         }
         else{
       	  this.model.save({
              success: function () {
            	  // trigger an update event.
                  self.trigger("child:update", self);
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
    	 this.model.unbind('change', this.render);
    	 this.model.unbind('destroy', this.unrender);
         //clean up events raised from the view
         this.unbind();
         //clean up events from the DOM
         $(this.el).remove();
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
     unrender: function () {
    	 this.model.unbind('change', this.render);
    	 this.model.unbind('destroy', this.unrender);
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
         "click div" :"switchMode"
     },
     initialize: function (options) {
    	 _.bindAll(this, "next", "prev", "removeOne","addOne");
    	 this.page = 0;
    	 this.idParent = null;
    	 this.rowViews = [];
         this.render();
     },
     render: function () {
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), {id_parent: this.idParent, id_structure:""}));
         if(this.$("#uploadFacility").length){
             	 this.$("#uploadFacility").uploadImage( this );
         }
         $(".btn_add").button({
             icons: {
                 primary: "ui-icon-gear"
             }
         });
         this.page = 0;
         this.addAll();
         (typeof this.idParent !== 'undefined' && this.idParent )? $(this.el).show() : $(this.el).hide();

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
    	 if(  this.getNumPages( this.page )){
    	 this.page--;
    	 // calculate width used by elements contained in wrapper
    	 
    	 var self = this,
    	 slideAmount =  (-1) * $(".wrapper",this.el).width() / 3 ;
	    	 
	    	 $(".wrapper ul",this.el).animate({
	    		    opacity: 0.25,
	    		    left: '+='+slideAmount
	    		  }, 1000, 'linear', function() {
	    		   
	    			  $(this).css("opacity", 1);
	    			  $(".ui-rcarousel-prev",self.el).removeClass("disable");
	    		  });
	    	 
    	 }	 
     },
     prev: function (event) {        
    	    if(this.page < 0 ){
    	    	  this.page++;
    	    		var self = this,
    				slideAmount =   $(".wrapper",this.el).width() / 3 ;
    				 
    		    	 $(".wrapper ul",this.el).animate({
    		    		    opacity: 0.25,
    		    		    left: '+='+slideAmount
    		    		  }, 1000, 'linear', function() {
    		    		   
    		    			  $(this).css("opacity", 1);
    		    			  ( self.page < 0 )? $(".ui-rcarousel-prev",self.el).removeClass("disable") : $(".ui-rcarousel-prev",self.el).addClass("disable");
    		    			
    		    		  });
    	    }

    	 
     },
     getNumPages: function(step){
    	 
    	 var slideAmount = $(".wrapper",this.el).width() / 3,
    	 slideWidth = this.$(".wrapper").width(),
    	 itemWidth = this.$(".wrapper ul li").width() + 16,
    	 numItems = this.$(".wrapper ul li").length,
    	 itemWidthUsed = numItems * itemWidth;
    	 return ( (itemWidthUsed + (step * slideAmount)) > slideWidth )? true : false;
  	 
    	 
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
	 className: "facilities",
     initialize: function (options) {
    	 options['mode'] || ( options['mode'] = "view");
    	 this.indexTemplate  = $("#facilities-" + options['mode'] + "-template");
    	 _.bindAll(this, "next", "prev","addOne");
         this.collection.bind('reset', this.render, this);
         this.collection.bind('remove', this.removeOne, this);
         // collection of facilities to check
         this.availableCollection = new AvailableFacilities({idStructure: Entity.idStructure});
         // array of single facility view
    	 this.rowViews = [];
    	 // page index for the slider
    	 this.page = 0;
    	 // id of parent entity-model
    	 this.idParent = null;
     },
     removeOne: function(){
    	 this.trigger("child:update", this);
     },
     
     saveElement: function(){ 
    	 
     },
 	/**
  	 * Merge checked facilities with available facilities and show not checked facilities.
  	 */
     getAvailableFacilities: function(){
    	 var self = this;
    	 this.availableCollection.fetch( {silent: true, success: function(){
    		 self.availableCollection =  _.without(self.availableCollection, self.collection );
    	 }});
    	this.availableCollection.each(this.addOne);
     },
 	/**
  	 * Add a facility view  to rowViews array render it.
  	 * @param {Object} backbone model of the facility.
  	 */
     addOne: function (item) {
         var view = new FacilityRowView({
             model: item
         });
         view.bind("child:update", function () {
             self.trigger("child:update", this);
         }, self);
         if( this.indexTemplate.attr("id") == "facilities-edit-template" )
    	 {
    	 
    	 	view.switchMode();
    	 }
         view.model.collection = this.collection;
         this.rowViews.push(view);
         this.$("ul").append(view.render().el);
     },
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "facilities-view-template") ? $("#facilities-edit-template") : $("#facilities-view-template");
         this.render();
         var self = this;

         $(this.el).undelegate("div", "click");
         $('<div></div>').overlay({
             effect: 'fade',
             onShow: function() {
            	 var overlay = this;
            	 // call a method to render availableCollection
            	 self.getAvailableFacilities();
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
	 className: "images",
     initialize: function (options) {
    	 options['mode'] || ( options['mode'] = "view");
    	 this.indexTemplate  = $("#images-" + options['mode'] + "-template");
    	 _.bindAll(this, "next", "prev","addOne");
         this.collection.bind('reset', this.render, this);
         this.collection.bind('remove', this.removeOne, this);
         this.rowViews = [];
    	 this.page = 0;
    	 this.idParent = null;
     },
     removeOne: function(){
    	 this.trigger("child:update", this);
     },
     saveElement: function(){
    	 //nothing  	 
     },
     addOne: function (item) {
         var view = new ImageRowView({
             model: item
         });
         view.model.collection = this.collection;
         /*  view.bind("child:update", function () {
             self.trigger("child:update", self);
         }, self);*/
       if( this.indexTemplate.attr("id") == "images-edit-template" )
        	 {
        	 
        	 	view.switchMode();
        	 }
         this.rowViews.push(view);
         this.$("ul").append(view.render().el);
     },
     
     switchMode: function () {
    	 // change in edit mode template
    	 if( this.indexTemplate.attr("id") == "images-edit-template" ){
    		 this.indexTemplate = $("#images-view-template");
             $(".overlay").remove();
             $(this.el).removeClass("edit-state-box");
             this.render();
             $($.fn.overlay.defaults.container).css('overflow', 'auto');
    	 }
    	 else{
             this.indexTemplate = $("#images-edit-template");
             this.render();
             var self = this;
             $(this.el).undelegate("div", "click");
             
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
    	 /*  //change list of rows in edit mode
    	 $.each(this.rowViews, function(index,aView){
    		 aView.switchMode();
    	 });
         
    */
     }
 });