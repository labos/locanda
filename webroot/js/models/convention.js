

window.Convention = Backbone.Model.extend({
//Create a model to hold convention attribute

defaults:	{
	id: null,
	name: "insert name",
	description: null,
	activationCode: "insert own code",
	id_structure: null,
},
validate: function(attrs){
	
},

});

window.Conventions = Backbone.Collection.extend({
	model: Convention,
	url: "findAllConventionsJson.action",
//This is our Conventions collection and holds our Convention models
initialize: function (models, options) {
this.bind("add", options.view.addConventionList);
//Listen for new additions to the collection and call a view function if so
}
});

