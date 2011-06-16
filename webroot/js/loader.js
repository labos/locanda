steal.plugins(	
		'controllers',			// a widget factory
		'models'	// subscribe to OpenAjax.hub

		)				

		.models("roomFacility","accomodation","roomType","guest","extra", "room")						// loads files in models folder 

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
		 		"online");					// loads files in controllers folder


