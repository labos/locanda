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
 * @class Booking
 * @parent Backbone.Model
 * @constructor
 * Model to hold a Booking.
 * @tag models
 * @author LabOpenSource
 */

window.Booking = Backbone.Model.extend({
	
    defaults: {
        booker: null,
        room: null,
        groupLeader: null,
        groupType: null,
        housedList: []
    },
    initialize: function () {
        
    },
    url: function () {
        var base = this.urlRoot;
        if (this.isNew()) return base;
        return base + (base.charAt(base.length - 1) == '/' ? '' : '/') + encodeURIComponent(this.id);
    },
    urlRoot: "rest/booking/",
    validate: function (attrs) {},
});

