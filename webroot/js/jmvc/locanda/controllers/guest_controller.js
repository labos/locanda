/**
 * @tag controllers, home
 * Displays a table of guests.	 Lets the user 
 * ["Locanda.Controllers.Guest.prototype.form submit" create], 
 * ["Locanda.Controllers.Guest.prototype.&#46;edit click" edit],
 * or ["Locanda.Controllers.Guest.prototype.&#46;destroy click" destroy] guests.
 */
$.Controller.extend('Locanda.Controllers.Guest',
/* @Static */
{
	onDocument: true
},
/* @Prototype */
{
 /**
 * When the page loads, gets all guests to be displayed.
 */
 load: function(){
	if(!$("#guest").length){
	 $(document.body).append($('<div/>').attr('id','guest'));
		 Locanda.Models.Guest.findAll({term: 'ro'}, this.callback('list'), this.callback('list_error'));
 	}
 },
 /**
 * Displays a list of guests and the submit form.
 * @param {Array} guests An array of Locanda.Models.Guest objects.
 */
 list: function( guests ){
	$('#guest').html(this.view('init', {guests:guests} ));
 },
 
 list_error: function(jqXHR, textStatus, errorThrown){
		$('#guest').html(this.view('init', {guests:guests} ));
	 },
 /**
 * Responds to the create form being submitted by creating a new Locanda.Models.Guest.
 * @param {jQuery} el A jQuery wrapped element.
 * @param {Event} ev A jQuery event whose default action is prevented.
 */
'form submit': function( el, ev ){
	ev.preventDefault();
	new Locanda.Models.Guest(el.formParams()).save(this.callback('list'), this.callback('list_error'));
},
/**
 * Listens for guests being created.	 When a guest is created, displays the new guest.
 * @param {String} called The open ajax event that was called.
 * @param {Event} guest The new guest.
 */
'guest.created subscribe': function( called, guest ){
	$("#guest tbody").append( this.view("list", {guests:[guest]}) );
	$("#guest form input[type!=submit]").val(""); //clear old vals
},
 /**
 * Creates and places the edit interface.
 * @param {jQuery} el The guest's edit link element.
 */
'.edit click': function( el ){
	var guest = el.closest('.guest').model();
	guest.elements().html(this.view('edit', guest));
},
 /**
 * Removes the edit interface.
 * @param {jQuery} el The guest's cancel link element.
 */
'.cancel click': function( el ){
	this.show(el.closest('.guest').model());
},
 /**
 * Updates the guest from the edit values.
 */
'.update click': function( el ){
	var $guest = el.closest('.guest'); 
	$guest.model().update($guest.formParams());
},
 /**
 * Listens for updated guests.	 When a guest is updated, 
 * update's its display.
 */
'guest.updated subscribe': function( called, guest ){
	this.show(guest);
},
 /**
 * Shows a guest's information.
 */
show: function( guest ){
	guest.elements().html(this.view('show',guest));
},
 /**
 *	 Handle's clicking on a guest's destroy link.
 */
'.destroy click': function( el ){
	if(confirm("Are you sure you want to destroy?")){
		el.closest('.guest').model().destroy();
	}
 },
 /**
 *	 Listens for guests being destroyed and removes them from being displayed.
 */
"guest.destroyed subscribe": function(called, guest){
	guest.elements().remove();	 //removes ALL elements
 }
});