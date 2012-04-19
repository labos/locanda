/**
 * @tag controllers, home
 * Displays a table of adjustements.	 Lets the user 
 * ["js.Controllers.Adjustement.prototype.form submit" create], 
 * ["js.Controllers.Adjustement.prototype.&#46;edit click" edit],
 * or ["js.Controllers.Adjustement.prototype.&#46;destroy click" destroy] adjustements.
 */
$.Controller.extend('js.Controllers.Adjustement',
/* @Static */
{
	onDocument: true
},
/* @Prototype */
{
 /**
 * When the page loads, gets all adjustements to be displayed.
 */
 "{window} load": function(){
	if(!$("#adjustement").length){
	 $(document.body).append($('<div/>').attr('id','adjustement'));
		 js.Models.Adjustement.findAll({}, this.callback('list'));
 	}
 },
 /**
 * Displays a list of adjustements and the submit form.
 * @param {Array} adjustements An array of js.Models.Adjustement objects.
 */
 list: function( adjustements ){
	$('#adjustement').html(this.view('init', {adjustements:adjustements} ));
 },
 /**
 * Responds to the create form being submitted by creating a new js.Models.Adjustement.
 * @param {jQuery} el A jQuery wrapped element.
 * @param {Event} ev A jQuery event whose default action is prevented.
 */
'form submit': function( el, ev ){
	ev.preventDefault();
	new js.Models.Adjustement(el.formParams()).save();
},
/**
 * Listens for adjustements being created.	 When a adjustement is created, displays the new adjustement.
 * @param {String} called The open ajax event that was called.
 * @param {Event} adjustement The new adjustement.
 */
'adjustement.created subscribe': function( called, adjustement ){
	$("#adjustement tbody").append( this.view("list", {adjustements:[adjustement]}) );
	$("#adjustement form input[type!=submit]").val(""); //clear old vals
},
 /**
 * Creates and places the edit interface.
 * @param {jQuery} el The adjustement's edit link element.
 */
'.edit click': function( el ){
	var adjustement = el.closest('.adjustement').model();
	adjustement.elements().html(this.view('edit', adjustement));
},
 /**
 * Removes the edit interface.
 * @param {jQuery} el The adjustement's cancel link element.
 */
'.cancel click': function( el ){
	this.show(el.closest('.adjustement').model());
},
 /**
 * Updates the adjustement from the edit values.
 */
'.update click': function( el ){
	var $adjustement = el.closest('.adjustement'); 
	$adjustement.model().update($adjustement.formParams());
},
 /**
 * Listens for updated adjustements.	 When a adjustement is updated, 
 * update's its display.
 */
'adjustement.updated subscribe': function( called, adjustement ){
	this.show(adjustement);
},
 /**
 * Shows a adjustement's information.
 */
show: function( adjustement ){
	adjustement.elements().html(this.view('show',adjustement));
},
 /**
 *	 Handle's clicking on a adjustement's destroy link.
 */
'.destroy click': function( el ){
	if(confirm("Are you sure you want to destroy?")){
		el.closest('.adjustement').model().destroy();
	}
 },
 /**
 *	 Listens for adjustements being destroyed and removes them from being displayed.
 */
"adjustement.destroyed subscribe": function(called, adjustement){
	adjustement.elements().remove();	 //removes ALL elements
 }
});