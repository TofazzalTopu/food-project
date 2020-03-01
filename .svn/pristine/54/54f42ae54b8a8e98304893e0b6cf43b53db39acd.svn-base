<%@ page import="com.bits.bdfp.common.Designation" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'designation.label', default: 'Designation')}"/>
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
                                  title="${message(code: 'designation.id.label', default: 'Id')}"/>

                <g:sortableColumn property="code"
                                  title="${message(code: 'designation.code.label', default: 'Code')}"/>

                <g:sortableColumn property="name"
                                  title="${message(code: 'designation.name.label', default: 'Name')}"/>

                <g:sortableColumn property="note"
                                  title="${message(code: 'designation.note.label', default: 'Note')}"/>

                <th><g:message code="designation.userCreated.label" default="User Created"/></th>

                <th><g:message code="designation.userUpdated.label" default="User Updated"/></th>

            </tr>
            </thead>
            <tbody>
            <g:each in="${designationList}" status="i" var="designation">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                    <td><g:link action="show"
                                id="${designation.id}">${fieldValue(bean: designation, field: "id")}</g:link></td>

                    <td>${fieldValue(bean: designation, field: "code")}</td>

                    <td>${fieldValue(bean: designation, field: "name")}</td>

                    <td>${fieldValue(bean: designation, field: "note")}</td>

                    <td>${fieldValue(bean: designation, field: "userCreated")}</td>

                    <td>${fieldValue(bean: designation, field: "userUpdated")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${designationTotal}"/>
    </div>
</div>
</body>
</html>
