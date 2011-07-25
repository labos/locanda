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
 * Wraps backend facility services.  Enables 
 * [Locanda.Models.Facility.static.findAll retrieving],
 * [Locanda.Models.Facility.static.update updating],
 * [Locanda.Models.Facility.static.destroy destroying], and
 * [Locanda.Models.Facility.static.create creating] facilities.
 */
$.Model.extend('Models.Facility',
/* @Static */
{
	/**
 	 * Retrieves facilities data from your backend services.
 	 * @param {Object} params params that might refine your results.
 	 * @param {Function} success a callback function that returns wrapped facility objects.
 	 * @param {Function} error a callback function for an error in the ajax request.
 	 */
	findAll: function( params, success, error ){
		$.ajax({
			url: '/facility',
			type: 'get',
			dataType: 'json',
			data: params,
			success: this.callback(['wrapMany',success]),
			error: error,
			fixture: "//locanda/fixtures/facilities.json.get" //calculates the fixture path from the url and type.
		});
	},
	
	 findOne : function(params, success, error){
		    var self = this,
		        id = params.id;
		    delete params.id;
		    return $.get("/findFacilityById?id="+id,
		      params,
		      success,
		      "json thing.model")
		  },

	/**
	 * Updates a facility's data.
	 * @param {String} id A unique id representing your facility.
	 * @param {Object} attrs Data to update your facility with.
	 * @param {Function} success a callback function that indicates a successful update.
 	 * @param {Function} error a callback that should be called with an object of errors.
     */
	update: function( id, attrs, success, error ){
		$.ajax({
			url: 'updateFacility.action',
			type: 'post',
			dataType: 'json',
			data: attrs,
			success: success,
			error: error
		});
	},
	/**
 	 * Destroys a facility's data.
 	 * @param {String} id A unique id representing your facility.
	 * @param {Function} success a callback function that indicates a successful destroy.
 	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	destroy: function( id, success, error ){
		$.ajax({
			url: 'deleteFacility.action?facility.id='+id,
			type: 'get',
			dataType: 'json',
			success: success,
			error: error
		});
	},
	/**
	 * Creates a facility.
	 * @param {Object} attrs A facility's attributes.
	 * @param {Function} success a callback function that indicates a successful create.  The data that comes back must have an ID property.
	 * @param {Function} error a callback that should be called with an object of errors.
	 */
	create: function( attrs, success, error ){
		$.ajax({
			url: '/facilities',
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