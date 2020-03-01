
<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'demandOrderApprovalWorkflow.label', default: 'DemandOrderApprovalWorkflow')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>
                
                <g:sortableColumn property="id"
                                  title="${message(code: 'demandOrderApprovalWorkflow.id.label', default: 'Id')}"/>
                
                <th><g:message code="demandOrderApprovalWorkflow.workflow.label" default="Workflow"/></th>
                
                <g:sortableColumn property="isApprove"
                                  title="${message(code: 'demandOrderApprovalWorkflow.isApprove.label', default: 'Is Approve')}"/>
                
                <g:sortableColumn property="isReject"
                                  title="${message(code: 'demandOrderApprovalWorkflow.isReject.label', default: 'Is Reject')}"/>
                
                <th><g:message code="demandOrderApprovalWorkflow.userApprovedReject.label" default="User Approved Reject"/></th>
                
                <g:sortableColumn property="dateApprovedReject"
                                  title="${message(code: 'demandOrderApprovalWorkflow.dateApprovedReject.label', default: 'Date Approved Reject')}"/>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${demandOrderApprovalWorkflowList}" status="i" var="demandOrderApprovalWorkflow">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${demandOrderApprovalWorkflow.id}">${fieldValue(bean: demandOrderApprovalWorkflow, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: demandOrderApprovalWorkflow, field: "workflow")}</td>
                    
                    <td><g:formatBoolean boolean="${demandOrderApprovalWorkflow.isApprove}"/></td>
                    
                    <td><g:formatBoolean boolean="${demandOrderApprovalWorkflow.isReject}"/></td>
                    
                    <td>${fieldValue(bean: demandOrderApprovalWorkflow, field: "userApprovedReject")}</td>
                    
                    <td><g:formatDate date="${demandOrderApprovalWorkflow.dateApprovedReject}"/></td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${demandOrderApprovalWorkflowTotal}"/>
    </div>
</div>
</body>
</html>
