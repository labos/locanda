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
 window.EditView = EditView.extend({
     events: {
         "submit form": "save",
         "click div": "switchMode"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
//        	 this.facilitiesListView = new FacilitiesListView( { collection: new RoomFacilities(null, Entity.idStructure)});
//        	 this.imagesListView = new ImagesListView( { collection: new Images(null, Entity.idStructure) } );
        	 this.id = null;
        	 this.roomTypes = new RoomTypes(null, {idStructure: Entity.idStructure});
        	 this.availableRoomTypes = [];
        	 var self = this;
             this.roomTypes.fetch({
                 success: function() {
                	 self.availableRoomTypes = self.setAvailableRoomTypes();
                 },
                 error: function() {
                	 alert("error");
                 },
             });
     },
     /**
      * Set the list of available roomTypes.
      * @return {Array} array of { value_name:"", value_id:"", selected: ""} objects.
      */
     setAvailableRoomTypes: function () {
    	 var self = this;
         _.each(self.roomTypes.models, function (val) {
        	 self.availableRoomTypes.push({
        		 value_name: val.attributes.name,
        		 value_id: val.attributes.id,
//        		 selected: (val.attributes.id == (this.model.attributes.id_roomType ?
//        			 (this.model.attributes.id_roomType ? true : false) : false)),
        		 selected: (val.attributes.id == self.model.get("id_roomType") ? true : false),
        	 });
         });
         return self.availableRoomTypes;
     },
     
     render: function () {
    	 // render main edit view
    	 var modelToRender = this.model.toJSON();
    	 // set additional attribute to display roomTypes. Only for the view.
    	 modelToRender.availableRoomTypes = this.availableRoomTypes;
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
         // add validation check
         this.$(".yform.json.full").validate();
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
     renderAssociated: function (){
    	 // check if model has changed, then update collections in associated views
//    	 if (this.model.isNew() || this.model.get("id")  != this.id){
//    		 var self = this;
//             this.id = this.model.get("id");
//             // unbind previous events raised from associated views
//             this.facilitiesListView.unbind("child:update");
//             this.imagesListView.unbind("child:update");
//             this.facilitiesListView.idParent= this.id;
//             this.imagesListView.idParent= this.id;
//             //set collection for associated views
//             this.facilitiesListView.collection.reset(this.model.get("facilities") );
//             this.imagesListView.collection.reset( this.model.get("images"));
//             // listen for changes in model on editing and fetch model if any change occur.
//             this.facilitiesListView.bind("child:update", function () {
//                 self.model.fetch({silent: true, success: function(){
//                     //set collection for associated views
//                     self.facilitiesListView.collection.reset(self.model.get("facilities") );
//                 }});    
//             });
//             this.imagesListView.bind("child:update", function () {
//                 self.model.fetch({silent: true, success: function(){
//                     //set collection for associated views
//                     self.imagesListView.collection.reset( self.model.get("images"));
//                 }});
//             });             
//             // now render associated views
//             if( $("#facilities").is(':empty') ) {
//                 $("#facilities").html( this.facilitiesListView.el ) ;
//                 $("#images").html( this.imagesListView.el);
//             }
//    	 } 
     }
 });