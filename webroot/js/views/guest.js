/*
 * @class EditSeasonView
 * @parent Backbone.View
 * @constructor
 * Edit a row selected in the listing.
 * @tag views
 * @author LabOpenSource
 */
window.EditGuestView = EditView.extend({
    events: {
        "submit form": "save",
        "click div": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
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
        
		this.$( 'input[name="birthDate"]' ).datepicker({
 			yearRange: "-110:+0",
			changeMonth: true,
			changeYear: true,
            showOn: "button",
            buttonImage: "images/calendar.gif",
            buttonImageOnly: true,
            dateFormat: I18NSettings.datePattern
		});
        // call for render associated views
        this.renderAssociated();
        this.delegateEvents();
        return this;
    },
    // save new item or update existing item.
    save: function (e) {
        e.preventDefault();
        var self = this,
        is_new = this.model.isNew() ? true : false,
        item = this.model.clone();
        // extract model by the template DOM
        modelToSave = $("#edit-form").serializeObject();
        // format date inputs in timestamp format.
        modelToSave.birthDate = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.birthDate));
        // save local model item
        item.save( modelToSave, {
            success: function (model, resp) {
                self.model.set(model);
                self.model.initialize();
                if (is_new) {
                    self.collection.add(self.model);
                }
                $.jGrowl($.i18n("congratulation"), { header: this.alertOK });
                self.switchMode();
                
            },
            error: function () {
                $().notify(this.alertKO, $.i18n("seriousErrorDescr") + ' ');
            }
        });
        return false;
    },
    /**
     * Render associated views
     */
    renderAssociated: function () {
        // check if model has changed or is new, then update collections in associated views
        if (this.model.isNew() || this.model.get("id") != this.id) {
            var self = this;
            this.id = this.model.get("id");

        }
    }
});