steal.plugins(	
		'controllers',			// a widget factory
		'models'	// subscribe to OpenAjax.hub
				// client side templates
		)				

		.models("roomFacility","accomodation","roomType","guest","extra")						// loads files in models folder 

		.controllers(
				 "guest",
				 "booking",
			 	 "season",
				 "main",
		 		"calendar",
		 		"extra",
		 		"upload",
		 		"tree",
		 		"accomodation",
		 		"online")					// loads files in controllers folder

		.views('//views/season/show.ejs');						// adds views to be added to build

