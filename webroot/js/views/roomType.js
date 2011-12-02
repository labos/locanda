 window.ImagesFacilitiesView = Backbone.View.extend({
 //    el: $("#carousel-small"),
     indexTemplate: null,
     events: {
         "click .ui-rcarousel-next": "next",
         "click .ui-rcarousel-prev": "prev",
         "click .save-elem": "saveElement",
         "click span.delete-elem": "removeElement",
         "click span.sub-edit" :"switchMode"
     },
     initialize: function () {
    	 _.bindAll(this, "next", "prev", "removeElement");
    	 this.page = 0;
         this.render();
     },
     render: function () {
         $(this.el).html(this.indexTemplate.html());
         if(this.$("#uploadFacility").length){
             	 this.$("#uploadFacility").uploadImage( );
         }
         this.delegateEvents();
         return this;
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
     removeElement: function(){
    	 
     },
     switchMode: function () {
     }
 });
 
 window.FacilitiesView = ImagesFacilitiesView.extend({
     initialize: function (mode) {
    	 mode || ( mode = "view");
    	 this.indexTemplate  = $("#facilities-" + mode + "-template");
    	 _.bindAll(this, "next", "prev");
    	 this.page = 0;
  //  	 this.el = $("#carousel-small");
     //    this.render();
     },
     
     saveElement: function(){
    	 
    	 
     },
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "facilities-view-template") ? $("#facilities-edit-template") : $("#facilities-view-template");
         this.render();
}
 });
 
 window.ImagesView = ImagesFacilitiesView.extend({
     initialize: function (mode) {
    	 mode || ( mode = "view");
    	 this.indexTemplate  = $("#images-" + mode + "-template");
    	 _.bindAll(this, "next", "prev");
    	 this.page = 0;
    //	 this.el = $("#carousel-large");
      //   this.render();
     },
     removeElement: function(){
         if (confirm($.i18n("alertDelete"))) {
        	 this.trigger("carousel-large:remove", this);
         } 
     },
     saveElement: function(){
    	 
    	 
     },
     
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "images-view-template") ? $("#images-edit-template") : $("#images-view-template");
         this.render();
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
     initialize: function () {
         this.model.bind('change', this.render, this);
        	 this.facilitiesView = new FacilitiesView();
        	 this.imagesView = new ImagesView( );
          this.render();
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
         $("#facilities").html( this.facilitiesView.render("view").el ) ;
         $("#images").html( this.imagesView.render("view").el);
         this.delegateEvents();
         return this;
     },
     switchMode: function () {
         this.indexTemplate = (this.indexTemplate.attr("id") == "edit-template") ? $("#view-template") : $("#edit-template");
         this.facilitiesView.switchMode("view");
         this.imagesView.switchMode("view");
         this.render();
     }
	 
 });