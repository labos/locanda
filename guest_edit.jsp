<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel='stylesheet' type='text/css' href='css/reset.css' /><!--
        <link rel='stylesheet' type='text/css' href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/start/jquery-ui.css' />
        -->
  <link rel='stylesheet' type='text/css' href=
  'css/south-street/jquery-ui-1.8.9.custom.css' />
  <script type='text/javascript' src='js/lib/jquery-1.4.4.min.js'>
</script><!--
    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>
    -->
  <script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'>
</script>
  <script type='text/javascript' src='js/ftod.js'>
</script>
  <script type='text/javascript' src='js/jquery.validate.min.js'>
</script>
<script type='text/javascript' src='js/main.js'></script>
  <title>LOCANDA - Open Source Booking Tool</title><!-- (en) Add your meta data here -->
  <!-- (de) Fuegen Sie hier ihre Meta-Daten ein -->
  <link href="css/layout_sliding_door.css" rel="stylesheet" type="text/css" />
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
          <span><a href="#">Not Logged In</a> | <a href="#">Login/Signup</a></span>
        </div>
        <h1>
        <span>&nbsp;</span><em>&nbsp;</em></h1><span>Polaris</span>
      </div><!-- begin: main navigation #nav -->
      <div id="nav" role="navigation">
        <div class="hlist">
          <ul>
            <li><a href="index.jsp">Planner</a></li>
            <li><a href="rooms.jsp">Accomodation</a>
            <ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
    			<li class="ui-menu-item"><a href="accomodation.jsp">ACCOMODATION</a></li>
    			<li class="ui-menu-item"><a href="extras.jsp">EXTRAS</a></li>
  			</ul>
            </li>
            <li><a href="guests.jsp">Guests</a></li>
            <li><a href="#">Reports</a></li>
            <li class="active"><strong>Settings</strong>
                <ul class="sub_menu ui-menu ui-widget ui-widget-content ui-corner-all">
    			<li class="ui-menu-item"><a href="online.jsp">ONLINE BOOKINGS</a></li>
    			<li class="ui-menu-item"><a href="seasons.jsp">SEASONS</a></li>
    			<li class="ui-menu-item"><a href="emails.jsp">EMAILS</a></li>
    			<li class="ui-menu-item"><a href="details.jsp">YOUR DETAILS</a></li>
    			<li class="ui-menu-item"><a href="discount.jsp">DISCOUNT</a></li>
  			</ul>
            </li>         
            <li><a href="#">Help</a></li>
          </ul>
        </div>
      </div><!-- end: main navigation -->="online.html">ONLINE BOOKINGS</a></li>
    			<li class="ui-menu-item"><a href="seasons.html">SEASONS</a></li>
    			<li class="ui-menu-item"><a href="emails.html">EMAILS</a></li>
    			<li class="ui-menu-item"><a href="details.html">YOUR DETAILS</a></li>
    			<li class="ui-menu-item"><a href="discount.html">DISCOUNT</a></li>
  			</ul>
            </li>         
            <li><a href="#">Help</a></li>
          </ul>
        </div>
      </div><!-- end: main navigation -->
      <!-- begin: main content area #main -->
      <div id="main">
        <!-- begin: #col1 - first float column -->
        <div id="col1" role="complementary">
          <div id="col1_content" class="clearfix">
          </div>
        </div><!-- end: #col1 -->
        <!-- begin: #col3 static column -->
        <div id="col3" role="main">
          <div id="col3_content" class="clearfix">
          <div class="header_section yform">
          <span class="name_section">Guest Details</span>
          <div class="right type-text">
          <input type="text" name="guest_search" class="txt_guest_search" /><button class="btn_g_search">SEARCH</button>
            <div class="search_links"><span>Or browse:&nbsp;</span><a href="#">staying this week</a><span>,&nbsp;</span><a href="#">staying next week</a>,&nbsp;<a href="#">staying this month</a>,&nbsp;<a href="#">staying last month</a>,&nbsp;<a href="#">all</a></div>
            </div>
          </div>
          <div>
 <form method="post" action="" class="yform" role="application">           
  <fieldset>
              <legend>Informations</legend>
              <div class="c50l">
           <div class="type-text">
                <label for="firstname">First name <sup title="This field is mandatory.">*</sup></label>
                <input class="required" type="text" name="firstname" id="firstname" size="20" value="giovanni"/>
              </div>
              <div class="type-text" role="alert" aria-live="assertive">
                <label for="lastname">Last name <sup title="This field is mandatory.">*</sup></label>
                <input class="required" type="text" name="lastname" id="lastname" size="20"  aria-required="true" value="rossi"/>
              </div>

                  <div class="type-text"><label for="phone">Phone:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "phone" id="phone" class="required" value="34057546746"/></div>
                  <div class="type-text"><label for="phone">Email:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "email" id="email" class="required" value="rossi@tiscali.it"/></div> 
                  <div class="type-text"><label for="address">Address:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "address" id="address" class="required" value="via poerio, 3" /></div>
                  <div class="type-text"><label for="country">Country:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "country" id="country" class="required" value="italy"/></div>
                  <div class="type-text"><label for="postcode">PostCode:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "postcode" id="postcode" class="required" value="07020"/></div>
                   <div class="type-text"><label for="body">Note:</label> 
                  <textarea name="body" id="body">
</textarea></div>
            </div>
              </fieldset>
                <fieldset>
              <legend>Bookings</legend>
              <div class="subcolumns">
              <div class="c50l book_guest">
              <div class="c20l"><span>28 Feb 2011</span></div><div class="c20l"><span>5 nights</span></div><div class="c20l"><span>&euro; 150</span></div>
              <div class="c20l"><a href="book.html"><span class="link">Details</span></a></div>
              </div>
              </div>
              <div class="subcolumns">
              &nbsp;
              </div>
              <div class="subcolumns book_guest">
              <div class="c20l"><a href="book.html"><span class="link">New Booking</span></a></div><div class="c20l"><span></span></div><div class="c20l"><span></span></div>
              <div class="c20l"></div>
              </div>
              </fieldset>
             <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_edit_guest">SAVE</button>
            <a href="#" title="delete guest">Delete Guest</a>
            </div>
           </form>        
		</div>        
          </div>
          <div id="ie_clearing">
            &nbsp;
          </div><!-- End: IE Column Clearing -->
        </div><!-- end: #col3 -->
      </div><!-- end: #main -->
	<!-- begin: #footer -->
   <div id="footer" role="contentinfo">
        <img src="images/labos-small.png" alt="Laboratorio Open Source" class="left" />
        <img src="images/sardegna_ricerche.png" alt="Sardegna Ricerche" class="left" />
        <img src="images/regione.gif" alt="Regione Autonoma della Sardegna" class="right" />
        <img src="images/governo.gif" alt="Repubblica Italiana" class="right" />
        <img src="images/unione_europea.gif" alt="Unione Europea" class="right" />
        <span class="center">Locanda<br />
        Open Source Booking Software<br /><br /></span>
      </div><!-- end: #footer -->
    </div>
  </div><!-- full skiplink functionality in webkit browsers -->
  <script src="yaml/core/js/yaml-focusfix.js" type="text/javascript">
</script>
</body>
</html>