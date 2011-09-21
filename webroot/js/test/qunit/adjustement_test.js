module("Model: js.Models.Adjustement")

asyncTest("findAll", function(){
	stop(2000);
	js.Models.Adjustement.findAll({}, function(adjustements){
		ok(adjustements)
        ok(adjustements.length)
        ok(adjustements[0].name)
        ok(adjustements[0].description)
		start()
	});
	
})

asyncTest("create", function(){
	stop(2000);
	new js.Models.Adjustement({name: "dry cleaning", description: "take to street corner"}).save(function(adjustement){
		ok(adjustement);
        ok(adjustement.id);
        equals(adjustement.name,"dry cleaning")
        adjustement.destroy()
		start();
	})
})
asyncTest("update" , function(){
	stop();
	new js.Models.Adjustement({name: "cook dinner", description: "chicken"}).
            save(function(adjustement){
            	equals(adjustement.description,"chicken");
        		adjustement.update({description: "steak"},function(adjustement){
        			equals(adjustement.description,"steak");
        			adjustement.destroy();
        			start()
        		})
            })

});
asyncTest("destroy", function(){
	stop(2000);
	new js.Models.Adjustement({name: "mow grass", description: "use riding mower"}).
            destroy(function(adjustement){
            	ok( true ,"Destroy called" )
            	start();
            })
})