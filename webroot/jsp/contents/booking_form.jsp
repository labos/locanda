<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<style>
.type_rooms{
display: none;
}
</style>
<div class="validationErrors"></div>
              <form class="yform json" action="saveUpdateBooking.action">
                             <input type="hidden" name="redirect_form" value="home.action?sect=planner" />
                             <input type="hidden" name="booking.id" value="<s:property value="booking.id"/>"/>
                             <div class="subcolumns_oldgecko">
                             <fieldset>
    							<legend>Resume Booking:</legend>
                             <div class="c20l">
                             <div class="subcl type-select">
               				 <label for="sel_rooms_list">Room <sup title="This field is mandatory.">*</sup> </label>
                			 <select size="1" id="sel_rooms_list" name="booking.room.id">
                  			 <s:iterator value="rooms" var="eachRoom" >
                   			   <option 
                   			   <s:if test="#eachRoom.id == booking.room.id">selected="selected"</s:if> 
                   			   value="<s:property value="#eachRoom.id"/>"><s:property value="#eachRoom.name"/>
                   			   </option>
                    		</s:iterator>
                    		<s:if test="booking.room == null">
							  <option selected="selected" value="-1">Select One</option>
							</s:if>	
                    		</select>
            				</div>
            				</div>
            				<div class="c20l">
            				<div class="subcl type-text">
            				<label for="datepicker">Date In: <sup title="This field is mandatory.">*</sup> </label>
							<input type="text" name="booking.dateIn" class="datepicker required"  value="<s:date name="booking.dateIn" format="%{#session.datePattern}" />" style="display: inline;"/>
							</div>
							</div>
							<div class="c20l">
                             <span id="date_booking" ></span><span id="duration"></span>
                             <div class="subcl type-select">
                             <label for="duration">N° nights <sup title="This field is mandatory.">*</sup> </label>
                             <select name="numNights" class="confirm" id="booking_duration" >
                             <option selected="selected" value="<s:property value="numNights"/>"><s:property value="numNights"/></option>
                             <option value="1">1 </option>
                             <option value="2">2 </option>
                             <option value="3">3 </option>
                             <option value="4">4 </option>
                             <option value="5">5 </option>
                             <option value="6">6 </option>
                             <option value="7">7 </option>
                             <option value="8">8 </option>
                             <option value="9">9 </option>
                       		 <option value="10">10 </option>
                       		 <option value="11">11 </option>
                       		 <option value="12">12 </option>
                       		 <option value="13">13 </option>
                       		 <option value="14">14 </option>
                       		 <option value="15">15 </option>
                       		 <option value="16">16 </option>
                       		 <option value="17">17 </option>
                       		 <option value="18">18 </option>
                       		 <option value="19">19 </option>
                       		 <option value="20">20 </option>
                       		 <option value="21">21 </option>
                       		 <option value="22">22 </option>
                       		 <option value="23">23 </option>
                             </select>
                             </div>
                             </div>
                            <div class="c20l">
            				<div class="subcl type-text">
            				<label for="datepicker">Date Out: <sup title="This field is mandatory.">*</sup> </label>
							<input type="text" name="booking.dateOut" class="datepicker required"  value="<s:date name="booking.dateOut" format="%{#session.datePattern}" />" style="display: inline;"/>
							</div>
							</div>
							<div class="c20l">
                             
                             <div class="subcl type-select">
                              <s:if test="booking.status == \"provisional\"">
                              <input type="hidden" name="booking.status" value="provisional" />
                              <button class="btn_check_in">CHECK IN</button>
                              </s:if>
                              <s:if test="booking.status == \"checkin\"">
                              <input type="hidden" name="booking.status" value="checkin" />
                              <button class="btn_check_out">CHECK OUT</button>
                              </s:if>
                               <s:if test="booking.status == \"checkout\"">
                               <input type="hidden" name="booking.status" value="checkout" />
                              <button class="btn_checked">CHECKED</button>
                              </s:if>           
                             <!--
                             <label for="confirm">Confirmed?</label>
                             <select name="confirm" class="confirm" id="confirm">
                             <option value="1">Confirmed</option>
                             <option value="0">Provisional</option>
                             </select>
                             --></div>
                             </div>
                 </fieldset>         
              </div>
              <div class="subcolumns_oldgecko">
               <div class="c45l">
              <fieldset>
    <legend>Booker Details:</legend>
                <input type="hidden" name="booking.booker.id" value="<s:property value="booking.booker.id"/>"/>
	               <div class="type-text"><label for="lname">Last Name: <sup title="This field is mandatory.">*</sup> </label> 
	               <input type="text" name="booking.booker.lastName" id="lname" value="<s:property value="booking.booker.lastName"/>" class="required"/></div>

                  <div class="type-text"><label for="fname">First Name: <sup title="This field is mandatory.">*</sup> </label> 
                  <input type="text" name="booking.booker.firstName" id="fname" value="<s:property value="booking.booker.firstName"/>" class="required"/></div>
                  
                  <a name="top_accordion"></a>
               <div id="accordion">
               <h2><a href="#top_accordion">BOOKER DETAILS</a></h2>
               <div>
                  <div class="type-text"><label for="phone">Phone: <sup title="This field is mandatory.">*</sup> </label> 
                  <input type="text" name="booking.booker.phone" id="phone" value="<s:property value="booking.booker.phone"/>" class="required"/></div>

                  <div class="type-text"><label for="address">Address: <sup title="This field is mandatory.">*</sup> </label> 
                  <input type="text" name="booking.booker.address" id="address" value="<s:property value="booking.booker.address"/>" class="required" /></div>

                  <div class="type-select"><label for="country">Country: <sup title="This field is mandatory.">*</sup> </label> 
                  <select class="required" name="booking.booker.country" id="country" size="1" aria-required="true">
				<option selected="selected" value="<s:property value="booking.booker.country"/>"><s:property value="booking.booker.country"/></option>
				<option value="Afghanistan">Afghanistan</option>
				<option value="Albania">Albania</option>
				<option value="Algeria">Algeria</option>
				<option value="Andorra">Andorra</option>
				<option value="Angola Republica">Angola Republica</option>
				<option value="Anguilla">Anguilla</option>
				<option value="Antigua and Barbuda">Antigua and Barbuda</option>
				<option value="Argentina">Argentina</option>
				<option value="Armenia">Armenia</option>
				<option value="Aruba">Aruba</option>
				<option value="Australia">Australia</option>
				<option value="Austria">Austria</option>
				<option value="Azerbaijan">Azerbaijan</option>
				<option value="Bahamas">Bahamas</option>
				<option value="Bahrain">Bahrain</option>
				<option value="Bangladesh">Bangladesh</option>
				<option value="Barbados">Barbados</option>
				<option value="Belarus">Belarus</option>
				<option value="Belgium">Belgium</option>
				<option value="Belize">Belize</option>
				<option value="Benin Republique">Benin Republique</option>
				<option value="Bermuda">Bermuda</option>
				<option value="Bhutan">Bhutan</option>
				<option value="Bolivia">Bolivia</option>
				<option value="Bosnia-Herzegovina">Bosnia-Herzegovina</option>
				<option value="Botswana Republic">Botswana Republic</option>
				<option value="Brazil">Brazil</option>
				<option value="British Virgin Islands">British Virgin Islands</option>
				<option value="Brunei">Brunei</option>
				<option value="Bulgaria">Bulgaria</option>
				<option value="Burkina Faso">Burkina Faso</option>
				<option value="Burundi Republique">Burundi Republique</option>
				<option value="Cambodia">Cambodia</option>
				<option value="Cameroon">Cameroon</option>
				<option value="Canada">Canada</option>
				<option value="Cape Verde">Cape Verde</option>
				<option value="Cayman Islands">Cayman Islands</option>
				<option value="Centrafricana Republique">Centrafricana Republique</option>
				<option value="Chad">Chad</option>
				<option value="Chile">Chile</option>
				<option value="China">China</option>
				<option value="Christmas Island">Christmas Island</option>
				<option value="Cocos Islands">Cocos Islands</option>
				<option value="Colombia">Colombia</option>
				<option value="Comoros">Comoros</option>
				<option value="Congo Rep. Democratique">Congo Rep. Democratique</option>
				<option value="Cook Islands">Cook Islands</option>
				<option value="Costa Rica">Costa Rica</option>
				<option value="CÔTE D'IVOIRE">CÔTE D'IVOIRE</option>
				<option value="Croatia">Croatia</option>
				<option value="Cuba">Cuba</option>
				<option value="Cyprus">Cyprus</option>
				<option value="Czechia">Czechia</option>
				<option value="Denmark">Denmark</option>
				<option value="Djibouti">Djibouti</option>
				<option value="Dominica">Dominica</option>
				<option value="Dominican Republic">Dominican Republic</option>
				<option value="Dutch Antilles">Dutch Antilles</option>
				<option value="Ecuador">Ecuador</option>
				<option value="Egypt">Egypt</option>
				<option value="El Salvador">El Salvador</option>
				<option value="Equatorial Guinea">Equatorial Guinea</option>
				<option value="Eritrea">Eritrea</option>
				<option value="Estonia">Estonia</option>
				<option value="Ethiopia">Ethiopia</option>
				<option value="Falkland Islands">Falkland Islands</option>
				<option value="Faroe Islands">Faroe Islands</option>
				<option value="Federated States of Micronesia">Federated States of Micronesia</option>
				<option value="Fiji">Fiji</option>
				<option value="Finland">Finland</option>
				<option value="France">France</option>
				<option value="French Guiana">French Guiana</option>
				<option value="French Polynesia">French Polynesia</option>
				<option value="Gabon Republique">Gabon Republique</option>
				<option value="Gambia Republic">Gambia Republic</option>
				<option value="Georgia">Georgia</option>
				<option value="Germany">Germany</option>
				<option value="Ghana Republic">Ghana Republic</option>
				<option value="Gibraltar">Gibraltar</option>
				<option value="Greece">Greece</option>
				<option value="Greenland">Greenland</option>
				<option value="Grenada">Grenada</option>
				<option value="Guadeloupe">Guadeloupe</option>
				<option value="Guatemala">Guatemala</option>
				<option value="Guinea Ecuatorial Republica">Guinea Ecuatorial Republica</option>
				<option value="Guinea-Bissau Republica">Guinea-Bissau Republica</option>
				<option value="Guinee Republique">Guinee Republique</option>
				<option value="Guyana">Guyana</option>
				<option value="Haiti">Haiti</option>
				<option value="Honduras">Honduras</option>
				<option value="Hong Kong">Hong Kong</option>
				<option value="Hungary">Hungary</option>
				<option value="Iceland">Iceland</option>
				<option value="Ile de la Reunion">Ile de la Reunion</option>
				<option value="India">India</option>
				<option value="Indonesia">Indonesia</option>
				<option value="Iran">Iran</option>
				<option value="Iraq">Iraq</option>
				<option value="Ireland">Ireland</option>
				<option value="Israel">Israel</option>
				<option value="Italy">Italy</option>
				<option value="Jamaica">Jamaica</option>
				<option value="Japan">Japan</option>
				<option value="Jordan">Jordan</option>
				<option value="Kazakhstan">Kazakhstan</option>
				<option value="Kenya">Kenya</option>
				<option value="Kiribati">Kiribati</option>
				<option value="Kuwait">Kuwait</option>
				<option value="Kyrgizstan">Kyrgizstan</option>
				<option value="Laos">Laos</option>
				<option value="Latvia">Latvia</option>
				<option value="Lebanon">Lebanon</option>
				<option value="Lesotho Kingdom">Lesotho Kingdom</option>
				<option value="Liberia Republic">Liberia Republic</option>
				<option value="Libya">Libya</option>
				<option value="Liechtenstein">Liechtenstein</option>
				<option value="Lithuania">Lithuania</option>
				<option value="Luxembourg">Luxembourg</option>
				<option value="Macau">Macau</option>
				<option value="Macedonia">Macedonia</option>
				<option value="Madagascar Republique">Madagascar Republique</option>
				<option value="Malawi Republic">Malawi Republic</option>
				<option value="Malaysia">Malaysia</option>
				<option value="Mali Republique">Mali Republique</option>
				<option value="Malta">Malta</option>
				<option value="Marshall Islands">Marshall Islands</option>
				<option value="Martinique">Martinique</option>
				<option value="Mauritania">Mauritania</option>
				<option value="Mauritius Ile Republic">Mauritius Ile Republic</option>
				<option value="Mexico">Mexico</option>
				<option value="Mocambique Republique">Mocambique Republique</option>
				<option value="Moldova">Moldova</option>
				<option value="Monaco Principaute">Monaco Principaute</option>
				<option value="Mongolia">Mongolia</option>
				<option value="Montenegro">Montenegro</option>
				<option value="Morocco">Morocco</option>
				<option value="Mozambique">Mozambique</option>
				<option value="Myanmar">Myanmar</option>
				<option value="Namibia Republic">Namibia Republic</option>
				<option value="Nauru">Nauru</option>
				<option value="Nepal">Nepal</option>
				<option value="Netherlands">Netherlands</option>
				<option value="New Caledonia">New Caledonia</option>
				<option value="New Zealand">New Zealand</option>
				<option value="Nicaragua">Nicaragua</option>
				<option value="Niger Republique">Niger Republique</option>
				<option value="Nigeria Republic">Nigeria Republic</option>
				<option value="Niue">Niue</option>
				<option value="Norfolk Island">Norfolk Island</option>
				<option value="North Korea">North Korea</option>
				<option value="Norway">Norway</option>
				<option value="Oman">Oman</option>
				<option value="Pakistan">Pakistan</option>
				<option value="Palau">Palau</option>
				<option value="Panama">Panama</option>
				<option value="Papau New Guinea">Papau New Guinea</option>
				<option value="Paraguay">Paraguay</option>
				<option value="Peru">Peru</option>
				<option value="Philippines">Philippines</option>
				<option value="Poland">Poland</option>
				<option value="Portugal">Portugal</option>
				<option value="Puerto Rico">Puerto Rico</option>
				<option value="Qatar">Qatar</option>
				<option value="Republic of Maldives">Republic of Maldives</option>
				<option value="Romania">Romania</option>
				<option value="Russia">Russia</option>
				<option value="Rwanda">Rwanda</option>
				<option value="San Marino">San Marino</option>
				<option value="Sao Tome e Principe">Sao Tome e Principe</option>
				<option value="Saudi Arabia">Saudi Arabia</option>
				<option value="Senegal Republique">Senegal Republique</option>
				<option value="Serbia">Serbia</option>
				<option value="Seychelles Republique">Seychelles Republique</option>
				<option value="Sierra Leone Republic">Sierra Leone Republic</option>
				<option value="Singapore">Singapore</option>
				<option value="Slovakia">Slovakia</option>
				<option value="Slovenia">Slovenia</option>
				<option value="Solomon Islands">Solomon Islands</option>
				<option value="Somalia">Somalia</option>
				<option value="South Africa">South Africa</option>
				<option value="South Korea">South Korea</option>
				<option value="Spain">Spain</option>
				<option value="Sri Lanka">Sri Lanka</option>
				<option value="St. Barthelemy">St. Barthelemy</option>
				<option value="St. Helena">St. Helena</option>
				<option value="St. Kitts/Nevis">St. Kitts/Nevis</option>
				<option value="St. Lucia">St. Lucia</option>
				<option value="St. Maarten">St. Maarten</option>
				<option value="St. Martin">St. Martin</option>
				<option value="St. Pierre et Miquelon">St. Pierre et Miquelon</option>
				<option value="St. Vincent">St. Vincent</option>
				<option value="Sudan">Sudan</option>
				<option value="Surinam">Surinam</option>
				<option value="Swaziland Kingdom">Swaziland Kingdom</option>
				<option value="Sweden">Sweden</option>
				<option value="Switzerland">Switzerland</option>
				<option value="Syria Republic">Syria Republic</option>
				<option value="Taiwan">Taiwan</option>
				<option value="Tajikistan">Tajikistan</option>
				<option value="Tanzania">Tanzania</option>
				<option value="Thailand">Thailand</option>
				<option value="Togolaise Republique">Togolaise Republique</option>
				<option value="Tokelau">Tokelau</option>
				<option value="Tonga">Tonga</option>
				<option value="Trinidad and Tobago">Trinidad and Tobago</option>
				<option value="Tunisia">Tunisia</option>
				<option value="Turkey">Turkey</option>
				<option value="Turkmenistan">Turkmenistan</option>
				<option value="Turks &amp; Caicos Isles">Turks &amp; Caicos Isles</option>
				<option value="Tuvalu">Tuvalu</option>
				<option value="Uganda Republic">Uganda Republic</option>
				<option value="Ukraine">Ukraine</option>
				<option value="United Arab Emirates">United Arab Emirates</option>
				<option value="United Kingdom">United Kingdom</option>
				<option value="Uruguay">Uruguay</option>
				<option value="USA">USA</option>
				<option value="Uzbekistan">Uzbekistan</option>
				<option value="Vanuatu">Vanuatu</option>
				<option value="Vatican City State">Vatican City State</option>
				<option value="Venezuela">Venezuela</option>
				<option value="Vietnam">Vietnam</option>
				<option value="Wallis e Futuna">Wallis e Futuna</option>
				<option value="Western Sahara">Western Sahara</option>
				<option value="Yemen">Yemen</option>
				<option value="Zaire">Zaire</option>
				<option value="Zambia Republic">Zambia Republic</option>
				<option value="Zimbabwe Republic">Zimbabwe Republic</option>
				</select></div>
                  <div class="type-text"><label for="email">Email:</label> 
                  <input type="text" name="booking.booker.email" id="email" value="<s:property value="booking.booker.email"/>" /></div>

                  <div class="type-text"><label for="zipCode">ZipCode: <sup title="This field is mandatory.">*</sup> </label> 
                  <input type="text" name="booking.booker.zipCode" id="zipCode" value="<s:property value="booking.booker.zipCode"/>" /></div>
                  
                   <div class="type-text"><label for="notes">Note:</label> 
                  	  <textarea name="booking.notes" id="notes"><s:property value="booking.notes"/></textarea>
                    </div>
                  
                    </div>
                    </div>
                  <!--  END ACCORDION  -->
                      <div class="type-select guests-select">
                      	<label for="nr_guests">Guests: <sup title="This field is mandatory.">*</sup></label> 
                      	<select name="booking.nrGuests" id="nr_guests" class="required">
                    	<% 
                    	if (request.getAttribute("booking.room.roomType.maxGuests") != null){
                    		int max_guests= (Integer) request.getAttribute("booking.room.roomType.maxGuests");
							int nr_guests = (Integer) request.getAttribute("booking.nrGuests");
						   	for(int i = 1; i <= max_guests; i++) {
							   %>
							    <option value="<% out.print(i) ; %>" <% if(nr_guests == i ) {out.print("selected=\"selected\"");}%>><% out.print(i) ; %></option>
							   <% 
						   	}	
						}else
						  	{
						  	%>
                    	  	<option value="1">1</option>
                    	  	<option value="2">2</option>
                    	  	<option value="3">3</option>
                    	  	<option value="4">4</option>
   							<%
   							}
						%>
                 		</select>
                 		<div style="margin-top:3px;">
                 	  	  <div class="c60l">
             			 	<a href="#bottom_anchor" class="add_guest" title="add guest"><img src="images/add-icon.png" alt="Add Guest" />Add New Guest Data</a>
              		  	  </div>
              			</div>
                 	  </div>

              		<s:iterator value="booking.guests" var="eachGuest" status="guestStatus" >
              		 <div  class="subcolumns guest_row">
              		  <input type="hidden" class="idGuest" name="booking.guests[<s:property value="#guestStatus.index"/>].id" value="<s:property value="#eachGuest.id"/>"/>
              		    <div class="c33l"><div class="subcl type-text"><span>FirstName:</span><input type="text" name="booking.guests[<s:property value="#guestStatus.index"/>].firstName" value="<s:property value="#eachGuest.firstName" />" class="require" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>LastName:</span><input type="text" name="booking.guests[<s:property value="#guestStatus.index"/>].lastName"  value="<s:property value="#eachGuest.lastName" />" class="required" style="width: 90%;"/></div></div>
                  	  	 <div class="c33r"><label>&nbsp;</label><a href="#" class="erase_guest" title="erase"><img src="images/delete.png" alt="Delete Guest" />Delete Guest</a>
              			</div>
                  	  </div>
                  	  </s:iterator>
                  	  	<s:if test="booking.guests.size() == 0"> 
                  	  <div  class="subcolumns guest_row">
              		  <div class="c33l"><div class="subcl type-text"><span>FirstName:</span><input type="text" name="booking.guests[0].firstName" value="" class="require" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>LastName:</span><input type="text" name="booking.guests[0].lastName"  value="" class="required" style="width: 90%;"/></div></div>
                  	  	 <div class="c33r"><label>&nbsp;</label><a href="#" class="erase_guest" title="erase"><img src="images/delete.png" alt="Delete Guest" />Delete Guest</a>
              			</div>
                  	  </div>
                  	  </s:if>
                  	  
                  	  <div class="type-text"><hr/></div>
                 	  
                  <div class="type-select"><label for="">Extras:</label>
                    <s:iterator value="extras" var="eachExtra" >
                    
                      <div class="type-check">
                    	<s:checkbox id="%{#eachExtra.id}_idExtra" name="bookingExtraIds"  value="bookingExtraIds.contains(#eachExtra.id)" fieldValue="%{#eachExtra.id}" label="%{#eachExtra.name}" />
                      </div> 
                      
                      <s:iterator value="booking.extraItems" var="eachExtraItem" status="itemStatus">
                                    
                      	<s:if test="#eachExtraItem.extra == #eachExtra">	
              		  	<div class="type-select">
              		  	  <input type="hidden" class="idExtraItem" name="booking.extraItems[<s:property value="#itemStatus.index"/>].id" value="<s:property value="#eachExtraItem.id"/>"/>
              		  	  <input type="hidden" class="idExtraItem" name="booking.extraItems[<s:property value="#itemStatus.index"/>].extra.id" value="<s:property value="#eachExtraItem.extra.id"/>"/>
              		  	  <div class="c40l">
              		  	  	<label for="quantity">Quantity: </label>
              		  	  	<select name="booking.extraItems[<s:property value="#itemStatus.index"/>].quantity" id="quantity" class="required">
              		  	  	
              		  	  	<s:if test="#eachExtraItem.quantity != null">
              		  	  	  <s:bean name="org.apache.struts2.util.Counter" var="counter">
								<s:param name="last" value="#eachExtraItem.quantity"/>
							  </s:bean>
							  <s:iterator value="#counter" var="index">
								<option value="<s:property value="#index"/>"
								  <s:if test="#eachExtraItem.quantity == #index">selected=selected</s:if> >
								  <s:property value="#index"/>					
								</option>
							  </s:iterator>
              		  	  	</s:if>
              		  	  	<s:else>
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
              		  	  	</s:else> 
              		  	  	</select>   	
              		  	</div>
                  	  	<div class="c33l">
                  	  	  <div class="subcl type-text"><span>&euro;: </span><s:property value="#eachExtraItem.unitaryPrice" /></div>
                  	  	</div>
                  	  </div>
                  	  </s:if>             	  
                  	  
              		  </s:iterator>
              		</s:iterator>
                  </div>
                   
                  <div class="type-select">
                  	<label for="convention">Convention: </label> 
                    <select name="booking.convention.id" id="convention" class="required">
                    <s:iterator value="conventions" var="eachConvention">
                   	  <option 
                   	  	<s:if test="#eachConvention.id == booking.convention.id">selected="selected"</s:if> 
                   		value="<s:property value="#eachConvention.id"/>"><s:property value="#eachConvention.name"/>
                   	  </option>
                   	</s:iterator>
                   	</select>
                  </div>  
                           
                </fieldset>
                </div>
                <div class="c5l">
                &nbsp;
                </div>
                <div class="book_details c50l">
                  <fieldset><legend>Price</legend>
                  	<div class="type-text"><span>Room: </span><div class="c33r"><span id="price_room" ><s:property value="booking.roomSubtotal"/></span> &euro; </div></div>
                  	<div class="type-text"><span>Extras: </span><div class="c33r"><span id="extras_room" ><s:property value="booking.extraSubtotal"/></span> &euro; </div></div>
                  	<div class="type-text"><hr/></div>
                  	<div class="type-text"><span class="green">&nbsp;Adjustments: </span>
                  	  <div class="c50r">
             			<a href="#bottom_anchor" class="add_adjustment" title="add adjustment"><img src="images/add-icon.png" alt="Add Adjustment" />Add New Adjustment</a>
              		  </div>
              		</div>
              		<s:iterator value="booking.adjustments" var="eachAdjust" status="adjustStatus">
              		  <div  class="subcolumns adjustment_row">
              		  	<input type="hidden" class="idAdjustment" name="booking.adjustments[<s:property value="#adjustStatus.index"/>].id" value="<s:property value="#eachAdjust.id"/>"/>
              		  	<div class="c40l">
              		  	  <div class="subcl type-text"><span>Name:</span><input type="text" name="booking.adjustments[<s:property value="#adjustStatus.index"/>].description" value="<s:property value="#eachAdjust.description" />" class="require" style="width: 90%;" /></div>
              		  	</div>
                  	  	<div class="c33l">
                  	  	  <div class="subcl type-text"><span>Amount(&euro;):</span><input type="text" name="booking.adjustments[<s:property value="#adjustStatus.index"/>].amount"  value="<s:property value="#eachAdjust.amount" />" class="extra_value_adjustment validPrice"/></div>
                  	  	</div>
                  	  	<div class="c25r"><label>&nbsp;</label><a href="#" class="erase_adjustment" title="erase"><img src="images/delete.png" alt="Delete Adjustment" />Delete Adjustment</a></div>
                  	  </div>
              		</s:iterator>
              		<a name="bottom_anchor"></a> 
                 	<div class="type-text">
                 	  <span>Subtotal: </span><div class="c33r"><span class="subtotal_room" ><s:property value="%{ booking.roomSubtotal + booking.extraSubtotal + adjustmentsSubtotal}"/>
                 	  </span> &euro;<input type="hidden" id="subtotal_room" name="booking.subtotal" value="<s:property value="%{ booking.roomSubtotal + booking.extraSubtotal + adjustmentsSubtotal}"/>" /></div>
                 	</div>
                  	<div class="type-text"><hr/></div>
                    <div class="type-text"><span class="green">&nbsp;Payment Received: </span>
                     	<div class="c50r">
             			 	<a href="#bottom_anchor" class="add_payment" title="add payment"><img src="images/add-icon.png" alt="Add Payment" />Add New Payment</a>
              			</div>
              		</div>
              		
              		
              		
              		
              		
              		
              		<s:iterator value="booking.payments" var="eachPayment" status="paymentStatus">
              		     <div  class="subcolumns payment_row">
              		     <input type="hidden" class="idPayment" name="booking.payments[<s:property value="#paymentStatus.index"/>].id" value="<s:property value="#eachPayment.id"/>"/>
              		    <div class="c40l"><div class="subcl type-text"><span>Name:</span><input type="text" name="booking.payments[<s:property value="#paymentStatus.index"/>].description" value="<s:property value="#eachPayment.description" />" class="require" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>Amount(&euro;):</span><input type="text" name="booking.payments[<s:property value="#paymentStatus.index"/>].amount"  value="<s:property value="#eachPayment.amount" />" class="extra_value_payment required validPrice"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor_payment" class="erase_payment" title="erase"><img src="images/delete.png" alt="Delete Payment" />Delete Payment</a>
              			</div>
                  	  </div>
              		</s:iterator><!--
              		<s:if test="booking.payments.size() == 0"> 
              	         <div  class="subcolumns payment_row">
                  	     <div class="c40l"><div class="subcl type-text"><span>Name:</span><input type="text" name="booking.payments[0].description" class="require" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>Amount(&euro;):</span><input type="text" name="booking.payments[0].amount"  class="extra_value_payment required"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor_payment" class="erase_payment" title="erase"><img src="images/delete.png" alt="Delete Payment" />Delete Payment</a>
              			</div>
                  	  </div>	
              		</s:if>
              		--><a name="bottom_anchor_payment"></a> 
              		<div class="type-text">
              			<span>Balance Due: </span>
              			<div class="c33r"><span class="balance_room" ><s:property value="%{ booking.roomSubtotal + booking.extraSubtotal + adjustmentsSubtotal - paymentsSubtotal}"/></span> &euro;<input type="hidden" id="balance_room" value="<s:property value="%{ booking.roomSubtotal + booking.extraSubtotal - paymentsSubtotal}"/>" /></div></div>
                  
              		
              		
                      <!--<div class="c50r">
                      	<div class="c10r">&euro;</div>
                      	<div class="c40l"><input type="text" name="pay_adjustment[]" id="pay_adjustment" /></div>
                      	<div class="c40r"><input type="text" name="pay_value_adjustment[]"  id="pay_value_adjustment" class="pay_value_adjustment digits"/></div>
                      </div>
                    <div class="type-text"><span>Balance Due: </span><div class="c33r"><span class="balance_room" ><s:property value="%{ booking.roomSubtotal + booking.extraSubtotal}"/></span> &euro;<input type="hidden" id="balance_room" value="<s:property value="%{ booking.roomSubtotal + booking.extraSubtotal}"/>" /></div></div>
                  
                  -->
                  </fieldset>
              	  <div class="type-button">
               		<button class="btn_save">SAVE</button>
            	  </div>
                </div>
              </div>
            </form>
           
                    <div  class="subcolumns adjustment_row" id="to_add_adjustment" style="display: none;">
                  	     <div class="c40l"><div class="subcl type-text"><span>Name:</span><input type="text" name="booking.adjustments[__PVALUE__].description" class="require" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>Amount(&euro;):</span><input type="text" name="booking.adjustments[__PVALUE__].amount"  class="extra_value_adjustment validPrice"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor" class="erase_adjustment" title="erase"><img src="images/delete.png" alt="Delete Adjustment" />Delete Adjustment</a>
              			</div>
                  	  </div>
                  	  
                  	  <div  class="subcolumns payment_row" id="to_add_payment" style="display: none;" >
                  	     <div class="c40l"><div class="subcl type-text"><span>Name:</span><input type="text" name="booking.payments[__PVALUE__].description" class="require" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>Amount(&euro;):</span><input type="text" name="booking.payments[__PVALUE__].amount"  class="extra_value_payment validPrice"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor_payment" class="erase_payment" title="erase"><img src="images/delete.png" alt="Delete Payment" />Delete Payment</a>
              			</div>
                  	  </div>
                  	  
                  	  <div  class="subcolumns guest_row" id="to_add_guest" style="display: none;">
              		  <div class="c33l"><div class="subcl type-text"><span>FirstName:</span><input type="text" name="booking.guests[__PVALUE__].firstName" value="" class="require" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span>LastName:</span><input type="text" name="booking.guests[__PVALUE__].lastName"  value="" class="required" style="width: 90%;"/></div></div>
                  	  	 <div class="c33r"><label>&nbsp;</label><a href="#" class="erase_guest" title="erase"><img src="images/delete.png" alt="Delete Guest" />Delete Guest</a>
              			</div>
                  	  </div>