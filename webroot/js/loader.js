/*
steal(
		 "controllers/guest",
		 "controllers/booking",
	 	 "controllers/season",
		 "controllers/main",
 		"controllers/calendar",
 		"controllers/extra",
 		"controllers/upload",
 		"controllers/tree",
 		"controllers/accomodation",
 		"controllers/online"
 		 		).then(function(){           //called when all prior files have completed
   //loads myapp/myapp.less
 	    })
;  */
 

steal.plugins(	
		'controllers',			// a widget factory
		'models'	// subscribe to OpenAjax.hub
					// client side templates
		)				

		.models('roomFacility')						// loads files in models folder 

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

		.views('//ejs/show.ejs');						// adds views to be added to build

