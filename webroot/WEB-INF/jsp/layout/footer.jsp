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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

				<div id="ie_clearing">&nbsp;</div>
				<!-- End: IE Column Clearing -->

				<!-- begin: #footer -->
				<div id="footer" role="contentinfo">
					<img src="images/labos.png" id="labos" alt="Laboratorio Open Source" class="left" height="7%" width="20%"/>
					<img src="images/sardegna_ricerche.png" alt="Sardegna Ricerche" class="left" height="7%" width="7%" />
					<img src="images/european_union.png" id="eu" alt="European Union" class="right" height="7%" width="7%" />
					<img src="images/repubblica.png" alt="Repubblica Italiana" class="right" height="7%" width="7%"/>
					<img src="images/regione_sardegna.png" alt="Regione Autonoma della Sardegna" class="right" height="7%" width=7%/>
					<span class="center"><s:text name="titleExtended"/><br/><br/><br/></span>
				</div>
				<!-- end: #footer -->
			</div>
			<!-- end: #col3 -->
		</div>
		<!-- end: #main -->
	</div>
</div>

<script type='text/javascript' src='js/lib/jquery.min.js'></script>
<!--<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js'></script>-->
<script type='text/javascript' src='js/lib/jquery-ui-1.8.9.custom.min.js'></script>
<script type='text/javascript' src="js/lib/jquerymx-1.0.custom.js"></script>
<script type='text/javascript' src='js/lib/ftod.js'></script>
<script type="text/javascript" src="js/lib/jstree/jquery.jstree.js"></script>
<script type='text/javascript' src='js/lib/jquery.form.js'></script>
<script type='text/javascript' src="js/lib/jquery.i18n.js"></script>
<script type='text/javascript' src="js/lib/underscore-min.js"></script>
<script type='text/javascript' src="js/lib/backbone.js"></script>
<script type='text/javascript' src="js/lib/mustache.js"></script>
<script type='text/javascript' src='js/lib/jquery.overlay.min.js'></script>
<script type='text/javascript' src='js/lib/jquery.colorbox.js'></script>
<script type='text/javascript' src='js/lib/jquery.slimscroll.min.js'></script>
<%
//Locale locale = (locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
String lang ="en";
Locale locale = ActionContext.getContext().getLocale();
if (locale != null){

	lang = locale.getLanguage();
}
%>

<script type='text/javascript' src='js/lang/jquery.<s:property value="#request.locale.getLanguage()" />.json'></script>
<script>
I18NSettings = {};
I18NSettings.datePattern = '<s:property value="#session.datePattern"/>'.replace('yyyy', 'yy').toLowerCase();
//to avoid undefined warning on pre-login phase...
if (typeof I18NSettings.datePattern === 'undefined') {
    I18NSettings.datePattern = "dd/mm/yy";
}
//$._.setLocale('<s:property value="#request.locale" />');
$._.setLocale('<s:property value="#request.locale.getLanguage()" />');
I18NSettings.language = '<s:property value="#request.locale.getLanguage()" />';

	$(document).ready(function () { <%
    	//code for menu tabs activation
    	String dPageDefault = "planner";
   		String dPage = request.getParameter("sect");
    	dPage = (dPage == null) ? dPageDefault : dPage;
    	out.println("\n var section= \'" + dPage + "\';");
    	%>
    	var text_tab = $("#" + section).children("a").hide().text();
    	$("#" + section).addClass("active").prepend("<strong>" + text_tab + "</strong>");
	});
</script>
<script type='text/javascript' src='js/lib/jquery.validate.min.js'></script>
<script type='text/javascript' src='js/lib/jquery.metadata.js'></script>
<s:if test="#request.locale.getLanguage() != 'en'">
	<script type="text/javascript" src="js/lang/messages_<s:property value="#request.locale.getLanguage()" />.js"></script>
</s:if>
<script type='text/javascript' src="js/lib/steal/steal.js?loader.js"></script>
<script type='text/javascript' src='js/lib/jquery.jgrowl_minimized.js'></script>
<!-- full skiplink functionality in webkit browsers -->
<script src="yaml/core/js/yaml-focusfix.js" type="text/javascript"></script>
<!--[if IE]>
<style>
.wc-scrollable-grid {
	width: 99% !important;
}
</style>
<![endif]-->

</body>
</html>