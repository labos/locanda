/**
 * @tag models, home
 * Wraps backend guest services.  Enables 
 * [Locanda.Models.Guest.static.findAll retrieving],
 * [Locanda.Models.Guest.static.update updating],
 * [Locanda.Models.Guest.static.destroy destroying], and
 * [Locanda.Models.Guest.static.create creating] guests.
 */
$.Model.extend('Locanda.Models.Guest',
/* @Static */
{
	/**
 	 * Retrieves guests data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped guest objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
	findAll: function( params, success, error ){
		$.ajax({
			url: 'findAllGuestsJson.action',
			type: 'get',
			dataType: 'json',
			data: params,
			success: this.callback(['wrapMany',success]),
			error: error
		});
	},
	/**
	 * Updates a guest's data.
	 * @param {String} id A unique id representing your guest.
	 * @param {Object} attrs Data to update your guest with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: 'saveUpdateGuest.action/'+id,
			type: 'put',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error
			//,	fixture: "-restUpdate" //uses $.fixture.restUpdate for response.
		});
	},
	/**
 	 * Destroys a guest's data.
 	 * @param {String} id A unique id representing your guest.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: 'deleteGuest.action/'+id,
			type: 'delete',
			dataType: 'json',
			success: success,
			error: error
			//,	fixture: "-restDestroy" // uses $.fixture.restDestroy for response.
		});
	},
	/**
	 * Creates a guest.
	 * @param {Object} attrs A guest's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: 'saveUpdateGuest.action',
			type: 'post',
			dataType: 'json',
			success: success,
			error: error,
			data: attrs
			//,fixture: "-restCreate" //uses $.fixture.restCreate for response.
		});
	}
},
/* @Prototype */
{});