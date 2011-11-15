/*
 * @class Conventions
 * @parent Backbone.Collection
 * @constructor
 * Collection for roomTypes.
 * @tag models
 * @author LabOpenSource
 */

window.RoomTypes = Backbone.Collection.extend({
    model: RoomType,
    //This is our Conventions collection and holds our Convention models
    initialize: function (from, to) {
        this.setFrom(from);
        this.setTo(to);
    },
    "url": function () {
        return 'findAllRoomTypesJson.action' + this.from + this.to;
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