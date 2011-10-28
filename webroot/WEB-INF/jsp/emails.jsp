<%--
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
--%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="layout/header_menu.jsp" />
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
          <span class="name_section">Settings</span>     
          </div>
          <div>
 <form method="post" action="" class="yform" role="application">           
  <fieldset>
              <legend>Automated Emails</legend>
              <div class="c50l">
           <div class="type-text">
                <label for="firstname">From Address:</label>
                <input class="required" type="text" name="firstname" id="firstname" size="20" value=""/>
              </div>
              <div class="type-text" role="alert" aria-live="assertive">
                <label for="lastname">Send copies of emails to:</label>
                <input class="required" type="text" name="lastname" id="lastname" size="20"  aria-required="true" value=""/>
              <span>separate multiple emails with commas</span>
              </div>
              <div class="type_rooms">
          	<a href="#" id="show_usable">Show Usable variables</a>
          	 </div>
             <div class="type_rooms">
          		<ul class="list_usable">
          		<li>[balance_due] : Booking balance due amount</li>
          		<li>[due_date] : Booking payment due date</li>
          		<li>[payment_amount] : Booking payment tota</li>
          		<li>[price_details] : Booking detailed prices (room price, extras prices, adjustment, payment)</li>
          		<li>[room_name] : Booking room name</li>
          		<li>[duration] : Duration</li>
          		<li>[arrival_date] : Guest arrival (check in)</li>
          		<li>[guest_email] : Guest email address</li>
          		<li>[guest_name] : Guest fullname</li>
          		<li>[guest_phone] : Guest phone number</li>
          		<li>[extras] : List of extras added</li>
          		<li>[number_of_guests] : Number of guests</li>
          		<li>[hotel_email] : Your hotel email address</li>
          		<li>[hotel_name] : Your hotel name</li>
          		<li>[hotel_phone] : Your hotel phone number</li>
          		<li>[hotel_url] : Your hotel site url</li>
          		</ul>
			 </div>
              <div class="type_rooms">
              <input type="checkbox" id="email_guest_check" name="email_guest_check" />
              <label for="email_guest_check">Email guest to acknowledge an online booking enquiry (no online payment)</label>
              </div>
              <div class="type-text">
                <label for="subject">Subject:</label>
                <input class="required" type="text" name="subject_guest_check"  size="20" value="Your Booking Request at [hotel_name]"/>
              </div>
               <div class="type-text"><label for="template_ck">Template:</label> 
                  <textarea name="template_ck" class="large" id="template_ck">
				Dear [guest_name],

Thank you for your booking request. Here are the details you submitted:

Name:             [guest_name]
Email:            [guest_email]
Phone:            [guest_phone]
Room:             [room_name]
Extras:           [extras]
Number of guests: [number_of_guests]
Arrival:          [arrival_date]
Duration:         [duration]

We'll get in touch shortly to confirm your booking and arrange payment.

[hotel_name]
[hotel_url]</textarea></div>

              <div class="type_rooms">
              <input type="checkbox" id="email_confirm_book" name="email_confirm_book" />
              <label for="email_confirm_book">Email to confirm a booking</label>
              </div>
              <div class="type-text">
                <label for="subject">Subject:</label>
                <input class="required" type="text" name="subject_confirm_book"  size="20" value="Your Booking at [hotel_name]"/>
              </div>
               <div class="type-text"><label for="template_cnf">Template:</label> 
                  <textarea name="template_cnf" class="large" id="template_cnf">
                  Dear [guest_name],

Thank you for your booking at [hotel_name].

Here are the details of your booking:

Name:             [guest_name]
Email:            [guest_email]
Phone:            [guest_phone]
Room:             [room_name]
Extras:           [extras]
Number of guests: [number_of_guests]
Arrival:          [arrival_date]
Duration:         [duration]

If you need to change anything, please contact us on [hotel_email] or [hotel_phone]

Thank you, and we look forward to seeing you on [arrival_date]!

[hotel_name]
[hotel_url]
				</textarea></div>
				
				<div class="type_rooms">
              <input type="checkbox" id="email_thanks" name="email_thanks" />
              <label for="email_confirm_book">Email guest to thank them for their stay</label>
              </div>
             <div class="type-text">
                <label for="next_thanks">Send email</label>
                <input class="required" type="text" name="next_thanks" id="next_thanks" size="20" value="2"/>
                <span>days after departure date</span>
                <span>Send email days after departure date Locanda will email your guest on the date you specify above thanking them for their stay
                </span>
              </div>
              <div class="type-text">
                <label for="subject_thanks">Subject:</label>
                <input class="required" type="text" name="subject_thanks"  size="20" value="Thank you for staying with us"/>
              </div>
               <div class="type-text"><label for="template_thanks">Template:</label> 
                  <textarea name="template_thanks" class="large" id="template_thanks">
Dear [guest_name],

We hope you enjoyed your stay at [hotel_name] on [arrival_date]. We'd love to see you again, and please do drop us a line if you have any comments about your stay

[hotel_name]
[hotel_url]
				</textarea></div>
				
				
				
				
            </div>
              </fieldset>
             <div class="type-button">
            <input type="text" name="new_name_season" id="chng_season_name" value=""/>
            <button class="btn_edit_guest">SAVE</button>
            </div>
           </form>        
		</div>        
          </div>
<jsp:include page="layout/footer.jsp" />     