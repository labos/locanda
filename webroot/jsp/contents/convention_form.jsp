<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

		<form method="post" action="saveUpdateConvention.action" class="yform json full" role="application">
		  <fieldset>
          	<legend>Convention details</legend>
          	  <div class="c50l">
              	<input type="hidden" name="redirect_form" value="findAllConventions.action?sect=settins" />
                <input type="hidden" name="convention.id" value="<s:property value="convention.id"/>"/>
                <div class="c50l">
                  <div class="type-text">	
                  	<label for="conventionFormName">Convention name <sup title="This field is mandatory.">*</sup></label>
                	<input type="text" class="required" name="convention.name" id="conventionFormName" value="<s:property value="convention.name"/>" aria-required="true"/>
                  </div>
                  <div class="type-text">           
       				<label for="conventionFormCode">Convention code <sup title="This field is mandatory.">*</sup></label>
                    <input type="text" class="required" name="convention.activationCode" id="conventionFormCode" value="<s:property value="convention.activationCode"/>" aria-required="true"/>
      		      </div> 
				  <div class="type-text">	
                  	<label for="conventionFormDescr">Convention description</label>
					<textarea name="convention.description" id="conventionFormDescr"><s:property value="convention.description"/></textarea>		 
                  </div>
                  <div class="type-button">
                  	<button class="btn_save">SAVE</button>
                    <button class="btn_reset btn_cancel_form">CANCEL</button>
                  </div>	
                </div>
              </div>
            </fieldset>   
          </form>