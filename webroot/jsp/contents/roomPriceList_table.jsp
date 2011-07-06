<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<s:iterator value="priceList.items" var="eachPriceListItem" status="itemStatus">
          	  <tr>
          	  	<td>	
				<input type="hidden" name="redirect_form" value="goFindAllRoomPriceLists.action?sect=settings" />
				<input type="hidden" name="priceList.id" value="<s:property value="priceList.id"/>"/>
			 <input type="hidden" name="priceList.items[<s:property value="#itemStatus.index"/>].id" value="<s:property value="#eachPriceListItem.id"/>"/><s:property value="#eachPriceListItem.numGuests"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceMonday" id="priceMonday" value="<s:property value="#eachPriceListItem.priceMonday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceTuesday" id="priceTuesday" value="<s:property value="#eachPriceListItem.priceTuesday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceWednesday" id="priceWednesday" value="<s:property value="#eachPriceListItem.priceWednesday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceThursday" id="priceThursday" value="<s:property value="#eachPriceListItem.priceThursday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceFriday" id=priceFriday" value="<s:property value="#eachPriceListItem.priceFriday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceSaturday" id="priceSaturday" value="<s:property value="#eachPriceListItem.priceSaturday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].priceSunday" id="priceSunday" value="<s:property value="#eachPriceListItem.priceSunday"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  </tr>
        </s:iterator>
                      
                      
					   