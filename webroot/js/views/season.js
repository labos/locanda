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
         "click div": "switchMode",
         "submit form": "save"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
         this.model.bind('destroy', this.unrender, this);
     },
     // Re-render the contents of the todo item.
     render: function () {
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
         this.$(".yform").validate();
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
             var validator = $(this).parents(".yform").validate();
             validator.resetForm();
             return false;
         });
			// attack datepickers
			this.$(".datepicker").removeClass('hasDatepicker').datepicker("destroy");
			this.$(".datepicker").datepicker({
				showOn: "button",
				buttonImage: "images/calendar.gif",
				buttonImageOnly: true,
				dateFormat: I18NSettings.datePattern
			});
         
         
         
         this.delegateEvents();
         return this;
     },
     unrender: function () {
         //clean up events raised from the view
         this.unbind();
         //clean up events from the DOM
         $(this.el).remove();
     },
     
     save: function(e){
    	 e.preventDefault();
         var self = this,
             is_new = this.model.isNew() ? true : false,
             item = this.model.clone();
         item.save($("#edit-form").serializeObject(), {
             success: function (model, resp) {
                 self.model.set(model);
                 self.model.initialize();
                 if (is_new) {
                     self.collection.add(self.model);
                 }
                 $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
                 self.switchMode();
                 
             },
             error: function () {
                 $().notify(this.alertKO, $.i18n("seriousErrorDescr") + ' ');
             }
         });
         return false;
    	 
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
         if( this.indexTemplate.attr("id") == "period-row-edit-template" ){
        	 this.indexTemplate =  $("#period-row-template");
        	 $(".overlay").remove();
        	 $(this.el).removeClass("edit-state-box");
        	 this.render();
        	 $($.fn.overlay.defaults.container).css('overflow', 'auto');
         }
         else{
         this.indexTemplate = (this.indexTemplate.attr("id") == "period-row-template") ? $("#period-row-edit-template") : $("#period-row-template");
         this.render();
         var self = this;
         $(this.el).undelegate("div", "click");
         $('<div></div>').overlay({
             effect: 'fade',
             onShow: function() {
            	 var overlay = this;
                 $(self.el).addClass("edit-state-box");
                 $(this).click( function (){
              	   if( confirm($.i18n( "alertExitEditState" )) ){
              		 $(self.el).removeClass("edit-state-box");
      					self.indexTemplate = $("#period-row-template");
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
 
 /*
  * @class PeriodsListView 
  * @parent Backbone.View
  * @constructor
  * Class to show list of periods.
  * @tag views
  * @author LabOpenSource
  */
 window.PeriodsListView = Backbone.View.extend({
	  indexTemplate: null,
	     events: {
	         "click .btn_add": "addNew"
	     },
     initialize: function (options) {
    	 this.indexTemplate  = $("#periods-view-template");
    	 _.bindAll(this,"addOne");
         this.collection.bind('reset', this.render, this);
         this.collection.bind('remove', this.removeOne, this);
         this.rowViews = [];
    	 this.page = 0;
     },
     render: function () {
         $(this.el).html(this.indexTemplate.html());
         this.$(".btn_add").button({
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
     addOne: function (item) {
         var view = new PeriodRowView({
             model: item
         });
         view.bind("row:edit", this.editOne);
         view.model.collection = this.collection;
         this.rowViews.push(view);
         this.$("ul").append(view.render().el);
     },
     // Add all items in the collection at once.
     addNew: function () {

    	 this.addOne( new Period( ));
    	 _.last(this.rowViews).switchMode();
    	 

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
        	 this.periodsListView = new PeriodsListView( { collection: new Periods( )});
        	 this.id = null;
     },
     
     render: function () {
    	 // render main edit view
    	 this.model.setYears();
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
         this.$(".yform").validate();
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
             var validator = $(this).parents(".yform").validate();
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
    	 if (this.model.isNew() || this.model.get("id")  != this.id){
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