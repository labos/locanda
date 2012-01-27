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
window.EditExtraView = EditView.extend({
    events: {
        "submit form": "save",
        "click div": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);

    },

    /**
     * Initialize priceType properties added to model and only to be used in the template.
     */
    checkPriceType: function ( type) {
    	if ( this.model.get("timePriceType") == type  || this.model.get("resourcePriceType" ) == type) {
    		return {"value": type,"checked":"checked=\"checked\""};
    		
    	} else {
        	return {"value": type,"checked":""};	
        }
    
    	
    	
    },
   
    render: function () {
        // render main edit view
        var modelToRender = this.model.toJSON();
        this.checkPriceType();
        // set additional attributes to display the radio buttons for price types. Only for the view.
        if ( this.model.isNew() ){
        	this.model.set({"timePriceType": "per Booking"},{silent: true});
        	this.model.set({"resourcePriceType": "per Item"},{silent: true});
        	        }
        modelToRender.nightPriceType = this.checkPriceType( "per Night" );
        modelToRender.weekPriceType = this.checkPriceType( "per Week" );
        modelToRender.bookingPriceType = this.checkPriceType( "per Booking" );
        modelToRender.roomPriceType = this.checkPriceType( "per Room" );
        modelToRender.personPriceType = this.checkPriceType( "per Person" );
        modelToRender.itemPriceType = this.checkPriceType( "per Item" );

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
        this.delegateEvents();
        return this;
    }
});