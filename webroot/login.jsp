<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type='text/css' href='css/south-street/jquery-ui-1.8.9.custom.css' rel='stylesheet' />
  <link type="text/css" href="css/layout_sliding_door.css" rel="stylesheet"  />  
 
  <script type='text/javascript' src='js/lib/jquery-1.4.4.min.js'></script>
  <!--
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>
  -->
  <script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
  <script type='text/javascript' src='js/jquery.validate.min.js'></script>
 <script type='text/javascript' src='js/main.js'></script>
  <title>LOCANDA - Open Source Booking Tool</title><!-- (en) Add your meta data here -->
  <!--[if lte IE 7]>
	<link href="css/patches/patch_sliding_door.css" rel="stylesheet" type="text/css" />
  <![endif]-->
</head>
<body>
  <!-- skip link navigation -->
  <ul id="skiplinks">
    <li><a class="skip" href="#nav">Skip to navigation (Press Enter).</a></li>
    <li><a class="skip" href="#col3">Skip to main content (Press Enter).</a></li>
  </ul>
  <div class="page_margins">
    <div class="page">
      <div id="header" role="banner">
        <div id="topnav" role="contentinfo">
          <span><a href="#">Not Logged In</a> | <a href="login.jsp">Login/Signup</a></span>
        </div>
        <h1>
        <span>&nbsp;</span><em>&nbsp;</em></h1><span>&nbsp;</span>
      </div><!-- begin: main navigation #nav -->
      <div id="home">
      	<h1>Log In</h1>
      	<p>Not yet registered? <a href="signup.jsp">Signup</a></span>.</p>
      	
      	<s:url action="login" var="url"></s:url>
      	
      	<form method="post" action="<s:property value="url"/>" class="yform" role="application">
            <fieldset>
              <legend>Log In data</legend>
              <div class="type-text">
                <label for="email">E-Mail <sup title="This field is mandatory.">*</sup></label>
                <input type="text" class="required email" name="email" id="email" size="20"  aria-required="true"/>
              </div>
              <div class="type-text">
                <label for="password">Password <sup title="This field is mandatory.">*</sup></label>
                <input type="password" class="required" name="password" id="password" size="20"  aria-required="true"/>
              </div>
            </fieldset>
            <div class="type-button">
          	  <button class="btn_submit" type="submit" role="button" aria-disabled="false">Log In</button>
            </div>
          </form>
      </div><!-- end: #home -->
          <div id="ie_clearing">
            &nbsp;
          </div><!-- End: IE Column Clearing -->
      <%@ include file="footer.jsp" %>
      </div><!-- end: #main -->   
    </div><!-- end: #page_margins -->
  </div><!-- full skiplink functionality in webkit browsers -->
  <script src="yaml/core/js/yaml-focusfix.js" type="text/javascript">
</script>
</body>
</html>