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
  * @class EditImagesFacilitiesView
  * @parent Backbone.View
  * @constructor
  * Edit a row selected in the listing.
  * @tag views
  * @author LabOpenSource
  */
 window.EditRoomView = EditView.extend({
     events: {
         "submit form": "save",
         "click div": "switchMode"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
        	 this.id = null;
        	 this.roomTypes = new RoomTypes(null, {idStructure: Entity.idStructure});
        	 this.availableRoomTypes = [];
        	 var self = this;
             this.roomTypes.fetch({
                 success: function() {
                	 self.initializeRoomTypes();
                	 // render again if success is called after render method.
                	 self.render();
                	 },
             });
             this.facilitiesListView = new FacilitiesListView({
                 collection: new RoomFacilities({}, {
                     id: this.model.get("id")
                 })
             });
             this.facilitiesListView.availableCollection = new AvailableRoomFacilities({}, {
                 id: this.model.get("id")
             });
             this.imagesListView = new ImagesListView({
                 collection: new RoomImages({}, {
                     id: this.model.get("id")
                 })
             });
             this.imagesListView.availableCollection = new AvailableRoomImages({}, {
                 id: this.model.get("id")
             });
             this.id = null;
             
             
     },
     /**
      * Set the list of available roomTypes.
      * @return {Array} array of { value_name:"", value_id:"", selected: ""} objects.
      */
     initializeRoomTypes: function () {
    	 var self = this;
         _.each(self.roomTypes.models, function (val) {
        	 self.availableRoomTypes.push({
        		 value_name: val.attributes.name,
        		 value_id: val.attributes.id,
        		 selected: false,
        	 });
         });
         return self.availableRoomTypes;
     },
     
     setRoomTypes: function (id_roomType) {
    	 var self = this;
         _.each(self.availableRoomTypes, function (val) {
         val.selected = false;
         if (val.value_id == id_roomType) {
        	 val.selected = true;
         	}   		 
         });
         return self.availableRoomTypes;
     },
     
     render: function () {
    	 // render main edit view
    	 var modelToRender = this.model.toJSON();
    	 // set additional attribute to display roomTypes. Only for the view.
    	 modelToRender.availableRoomTypes = this.model.get("roomType")? this.setRoomTypes(this.model.get("roomType").id) : this.setRoomTypes( null );
         // check for empty roomtypes
    	 if( $.isEmptyObject(modelToRender.availableRoomTypes)){
    		 this.indexTemplate = $("#no-roomtype-template");
    	 }
    	 $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
         // add validation check
         this.$(".yform").validate();
         // renderize buttons
         $(".btn_save").button({
             icons: {
                 primary: "ui-icon-check"
             }
         });
         //button for form reset  
         $(".btn_reset").button({
             icons: {
                 primary: "ui-icon-arrowreturnthick-1-w"
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
     /**
      * Render associated views
      */
     renderAssociated: function () {
         // check if model has changed, then update collections in associated views
         if (this.model.isNew()) {
             //disable sliders when you add a new RoomType
             this.facilitiesListView.disable();
             this.imagesListView.disable();

         } else if (this.model.get("id") != this.id) {
             this.id = this.model.get("id");
             this.resetAssociated(this.id);
             // now render associated views
             $("#facilities").html(this.facilitiesListView.el);
             $("#images").html(this.imagesListView.el);

         }
     },
     /**
      * Reset slider views collections
      */
     resetAssociated: function () {
         var self = this;
         // unbind previous events raised from associated views
         this.facilitiesListView.unbind("child:update");
         this.imagesListView.unbind("child:update");
         this.facilitiesListView.idParent = this.id;
         this.imagesListView.idParent = this.id;
         //set collection for associated views
         this.facilitiesListView.collection.setIdWrapper(this.id);
         this.facilitiesListView.availableCollection.setIdWrapper(this.id);
         this.facilitiesListView.collection.fetch();
         this.imagesListView.collection.setIdWrapper(this.id);
         this.imagesListView.availableCollection.setIdWrapper(this.id);
         this.imagesListView.collection.fetch();
         // listen for changes in model on editing and fetch model if any change occur.
         this.facilitiesListView.bind("child:update", function () {
             self.model.fetch({
                 silent: true,
                 success: function () {
                     //set collection for associated views
                     self.facilitiesListView.collection.fetch();
                     $(self.facilitiesListView.el).undelegate("div", "click");

                 }
             });


         });
         this.imagesListView.bind("child:update", function () {
             self.model.fetch({
                 silent: true,
                 success: function () {
                     //set collection for associated views
                     self.imagesListView.collection.fetch();
                     $(self.imagesListView.el).undelegate("div", "click");

                 }
             });
         });


     },
     clear: function(){
		 this.resetModel(Entity.model({
             id_structure: Entity.idStructure
    		 }));
		 $(this.el).undelegate("div", "click");
    	 
     }
 });