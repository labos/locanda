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
 * @class Provinces
 * @parent Backbone.Collection
 * @constructor
 * Collection for Provinces.
 * @tag models
 * @author LabOpenSource
 */

window.Provinces = Backbone.Collection.extend({
    model: Province,
    initialize: function (models, options) {
    	var that = this;
    	$.ajax({
    		async:false, //Provinces are important!!! Waiting for...
    		url:that.baseUrl,
    		success: function(data) {
    			var r = [];
    			var json = eval(data);
    			for (p in json) {
    				r.push({code:json[p],description:json[p]});
    			};
    			that.reset();
    			that.add(r);
    		},
    		error: function() {
    			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
    		}
    	});
    },
    url: function () {
        return this.baseUrl;
    },
    baseUrl: 'rest/municipalities/provinces',
});

window.AllProvinces = new Provinces([],Entity.idStructure);