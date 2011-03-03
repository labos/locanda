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
		 <form method="post" action="" class="yform full" role="application">
            <fieldset>
              <legend>Seasons</legend>
              <div class="subcolumns">
              <span class="title_season"><input type="text"name="season_name" value="Your first season" readonly="readonly"/></span>&nbsp; (<a href="#" id="rename_season" title="rename" >Rename</a>)&nbsp;(<a href="#" title="delete" >delete season</a>)
              </div>
              <div class="subcolumns">
              <a href="#" id="add_period" title="add period">Add New Period</a></div>
              </fieldset>
            <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_season">ADD NEW SEASON</button>
            <button class="btn_save_all">SAVE ALL</button>
            </div>
          </form>        
		</div>        
          </div>
          
<!--  Hidden new season  -->
             <div class="subcolumns" id="to_add_period" style="display: none;">
             <div class="c10l">
               <div class="subcl type-select">
                <span>From</span>
                <select class="required" name="from" size="1" aria-required="true">
                  <option value="0" selected="selected" disabled="disabled">Please choose</option>
                  			<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
                </select>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-select">
              <span>&nbsp;</span>
                <select class="required" name="from_month"  size="1" aria-required="true">
                  <option value="0" selected="selected" disabled="disabled">Please choose</option>
					<option selected="selected" value="1">January</option>
					<option value="2">February</option>
					<option value="3">March</option>
					<option value="4">April</option>
					<option value="5">May</option>
					<option value="6">June</option>
					<option value="7">July</option>
					<option value="8">August</option>
					<option value="9">September</option>
					<option value="10">October</option>
					<option value="11">November</option>
					<option value="12">December</option>
				 </select>
              </div>
              </div>
              <div class="c10l">
               <div class="subcl type-select">
               <span>&nbsp;</span>
                <select class="required" name="from_year" size="1" aria-required="true">
					<option value="2005">2005</option>
					<option value="2006">2006</option>
					<option value="2007">2007</option>
					<option value="2008">2008</option>
					<option value="2009">2009</option>
					<option value="2010">2010</option>
					<option selected="selected" value="2011">2011</option>
					<option value="2012">2012</option>
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
                </select>
              </div>
              </div>
              <div class="c10l">
              <div class="subcl type-select">
                <span>To</span>
                <select class="required" name="to"  size="1" aria-required="true">
                  <option value="0" selected="selected" disabled="disabled">Please choose</option>
                         	<option value="2">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
                </select>
              </div>
              </div>
              <div class="c20l">
              <div class="subcl type-select">
              <span>&nbsp;</span>
                <select class="required" name="to_month" size="1" aria-required="true">
					<option selected="selected" value="1">January</option>
					<option value="2">February</option>
					<option value="3">March</option>
					<option value="4">April</option>
					<option value="5">May</option>
					<option value="6">June</option>
					<option value="7">July</option>
					<option value="8">August</option>
					<option value="9">September</option>
					<option value="10">October</option>
					<option value="11">November</option>
					<option value="12">December</option>
                </select>
              </div>
              </div>
              <div class="c10l">
              <div class="subcl type-select">
              <span>&nbsp;</span>
                <select class="required" name="to_year" size="1" aria-required="true">
                  <option value="0" selected="selected" disabled="disabled">Please choose</option>
					<option value="2005">2005</option>
					<option value="2006">2006</option>
					<option value="2007">2007</option>
					<option value="2008">2008</option>
					<option value="2009">2009</option>
					<option value="2010">2010</option>
					<option selected="selected" value="2011">2011</option>
					<option value="2012">2012</option>
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
                </select>
              </div>
              </div>
              <div class="c10l">
              <label>&nbsp;</label>
              <a href="#" class="erase_period" title="erase">Delete Period</a>
              </div>
              </div>
<jsp:include page="jsp/layout/footer.jsp" />   