$(function() {
	
	
	 $.Class.extend('Controller.Season',
			 
			 /* @static */
				  {
				  },
				 {
					init: function( ){
						
						/* Hide/Show season name change */
					    $(".btn_season").button({
					        icons: {
					            primary: "ui-icon-circle-plus"
					        }
					    }).click(function (event) {
							event.preventDefault();
							$(this).parents(".yform").submitForm();
						});
						$(".rename_season").toggle(function () {
							$(this).siblings('input[name="season.name"]').focus().css("border", "1px solid").removeAttr("readonly");
						}, function () {
							$('input[name="season.name"]').css("border", "none").attr("readonly", "true");
						}); 
						/* Add period management */
														
						$(".add_period").click(function () {
							//count the number of periods already added
							var formParent = $(this).parents(".subcolumns");
							var num_of_periods = formParent.siblings(".subcolumns.period").size();
							// get last subcolumns
							var dd = formParent.siblings(".subcolumns:last");
							// setup of cloned row to add
							var added = new EJS({url: 'js/views/season/show.ejs'}).render({index: num_of_periods, labels:{to: $.i18n("to"),from: $.i18n("from"), delete: $.i18n("delete")}});
							$added = $(added);
							$added.insertAfter(dd);

							
							// attach listener to cloned row
							// attach erase click
							$added.find(".erase_period").click(function () {
								$(this).closest(".subcolumns").remove();
							});
							$(".rename_season").toggle(function () {
								$(this).siblings('input[name="season.name"]').focus().css("border", "1px solid").removeAttr("readonly");
							}, function () {
								$('input[name="season.name"]').css("border", "none").attr("readonly", "true");
							});
							// attack datepickers
							$added.find(".adddatepicker").removeClass('hasDatepicker').datepicker("destroy");
							$added.find(".adddatepicker").datepicker({
								showOn: "button",
								buttonImage: "images/calendar.gif",
								buttonImageOnly: true,
								dateFormat: I18NSettings.datePattern
							});
							return false;
						});
						$(".erase_period").click(function () {
							$(this).closest(".subcolumns").remove();
						});
						
						
					}  
					  
				 });
	 

	//---  END SEASONS SECTION CODE 
	 
	 new Controller.Season(I18NSettings.lang, I18NSettings.datePattern);
});
