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

		<form method="post" action="saveUpdateConvention.action" class="yform json full" role="application">
		  <fieldset>
          	<legend><s:text name="conventionDetails" /></legend>
          	  <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllConventions.action?sect=settings" />
                <input type="hidden" name="convention.id" value="<s:property value="convention.id"/>"/>
                <div class="c50l">
                  <div class="type-text">	
                  	<label for="conventionFormName"><s:text name="name"/><sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="convention.name" id="conventionFormName" value="<s:property value="convention.name"/>" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="conventionFormCode"><s:text name="code"/><sup title="This field is mandatory.">*</sup></label>
                    <input type="text" class="required" name="convention.activationCode" id="conventionFormCode" value="<s:property value="convention.activationCode"/>" aria-required="true"/>
      		      </div> 
				  <div class="type-text">	
                  	<label for="conventionFormDescr"><s:text name="description"/></label>
					<textarea name="convention.description" id="conventionFormDescr"><s:property value="convention.description"/></textarea>		 
                  </div>
                  <div class="type-button">
                  	<button class="btn_save"><s:text name="save"/></button>
                    <button class="btn_reset btn_cancel_form"><s:text name="cancel"/></button>
                  </div>	
                </div>
              </div>
            </fieldset>   
          </form>