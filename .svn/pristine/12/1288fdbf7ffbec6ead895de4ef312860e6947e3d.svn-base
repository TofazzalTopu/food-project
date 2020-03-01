%{--
  - ****************************************************************
  - Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
  - DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
  - This software is the confidential and proprietary information of
  - Documentatm (TM) Limited ("Confidential Information").
  - You shall not disclose such Confidential Information and shall use
  - it only in accordance with the terms of the license agreement
  - you entered into with Documentatm (TM) Limited.
  - *****************************************************************
  --}%

<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 5/10/11
  Time: 1:39:02 AM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/html"  id="request-map-template">
  <g:render plugin="docuaccesscontrol" template="tabs/requestmap"  model="[]"/>
</script>

<table border="1" id="request-map-table" class="simple-table-css">
<tr>
  <td ><g:message code="Feature Name" default="Feature Name"/></td>
  <td ><g:message code="Feature Action" default="Feature Action"/></td>
</tr>


     <g:render plugin="docuaccesscontrol" template="tabs/requestmap"  model="['featureAction':featureAction,editMode:editMode]"/>

  %{--<g:each var="daycloserule" in="${dayCloseAccountRestrictionList}" status="i">--}%
    %{--<g:render template='tabs/daycloseaccrule' model="['dayCloseAccountsRestrictionInfo':daycloserule,'i':i]"/>--}%

  %{--</g:each>--}%
</table>

<div style="margin-top: 10px;">
    <a href="javascript:void(0)" onclick="selectAll()">Select All</a>
    &nbsp;|&nbsp;
    <a href="javascript:void(0)" onclick="deSelectAll()">Un select All</a>
</div>
<br/>

