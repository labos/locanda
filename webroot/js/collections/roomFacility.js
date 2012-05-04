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
 * @class RoomFacilities
 * @parent Backbone.Collection
 * @constructor
 * Collection for RoomFacilities.
 * @tag models
 * @author LabOpenSource
 */

window.RoomFacilities = Backbone.Collection.extend({
    model: RoomFacility,
    //This is our Conventions collection and holds our Convention models
    initialize: function (models, options) {
    	this.setIdWrapper(options.id);
    	this.setFrom(0);
    	this.setTo(7);
    	this.setTerm(null);
    },
    url: function () {
        return 'rest/facilities/checked/room/'+ this.idWrapper  + this.from + this.to + '?term=' + this.term ;
    },
    setTerm: function (aTerm) {
        this.term = (typeof aTerm !== "undefined" && aTerm) ? aTerm : '';
    },
    setIdWrapper: function (id) {
        this.idWrapper = (typeof id === "number") ? id : '';
    },
    getIdWrapper: function () {
        return this.idWrapper;
    },
    setFrom: function (begin) {
        this.from = (typeof begin === "number") ? '/' + begin : '';
    },
    setTo: function (end) {
        this.to = (typeof end === "number") ? '/' + end : '';
    }

});