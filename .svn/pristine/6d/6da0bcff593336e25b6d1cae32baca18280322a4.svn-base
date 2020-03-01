
<%@ page import="com.bits.bdfp.inventory.workflow.WorkflowUserMapping" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'workflowUserMapping.label', default: 'WorkflowUserMapping')}"/>
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
                                  title="${message(code: 'workflowUserMapping.id.label', default: 'Id')}"/>
                
                <th><g:message code="workflowUserMapping.workflow.label" default="Workflow"/></th>
                
                <th><g:message code="workflowUserMapping.applicationUser.label" default="Application User"/></th>
                
                <g:sortableColumn property="isActive"
                                  title="${message(code: 'workflowUserMapping.isActive.label', default: 'Is Active')}"/>
                
                <th><g:message code="workflowUserMapping.userCreated.label" default="User Created"/></th>
                
                <th><g:message code="workflowUserMapping.userUpdated.label" default="User Updated"/></th>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${workflowUserMappingList}" status="i" var="workflowUserMapping">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${workflowUserMapping.id}">${fieldValue(bean: workflowUserMapping, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: workflowUserMapping, field: "workflow")}</td>
                    
                    <td>${fieldValue(bean: workflowUserMapping, field: "applicationUser")}</td>
                    
                    <td><g:formatBoolean boolean="${workflowUserMapping.isActive}"/></td>
                    
                    <td>${fieldValue(bean: workflowUserMapping, field: "userCreated")}</td>
                    
                    <td>${fieldValue(bean: workflowUserMapping, field: "userUpdated")}</td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${workflowUserMappingTotal}"/>
    </div>
</div>
</body>
</html>
