<<?xml version="1.0" encoding="UTF-8" ?>
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
                <input class="required" type="text" name="firstname" id="firstname" size="20" />
              </div>
              <div class="type-text" role="alert" aria-live="assertive">
                <label for="lastname">Last name <sup title="This field is mandatory.">*</sup></label>
                <input class="required" type="text" name="lastname" id="lastname" size="20"  aria-required="true"/>
              </div>

                  <div class="type-text"><label for="phone">Phone:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "phone" id="phone" class="required" /></div>
                  <div class="type-text"><label for="phone">Email:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "email" id="email" class="required" value="rossi@tiscali.it"/></div> 
                  <div class="type-text"><label for="address">Address:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "address" id="address" class="required" /></div>

                  <div class="type-text"><label for="country">Country:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "country" id="country" class="required"/></div>

                  <div class="type-text"><label for="postcode">PostCode:<sup title="This field is mandatory.">*</sup></label> <input type="text" name=
                  "postcode" id="postcode" class="required"/></div>
                   <div class="type-text"><label for="body">Note:</label> 
                  <textarea name="body" id="body">
</textarea></div>
            <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_save_guest">Add New Guest</button>
            </div>
            </div>
              </fieldset>
           </form>        
		</div>        
          </div>
<jsp:include page="jsp/layout/footer.jsp" />     