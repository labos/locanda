<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
<!-- Start of first page -->
<div data-role="page" id="foo4">

	<div data-role="header">
		<h1>Confirm Details</h1>
	</div><!-- /header -->

	<div data-role="content">
			<p><strong><s:property value="dateArrival"/></strong>, <s:property value="numNight"/> nights, <s:property value="numGuests"/> peoples</p>	
			<form action="goOnlineBookingFinal.action">	
<div id="firstNameDiv" data-role="fieldcontain">
  <label for="firstNo">First Name*</label>
  <input id="firstNo" name="guest.firstName" type="text" />
</div>
<div id="lastNameDiv" data-role="fieldcontain">
  <label for="lastNo">Last Name*</label>
  <input id="lastNo" name="guest.lastName" type="text" />
</div>
<div id="phoneNameDiv" data-role="fieldcontain">
  <label for="phoneNo">Phone*</label>
  <input id="phoneNo" name="guest.Phone" type="text" />
</div>
  	<div id="stateDiv" data-role="fieldcontain">
  		<label id="stateLabel" for="state">State*</label>		
      <select id="state" name="guest.state" tabindex="2">
                  <option value="ZZ">Please select a state</option>
    							<option value="AL">Alabama</option>
    							<option value="AK">Alaska</option>
    							<option value="AZ">Arizona</option>
    							<option value="AR">Arkansas</option>
    							<option value="CA">California</option>
    							<option value="CO">Colorado</option>
    							<option value="CT">Connecticut</option>
    							<option value="DE">Delaware</option>
    							<option value="FL">Florida</option>
    							<option value="GA">Georgia</option>
    							<option value="HI">Hawaii</option>
    							<option value="ID">Idaho</option>
    							<option value="IL">Illinois</option>
    							<option value="IN">Indiana</option>
    							<option value="IA">Iowa</option>
    							<option value="KS">Kansas</option>
    							<option value="KY">Kentucky</option>
    							<option value="LA">Louisiana</option>
    							<option value="ME">Maine</option>
    							<option value="MD">Maryland</option>
    							<option value="MA">Massachusetts</option>
    							<option value="MI">Michigan</option>
    							<option value="MN">Minnesota</option>
    							<option value="MS">Mississippi</option>
    							<option value="MO">Missouri</option>
    							<option value="MT">Montana</option>
    							<option value="NE">Nebraska</option>
    							<option value="NV">Nevada</option>
    							<option value="NH">New Hampshire</option>
    							<option value="NJ">New Jersey</option>
    							<option value="NM">New Mexico</option>
    							<option value="NY">New York</option>
    							<option value="NC">North Carolina</option>
    							<option value="ND">North Dakota</option>
    							<option value="OH">Ohio</option>
    							<option value="OK">Oklahoma</option>
    							<option value="OR">Oregon</option>
    							<option value="PA">Pennsylvania</option>
    							<option value="RI">Rhode Island</option>
    							<option value="SC">South Carolina</option>
    							<option value="SD">South Dakota</option>
    							<option value="TN">Tennessee</option>
    							<option value="TX">Texas</option>
    							<option value="UT">Utah</option>
    							<option value="VT">Vermont</option>
    							<option value="VA">Virginia</option>
    							<option value="WA">Washington</option>
    							<option value="WV">West Virginia</option>
    							<option value="WI">Wisconsin</option>
    							<option value="WY">Wyoming</option>
    		</select>
    </div>
	

<div data-role="fieldcontain">
	<button type="submit" data-theme="b">SUBMIT REQUEST</button>
</div>

</form>

         
	</div><!-- /content -->

	<div data-role="footer">
		<h4>Locanda Mobile Widget</h4>
	</div><!-- /header -->
</div><!-- /page -->