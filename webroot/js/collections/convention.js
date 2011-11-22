/*
 * @class Conventions
 * @parent Backbone.Collection
 * @constructor
 * Collection for conventions.
 * @tag models
 * @author LabOpenSource
 */

window.Conventions = Backbone.Collection.extend({
    model: Convention,
    //This is our Conventions collection and holds our Convention models
    initialize: function (idStructure) {
    	this.setIdWrapper(idStructure);
    	this.setFrom(null);
    	this.setTo(null);
    },
    "url": function () {
        return 'rest/conventions/structure' + this.idWrapper  + this.from + this.to;
    },
    setIdWrapper: function (id) {
        this.idWrapper = (typeof id === "number") ? '/' + id : '';
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
    }
});