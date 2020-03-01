<%@ page import="com.bits.bdfp.common.Designation" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'designation.label', default: 'Designation')}"/>
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
    <g:hasErrors bean="${designation}">
        <div class="errors">
            <g:renderErrors bean="${designation}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post">
        <g:hiddenField name="id" value="${designation?.id}"/>
        <g:hiddenField name="version" value="${designation?.version}"/>
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="code"><g:message code="designation.code.label"
                                                     default="Code"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: designation, field: 'code', 'errors')}">
                        <g:textField name="code" value="${designation?.code}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="name"><g:message code="designation.name.label"
                                                     default="Name"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: designation, field: 'name', 'errors')}">
                        <g:textField name="name" value="${designation?.name}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="note"><g:message code="designation.note.label"
                                                     default="Note"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: designation, field: 'note', 'errors')}">
                        <g:textField name="note" value="${designation?.note}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="userCreated"><g:message code="designation.userCreated.label"
                                                            default="User Created"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: designation, field: 'userCreated', 'errors')}">
                        <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}"
                                  optionKey="id" value="${designation?.userCreated?.id}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="userUpdated"><g:message code="designation.userUpdated.label"
                                                            default="User Updated"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: designation, field: 'userUpdated', 'errors')}">
                        <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}"
                                  optionKey="id" value="${designation?.userUpdated?.id}" noSelection="['null': '']"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="dateUpdated"><g:message code="designation.dateUpdated.label"
                                                            default="Date Updated"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: designation, field: 'dateUpdated', 'errors')}">
                        <g:datePicker name="dateUpdated" precision="day" value="${designation?.dateUpdated}"
                                      default="none" noSelection="['': '']"/>
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
