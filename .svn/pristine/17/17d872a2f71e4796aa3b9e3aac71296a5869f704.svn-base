<%@ page import="com.docu.commons.DateUtil; org.codehaus.groovy.grails.commons.ApplicationHolder; com.docu.commons.CommonConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 6/11/11
  Time: 1:54 PM
  To change this template use File | Settings | File Templates.
--%>
%{--<title><g:message code="Application User List" default="Application User List"/></title>--}%
%{--<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>--}%
%{--<g:render template='/common/message'/>--}%
%{--<script type="text/javascript">--}%
  %{--$(document).ready(function() {--}%
    %{--var dataArr = ${gridFeed}--}%
            %{--jQuery("#application-user-Grid").jqGrid({--}%
              %{--data: dataArr,--}%
              %{--datatype: "local",--}%
              %{--colNames:[--}%
              %{--'ID',--}%
			  %{--'Username' ,--}%
			  %{--'Full Name',--}%
			  %{--'Enabled',--}%
			  %{--'Account Expired',--}%
			  %{--'Account Locked',--}%
			  %{--'Password Expired',--}%
			  %{--'Date Created',--}%
			  %{--'Assigned role'--}%
          %{--],--}%
          %{--colModel:[--}%
              %{--{name:'id',index:'id', width:0, hidden:true},--}%

			  %{--{name:'username', index:'username', width:150, sortable:true, align:'left' , formatter: 'showlink', formatoptions: {baseLinkUrl: 'show'}},--}%
			  %{--{name:'fullname', index:'fullname', width:150, sortable:true, align:'left'},--}%
			  %{--{name:'enabled', index:'enabled', width:150, sortable:true, align:'left'},--}%
			  %{--{name:'accountexpired', index:'accountexpired', width:150, sortable:true, align:'left'},--}%
			  %{--{name:'accountlocked', index:'accountlocked', width:150, sortable:true, align:'left'},--}%
			  %{--{name:'passwordexpired', index:'passwordexpired', width:150, sortable:true, align:'left'},--}%
			  %{--{name:'passwordexpired', index:'passwordexpired', width:150, sortable:true, align:'left'},--}%
			  %{--{name:'assignedrole', index:'assignedrole', width:250, sortable:true, align:'left'}--}%
          %{--],--}%
              %{--rowNum:10,--}%
              %{--rowList:[10,20,30],--}%
              %{--pager: '#jqgrid-pager',--}%
              %{--sortname: 'id',--}%
              %{--altRows:true,--}%
              %{--viewrecords: true,--}%
              %{--sortorder: "desc",--}%
              %{--width:850,--}%
              %{--height:200,--}%
              %{--scrollOffset: 0,--}%
              %{--caption:"Application User List",--}%
              %{--loadComplete: function() {--}%

              %{--}--}%
            %{--});--}%

    %{--$("#application-user-Grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false,refresh:false,view:false});--}%

  %{--});--}%
%{--</script>--}%

%{--<div class="blue_grid">--}%
  %{--<table id="application-user-Grid"></table>--}%
  %{--<div id="jqgrid-pager"></div>--}%
%{--</div>--}%


<title>Application User List</title>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<g:render template='/common/message'/>
<script type="text/javascript">
    $(document).ready(function() {
        $('#applicationUserList').dataTable({
            "sPaginationType":"full_numbers",
            "bPaginate":true,
            //"iDisplayLength": 20,
            "bJQueryUI":true
        });

    });
</script>


<div class="main_container">
    <h1>Application User List</h1>
    <div class="clear" style="height:5px;"></div>
    <form id="user-operation-config-form" method="post" action="">
        <table id="applicationUserList" border="0" cellpadding="0" cellspacing="0" class="pretty borderTop borderBottom bodColDark">
            <thead>
            <tr>
                <th>User Name</th>
                <th>Full Name</th>
                <th>Enabled</th>
                <th>Account Locked</th>
                <th>Account Expired</th>
                <th>Password Expired</th>
                <th>Date Created</th>
                <th>Assigned role</th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${gridFeed}" var="row" status="i">
                <g:if test='${i % 2 == 0}'>
                    <tr class="even userRow">
                </g:if>
                <g:else>
                    <tr class="odd userRow">
                </g:else>

                <td><a href="${request.contextPath}/${params.controller}/show?id=${row.id}">${row.username}<g:hiddenField name="id" id="id" class="id" value="${row.id}"/></a> </td>
                <td>${row.fullname}</td>
                <td style="text-align: center">${row.enabled}</td>
                <td style="text-align: center">${row.accountlocked}</td>
                <td style="text-align: center">${row.accountexpired}</td>
                <td style="text-align: center">${row.passwordexpired}</td>
                <td style="text-align: center">${DateUtil.getDateFormatAsString(row.datecreated)}</td>
                <td style="text-align: center">${row.assignedrole}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </form>
</div>