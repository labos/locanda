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
 * @class ImagesFacilitiesView
 * @parent Backbone.View
 * @constructor
 * Class to show a single facility
 * @tag views
 * @author LabOpenSource
 */
window.FacilityRowView = Backbone.View.extend({
    //list of tags.
    tagName: "li",
    indexTemplate: $("#facility-row-template"),
    // The DOM events specific to an row.
    events: {
        "click input.choose-elem": "choose"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        //this.model.bind('destroy', this.unrender, this);
    },
    /**
     * Re-render the contents of the facility item.
     */
    render: function () {
        if (this.model.isNew()) {
            this.indexTemplate.find(".choose-elem").prop("checked", "");
        }
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
        return this;
    },
    /**
     * Assign or de-assign facility to the parent model by checkbox click
     * @param {Object} event triggered from checkbox pushed.
     */
    choose: function (event) {
        //send cheched/unchecked facility
        var $target = $(event.target),
            self = this;
   	 // prevents the event from bubbling up the DOM tree
   	 if ( typeof event !== 'undefined' ) {
   		 event.stopPropagation();
   	 }
        if (!$target.is(":checked")) {
        	$target.attr("disabled", true);
            this.model.destroy({
                success: function () {
                    $.jGrowl($.i18n("facilityUnCheckedSucces"), {
                        header: this.alertOK, position: 'top-right' 
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // re-check if destroy fail
                    $target.prop("checked", "checked");
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl($.i18n("seriousErrorDescr"), {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true
                    });
                }
            });
        } else {
        	$target.attr("disabled", true);
            this.model.save(null, {
                success: function () {
                    // trigger an update event.
                    self.trigger("child:update", self);
                    $.jGrowl($.i18n("facilityCheckedSuccess"), {
                        header: this.alertOK, position: 'top-right' 
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl($.i18n("seriousErrorDescr"), {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true
                    });
                }
            });
        }
    },
    unrender: function () {
        this.model.unbind('change', this.render);
        this.model.unbind('destroy', this.unrender);
        //clean up events raised from the view
        this.unbind();
        //clean up events from the DOM
        $(this.el).remove();
    },
    // clear all attributes from the model
    clear: function () {
        this.model.clear();
    },
    switchMode: function () {
        this.indexTemplate = (this.indexTemplate.attr("id") == "facility-row-template") ? $("#facility-row-edit-template") : $("#facility-row-template");
        this.render();
    }
});
/*
 * @class ImageRowView
 * @parent Backbone.View
 * @constructor
 * Class to show a single image
 * @tag views
 * @author LabOpenSource
 */
window.ImageRowView = Backbone.View.extend({
    //list of tags.
    tagName: "li",
    indexTemplate: $("#image-row-template"),
    // The DOM events specific to an row.
    events: {
        "click input.choose-elem": "choose"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        //this.model.bind('destroy', this.unrender, this);
    },
    /**
     * Re-render the contents of the facility item.
     */
    render: function () {
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), this.model.toJSON()));
        return this;
    },
    // 
    /**
     * Assign or de-assign facility to the parent model by checkbox click
     * @param {Object} event triggered from checkbox pushed.
     */
    choose: function (event) {
        //send cheched/unchecked roomTypeFacility
        var $target = $(event.target),
            self = this;
      	 // prevents the event from bubbling up the DOM tree
      	 if ( typeof event !== 'undefined' ) {
      		 event.stopPropagation();
      	 }
        if (!$target.is(":checked")) {
        	$target.attr("disabled", true);
            this.model.destroy({
                success: function () {
                    $.jGrowl($.i18n("imageUnCheckedSuccess"), {
                        header: this.alertOK, position: 'top-right' 
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // re-check if destroy fail
                    $target.prop("checked", "checked");
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl($.i18n("seriousErrorDescr"), {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true
                    });
                }
            });
        } else {
        	$target.attr("disabled", true);
            this.model.save(null, {
                success: function () {
                    // trigger an update event.
                    self.trigger("child:update", self);
                    $.jGrowl($.i18n("imageCheckedSuccess"), {
                        header: this.alertOK, position: 'top-right' 
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $target.prop("checked", "");
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl($.i18n("seriousErrorDescr"), {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true
                    });
                }
            });
        }
    },
    unrender: function () {
        this.model.unbind('change', this.render);
        this.model.unbind('destroy', this.unrender);
        //clean up events raised from the view
        this.unbind();
        //clean up events from the DOM
        $(this.el).remove();
    },
    // clear all attributes from the model
    clear: function () {
        this.model.clear();
    },
    switchMode: function () {
        this.indexTemplate = (this.indexTemplate.attr("id") == "image-row-template") ? $("#image-row-edit-template") : $("#image-row-template");
        this.render();
    }
});
/*
 * @class ImagesFacilitiesView
 * @parent Backbone.View
 * @constructor
 * Class for listing of the images or facilities.
 * @tag views
 * @author LabOpenSource
 */
window.ImagesFacilitiesView = Backbone.View.extend({
    indexTemplate: null,
    events: {
        "click .ui-rcarousel-next": "next",
        "click .ui-rcarousel-prev": "prev",
        "click .save-elem": "saveElement",
        "click div": "switchMode"
    },
    initialize: function (options) {
        _.bindAll(this, "next", "prev", "removeOne", "addOne");
        this.page = 0;
        // collection of facilities or images to check
        this.availableCollection = null;
        // set a copy of collection
        this.initCollection = this.collection;
        this.idParent = null;
        this.rowViews = [];
        this.render();
    },
    render: function () {
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), {
            id_parent: this.idParent,
            id_structure: ""
        }));
        $(".btn_add").button({
            icons: {
                primary: "ui-icon-gear"
            }
        });
        if (this.page < 0) {
            this.enablePrev();
        };
        this.addAll();
        (typeof this.idParent !== 'undefined' && this.idParent) ? $(this.el).show() : $(this.el).hide();
        this.delegateEvents();
        return this;
    },
    /**
     * Set collection with available images or facilities (to check or uncheck).
     */
    setAvailables: function () {
        var self = this;
        this.collection.unbind('reset', this.render);
        this.collection.unbind('remove', this.removeOne);
        // reset starting page from 0
        this.availableCollection.setFrom(0);
        this.collection = this.availableCollection;
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        this.collection.fetch({
            silent: true,
            success: function () {
                self.render();
            }
        });
    },
    /**
     * Set collection with checked images or facilities.
     */
    setChecked: function () {
        /* override in extended classes */
    },
    /**
     * Add all items in the collection at once.
     */
    addAll: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        //set slider content
        (this.collection.length > 0)? this.$("ul").empty() : this.$("ul").append(this.getEmptyMessage());
        this.rowViews = [];
        this.collection.each(this.addOne);
        this.checkNumPages(null)? this.enableNext() : this.disableNext();
    },
    addOne: function (item) {
    	/* override in extended classes */
        if (item.isNew) {
            return;
        }
    },
    /**
     * Show next facilities or images page.
     */
    next: function () {
        if (this.checkNumPages(this.page)) {
            this.page--;
            this.collection.setFrom(-6 * this.page);
            // calculate width used by elements contained in wrapper
            var self = this;
            $(".wrapper ul", self.el).css("opacity", 0.25);
            $(".add-new", this.el).addClass("slider-loader");
            this.collection.fetch({
                silent: true,
                success: function () {
                    $(".add-new", self.el).removeClass("slider-loader");
                    $(".wrapper ul", self.el).css("opacity", 1);
                    self.enablePrev();
                    self.addAll();
                    $(".add-new", self.el).removeClass("slider-loader");
                    $(".wrapper ul", self.el).css("opacity", 1);
                },
                error: function () {
                    $.jGrowl($.i18n("seriousErrorDescr"), {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true
                    });
                    $(".add-new", self.el).removeClass("slider-loader");
                }
            });
        }
    },
    /**
     * Show previous facilities or images page.
     */
    prev: function (event) {
        if (this.page < 0) {
            this.page++;
            var self = this;
            this.collection.setFrom(-6 * this.page);
            $(".wrapper ul", self.el).css("opacity", 0.25);
            $(".add-new", this.el).addClass("slider-loader");
            this.collection.fetch({
                silent: true,
                success: function () {
                    $(".add-new", this.el).removeClass("slider-loader");
                    $(".wrapper ul", self.el).css("opacity", 1);
                    (self.page < 0) ? self.enablePrev() : self.disablePrev();
                    self.addAll();
                },
                error: function () {
                    $.jGrowl($.i18n("seriousErrorDescr"), {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true
                    });
                }
            });
        }
    },
    /**
     * Disable "previous" left button on the slider.
     */
    disablePrev: function () {
        $(".ui-rcarousel-prev", this.el).addClass("disable");
    },
    /**
     * Enable "previous" left button on the slider.
     */    
    enablePrev: function () {
        $(".ui-rcarousel-prev", this.el).removeClass("disable");
    },
    /**
     * Disable "next" right button on the slider.
     */
    disableNext: function () {
    	$(".ui-rcarousel-next", this.el).addClass("disable");
    },
    /**
     * Disable "next" right button on the slider.
     */
    enableNext: function () {
    	$(".ui-rcarousel-next", this.el).removeClass("disable");
    },
    /**
     * Check if pagination is needed.
     */
    checkNumPages: function (step) {
        var numItems = this.$(".wrapper ul li").length;
        return ( numItems >= 7) ? true : false;
    },
    /**
     * Destroy current view.
     */
    close: function () {
        this.remove();
        this.unbind();
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        this.collection.unbind('reset', this.render);
        this.collection.unbind('remove', this.removeOne);
    },
    /**
     * Disable (but not destroy) current view.
     */
    disable: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        $(this.el).hide();
    },
    getEmptyMessage: function	()	{
    	return '<li class="message">' + '' + '</li>';
    },
    addElement: function () {},
    saveElement: function () {},
    removeOne: function () {},
    editOne: function () {},
    switchMode: function () {}
});
/*
 * @class FacilitiesListView
 * @parent Backbone.View
 * @constructor
 * Class to show list of facilities.
 * @tag views
 * @author LabOpenSource
 */
