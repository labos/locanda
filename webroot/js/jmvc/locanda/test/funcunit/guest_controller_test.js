/*global module: true, ok: true, equals: true, S: true, test: true */
module("guest", {
	setup: function () {
		// open the page
		S.open("//locanda/locanda.html");

		//make sure there's at least one guest on the page before running a test
		S('.guest').exists();
	},
	//a helper function that creates a guest
	create: function () {
		S("[name=name]").type("Ice");
		S("[name=description]").type("Cold Water");
		S("[type=submit]").click();
		S('.guest:nth-child(2)').exists();
	}
});

test("guests present", function () {
	ok(S('.guest').size() >= 1, "There is at least one guest");
});

test("create guests", function () {

	this.create();

	S(function () {
		ok(S('.guest:nth-child(2) td:first').text().match(/Ice/), "Typed Ice");
	});
});

test("edit guests", function () {

	this.create();

	S('.guest:nth-child(2) a.edit').click();
	S(".guest input[name=name]").type(" Water");
	S(".guest input[name=description]").type("\b\b\b\b\bTap Water");
	S(".update").click();
	S('.guest:nth-child(2) .edit').exists(function () {

		ok(S('.guest:nth-child(2) td:first').text().match(/Ice Water/), "Typed Ice Water");

		ok(S('.guest:nth-child(2) td:nth-child(2)').text().match(/Cold Tap Water/), "Typed Cold Tap Water");
	});
});

test("destroy", function () {

	this.create();

	S(".guest:nth-child(2) .destroy").click();

	//makes the next confirmation return true
	S.confirm(true);

	S('.guest:nth-child(2)').missing(function () {
		ok("destroyed");
	});

});