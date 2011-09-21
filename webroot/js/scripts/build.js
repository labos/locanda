//steal/js js/scripts/compress.js

load("steal/rhino/steal.js");
steal.plugins('steal/build','steal/build/scripts','steal/build/styles',function(){
	steal.build('js/scripts/build.html',{to: 'js'});
});
