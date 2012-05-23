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
 * @class EditExtraView
 * @parent Backbone.View
 * @constructor
 * Edit a row selected in the listing.
 * @tag views
 * @author LabOpenSource
 */
window.RowView = Backbone.View.extend({
     //... is a list tag.
     tagName: "li",
     indexTemplate: $("#row-template"),
     // The DOM events specific to an row.
     events: {
         "click span.row-item-destroy": "remove",
         "click .row-item": "edit"
     },
     initialize: function () {
         this.model.bind('change', this.render, this);
         this.model.bind('destroy', this.unrender, this);
     },
     // adds the property label to an object to allow internationalization
     translate: function (property) {
 		return {"label": $.i18n(property)};		
     },
     // Re-render the contents of the todo item.
     render: function () {
    	 var modelToRender = this.model.toJSON();
    	 // set additional attributes to translate the price type names. Only for the view.
    	 modelToRender.timePriceType = this.translate(this.model.get("timePriceType"));
         modelToRender.resourcePriceType = this.translate(this.model.get("resourcePriceType"));
         
         $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));
         return this;
     },
     // Switch this view into `"editing"` mode, displaying the input field.
     edit: function (event) {
         var $target = $(event.target);
         if (!$target.is("span.row-item-destroy")) {
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
                     $.jGrowl($.i18n("cancelSuccess"), { header: this.alertOK, position: 'top-right'  });
                 },
                 error: function (jqXHR, textStatus, errorThrown) {
                     textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                     $.jGrowl(textStatus.responseText, { header: this.alertKO, theme: "notify-error",sticky: true   }); 
                 }
             });
         }
     },
     // clear all attributes from the model
     clear: function () {
         this.model.clear();
     }
 });

window.EditExtraView = EditView.extend({
    events: {
        "submit form": "save",
        "click div": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
    },
    // Initialize priceType properties added to model and only to be used in the template.  
    checkPriceType: function (type) {
    	if (this.model.get("timePriceType") == type  || this.model.get("resourcePriceType" ) == type) {
    		return {"value": type,"checked":"checked=\"checked\""};		
    	} else {
        	return {"value": type,"checked":""};	
        }
    },
    // adds the property label to an object to allow internationalization
    translate: function (property) {
    		return {"label": $.i18n(property)};		
    },
    render: function () {
        // render main edit view
        var modelToRender = this.model.toJSON();
        
        if ( this.model.isNew() ){
        	this.model.set({"timePriceType": "extraPerNight"},{silent: true});
        	this.model.set({"resourcePriceType": "extraPerPerson"},{silent: true});
        	        }
        // set additional attributes to display the radio buttons for price types. Only for the view.
        modelToRender.nightPriceType = this.checkPriceType("extraPerNight");
        modelToRender.weekPriceType = this.checkPriceType("extraPerWeek");
        modelToRender.bookingPriceType = this.checkPriceType("extraPerBooking");
        modelToRender.roomPriceType = this.checkPriceType("extraPerRoom" );
        modelToRender.personPriceType = this.checkPriceType("extraPerPerson");
        modelToRender.itemPriceType = this.checkPriceType("extraPerItem");
        // set additional attributes to translate the price type names. Only for the view.
        modelToRender.timePriceType = this.translate(this.model.get("timePriceType"));
        modelToRender.resourcePriceType = this.translate(this.model.get("resourcePriceType"));

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
        //disables resource price type radio buttons set forcing it to "per Booking" when "per Item is selected"       
        if(this.model.get("resourcePriceType") == "extraPerItem")  {
       		$("#radioBooking").attr('checked', 'checked');
       		$("#radioNight, #radioWeek").attr('disabled', 'disabled');	
       	}; 
       	$("input[name=resourcePriceType]").click(function() {
       		if($('#radioItem').attr('checked')) {
       			$("#radioBooking").attr('checked', 'checked');
       			$("#radioNight, #radioWeek").attr('disabled', 'disabled');
       		}else {$("input[name=timePriceType]").removeAttr('disabled');}
       	});
        this.delegateEvents();
        return this;
    }
});
