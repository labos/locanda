/**
 * @tag models, home
 * Wraps backend accomodation services.  Enables 
 * [Locanda.Models.Accomodation.static.findAll retrieving],
 * [Locanda.Models.Accomodation.static.update updating],
 * [Locanda.Models.Accomodation.static.destroy destroying], and
 * [Locanda.Models.Accomodation.static.create creating] accomodations.
 */
$.Model.extend('Models.Accomodation',
/* @Static */
{
	
	associations : {
	    hasMany : "RoomFacility"
	  },
	/**
 	 * Retrieves accomodations data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped accomodation objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
		findAll: function( params, success, error ){
			$.ajax({
				url: '/accomodation',
				type: 'get',
				dataType: 'json',
				data: params,
				success: this.callback(['wrapMany',success]),
				error: error,
				fixture: "//locanda/fixtures/accomodations.json.get" //calculates the fixture path from the url and type.
			});
		},
	/**
	 * Updates a accomodation's data.
	 * @param {String} id A unique id representing your accomodation.
	 * @param {Object} attrs Data to update your accomodation with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: '/accomodations/'+id,
			type: 'put',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error,
			fixture: "-restUpdate" //uses $.fixture.restUpdate for response.
		});
	},
	/**
 	 * Destroys a accomodation's data.
 	 * @param {String} id A unique id representing your accomodation.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: '/accomodations/'+id,
			type: 'delete',
			dataType: 'json',
			success: success,
			error: error,
			fixture: "-restDestroy" // uses $.fixture.restDestroy for response.
		});
	},
	/**
	 * Creates a accomodation.
	 * @param {Object} attrs A accomodation's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: '/accomodations',
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
{

		  setRoomType: function(roomtype) {
			    return roomtype;
			  },
			  
			  	getRoomTypeFacilities: function(roomTypeId, success, error){
				  var self = this;

					$.ajax({
						url: 'findRoomTypesForRoom.action',
						type: 'POST',
						dataType: 'html',
						data: {'room.roomType.id': roomTypeId},
						success: success,
						error: error
					});
				  }

		  


});