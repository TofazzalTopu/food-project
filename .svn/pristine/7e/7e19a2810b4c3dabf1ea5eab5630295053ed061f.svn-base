
<%@ page import="com.bits.bdfp.customer.CustomerTerritorySubArea" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'customerTerritorySubArea.label', default: 'CustomerTerritorySubArea')}"/>
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
                                  title="${message(code: 'customerTerritorySubArea.id.label', default: 'Id')}"/>
                
                <th><g:message code="customerTerritorySubArea.customerMaster.label" default="Customer Master"/></th>
                
                <th><g:message code="customerTerritorySubArea.territorySubArea.label" default="Territory Sub Area"/></th>
                
                <th><g:message code="customerTerritorySubArea.userCreated.label" default="User Created"/></th>
                
                <th><g:message code="customerTerritorySubArea.userUpdated.label" default="User Updated"/></th>
                
                <g:sortableColumn property="dateCreated"
                                  title="${message(code: 'customerTerritorySubArea.dateCreated.label', default: 'Date Created')}"/>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${customerTerritorySubAreaList}" status="i" var="customerTerritorySubArea">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${customerTerritorySubArea.id}">${fieldValue(bean: customerTerritorySubArea, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: customerTerritorySubArea, field: "customerMaster")}</td>
                    
                    <td>${fieldValue(bean: customerTerritorySubArea, field: "territorySubArea")}</td>
                    
                    <td>${fieldValue(bean: customerTerritorySubArea, field: "userCreated")}</td>
                    
                    <td>${fieldValue(bean: customerTerritorySubArea, field: "userUpdated")}</td>
                    
                    <td><g:formatDate date="${customerTerritorySubArea.dateCreated}"/></td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${customerTerritorySubAreaTotal}"/>
    </div>
</div>
</body>
</html>
