   /*
    * @class PeriodRowView
    * @parent Backbone.View
    * @constructor
    * Class to show a single period
    * @tag views
    * @author LabOpenSource
    */
window.PeriodRowView = Backbone.View.extend({
     //... is a list tag.
     tagName: "li",
     indexTemplate: $("#period-row-template"),
     // The DOM events specific to an row.
     events: {
         "click span.delete-elem": "remove",
         "click .row-item": "edit",
         "click div": "switchMode"
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
         this.indexTemplate = (this.indexTemplate.attr("id") == "period-row-template") ? $("#period-row-edit-template") : $("#image-row-template");
         this.render();
     }
 }); 



 
 /*
  * @class PeriodsListView 
  * @parent Backbone.View
  * @constructor
  * Class to show list of periods.
  * @tag views
  * @author LabOpenSource
  */
 window.PeriodsListView = View.extend({
	  indexTemplate: null,
	     events: {
	         "click .save-elem": "saveElement",
	         "click btn_add": "addNew"
	     },
     initialize: function (options) {
    	 this.indexTemplate  = $("#periods-view-template");
         this.collection.bind('reset', this.render, this);
         this.collection.bind('remove', this.removeOne, this);
         this.rowViews = [];
    	 this.page = 0;
     },
     render: function () {
         $(this.el).html(this.indexTemplate.html());
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
     removeOne: function(){
         if (confirm($.i18n("alresetertDelete"))) {
        	 this.trigger("period:remove", this);
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
     // Add all items in the collection at once.
     addNew: function () {

     }
 });
 
 
 /*
  * @class EditSeasonView
  * @parent Backbone.View
  * @constructor
  * Edit a row selected in the listing.
  * @tag views
  * @author LabOpenSource
  */
 window.EditSeasonView = EditView.extend({
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
             this.periodsListView.collection.reset(this.model.get("periods") );
             this.id = this.model.get("id");
             // now render associated views
             if( $("#periods").is(':empty') ) {
                 $("#periods").html( this.periodsListView.el ) ;
             }

    	 }


    	 
     },
     switchMode: function () {

         if( this.indexTemplate.attr("id") == "edit-template" ){
        	 this.indexTemplate =  $("#view-template");
        	 $(".overlay").remove();
        	 $(this.el).removeClass("edit-state-box");
        	 this.render();
        	 $($.fn.overlay.defaults.container).css('overflow', 'auto');
         }
         else{
        	 this.indexTemplate =  $("#edit-template");
        	 this.render();
        	 $(this.el).undelegate("div", "click");
        	 var self = this;
             $('<div></div>').overlay({
                 effect: 'fade',
                 onShow: function() {
                	 var overlay = this;
                	 $(self.el).addClass("edit-state-box");
                     $(this).click( function (){
                  	   if(confirm($.i18n( "alertExitEditState" ))){
                  		 $(self.el).removeClass("edit-state-box");
       					self.indexTemplate = $("#view-template");
       					self.render();
       					$(overlay).remove();
       					$($.fn.overlay.defaults.container).css('overflow', 'auto');

                   	   }
                    	 
                     });
                   }
               });
        	 
         }

         
     }
	 
 });