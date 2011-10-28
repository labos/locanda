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
		<s:iterator value="priceList.items" var="eachPriceListItem" status="itemStatus">
          	  <tr>
          	  	<td><input type="hidden" name="redirect_form" value="false" />
				<input type="hidden" name="priceList.items[<s:property value="#itemStatus.index"/>].id" value="<s:property value="#eachPriceListItem.id"/>"/>
          	  	<b><s:property value="#eachPriceListItem.extra.name"/></b> (<s:property value="#eachPriceListItem.extra.timePriceType"/>/<s:property value="#eachPriceListItem.extra.resourcePriceType"/>)</td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].price" id="extraPrices" value="<s:property value="#eachPriceListItem.price"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  </tr>
        </s:iterator>
     <tr style="display:none;"><td><input type="hidden" name="priceList.id" value="<s:property value="priceList.id"/>"/>		
</td></tr>      
                      
                      
					   