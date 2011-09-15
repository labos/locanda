steal.plugins(	
	'lib/jquery/controller',			// a widget factory
	'lib/jquery/controller/subscribe',	// subscribe to OpenAjax.hub
	'lib/jquery/view/ejs',				// client side templates
	'lib/jquery/controller/view',		// lookup views with the controller's name
	'lib/jquery/model',					// Ajax wrappers
	'lib/jquery/dom/fixture',			// simulated Ajax requests
	'lib/jquery/dom/form_params')		// form data helper
	
	.css('locanda')	// loads styles

	.resources()					// 3rd party script's (like jQueryUI), in resources folder

	.models('adjustment')						// loads files in models folder 

	.controllers('adjustment')					// loads files in controllers folder

	.views();						// adds views to be added to build
