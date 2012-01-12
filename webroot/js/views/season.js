window.PeriodRowView = Backbone.View.extend({
    //... is a list tag.
    tagName: "li",
    indexTemplate: $("#period-row-template"),
    // The DOM events specific to an row.
    events: {
        "click span.row-sub-item-destroy": "remove",
        "click div": "switchMode",
        "submit form": "save"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        this.model.bind('destroy', this.unrender, this);
    },
    // Re-render the contents of the todo item.
    render: function () {
        var modelToRender = this.model.toJSON();
        if (!this.model.isNew()) {
            modelToRender.endDate = this.convertDate(modelToRender.endDate);
            modelToRender.startDate = this.convertDate(modelToRender.startDate);
        }
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
        this.$(".yform").validate();
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
            var validator = $(this).parents(".yform").validate();
            validator.resetForm();
            return false;
        });
        // attack datepickers
        this.$(".datepicker").removeClass('hasDatepicker').datepicker("destroy");
        this.$(".datepicker").datepicker({
            //				altField: "#alternate",
            //				altFormat: '@',
            showOn: "button",
            buttonImage: "images/calendar.gif",
            buttonImageOnly: true,
            dateFormat: I18NSettings.datePattern
        });
        this.delegateEvents();
        return this;
    },
    unrender: function () {
        //clean up events raised from the view
        this.unbind();
        //clean up events from the DOM
        $(this.el).remove();
    },
    convertDate: function (aStringDate) {
        var dateDate = new Date(parseInt(aStringDate));
        return $.datepicker.formatDate(I18NSettings.datePattern, dateDate);
    },
    save: function (e) {
        e.preventDefault();
        var self = this,
            is_new = this.model.isNew() ? true : false,
            item = this.model.clone(),
            modelToSave = $("#edit-form").serializeObject();
        modelToSave.startDate = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.startDate));
        modelToSave.endDate = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.endDate));
        item.save(modelToSave, {
            success: function (model, resp) {
                self.model.set(model);
                self.model.initialize();
                if (is_new) {
                    self.model.collection.add(self.model);
                }
                self.trigger("period:update", self);
                $.jGrowl($.i18n("congratulation"), {
                    header: this.alertOK
                });
                self.switchMode();
            },
            error: function () {
                $.jGrowl($.i18n("seriousErrorDescr"), {
                    header: this.alertKO,
                    theme: "notify-error"
                });
            }
        });
        return false;
    },
    // Remove this view from the DOM.
    remove: function () {
        if (confirm($.i18n("alertDelete"))) {
        	var self = this;
            this.model.destroy({
                success: function () {
                	 self.trigger("period:update", self);
                    $.jGrowl($.i18n("congratulation"), {
                        header: this.alertOK
                    });
                    self.switchMode();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl(textStatus.responseText, {
                        header: this.alertKO,
                        theme: "notify-error"
                    });
                }
            });
        }
    },
    // clear all attributes from the model
    clear: function () {
        this.model.clear();
    },
    switchMode: function () {
        if (this.indexTemplate.attr("id") == "period-row-edit-template") {
            this.indexTemplate = $("#period-row-template");
            $(".overlay").remove();
            $(this.el).removeClass("edit-state-box");
            this.render();
            $($.fn.overlay.defaults.container).css('overflow', 'auto');
        } else {
            this.indexTemplate = (this.indexTemplate.attr("id") == "period-row-template") ? $("#period-row-edit-template") : $("#period-row-template");
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
                            $(overlay).remove();
                            if (self.model.isNew()) {
                                self.unrender();
                            } else {
                                $(self.el).removeClass("edit-state-box");
                                self.indexTemplate = $("#period-row-template");
                                $($.fn.overlay.defaults.container).css('overflow', 'auto');
                                self.render();
                            }
                        }
                    });
                }
            });
        }
    }
});
/*
 * @class PeriodsListView 
 * @parent Backbone.View
 * @constructor
 * Class to show list of periods.
 * @tag views
 * @author LabOpenSource
 */
window.PeriodsListView = Backbone.View.extend({
    indexTemplate: null,
    events: {
        "click .btn_add": "addNew"
    },
    initialize: function (options) {
        this.indexTemplate = $("#periods-view-template");
        _.bindAll(this, "addOne");
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        this.idSeason = null;
        this.rowViews = [];
        this.page = 0;
    },
    render: function () {
        $(this.el).html(this.indexTemplate.html());
        this.$(".btn_add").button({
            icons: {
                primary: "ui-icon-plusthick"
            }
        });
        this.addAll();
        (typeof this.idSeason !== 'undefined' && this.idSeason )? $(this.el).show() : $(this.el).hide();
        this.delegateEvents();
        return this;
    },
    // Add all items in the collection at once.
    addAll: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        this.rowViews = [];
        this.collection.each(this.addOne);
    },
    removeOne: function () {
/*        if (confirm($.i18n("alresetertDelete"))) {
            this.trigger("period:remove", this);
        }*/
    },
    addOne: function (item) {
    	var self = this;
        var view = new PeriodRowView({
            model: item
        });
        view.bind("period:update",function(){self.trigger("period:update",this);},self);
        view.model.collection = this.collection;
        this.rowViews.push(view);
        this.$("ul").append(view.render().el);
    },
    // Add all items in the collection at once.
    addNew: function () {
        this.addOne(new Period({id_season:this.idSeason }));
        _.last(this.rowViews).switchMode();
    }
});
/*
 * @class EditSeasonView
 * @parent Backbone.View
 * @constructor
 * Edit a row selected in the listing.
 * @tag views
 * @author LabOpenSource
 */
window.EditSeasonView = EditView.extend({
    events: {
        "submit form": "save",
        "click div": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        this.periodsListView = new PeriodsListView({
            collection: new Periods()
        });
       // this.periodsListView.bind("associated:change", this.model.fetch() );
        this.id = null;
        this.availableYears = [];
        this.initializeYears();
    },
    initializeYears: function () {
        var initYear = (new Date).getFullYear(),
            currYear = (new Date).getFullYear();
        for (var i = -10; i < 20; i++) {
            this.availableYears.push({
                value: initYear + i,
                selected: (initYear + i == currYear) ? true : false
            });
        }
    },
    setYears: function (aYear) {
        _.each(this.availableYears, function (val) {
            val.selected = false;
            if (val.value == aYear) {
                val.selected = true;
            }
        });
        return this.availableYears;
    },
    render: function () {
        // render main edit view
        var modelToRender = this.model.toJSON();
        // set additional attribute to display years. Only for the view.
        modelToRender.availableYears = this.setYears(this.model.get("year"));
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
        this.$(".yform").validate();
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
            var validator = $(this).parents(".yform").validate();
            validator.resetForm();
            return false;
        });
        // call for render associated views
        this.renderAssociated();
        this.delegateEvents();
        return this;
    },
    renderAssociated: function () {
        // check if model has changed or is new, then update collections in associated views
        if (this.model.isNew() || this.model.get("id") != this.id) {
        	var self = this;
            this.id = this.model.get("id");
            //set id season for new periods to add in periods list
            this.periodsListView.idSeason= this.id;
            
            //set collection for associated views
            this.periodsListView.collection.reset(this.model.get("periods"));
            
            // listen for changes in model on editing
            this.periodsListView.bind("period:update", function (){ alert("cassato"); 
            self.model.fetch();});
            // now render associated views
            if ($("#periods").is(':empty')) {
                $("#periods").html(this.periodsListView.el);
            }
        }
    }
});