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
 <s:url action="birt" var="url_birt"></s:url>
<style>
.type_rooms{
display: none;
}
.yform .type-select select {
width:80%;
}
</style>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
Map<String, String> creditCardMap = new HashMap<String, String>();
creditCardMap.put("visa", "Visa");
creditCardMap.put("mastercard", "Master Card");
creditCardMap.put("amex", "American Express");
creditCardMap.put("discover", "Discover");
String cardTypeSelected = (String) request.getAttribute("booking.creditCard.cardType");
%>
	<div class="validationErrors"></div>
      <form class="yform json" action="saveUpdateBooking.action">
        <input type="hidden" name="redirect_form" value="false" />
                             <input type="hidden" name="booking.id" value="<s:property value="booking.id"/>"/>
                             <div class="subcolumns_oldgecko">
                             <fieldset>
    							<legend><s:text name="resumeBooking" />:</legend>
                             <div class="c20l">
                             <div class="subcl type-select">
               				 <label for="sel_rooms_list"><s:text name="room" /> <sup title="<s:text name="thisFileMandatory" />.">*</sup> </label>
                			 <select size="1" id="sel_rooms_list" class="required number" name="booking.room.id">
                  			 <s:iterator value="rooms" var="eachRoom" >
                   			   <option 
                   			   <s:if test="#eachRoom.id == booking.room.id">selected="selected"</s:if> 
                   			   value="<s:property value="#eachRoom.id"/>"><s:property value="#eachRoom.name"/>
                   			   </option>
                    		</s:iterator>
                    		<s:if test="booking.room == null">
							  <option selected="selected" value=""><s:text name="selectOne"/></option>
							</s:if>	
                    		</select>
            				</div>
            				</div>
            				<div class="c20l">
            				<div class="subcl type-text">
            				<label for="datepicker"><s:text name="dateIn" />: <sup title="<s:text name="thisFileMandatory"/>.">*</sup> </label>
							<input type="text" name="booking.dateIn" class="datepicker required"  value="<s:date name="booking.dateIn" format="%{#session.datePattern}" />" style="display: inline;"/>
							</div>
							</div>
							<div class="c20l">
                             <span id="date_booking" ></span><span id="duration"></span>
                             <div class="subcl type-select">
                             <label for="duration"><s:text name="numNights" /> <sup title="<s:text name="thisFileMandatory" />.">*</sup> </label>
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
                       		 <option value="24">24 </option>
                       		 <option value="25">25 </option>
                       		 <option value="26">26 </option>
                       		 <option value="27">27 </option>
                       		 <option value="28">28 </option>
                       		 <option value="29">29 </option>
                       		 <option value="30">30 </option>
                       		 <option value="31">31 </option>
                       		 <option value="32">32 </option>
                       		 <option value="33">33 </option>
                       		 <option value="34">34 </option>
                       		 <option value="35">35 </option>
                       		 <option value="36">36 </option>
                       		 <option value="37">37 </option>
                       		 <option value="38">38 </option>
                       		 <option value="39">39 </option>
                       		 <option value="40">40 </option>
                       		 <option value="41">41 </option>
                       		 <option value="42">42 </option>
                       		 <option value="43">43 </option>
                       		 <option value="44">44 </option>
                       		 <option value="45">45 </option>
                       		 <option value="46">46 </option>
                       		 <option value="47">47 </option>
                       		 <option value="48">48 </option>
                       		 <option value="49">49 </option>
                       		 <option value="50">50 </option>
                       		 <option value="51">51 </option>
                       		 <option value="52">52 </option>
                       		 <option value="53">53 </option>
                       		 <option value="54">54 </option>
                       		 <option value="55">55 </option>
                       		 <option value="56">56 </option>
                       		 <option value="57">57 </option>
                       		 <option value="58">58 </option>
                       		 <option value="59">59 </option>
                       		 <option value="60">60 </option>
                       		 </select>
                             </div>
                             </div>
                            <div class="c20l">
            				<div class="subcl type-text">
            				<label for="datepicker"><s:text name="dateOut" />: <sup title="<s:text name="thisFileMandatory" />.">*</sup> </label>
							<input type="text" name="booking.dateOut" class="datepicker required"  value="<s:date name="booking.dateOut" format="%{#session.datePattern}" />" style="display: inline;"/>
							</div>
							</div>
							<div class="c20l">
                             
                             <div class="subcl type-select">
                             <!--
                             <label for="confirm"><s:text name="status" /></label>
                             <select name="booking.status" class="confirm" id="confirm">
                             <option selected="selected" value="<s:property value="booking.status" default="confirmed"/>"><s:text name="%{booking.status}"/></option>
                             <option value="confirmed"><s:text name="confirmed" /></option>
                             <option value="provisional"><s:text name="provisional" /></option>
                             <option value="checkedin"><s:text name="checkedin" /></option>
                             <option value="checkedout"><s:text name="checkedout" /></option>
                             </select>
                               -->
	                              <s:select label="%{getText('status')}"
							        name="booking.status"
							        list="#{'confirmed':getText('confirmed'), 'provisional':getText('provisional'), 'checkedin': getText('checkedin'), 'checkedout': getText('checkedout')}"
									headerKey="booking.status"
							        multiple="false"
							        size="1"
							        required="true"/>
                                    </div>
                             </div>
                 </fieldset>         
              </div>
              <div class="subcolumns_oldgecko">
               <div class="c50l">
              <fieldset>
    <legend><s:text name="bookingDetails" />:</legend>
                <input type="hidden" name="booking.booker.id" value="<s:property value="booking.booker.id"/>"/>
                <jsp:include page="../templates/housed.mustache.jsp"/>
                
                <s:if test="booking.id != null">
                	<div id="accordion">
                		<h2><a href="#top_accordion"><s:text name="bookerDetails" /></a></h2>
                		<div id="selectbooker-list"></div>
                		<h2><a href="#top_accordion"><s:text name="groupleaderDetails" /></a></h2>
                		<div id="selectgroupleader-list"></div>
                	</div>
                	<div class="type-select guests-select">
			          	<s:select label="%{getText('guests')}"
							        name="booking.nrGuests"
							        list="listNumGuests"
									headerKey="booking.nrGuests"
							        multiple="false"
							        size="1"
							        required="true"
							        id="nr_guests"/>
							        
			    	</div>
                	<div id="selecthouseds-list"></div>
		        	<script>
		        		window.SelectBookerWidget = new SelectBookerView({
                        	idStructure: Entity.idStructure,
                        	modifing: true,
                        	id_booking: <s:property value="booking.id"/>
                    	});
                    	window.SelectGroupLeaderWidget = new SelectGroupLeaderView({
                    		idStructure: Entity.idStructure,
                    	});
                    	window.ListHousedWidget = new ListHousedView({
                    		idStructure: Entity.idStructure,
                    		id_booking: <s:property value="booking.id"/>
                    	});
		        	</script>
                </s:if>
                <s:else>
                	<div id="accordion">
                		<h2><a href="#top_accordion"><s:text name="bookerDetails" /></a></h2>
                		<div id="selectbooker-list"></div>
                	</div>
                	<div class="type-select guests-select">
		              	<s:select label="%{getText('guests')}"
							        name="booking.nrGuests"
							        list="listNumGuests"
									headerKey="booking.nrGuests"
							        multiple="false"
							        size="1"
							        required="true"
							        id="nr_guests"/>
							        
		        	</div>
                </s:else>
                
	            
              	
                
                	
               			
                  <div class="type-text"><hr/></div>	
                    <div id="accordion2">
                		<h2><a href="#top_accordion">Extras</a></h2>  
                  <div class="type-select extraCheckList"><label for="">Extras:</label>
                      	<jsp:include page="extraQuantity_select.jsp" />     	  
                  </div>
                  </div>
                  <div class="type-select">
                  	<label for="convention"><s:text name="convention" />: </label> 
                    <select name="booking.convention.id" id="convention" class="required">
                    <s:iterator value="conventions" var="eachConvention">
                   	  <option 
                   	  	<s:if test="#eachConvention.id == booking.convention.id">selected="selected"</s:if> 
                   		value="<s:property value="#eachConvention.id"/>"><s:property value="#eachConvention.name"/>
                   	  </option>
                   	</s:iterator>
                   	</select>
                  </div>
                  <div id="accordion3">
                		<h2><a href="#top_accordion"><s:text name="notes"></s:text></a></h2>  
	                  <div class="type-select"><label for=""><s:text name="notes" />:</label>
	                      	<textarea rows="5" cols="5" name="booking.notes"><s:property value="booking.notes"/></textarea>     	  
	                  </div>
                  </div>
                           
                </fieldset>
                </div>
                <div class="c5l">
                &nbsp;
                </div>
                <div class="book_details c45l">
                  <fieldset><legend><s:text name="price" /></legend>
                  	<div class="type-text"><span><s:text name="room" />: </span><div class="c33r"><span id="price_room" ><s:property value="booking.roomSubtotal"/></span> &euro; </div></div>
                  	<div class="type-text"><span><s:text name="extras" />: </span><div class="c33r"><span id="extras_room" ><s:property value="booking.extraSubtotal"/></span> &euro; </div></div>
                  	<div class="type-text"><hr/></div>
                  	<div class="type-text">
                  	  <div class="c80r">
             			<a href="#adjustment_anchor" class="add_adjustment" title="add adjustment"><img src="images/add-icon.png" alt="Add Adjustment" /><s:text name="addNewAdjustment" /></a>
              		  	<a name="adjustment_anchor"></a>
              		  </div>
              		</div>
              		<s:iterator value="booking.adjustments" var="eachAdjust" status="adjustStatus">
              		  <div  class="subcolumns adjustment_row">
              		  	<input type="hidden" class="idAdjustment" name="booking.adjustments[<s:property value="#adjustStatus.index"/>].id" value="<s:property value="#eachAdjust.id"/>"/>
              		  	<div class="c40l">
              		  	  <div class="subcl type-text"><span><s:text name="name"/>:</span><input type="text" name="booking.adjustments[<s:property value="#adjustStatus.index"/>].description" value="<s:property value="#eachAdjust.description" />" class="required" style="width: 90%;" /></div>
              		  	</div>
                  	  	<div class="c33l">
                  	  	  <div class="subcl type-text"><span><s:text name="amount"/> (&euro;):</span><input type="text" name="booking.adjustments[<s:property value="#adjustStatus.index"/>].amount"  value="<s:property value="#eachAdjust.amount" />" class="extra_value_adjustment required validPrice"/></div>
                  	  	</div>
                  	  	<div class="c25r"><label>&nbsp;</label><a href="#" class="erase_adjustment" title="erase"><img src="images/delete.png" alt="Delete Adjustment" /><s:text name="deleteAdjustment" /></a></div>
                  	  </div>
              		</s:iterator> 
                 	<div class="type-text">
                 	  <span><s:text name="subtotal" />: </span><div class="c33r"><span class="subtotal_room" ><s:property value="%{ booking.roomSubtotal + booking.extraSubtotal + adjustmentsSubtotal}"/>
                 	  </span> &euro;<!-- <input type="hidden" id="subtotal_room" name="booking.subtotal" value="<s:property value="%{ booking.roomSubtotal + booking.extraSubtotal + adjustmentsSubtotal}"/>" /> --></div>
                 	</div>
                  	<div class="type-text"><hr/></div>
                    <div class="type-text">
                     	<div class="c80r">
             			 	<a href="#payment_anchor" class="add_payment" title="add payment"><img src="images/add-icon.png" alt="Add Payment" /><s:text name="addNewPayment" /></a>
              				<a name="payment_anchor"></a>
              			</div>
              		</div>
              		
              		
              		
              		
              		
              		
              		<s:iterator value="booking.payments" var="eachPayment" status="paymentStatus">
              		     <div  class="subcolumns payment_row">
              		     <input type="hidden" class="idPayment" name="booking.payments[<s:property value="#paymentStatus.index"/>].id" value="<s:property value="#eachPayment.id"/>"/>
              		    <div class="c40l"><div class="subcl type-text"><span><s:text name="name" />:</span><input type="text" name="booking.payments[<s:property value="#paymentStatus.index"/>].description" value="<s:property value="#eachPayment.description" />" class="required" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span><s:text name="amount" />(&euro;):</span><input type="text" name="booking.payments[<s:property value="#paymentStatus.index"/>].amount"  value="<s:property value="#eachPayment.amount" />" class="extra_value_payment required validPricePositive"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor_payment" class="erase_payment" title="erase"><img src="images/delete.png" alt="Delete Payment" /><s:text name="deletePayment" /></a>
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
              			<span><s:text name="balanceDue" />: </span>
              			<div class="c33r"><span class="balance_room" ><s:property value="%{ booking.roomSubtotal + booking.extraSubtotal + adjustmentsSubtotal - paymentsSubtotal}"/></span> &euro;<input type="hidden" id="balance_room" value="<s:property value="%{ booking.roomSubtotal + booking.extraSubtotal - paymentsSubtotal}"/>" /></div></div>
                  
              		
              		
                      <!--<div class="c50r">
                      	<div class="c10r">&euro;</div>
                      	<div class="c40l"><input type="text" name="pay_adjustment[]" id="pay_adjustment" /></div>
                      	<div class="c40r"><input type="text" name="pay_value_adjustment[]"  id="pay_value_adjustment" class="pay_value_adjustment digits"/></div>
                      </div>
                    <div class="type-text"><span>Balance Due: </span><div class="c33r"><span class="balance_room" ><s:property value="%{ booking.roomSubtotal + booking.extraSubtotal}"/></span> &euro;<input type="hidden" id="balance_room" value="<s:property value="%{ booking.roomSubtotal + booking.extraSubtotal}"/>" /></div></div>
                  
                  -->
                  <div id="accordion4">
                		<h2><a href="#top_accordion"><s:text name="creditCard"></s:text></a></h2>
                		 <div>
                		 <!--<div class="subcolumns">
                		 <a id="eraseCreditCard" title="erase" style="float: right;" href="#bottom_anchor_payment"><img alt="Delete Credit Card" src="images/delete.png"></a>
                		 </div>  -->
                		  <div  class="subcolumns">
                		    <input type="hidden"  name="booking.creditCard.id" value="<s:property value="booking.creditCard.id"/>"/>
                		   	<div class="c40l"><div class="subcl type-select"><span><s:text name="creditCardType" />:</span><select name="booking.creditCard.cardType" value="<s:property value="booking.creditCard.cardType" />">
                 		<s:if test="booking.creditCard.id != null">
                 		<option selected="selected" value="<s:property value="booking.creditCard.cardType"/>"><% 	out.println(creditCardMap.get(cardTypeSelected)); %></option>
                 		</s:if>
                 		<option value="visa">Visa</option>
                 		<option value="mastercard">Master Card</option>
                 		<option value="amex">American Express</option>
                 	    <option value="discover">Discover</option>	
	              		</select></div></div>
                  	  	<div class="c60l"><div class="subcl type-text"><span><s:text name="creditCardNumber" /></span><input type="text" name="booking.creditCard.cardNumber"  value="<s:property value="booking.creditCard.cardNumber" />" class="digits"/></div></div>            	  	 
                 	  </div>
                 	  <div class="subcolumns">
                 	  	<strong><s:text name="creditCardExpirationDate"></s:text></strong>
                 	  </div>
                 	  <div  class="subcolumns"> 	
                 		<div class="c40l"><div class="subcl type-select" style="border-top:1px solid grey;"><span><s:text name="month" />:</span><select name="booking.creditCard.expMonth" value="<s:property value="booking.creditCard.expMonth" />" class="digits">
                 		<s:if test="booking.creditCard.id != null">
                 		<option selected="selected" value="<s:property value="booking.creditCard.expMonth"/>"><s:property value="booking.creditCard.expMonth"/></option>
                 		</s:if>
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
                 		</select></div></div>
                  	  	<div class="c25l"><div class="subcl type-select"  style="border-top:1px solid grey;"><span><s:text name="year" />:</span><input type="text" name="booking.creditCard.expYear"  value="<s:property value="booking.creditCard.expYear" />" class="validPricePositive" min="" max=""/></div></div>            	  	 
                 	    <div class="c33l"><!--<div class="subcl type-text"><span><s:text name="creditCardSecurityCode" /></span><input type="text" name="booking.creditCard.securityCode"  value="<s:property value="booking.creditCard.securityCode" />" class="digits" minlength="3" maxlength="3" /></div>--></div>            	  	 
                 	  </div>
                 	  <div  class="subcolumns"> 	
                 		<div class="c50l"><div class="subcl type-text"><span><s:text name="firstName" />:</span><input type="text" name="booking.creditCard.firstName" value="<s:property value="booking.creditCard.firstName" />"  /></div></div>
                  	  	<div class="c50l"><div class="subcl type-text"><span><s:text name="lastName" /></span><input type="text" name="booking.creditCard.lastName"  value="<s:property value="booking.creditCard.lastName" />" /></div></div>            	  	 
                 	  </div>
                 	 </div>
                  </div>
                  </fieldset>
              	  <div class="type-button">
               		<button class="btn_save"><s:text name="save"/></button>
               		<a class="canc_booking" href="<s:property value="url_home"/>?sect=planner"><s:text name="cancel" /></a>
               		<s:if test="booking.id != null">
               		<a class="invoice_booking" target="_blank" href="<s:property value="url_birt"/>?rp=bookinginvoice&bookid=${booking.id}"><s:text name="invoiceDownload" /></a>
               		</s:if>
            	  </div>
                </div>
              </div>
            </form>
           
                    <div  class="subcolumns adjustment_row" id="to_add_adjustment" style="display: none;">
                  	     <div class="c40l"><div class="subcl type-text"><span><s:text name="name" />:</span><input type="text" name="booking.adjustments[__PVALUE__].description" class="required" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span><s:text name="amount" />(&euro;):</span><input type="text" name="booking.adjustments[__PVALUE__].amount"  class="extra_value_adjustment required validPrice"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor" class="erase_adjustment" title="erase"><img src="images/delete.png" alt="Delete Adjustment" /><s:text name="deleteAdjustment" /></a>
              			</div>
                  	  </div>
                  	  
                  	  <div  class="subcolumns payment_row" id="to_add_payment" style="display: none;" >
                  	     <div class="c40l"><div class="subcl type-text"><span><s:text name="name" />:</span><input type="text" name="booking.payments[__PVALUE__].description" class="required" style="width: 90%;" /></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span><s:text name="amount" />(&euro;):</span><input type="text" name="booking.payments[__PVALUE__].amount"  class="extra_value_payment required validPricePositive"/></div></div>
                  	  	 <div class="c25r"><label>&nbsp;</label><a href="#bottom_anchor_payment" class="erase_payment" title="erase"><img src="images/delete.png" alt="Delete Payment" /><s:text name="deleteAdjustment" /></a>
              			</div>
                  	  </div>
                  	  
                  	  <div  class="subcolumns guest_row" id="to_add_guest" style="display: none;">
              		  <div class="c25l"><div class="subcl type-text"><span><s:text name="firstName" />:</span><input type="text" name="booking.guests[__PVALUE__].firstName" value="" class="required" /></div></div>
                  	  	<div class="c25l"><div class="subcl type-text"><span><s:text name="lastName" />:</span><input type="text" name="booking.guests[__PVALUE__].lastName"  value="" class="required" style="width: 90%;"/></div></div>
                  	  	<div class="c33l"><div class="subcl type-text"><span><s:text name="idNumber" />:</span><input type="text" name="booking.guests[__PVALUE__].idNumber" value="" class="required" /></div></div> 
                  	  	 <div class="c10r"><label>&nbsp;</label><a href="#" class="erase_guest" title="erase"><img src="images/delete.png" alt="Delete Guest" /><s:text name="deleteGuest" /></a>
              			</div>
                  	  </div>
                  	  
<s:if test="booking.id == null">
	<script>
		var readyStateCheckInterval = setInterval(function() {
		    if (document.readyState === "complete") {
		    	try {
			        window.SelectBookerWidget = new SelectBookerView({
			        	idStructure: Entity.idStructure,
			    	});
			        clearInterval(readyStateCheckInterval);
			    } catch(e) {}
		    }
		}, 100);
	</script>
</s:if>