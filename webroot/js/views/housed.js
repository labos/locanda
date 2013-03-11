/*
 * @class SelectBookerView
 * @parent Backbone.View
 * @constructor
 * Select a guest booker.
 * @tag views
 * @author LabOpenSource
 */
window.SelectBookerView = RowView.extend({
	modifing: false, //a special init param for update with rest or not
	id_booking: 0,
	indexTemplate: "#selectbooker-template",
	el: '#selectbooker-list',
    initialize: function (options) {
    	this.modifing = options.modifing;
    	if (options.id_booking) { 
    		this.id_booking = options.id_booking;
    	}
        this.render();
        this.get();
    },
    render: function (model) {
    	var that = this;
    	if (model) {
    		$(this.el).html(Mustache.to_html($(this.indexTemplate).html(), {data:model}));
    	} else {
    		$(this.el).html(Mustache.to_html($(this.indexTemplate).html(), {}));
    	}
        
        /* Button for set booker */
        $(".set_booker").button({
            icons: {
                primary: "ui-icon-arrowreturnthick-1-s"
            }
        }).click(function(event){
        	//show colorbox for setGuests
        	$.colorbox({
        		iframe:true,
        		width:980,
        		height:560,
        		href:'goUpdateGuestsFromPlanner.action?sect=guests&callback=setbooker&housed=false',
        		onOpen:function() {
        			//$('.ui-dialog .ui-widget').hide();
        			//$('.ui-widget-overlay').hide();
        		},
        		onCleanup: function() {
        			//$('.ui-dialog .ui-widget').show();
        			//$('.ui-widget-overlay').show();
        			//$('.btn_save').hide();
        			//$('.canc_booking').hide();
        			
        			//refresh this widget
        			that.get();
        		}
        	});
        });
        
        return this;
    },
    get: function() {
    	//get current booker guest
    	var that = this;
    	var id = $('input:hidden[name="booking.booker.id"]').get(0).value;
    	if (id != "" && id > 0) {
    		$.ajax({
        		url:'rest/guests/'+id,
        		data:{},
        		success:function(data) {
        			var json = $.parseJSON(JSON.stringify(data, undefined, 2));
        			return that.render(json);
        		},
        		error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
        	});
    	}
    },
    select: function(model) {
    	var that = this;
    	//set current booker guest
    	$('input:hidden[name="booking.booker.id"]').get(0).value = model.id;
    	if (this.modifing == true) {
    		//if a mod.. call rest/PUT
    		$.ajax({
        		type:'PUT',
        		url:'rest/booker/'+model.id,
        		contentType: "application/json",
        		data: JSON.stringify({
        			id_booking: this.id_booking,
        			id_guest: model.id,
        		}),
        		success:function(data) {
        			//var json = $.parseJSON(JSON.stringify(data, undefined, 2));
        			that.get();
        		},
        		error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
        	});
    	}
    	
    	//update frontend
    	this.render(model);
    }
});

/*
 * @class SelectGroupLeaderView
 * @parent Backbone.View
 * @constructor
 * Select a Group Leader guest.
 * @tag views
 * @author LabOpenSource
 */
