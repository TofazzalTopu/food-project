

<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'demandOrderApprovalWorkflow.label', default: 'DemandOrderApprovalWorkflow')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${demandOrderApprovalWorkflow}">
        <div class="errors">
            <g:renderErrors bean="${demandOrderApprovalWorkflow}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save" >
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="workflow"><g:message code="demandOrderApprovalWorkflow.workflow.label"
                                                      default="Workflow"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'workflow', 'errors')}">
                    <g:select name="workflow.id" from="${com.bits.bdfp.inventory.workflow.Workflow.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.workflow?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="isApprove"><g:message code="demandOrderApprovalWorkflow.isApprove.label"
                                                      default="Is Approve"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'isApprove', 'errors')}">
                    <g:checkBox name="isApprove" value="${demandOrderApprovalWorkflow?.isApprove}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="isReject"><g:message code="demandOrderApprovalWorkflow.isReject.label"
                                                      default="Is Reject"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'isReject', 'errors')}">
                    <g:checkBox name="isReject" value="${demandOrderApprovalWorkflow?.isReject}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userApprovedReject"><g:message code="demandOrderApprovalWorkflow.userApprovedReject.label"
                                                      default="User Approved Reject"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'userApprovedReject', 'errors')}">
                    <g:select name="userApprovedReject.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.userApprovedReject?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="dateApprovedReject"><g:message code="demandOrderApprovalWorkflow.dateApprovedReject.label"
                                                      default="Date Approved Reject"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'dateApprovedReject', 'errors')}">
                    <g:datePicker name="dateApprovedReject" precision="day" value="${demandOrderApprovalWorkflow?.dateApprovedReject}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userCreated"><g:message code="demandOrderApprovalWorkflow.userCreated.label"
                                                      default="User Created"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'userCreated', 'errors')}">
                    <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.userCreated?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userUpdated"><g:message code="demandOrderApprovalWorkflow.userUpdated.label"
                                                      default="User Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'userUpdated', 'errors')}">
                    <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.userUpdated?.id}" noSelection="['null': '']" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="enterpriseConfiguration"><g:message code="demandOrderApprovalWorkflow.enterpriseConfiguration.label"
                                                      default="Enterprise Configuration"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: demandOrderApprovalWorkflow, field: 'enterpriseConfiguration', 'errors')}">
                    <g:select name="enterpriseConfiguration.id" from="${com.bits.bdfp.settings.EnterpriseConfiguration.list()}" optionKey="id" value="${demandOrderApprovalWorkflow?.enterpriseConfiguration?.id}"  />
                </td>
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <span class="button"><g:submitButton name="create" class="save"
                                             value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
    </div>
    </g:form>
</div>
</body>
</html>
