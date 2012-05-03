/*******************************************************************************
 *
 *  Copyright 2012 - Sardegna Ricerche, Distretto ICT, Pula, Italy
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
 * @class ImageView
 * @parent Backbone.View
 * @constructor
 * Show or edit an image.
 * @tag views
 * @author LabOpenSource
 */
window.ImageView = Backbone.View.extend({
    indexTemplate: $("#image-view-template"),
    events: {
        "click div": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        this.render();

    },
    render: function () {
        var modelToRender = this.model.toJSON();
        modelToRender.rnd = Math.ceil(Math.random() * 500);
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
        if (this.$("#uploadFacility").length) {
            this.$("#uploadFacility").uploadImage(this);
        }

        this.delegateEvents();
        return this;
    },
    close: function () {
        this.remove();
        this.unbind();
        this.model.unbind("change", this.render);
    },
    switchMode: function () {
    	 if( this.indexTemplate.attr("id") == "image-edit-template" ){
    		 this.indexTemplate = $("#image-view-template");
    		 $(".overlay").remove();
        	 $(this.el).removeClass("edit-state-box");
        	 this.render();
        	 $($.fn.overlay.defaults.container).css('overflow', 'auto');
    	 }
    	 else{
        	 
    	        this.indexTemplate = $("#image-edit-template");
    	        this.render();
    	        var self = this;
    	        $(this.el).undelegate("div", "click");
    	        $('<div></div>').overlay({
    	            effect: 'fade',
    	            onShow: function () {
    	                var overlay = this;
    	                $(self.el).addClass("edit-state-box");
    	                $(this).click(function () {
    	                    if (confirm($.i18n("alertExitEditState"))) {
    	                        $(self.el).removeClass("edit-state-box");
    	                        self.indexTemplate = $("#image-view-template");
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
 * @class EditImageView
 * @parent Backbone.View
 * @constructor
 * Show or edit a image.
 * @tag views
 * @author LabOpenSource
 */
window.EditImageView = EditView.extend({
    events: {
        "submit form": "save",
        "click div": "switchMode"
    },
    indexTemplate: $("#view-template"),
    initialize: function () {
        this.model.bind('change', this.render, this);
        // initialize image view which show an image that represent a facility
        this.imageView = new ImageView({
            model: new Image()
        });

    },

    render: function () {
        // render main edit view
        var modelToRender = this.model.toJSON();
        modelToRender.idStructure = Entity.idStructure;
        // check if model is new then render the new template.
        if (this.model.isNew()) {
            this.renderNew();
        }

        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
        if (this.$("#uploadFacility").length) {
            this.$("#uploadFacility").uploadImage(this);
        }
        // add validation check
        this.$(".yform").validate();
        // renderize buttons
        $(".btn_save").button({
            icons: {
                primary: "ui-icon-check"
            }
        });

        $(".btn_reset").button({
            icons: {
                primary: "ui-icon-arrowreturnthick-1-w"
            }
        }).click(function (event) {
            var validator = $(this).parents(".yform").validate();
            validator.resetForm();
            return false;
        });
        this.renderAssociated();
        this.delegateEvents();
        return this;
    },
    /**
     * Render associated views
     */
    renderAssociated: function () {
        // check if model has changed or is new, then update collections in associated views
        if (!this.model.isNew() && this.model.get("id") != this.id) {
            var self = this;
            // set id property of view as model id attribute
            this.id = this.model.get("id");
            // close imageView opened eventually
            this.imageView.close();
            // create a new image view
            this.imageView = new ImageView({
                model: this.model.get("file") ? this.imageView.model.set(this.model.get("file")) : this.imageView.model.set(new File())

            });

            // listen for changes in model on editing and fetch model if any change occur.
            this.imageView.bind("child:update", function () {
                self.model.fetch({
                    silent: false,
                    success: function () {
                        //set collection for associated views
                        self.imageView.model.set(self.model.get("file"));
                        self.model.set({
                            rnd: Math.ceil(Math.random() * 500)
                        });
                        self.imageView.switchMode();
                        //$(self.imageView.el).undelegate("div", "click");
                    }
                });
            });

            // now render associated view
            $("#image").html(this.imageView.el);

        } else if (this.model.isNew()) {
            this.imageView.close();
        }

    },
    /**
     * Render associated views
     */
    renderNew: function () {
        var self = this;
        this.bind("child:update", function (event, paramId, paramOther) {
            self.model.set({
                id: paramId
            }, {
                silent: true
            });
            self.model.fetch({
                error: function () {
                    alert($.i18n("seriousError"));
                },
                success: function () {
                    self.indexTemplate = $("#edit-template");
                    self.collection.add(self.model);
                    self.switchMode();
                }
            });
        });
        // set the right template for new action
        this.indexTemplate = $("#new-template");

    }

});