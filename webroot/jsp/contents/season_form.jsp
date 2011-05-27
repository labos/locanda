<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<form method="post" action="saveUpdateSeason.action" class="yform json"
	role="application"><input type="hidden" name="redirect_form"
	value="findAllSeasons.action?sect=settings" /> <input type="hidden"
	name="season.id" value="<s:property value="season.id"/>" />
	<fieldset><legend>Seasons</legend>
		<div class="subcolumns"><a href="#top_anchor" class="add_period"
			title="add period">Add New Period</a></div>
		<div class="subcolumns">
		<div class="c33l">
		<div class="subcl type-text"><span>Name:</span> <input type="text"
			name="season.name" class="required"
			value="<s:property value="season.name"/>" style="display: inline;" /></div>
		</div>
		</div>
		<div class="subcolumns period">
		<div class="c25l">
		<div class="subcl type-text"><span>From:</span> <input type="text"
			class="datepicker required" name="periods[0].startDate"
			value="<s:date name="season.periods[0].startDate" format="%{#session.datePattern}" />"
			style="display: inline;" /></div>
		</div>
		<div class="c20l">
		<div class="subcl type-text"><span>To:</span> <input type="text"
			class="datepicker required" name="periods[0].endDate"
			value="<s:date name="season.periods[0].endDate" format="%{#session.datePattern}" />"
			style="display: inline;" /></div>
		</div>
		<div class="c10l"><label>&nbsp;</label> <input type="hidden"
			class="idPeriod" name="periods[0].id"
			value="<s:property value="season.periods[0].id"/>" /> <a
			href="#seas_anchor_<s:property value="season.periods[0].id"/>"
			class="erase_period" title="erase">Delete Period</a></div>
		</div>
		<!-- ITERATION OF PERIODS  -->
		<s:iterator value="season.periods"
			var="eachPeriod" status="periodStatus">
			<s:if test="#periodStatus.index > 0">
				<div class="subcolumns period">
				<div class="c25l">
				<div class="subcl type-text"><span>From:</span> <input
					type="text" class="datepicker required"
					name="periods[<s:property value="#periodStatus.index"/>].startDate"
					value="<s:date name="#eachPeriod.startDate" format="%{#session.datePattern}" />"
					style="display: inline;" /></div>
				</div>
				<div class="c20l">
				<div class="subcl type-text"><span>To:</span> <input type="text"
					class="datepicker required"
					name="periods[<s:property value="#periodStatus.index"/>].endDate"
					value="<s:date name="#eachPeriod.endDate" format="%{#session.datePattern}" />"
					style="display: inline;" /></div>
				</div>
				<div class="c10l"><label>&nbsp;</label> <s:if
					test="#periodStatus.index > 0">
					<input type="hidden" class="idPeriod"
						name="periods[<s:property value="#periodStatus.index"/>].id"
						value="<s:property value="#eachPeriod.id"/>" />
					<a href="#seas_anchor_<s:property value="#eachSeason.id"/>"
						class="erase_period" title="erase">Delete Period</a>
				</s:if></div>
				</div>
		
			</s:if>
		
		</s:iterator>
		<div class="type-button">
		<button class="btn_save">SAVE</button>
		<button class="btn_reset btn_cancel_form">CANCEL</button>
		</div>
		<a name="top_anchor"></a>
	
	</fieldset>
</form>
<!--  Hidden new season  -->
<div class="subcolumns period" id="to_add_period" style="display: none;">
<div class="c25l">
<div class="subcl type-text"><span>From:</span> <input type="text"
	class="adddatepicker required" name="periods[__PVALUE__].startDate"
	value="" style="display: inline;" /></div>
</div>
<div class="c20l">
<div class="subcl type-text"><span>To:</span> <input type="text"
	class="adddatepicker required" name="periods[__PVALUE__].endDate"
	value="" style="display: inline;" /></div>
</div>
<div class="c10l"><label>&nbsp;</label> <a href="#"
	class="erase_period" title="erase">Delete Period</a></div>
</div>
<!--  End Hidden new season  -->

