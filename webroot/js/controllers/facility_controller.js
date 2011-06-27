/**
 * @tag controllers, home
 * Displays a table of facilities.	 Lets the user 
 * ["Locanda.Controllers.Facility.prototype.form submit" create], 
 * ["Locanda.Controllers.Facility.prototype.&#46;edit click" edit],
 * or ["Locanda.Controllers.Facility.prototype.&#46;destroy click" destroy] facilities.
 */
$.Controller.extend('Controllers.Facility',
/* @Static */
{
	onDocument: true
},
/* @Prototype */
{
 /**
 * When the page loads, gets all facilities to be displayed.
 */
 load: function(){
	if(!$("#facility").length){
	 $(document.body).append($('<div/>').attr('id','facility'));
		 Models.Facility.findAll({}, this.callback('list'));
 	}
 },
 /**
 * Displays a list of facilities and the submit form.
 * @param {Array} facilities An array of Locanda.Models.Facility objects.
 */
 list: function( facilities ){
	$('#facility').html(this.view('init', {facilities:facilities} ));
 },
 /**
 * Responds to the create form being submitted by creating a new Locanda.Models.Facility.
 * @param {jQuery} el A jQuery wrapped element.
 * @param {Event} ev A jQuery event whose default action is prevented.
 */
'form submit': function( el, ev ){
	ev.preventDefault();
	new Models.Facility(el.formParams()).save();
},
/**
 * Listens for facilities being created.	 When a facility is created, displays the new facility.
 * @param {String} called The open ajax event that was called.
 * @param {Event} facility The new facility.
 */
'facility.created subscribe': function( called, facility ){
	$("#facility tbody").append( this.view("list", {facilities:[facility]}) );
	$("#facility form input[type!=submit]").val(""); //clear old vals
},
 /**
 * Creates and places the edit interface.
 * @param {jQuery} el The facility's edit link element.
 */
'.edit click': function( el ){
	var facility = el.closest('.facility').model();
	facility.elements().html(this.view('edit', facility));
},
 /**
 * Removes the edit interface.
 * @param {jQuery} el The facility's cancel link element.
 */
'.cancel click': function( el ){
	this.show(el.closest('.facility').model());
},
 /**
 * Updates the facility from the edit values.
 */
'.update click': function( el ){
	var $facility = el.closest('.facility'); 
	$facility.model().update($facility.formParams());
},
 /**
 * Listens for updated facilities.	 When a facility is updated, 
 * update's its display.
 */
'facility.updated subscribe': function( called, facility ){
	this.show(facility);
},
 /**
 * Shows a facility's information.
 */
show: function( facility ){
	facility.elements().html(this.view('show',facility));
},
 /**
 *	 Handle's clicking on a facility's destroy link.
 */
'.destroy click': function( el ){
	if(confirm("Are you sure you want to destroy?")){
		el.closest('.facility').model().destroy();
	}
 },
 /**
 *	 Listens for facilities being destroyed and removes them from being displayed.
 */
"facility.destroyed subscribe": function(called, facility){
	facility.elements().remove();	 //removes ALL elements
 }
});