window.SelectGroupLeaderView = RowView.extend({
	indexTemplate: "#selectgroupleader-template",
	el: '#selectgroupleader-list',
	current_groupLeader:null,
    initialize: function (options) {
    	this.get();
    },
    events: {
    	'click input:radio[name="groupType"]':'switchgroupType'
    },
    render: function (model, housedType) {
    	var that = this;
    	if (model) {
    		//if for family/group
    		if (housedType) {
    			model.family = (housedType.description=='CAPOFAMIGLIA') ? true : null;
    			model.group = (housedType.description=='CAPOGRUPPO') ? true : null;
    		}
    		$(this.el).html(Mustache.to_html($(this.indexTemplate).html(), {data:model}));
    	} else {
    		$(this.el).html(Mustache.to_html($(this.indexTemplate).html(), {}));
    	}
        
        /* Button for set group leader guests */
        $(".set_groupleader").button({
            icons: {
                primary: "ui-icon-arrowreturnthick-1-s"
            }
        }).click(function(event){
        	//show colorbox for setGuests
        	$.colorbox({
        		iframe:true,
        		width:980,
        		height:560,
        		href:'goUpdateGuestsFromPlanner.action?sect=guests&callback=setgroupleader&housed=true',
        		onOpen:function() {
        			//$('.ui-dialog .ui-widget').hide();
        			//$('.ui-widget-overlay').hide();
        		},
        		onCleanup: function() {
        			//$('.ui-dialog .ui-widget').show();
        			//$('.ui-widget-overlay').show();
        			//$('.btn_save').hide();
        			//$('.canc_booking').hide();
        			that.get();
        		}
        	});
        });
        return this;
    },
    switchgroupType: function(e) {
    	//switch the groupType when already selected.
    	e.stopPropagation();
    	var that = this;
    	var id_booking = parseInt($('input:hidden[name="booking.id"]').get(0).value);
    	var current_groupType = $(e.currentTarget).val();
    	$.ajax({
    		type:'PUT',
    		url:'rest/groupLeader/'+that.current_groupLeader.id,
    		contentType: "application/json",
    		data: JSON.stringify({
    			id_booking: id_booking,
    			id_guest: that.current_groupLeader.housed.guest.id,
    			groupType: current_groupType
    		}),
    		success:function(data) {
    			that.get();
    		},
    		error: function() {
    			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
    		}
    	});
    },
    get: function() {
    	//get current groupLeader
    	var that = this;
    	var id_booking = parseInt($('input:hidden[name="booking.id"]').get(0).value);
    	$.ajax({
    		url:'rest/groupLeader/booking/'+id_booking,
    		data:{},
    		success:function(data) {
    			var json = $.parseJSON(JSON.stringify(data, undefined, 2));
    			if (json.housed) {
    				that.current_groupLeader = json;
    				that.render(json.housed.guest, json.housed.housedType);
    			} else {
    				that.render();
    			}
    		},
    		error: function() {
    			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
    		}
    	});
    },
    select: function(model) {
    	//set current group leader
    	var that = this;
    	var id_booking = parseInt($('input:hidden[name="booking.id"]').get(0).value);
    	//if already present
    	if (that.current_groupLeader) {
    		$.ajax({
        		type:'PUT',
        		url:'rest/groupLeader/'+that.current_groupLeader.id,
        		contentType: "application/json",
        		data: JSON.stringify({
        			id_booking: id_booking,
        			id_guest: model.id,
        			groupType: "CAPOFAMIGLIA" //manual default
        		}),
        		success:function(data) {
        			//var json = $.parseJSON(JSON.stringify(data, undefined, 2));
        			that.get();
        		},
        		error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
        	});
    	} 
    	//new selection
    	else {
	    	$.ajax({
	    		type:'POST',
	    		url:'rest/groupLeader',
	    		contentType: "application/json",
	    		data: JSON.stringify({
	    			id_booking: id_booking,
	    			id_guest: model.id,
	    			groupType: "CAPOFAMIGLIA" //manual
	    		}),
	    		success:function(data) {
	    			//var json = $.parseJSON(JSON.stringify(data, undefined, 2));
	    			that.get();
	    		},
	    		error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
	    	});
    	}
    }
});


/*
 * @class ListHousedView
 * @parent Backbone.View
 * @constructor
 * List of housed guest.
 * @tag views
 * @author LabOpenSource
 */
