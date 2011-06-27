$(function() {

    $.Class.extend('Controllers.Upload', /* @static */ {	
    	init: function () {

    		
	$.fn.addImageObject = function (responseObject, actionName) {
		if (responseObject && typeof actionName !== "undefined" && actionName && typeof actionName === "string") {
			if (responseObject.roomFacility && actionName.indexOf("uploadFacility") >= 0) {
				//get the name of the facility
				var name_facility = responseObject.roomFacility.name;
				//get the file name of the facility
				var file_facility = responseObject.roomFacility.fileName;
				//get the id of the facility
				var id_facility = responseObject.roomFacility.id;
				//clone the html portion to replicate
				var facility_row_checked_cloned = $(".facility:hidden").clone();
				//set src file name
				var src = facility_row_checked_cloned.find('img').attr("src") + file_facility;
				//add src file name
				facility_row_checked_cloned.find('img').attr("src", src);
				//add checkbox id
				facility_row_checked_cloned.find('input:checkbox').attr("id", id_facility + "_fac");
				//add checkbox name
				facility_row_checked_cloned.find('input:checkbox').attr("name", "roomFacilitiesIds");
				//add checkbox value
				facility_row_checked_cloned.find('input:checkbox').attr("value", id_facility);
				//add label text
				facility_row_checked_cloned.find('label').attr("for", id_facility + "_fac").text(name_facility);
				facility_row_checked_cloned.insertAfter($(".facility:last")).show();
				facility_row_checked_cloned.animate({
					backgroundColor: "#A2D959",
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.roomFacility && actionName.indexOf("uploadRoomTypeFacility") >= 0) {
				//get the name of the facility
				var name_facility = responseObject.roomFacility.name;
				//get the file name of the facility
				var file_facility = responseObject.roomFacility.fileName;
				//get the id of the facility
				var id_facility = responseObject.roomFacility.id;
				//clone the html portion to replicate
				var facility_row_checked_cloned = $(".facility:hidden").clone();
				//set src file name
				var src = facility_row_checked_cloned.find('img').attr("src") + file_facility;
				//add src file name
				facility_row_checked_cloned.find('img').attr("src", src).click(function () {
					alert("modifica?");
				});
				//add checkbox id
				facility_row_checked_cloned.find('input:checkbox').attr("id", id_facility + "_fac");
				//add checkbox name
				facility_row_checked_cloned.find('input:checkbox').attr("name", "roomTypeFacilitiesIds");
				//add checkbox value
				facility_row_checked_cloned.find('input:checkbox').attr("value", id_facility);
				//add label text
				facility_row_checked_cloned.find('label').attr("for", id_facility + "_fac").text(name_facility);
				facility_row_checked_cloned.insertAfter($(".facility:last")).show();
				facility_row_checked_cloned.animate({
					backgroundColor: "#A2D959",
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.image && actionName.indexOf("uploadStructureImage") >= 0) {
				//get the name of the image
				var name_image = responseObject.image.name;
				//get the file name of the image
				var file_image = responseObject.image.fileName;
				//get the id of the image
				var id_image = responseObject.image.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_image;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_image);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_image;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.structureFacility && actionName.indexOf("uploadStructureFacility") >= 0) {
				//get the name of the facility
				var name_facility = responseObject.structureFacility.name;
				//get the file name of the facility
				var file_facility = responseObject.structureFacility.fileName;
				//get the id of the facility
				var id_facility = responseObject.structureFacility.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs_facility li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_facility;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_facility);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_facility;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs_facility li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.image && actionName.indexOf("uploadRoomImage") >= 0) {
				//get the name of the image
				var name_image = responseObject.image.name;
				//get the file name of the image
				var file_image = responseObject.image.fileName;
				//get the id of the image
				var id_image = responseObject.image.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_image;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_image);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_image;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			} else if (responseObject.image && actionName.indexOf("uploadRoomTypeImage") >= 0) {
				//get the name of the image
				var name_image = responseObject.image.name;
				//get the file name of the image
				var file_image = responseObject.image.fileName;
				//get the id of the image
				var id_image = responseObject.image.id;
				//clone the html portion to replicate
				var image_row_cloned = $(".thumbs li:hidden").clone();
				//set src file name
				var src = image_row_cloned.find('a.thumb img').attr("src") + file_image;
				//add src file name
				image_row_cloned.find('a.thumb img').attr("src", src);
				image_row_cloned.find("span.name_image").html(function (index, oldhtml) {
					return oldhtml.replace(/__PVALUE__/ig, name_image);
				});
				image_row_cloned.find("a.erase_image").attr("href", function (i, val) {
					return val + id_image;
				}).click(function (event) {
					addEventDeleteImage(event);
				});
				image_row_cloned.insertAfter($(".thumbs li:last")).show();
				image_row_cloned.animate({
					opacity: 0.67,
					color: "#000",
					border: "1px solid #fff"
				}, 500).effect("pulsate", {
					times: 10
				}, 1000);
			}
		} else {
			//nothing
		}
	};
	

$.fn.deprecatedBrowser = function () {
		var deprecated = false;
		if ($.browser.msie || $.browser.opera || ($.browser.mozilla && $.browser.version.slice(0, 3) == "1.9" && parseInt($.browser.version.slice(4, 5)) < 2)) {
			deprecated = true;
		}
		return deprecated;
	};
	$('#uploadFacility, #uploadImage, #uploadStructFacility').fileUploadUI({
		uploadTable: $('#result_facility_upload'),
		downloadTable: $('#result_facility_upload'),
		url: function (form) {
			var actionUrl = form.attr('action');
			var splittedUrl = ["", ""];
			splittedUrl = actionUrl.split(".");
			if ($().deprecatedBrowser()) {
				actionUrl = splittedUrl[0] + 'IF.' + splittedUrl[1];
			}
			return actionUrl;
		},
		onProgress: function (event, files, index, xhr, handler) {
			if (handler.progressbar) {
				handler.progressbar.progressbar('value', parseInt(event.loaded / event.total * 100, 10));
			}
		},
		buildUploadRow: function (files, index) {
			return $('<tr><td>' + files[index].name + '<\/td>' + '<td class="file_upload_progress"><div><\/div><\/td>' + '<td class="file_upload_cancel">' + '<button class="ui-state-default ui-corner-all" title="Cancel">' + '<span class="ui-icon ui-icon-cancel">Cancel<\/span>' + '<\/button><\/td><\/tr>');
		},
		buildDownloadRow: function (file) {
			var resultRow = "";
			if (typeof file !== "undefined") {
				resultRow = file.message.result;
			} else {
				resultRow = "";
			}
			return $('<tr><td>' + resultRow + '<\/td><\/tr>');
		},
		onComplete: function (event, files, index, xhr, handler) {
			var json = handler.response;
			if (typeof json !== "undefined" && typeof json.message !== "undefined" && json.message.result == "success") {
				var action = this.uploadForm.attr("action");
				try {
					$().addImageObject(json, action);
				} catch (e) {
					//var log = e;
				}
				$().notify($.i18n("congratulation"), json.message.description);
			} else if (typeof json !== "undefined" && typeof json.message !== "undefined" && json.message.result == "error") {
				$().notify($.i18n("warning"), json.description);
			} else {
				$().notify($.i18n("warning"), $.i18n("seriousErrorDescr"));
			}
		},
		onAbort: function (event, files, index, xhr, handler) {
			$().notify($.i18n("warning"), $.i18n("uploadStopped"));
			handler.removeNode(handler.uploadRow);
		},
		onError: function (event, files, index, xhr, handler) {
			if (handler.originalEvent) {
				// handle JSON parsing errors 
			} else {
				// handle XHR upload errors ... 
			}
		},
		parseResponse: function (xhr, handler) {
			//var prova = xhr.contents();
			if (typeof xhr.responseText !== 'undefined') {
				return $.parseJSON(xhr.responseText);
			} else {
				// Instead of an XHR object, an iframe is used for legacy browsers:
				return $.parseJSON(xhr.contents().text());
			}
		},
		beforeSend: function (event, files, index, xhr, handler, callBack) {
			var facility_name = handler.uploadForm.parents(".beauty").find('input[name="facility_name"]').val();
			var type_img = files.type;
			if (facility_name.length > 2) {
				handler.uploadForm.find('input:hidden[name="name"]').val(facility_name);
				callBack();
			} else {
				handler.uploadForm.parents(".beauty").find('input[name="facility_name"]').addClass("error").after('<label for="name_facility"  class="error">This field is required.</label>');
				var readyState = xhr.readyState;
				xhr.abort();
				// If readyState is below 2, abort() has no effect:
				if (isNaN(readyState) || readyState < 2) {
					handler.onAbort(event, files, index, xhr, handler);
				}
				$().notify($.i18n("warning"), $.i18n("facilityNameRequired"));
			}
		},
		previewSelector: ".image_preview",
		imageTypes: '/^image\/(gif|jpeg|png)$/'
	});
	
	
	$("a.erase_image").click(function (event) {
		addEventDeleteImage(event)
	});
	
	

	//---  DETAILS SECTION CODE   
	var addEventDeleteImage = function (event) {
			event.preventDefault();
			var $this = $(event.currentTarget);
			//--  $(this).closest("." + "guest" + "_row").remove();
			var urlAction = $this.attr("href");
			if (confirm($.i18n("alertDelete"))) {
				$.ajax({
					type: 'POST',
					url: urlAction,
					success: function (data_action) {
						var title_notification = null;
						if (data_action.result == "success") {
							$().notify($.i18n("congratulation"), data_action.description);
							$this.parents("li").remove();
						} else if (data_action.result == "error") {
							$().notify($.i18n("warning"), data_action.description);
						} else {
							$(".validationErrors").html(data_action);
						}
					},
					error: function () {
						$().notify($.i18n("seriousError"), $.i18n("seriousErrorDescription"));
					}
				});
			}
		};
		
		
    	}
    	
    },	{
    	init: function(){
    		var self = this;
    		
    	     /**
             * @attribute $facilityDom
             * Jquery Object Dom of facility in editing (private variable)
             */
            this.$facilityDom = null;
	        /**
	        *
	        * Local variable to store dialog buttons translated for facility editing and related execution code.
	        */
		var dialogButtons = {};
		var params = $( "#dialog-facility" ).find("form").serialize();
			dialogButtons[$.i18n("save")] = function() { 
			
			Models.Facility.update({},params, self.callback('updateFacilitySuccess'), self.callback('updateFacilityError')); };
			
			dialogButtons[$.i18n("delete")] = function() { 
                if (confirm($.i18n("alertCancel"))) {
                	Models.Facility.destroy({},params, self.callback('destroyFacilitySuccess'), self.callback('destroyFacilityError')); 
                    $(this).dialog("close");
                };
               
                    
                };
			
		
		
	        /**
	        *
	        * Manage facility editing with image click event handler.
	        */
		$('.facility').find("img").click( function() {
			self.$facilityDom = $(this).parent();
			$( "#dialog-facility:ui-dialog" ).dialog( "destroy" );
			var idFacility = $(this).siblings("input:hidden").val();
			var nameFacility = $(this).siblings("label").text();
			var fileNameFacility = $(this).attr("src");
			var added = new EJS({url: 'js/views/facility/show.ejs'}).render({facility: {id:idFacility, name: nameFacility, fileName: fileNameFacility}});
			$( "#dialog-facility" ).html(added).dialog({
				minHeight: 140,
				minWidth: 400, 
				modal: true,
				buttons: dialogButtons

			});

		});
    	
    },
    /**
     * update a facility.
     * @param {String} .
     */
	updateFacilitySuccess: function(data){
		var self = this;
		$().notify($.i18n("warning"), $.i18n("updateFacilitySuccess"));
		
	},
	/**
     * manage errors during update facility.
     */
	updateFacilityError: function(){
		
		$().notify($.i18n("warning"), $.i18n("updateFacilityError"));
	},
    /**
     * destroy a facility.
     * @param {String}.
     */
	destroyFacilitySuccess: function(data){
		var self = this;
		this.$facilityDom.remove();
		$().notify($.i18n("warning"), $.i18n("destroyFacilitySuccess"));
		
	},
	/**
     * manage errors during destroy facility.
     */
	destroyFacilityError: function(){
		
		$().notify($.i18n("warning"), $.i18n("destroyFacilityError"));
	}
    
    
    
    });
    
    new Controllers.Upload();
});