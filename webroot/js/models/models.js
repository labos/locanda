/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
/**
 * @tag models, home
 * Wraps backend check_in services.  Enables 
 * [Locanda.Models.CheckIn.static.findAll retrieving],
 * [Locanda.Models.CheckIn.static.update updating],
 * [Locanda.Models.CheckIn.static.destroy destroying], and
 * [Locanda.Models.CheckIn.static.create creating] check_ins.
 */
$.Model.extend('Locanda.Models.CheckIn',
/* @Static */
{
	/**
 	 * Retrieves check_ins data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped check_in objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
	findAll: function( params, success, error ){
		$.ajax({
			url: '/check_in',
			type: 'get',
			dataType: 'json',
			data: params,
			success: this.callback(['wrapMany',success]),
			error: error
		});
	},
	/**
	 * Updates a check_in's data.
	 * @param {String} id A unique id representing your check_in.
	 * @param {Object} attrs Data to update your check_in with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: 'checkInBooking.action'+id,
			type: 'put',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error
		});
	},
	/**
 	 * Destroys a check_in's data.
 	 * @param {String} id A unique id representing your check_in.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: '/check_ins/'+id,
			type: 'delete',
			dataType: 'json',
			success: success,
			error: error
		});
	},
	/**
	 * Creates a check_in.
	 * @param {Object} attrs A check_in's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: '/check_ins',
			type: 'post',
			dataType: 'json',
			success: success,
			error: error,
			data: attrs
		});
	}
},
/* @Prototype */
{});