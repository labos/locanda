(function ($) {

window.AppRouter = Backbone.Router.extend({
        routes: {
            "edit/:id": "editItem",
            "new": "newItem",
            "tab/:entity": "switchTab",
            "show/:entity/:id": "showEntity",
            "*actions": "defaultRoute" // Backbone will try match the route above first
        },
        initialize: function () {
            this.appView = new AppView();
            // Start Backbone history a neccesary step for bookmarkable URL's
            Backbone.history.start();
        },
        editItem: function (id) {
            // Note the variable in the route definition being passed in here
            var item = Entity.model({
                id: id
            });
            var self = this;
            typeof this.appView !== undefined || (this.appView = new AppView() );
            item.fetch({
                success: function (model, resp) {
                    self.appView.editView.resetModel(model);
                },
                error: function () {
                    alert("Error");
                    window.location.hash = '#';
                }
            });
        },
        newItem: function () {
            new EditView({
                model: Entity.model()
            });
        },
        switchTab: function ( entity ) {
        	// filter listView setting the contained collection.	
        	this.appView.filterAll(entity, "");
        	return true;
        	
        },
        showEntity: function ( entity, id ) {
        	// show in edit view a specified entity
        	this.appView.filterAll(entity, id);        	
        	$( "#tabs" ).bind( "tabscreate", function(event, ui) {
            		$("a[href$='#tab-" + entity  + "']").trigger('click');        		 
        		});
        	
        	return true;
        	
        },
        defaultRoute: function (actions) {
            ///nothing
        }
    });
    // Instantiate the router
    window.appRouter = new AppRouter;
    

})(jQuery);