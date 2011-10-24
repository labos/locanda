(function ($) {

/*
	window.ConventionView = Backbone.View.extend({
		el: $("#conventions-list"),
		tagName: "ul",
	id: "conventions-list",
	model: new Convention,
	initialize: function () {
	this.model.bind('change', this.render, this);
	this.model.bind('destroy', this.remove, this);
	this.collection = new Conventions( null, { view: this });
	this.collection.fetch({success: function(){conventionView.render();}, 
		error: function(xhr, error){ alert("error convention retrieving!")}});
	//Create a convention collection when the view is initialized.
	//Pass it a reference to this view to create a connection between the two
	},
	events: {
	"click #add-convention":  "showPrompt",
	"click .item_list": "edit",
	"click .item-destroy": "clear"
	},
	edit: function (e) {
			var selectedId = $(e.currentTarget).find('input[name="id"]').val();
			this.model = this.collection.get(selectedId);
			this.input = $("#conventionFormName");
		 $("#item_list_container").html( Mustache.to_html($("#container-template").html(),this.model.toJSON()) );
		 $("#item_list_container").find(".btn_save").click( function(){
			 this.model.save({name: this.input});
		 })
		
	},
	
	showPrompt: function () {
	var convention_name = prompt("Who is your convention?");
	var convention_model = new Convention({ name: convention_name });
	//Add a new friend model to our friend collection
	this.conventions.add( convention_model );
	},
	render: function() {
	    var rendered = this.collection.map(function(current) {
	    	current.set({description: current.get("description").substring(0,20)});
	    	 return Mustache.to_html($("#first-template").html(),current.toJSON());
	    	
	    });
	    this.el.html(rendered.reduce(function(memo,str) { return memo + str }, ''));
	    return this;
	  },
	addConventionList: function (model) {
	//The parameter passed is a reference to the model that was added
	$("#conventions-list").append("<li>" + model.get('activationCode') + "</li>");
	//Use .get to receive attributes of the model
	},
	 close: function() {
	      this.model.save({name: this.input.val()});
	      $(this.el).removeClass("editing");
	    },
	
	  clear: function() {
	      this.model.destroy();
	    },
      remove: function() {
	        $(this.el).remove();
	    },
	  updateOnEnter: function(e) {
	        if (e.keyCode == 13) this.close();
	      }

	
	});
	

	

	var conventionView = new ConventionView();
	
	
	*/
	  window.ConventionView = Backbone.View.extend({

		    //... is a list tag.
		    tagName: "li",

		    // Cache the template function for a single item.
		    //--template: _.template($('#item-template').html()),

		    // The DOM events specific to an item.
		    events: {
<<<<<<< OURS
		  	 "click .item_list": "edit",
		      "click span.item-destroy" : "remove",
		      "keypress .todo-input" : "updateOnEnter"
=======
		    			"click span.item-destroy" : "remove",
		    			"click .item_list": "edit"

>>>>>>> THEIRS
		    },

		    // The TodoView listens for changes to its model, re-rendering. Since there's
		    // a one-to-one correspondence between a **Todo** and a **TodoView** in this
		    // app, we set a direct reference on the model for convenience.
		    initialize: function() {
<<<<<<< OURS
		      _.bindAll(this, 'render', 'close');
=======
		    	
>>>>>>> THEIRS
		      this.model.bind('change', this.render);
<<<<<<< OURS
		      this.model.view = this;
=======
		      this.model.bind('remove', this.unrender);

>>>>>>> THEIRS
		    },

		    // Re-render the contents of the todo item.
		    render: function() {
		      $(this.el).html(Mustache.to_html($("#first-template").html(),this.model.toJSON()));
		     //-- this.setContent();
		      return this;
		    },

<<<<<<< OURS
		    // To avoid XSS (not that it would be harmful in this particular app),
		    // we use `jQuery.text` to set the contents of the todo item.
		    setContent: function() {
		      var content = this.model.get('content');
		      this.$('.todo-content').text(content);
		      this.input = this.$('.todo-input');
		      this.input.bind('blur', this.close);
		      this.input.val(content);
		    },

		    // Toggle the `"done"` state of the model.
		    toggleDone: function() {
		      this.model.toggle();
		    },

=======
>>>>>>> THEIRS
		    // Switch this view into `"editing"` mode, displaying the input field.
<<<<<<< OURS
		    edit: function() {
			 
		      new ConventionEditView({model: this.model});
=======
		    edit: function(event) {
		    	var $target = $(event.target);
		    	  if( ! $target.is("span.item-destroy") ) {
		    		  	//populate convention edit global view
			    		ConventionEdit.resetModel(this.model);
		    	  }



>>>>>>> THEIRS
		    
		    },

<<<<<<< OURS
		    // Close the `"editing"` mode, saving changes to the todo.
		    close: function() {
		      this.model.save({content: this.input.val()});
		      $(this.el).removeClass("editing");
=======
			

		    unrender: function(){
 	    		//clean up events raised from the view
	    		this.unbind();
	    		//clean up events from the DOM
	    		$(this.el).remove();
		    },
	
		    // Remove this view from the DOM.
		    remove: function() {
		    	
		    	
		    	 var self = this;
		    	
		    	 //begin test
		    	 //  delete this.model;
		    	 //end test
		    	 if(confirm($.i18n("alertDelete"))){
		    		 
		 	    	this.model.destroy({success: function(){
			    	/*	
		 	    		//clean up events raised from the view
			    		self.unbind();
			    		//clean up events from the DOM
			    		$(self.el).remove();
			    		
*/
			    		// now check if deleted model is in editing mode
			    		if( ConventionEdit && ConventionEdit.get("id")==this.model.get("id") ){
				    		ConventionEdit.clear();
			    			
			    		}
			    	     //clean up events bound from the model
			//    	     self.model.unbind("change", self.render);
			    	},
			    	error: function(){
			    		
			    		$().notify(this.alertKO, $.i18n("seriousErrorDescr"));
			    					
			    	}
			    	});
		    		 
		    	 }		     
>>>>>>> THEIRS
		    },

		    // If you hit `enter`, we're through editing the item.
		    updateOnEnter: function(e) {
		      if (e.keyCode == 13) this.close();
		    },

		    // Remove this view from the DOM.
		    remove: function() {
		    	this.model.destroy({success: function(){
		    		$(this.el).remove();
		    	},
		    	error: function(){
		    		   new App.Views.Error();
		    	}
		    	});
		     
		    },

		    // Remove the item, destroy the model.
		    clear: function() {
		      this.model.clear();
		    }

		  });
	
	
	
	
	
	
	
	// Our overall **AppView** is the top-level piece of UI.
	  window.AppView = Backbone.View.extend({

	    // Instead of generating a new element, bind to the existing skeleton of
	    // the App already present in the HTML.
	    el: $("#conventionapp"),

	    // Our template for the line of statistics at the bottom of the app.
	  //--  statsTemplate: _.template($('#stats-template').html()),

	    // Delegated events for creating new items, and clearing completed ones.
	    events: {
	      "keypress #new-todo": "createOnEnter",
	      "keyup #new-todo": "showTooltip",
	      "click .todo-clear a": "clearCompleted"
	    },

	    // At initialization we bind to the relevant events on the `Todos`
	    // collection, when items are added or changed. Kick things off by
	    // loading any preexisting todos that might be saved in *localStorage*.
	    initialize: function() {
	      _.bindAll(this, 'addOne', 'addAll', 'render');

	     //-- this.input = this.$("#new-todo");

	      this.collection.bind('add', this.addOne);
	      this.collection.bind('refresh', this.addAll);
	      this.collection.bind('all', this.render);

	      this.collection.fetch();

	      this.el.show();
	     //--$('#instructions').show();
	    },

	    // Re-rendering the App just means refreshing the statistics -- the rest
	    // of the app doesn't change.
	    render: function() {
	     /* var done = this.collection.done().length;
	      this.$('#todo-stats').html(this.statsTemplate({
	        total: this.collection.length,
	        done: this.collection.done().length,
	        remaining: this.collection.remaining().length
	      }));
	      
	      */
	    },

	    // Add a single todo item to the list by creating a view for it, and
	    // appending its element to the `<ul>`.
	    addOne: function(item) {
	      var view = new ConventionView({model: item});
	      this.$("#conventions-list").append(view.render().el);
	    },

	    // Add all items in the **Todos** collection at once.
	    addAll: function() {
	      this.collection.each(this.addOne);
	    },

	    // Generate the attributes for a new Todo item.
	    newAttributes: function() {
	      return {
	        content: this.input.val(),
	        order: this.collection.nextOrder(),
	        done: false
	      };
	    },

	    // If you hit return in the main input field, create new **Todo** model,
	    // persisting it to *localStorage*.
	    createOnEnter: function(e) {
	      if (e.keyCode != 13) return;
	      this.collection.create(this.newAttributes());
	      this.input.val('');
	    },

	    // Clear all done todo items, destroying their models.
	    clearCompleted: function() {
	      _.each(this.collection.done(), function(todo){ todo.clear(); });
	      return false;
	    },

	    // Lazily show the tooltip that tells you to press `enter` to save
	    // a new todo item, after one second.
	    showTooltip: function(e) {
	      var tooltip = this.$(".ui-tooltip-top");
	      var val = this.input.val();
	      tooltip.fadeOut();
	      if (this.tooltipTimeout) clearTimeout(this.tooltipTimeout);
	      if (val == '' || val == this.input.attr('placeholder')) return;
	      var show = function(){ tooltip.show().fadeIn(); };
	      this.tooltipTimeout = _.delay(show, 1000);
	    }

	  });


	  window.App = new AppView({collection: new Conventions( null, { view: ConventionView })});
	  
	  
	  window.ConventionEditView = Backbone.View.extend({
		  el: $("#item_list_container"),
		    events: {
		        "submit form": "save"
		    },
		    
		    initialize: function() {
		        this.model.bind('change', this.render);
		        this.render();
		    },
		    
		    save: function(e) {
		    	e.preventDefault();
		        var self = this;
		        var msg = this.model.isNew() ? 'Successfully created!' : "Saved!";
		        
		        this.model.save({ name: this.$('input[name="convention.name"]').val(), activationCode: this.$('input[name="convention.activationCode"]').val() }, {
		            success: function(model, resp) {
		               //--- new App.Views.Notice({ message: msg });
		               //--- Backbone.history.saveLocation('documents/' + model.id);
		            	$().notify(this.alertOK, $.i18n("congratulation"));
		            },
		            error: function() {
		            	$().notify(this.alertKO, $.i18n("seriousErrorDescr"));
		            	
		            }
		        });
		        
		        return false;
		    },
		    
		    render: function() {
		    	
				 $(this.el).html( Mustache.to_html($("#container-template").html(),this.model.toJSON()) );
	        
		        // use val to fill in title, for security reasons
		        //--this.$('[name=title]').val(this.model.get('title'));
		        
		        this.delegateEvents();
		    }
		});
	
	  window.NoticeView = Backbone.View.extend({
		    className: "success",
		    displayLength: 5000,
		    defaultMessage: '',
		    
		    initialize: function() {
		        _.bindAll(this, 'render');
		        this.message = this.options.message || this.defaultMessage;
		        this.render();
		    },
		    
		    render: function() {
		        var view = this;
		        
		        $(this.el).html(this.message);
		        $(this.el).hide();
		        $('#notice').html(this.el);
		        $(this.el).slideDown();
		      /*  $.doTimeout(this.displayLength, function() {
		            $(view.el).slideUp();
		            $.doTimeout(2000, function() {
		                view.remove();
		            });
		        });*/
		        
		        return this;
		    }
		});

<<<<<<< OURS
		window.ErrorView = NoticeView.extend({
		    className: "error",
		    defaultMessage: 'Uh oh! Something went wrong. Please try again.'
		});
=======
	  //create a shareable conventionedit view for edit every model selected from the list
	  window.ConventionEdit = new ConventionEditView({model: new Convention});
	
	
	// Our overall **AppView** is the top-level piece of UI.
	  window.AppView = Backbone.View.extend({

	    // Instead of generating a new element, bind to the existing skeleton of
	    // the App already present in the HTML.
	    el: $("#main"),


	    // Delegated events for creating new items, and clearing completed ones.
	    events: {
	      "click .btn_add_form": "addNew"
	    },

	    // At initialization we bind to the relevant events on the `this.collection`
	    // collection, when items are added or changed. Kick things off by
	    initialize: function() {
	      this.collection.bind('add', this.addOne, this);
	      this.collection.bind('remove', this.removeOne, this);
	      this.collection.bind('reset', this.addAll, this);
	      this.collection.bind('all', this.render, this);
	      this.collection.fetch();
	      //attach collection to ConventionEdit
	      ConventionEdit.collection = this.collection;

	    },

	    // Re-rendering the App just means refreshing the view -- the rest
	    // of the app doesn't change.
	    render: function() {
	    	
	    	return this;
	    },
	    addNew: function() {
	    	//--new ConventionEditView({ model: new Convention() });
	    	ConventionEdit.resetModel(new Convention());
		    },
	    // Add a single todo item to the list by creating a view for it, and
	    // appending its element to the `<ul>`.
	    addOne: function(item) {
	      var view = new ConventionRowView({model: item});
	      this.$("#conventions-list").append(view.render().el);
	    },

	    // Add all items in the **Todos** collection at once.
	    addAll: function() {
	    	this.$("#conventions-list").empty();

	      this.collection.each(this.addOne);
	    },
	    removeOne: function(item) {
	    	this.addAll();
		    },
  

	    // Clear all items, destroying their models.
	    clearCompleted: function() {
	      _.each(this.collection, function(item){ item.clear(); });
	      return false;
	    },


	  });

	  


		
		window.AppRouter = Backbone.Router.extend({
	        routes: {
	            "edit/:id": "editItem",
	            "delete/:id": "deleteItem",
	            "new": "newItem",
	            "*actions": "defaultRoute" // Backbone will try match the route above first
	        },
	        editItem: function( id ) {
	            // Note the variable in the route definition being passed in here
	            var item = new Convention({ id: id });
	            item.fetch({
	                success: function(model, resp) {
	                	ConventionEdit.resetModel(model);
	                },
	                error: function() {
	                    new Error({ message: 'Could not find that document.' });
	                    window.location.hash = '#';
	                }
	            });
	        },
	        deleteItem: function( id ){
	        	//remove an item
	        	
	        },
	        newItem: function( ){
	        	new ConventionEditView({ model: new Convention() });
	        },
	        defaultRoute: function( actions ){
	            ///nothing
	        	
	        }
	    });
		
		window.myAppView =  new AppView({collection: new Conventions( null, { view: ConventionRowView })});
	    // Instantiate the router
	    var app_router = new AppRouter;
	    // Start Backbone history a neccesary step for bookmarkable URL's
	    Backbone.history.start();
>>>>>>> THEIRS
		
		
})(jQuery);

