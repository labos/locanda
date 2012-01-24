/*
 * @class Guests
 * @parent Backbone.Collection
 * @constructor
 * Collection for guests.
 * @tag models
 * @author LabOpenSource
 */

window.Guests = Backbone.Collection.extend({
    model: Guest,
    //This is our Guests collection and holds our Guest models
    initialize: function (idStructure) {
    	this.setIdWrapper(idStructure);
    	this.setFrom(0);
    	this.setTo(10);
    	this.setTerm(null);
    },
    url: function () {
        return 'rest/guests/structure/' + this.idWrapper +  '/search' + this.from + this.to + '?term=' + this.term ;
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
    },
    setFilter: function (attribute, value) {
        this.filter = "";
        if (arguments.length === 2 && attribute !== undefined && value !== undefined) {
            this.filter = (attribute && value) ? '/' + attribute + '/' + value : "";
        }
        return this;
    },

});