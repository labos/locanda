$(function() {
	
//---  EXTRAS SECTION CODE   
	var values = [];
	$(".btn_addExtra").show();
	$(".btn_addExtra").button({
		icons: {
			primary: "ui-icon-circle-plus"
		}
	});
	$(".btn_saveExtra").button({
		icons: {
			primary: "ui-icon-check"
		}
	});
	$(".btn_cancel").button({
		icons: {
			primary: "ui-icon-cancel"
		}
	});
	$(".btn_addExtra").click(function () {
		$(this).hide();
		$("#newExtraForm").show();
	});
	$(".btn_delete_extra").click(function (event) {
		event.preventDefault();
		$(this).parents("#extraForm").submitForm("deleteExtra.action");
	});
	$(".renameExtra").click(function () { //gestisco il rename facendo comparire il form relativo
		$(this).hide();
		$(this).siblings(".extraName").hide();
		$(this).siblings(".renameExtraForm").show();
		$(this).siblings(".renameExtraForm").select();
	});
	$(".renameExtraForm").blur(function () { //gestisco il blur per salvare la rinomina dell'extra
		//var extraName = added.find(".extraName").text();
		//var index = values.indexOf(extraName); 					//memorizzo l'indice del div corrente usando il nome dell'extra
		var newName = $(this).val(); //memorizzo il nome dell'extra modificato
		//values.splice(index,1, newName);
		$(this).hide();
		$(".renameExtra").show(); //mostro il link di rinomina
		$(this).siblings(".extraName").text(newName); //setto il nome dell'extra modificato
		$(".extraName").show(); //mostro il nome dell'extra modificato
	});

	$(".btn_saveExtra").click(function () {
		$("#extraForm").hide();
		$(".btn_addExtra").show();
	});
	//---  END EXTRAS SECTION CODE  
	
	
	

	
});