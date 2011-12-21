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
     * @class RowView
     * @parent Backbone.View
     * @constructor
     * Create a Row view in the listing.
     * @tag views
     * @author LabOpenSource
     */
   window.RowView = Backbone.View.extend({
     //... is a list tag.
     tagName: "li",
     indexTemplate: $("#row-template"),
     // The DOM events specific to an row.
     events: {
         "click span.row-item-destroy": "remove",
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
     }
 });
 window.MoreListView = Backbone.View.extend({
     el: $("#nav-bottom"),
     template: $("#tpl_more"),
     indexList: 0,
     events: {
         "click": "change"
     },
     initialize: function () {
         this.render();
     },
     render: function () {
         this.el.html(this.template.html());
         return this;
     },
     next: function () {
         // to override
         //workspaceRouter.navigate('retrieve/' + this.indexList +'/10', true);
     },
     change: function () {},
     hide: function () {
         this.el.hide();
     },
     show: function () {
         //this.el.fadeTo("slow", 1);
         this.el.show();
     },
     shade: function () {
         this.el.fadeTo("slow", 0.33);
     }
 });
 window.MoreListBottomView = MoreListView.extend({
     el: $("#nav-bottom"),
     change: function () {
         this.trigger("next", this);
     }
 });
 window.MoreListTopView = MoreListView.extend({
     el: $("#nav-top"),
     change: function () {
         this.trigger("prev", this);
     }
 });
 

 /*
  * @class EditView
  * @parent Backbone.View
  * @constructor
  * Edit a row selected in the listing.
  * @tag views
  * @author LabOpenSource
  */
 window.EditView = Backbone.View.extend({
     el: $("#row-edit-container"),
     indexTemplate: $("#view-template"),
     events: {
         "submit form": "save",
         "keypress input:text": "updateOnEnter",
         "dblclick #view-form span": "switchMode",
         "click span.inplace-edit": "switchMode"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);         
         this.render();
     },
     // save new item or update existing item.
     save: function (e) {
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
     render: function () {
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

         this.delegateEvents();
         return this;
     },
     updateOnEnter: function (e) {
         if (e.keyCode != 13) return;
         this.save(e);
     },
     // clear all attributes from the model
     clear: function () {
         this.model.clear();
     },
     //unbind all callbacks from the current model  
     resetModel: function (amodel) {
         this.indexTemplate = amodel.isNew() ? $("#edit-template") : $("#view-template");
         this.model.unbind("change", this.render, this);
         this.model = amodel;
         this.model.bind('change', this.render, this);
         this.render();
         return this.model;
     },
     close: function () {
         this.remove();
         //clean up events raised from the view
         this.unbind();
         //clean up events bound from the model
         this.model.unbind("change", this.render);
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
                	 
                 });}
           });
     }
     }
 });
 /*
  * @class ToolBarView
  * @parent Backbone.View
  * @constructor
  * View for item filtering.
  * @tag views
  * @author LabOpenSource
  */
 window.ToolBarView = Backbone.View.extend({
     tagName: "ul",
     indexTemplate: $("#toolbar-template"),
      events: {
         "submit form": "search",
         "click .filter-close": "closeFilter"
     },
     initialize: function () {
    	
         this.cachedSearch = '';
         this.render();
     },
     render: function () {
         $("#toolbar-container").append($(this.el).html(Mustache.to_html(this.indexTemplate.html())));
         $("#form-filter-container").html(Mustache.to_html($("#form-filter-template").html()));
         $("#item-filter").button({
             icons: {
                 secondary: "ui-icon-triangle-1-s"
             },
             text: false
         }).click(function () {
             $("#form-filter-container").slideToggle();
         });
         
         $(".btn_submit").button({
             icons: {
                 primary: "ui-icon-triangle-1-e"
             }
         });
         
         this.autoComplete("#item-autocomplete", null);
         return this;
     },
     // save new item or update existing item.
     search: function (e) {
         e.preventDefault();
         var self = this,
           searched = $("#filter-form").serializeObject(),
           stringTerm = '',
           count = 0;
         $.each(searched, function(key, value){
        	 if( value !=='' && value !==' '){
        		 
        		 var operator = ( count > 0 )? 'AND ' : '';
            	 stringTerm = stringTerm  + operator + key + ':' + value + '* ';
            	 count++;
        	 }

        	 
         });
        // searched = $("#filter-form").serialize();
         var alreadyTyped =  $("#item-autocomplete").val();
         if( alreadyTyped == this.cachedSearch){
        	 
        	 alreadyTyped ='';
         }
         this.cachedSearch = stringTerm + ' ' + alreadyTyped;
         $("#item-autocomplete").val( this.cachedSearch );

    	 self.collection.setTerm(  stringTerm  );
         self.collection.setFrom(0);
         self.collection.setTo(10);
    	 self.collection.fetch(); 
        // this.collection.search( JSON.stringify( searched ) );
         self.closeFilter( );
         return false;
     },
        
     autoComplete: function (selector, onselectToDo) {
         var self = this,
             cache = {};
         var toDo = onselectToDo || null;
         $(selector).focus();
         $(selector).autocomplete({
        	 disabled: false,
        	 minLength: 0,
             search: function (event, ui){
            	 var term  = $("#item-autocomplete").val();
            	 if ( term.length !== 1){
                	 self.collection.setTerm( $("#item-autocomplete").val() );
                     self.collection.setFrom(0);
                     self.collection.setTo(10);
                	 self.collection.fetch(); 
                	 $("#filter-form").find("input").val("");
            	 }

            	 return false;
             },
             source: function (request, response) {
                 var term = request.term;
                 if (term in cache) {
                     response(cache[term]);
                     return;
                 }
                 var autocompletes = new Autocompletes(term, self.collection.getIdWrapper());
                 autocompletes.fetch({
                     success: function () {
                         var result = new Array();
                         try {
                             result = autocompletes.toJSON();
                         } catch (e) {
                             //nothing. result is empty
                         }
                         cache[term] = result;
                         response(result);
                     }
                 });
             },
             // manage the selection of an item
             select: function (event, ui) {
                 if (ui.item) {
                     if (toDo == "findAll") {
                         //nothing
                     } else {
                         $(selector).val(ui.item.id);
                         self.trigger("toolBar:autocomplete", {
                             id: ui.item.id,
                             value: ui.item.value
                         });
                     } //END ELSE
                 }
             }
         });
         //end autocomplete
     },
     
     closeFilter: function(){
    	 
    	 $("#form-filter-container").slideUp()( );
     }
 });
 /*
  * @class ListView
  * @parent Backbone.View
  * @constructor
  * List all rows.
  * @tag views
  * @author LabOpenSource
  */
 window.ListView = Backbone.View.extend({
     // Instead of generating a new element, bind to the existing skeleton of
     // the App already present in the HTML.
     tagName: "ul",
     editView: null,
     currentIndex: null,
     // At initialization we bind to the relevant events on the `this.collection`
     // collection, when items are added or changed. Kick things off by
     initialize: function (options) {
         this.editView = options['editView'];
         // array of rowViews rendered into listView
         this.rowViews = [];
         // number of remaining results
         this.remainingResults = 0;
         // number of results per page
         this.perPageResults = 10;
         // total results
         this.totalResults = 0;
         $("#row-list").append($(this.el));
         //add collection to editView for increase this collection when a new model was saved
         this.editView.collection = this.collection;
         //bind collection's events
         _.bindAll(this, "addOne", "editOne");
         this.collection.bind('add', this.addOne, this);
         this.collection.bind('remove', this.removeOne, this);
         this.collection.bind('reset', this.addAll, this);
         this.collection.bind('destroy', this.removeOne, this);
         this.collection.bind('all', this.render, this);
         this.collection.fetch();
         //handle moreViews for pagination
         this.moreViewTop = new MoreListTopView();
         this.moreViewTop.bind("prev", this.prev, this);
         this.moreViewTop.hide();
         this.moreViewBottom = new MoreListBottomView();
         this.moreViewBottom.bind("next", this.next, this);
         this.currentIndex = 0;
     },
     // Re-rendering the App just means refreshing the view -- the rest
     // of the app doesn't change.
     render: function () {
         return this;
     },
     // Add a single item to the list by creating a view for it, and
     // appending its element to the `<ul>`.
     addOne: function (item) {
         var view = new RowView({
             model: item
         });
         view.bind("row:edit", this.editOne);
         view.model.collection = this.collection;
         this.rowViews.push(view);
         $(this.el).append(view.render().el);
     },
     // Add all items in the collection at once.
     addAll: function () {
         //$(this.el).empty();
    	 $("#row-list").addClass("back");
         $.each(this.rowViews, function (index, value) {
             this.unrender();
         });
         this.rowViews = [];
         this.collection.each(this.addOne);
         if (this.collection.length) {
             this.editView.resetModel(this.collection.at(0));
         }
    	 $("#row-list").removeClass("back");
     },
     removeOne: function (aModel) {
         // now check if deleted model is in editing mode
         if (this.editView && this.editView.model.get("id") == aModel.get("id")) {
             this.editView.clear();
         }
         this.collection.remove(aModel);
     },
     editOne: function (row) {
         this.editView.resetModel(row.model);
         //-- this.addAll();
     },
     // Clear all items, destroying their models.
     clearCompleted: function () {
         _.each(this.collection, function (item) {
             item.clear();
         });
         return false;
     },
     next: function () {
         this.currentIndex = this.currentIndex + this.perPageResults;
         this.updatePage();
     },
     prev: function () {
         if (this.currentIndex > 0) {
             this.currentIndex = this.currentIndex - this.perPageResults;
             this.updatePage();
         }
     },
     updatePage: function () {
         var self = this;
         $(this.el).fadeTo("slow", 0.33);
         this.collection.setFrom(this.currentIndex);
         this.collection.setTo(this.perPageResults);
         this.collection.fetch({
             success: function () {
                 $(self.el).fadeTo("slow", 1);
                 self.moreViewTop.show();
                 self.collection.length ? self.moreViewBottom.show() : self.moreViewBottom.hide();
                 if (self.currentIndex == 0) {
                     self.moreViewTop.hide();
                 }
             }
         });
     }
 });
 // Our overall **AppView** is the top-level piece of UI.  
 window.AppView = Backbone.View.extend({
     el: $("#main"),
     // Delegated events for creating new items, and clearing completed ones.
     events: {
         "click .btn_add_form": "addNew"
     },
     initialize: function () {
         this.editView = Entity.editView({
             model: Entity.model({
                 id_structure: Entity.idStructure
             })
         });
         this.listView = new ListView({
             collection: Entity.collection(Entity.idStructure, {
                 view: RowView
             }),
             editView: this.editView
         });
         this.toolBarView = new ToolBarView({ collection: this.listView.collection });
         this.toolBarView.bind("toolBar:autocomplete", this.selectAutocomplete, this);
         this.render();
     },
     render: function () {
         this.delegateEvents();
         return this;
     },
     addNew: function () {
         this.editView.resetModel(Entity.model({
             id_structure: Entity.idStructure
         }));
         $(this.editView.el).undelegate("div", "click");
        // this.editView.switchMode();
     },
     filterAll: function (attribute, value) {
         self.listView.collection.setFilter(attribute, value).fetch();
     },
     selectAutocomplete: function (aResult) {
         var aModel = Entity.model({
             id: aResult.id
         });
         aModel.fetch();
         var selectedModel = this.editView.resetModel(aModel);
         if (typeof this.listView.collection.get(aModel.id) == "undefined") {
             this.listView.collection.add(selectedModel);
         }
     }
 });
