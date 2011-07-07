<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:include page="jsp/layout/header_menu.jsp" />
  <div id="main">
    <!-- begin: #col1 - first float column -->
    <div id="col1" role="complementary">
      <div id="col1_content" class="clearfix"></div>
    </div><!-- end: #col1 -->
    <!-- begin: #col3 static column -->
    <div id="col3" role="main">
      <div id="col3_content" class="clearfix">
        <div class="header_section">
          <span class="name_section"><s:text name="manageConventions"/></span>
      	</div>
        <div>
          <button class="btn_add_form"><s:text name="addNew" /></button>
        </div>
        <div class="yform hideform">
       	  <jsp:include page="jsp/contents/convention_form.jsp" />
        </div>
          
        <s:iterator value="conventions" var="eachConvention" >
          <div>
		    <form method="post" action="deleteConvention.action" class="yform json full" role="application">
              <fieldset>
                <input type="hidden" name="redirect_form" value="findAllConventions.action?sect=settings"/>
                 <input type="hidden" name="convention.id" value="<s:property value="#eachConvention.id"/>"/>
                <legend class="title_season">
                	<a href="goUpdateConvention.action?sect=settings&convention.id=<s:property value="#eachConvention.id"/>"><s:property value="#eachConvention.name"/></a>
                	<a href="goUpdateConvention.action?sect=settings&convention.id=<s:property value="#eachConvention.id"/>"><img src="images/sign-up-icon.png" alt="edit"/></a>
                </legend>
		    	<div class="subcolumns">
      		 	  <div class="c40l">
                    <div class="type-text">
					  <ul>
				        <li><b><s:text name="name"/>:</b> <s:property value="#eachConvention.name"/></li>
				        <li><b><s:text name="code"/>:</b> <s:property value="#eachConvention.activationCode"/></li>
				        <li><b><s:text name="description"/>:</b> <s:property value="#eachConvention.description"/></li>
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
		    
      </div>  
<jsp:include page="jsp/layout/footer.jsp" />   