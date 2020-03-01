

<%@ page import="com.bits.bdfp.inventory.workflow.WorkflowUserMapping" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'workflowUserMapping.label', default: 'WorkflowUserMapping')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
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
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${workflowUserMapping}">
        <div class="errors">
            <g:renderErrors bean="${workflowUserMapping}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post" >
    <g:hiddenField name="id" value="${workflowUserMapping?.id}"/>
    <g:hiddenField name="version" value="${workflowUserMapping?.version}"/>
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="workflow"><g:message code="workflowUserMapping.workflow.label"
                                                      default="Workflow"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: workflowUserMapping, field: 'workflow', 'errors')}">
                    <g:select name="workflow.id" from="${com.bits.bdfp.inventory.workflow.Workflow.list()}" optionKey="id" value="${workflowUserMapping?.workflow?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="applicationUser"><g:message code="workflowUserMapping.applicationUser.label"
                                                      default="Application User"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: workflowUserMapping, field: 'applicationUser', 'errors')}">
                    <g:select name="applicationUser.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${workflowUserMapping?.applicationUser?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="isActive"><g:message code="workflowUserMapping.isActive.label"
                                                      default="Is Active"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: workflowUserMapping, field: 'isActive', 'errors')}">
                    <g:checkBox name="isActive" value="${workflowUserMapping?.isActive}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userCreated"><g:message code="workflowUserMapping.userCreated.label"
                                                      default="User Created"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: workflowUserMapping, field: 'userCreated', 'errors')}">
                    <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${workflowUserMapping?.userCreated?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userUpdated"><g:message code="workflowUserMapping.userUpdated.label"
                                                      default="User Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: workflowUserMapping, field: 'userUpdated', 'errors')}">
                    <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${workflowUserMapping?.userUpdated?.id}" noSelection="['null': '']" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="dateUpdated"><g:message code="workflowUserMapping.dateUpdated.label"
                                                      default="Date Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: workflowUserMapping, field: 'dateUpdated', 'errors')}">
                    <g:datePicker name="dateUpdated" precision="day" value="${workflowUserMapping?.dateUpdated}" default="none" noSelection="['': '']" />
                </td>
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update"
                                             value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
        <span class="button"><g:actionSubmit class="delete" action="delete"
                                             value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                             onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
    </div>
    </g:form>
</div>
</body>
</html>
