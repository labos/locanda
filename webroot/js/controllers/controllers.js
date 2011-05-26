/**
 * @tag controllers, home
 * Make a checkin request
 */
jQuery.Controller.extend('Locanda.Controllers.CheckIn',
/* @Static */
{
	onDocument: true
},
/* @Prototype */
{
 /**
 * When the page loads, gets all checkin to be displayed.
 */
 load: function(){
	if(!$("#guest").length){
	 $(document.body).append($('<div/>').attr('id','guest'));
		 Locanda.Models.Guest.findAll({term: 'ro'}, this.callback('list'));
 	}
 },
 /**
 * Displays a list of guests and the submit form.
 * @param {Array} guests An array of Locanda.Models.Guest objects.
 */
 list: function( guests ){
	$('#guest').html(this.view('init', {guests:guests} ));
 },
 

 /**
 * Creates and places the edit interface.
 * @param {jQuery} el The guest's edit link element.
 */
'.edit click': function( el ){
	var guest = el.closest('.guest').model();
	guest.elements().html(this.view('edit', guest));
},

 /**
 * Updates the guest from the edit values.
 */
'.btn_check_in click': function( el ){
	ev.preventDefault();
	idBooking = (typeof parseInt($('input:hidden[name="booking.id"]').val()) === 'number') ? parseInt($('input:hidden[name="booking.id"]').val()) : null;
	if (idBooking && idBooking > 0) {
	Locanda.Models.CheckIn.update( idBooking, {}, this.callback('checkinSuccess'), this.callback('checkinError') );
	}
	},
/**
 * Get returned message from checkin action.
 * @param {Array} guests An array of Locanda.Models.Guest objects.
 */
 checkinSuccess: function( data_action ){
	
		var title_notification = null;
		if (data_action.result == "success") {
			$().notify($.i18n("congratulation"), data_action.description);
			//UPDATE BOOKING STATUS HIDDEN FIELD
			$("input:hidden[name='booking.status']").val("checkin");
			//ADD CHECKOUT LISTENER
			$this.attr("class", "btn_check_out").button("destroy").button({
				label: "CHECK OUT",
				icons: {
					primary: "ui-icon-check"
				}
			}).click(function (event) {
				event.preventDefault();
				var hrefAction = "checkOutBooking.action",
					$this = $(this),
					idBooking = (typeof parseInt($('input:hidden[name="booking.id"]').val()) === 'number') ? parseInt($('input:hidden[name="booking.id"]').val()) : null;
				$.ajax({
					type: "POST",
					url: hrefAction,
					data: {
						id: idBooking
					},
					success: function (data_action) {
						var title_notification = null;
						if (data_action.result == "success") {
							$().notify($.i18n("congratulation"), data_action.description);
							// UPDATE BUTTON CHECKING
							$this.button({
								disabled: true,
								label: "CHECKED"
							});
							// END UPDATE BUTTON CHECKING
							//UPDATE BOOKING STATUS HIDDEN FIELD
							$("input:hidden[name='booking.status']").val("checkout");
						} else if (data_action.result == "error") {
							$().notify($.i18n("warning"), data_action.description);
						} else {
							$(".validationErrors").html(data_action);
						}
					},
					error: function () {
						$().notify("Errore Grave", "Problema nella risorsa interrogata nel server");
					}
				});
			});
			//END ADDING CHECKOUT LISTENER
		} else if (data_action.result == "error") {
			$().notify($.i18n("warning"), data_action.description);
		} else {
			$(".validationErrors").html(data_action);
		}
	 
	 
 },
 /**
  * Get error  from checkin action.
  * @param {Array} guests An array of Locanda.Models.Guest objects.
  */
  checkinError: function( data_action ){
 	
 	 
 	 
 	 
  }
});