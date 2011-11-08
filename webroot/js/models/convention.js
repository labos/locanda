window.Convention = Backbone.Model.extend({
    //Create a model to hold convention attribute
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
    url: "conventionJson.action",

    validate: function (attrs) {

    },

});

window.Conventions = Backbone.Collection.extend({
    model: Convention,
    url: "findAllConventionsJson.action",
    //This is our Conventions collection and holds our Convention models
    initialize: function (from, to) {
        this.setFrom(from);
        this.setTo(to);

    },
    
    "url": function () {
        return 'findAllConventionsJson.action' + this.from + this.to;
    },

    setFrom: function (begin) {
        this.from = ( typeof begin === "number") ? '/' + begin : '';

    },
    setTo: function (end) {
        this.to =  (typeof end === "number")  ? '/' + end : '';


    },
    
    setFilter: function (attribute, value) {
    	this.filter = "";
    	if ( arguments.length === 2 && attribute !== undefined && value !== undefined  ) {
    		this.filter =  (attribute && value)  ? '/' + attribute + '/' + value  : "";
    		
    	}
    	return this;

    },
    
    setFilter: function (attribute, value) {
    	this.filter = "";
    	if ( arguments.length === 2 && attribute !== undefined && value !== undefined  ) {
    		this.filter =  (attribute && value)  ? '/' + attribute + '/' + value  : "";
    		
    	}
    	return this;

        

    }
});
