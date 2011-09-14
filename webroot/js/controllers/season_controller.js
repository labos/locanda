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
$(function() {
	
	
	 $.Class.extend('Controllers.Season',
			 
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
					    
					    /* Change year management */
					    $("#season_year").change(function(){
					    	
					    	var selectedYear  = $(this).find(":selected").val();
					    	
					    
					    	$(".datepicker").each(function(index) {
					    		 var selectedData = $(this).datepicker("getDate");
					    		 var dateDate = new Date(selectedData);
					    		 if ( selectedYear  && selectedYear != dateDate.getFullYear()){
					    			 dateDate.setFullYear(selectedYear);
					    			 $(this).datepicker("setDate",dateDate);
					    		 }
					    	    
					    	  });

					    	
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
							var added = new EJS({url: 'js/views/season/show.ejs'}).render({index: num_of_periods, labels:{to: $.i18n("to"),from: $.i18n("from"), erase: $.i18n("erase")}});
							var $added = $(added);
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
	 
	 new Controllers.Season(I18NSettings.lang, I18NSettings.datePattern);
});
