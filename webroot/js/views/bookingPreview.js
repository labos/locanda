/*******************************************************************************
 *
 *  Copyright 2013 - Sardegna Ricerche, Distretto ICT, Pula, Italy
 *
 * Licensed under the EUPL, Version 1.1.
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 *  http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 * In case of controversy the competent court is the Court of Cagliari (Italy).
 *******************************************************************************/
/*
 * @class BookingPreviewRowView 
 * @parent Backbone.View
 * @constructor
 * Class to show a bookingPreview.
 * @tag views
 * @author LabOpenSource
 */
window.BookingPreviewRowView = Backbone.View.extend({
    tagName: "li",
    indexTemplate: $("#bookingPreview-row-template"),
    // The DOM events specific to an row.
    events: {
        "click": "switchMode"
    },
    initialize: function () {
        this.model.bind('change', this.render, this);
        this.model.bind('destroy', this.unrender, this);
    },
    /**
     * Render the contents of the bookingPreview item.
     */
    render: function () {
        // set a local row model object to use in the template
        var modelToRender = this.model.toJSON();
        // if the model is not new then convert dates.
        if (!this.model.isNew()) {
            modelToRender.dateIn = this.convertDate(modelToRender.dateIn);
            modelToRender.dateOut = this.convertDate(modelToRender.dateOut);
        }
        // render model using template engine
        $(this.el).html(Mustache.to_html(this.indexTemplate.html(), modelToRender));

        this.delegateEvents();
        return this;
    },
    /**
     * un-render current row and un-register events.
     */
    unrender: function () {
        // trigger an update event.
        this.trigger("bookingPreview:update", this);
        //clean up events raised from the view
        this.unbind();
        //clean up events from the DOM
        $(this.el).remove();
    },
	/*
	 * Show a particular booking
	 */
    switchMode: function(event) {
		var $dialogContent = $("#event_edit_container");
        $dialogContent.addClass("loaderback").load("goUpdateBookingFromPlanner.action", {
            id: this.model.get("id")
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
                if (self.booking.modified)  {
              	  if(confirm($.i18n("alertCancel"))){
                        $dialogContent.dialog("destroy");
                        self.$calendar.weekCalendar("removeUnsavedEvents");
                        self.$calendar.weekCalendar("refresh");  
              	  }else{
              		  return false;
              	  }
                }
                else{
                    $dialogContent.dialog("destroy");
                    self.$calendar.weekCalendar("removeUnsavedEvents");
                    self.$calendar.weekCalendar("refresh");
                }
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
    /**
     * Convert date in local format.
     * @param {String} date string to be converted.
     * @return {Stringlogin.action} date string converted using local date settings.
     */
    convertDate: function (aStringDate) {
        var dateDate = new Date(parseInt(aStringDate));
        return $.datepicker.formatDate(I18NSettings.datePattern, dateDate);
    },
    /**
     * Save or update current bookingPreview model.
     * @param {Object} event that is launched by a save request.
     */
    save: function (e) {
        e.preventDefault();
        var self = this,
            is_new = this.model.isNew() ? true : false,
            item = this.model.clone(),
            // extract model by the template DOM
            modelToSave = $("#edit-form").serializeObject();
        // format date inputs in timestamp format.
        modelToSave.dateIn = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.dateIn));
        modelToSave.dateOut = $.datepicker.formatDate('@', $.datepicker.parseDate(I18NSettings.datePattern, modelToSave.dateOut));
        // save local model item
        item.save(modelToSave, {
            success: function (model, resp) {
                // set current model with new saved model and initialize it.
                self.model.set(model);
                self.model.initialize();
                // if it's an new model, add to current collection.
                if (is_new) {
                    self.model.collection.add(self.model);
                }
                // trigger an update event.
                self.trigger("bookingPreview:update", self);
                // show a notification.
                $.jGrowl($.i18n("congratulation"), {
                    header: this.alertOK, position: 'top-right'
                });
                // switch to in Non-Edit mode
                self.switchMode();
            },
            error: function (model, resp) {
                $.jGrowl(resp.responseText, {
                    header: this.alertKO,
                    theme: "notify-error"
                });
            }
        });
        return false;
    },
    /**
     * Remove this view from the DOM.
     */
    remove: function () {
        if (confirm($.i18n("alertDelete"))) {
            var self = this;
            this.model.destroy({
                success: function () {
                    $.jGrowl($.i18n("cancelSuccess"), {
                        header: this.alertOK, position: 'top-right' 
                    });
                    self.switchMode();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    textStatus.responseText || (textStatus.responseText = $.i18n("seriousErrorDescr"));
                    $.jGrowl(textStatus.responseText, {
                        header: this.alertKO,
                        theme: "notify-error",
                        sticky: true 
                    });
                }
            });
        }
    },
    /**
     * Clear all attriblogin.actionutes from the model.
     */
    clear: function () {
        this.model.clear();
    }
});
/*
 * @class bookingPreviewListView 
 * @parent Backbone.View
 * @constructor
 * Class to show list of bookingPreview.
 * @tag views
 * @author LabOpenSource
 */
window.BookingPreviewListView = Backbone.View.extend({
    indexTemplate: null,
    events: {
        "click .btn_add": "addNew"
    },
    initialize: function (options) {
        this.indexTemplate = $("#bookingPreview-view-template");
        _.bindAll(this, "addOne");
        this.collection.bind('reset', this.render, this);
        this.collection.bind('remove', this.removeOne, this);
        // declare container object id  as null.
        this.id = null;
        // set row views array to contain a list of bookingPreview rows.
        this.rowViews = [];
    },
    render: function () {
        $(this.el).html(this.indexTemplate.html());
        // render add bookingPreview button
        this.$(".btn_add").button({
            icons: {
                primary: "ui-icon-plusthick"
            }
        });
        // add all bookingsPreview if a rendering request occur
        this.addAll();
        // hide list of bookingsPreview if the season is new and not saved.
        //(typeof this.id !== 'undefined' && this.id) ? $(this.el).show() : $(this.el).hide();
        this.delegateEvents();
        return this;
    },
    /**
     * Add all items in the collection at once.
     */
    addAll: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        this.rowViews = [];
        this.collection.each(this.addOne);
        if( this.collection.length == 0 ){
        	this.$("ul").append('<h4>' + $.i18n("guestWithoutBookings")+ '</h4>');	
        }
        this.trigger("bookingPreviewList:update", this);
    },
    /**
     * Called if a model is removed from the collection.
     */
    removeOne: function () {
        // enter additional code in case of row deleted
    },
    /**
     * Add a bookingPreview.
     * @param {Object} bookingPreview to show for a season.
     */
    addOne: function (item) {
        var self = this;
        var view = new BookingPreviewRowView({
            model: item
        });
        // attach an handler to every view update event and trigger a further event from this bookingPreviewListView
        view.bind("bookingPreview:update", function () {
            self.trigger("bookingPreview:update", this);
        }, self);
        // set this collection to the view
        view.model.collection = this.collection;
        // push current view in the rowView list
        this.rowViews.push(view);
        // add current view into html template already rendered
        this.$("ul").append(view.render().el);
    },
    /**
     * Add new bookingPreview. It's called when add new bookingPreview button is pushed.
     */
    addNew: function () {
        this.addOne(new BookingPreview({
            id: this.id
        }));
        // get the last rowView just added and switch it in edit mode
        _.last(this.rowViews).switchMode();
    },
    /**
     * Disable current view.
     */
    disable: function () {
        $.each(this.rowViews, function (index, value) {
            this.unrender();
        });
        this.rowViews = [];
        $(this.el).hide();
    }
});