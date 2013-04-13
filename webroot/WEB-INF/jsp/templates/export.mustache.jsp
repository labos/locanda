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
  

<script id="export-exportDateList-template" type="text/x-handlebars-template">
	{{#populated}}
		<option value=""><s:text name="selectDate"/></option>
	{{/populated}}
	{{#dates}}
		<option value="{{ts}}">{{date}}</option>
	{{/dates}}
	{{^dates}}
		<option value=""><s:text name="dateAbsent"/></option>
	{{/dates}}
</script>

<script id="export-checkResult-template" type="text/x-handlebars-template">
	{{#bookings}}
		<div class="clear">
			<a href="#"><s:text name="booking"/>: {{id}}</a>
		</div>
	{{/bookings}}
</script>

<script id="export-template" type="text/x-handlebars-template">
	<div class="float_left borders-light padding-light">
	<h1><s:text name="exportSired"/></h1>
		<div class="clear">
			<input type="radio" name="exportType" class="exportType" value="0" checked="checked"/>
			<span>
				<s:text name="exportDateSelected"/>
			</span>
			<div class="clear">&nbsp;</div>
			<div class="clear">
				<select id="exportDateList" name="exportDateList"></select>
			</div>
		</div>
		<div class="clear">&nbsp;</div>
		<div class="clear">
			<input type="radio" name="exportType" class="exportType" value="1"/>
			<span>
				<s:text name="exportForceDateSelected"/>
			</span>
			<div class="clear">&nbsp;</div>
			<div class="clear">
				<input type="text" id="dateExport" name="dateExport" value="" class="datepicker required hasDatepicker" readonly="readonly">
			</div>
		</div>
	</div>

<!-- questura export -->
	<div class="float_right borders-light padding-light">
	<h1><s:text name="exportQuestura"/></h1>
		<div class="clear">
			<input type="radio" name="exportType" class="exportTypeQuestura" value="0" checked="checked"/>
			<span>
				<s:text name="exportDateSelected"/>
			</span>
			<div class="clear">&nbsp;</div>
			<div class="clear">
				<select id="exportDateListQuestura" name="exportDateList"></select>
			</div>
		</div>
		<div class="clear">&nbsp;</div>
		<div class="clear">
			<input type="radio" name="exportType" class="exportType" value="1"/>
			<span>
				<s:text name="exportForceDateSelected"/>
			</span>
			<div class="clear">&nbsp;</div>
			<div class="clear">
				<input type="text" id="dateExportQuestura" name="dateExportQuestura" value="" class="datepicker required hasDatepicker" readonly="readonly">
			</div>
		</div>
	</div>
<!-- end questura export -->

	<div class="clear none" id="dateInfo">
		<span><s:text name="exportFrom"/></span>
		<b>	<span id="startexporttime"></span>
			<span>--</span>
			<span id="startexportdate"></span>
		</b>
		<span><s:text name="to"/></span>
		<b>
			<span id="endexporttime"></span>
			<span>--</span>
			<span id="endexportdate"></span>
		</b>
	</div>
	<div class="clear">&nbsp;</div>
	<div id="checkResult" class="borders-light"></div>
	<div class="clear">&nbsp;</div>
	<div class="clear">
		<div class="float_left" style="padding-right:40px;">
			<div class="clear">
				<button class="btn_check_sired">
					<s:text name="checkSired" />
				</button>
			</div>
			<div class="clear">&nbsp;</div>
			<div class="clear">
				<button class="btn_export_sired">
					<s:text name="exportSired" />
				</button>
			</div>
		</div>
		<div class="float_right" style="padding-left:40px;">
			<div class="clear">
				<button class="btn_check_questura">
					<s:text name="checkQuestura" />
				</button>
			</div>
			<div class="clear">&nbsp;</div>
			<div class="clear">
				<button class="btn_export_questura">
					<s:text name="exportQuestura" />
				</button>
			</div>
		</div>
	</div>
</script>