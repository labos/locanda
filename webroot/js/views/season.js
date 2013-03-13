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
 * @class PeriodsRowView 
 * @parent Backbone.View
 * @constructor
 * Class to show a period.
 * @tag views
 * @author LabOpenSource
 */
window.PeriodRowView = Backbone.View.extend({
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
    /**
     * Render the contents of the period item.
     */
    render: function () {
        // set a local row model object to use in the template
        var modelToRender = this.model.toJSON();
        // if the model is not new then convert dates.
        if (!this.model.isNew()) {
            modelToRender.endDate = this.convertDate(modelToRender.endDate);
            modelToRender.startDate = this.convertDate(modelToRender.startDate);
        }
        // render model using template engine
        
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
        // add validation check
        this.$(".yform").validate();
        // render the buttons
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
        // initialize and render datepickers.
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
    /**
     * un-render current row and un-register events.
     */
    unrender: function () {
        // trigger an update event.
        this.trigger("period:update", this);
        //clean up events raised from the view
        this.unbind();
        //clean up events from the DOM
        $(this.el).remove();
    },
    /**
     * Convert date in local format.
     * @param {String} date string to be converted.
     * @return {String} date string converted using local date settings.
     */
    convertDate: function (aStringDate) {
        var dateDate = new Date(parseInt(aStringDate));
        return $.datepicker.formatDate(I18NSettings.datePattern, dateDate);
    },
    /**
     * Save or update current period model.
     * @param {Object} event that is launched by a save request.
     */
    save: function (e) {
        e.preventDefault();
        var self = this,
            is_new = this.model.isNew() ? true : false,
            item = this.model.clone(),
            // extract model by the template DOM
            modelToSave = $("#edit-form").serializeObject();
        // format date inputs in timestamp format.
        modelToSave.startDate = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.startDate));
        modelToSave.endDate = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.endDate));
        // save local model item
        item.save(modelToSave, {
            success: function (model, resp) {
                // set current model with new saved model and initialize it.
                self.model.set(model);
                self.model.initialize();
                // if it's an new model, add to current collection.
                if (is_new) {
                    self.model.collection.add(self.model);
                }
                // trigger an update event.
                self.trigger("period:update", self);
                // show a notification.
                $.jGrowl($.i18n("congratulation"), {
                    header: this.alertOK, position: 'top-right'
                });
                // switch to in Non-Edit mode
                self.switchMode();
            },
            error: function (model, resp) {
                $.jGrowl(resp.responseText, {
                    header: this.alertKO,
                    theme: "notify-error"
                });
            }
        });
        return false;
    },
    /**
     * Remove this view from the DOM.
     */
    remove: function () {
        if (confirm($.i18n("alertDelete"))) {
            var self = this;
            this.model.destroy({
                success: function () {
                    $.jGrowl($.i18n("cancelSuccess"), {
                        header: this.alertOK, position: 'top-right' 
                    });
                    self.switchMode();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl(textStatus.responseText, {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true 
                    });
                }
            });
        }
    },
    /**
     * Clear all attributes from the model.
     */
    clear: function () {
        this.model.clear();
    },
    switchMode: function () {
        if (this.indexTemplate.attr("id") == "period-row-edit-template") {
            // execute actions to set current template in No-edit mode.
            this.indexTemplate = $("#period-row-template");
            $(".overlay").remove();
            $(this.el).removeClass("edit-state-box");
            this.render();
            $($.fn.overlay.defaults.container).css('overflow', 'auto');
            // end actions to set current template in No-edit mode.
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
        // declare container object id  as null.
        this.idSeason = null;
        // set row views array to contain a list of period rows.
        this.rowViews = [];
    },
    render: function () {
        $(this.el).html(this.indexTemplate.html());
        // render add period button
        this.$(".btn_add").button({
            icons: {
                primary: "ui-icon-plusthick"
            }
        });
        // add all periods if a rendering request occur
        this.addAll();
        // hide list of periods if the season is new and not saved.
        (typeof this.idSeason !== 'undefined' && this.idSeason) ? $(this.el).show() : $(this.el).hide();
        this.delegateEvents();
        return this;
    },
    /**
     * Add all items in the collection at once.
     */
    addAll: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        this.rowViews = [];
        this.collection.each(this.addOne);
    },
    /**
     * Called if a model is removed from the collection.
     */
    removeOne: function () {
        // enter additional code in case of row deleted
    },
    /**
     * Add a period.
     * @param {Object} period to show for a season.
     */
    addOne: function (item) {
        var self = this;
        var view = new PeriodRowView({
            model: item
        });
        // attach an handler to every view update event and trigger a further event from this PeriodsListView
        view.bind("period:update", function () {
            self.trigger("period:update", this);
        }, self);
        // set this collection to the view
        view.model.collection = this.collection;
        // push current view in the rowView list
        this.rowViews.push(view);
        // add current view into html template already rendered
        this.$("ul").append(view.render().el);
    },
    /**
     * Add new period. It's called when add new period button is pushed.
     */
    addNew: function () {
        this.addOne(new Period({
            id_season: this.idSeason
        }));
        // get the last rowView just added and switch it in edit mode
        _.last(this.rowViews).switchMode();
    },
    /**
     * Disable current view.
     */
    disable: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        this.rowViews = [];
        $(this.el).hide();
    },
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
            collection: new Periods(null, Entity.idStructure)
        });
        this.id = null;
        this.availableYears = [];
        this.initializeYears();
    },
    /**
     * Initialize availableYears property added to model  and only to be used in the template.
     */
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
    /**
     * Set the saved year in the list of available years.
     * @param {String} year to be setted.
     * @return {Array} array of { value:"", selected: ""} objects.
     */
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
        // check if model is new then render the new template.
        if (this.model.isNew()) {
            this.renderNew();
        }
        //add thirth parameter to pass i18n by partials:$.i18n[$.i18n.getLocale()].strings
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
        // call for render associated views
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
            this.id = this.model.get("id");
            //set id season for new periods to add in periods list
            this.periodsListView.idSeason = this.id;

            //set collection for associated views
            this.periodsListView.collection.reset(this.model.get("periods"));

            // listen for changes in model on editing and fetch model if any change occur.
            this.periodsListView.bind("period:update", function () {
                self.model.fetch();
            });
            // now render associated views
            if ($("#periods").is(':empty')) {
                $("#periods").html(this.periodsListView.el);
            }
        }
    },
    /**
     * Render associated views
     */
    renderNew: function () {
    	this.id = null;
        //set id season for new periods to add in periods list
        this.periodsListView.idSeason = null;
        this.periodsListView.unbind("period:update");
        this.periodsListView.disable();
        // now render associated views
        if ($("#periods").is(':empty')) {
            $("#periods").html(this.periodsListView.el);
        }


    }
});