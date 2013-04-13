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
	availableDates:[], //timestamps of available dates
	availableDatesQuestura:[], //timestamps of available dates for questura export
	events: {
		'change #exportDateList':'selectedDateExport',
		'change #exportDateListQuestura':'selectedDateExportQuestura',
		'click .exportType':'changeExportType',
		'click .exportTypeQuestura':'changeExportTypeQuestura',
		'click .btn_check_questura':'checkExportQuestura',
		'click .btn_check_sired':'checkExportSired',
		'click .btn_export_questura':'doExportQuestura',
		'click .btn_export_sired':'doExportSired',
	},
	/**
     * Convert date in local format.
     * @param {String} date string to be converted.
     * @return {String} date string converted using local date settings.
     */
    convertDate: function (aStringDate) {
        var dateDate = new Date(parseInt(aStringDate));
        return $.datepicker.formatDate("dd/mm/yy", dateDate);
    },
    /**
     * Convert date in ts format.
     * @param {String} date string to be converted.
     * @return {String} date string converted using local date settings.
     */
    unconvertDate: function (aStringDate) {
    	var d = $.datepicker.parseDate("dd/mm/yy", aStringDate);
        return parseInt($.datepicker.formatDate("@", d));
    },
    /*
     * Get date in timestamp format to post
     */
    getDate: function() {
    	if ($('.exportType:checked').val()==0){
    		var d = $('#exportDateList option:selected').val();
    		if (d!='') {
    			return parseInt(d);
    		}
    	} else {
    		var d = $('#dateExport').val();
    		if (d!='') {
    			return this.unconvertDate(d);
    		}
    	}
    },
    /*
     * Get date in timestamp format to post
     */
    getDateQuestura: function() {
    	if ($('.exportTypeQuestura:checked').val()==0){
    		var d = $('#exportDateListQuestura option:selected').val();
    		if (d!='') {
    			return parseInt(d);
    		}
    	} else {
    		var d = $('#dateExportQuestura').val();
    		if (d!='') {
    			return this.unconvertDate(d);
    		}
    	}
    },
	initialize: function(options) {
		this.render();
	},
	render: function() {
		var that = this;
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
		
		$(".btn_check_questura").button({
            icons: {
                primary: "ui-icon-check"
            }
        });
		$(".btn_check_sired").button({
            icons: {
                primary: "ui-icon-check"
            }
        });
		
		// initialize and render datepickers.
        this.$(".datepicker").removeClass('hasDatepicker').datepicker("destroy");
		this.$( 'input[name="dateExport"],input[name="dateExportQuestura"]' ).datepicker({
 			maxDate:  new Date(),
			changeMonth: true,
			changeYear: true,
            showOn: "both",
            buttonImage: "images/calendar.gif",
            buttonImageOnly: true,
            dateFormat: 'dd/mm/yy',
            onSelect: function() {
            	that.clearResult();
            	that.renderDateinfo();
            	that.renderDateinfoQuestura();	
            }
		});
		
		this.clearResult();
		this.initDates();
	},
	changeExportType: function(e) {
		this.clearResult();
		this.renderDateinfo();
	},
	changeExportTypeQuestura: function(e) {
		this.clearResult();
		this.renderDateinfoQuestura();
	},
	/*
	 * Clear the result
	 */
	clearResult: function() {
		$('#checkResult').html('');
	},
	/*
	 * Initialize Dates from server
	 */
	initDates: function() {
		var that = this;
		var id_structure = Entity.idStructure;
		$.ajax({
			url:'rest/export/structure/'+id_structure+'/dates/available',
			success: function(data) {
				var json = eval(data);
				that.availableDates = json;
				that.renderAvailableDates();
			},
			error: function(data) {
				if (data.status==404) {
    				$.jGrowl(data.responseText, { theme: "notify-error",header: this.alertOK,sticky: true });
    			} else {
    				$.jGrowl($.i18n("seriousErrorDescr") + '', { theme: "notify-error",header: this.alertOK,sticky: true });
    			}
			}
		});
		
		//call to get available dates for questura
		$.ajax({
			url:'rest/export/structure/'+id_structure+'/dates/availableQuestura',
			success: function(data) {
				var json = eval(data);
				that.availableDatesQuestura = json;
				that.renderAvailableDatesQuestura();
			},
			error: function(data) {
				if (data.status==404) {
    				$.jGrowl(data.responseText, { theme: "notify-error",header: this.alertOK,sticky: true });
    			} else {
    				$.jGrowl($.i18n("seriousErrorDescr") + '', { theme: "notify-error",header: this.alertOK,sticky: true });
    			}
			}
		});
	},
	/*
	 * Render DateList selector
	 */
	renderAvailableDates: function() {
		var model = {}
		//set dates
		var dates = [];
		for (t in this.availableDates) {
			var ts = this.availableDates[t];
			//convert
			var d = this.convertDate(ts);
			dates.push({date:d,ts:ts});
		}
		if (dates.length > 0) {
			model.dates = dates;
			model.populated = true;
		}
		$('#exportDateList').html(Mustache.to_html($('#export-exportDateList-template').html(),model));
	},
	/*
	 * Render DateList selector
	 */
	renderAvailableDatesQuestura: function() {
		var model = {}
		//set dates
		var dates = [];
		for (t in this.availableDatesQuestura) {
			var ts = this.availableDatesQuestura[t];
			//convert
			var d = this.convertDate(ts);
			dates.push({date:d,ts:ts});
		}
		if (dates.length > 0) {
			model.dates = dates;
			model.populated = true;
		}
		$('#exportDateListQuestura').html(Mustache.to_html($('#export-exportDateList-template').html(),model));
	},
	/*
	 * Render Bookings result
	 */
	renderBookings: function(collection) {
		var that = this;
		this.clearResult();
		if (collection.length>0) {
			var model = {bookings:collection};
		} else {
			var model = {};
		}
		$('#checkResult').html(Mustache.to_html($('#export-checkResult-template').html(),model));
		
		//bind functions
		if (collection.length>0) {
			$.each($($('#checkResult a')),function(i,v){
				$(v).bind('click',function(data){
					that.showBooking(collection[i].id);
				})
			});
		}
	},
	/*
	 * Show a particular booking
	 */
	showBooking: function(id_booked) {
		var $dialogContent = $("#event_edit_container");
        $dialogContent.addClass("loaderback").load("goUpdateBookingFromPlanner.action", {
            id: id_booked
        }, function () {
            $(this).removeClass("loaderback");
            new Main(I18NSettings.lang, I18NSettings.datePattern);
            delete (self.booking);
            self.booking = new Controllers.Booking(I18NSettings.lang, I18NSettings.datePattern);
            $(".btn_save").hide();
            $(".canc_booking").hide();
            //handle form submit event 
            $dialogContent.find(".yform.json").bind('submitForm', function(e,data){
          	  if(data.type == "success"){
          		  $(this).unbind('submitForm');
          		  self.booking.modified = false;
                    $dialogContent.dialog("close");
          	  }});
        }).dialog({
            open: function (event, ui) {               	  
            },
            modal: true,
            width: 790,
            position: 'top',
            closeOnEscape: false,
            title: $.i18n("modifyBooking"),
            beforeClose: function( event, ui ) {
            	$dialogContent.dialog("destroy");
            },
            buttons: [
                      {
                          text: $.i18n("save"),
                          click: function() {
                              if (!$dialogContent.find(".yform.json").valid()) {
                                  $("#accordion,#accordion2").accordion("option", "active", 0);
                              }
                              $dialogContent.find(".yform.json").submitForm();
                          }
                      },
                      {
                          text: $.i18n("erase"),
                          click: function() {
                              if (confirm($.i18n("alertDelete"))) {
                                  $dialogContent.find(".yform.json").submitForm("deleteBooking.action");
                                  
                              }
                          }
                      },
                      {
                          text: $.i18n("close"),
                          click: function() {
                                  $dialogContent.dialog("close");
                          }
                      }
                      ]                        
            
          
        }).show();
        $(window).resize().resize(); //fixes a bug in modal overlay size ??
	},
	renderDateinfo: function() {
		$('#dateInfo').addClass('none');
		var d = this.getDate();
		if (d) {
			//update visual date
			var time_start = '00:00';
			var date_start = this.convertDate((d-86400000));
			var time_end = '23:59';
			var date_end = this.convertDate(d);
			$('#startexporttime').html(time_start);
			$('#startexportdate').html(date_start);
			$('#endexporttime').html(time_end);
			$('#endexportdate').html(date_end);
			$('#dateInfo').removeClass('none');
		}
	},
	renderDateinfoQuestura: function() {
		$('#dateInfo').addClass('none');
		var d = this.getDateQuestura();
		if (d) {
			//update visual date
			var time_start = '00:00';
			var date_start = this.convertDate((d-86400000));
			var time_end = '23:59';
			var date_end = this.convertDate(d);
			$('#startexporttime').html(time_start);
			$('#startexportdate').html(date_start);
			$('#endexporttime').html(time_end);
			$('#endexportdate').html(date_end);
			$('#dateInfo').removeClass('none');
		}
	},
	selectedDateExport: function(e) {
		this.renderDateinfo();
	},
	selectedDateExportQuestura: function(e) {
		this.renderDateinfoQuestura();
	},
	checkExportQuestura: function(e) {
		var that = this;
		var d = this.getDateQuestura();
		var id_structure = Entity.idStructure;
		if (d) {
			$.ajax({
				url:'rest/export/structure/'+id_structure+'/check/questura/?date='+d,
				success:function(data) {
					var json = eval(data);
					return that.renderBookings(data);
				}
			});
		} else {
			$.jGrowl($.i18n("exportDateError") + ' ', { header: this.alertOK,sticky: true });
		}
	},
	checkExportSired: function(e) {
		var that = this;
		var d = this.getDate();
		var id_structure = Entity.idStructure;
		if (d) {
			$.ajax({
				url:'rest/export/structure/'+id_structure+'/check/sired/?date='+d,
				success:function(data) {
					var json = eval(data);
					return that.renderBookings(data);
				}
			});
		} else {
			$.jGrowl($.i18n("exportDateError") + ' ', { header: this.alertOK,sticky: true });
		}
	},
	doExportQuestura: function(e) {
		var d = this.getDateQuestura();
		var id_structure = Entity.idStructure;
		var force = "false";
		if (d) {
			if ($('.exportTypeQuestura:checked').val()==1){
				force = "true";
			}
			window.location='rest/export/structure/'+id_structure+'/do/questura?date='+d+'&force=' + force;
		} else {
			$.jGrowl($.i18n("exportDateError") + ' ', { header: this.alertOK,sticky: true });
		}
	},
	doExportSired: function(e) {
		var d = this.getDate();
		var id_structure = Entity.idStructure;
		var force = "false";
		if (d) {
			if ($('.exportType:checked').val()==1){
				force = "true";
			}
			window.location='rest/export/structure/'+id_structure+'/do/sired?date='+d+'&force=' + force;		
		} else {
			$.jGrowl($.i18n("exportDateError") + ' ', { header: this.alertOK,sticky: true });
		}
	},
});

window.ExportWidget = new ExportView({
	
});