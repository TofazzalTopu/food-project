
<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'demandOrderApprovalWorkflow.label', default: 'DemandOrderApprovalWorkflow')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.id.label"
                                                         default="Id"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: demandOrderApprovalWorkflow, field: "id")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.workflow.label"
                                                         default="Workflow"/></td>
                
                <td valign="top" class="value"><g:link controller="workflow"
                                                       action="show"
                                                       id="${demandOrderApprovalWorkflow?.workflow?.id}">${demandOrderApprovalWorkflow?.workflow?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.isApprove.label"
                                                         default="Is Approve"/></td>
                
                <td valign="top" class="value"><g:formatBoolean boolean="${demandOrderApprovalWorkflow?.isApprove}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.isReject.label"
                                                         default="Is Reject"/></td>
                
                <td valign="top" class="value"><g:formatBoolean boolean="${demandOrderApprovalWorkflow?.isReject}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.userApprovedReject.label"
                                                         default="User Approved Reject"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${demandOrderApprovalWorkflow?.userApprovedReject?.id}">${demandOrderApprovalWorkflow?.userApprovedReject?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.dateApprovedReject.label"
                                                         default="Date Approved Reject"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${demandOrderApprovalWorkflow?.dateApprovedReject}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.userCreated.label"
                                                         default="User Created"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${demandOrderApprovalWorkflow?.userCreated?.id}">${demandOrderApprovalWorkflow?.userCreated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.userUpdated.label"
                                                         default="User Updated"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${demandOrderApprovalWorkflow?.userUpdated?.id}">${demandOrderApprovalWorkflow?.userUpdated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.dateCreated.label"
                                                         default="Date Created"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${demandOrderApprovalWorkflow?.dateCreated}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.lastUpdated.label"
                                                         default="Last Updated"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${demandOrderApprovalWorkflow?.lastUpdated}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="demandOrderApprovalWorkflow.enterpriseConfiguration.label"
                                                         default="Enterprise Configuration"/></td>
                
                <td valign="top" class="value"><g:link controller="enterpriseConfiguration"
                                                       action="show"
                                                       id="${demandOrderApprovalWorkflow?.enterpriseConfiguration?.id}">${demandOrderApprovalWorkflow?.enterpriseConfiguration?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${demandOrderApprovalWorkflow?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
