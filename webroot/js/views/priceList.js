/*******************************************************************************
 *
 *  Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
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
$(function () {
/*
 * @class EditPriceListView 
 * @parent Backbone.View
 * @constructor
 * Class to show a price list.
 * @tag views
 * @author LabOpenSource
 */
window.EditPriceListView = Backbone.View.extend({
	
	  initialize: function() {
		  
		  this.render();	    
		  
	  },

	  render: function() {
		  
		     $("#priceList_buttons").hide();
	            $("#priceList_buttons").html('<button class="btn_save">' + $.i18n("save") + '</button>' + '<button class="btn_reset">' + $.i18n("close") + '</button>');
	             $(".yform.json").submit(function (event) {
	                 $(this).bind('submitForm', function(e,data){
	                 	  if(data.type == "success"){
	                 	  //switch to read-only mode
		                        $("#priceList_form").find("input").addClass("noBorder");
		                        $("#priceList_form").find("input").attr('readonly', 'readonly');
		                        $(".copy").hide();
		                        $("#priceList_buttons").hide();
	                 	  }});
	                 $(this).submitForm();
	                 return false;
	             });
	        	
	            $(".btn_save").button({
	                icons: {
	                    primary: "ui-icon-check"
	                }
	            });
	            $(".btn_reset").button({
	                icons: {
	                    primary: "ui-icon-trash"
	                }
	            }).click(function (event) {
	                event.preventDefault();
	                var validator = $(this).parents(".yform.json").validate();
	                validator.resetForm();
	            });
	            $("#priceList_edit").button({
	                icons: {
	                    primary: "ui-icon-pencil"
	                }
	            });
	            
	            $(".copy").live('click', function(event){
	            	
	            	event.preventDefault();
	            	var priceDefault = $(this).parents("td").children("input:text").val();
	            	var pricesWeek = $(this).parents("td").siblings("td");
	            	pricesWeek.each( function( index, element ){
	            		
	            		var value = $(element).children("input:text").val( priceDefault );
	            		
	            	});
	            });
	        	

	            
	            $(".room_tree, .extra_tree").bind("loaded.jstree", function (event, data) {
					//$(".room_tree, .extra_tree").jstree("open_all");
	                $(".jstree-leaf").click(function (event) {
	                	var self = this;
	                    event.preventDefault();
	                    var url_table = $("a", this).attr("href");
	                    $.ajax({
	                        url: url_table,
	                        context: document.body,
	                        dataType: "html",
	                        success: function (data) {
	                        	var caption = "";
	                        	// get path of selected nodes
	                        	var path_nodes = $(self).parents(".jstree-open").children("a").add( $(self).children("a"));
	                        	// set a caption string for selected nodes
	                        	$(path_nodes).each( function ( index, element ){
	                        		
	                        		caption = caption + $( element ).text() + '/';
	                        	});
	                        	// set caption
	                        	$("#path_nodes").text(caption);
	                        	// reset change selected node
	                        	$(".jstree-leaf").find("a").css({ 'color':'black'})
	                        	// change selected node
	                        	$(self).find("a").css({ 'color':'red', 'font-weight': '600'});
		                        $("#priceList_buttons").hide();
	                            $(".priceList_table > tbody").html(data);
	                        },
	                        error: function (request, state, errors) {
	                            $.jGrowl($.i18n("warning"), { theme: "notify-error",sticky: true});
	                        }
	                    });

	                    $("#priceList_edit").toggle(function () {
	                        $("#priceList_form").find("input").removeClass("noBorder");
	                        $("#priceList_form").find("input").removeAttr('readonly', 'readonly');
	                        $(".copy").show();
	                        $("#priceList_buttons").show();
	                    }, function () {
	                        $("#priceList_form").find("input").addClass("noBorder");
	                        $("#priceList_form").find("input").attr('readonly', 'readonly');
	                        $(".copy").hide();
	                        $("#priceList_buttons").hide();
	                    });
	                });
	            });
	            $(".room_tree").jstree({
	                "json_data": {
	                    "ajax": {
	                        "url": "findAllRoomPriceLists.action",
	                    	"success" : function (data) {
	                    		if($.isEmptyObject(data)){
	                                $.jGrowl($.i18n("alertNoSeason"), { theme: "notify-error",sticky: true   });
	                    		}
	                    		return data.d; }
	                    }
	                },
	                "themes": {
	                    "theme": "default",
	                    "dots": true,
	                    "icons": true
	                },
	                "plugins": ["themes", "json_data", "ui"]
	            }).bind("select_node.jstree", function (e, data) {
		            data.inst.toggle_node(data.rslt.obj); return false; });
	            $(".extra_tree").jstree({
	                "core": {
	                    "initially_open": ["root"]
	                },
	                "json_data": {
	                    "ajax": {
	                    	"url": "findAllExtraPriceLists.action",
	                    	"success" : function (data) {
	                    		if($.isEmptyObject(data)){
	                                $.jGrowl($.i18n("alertNoSeason"), { theme: "notify-error",sticky: true });
	                    		}
	                    		return data.d; }
	                    }
	                },
	                "themes": {
	                    "theme": "default",
	                    "dots": true,
	                    "icons": true
	                },
	                "plugins": ["themes", "json_data", "ui"]
	            }).bind("select_node.jstree", function (e, data) {
	            data.inst.toggle_node(data.rslt.obj); return false; });
	   
	  }

	
});

new EditPriceListView();
});