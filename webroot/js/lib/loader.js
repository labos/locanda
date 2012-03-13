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
typeof Entity !== "undefined" || ( Entity = {name:"default", editView:null} );
// conditional file loading section
if ( Entity.name == "roomType" || Entity.name == "room" || Entity.name == "structure") {
    steal("../../css/rcarousel.css","../views/commonMedia.js");
}
if ( Entity.name == "roomType") {
    steal("../models/roomTypeFacility.js", "../models/roomTypeImage.js", "../models/file.js").then("../collections/roomTypeFacility.js","../collections/availableRoomTypeFacility.js","../collections/availableRoomTypeImages.js","../collections/roomTypeImage.js");
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
    steal("jquery.weekcalendar.js");
}
if (Entity.name == "season") {
    steal("../models/period.js").
    then("../collections/period.js");
}
// end conditional file loading section

// load common js scripts
steal("../helpers/common.js", "../helpers/autocomplete.js").
//then roomtype files, to be moved elsewhere
then("../models/roomType.js").
then("../collections/roomType.js").
//then load views, model, collections and routers
then("../models/" + Entity.name + ".js").
then("../collections/" + Entity.name + ".js").
then("../views/common.js").
then("../views/" + Entity.name + ".js").
then("../routers/common.js");
