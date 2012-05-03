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
 * @class ThumbnailView
 * @parent Backbone.View
 * @constructor
 * Show or edit a thumbnail image.
 * @tag views
 * @author LabOpenSource
 */
window.ThumbnailView = Backbone.View.extend({
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
 * @class EditFacilityView
 * @parent Backbone.View
 * @constructor
 * Show or edit a facility.
 * @tag views
 * @author LabOpenSource
 */
window.EditFacilityView = EditView.extend({
    events: {
        "submit form": "save",
        "click div": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        // initialize thumbnail view which show an image that represent a facility
        this.thumbnailView = new ThumbnailView({
            model: new Image()
        });

    },

    render: function () {
        // render main edit view
        var modelToRender = this.model.toJSON();
        // set additional attributes to display in the template. Only for the view.
        if (this.model.isNew()) {
            // add additional fields eventually..
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
        if (!this.model.isNew()  && this.model.get("id") != this.id) {
            var self = this;
            this.id = this.model.get("id");
            this.thumbnailView.close();
            this.thumbnailView = new ThumbnailView({
                model: this.model.get("image") ? this.thumbnailView.model.set(this.model.get("image").file) : this.thumbnailView.model.set(new Image())

            });

            // listen for changes in model on editing and fetch model if any change occur.
            this.thumbnailView.bind("child:update", function () {
                self.model.fetch({
                    silent: true,
                    success: function () {
                        //set collection for associated views
                        self.thumbnailView.model.set(self.model.get("image").file);
                        //update rnd attributes when raise a change in the list collection
                        self.model.set({
                            rnd: Math.ceil(Math.random() * 500)
                        });
                        self.thumbnailView.switchMode();
                        //self.thumbnailView.render();
                        //$(self.thumbnailView.el).undelegate("div", "click");
                    }
                });
            });
            // now render associated view
            $("#thumbnail").html(this.thumbnailView.el);

        } else if (this.model.isNew()) {
            this.thumbnailView.close();
        }
    }
});