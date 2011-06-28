steal.plugins(	
		'controllers',			// a widget factory
		'models'	// subscribe to OpenAjax.hub

		)				

		.models("roomFacility","roomType","guest","extra", "room", "facility")						// loads files in models folder 

		.controllers(
				 "guest",
				 "booking",
			 	 "season",
				 "main",
		 		"calendar",
		 		"extra",
		 		"upload",
		 		"tree",
		 		"room",
		 		"facility",
		 		"online");					// loads files in controllers folder


