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

<jsp:include page="layout/header_menu.jsp" />
<script>
Entity = {name: "extra", 
		model: function(options){ return new Extra( options );},
		collection: function(options){ return new Extra( options );},
		editView: null,
		idStructure : <s:property value="#session.user.structure.id"/>
		};
</script>
      <div id="main">
        <!-- begin: #col1 - first float column -->
        <div id="col1" role="complementary">
          <div id="col1_content" class="clearfix">
          </div>
        </div><!-- end: #col1 -->
        <!-- begin: #col3 static column -->
        <div id="col3" role="main">
          <div id="col3_content" class="clearfix">
          	<div class="header_section">
          	  <span class="name_section"><s:text name="extras"/></span>
      	 	</div>
      	
      	    <div>
              <button class="btn_add_form btn_addExtra"><s:text name="addNew"/></button>
            </div>
            <div class="yform hideform" id="newExtraForm">
          	  <jsp:include page="contents/extra_form.jsp" />
            </div>
            
            <s:iterator value="extras" var="eachExtra" >
			
			<div>
			  <form method="post" action="deleteExtra.action" class="yform json full" id="extraForm" role="application">
			  	<fieldset>
				  <input type="hidden" name="redirect_form" value="findAllExtras.action?sect=accomodation" />
                  <input type="hidden" name="extra.id" value="<s:property value="#eachExtra.id"/>"/> 
                  <legend class="title_season">
              		<a href="goUpdateExtra.action?sect=accomodation&extra.id=<s:property value="#eachExtra.id"/>"><s:property value="#eachExtra.name"/></a>
              		<a href="goUpdateExtra.action?sect=accomodation&extra.id=<s:property value="#eachExtra.id"/>"><img src="images/sign-up-icon.png" alt="edit"/></a>
              	  </legend>
                  <div class="subcolumns">
      		 		<div class="c40l">
                      <div class="type-text">
                      	<ul>
					  	  <li><b><s:text name="extraPriceType"/>:</b> <s:property value="#eachExtra.timePriceType"/>/<s:property value="#eachExtra.resourcePriceType"/></li>
                      	  <li><b><s:text name="description"/>:</b><textarea readonly="readonly"><s:property value="#eachExtra.description"/></textarea></li>
                      	</ul>
                      </div>                  
               	    </div>
             	  </div>
                  <div class="type-button">
             	  	<button class="btn_delete"><s:text name="delete"/></button>
             	  </div>	
             	</fieldset>
              </form>
            </div>  
            
            </s:iterator>
               
		  </div><!-- end: #col3_content-->		  
<jsp:include page="layout/footer.jsp" />  
<script type='text/javascript' src="js/controllers/extra_controller.js"></script>   