
<%@ page import="com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'distributionPointTerritorySubArea.label', default: 'DistributionPointTerritorySubArea')}"/>
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
                                  title="${message(code: 'distributionPointTerritorySubArea.id.label', default: 'Id')}"/>
                
                <th><g:message code="distributionPointTerritorySubArea.distributionPoint.label" default="Distribution Point"/></th>
                
                <th><g:message code="distributionPointTerritorySubArea.territorySubArea.label" default="Territory Sub Area"/></th>
                
                <th><g:message code="distributionPointTerritorySubArea.userCreated.label" default="User Created"/></th>
                
                <th><g:message code="distributionPointTerritorySubArea.userUpdated.label" default="User Updated"/></th>
                
                <g:sortableColumn property="dateCreated"
                                  title="${message(code: 'distributionPointTerritorySubArea.dateCreated.label', default: 'Date Created')}"/>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${distributionPointTerritorySubAreaList}" status="i" var="distributionPointTerritorySubArea">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${distributionPointTerritorySubArea.id}">${fieldValue(bean: distributionPointTerritorySubArea, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: distributionPointTerritorySubArea, field: "distributionPoint")}</td>
                    
                    <td>${fieldValue(bean: distributionPointTerritorySubArea, field: "territorySubArea")}</td>
                    
                    <td>${fieldValue(bean: distributionPointTerritorySubArea, field: "userCreated")}</td>
                    
                    <td>${fieldValue(bean: distributionPointTerritorySubArea, field: "userUpdated")}</td>
                    
                    <td><g:formatDate date="${distributionPointTerritorySubArea.dateCreated}"/></td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${distributionPointTerritorySubAreaTotal}"/>
    </div>
</div>
</body>
</html>
