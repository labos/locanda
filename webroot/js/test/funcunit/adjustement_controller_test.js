/*global module: true, ok: true, equals: true, S: true, test: true */
module("adjustement", {
	setup: function () {
		// open the page
		S.open("//js/js.html");

		//make sure there's at least one adjustement on the page before running a test
		S('.adjustement').exists();
	},
	//a helper function that creates a adjustement
	create: function () {
		S("[name=name]").type("Ice");
		S("[name=description]").type("Cold Water");
		S("[type=submit]").click();
		S('.adjustement:nth-child(2)').exists();
	}
});

test("adjustements present", function () {
	ok(S('.adjustement').size() >= 1, "There is at least one adjustement");
});

test("create adjustements", function () {

	this.create();

	S(function () {
		ok(S('.adjustement:nth-child(2) td:first').text().match(/Ice/), "Typed Ice");
	});
});

test("edit adjustements", function () {

	this.create();

	S('.adjustement:nth-child(2) a.edit').click();
	S(".adjustement input[name=name]").type(" Water");
	S(".adjustement input[name=description]").type("\b\b\b\b\bTap Water");
	S(".update").click();
	S('.adjustement:nth-child(2) .edit').exists(function () {

		ok(S('.adjustement:nth-child(2) td:first').text().match(/Ice Water/), "Typed Ice Water");

		ok(S('.adjustement:nth-child(2) td:nth-child(2)').text().match(/Cold Tap Water/), "Typed Cold Tap Water");
	});
});

test("destroy", function () {

	this.create();

	S(".adjustement:nth-child(2) .destroy").click();

	//makes the next confirmation return true
	S.confirm(true);

	S('.adjustement:nth-child(2)').missing(function () {
		ok("destroyed");
	});

});