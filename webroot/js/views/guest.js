/*
 * @class EditGuestView
 * @parent Backbone.View
 * @constructor
 * Edit a row selected in the listing.
 * @tag views
 * @author LabOpenSource
 */
window.EditGuestView = EditView.extend({
	countryDefaultId: 1, //Italy
	ITProvinces: [], //All italian provinces
	MunicipalityOfBirthCollection: null, //instance of Municipality for birth
	MunicipalityOfResidenceCollection: null, //instance of Municipality for residence
	IdentificationTypeCollection: AllIdentificationTypes, //instance of IdentificationType
    events: {
        "submit form": "save",
        "click div": "switchMode",
        "change #Formid_countryOfBirth":"selectedCountryOfBirth",
        "change #FormBirthProvinceSelector":"selectedProvinceOfBirth",
        "change #FormBirthPlace":"updateFormBirthPlace",
        "change #Formid_countryOfResidence":"selectedCountryOfResidence",
        "change #FormResidenceProvinceSelector":"selectedProvinceOfResidence",
        "change #FormResidencePlace":"updateFormResidencePlace",
    },
    initialize: function () {
    	var that = this;
        this.model.bind('change', this.render, this);
        this.id = null;
        this.ITProvinces = AllProvinces.toJSON();
        this.availableCitizenships = [];
        this.availableCountriesBirth = [];
        this.availableCountriesResidence = [];
        this.initializeCitizenships();
        this.initializeCountriesBirth();
        this.initializeCountriesResidence();
        this.initializeIdentificationTypes();
        
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
     * Initialize availableCitizenship property added to model  and only to be used in the template.
     */
    initializeCitizenships: function () {
    	this.availableCitizenships=AllCountries.toJSON();
    },
    /**
     * Initialize availableCountries property added to model  and only to be used in the template.
     */
    initializeCountriesBirth: function () {
    	this.availableCountriesBirth=AllCountries.toJSON();
    },
    /**
     * Initialize availableCountries property added to model  and only to be used in the template.
     */
    initializeCountriesResidence: function () {
    	this.availableCountriesResidence=AllCountries.toJSON();
    },
    /**
     * Initialize availableIndentificationTypes property added to model  and only to be used in the template.
     */
    initializeIdentificationTypes: function () {
    	this.availableIdentificationTypes=this.IdentificationTypeCollection.toJSON();
    },
    /**
     * Set the saved Citizenship in the list of available countries.
     */
    setCitizenships: function (aCountryId) {
    	aCountry = (aCountryId && aCountryId > 0) ? parseInt(aCountryId) : 0;
    	_.each(this.availableCitizenships, function (val) {
            val.selected = false;
            if (val.id == aCountry) {
                val.selected = true;
            }
        });
        return this.availableCitizenships;
    },
    /**
     * Set the saved country in the list of available countries.
     */
    setCountriesBirth: function (aCountryId) {
    	aCountry = (aCountryId && aCountryId > 0) ? parseInt(aCountryId) : 0; 
    	_.each(this.availableCountriesBirth, function (val) {
            val.selected = false;
            if (val.id == aCountry) {
                val.selected = true;
            }
        });
        return this.availableCountriesBirth;
    },
    /**
     * Set the saved country in the list of available countries.
     */
    setCountriesResidence: function (aCountryId) {
    	aCountry = (aCountryId && aCountryId > 0) ? parseInt(aCountryId) : 0; 
    	_.each(this.availableCountriesResidence, function (val) {
            val.selected = false;
            if (val.id == aCountry) {
                val.selected = true;
            }
        });
        return this.availableCountriesResidence;
    },
    /**
     * Set the saved idType in the list of available identificationTypes.
     */
    setIdentificationTypes: function (aIdentificationTypeId) {
    	aIdentificationType = (aIdentificationTypeId && aIdentificationTypeId > 0) ? parseInt(aIdentificationTypeId) : 0; 
    	_.each(this.availableIdentificationTypes, function (val) {
            val.selected = false;
            if (val.id == aIdentificationType) {
                val.selected = true;
            }
        });
        return this.availableIdentificationTypes;
    },
    /**
     * On select country show a select for province.
     * Only if Italy.
     */
    selectedCountryOfBirth: function(e) {
    	var select = $(e.currentTarget);
    	var current_id = parseInt(select.val());
    	
    	//reset fields.
    	$.each($('#FormBirthProvinceSelector option'),function(i,v){
    		if (i==0) {
    			$(v).attr("selected","selected");
    		} else {
    			$(v).attr("selected",false);
    		}
    	});
    	try {
    		//on init this field not exists!
    		$('#id_municipalityOfBirth').get(0).value='';
    	} catch(e){}
    	
    	if (current_id==this.countryDefaultId) {
    		$('#BirthProvinceSelector').removeClass('none');
    	} else {
    		$('#BirthProvinceSelector').addClass('none');
    		$('#BirthPlaceSelector').addClass('none');
    	}
    },
    /**
     * On select province of Birth populate Municipality.
     * Only if Italy.
     */
    selectedProvinceOfBirth: function(e) {
    	var that = this;
    	//reset fields
    	$('#id_municipalityOfBirth').get(0).value='';
    	$('#BirthPlaceSelector').addClass('none'); //container of placeselection
    	var select = $(e.currentTarget);
    	var current_province_code = select.val();
    	if (current_province_code != "") {
    		//get municipalities
    		this.MunicipalityOfBirthCollection = new Municipalities([],{
    			provinceCode: current_province_code
    		});
    		this.MunicipalityOfBirthCollection.reset();
    		this.MunicipalityOfBirthCollection.fetch({
    			success: function() {
    				that.populateFormBirthPlace();
    			},
    			error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
    		});
    	}
    },
    /**
     * populate Birth Municipality set current selected by model.
     * Only if Italy.
     */
    populateFormBirthPlace: function() {
    	var that = this;
    	var select = $('#FormBirthPlace');
    	if (this.MunicipalityOfBirthCollection) {
    		select.html('');
    		select.append('<option value="0">Scegli la città</option>');
    		$.each(this.MunicipalityOfBirthCollection.toJSON(), function(i,v){
    			var selected = (that.model.get("id_municipalityOfBirth")==v.id) ? 'selected="selected"':'';
    			select.append('<option value="'+v.id+'" '+selected+'>'+v.description+'</option>');
    		});
    		$('#BirthPlaceSelector').removeClass('none')
    	}
    },
    /**
     * Update Hidden for Birth Municipality id/name.
     * Only if Italy.
     */
    updateFormBirthPlace: function(e) {
    	var that = this;
    	var select = $(e.currentTarget);
    	var current_id = parseInt(select.val());
    	var current_text = select.find("option:selected").text();
    	$('#id_municipalityOfBirth').get(0).value=current_id;
    	//$('#municipalityOfBirth').get(0).value=current_text;
    },
    /**
     * On select country show a select for province.
     * Only if Italy.
     */
    selectedCountryOfResidence: function(e) {
    	var select = $(e.currentTarget);
    	var current_id = parseInt(select.val());
    	
    	//reset fields.
    	$.each($('#FormResidenceProvinceSelector option'),function(i,v){
    		if (i==0) {
    			$(v).attr("selected","selected");
    		} else {
    			$(v).attr("selected",false);
    		}
    	});
    	try {
    		//on init this field not exists!
    		$('#id_municipalityOfResidence').get(0).value='';
    	} catch(e){};
    	try {
    		//on init this field not exists!
    		$('#FormAddress').get(0).value = '';
    	} catch(e){};
    	
    	if (current_id==this.countryDefaultId) {
    		$('#ResidenceProvinceSelector').removeClass('none');
    	} else {
    		$('#ResidenceProvinceSelector').addClass('none');
    		$('#ResidencePlaceSelector').addClass('none');
    	}
    },
    /**
     * On select province of Residence populate Municipality.
     * Only if Italy.
     */
    selectedProvinceOfResidence: function(e) {
    	var that = this;
    	//reset fields
    	$('#id_municipalityOfResidence').get(0).value='';
    	$('#FormAddress').get(0).value = '';
    	$('#ResidencePlaceSelector').addClass('none'); //container of placeselection
    	var select = $(e.currentTarget);
    	var current_province_code = select.val();
    	if (current_province_code != "") {
    		//get municipalities
    		this.MunicipalityOfResidenceCollection = new Municipalities([],{
    			provinceCode: current_province_code
    		});
    		this.MunicipalityOfResidenceCollection.reset();
    		this.MunicipalityOfResidenceCollection.fetch({
    			success: function() {
    				that.populateFormResidencePlace();
    			},
    			error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
    		});
    	}
    },
    /**
     * populate Residence Municipality set current selected by model.
     * Only if Italy.
     */
    populateFormResidencePlace: function() {
    	var that = this;
    	var select = $('#FormResidencePlace');
    	if (this.MunicipalityOfResidenceCollection) {
    		select.html('');
    		select.append('<option value="0">Scegli la città</option>');
    		$.each(this.MunicipalityOfResidenceCollection.toJSON(), function(i,v){
    			var selected = (that.model.get("id_municipalityOfResidence")==v.id) ? 'selected="selected"':'';
    			select.append('<option value="'+v.id+'" '+selected+'>'+v.description+'</option>');
    		});
    		$('#ResidencePlaceSelector').removeClass('none')
    	}
    },
    /**
     * Update Hidden for Residence Municipality id/name.
     * Only if Italy.
     */
    updateFormResidencePlace: function(e) {
    	var that = this;
    	var select = $(e.currentTarget);
    	var current_id = parseInt(select.val());
    	var current_text = select.find("option:selected").text();
    	$('#id_municipalityOfResidence').get(0).value=current_id;
    	//$('#municipalityOfResidence').get(0).value=current_text;
    	//reset fields
    	$('#FormAddress').get(0).value = '';
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
        // set additional attribute to display Citizenship/countries/Provinces/identification types
        modelToRender.ITProvinces = this.ITProvinces;
        modelToRender.availableCitizenships = this.setCitizenships(this.model.get("id_citizenship"));
        modelToRender.availableCountriesBirth = this.setCountriesBirth(this.model.get("id_countryOfBirth"));
        modelToRender.availableCountriesResidence = this.setCountriesResidence(this.model.get("id_countryOfResidence"));
        modelToRender.availableIdentificationTypes = this.setIdentificationTypes(this.model.get("id_idType"));
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
        this.checkMunicipalities();
        
        return this;
    },
    /*
     * Init function for checking correct Municipalities on Model Data
     * 
     */
    checkMunicipalities: function() {
    	var that = this;
    	//check for Municipalities
    	if (that.model.get("id_municipalityOfBirth")) {
        	//AJAX CALL for get province of municipality
        	$.ajax({
        		url:'rest/municipalities/'+this.model.get("id_municipalityOfBirth"),
        		data:{},
        		success:function(data) {
        			var json = $.parseJSON(JSON.stringify(data, undefined, 2));
        			
        			//update province
        			$.each($('#FormBirthProvinceSelector option'), function(i,v) {
                		if ($(v).attr("value")==json.province) {
                			$(v).attr("selected","selected");
                			$('#BirthProvinceSelector').removeClass('none');
                			//update municipality
                    		that.MunicipalityOfBirthCollection = new Municipalities([],{
                    			provinceCode: json.province
                    		});
                    		that.MunicipalityOfBirthCollection.reset();
                    		that.MunicipalityOfBirthCollection.fetch({
                    			success: function() {
                    				that.populateFormBirthPlace();
                    			},
                    			error: function() {
                        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
                        		}
                    		});
                			
                		}
                	});
        			
        			
        		},
        		error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
        	}); 
        } else {
        	this.selectedCountryOfBirth({currentTarget:'#Formid_countryOfBirth'});
        }
        if (that.model.get("id_municipalityOfResidence")) {
        	//AJAX CALL for get province of municipality
        	$.ajax({
        		url:'rest/municipalities/'+this.model.get("id_municipalityOfResidence"),
        		data:{},
        		success:function(data) {
        			var json = $.parseJSON(JSON.stringify(data, undefined, 2));
        			
        			//update province
        			$.each($('#FormResidenceProvinceSelector option'), function(i,v) {
                		if ($(v).attr("value")==json.province) {
                			$(v).attr("selected","selected");
                			$('#ResidenceProvinceSelector').removeClass('none');
                			//update municipality
                    		that.MunicipalityOfResidenceCollection = new Municipalities([],{
                    			provinceCode: json.province
                    		});
                    		that.MunicipalityOfResidenceCollection.reset();
                    		that.MunicipalityOfResidenceCollection.fetch({
                    			success: function() {
                    				that.populateFormResidencePlace();
                    			},
                    			error: function() {
                        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
                        		}
                    		});
                			
                		}
                	});
        			
        			
        		},
        	}); 
        } else {
        	this.selectedCountryOfResidence({currentTarget:'#Formid_countryOfResidence'});
        }
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
        modelToSave.idNumber = $('#FormIdNumber').val(); //strange BUG!!!! it's a string!!!
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
    }
});