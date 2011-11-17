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
 * @class Convention
 * @parent Backbone.Model
 * @constructor
 * Model to hold convention attribute.
 * @tag models
 * @author LabOpenSource
 */

window.Convention = Backbone.Model.extend({
	
    defaults: {
        id: null,
        name: "insert name",
        description: null,
        activationCode: "insert own code",
        id_structure: null,
        sub_description: null
    },
    initialize: function () {
        this.get("description") ? this.set({
            sub_description: this.get("description").substring(0, 20) + '...'
        }) : this.set({
            sub_description: null
        });
    },
    urlRoot: "resources/conventions/",
    validate: function (attrs) {},
});

window.Autocompletes = AutocompleteCollection.extend({

    url: function () {
        return 'findAllConventionsJson.action' + this.term;
    },
    parse: function (response) {
        var parsedResponse = [];
        $.each(response, function (index, value) {
            parsedResponse.push({
                "id": value.id,
                "label": value.name,
                "value": value.name
            });
        });
        return parsedResponse;
    }
});

