<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%--
  Created by IntelliJ IDEA.
  User: kibria
  Date: 6/10/12
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>


<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title><g:message code="applicationUser.userControlPanel.title" default="User Control Panel"/></title>
<g:render template='/common/message' model="['domain':null]"/>

<script type="text/javascript">
    $(document).ready(function () {
        $('#user-control-panel-data-table').dataTable({
            "sPaginationType":"full_numbers",
            "bPaginate":false,
            "bJQueryUI":true
        });
    });
    function updateUserStatus(){
        var data = [];
        var tableRows = $(".userRow") ;
        $.each(tableRows, function(index, value) {
            var item = {};
            item['id'] = $(this).find("#id").val();

            var enabled = $(this).find(".enabledCheckBox");
            if($(enabled).is(':checked')){
                item['enabled'] = true;
            }else{
                item['enabled'] = false;
            }

            var locked =  $(this).find(".accountLockedCheckBox");
            if($(locked).is(':checked')){
                item['accountLocked'] = true;
            }else{
                item['accountLocked'] = false;
            }

            var expired =  $(this).find(".accountExpiredCheckBox");
            if($(expired).is(':checked')){
                item['accountExpired'] = true;
            }else{
                item['accountExpired'] = false;
            }

            var passwordExpired =  $(this).find(".passwordExpiredCheckBox");
            if($(passwordExpired).is(':checked')){
                item['passwordExpired'] = true;
            }else{
                item['passwordExpired'] = false;
            }
            data.push(item);

        });
        if (data.length) {
            SubmissionLoader.showTo();
            var url = "${request.contextPath}/${params.controller}/updateApplicationUserStatus";
            var params = {items: JSON.stringify(data)};
            DocuAjax.json(url, params, function(json) {
                SubmissionLoader.hideFrom();
                MessageRenderer.render(json);
            });
        }

    }

    function refreshPage(){
        location.href = "${request.contextPath}/${params.controller}/userControlPanel";
    }
</script>


<div class="main_container">
    <h1><g:message code="applicationUser.userControlPanel.title" default="User Operation Control Panel"/></h1>
    <div class="clear" style="height:5px;"></div>
    <form id="user-operation-config-form" method="post" action="">
        <table id="user-control-panel-data-table" border="0" cellpadding="0" cellspacing="0" class="pretty borderTop borderBottom bodColDark">
            <thead>
                <tr>
                    <th>User Name</th>
                    <th>Full Name</th>
                    <th>Enable</th>
                    <th>Locked</th>
                    <th>Expired</th>
                    <th>Password Expired</th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${tableData}" var="row" status="i">
                    <g:if test='${i%2 == 0}'>
                        <tr class="even userRow">
                    </g:if>
                    <g:else>
                        <tr class="odd userRow">
                    </g:else>

                        <td >${row.username}<g:hiddenField name="id" id="id" class="id" value="${row.id}"/> </td>
                        <td >${row.name}</td>
                        <td style="text-align: center" >
                            <g:if test="${row.enabled == 1}"><input type="checkbox" id="enabledCheckBox${row.id}" class="enabledCheckBox select" value="1" checked="true"></g:if>
                            <g:else><input type="checkbox" id="enabledCheckBox${row.id}" class="enabledCheckBox select"  value="0" > </g:else>
                        </td>

                        <td style="text-align: center" >
                            <g:if test="${row.accountLocked == 1}"><input type="checkbox" id="accountLockedCheckBox${row.id}" class="accountLockedCheckBox select" value="1" checked="true" > </g:if>
                            <g:else><input type="checkbox" id="accountLockedCheckBox${row.id}" class="accountLockedCheckBox select" value="0"> </g:else>
                        </td>

                        <td style="text-align: center" >
                            <g:if test="${row.accountExpired == 1}"><input type="checkbox" id="accountExpiredCheckBox${row.id}" class="accountExpiredCheckBox select" value="1" checked="true"></g:if>
                            <g:else><input type="checkbox" id="accountExpiredCheckBox${row.id}" class="accountExpiredCheckBox select" value="0" ></g:else>
                         </td>

                        <td style="text-align: center" >
                            <g:if test="${row.passwordExpired == 1}"><input type="checkbox" id="passwordExpiredCheckBox${row.id}" class="passwordExpiredCheckBox select" value="1" checked="true"> </g:if>
                            <g:else><input type="checkbox" id="passwordExpiredCheckBox${row.id}" class="passwordExpiredCheckBox select" value="0" > </g:else>
                        </td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </form>
    <div class="floatL pad_all10" style="font-size: 8px;">
        <a href="javascript:void(0)"
           onClick="JqGridManager.checkAllCheckbox('select')">Select All</a>
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.unCheckAllCheckbox('select')">Select None</a>

        <!-- Enable All/None -->
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.checkAllCheckbox('enabledCheckBox')">Enable All</a>
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.unCheckAllCheckbox('enabledCheckBox')">Enable None</a>

        <!-- Locked All/None -->
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.checkAllCheckbox('accountLockedCheckBox')">Locked All</a>
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.unCheckAllCheckbox('accountLockedCheckBox')">Locked None</a>

        <!-- Expired All/None -->
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.checkAllCheckbox('accountExpiredCheckBox')">Expired All</a>
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.unCheckAllCheckbox('accountExpiredCheckBox')">Expired None</a>

        <!-- Password Expired All/None -->
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.checkAllCheckbox('passwordExpiredCheckBox')">Password Expired All</a>
        &nbsp;|&nbsp;
        <a href="javascript:void(0)"
           onClick="JqGridManager.unCheckAllCheckbox('passwordExpiredCheckBox')">Password Expired None</a>
    </div>

    <div class="clear"></div>
    <div class="buttons">
        <input type="button" name="save" value="Update" onClick="updateUserStatus()" class="ui-button ui-widget ui-state-default ui-corner-all"/>
        <input type="button" name="reset" value="Reset" onclick="refreshPage()" class="ui-button ui-widget ui-state-default ui-corner-all"/>
    </div>
</div>



