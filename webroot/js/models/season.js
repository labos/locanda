/**
 * @tag models, home
 * Wraps backend season services.  Enables 
 * [Locanda.Models.Season.static.findAll retrieving],
 * [Locanda.Models.Season.static.update updating],
 * [Locanda.Models.Season.static.destroy destroying], and
 * [Locanda.Models.Season.static.create creating] seasons.
 */
$.Model.extend('Models.Season',
/* @Static */
{
	/**
 	 * Retrieves seasons data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped season objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
	findAll: function( params, success, error ){
		$.ajax({
			url: '/season',
			type: 'get',
			dataType: 'json',
			data: params,
			success: this.callback(['wrapMany',success]),
			error: error,
			fixture: "//locanda/fixtures/seasons.json.get" //calculates the fixture path from the url and type.
		});
	},
	/**
	 * Updates a season's data.
	 * @param {String} id A unique id representing your season.
	 * @param {Object} attrs Data to update your season with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: '/seasons/'+id,
			type: 'put',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error,
			fixture: "-restUpdate" //uses $.fixture.restUpdate for response.
		});
	},
	/**
 	 * Destroys a season's data.
 	 * @param {String} id A unique id representing your season.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: '/seasons/'+id,
			type: 'delete',
			dataType: 'json',
			success: success,
			error: error,
			fixture: "-restDestroy" // uses $.fixture.restDestroy for response.
		});
	},
	/**
	 * Creates a season.
	 * @param {Object} attrs A season's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: '/seasons',
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