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
// set defaults for pages not managed by entities
( typeof Entity !== "undefined" && typeof Entity.model !== "undefined" ) || ( Entity = {name:"default", editView:null} );

//load common js scripts
steal("../helpers/common.js", "../helpers/autocomplete.js");
// conditional file loading section
if ( Entity.name == "roomType" || Entity.name == "room" || Entity.name == "structure") {
    steal("../../css/rcarousel.css","../views/commonMedia.js");
}
if ( Entity.name == "room") {
    steal("../models/roomFacility.js", "../models/roomImage.js", "../models/file.js").then("../collections/roomFacility.js","../collections/availableRoomFacilities.js","../collections/availableRoomImages.js","../collections/roomImage.js").
    then("../models/roomType.js").
    then("../collections/roomType.js");
}
if ( Entity.name == "roomType") {
    steal("../models/roomTypeFacility.js", "../models/roomTypeImage.js", "../models/file.js").then("../collections/roomTypeFacility.js","../collections/availableRoomTypeFacilities.js","../collections/availableRoomTypeImages.js","../collections/roomTypeImage.js");
}
if ( Entity.name == "structure") {
    steal("../models/structureFacility.js", "../models/structureImage.js", "../models/file.js").then("../collections/structureFacility.js","../collections/availableStructureFacilities.js","../collections/availableStructureImages.js","../collections/structureImage.js");
}
if ( Entity.name == "facility" ) {
    steal("jquery.fileupload.js", "jquery.fileupload-ui.js", "jquery.fileupload-uix.js").
    then("../helpers/upload.js","../models/image.js");
}
if ( Entity.name == "image" ) {
    steal("jquery.fileupload.js", "jquery.fileupload-ui.js", "jquery.fileupload-uix.js").
    then("../helpers/upload.js");
}
if (Entity.name == "planner") {
    steal("../helpers/autocomplete.js").then("../../css/jquery.weekcalendar.css","../../css/calendar.css","../models/housed.js","../models/tourismType.js","../models/transport.js").then("../collections/housed.js","../collections/tourismType.js","../collections/transport.js").then("../controllers/booking_controller.js","jquery.weekcalendar.js");
}
if (Entity.name == "season") {
    steal("../models/period.js").
    then("../collections/period.js");
}
if (Entity.name == "guest") {
	steal("../models/country.js","../models/province.js","../models/municipality.js","../models/identificationtype.js").
	then("../collections/country.js","../collections/province.js","../collections/municipality.js","../collections/identificationtype.js");
}
if (Entity.name == "export") {
    steal("../helpers/autocomplete.js").then("../../css/jquery.weekcalendar.css","../../css/calendar.css","../models/housed.js").then("../collections/housed.js").then("../controllers/booking_controller.js","jquery.weekcalendar.js");
}
// end conditional file loading section


//then load views, model, collections and routers
steal("../helpers/autocomplete.js").then("../models/" + Entity.name + ".js").
then("../collections/" + Entity.name + ".js").
then("../views/common.js").
then("../views/" + Entity.name + ".js").
then("../routers/common.js");

if (Entity.name == "planner") {
    steal("../views/housed.js");
}
if (Entity.name == "export") {
    steal("../views/housed.js");
}
if (Entity.name == "guest" && !Entity.isDialog) {
   steal("../models/housed.js","../models/tourismType.js","../models/transport.js","../models/booking.js").then("../collections/tourismType.js","../collections/transport.js","../collections/booking.js","../collections/housed.js").then("../../css/jquery.weekcalendar.css","../views/bookingPreview.js","../views/housed.js").
   then("../controllers/booking_controller.js").then("../../css/jquery.weekcalendar.css","../../css/calendar.css");
}
