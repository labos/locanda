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
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="layout/header_menu.jsp" />
<script>
Entity = {name: "guest", 
		model: function(options){ return new Guest( options );},
		collection: function(options){ return new Guest( options );},
		editView: null,
		idStructure : <s:property value="#session.user.structure.id"/>
		};
</script>
  <div id="main">
    <!-- begin: #col1 - first float column -->
    <div id="col1" role="complementary">
      <div id="col1_content" class="clearfix"></div>
    </div><!-- end: #col1 -->
    <!-- begin: #col3 static column -->
    <div id="col3" role="main">
      <div id="col3_content" class="clearfix">
      <form action="findAllGuestsFiltered.action">
        <div class="header_section">
          <span class="name_section"><s:text name="guests" /></span>   
          <div class="right type-text">
            <input type="text" name="term" class="txt_guest_search" /><button class="btn_g_search"><s:text name="search"/></button>
          </div>  
        </div>
      </form>
      <div>
        <button class="btn_add_form"><s:text name="addNew" /></button>
      </div>
        <div class="yform hideform" id="newExtraForm">
       	  <jsp:include page="contents/guest_form.jsp" />
        </div>
          
        <s:iterator value="guests" var="eachGuest" >  
          <div>
		    <form method="post" action="deleteGuest.action" class="yform json full" role="application">
              <fieldset>
                <input type="hidden" name="redirect_form" value="findAllGuests.action?sect=guests" />
                <input type="hidden" name="id" value="<s:property value="id"/>"/>
                <legend class="title_season">
                	<a href="goUpdateGuest.action?sect=guests&id=<s:property value="#eachGuest.id"/>"><s:property value="#eachGuest.firstName"/> <s:property value="#eachGuest.lastName"/></a>
                	<a href="goUpdateGuest.action?sect=guests&id=<s:property value="#eachGuest.id"/>"><img src="images/sign-up-icon.png" alt="edit" /></a>
                </legend>
		    	<div class="subcolumns">
      		 	  <div class="c40l">
                    <div class="type_rooms">
					  <ul>
				        <li><b><s:text name="address"/>:</b> <s:property value="#eachGuest.address"/> - <s:property value="#eachGuest.zipCode"/> <s:property value="#eachGuest.country"/></li>
				        <li><b><s:text name="phone"/>:</b> <s:property value="#eachGuest.phone"/></li>
				        <li><b><s:text name="email"/>:</b> <s:property value="#eachGuest.email"/></li>
				        <li><b><s:text name="notes"/>:</b> <s:property value="#eachGuest.notes"/></li>
				      </ul>
                    </div>                  
                  </div>
                </div>
             	<div class="type-button">
             	  <button class="btn_delete"><s:text name="delete" /></button>
                </div>
           	  </fieldset>
            </form>        
		  </div>
		</s:iterator>
		       
      </div>    
<jsp:include page="layout/footer.jsp" />   