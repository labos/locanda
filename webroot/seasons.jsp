<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />
      <div id="main">
        <!-- begin: #col1 - first float column -->
        <div id="col1" role="complementary">
          <div id="col1_content" class="clearfix">
          </div>
        </div><!-- end: #col1 -->
        <!-- begin: #col3 static column -->
        <div id="col3" role="main">
          <div id="col3_content" class="clearfix">
          <div class="header_section">
          <span class="name_section">Settings</span>
          </div>
          <div>
		 <form method="post" action="saveUpdateSeason.action" class="yform" role="application">
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
<input type="text"name="season.name" value="Your first season" style="display: inline;" readonly="readonly"/>&nbsp; (<a href="#" class="rename_season" title="rename" >Rename</a>)              </div>
              </div>
              <div class="subcolumns">
             <div class="c20l">
               <div class="subcl type-text">
                <span>From:</span>
                <input type="text" class="datepicker" name="periods[0].startDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-text">
              <span>To:</span>
              <input type="text" class="datepicker" name="periods[0].endDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c10l">
              <label>&nbsp;</label>
              
              </div>
              </div>
              </div>
              <a NAME="top_anchor"></a> 
              </fieldset>
            <div class="type-button">
            <button class="btn_season">ADD NEW SEASON</button>
            <!--  <button class="btn_save_all">SAVE ALL</button> -->
            </div>
          </form>        
		</div>        
          </div>
 <!-- Seasons iteration -->
 
 <s:iterator value="seasons" var="eachSeason" >
 <form method="post" action="saveUpdateSeason.action" class="yform" role="application">
 		 <input type="hidden" name="redirect_form" value="findAllSeasons.action?sect=settings" />
  		 <input type="hidden" name="season.id" value="<s:property value="#eachSeason.id"/>" />
 
  	<fieldset>	
  	          <div class="subcolumns">
              <a href="#top_anchor" class="add_period" title="add period">Add New Period</a>
              </div>
               <div class="subcolumns">
               <div class="c33l">
               <div class="subcl type-text">
                <span>Name:</span>
                <a name="<s:property value="#eachSeason.name"/>"></a>
<input type="text"name="season.name" value="<s:property value="#eachSeason.name"/>" style="display: inline;" readonly="readonly"/></span>&nbsp; (<a href="#<s:property value="#eachSeason.name"/>" class="rename_season" title="rename" >Rename</a>)              </div>
              </div>
<s:iterator value="#eachSeason.periods" var="eachPeriod" status="periodStatus">

<div class="subcolumns">
             <div class="c20l">
               <div class="subcl type-text">
                <span>From:</span>
                <input type="text" class="datepicker" name="periods[<s:property value="#periodStatus.index"/>].startDate" value="<s:date name="#eachPeriod.startDate" format="MM/dd/yyyy" />" style="display: inline;"/>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-text">
              <span>To:</span>
              <input type="text" class="datepicker" name="periods[<s:property value="#periodStatus.index"/>].endDate" value="<s:date name="#eachPeriod.endDate" format="MM/dd/yyyy" />" style="display: inline;"/>
              </div>
              </div>
              <div class="c10l">
              <label>&nbsp;</label>
              <a href="#" class="erase_period" title="erase">Delete Period</a>
              </div>
              </div>

</s:iterator>
              </div>
            <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_season">UPDATE SEASON</button>
            </div>
               </fieldset>
 </form>
 </s:iterator>

 
          
<!--  Hidden new season  -->
             <div class="subcolumns" id="to_add_period" style="display: none;">
             <div class="c20l">
               <div class="subcl type-text">
                <span>From:</span>
                <input type="text" class="datepicker" name="periods[1].startDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-text">
              <span>To:</span>
              <input type="text" class="datepicker" name="periods[1].endDate" value="" style="display: inline;"/>
              </div>
              </div>
              <div class="c10l">
              <label>&nbsp;</label>
              <a href="#" class="erase_period" title="erase">Delete Period</a>
              </div>
              </div>
<jsp:include page="jsp/layout/footer.jsp" />   