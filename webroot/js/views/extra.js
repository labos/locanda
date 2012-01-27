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
        
        this.nightPriceType = {"value":"per Night","checked":false};
        this.weekPriceType = {"value":"per Week","checked":false};
        this.bookingPriceType = {"value":"per Booking","checked":false};
        this.checkPriceType();
    },
    /**
     * Initialize priceType properties added to model and only to be used in the template.
     */
    checkPriceType: function () {
    	if (this.model.get("timePriceType") == "per Night") {
    		this.nightPriceType.checked = true;
    	} else
    	if (this.model.get("timePriceType") == "per Week") {
      		this.weekPriceType.checked = true;
        } else this.bookingPriceType.checked = true;	
    },
   
    render: function () {
        // render main edit view
        var modelToRender = this.model.toJSON();
        // set additional attributes to display the radio buttons for price types. Only for the view.
        modelToRender.nightPriceType = this.nightPriceType;
        modelToRender.weekPriceType = this.weekPriceType;
        modelToRender.bookingPriceType = this.bookingPriceType;
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