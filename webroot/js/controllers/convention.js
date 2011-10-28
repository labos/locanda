(function ($) {


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
        indexTemplate: $("#first-template"),

        // The DOM events specific to an item.
        events: {
            "click span.item-destroy": "remove",
            "click .item_list": "edit"

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
            if (!$target.is("span.item-destroy")) {

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
                        $().notify(this.alertOK, $.i18n("congratulation"));
                    },
                    error: function () {

                        $().notify(this.alertKO, $.i18n("seriousErrorDescr"));

                    }
                });

            }
        },

        // clear all attributes from the model
        clear: function () {
            this.model.clear();
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
        el: $("#item_list_container"),
        indexTemplate: $("#container-template"),
        events: {
            "submit form": "save",
            "keypress input:text": "updateOnEnter",

        },

        initialize: function () {

            this.model.bind('change', this.render, this);
            this.render();
        },
        // save new item or update existing item.
        save: function (e) {
            e.preventDefault();
            var self = this;
            var msg = this.model.isNew() ? 'Successfully created!' : "Saved!";
            var item = this.model.clone();
            
            item.save({
                name: this.$('input[name="convention.name"]').val(),
                activationCode: this.$('input[name="convention.activationCode"]').val()
            }, {
                success: function (model, resp) {
                    //--- Backbone.history.saveLocation('documents/' + model.id);
                    self.model = model;
                    this.model.isNew() ? this.model.collection.add(self.model) : this.model.collection.fetch();
                    $().notify(this.alertOK, msg);

                },
                error: function () {
                    $().notify(this.alertKO, $.i18n("seriousErrorDescr") + ' ' + msg);

                }
            });



            return false;
        },

        render: function () {

            $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
            this.$(".yform.json.full").validate();
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

            this.model.unbind("change", this.render, this);
            this.model = amodel;
            this.model.bind('change', this.render,this);
            this.render();

        },

        close: function () {
            this.remove();
            //clean up events raised from the view
            this.unbind();
            //clean up events bound from the model
            this.model.unbind("change", this.render);

        }
    });




    window.ListView = Backbone.View.extend({

        // Instead of generating a new element, bind to the existing skeleton of
        // the App already present in the HTML.
        id: "conventions-list",
        editView: null,



        // At initialization we bind to the relevant events on the `this.collection`
        // collection, when items are added or changed. Kick things off by
        initialize: function (options) {
            options['editView'] || (options['editView'] = new EditView({
                model: new Convention
            }));
            this.editView = options['editView'];

            _.bindAll(this, "addOne", "editOne");
            this.collection.bind('add', this.addOne, this);
            this.collection.bind('remove', this.removeOne, this);
            this.collection.bind('reset', this.addAll, this);
            this.collection.bind('destroy', this.removeOne, this);
            this.collection.bind('all', this.render, this);
            this.collection.fetch();



        },

        // Re-rendering the App just means refreshing the view -- the rest
        // of the app doesn't change.
        render: function () {

            return this;
        },

        // Add a singleitem to the list by creating a view for it, and
        // appending its element to the `<ul>`.
        addOne: function (item) {
            var view = new RowView({
                model: item
            });
            view.bind("row:edit", this.editOne);
            view.model.collection = this.collection;
            $("#conventions-list").append(view.render().el);
        },

        // Add all items in the collection at once.
        addAll: function () {
            this.$("#conventions-list").empty();

            this.collection.each(this.addOne);
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
        }


    });

    // Our overall **AppView** is the top-level piece of UI.  
    window.AppView = Backbone.View.extend({

        el: $("#main"),

        // Delegated events for creating new items, and clearing completed ones.
        events: {
            "click #schiaccia": "addNew"
        },

        initialize: function () {

            this.editView = new EditView({
                model: new Convention
            });
            this.listView = new ListView({
                collection: new Conventions(null, {
                    view: RowView
                }),
                editView: this.editView
            });
            this.render();

        },
        render: function () {
            this.delegateEvents();
            return this;
        },


        addNew: function () {

            this.editView.resetModel(new Convention());
        }
    });


    window.AppRouter = Backbone.Router.extend({
        routes: {
            "edit/:id": "editItem",
            "new": "newItem",
            "*actions": "defaultRoute" // Backbone will try match the route above first
        },

        initialize: function () {
            window.myAppView = new AppView();
            // Start Backbone history a neccesary step for bookmarkable URL's
            Backbone.history.start();

        },
        editItem: function (id) {
            // Note the variable in the route definition being passed in here
            var item = new Convention({
                id: id
            });
            item.fetch({
                success: function (model, resp) {
                    myAppView.editView.resetModel(model);
                },
                error: function () {
                    alert("Error");
                    window.location.hash = '#';
                }
            });
        },
        newItem: function () {
            new EditView({
                model: new Convention()
            });
        },
        defaultRoute: function (actions) {
            ///nothing
        }
    });



    // Instantiate the router
    var app_router = new AppRouter;



})(jQuery);