/*
 * @class EditGuestView
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
    	var that = this;
        this.model.bind('change', this.render, this);
        this.id = null;
        this.availableCountries = [];
        this.initializeCountries();
        
        //function to send guest id on parent
		$(".btn_select_guest").click(function(event){
			var id = 0;
			try {
				id = that.model.get("id");
			} catch(e) {
				console.log(e);
			}
			if (id > 0) {
				//call correct callback
				var cb = GetQueryStringParams('callback');
				if (cb == 'setbooker') {
					parent.window.SelectBookerWidget.select(that.model.toJSON());
					parent.$.colorbox.close();
				} else if (cb == 'setgroupleader') {
					parent.window.SelectGroupLeaderWidget.select(that.model.toJSON());
					parent.$.colorbox.close();
				} else if (cb == 'setguests') {
					parent.window.ListHousedWidget.select(that.model.toJSON());
					parent.$.colorbox.close();
				}
			}
		});
        
    },
    /**
     * Initialize availableCountries property added to model  and only to be used in the template.
     */
    initializeCountries: function () {
    		//get all countries by AllCountries
            this.availableCountries=AllCountries.toJSON();
            
    },
    /**
     * Set the saved country in the list of available countries.
     * @param {String} country to be setted.
     * @return {Array} array of { value:"", selected: ""} objects.
     */
    setCountries: function (aCountry) {
    	aCountry = (aCountry && aCountry.length > 0) ? aCountry : 'US'; 
        _.each(this.availableCountries, function (val) {
            val.selected = false;
            if (val.code == aCountry) {
                val.selected = true;
            }
        });
        return this.availableCountries;
    },
    /**
     * Initialize guest properties added to model and only to be used in the template.
     */
    checkGender: function ( type) {
    	return {value: type, selected: this.model.get("gender")==type? true : false  };
    },
    render: function () {
    	var that = this;
        // render main edit view
        var modelToRender = this.model.toJSON();
        // set additional attribute to display years. Only for the view.
        modelToRender.availableCountries = this.setCountries(this.model.get("country"));
        // set gender attribute to be suitable in mustache template.
        modelToRender.genders = [this.checkGender("M"),this.checkGender("F")];
        // if the model is not new then convert dates.
        if (this.model.get("birthDate")) {
            modelToRender.birthDate = this.convertDate(modelToRender.birthDate);
        }
        else{
        	modelToRender.birthDate = this.convertDate( new Date().getTime() );
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
        // initialize and render datepickers.
        this.$(".datepicker").removeClass('hasDatepicker').datepicker("destroy");
		this.$( 'input[name="birthDate"]' ).datepicker({
 			yearRange: "-110:+0",
 			maxDate:  new Date(),
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
        this.initautoComplete();
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
                $.jGrowl($.i18n("congratulation"), { header: this.alertOK, position: 'top-right', position: 'top-right'  });
                self.switchMode();
                
            },
            error: function () {
            	$.jGrowl($.i18n("seriousErrorDescr") + ' ', { header: this.alertOK,sticky: true });
            }
        });
        return false;
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
     * Render associated views
     */
    renderAssociated: function () {
        // check if model has changed or is new, then update collections in associated views
        if (this.model.isNew() || this.model.get("id") != this.id) {
            var self = this;
            this.id = this.model.get("id");

        }
    },
    /**
     * Init the autocomplete for various fields
     */
    initautoComplete: function () {
    	var Clist = AllCountries.toJSON();
    	var Mlist = AllMunicipalities.toJSON();
 		var Cr = [];
 		var Mr = [];
 		for (o in Clist) {
 			Cr.push(Clist[o].name);
 		};
 		for (o in Mlist) {
 			Mr.push(Mlist[o].name);
 		}
        $('#FormAddress').autocomplete({
        	disabled: false,
       	 	minLength: 0,
       	 	source: Mr,
        });
        $('#FormBirthPlace').autocomplete({
        	disabled: false,
       	 	minLength: 0,
       	 	source: Mr,
        });
        //end autocomplete
    }
});