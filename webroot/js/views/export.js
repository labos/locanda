/*
 * @class ExportView
 * @parent Backbone.View
 * @constructor
 * View for export.
 * @tag views
 * @author LabOpenSource
 */
window.ExportView = Backbone.View.extend({
	el: $('#export-widget'),
	events: {
		'change #exportsList':'selectedExport',
		'click .btn_export_questura':'doExportQuestura',
		'click .btn_export_sired':'doExportSired',
	},
	initialize: function(options) {
		this.render();
	},
	render: function() {
		$(this.el).html(Mustache.to_html($('#export-template').html(), {}));
		$(".btn_export_questura").button({
            icons: {
                primary: "ui-icon-check"
            }
        });
		$(".btn_export_sired").button({
            icons: {
                primary: "ui-icon-check"
            }
        });
		
		// initialize and render datepickers.
        this.$(".datepicker").removeClass('hasDatepicker').datepicker("destroy");
		this.$( 'input[name="dateExport"]' ).datepicker({
 			maxDate:  new Date(),
			changeMonth: true,
			changeYear: true,
            showOn: "both",
            buttonImage: "images/calendar.gif",
            buttonImageOnly: true,
            dateFormat: 'dd/mm/yy',
		});
	},
	selectedExport: function(e) {
		alert('sssssssse');
	},
	doExportQuestura: function(e) {
		alert('Ti arresto!');
	},
	doExportSired: function(e) {
		alert('boooo');
	}
});

window.ExportWidget = new ExportView({
	
});