/**
 * @tag models, home
 * Wraps backend room services.  Enables 
 * [Locanda.Models.Room.static.findAll retrieving],
 * [Locanda.Models.Room.static.update updating],
 * [Locanda.Models.Room.static.destroy destroying], and
 * [Locanda.Models.Room.static.create creating] rooms.
 */
$.Model.extend('Models.Room',
/* @Static */
{
	associations : {
	    hasMany : "RoomFacility"
	  },
	/**
 	 * Retrieves rooms data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped room objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
	findAll: function( params, success, error ){
		$.ajax({
			url: 'findAllRoomsJson.action',
			type: 'get',
			dataType: 'json',
			data: params,
			success: success,
			error: error
		});
	},
	/**
	 * Updates a room's data.
	 * @param {String} id A unique id representing your room.
	 * @param {Object} attrs Data to update your room with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: '/rooms/'+id,
			type: 'put',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error,
			fixture: "-restUpdate" //uses $.fixture.restUpdate for response.
		});
	},
	/**
 	 * Destroys a room's data.
 	 * @param {String} id A unique id representing your room.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: '/rooms/'+id,
			type: 'delete',
			dataType: 'json',
			success: success,
			error: error,
			fixture: "-restDestroy" // uses $.fixture.restDestroy for response.
		});
	},
	/**
	 * Creates a room.
	 * @param {Object} attrs A room's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: '/rooms',
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
{		  setRoomType: function(roomtype) {
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