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
		     	  
		  <s:iterator value="extras" var="eachExtra" >
		  
		     	<div class="type-check ">
                  <s:checkbox id="%{#eachExtra.id}_extraCheckBox" name="bookingExtraIds"  value="bookingExtraIds.contains(#eachExtra.id)" fieldValue="%{#eachExtra.id}" label="%{#eachExtra.name}" />
                </div>
                	  
					<s:iterator value="booking.extraItems" var="eachExtraItem" status="itemStatus">
                               
                      <s:if test="#eachExtraItem.extra == #eachExtra">
                      	<div id="<s:property value="#eachExtra.id"/>_extraQuantity" class="type-select ">
                      	  <input type="hidden" class="idExtraItem" name="booking.extraItems[<s:property value="#itemStatus.index"/>].id" value="<s:property value="#eachExtraItem.id"/>"/>
       					  <input type="hidden" class="idExtra" name="booking.extraItems[<s:property value="#itemStatus.index"/>].extra.id" value="<s:property value="#eachExtraItem.extra.id"/>"/>
              		  	  <div class="c40l">
              		  	  	<label for="quantity"><s:text name="quantity" />: </label>
              		  	  	<select name="booking.extraItems[<s:property value="#itemStatus.index"/>].quantity" id='<s:property value="#eachExtra.id"/>_quantity' class="quantity required">
              		  	  	  <s:bean name="org.apache.struts2.util.Counter" var="counter">
								<s:param name="last" value="#eachExtraItem.maxQuantity"/>
							  </s:bean>
							  <s:iterator value="#counter" var="index">
								<option value="<s:property value="#index"/>"
								  <s:if test="#eachExtraItem.quantity == #index">selected=selected</s:if> >
								  <s:property value="#index"/>					
								</option>
							  </s:iterator>
              		  	  	</select>
              		  	  </div>
                  	  	  <div class="c40l">
                  	  	    <div class="subcl type-text"><span>&euro;: </span><s:property value="#eachExtraItem.unitaryPrice" />&nbsp;<s:text name="unitaryPrice" />
                  	  	     <input type="hidden"  name="booking.extraItems[<s:property value="#itemStatus.index"/>].unitaryPrice" value="<s:property value="#eachExtraItem.unitaryPrice" />"/>
                  	        </div>
                  	  	  </div>
                  	    </div>
                  	  </s:if>
          	        </s:iterator>
          </s:iterator>		   