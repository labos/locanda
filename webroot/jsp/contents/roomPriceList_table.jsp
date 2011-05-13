<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		
		<s:iterator value="priceList.items" var="eachPriceListItem" status="itemStatus">
          	  <tr>
          	  	<td> <input type="hidden" name="priceList.items[<s:property value="#itemStatus.index"/>].id" value="<s:property value="#eachPriceListItem.id"/>"/>
          	  	<s:property value="#eachPriceListItem.numGuests"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[0]" id="prices[0]" value="<s:property value="#eachPriceListItem.prices[0]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[1]" id="prices[1]" value="<s:property value="#eachPriceListItem.prices[1]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[2]" id="prices[2]" value="<s:property value="#eachPriceListItem.prices[2]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[3]" id="prices[3]" value="<s:property value="#eachPriceListItem.prices[3]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[4]" id="prices[4]" value="<s:property value="#eachPriceListItem.prices[4]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[5]" id="prices[5]" value="<s:property value="#eachPriceListItem.prices[5]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].prices[6]" id="prices[6]" value="<s:property value="#eachPriceListItem.prices[6]"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  </tr>
        </s:iterator>
                      
                      
					   