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

<jsp:include page="jsp/layout/header.jsp" />
	
	<script id="some-template" type="text/x-handlebars-template">
  <table>
    <thead>
      <th>Username</th>
      <th>Real Name</th>
      <th>Email</th>
    </thead>
    <tbody>
      {{#users}}
        <tr>
          <td>{{username}}</td>
          <td>{{firstName}} {{lastName}}</td>
          <td>{{email}}</td>
        </tr>
      {{/users}}
    </tbody>
  </table>
</script>

<script type="text/javascript">
	var source   = $("#some-template").html();
  	var template = Handlebars.compile(source);
  	var data = { users: [
    	{username: "alan", firstName: "Alan", lastName: "Johnson", email: "alan@test.com" },
      	{username: "allison", firstName: "Allison", lastName: "House", email: "allison@test.com" },
      	{username: "ryan", firstName: "Ryan", lastName: "Carson", email: "ryan@test.com" }
    			]};
  	$("#content-placeholder").html(template(data));
</script>	


	
	    <h1>handlebars spike</h1>
        <ul>
			<li>bla.</li>
			<li>bla</li>
        </ul>
        <div id="content-placeholder">erase me</div>

<jsp:include page="jsp/layout/footer.jsp" />  