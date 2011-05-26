module("Model: Locanda.Models.Guest")

test("findAll", function(){
	stop(2000);
	Locanda.Models.Guest.findAll({}, function(guests){
		start()
		ok(guests)
        ok(guests.length)
        ok(guests[0].name)
        ok(guests[0].description)
	});
	
})

test("create", function(){
	stop(2000);
	new Locanda.Models.Guest({name: "dry cleaning", description: "take to street corner"}).save(function(guest){
		start();
		ok(guest);
        ok(guest.id);
        equals(guest.name,"dry cleaning")
        guest.destroy()
	})
})
test("update" , function(){
	stop();
	new Locanda.Models.Guest({name: "cook dinner", description: "chicken"}).
            save(function(guest){
            	equals(guest.description,"chicken");
        		guest.update({description: "steak"},function(guest){
        			start()
        			equals(guest.description,"steak");
        			guest.destroy();
        		})
            })

});
test("destroy", function(){
	stop(2000);
	new Locanda.Models.Guest({name: "mow grass", description: "use riding mower"}).
            destroy(function(guest){
            	start();
            	ok( true ,"Destroy called" )
            })
})