<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
				
		<s:iterator value="priceList.items" var="eachPriceListItem" status="itemStatus">
          	  <tr>
          	  	<td><input type="hidden" name="redirect_form" value="goFindAllExtraPriceLists.action?sect=settings" />
				<input type="hidden" name="priceList.id" value="<s:property value="priceList.id"/>"/><input type="hidden" name="priceList.items[<s:property value="#itemStatus.index"/>].id" value="<s:property value="#eachPriceListItem.id"/>"/>
          	  	<b><s:property value="#eachPriceListItem.extra.name"/></b> (<s:property value="#eachPriceListItem.extra.timePriceType"/>/<s:property value="#eachPriceListItem.extra.resourcePriceType"/>)</td>
          	  	<td><input type="text" name="priceList.items[<s:property value="#itemStatus.index"/>].price" id="extraPrices" value="<s:property value="#eachPriceListItem.price"/>" class="required number noBorder static_small_input" readonly="readonly"/></td>
          	  </tr>
        </s:iterator>
                      
                      
					   