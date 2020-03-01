
<%@ page import="com.bits.bdfp.inventory.sales.DistributionPointWarehouse" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'distributionPointWarehouse.label', default: 'DistributionPointWarehouse')}"/>
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
                                  title="${message(code: 'distributionPointWarehouse.id.label', default: 'Id')}"/>
                
                <th><g:message code="distributionPointWarehouse.distributionPoint.label" default="Distribution Point"/></th>
                
                <th><g:message code="distributionPointWarehouse.warehouse.label" default="Warehouse"/></th>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${distributionPointWarehouseList}" status="i" var="distributionPointWarehouse">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${distributionPointWarehouse.id}">${fieldValue(bean: distributionPointWarehouse, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: distributionPointWarehouse, field: "distributionPoint")}</td>
                    
                    <td>${fieldValue(bean: distributionPointWarehouse, field: "warehouse")}</td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${distributionPointWarehouseTotal}"/>
    </div>
</div>
</body>
</html>
