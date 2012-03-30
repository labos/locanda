
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
 * @class Autocomplete
 * @parent Backbone.Model
 * @constructor
 * Model for auto-complete functionality.
 * @tag models
 * @author LabOpenSource
 */

window.Autocomplete = Backbone.Model.extend({
    defaults: {
        id: null,
        label: null,
        value: null
    },
    initialize: function () {

    }
});
/*
 * @class Autocompletes
 * @parent Backbone.Collection
 * @constructor
 * Collection for auto-complete functionality.
 * @tag collections
 * @author LabOpenSource
 */

window.AutocompleteCollection = Backbone.Collection.extend({
    model: Autocomplete,
    initialize: function (term, wrapper) {
        this.setTerm(term);
        this.setIdWrapper(wrapper);
    },
    setTerm: function (aTerm) {
        this.term = (typeof aTerm !== 'undefined' && aTerm) ? '?term=' + aTerm : "";
    },
    setIdWrapper: function (id) {
        this.idWrapper = (typeof id === "number") ?  id : '';
    },
    parse: function (response) {
        var parsedResponse = [];
        $.each(response, function (index, value) {
            parsedResponse.push({
               // "id": null,
                 "label": value,
                "value": value
            });
        });
        return parsedResponse;
    }
});