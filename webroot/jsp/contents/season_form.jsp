<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		 <form method="post" action="saveUpdateSeason.action" class="yform json" role="application">
		 <input type="hidden" name="redirect_form" value="findAllSeasons.action?sect=settings" />
            <fieldset>
              <legend>Seasons</legend>
              <div class="subcolumns">
              <a href="#top_anchor" class="add_period" title="add period">Add New Period</a>
              </div>
              <div class="subcolumns">
               <div class="c33l">
               <div class="subcl type-text">
                <span>Name:</span>
<input type="text"name="season.name" class="required" value="Your first season" style="display: inline;" readonly="readonly"/>&nbsp; (<a href="#" class="rename_season" title="rename" >Rename</a>)              </div>
              </div>
              </div>
              <div class="subcolumns period">
             <div class="c20l">
               <div class="subcl type-text">
                <span>From:</span>
                <input type="text" class="datepicker required date" name="periods[0].startDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-text">
              <span>To:</span>
              <input type="text" class="datepicker required date" name="periods[0].endDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c10l">
              <label>&nbsp;</label>
              
              </div>
              </div>
                  <div class="type-button">
                              <input type="text" name="new_name_season" id="chng_season_name" value=""/>
                  	<button class="btn_save">SAVE</button>
                    <button class="btn_reset btn_cancel_form">CANCEL</button>
                  </div>	
              <a name="top_anchor"></a> 
              </fieldset>
          </form>        
          
          
          <!--  Hidden new season  -->
             <div class="subcolumns period" id="to_add_period" style="display: none;">
             <div class="c20l">
               <div class="subcl type-text">
                <span>From:</span>
                <input type="text" class="adddatepicker required date" name="periods[__PVALUE__].startDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-text">
              <span>To:</span>
              <input type="text" class="adddatepicker required date" name="periods[__PVALUE__].endDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c10l">
              <label>&nbsp;</label>
              <a href="#" class="erase_period" title="erase">Delete Period</a>
              </div>
              </div>
<!--  End Hidden new season  -->

