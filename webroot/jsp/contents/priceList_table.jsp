<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		<s:iterator value="priceList.items" var="eachPriceListItem" >
          	  <tr>
          	  	<td><s:property value="#eachPriceListItem.numGuests"/></td>	
          	  	<td><s:property value="#eachPriceListItem.prices[0]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[1]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[2]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[3]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[4]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[5]"/></td>
          	  	<td><s:property value="#eachPriceListItem.prices[6]"/></td>
          	  </tr>
        </s:iterator>
                      
                      
					   