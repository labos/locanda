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
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script>
	Entity = {
		name : "guest",
		model : function(options) {
			return new Guest(options);
		},
		collection : function(options) {
			return new Guests( {}, options );
		},
		editView : function(options) {
			return new EditGuestView(options);
		},
		isDialog:false,
		idStructure : <s:property value="#session.user.structure.id"/>
	};
</script>
<jsp:include page="layout/header_menu.jsp" />
<link rel='stylesheet' type='text/css'
	href='css/screen/basemod_2col_advanced.css' />

<div id="main">
	<!-- begin: #col1 - first float column -->
	<div id="col1" role="complementary">
		<div class="clearfix" id="col1_content">
			<div>
				<button class="btn_add_form">
					<s:text name="addNew" />
				</button>
			</div>
			<div class="subcolumns" id="row-edit-container" style="padding-bottom:40px"></div>
			<div id="bookings"></div>
		</div>
	</div>
	<!-- end: #col1 -->

	<!-- begin: #col3 static column -->
	<div id="col3" role="main">
		<div class="clearfix" id="col3_content">
			<h2>
				<s:text name="guests" />
			</h2>
			<div id="toolbar-container"></div>
			<div id="main-app">
				<div id="nav-top"></div>
				<div id="row-list" class="back"></div>
				<div id="nav-bottom"></div>
			</div>
		</div>
		<div id="ie_clearing">&nbsp;</div>
		<div id="event_edit_container"><div style="text-align: center"></div></div>
		<!-- End: IE Column Clearing -->
	</div>
	<!-- end: #col3 -->
</div>

<jsp:include page="templates/guest.mustache.jsp"/>
<jsp:include page="templates/bookingPreview.mustache.jsp"/>
<jsp:include page="layout/footer.jsp"/>