window.ListHousedView = RowView.extend({
	indexTemplate: "#selecthouseds-template",
	id_booking:0,
	el: '#selecthouseds-list',
    events: {
        "click span.row-item-destroy": "del",
        "click .housed_cleardate":"clearDate",
        "click .housed_selectdate":"showDateSelector",
    },
    initialize: function (options) {
    	this.id_booking = options.id_booking;
    	this.collection = new Houseds([],{});
        //this.collection.bind('change', this.render, this);
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.render, this);
        this.collection.bind('add', this.render, this);
        this.collection.fetch({
        	url:'rest/housed/booking/'+options.id_booking
        });
    },
    /**
     * Convert date in local format.
     * @param {String} date string to be converted.
     * @return {String} date string converted using local date settings.
     */
    convertDate: function (aStringDate) {
        var dateDate = new Date(parseInt(aStringDate));
        return $.datepicker.formatDate(I18NSettings.datePattern, dateDate);
    },
    // Re-render the contents of the todo item.
    render: function () {
    	var that = this;
    	var coll = this.collection.toJSON();
    	
    	//convert data
    	for (c in coll) {
    		coll[c].checkInDate = (coll[c].checkInDate) ? this.convertDate(coll[c].checkInDate) : null;
    		coll[c].checkOutDate = (coll[c].checkOutDate) ? this.convertDate(coll[c].checkOutDate) : null;
    	}
    	
        $(this.el).html(Mustache.to_html($(this.indexTemplate).html(), {hs:coll}));
        
        /* Button for set housed guests */
        $(".set_guests").button({
            icons: {
                primary: "ui-icon-arrowreturnthick-1-s"
            }
        }).click(function(event){
            /* Check max number of guests before adding a new housed */        	
        	$.ajax({
	    		type:'GET',
	    		url:'rest/housed/booking/' +that.id_booking +'/maxGuests',
	    		success: function(data) {
	    			//default is false..
	    			var addNewGuestPermission = false;
	    			
	    			if (typeof parseInt(data) == "number" && parseInt(data) >  that.collection.size()) {

	    				addNewGuestPermission = true;
	    			}
	    			else{
	    				addNewGuestPermission = false;
	    				$.jGrowl($.i18n("alertOverwriteGuest") + ' ', {theme: "notify-error",sticky: true   });   				
	    				
	    			}
	    			if (addNewGuestPermission) {
	    	         	//show colorbox for setGuests
	    	        	$.colorbox({
	    	        		iframe:true,
	    	        		width:980,
	    	        		height:560,
	    	        		href:'goUpdateGuestsFromPlanner.action?sect=guests&callback=setguests&housed=false',
	    	        		onOpen:function() {
	    	        			//$('.ui-dialog .ui-widget').hide();
	    	        			//$('.ui-widget-overlay').hide();
	    	        		},
	    	        		onCleanup: function() {
	    	        			//$('.ui-dialog .ui-widget').show();
	    	        			//$('.ui-widget-overlay').show();
	    	        			//$('.btn_save').hide();
	    	        			//$('.canc_booking').hide();
	    	        			that.render();
	    	        		}
	    	        	});
	    				
	    			}
	    		},
	    		error: function() {
        			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
        		}
	    	});
      

        });
        
        //UI for slideup/slideDown
        $(".housed_accordion").accordion({
            collapsible: true,
            active: false,
            animated: 'bounceslide',
            autoHeight: false
        });
        
        
        //UI DatePicker
        $.each($('.housed_inputdate'),function(i,v){
        	$(v).datepicker({
        		dateFormat: 'dd/mm/yy',
        		onSelect: function(dateString, instance) {
        			//get from hidden the correct id of housed
        			var current_id = parseInt($(v).parent().parent().parent().parent().parent().parent().find("input:hidden['name=housedid']").val());
        			var model = that.collection.get({id:current_id});
        			//update with selected date.
        			var date = $(v).val();
        			var checkin = ($(v).attr('name').search('datecheckin')>-1) ? $(v).val():null;
        			var checkout = ($(v).attr('name').search('datecheckout')>-1) ? $(v).val():null;
        			var datein = (checkin) ? parseInt($.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, date))) :null;
        			var dateout = (checkout) ? parseInt($.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, date))) :null;
        			
        			pdata = {
        					id: model.get("id"),
        					id_booking: that.id_booking,
        					id_guest: model.get("guest").id,
        					checkInDate: datein,
        					checkOutDate: dateout,
        			};
        	    	$.ajax({
        	    		type:'PUT',
        	    		url:'rest/housed/'+model.get("id"),
        	    		contentType: "application/json",
        	    		data: JSON.stringify(pdata),
        	    		success: function(data) {
        	    			if (checkin) {
        	    				model.set({checkInDate:datein})
        	    			}
        	    			if (checkout) {
        	    				model.set({checkOutDate:dateout});
        	    			}
        	    		},
        	    		error: function() {
                			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
                		}
        	    	});
        		}
        	});
        });
        
        return this;
    },
    showDateSelector: function(e) {
    	//launch date selector for prev element with focus!
    	$(e.currentTarget).prev().focus();
    },
    clearDate: function(e) {
    	e.stopPropagation();
    	//clear date checkin/checkout
    	//get from hidden the correct id of housed
    	var hidden_id = $(e.currentTarget).parent().parent().parent().parent().parent().parent().parent().find("input:hidden['name=housedid']");
    	var input = $(e.currentTarget).parent().find('input:text');
		var current_id = parseInt(hidden_id.val());
		var model = this.collection.get({id:current_id});
    	//clear a date of checkin/checkout
    	pdata = {
				id: model.get("id"),
				id_booking: this.id_booking,
				id_guest: model.get("guest").id,
		};
    	if (input.attr('name').search('checkin')>-1) {
    		pdata.checkInDate = null;
    		model.set({"checkInDate":null});
    	}
    	if (input.attr('name').search('checkout')>-1) {
    		pdata.checkOutDate = null;
    		model.set({"checkOutDate":null});
    	}
		$.ajax({
    		type:'PUT',
    		url:'rest/housed/'+model.get("id"),
    		contentType: "application/json",
    		data: JSON.stringify(pdata),
    		success: function(data) {
    			$(input).get(0).value = '';
    		},
    		error: function() {
    			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
    		}
    	});
    },
    del: function(e) {
    	var that = this,
    	id = $(e.currentTarget).parent().find(":hidden").get(0).value;
    	m = this.collection.get(id);
    	m.destroy({
    		success: function(model, data){
    			that.collection.remove(model);
    		},
    		error: function() {
    			$.jGrowl($.i18n("delHousedGroupLoader") + '', { header: this.alertOK,sticky: true });
    		}
    	});
    },
    select: function(model) {
    	//add a housed by received guest model
    	//check if already present
    	var c = this.collection.toJSON();
    	for (m in c) {
    		if (c[m].guest.id==model.id) {
    			$.jGrowl($.i18n("alreadyHoused") + '', { header: this.alertOK,sticky: true });
    			return false;
    		}
    	}
    	var that = this;
    	var pdata = {};
    	pdata.id_guest = model.id;
    	pdata.id_booking = this.id_booking;
    	var new_model = {
        	booking: null,
        	guest: model,
        	checkin_date: null,
        	checkout_date: null,
        	housedType: 'family',
        	exported: false,
        	deleted: false
        };
    	$.ajax({
    		type:'POST',
    		url:'rest/housed',
    		contentType: "application/json",
    		data: JSON.stringify(pdata),
    		success: function(data) {
    			new_model.id = parseInt(data);
        		new_model.isNew = false;
    			that.collection.add([new_model]);
    		},
    		error: function() {
    			$.jGrowl($.i18n("seriousErrorDescr") + '', { header: this.alertOK,sticky: true });
    		}
    	});
    }
});