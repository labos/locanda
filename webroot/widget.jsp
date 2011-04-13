<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />

        <!-- begin: #col3 static column -->
<script>
	$(function() {
		$( "#largeDatepicker" ).datepicker();
	});
	</script>
	<style>
		#largeDatepicker {

			width:400px;
			height: 300px;
			}
	.ui-datepicker {

		width:100%;
		height: 100%;
		}
		
		.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default {
		
		height:30px;
			}
	</style>

            <div id='largeDatepicker'></div>

