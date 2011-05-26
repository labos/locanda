//steal/js locanda/scripts/compress.js

load("steal/rhino/steal.js");
steal.plugins('steal/build','steal/build/scripts','steal/build/styles',function(){
	steal.build('locanda/scripts/build.html',{to: 'locanda'});
});
