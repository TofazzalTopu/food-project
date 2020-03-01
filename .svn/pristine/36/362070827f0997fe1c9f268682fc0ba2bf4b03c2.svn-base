
<%@ page import="com.bits.bdfp.inventory.workflow.WorkflowUserMapping" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'workflowUserMapping.label', default: 'WorkflowUserMapping')}"/>
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
                <td valign="top" class="name"><g:message code="workflowUserMapping.id.label"
                                                         default="Id"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: workflowUserMapping, field: "id")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.workflow.label"
                                                         default="Workflow"/></td>
                
                <td valign="top" class="value"><g:link controller="workflow"
                                                       action="show"
                                                       id="${workflowUserMapping?.workflow?.id}">${workflowUserMapping?.workflow?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.applicationUser.label"
                                                         default="Application User"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${workflowUserMapping?.applicationUser?.id}">${workflowUserMapping?.applicationUser?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.isActive.label"
                                                         default="Is Active"/></td>
                
                <td valign="top" class="value"><g:formatBoolean boolean="${workflowUserMapping?.isActive}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.userCreated.label"
                                                         default="User Created"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${workflowUserMapping?.userCreated?.id}">${workflowUserMapping?.userCreated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.userUpdated.label"
                                                         default="User Updated"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${workflowUserMapping?.userUpdated?.id}">${workflowUserMapping?.userUpdated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.dateCreated.label"
                                                         default="Date Created"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${workflowUserMapping?.dateCreated}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="workflowUserMapping.dateUpdated.label"
                                                         default="Date Updated"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${workflowUserMapping?.dateUpdated}"/></td>
                
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${workflowUserMapping?.id}"/>
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
