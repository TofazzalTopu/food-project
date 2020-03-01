<%@ page import="com.bits.bdfp.common.Designation" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'designation.label', default: 'Designation')}"/>
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
                <td valign="top" class="name"><g:message code="designation.id.label"
                                                         default="Id"/></td>

                <td valign="top" class="value">${fieldValue(bean: designation, field: "id")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.code.label"
                                                         default="Code"/></td>

                <td valign="top" class="value">${fieldValue(bean: designation, field: "code")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.name.label"
                                                         default="Name"/></td>

                <td valign="top" class="value">${fieldValue(bean: designation, field: "name")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.note.label"
                                                         default="Note"/></td>

                <td valign="top" class="value">${fieldValue(bean: designation, field: "note")}</td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.userCreated.label"
                                                         default="User Created"/></td>

                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${designation?.userCreated?.id}">${designation?.userCreated?.encodeAsHTML()}</g:link></td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.userUpdated.label"
                                                         default="User Updated"/></td>

                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${designation?.userUpdated?.id}">${designation?.userUpdated?.encodeAsHTML()}</g:link></td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.dateCreated.label"
                                                         default="Date Created"/></td>

                <td valign="top" class="value"><g:formatDate date="${designation?.dateCreated}"/></td>

            </tr>

            <tr class="prop">
                <td valign="top" class="name"><g:message code="designation.dateUpdated.label"
                                                         default="Date Updated"/></td>

                <td valign="top" class="value"><g:formatDate date="${designation?.dateUpdated}"/></td>

            </tr>

            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${designation?.id}"/>
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
