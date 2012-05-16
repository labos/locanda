<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="../layout/header_widget.jsp" />
  <!-- begin: #col3 static column -->
  <div class="widget-booking">
	<div><h2><s:text name="selectDateOnline"/></h2></div>
    <div id='largeDatepicker'></div>
    <form action="goOnlineBookingRooms.action" class="yform json">
      <input type="hidden" name="dateArrival" value=""/>
      <div class="c33l">
        <div class="subcl type-select">
          <label for="sel_rooms_list"><s:text name="nights"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
          <select name="booking.room.id" id="sel_rooms_list" size="1">
           	<option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
          </select>
        </div>
      </div>
      <div class="c33l">
        <div class="subcl type-select">
          <label for="sel_rooms_list"><s:text name="persons"/><sup title="<s:text name="thisFileMandatory"/>.">*</sup></label>
          <select name="booking.room.id" id="sel_rooms_list" size="1">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
          </select>
        </div>
      </div>
      <div class="c33l">
        <div class="type-button">
          <button class="btn_next"><s:text name="next"/></button>
        </div>
      </div>
    </form>
  </div>