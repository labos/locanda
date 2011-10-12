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
// load files in models folder. Please not wrape code into jquery ready function in model's file. 
steal("models/convention.js","models/roomFacility.js","models/roomType.js","models/guest.js","models/extra.js", "models/room.js", "models/facility.js", "models/convention.js").
//then load controllers		
then( "controllers/main_controller.js").then(
		 "controllers/guest_controller.js",
		 "controllers/season_controller.js",
 		"controllers/calendar_controller.js",
 		"controllers/extra_controller.js",
 		"controllers/upload_controller.js",
 		"controllers/tree_controller.js",
 		"controllers/room_controller.js",
 		"controllers/facility_controller.js",
 		"controllers/online_controller.js").then(
 		"controllers/booking_controller.js",
 		"controllers/convention.js"

);	