window.FacilitiesListView = ImagesFacilitiesView.extend({
    className: "facilities",
    initialize: function (options) {
        options['mode'] || (options['mode'] = "view");
        this.indexTemplate = $("#facilities-" + options['mode'] + "-template");
        _.bindAll(this, "next", "prev", "addOne");
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        // collection of facilities to check
        this.availableCollection = null;
        // set a copy of collection
        this.initCollection = this.collection;
        // array of single facility view
        this.rowViews = [];
        // page index for the slider
        this.page = 0;
        // id of parent entity-model
        this.idParent = null;
    },
    removeOne: function () {
        this.trigger("child:update", this);
    },
    saveElement: function () {},
    /**
     * set collection with checked images or facilities.
     */
    setChecked: function () {
        var self = this;
        this.collection.unbind('reset', this.render);
        this.collection.unbind('remove', this.removeOne);
        this.collection = this.initCollection.initialize({}, {
            id: this.idParent
        });
        this.collection = this.initCollection;
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        this.collection.fetch({
            silent: true,
            success: function () {
                self.render();
            }
        });
    },
    /**
     * Add a facility view  to rowViews array render it.
     * @param {Object} backbone model of the facility.
     */
    addOne: function (item) {
        //return FacilitiesListView.__super__.addOne.apply(this, arguments);
        var view = new FacilityRowView({
            model: item
        });
        view.bind("child:update", function () {
            this.trigger("child:update", this);
        }, this);
        if (this.indexTemplate.attr("id") == "facilities-edit-template") {
            view.switchMode();
        }
        view.model.collection = this.collection;
        this.rowViews.push(view);
        this.$("ul").append(view.render().el);
    },
    getEmptyMessage: function	()	{
    	return '<li class="message">' + $.i18n("facilities") + '</li>';
    },
    switchMode: function () {
        //reset to initial page
        this.page = 0;
        // change in edit mode template
        if (this.indexTemplate.attr("id") == "facilities-edit-template") {
            this.indexTemplate = $("#facilities-view-template");
            $(".overlay").remove();
            $(this.el).removeClass("edit-state-box");
            this.setChecked();
            $($.fn.overlay.defaults.container).css('overflow', 'auto');
        } else {
            var self = this;
            this.indexTemplate = $("#facilities-edit-template");
            // call a method to render availableCollection
            this.setAvailables();
            //this.render();
            $(this.el).undelegate("div", "click");
            $('<div></div>').overlay({
                effect: 'fade',
                onShow: function () {
                    var overlay = this;
                    $(self.el).addClass("edit-state-box");
                    $(this).click(function () {
                        if (confirm($.i18n("alertExitEditState"))) {
                            $(self.el).removeClass("edit-state-box");
                            self.indexTemplate = $("#facilities-view-template");
                            self.page = 0;
                            self.setChecked();
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
 * @class ImagesListView
 * @parent Backbone.View
 * @constructor
 * Class to show list of images.
 * @tag views
 * @author LabOpenSource
 */
window.ImagesListView = ImagesFacilitiesView.extend({
    className: "images",
    initialize: function (options) {
        options['mode'] || (options['mode'] = "view");
        this.indexTemplate = $("#images-" + options['mode'] + "-template");
        _.bindAll(this, "next", "prev", "addOne");
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        // set a copy of collection
        this.initCollection = this.collection;
        // collection of images to check
        this.availableCollection = null;
        this.rowViews = [];
        this.page = 0;
        this.idParent = null;
    },
    removeOne: function () {
        this.trigger("child:update", this);
    },
    saveElement: function () {
        //nothing  	 
    },
    addOne: function (item) {
        var view = new ImageRowView({
            model: item
        });
        view.model.collection = this.collection;
        if (this.indexTemplate.attr("id") == "images-edit-template") {
            view.switchMode();
        }
        this.rowViews.push(view);
        this.$("ul").append(view.render().el);
    },
    /**
     * set collection with checked images or facilities.
     */
    setChecked: function () {
        this.collection.unbind('reset', this.render);
        this.collection.unbind('remove', this.removeOne);
        this.collection = this.initCollection.initialize({}, {
            id: this.idParent
        });
        this.collection = this.initCollection;
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        this.collection.fetch();
    },
    getEmptyMessage: function	()	{
    	return '<li class="message">' + $.i18n("images") + '</li>';
    },
    switchMode: function () {
        //reset to initial page
        this.page = 0;
        // change in edit mode template
        if (this.indexTemplate.attr("id") == "images-edit-template") {
            this.indexTemplate = $("#images-view-template");
            $(".overlay").remove();
            $(this.el).removeClass("edit-state-box");
            this.setChecked();
            $($.fn.overlay.defaults.container).css('overflow', 'auto');
        } else {
            var self = this;
            this.indexTemplate = $("#images-edit-template");
            // call a method to render availableCollection
            this.setAvailables();
            //this.render();
            $(this.el).undelegate("div", "click");
            $('<div></div>').overlay({
                effect: 'fade',
                onShow: function () {
                    var overlay = this;
                    $(self.el).addClass("edit-state-box");
                    $(this).click(function () {
                        if (confirm($.i18n("alertExitEditState"))) {
                            $(self.el).removeClass("edit-state-box");
                            self.indexTemplate = $("#images-view-template");
                            self.page = 0;
                            self.setChecked();
                            $(overlay).remove();
                            $($.fn.overlay.defaults.container).css('overflow', 'auto');
                        }
                    });
                }
            });
        }
    }
});