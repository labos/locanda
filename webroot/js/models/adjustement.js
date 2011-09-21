/**
 * @tag models, home
 * Wraps backend adjustement services.  Enables 
 * [js.Models.Adjustement.static.findAll retrieving],
 * [js.Models.Adjustement.static.update updating],
 * [js.Models.Adjustement.static.destroy destroying], and
 * [js.Models.Adjustement.static.create creating] adjustements.
 */
$.Model.extend('js.Models.Adjustement',
/* @Static */
{
	/**
 	 * Retrieves adjustements data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped adjustement objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
	findAll: function( params, success, error ){
		$.ajax({
			url: '/adjustement',
			type: 'get',
			dataType: 'json',
			data: params,
			success: this.callback(['wrapMany',success]),
			error: error,
			fixture: "//js/fixtures/adjustements.json.get" //calculates the fixture path from the url and type.
		});
	},
	/**
	 * Updates a adjustement's data.
	 * @param {String} id A unique id representing your adjustement.
	 * @param {Object} attrs Data to update your adjustement with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: '/adjustements/'+id,
			type: 'put',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error,
			fixture: "-restUpdate" //uses $.fixture.restUpdate for response.
		});
	},
	/**
 	 * Destroys a adjustement's data.
 	 * @param {String} id A unique id representing your adjustement.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: '/adjustements/'+id,
			type: 'delete',
			dataType: 'json',
			success: success,
			error: error,
			fixture: "-restDestroy" // uses $.fixture.restDestroy for response.
		});
	},
	/**
	 * Creates a adjustement.
	 * @param {Object} attrs A adjustement's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: '/adjustements',
			type: 'post',
			dataType: 'json',
			success: success,
			error: error,
			data: attrs,
			fixture: "-restCreate" //uses $.fixture.restCreate for response.
		});
	}
},
/* @Prototype */
{});